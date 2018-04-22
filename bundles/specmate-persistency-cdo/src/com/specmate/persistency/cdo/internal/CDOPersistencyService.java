package com.specmate.persistency.cdo.internal;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.emf.cdo.CDOObject;
import org.eclipse.emf.cdo.common.id.CDOID;
import org.eclipse.emf.cdo.common.id.CDOIDUtil;
import org.eclipse.emf.cdo.common.revision.CDORevision;
import org.eclipse.emf.cdo.eresource.CDOResource;
import org.eclipse.emf.cdo.net4j.CDONet4jSession;
import org.eclipse.emf.cdo.net4j.CDONet4jSessionConfiguration;
import org.eclipse.emf.cdo.net4j.CDONet4jUtil;
import org.eclipse.emf.cdo.server.CDOServerUtil;
import org.eclipse.emf.cdo.server.IRepository;
import org.eclipse.emf.cdo.server.IStore;
import org.eclipse.emf.cdo.server.db.CDODBUtil;
import org.eclipse.emf.cdo.server.db.mapping.IMappingStrategy;
import org.eclipse.emf.cdo.server.net4j.CDONet4jServerUtil;
import org.eclipse.emf.cdo.session.CDOSessionInvalidationEvent;
import org.eclipse.emf.cdo.transaction.CDOTransaction;
import org.eclipse.emf.cdo.util.CommitException;
import org.eclipse.emf.cdo.view.CDOAdapterPolicy;
import org.eclipse.emf.cdo.view.CDOView;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.spi.cdo.CDOMergingConflictResolver;
import org.eclipse.net4j.Net4jUtil;
import org.eclipse.net4j.acceptor.IAcceptor;
import org.eclipse.net4j.connector.IConnector;
import org.eclipse.net4j.db.DBUtil;
import org.eclipse.net4j.db.IDBAdapter;
import org.eclipse.net4j.db.IDBConnectionProvider;
import org.eclipse.net4j.db.h2.H2Adapter;
import org.eclipse.net4j.jvm.JVMUtil;
import org.eclipse.net4j.tcp.TCPUtil;
import org.eclipse.net4j.util.container.IManagedContainer;
import org.eclipse.net4j.util.container.IPluginContainer;
import org.eclipse.net4j.util.event.IEvent;
import org.eclipse.net4j.util.event.IListener;
import org.eclipse.net4j.util.lifecycle.LifecycleUtil;
import org.eclipse.net4j.util.om.OMPlatform;
import org.eclipse.net4j.util.om.log.PrintLogHandler;
import org.eclipse.net4j.util.om.trace.PrintTraceHandler;
import org.h2.jdbcx.JdbcDataSource;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.event.EventAdmin;
import org.osgi.service.log.LogService;
import org.osgi.service.metatype.annotations.Designate;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;

import com.specmate.administration.api.IStatusService;
import com.specmate.common.SpecmateException;
import com.specmate.migration.api.IMigratorService;
import com.specmate.persistency.IChangeListener;
import com.specmate.persistency.IPackageProvider;
import com.specmate.persistency.IPersistencyService;
import com.specmate.persistency.ITransaction;
import com.specmate.persistency.IView;
import com.specmate.persistency.cdo.config.CDOPersistenceConfig;
import com.specmate.persistency.cdo.internal.CDOPersistencyService.Config;
import com.specmate.persistency.event.EChangeKind;
import com.specmate.persistency.event.ModelEvent;
import com.specmate.urihandler.IURIFactory;

@Designate(ocd = Config.class)
@Component(service = IPersistencyService.class, configurationPid = CDOPersistenceConfig.PID, configurationPolicy = ConfigurationPolicy.REQUIRE)
public class CDOPersistencyService implements IPersistencyService, IListener {

	private static final String NET4J_JVM_NAME = "com.specmate.cdo";
	private CDONet4jSessionConfiguration configuration;
	private IManagedContainer container;
	private IConnector connector;
	private CDONet4jSession session;

	private String repository;
	private String resourceName;
	private IAcceptor acceptorJVM;
	private IAcceptor acceptorTCP;
	private LogService logService;
	public static IRepository theRepository;
	// private CDOView eventView;
	private EventAdmin eventAdmin;
	private IURIFactory uriFactory;
	private List<EPackage> packages = new ArrayList<>();
	private List<IChangeListener> listeners = new ArrayList<>();
	private String userResourceName;
	private String jdbcConnection;
	private IMigratorService migrationService;
	private IStatusService statusService;
	private boolean active;
	private List<ViewImpl> openViews = new ArrayList<>();
	private List<TransactionImpl> openTransactions = new ArrayList<>();

	@ObjectClassDefinition(name = "")
	@interface Config {
		String cdoJDBCConnection()

		default "jdbc:h2:./database/specmate";

		String cdoRepositoryName()

		default "repo1";

		String cdoResourceName()

		default "specmateResource";

		String cdoUserResourceName() default "userResource";
	};

	@Activate
	public void activate(Config config) throws SpecmateException {
		if (!readConfig(config)) {
			logService.log(LogService.LOG_ERROR, "Invalid configuration of cdo persistency. Fall back to defaults.");
			throw new SpecmateException("Invalid configuration of cdo persistency. Fall back to defaults.");
		}
		start();
	}

	@Modified
	public void modified(Config config) throws SpecmateException {
		shutdown();
		if (!readConfig(config)) {
			logService.log(LogService.LOG_ERROR, "Invalid configuration of cdo persistency. Fall back to defaults.");
			throw new SpecmateException("Invalid configuration of cdo persistency. Fall back to defaults.");
		}
		start();
	}

	public void activateFromTest(String dbname) {
		// TODO This method is used in migration tests so we can manually start
		// and activate a service,
		// since we cannot call the above active(config) method.
		// Once we find a solution this workaround, remember that we will call
		// the above activate method which
		// does the migration, hence a manual initiation of the migration as it
		// is currently done in the tests
		// will not be necessary anymore.
		jdbcConnection = "jdbc:h2:mem:" + dbname + ";DB_CLOSE_DELAY=-1";
		// jdbcConnection = "jdbc:h2:./database/specmate";
		repository = "repo1";
		resourceName = "specmateResource";
		userResourceName = "userResource";

		startPersistency();
		this.active = true;
	}

	@Override
	public synchronized void start() throws SpecmateException {
		if (migrationService.needsMigration()) {
			migrationService.doMigration();
		}
		startPersistency();
		updateOpenViews();
		this.active = true;
	}

	private void updateOpenViews() throws SpecmateException {
		for (ViewImpl view : this.openViews) {
			view.update(openCDOView());
		}

		for (TransactionImpl transaction : this.openTransactions) {
			transaction.update(openCDOTransaction());
		}
	}

	private void closeOpenViews() {
		for (ViewImpl view : this.openViews) {
			view.close();
		}

		for (TransactionImpl transaction : this.openTransactions) {
			transaction.close();
		}
	}

	@Override
	public synchronized void shutdown() {
		if (!active) {
			return;
		}
		session.removeListener(this);
		this.active = false;
		// LifecycleUtil.deactivate(eventView);
		closeOpenViews();
		LifecycleUtil.deactivate(session);
		LifecycleUtil.deactivate(connector);
		LifecycleUtil.deactivate(acceptorJVM);
		LifecycleUtil.deactivate(acceptorTCP);
		LifecycleUtil.deactivate(theRepository);
	}

	private void startPersistency() {
		OMPlatform.INSTANCE.setDebugging(true);
		OMPlatform.INSTANCE.addLogHandler(PrintLogHandler.CONSOLE);
		OMPlatform.INSTANCE.addTraceHandler(PrintTraceHandler.CONSOLE);
		createContainer();
		createServer();
		createSession();
		// openEventView();
		installListener();
	}

	// private void openEventView() {
	// eventView = session.openView();
	// eventView.options().addChangeSubscriptionPolicy(CDOAdapterPolicy.ALL);
	// eventView.options().setInvalidationNotificationEnabled(false);
	// }

	@Reference
	public void setLogService(LogService logService) {
		this.logService = logService;
	}

	private void createContainer() {
		container = IPluginContainer.INSTANCE;
		Net4jUtil.prepareContainer(container);
		JVMUtil.prepareContainer(container);
		TCPUtil.prepareContainer(container);
		CDONet4jServerUtil.prepareContainer(container);
	}

	private void createServer() {
		IStore store = createStore();
		createRepository(store);
		createAcceptors();
	}

	private void createAcceptors() {
		acceptorJVM = (IAcceptor) IPluginContainer.INSTANCE.getElement("org.eclipse.net4j.acceptors", "jvm",
				NET4J_JVM_NAME);
		acceptorTCP = (IAcceptor) IPluginContainer.INSTANCE.getElement("org.eclipse.net4j.acceptors", "tcp",
				"localhost:2036");
	}

	private void createRepository(IStore store) {
		Map<String, String> props = new HashMap<>();
		props.put(IRepository.Props.OVERRIDE_UUID, "specmate");
		props.put(IRepository.Props.SUPPORTING_AUDITS, "true");
		props.put(IRepository.Props.SUPPORTING_BRANCHES, "true");
		theRepository = CDOServerUtil.createRepository(repository, store, props);
		CDOServerUtil.addRepository(IPluginContainer.INSTANCE, theRepository);
	}

	private IStore createStore() {
		JdbcDataSource dataSource = new JdbcDataSource();
		dataSource.setURL(this.jdbcConnection);
		IMappingStrategy mappingStrategy = CDODBUtil.createHorizontalMappingStrategy(true);
		IDBAdapter dbAdapter = new H2Adapter();
		IDBConnectionProvider dbConnectionProvider = DBUtil.createConnectionProvider(dataSource);
		IStore store = CDODBUtil.createStore(mappingStrategy, dbAdapter, dbConnectionProvider);
		return store;
	}

	private void createSession() {
		connector = JVMUtil.getConnector(container, NET4J_JVM_NAME);
		configuration = CDONet4jUtil.createNet4jSessionConfiguration();
		configuration.setConnector(connector);
		configuration.setRepositoryName(this.repository);
		session = configuration.openNet4jSession();
		registerPackages();
		createModelResource();
	}

	private void createModelResource() {
		CDOTransaction transaction = session.openTransaction();
		transaction.getOrCreateResource(resourceName);
		try {
			transaction.commit();
		} catch (CommitException e) {
			logService.log(LogService.LOG_ERROR, "Could not create resource " + resourceName);
		}
	}

	private void registerPackages() {
		CDOTransaction transaction = session.openTransaction();
		CDOResource resource = transaction.getOrCreateResource("dummy");
		for (EPackage pack : packages) {
			if (session.getPackageRegistry().getEPackage(pack.getNsURI()) == null) {
				logService.log(LogService.LOG_INFO, "Registering package " + pack.getNsURI());
				EClass eClass = getAnyConcreteEClass(pack);
				EObject object = pack.getEFactoryInstance().create(eClass);
				resource.getContents().add(object);
			}
		}
		try {
			transaction.commit();
		} catch (Exception e) {
			logService.log(LogService.LOG_ERROR, "Could not commit packages to dummy resource");
		}
		transaction.close();
	}

	private void installListener() {
		session.addListener(this);
	}

	@Override
	public ITransaction openTransaction() throws SpecmateException {
		return openTransaction(true);
	}

	@Override
	public ITransaction openTransaction(boolean attachCommitListeners) throws SpecmateException {
		return openTransaction(attachCommitListeners, this.resourceName);
	}

	@Override
	public ITransaction openUserTransaction() throws SpecmateException {
		return openTransaction(false, this.userResourceName);
	}

	public ITransaction openTransaction(boolean attachCommitListeners, String alterantiveResourceName)
			throws SpecmateException {
		if (!this.active) {
			throw new SpecmateException("Attempt to open transaction when persistency service is not active");
		}
		CDOTransaction cdoTransaction = openCDOTransaction();
		TransactionImpl transaction = new TransactionImpl(this, cdoTransaction, alterantiveResourceName, logService,
				statusService, attachCommitListeners ? listeners : Collections.emptyList());
		this.openTransactions.add(transaction);
		return transaction;
	}

	@Override
	public IView openView() throws SpecmateException {
		if (!this.active) {
			throw new SpecmateException("Attempt to open transaction when persistency service is not active");
		}
		CDOView cdoView = openCDOView();
		ViewImpl view = new ViewImpl(cdoView, resourceName, logService);
		this.openViews.add(view);
		return view;
	}

	/* package */CDOTransaction openCDOTransaction() throws SpecmateException {
		CDOTransaction transaction = session.openTransaction();
		transaction.options().addChangeSubscriptionPolicy(CDOAdapterPolicy.ALL);
		transaction.options().setInvalidationNotificationEnabled(true);
		transaction.options().addConflictResolver(new CDOMergingConflictResolver());
		logService.log(LogService.LOG_INFO, "Transaction initialized: " + transaction.getViewID());
		return transaction;
	}

	/* package */CDOView openCDOView() throws SpecmateException {
		CDOView view = session.openView();
		view.options().addChangeSubscriptionPolicy(CDOAdapterPolicy.ALL);
		view.options().setInvalidationNotificationEnabled(true);
		logService.log(LogService.LOG_INFO, "Transaction initialized: " + view.getViewID());
		return view;
	}

	private boolean readConfig(Config config) {
		jdbcConnection = config.cdoJDBCConnection();
		repository = config.cdoRepositoryName();
		resourceName = config.cdoResourceName();
		userResourceName = config.cdoUserResourceName();
		if (StringUtils.isEmpty(repository) || StringUtils.isEmpty(resourceName)
				|| StringUtils.isEmpty(userResourceName)) {
			return false;
		}
		logService.log(LogService.LOG_INFO, "Configured CDO with [repository=" + repository + ", resource="
				+ resourceName + ", user resource=" + userResourceName + "]");
		return true;
	}

	@Override
	public void notifyEvent(IEvent event) {
		if (!(event instanceof CDOSessionInvalidationEvent)) {
			return;
		}
		CDOSessionInvalidationEvent invalEvent = (CDOSessionInvalidationEvent) event;
		CDOTransaction view = invalEvent.getLocalTransaction();

		DeltaProcessor processor = new DeltaProcessor(invalEvent) {

			@Override
			protected void newObject(CDOID id, String className, Map<EStructuralFeature, Object> featureMap) {
				postEvent(view, id, className, 0, featureMap, EChangeKind.NEW, 0);
			}

			@Override
			protected void detachedObject(CDOID id, int version) {
				postEvent(view, id, null, version, null, EChangeKind.DELETE, 0);
			}

			@Override
			public void changedObject(CDOID id, EStructuralFeature feature, EChangeKind changeKind, Object oldValue,
					Object newValue, int index) {
				postEvent(view, id, null, 0, Collections.singletonMap(feature, newValue), changeKind, index);
			}
		};
		processor.process();
	}

	private void postEvent(CDOView eventView, CDOID id, String className, int version,
			Map<EStructuralFeature, Object> featureMap, EChangeKind changeKind, int index) {

		Optional<String> optUri;
		if (changeKind == EChangeKind.DELETE) {
			optUri = resolveUri(eventView, id, version);
		} else {
			optUri = resolveUri(eventView, id);
		}
		if (!optUri.isPresent()) {
			logService.log(LogService.LOG_ERROR, "Could not determine uri for object");
			return;
		}
		String uri = optUri.get();

		ModelEvent event = null;

		StringBuilder builder = new StringBuilder();
		CDOIDUtil.write(builder, id);
		String idAsString = builder.toString();

		switch (changeKind) {
		case ADD:
			event = new ModelEvent(idAsString, null, uri, featureMap, EChangeKind.ADD, index);
			break;
		case REMOVE:
			event = new ModelEvent(idAsString, null, uri, featureMap, EChangeKind.REMOVE, index);
			break;
		case CLEAR:
			event = new ModelEvent(idAsString, null, uri, featureMap, EChangeKind.CLEAR, -1);
			break;
		case SET:
			Entry<EStructuralFeature, Object> entry = featureMap.entrySet().iterator().next();
			if (entry.getValue() instanceof CDOID) {
				CDOObject newValue = eventView.getObject((CDOID) entry.getValue());
				featureMap.put(entry.getKey(), newValue);
			}
			event = new ModelEvent(idAsString, null, uri, featureMap, EChangeKind.SET);
			break;
		case NEW:
			event = new ModelEvent(idAsString, className, uri, featureMap, EChangeKind.NEW);
			break;
		case DELETE:
			event = new ModelEvent(idAsString, null, null, null, EChangeKind.DELETE);
			break;
		default:
			logService.log(LogService.LOG_ERROR, "Unsupported Delta type:" + changeKind.toString());
		}
		if (event != null) {
			eventAdmin.postEvent(event);
		}
	}

	private Optional<String> resolveUri(CDOView eventView, CDOID id, int version) {
		CDORevision revision = getSession().getRevisionManager().getRevisionByVersion(id,
				eventView.getBranch().getVersion(version), 0, true);
		CDOView view = getSession().openView(revision.getTimeStamp());
		Optional<String> uri = resolveUri(view, id);
		view.close();
		return uri;
	}

	private Optional<String> resolveUri(CDOView view, CDOID id) {
		try {
			CDOObject object = view.getObject(id);
			return Optional.ofNullable(uriFactory.getURI(object));
		} catch (SpecmateException e) {
			return Optional.empty();
		}
	}

	// Cloned from CDOPackageTypeRegistry, as it is defined private
	private static EClass getAnyConcreteEClass(EPackage ePackage) {
		for (EClassifier classifier : ePackage.getEClassifiers()) {
			if (classifier instanceof EClass) {
				EClass eClass = (EClass) classifier;
				if (!(eClass.isAbstract() || eClass.isInterface())) {
					return eClass;
				}
			}
		}

		for (EPackage subpackage : ePackage.getESubpackages()) {
			EClass eClass = getAnyConcreteEClass(subpackage);
			if (eClass != null) {
				return eClass;
			}
		}

		return null;
	}

	/* package */CDONet4jSession getSession() {
		return this.session;
	}

	@Reference
	public void setUriFactory(IURIFactory factory) {
		this.uriFactory = factory;
	}

	@Reference
	public void setEventAdmin(EventAdmin admin) {
		this.eventAdmin = admin;
	}

	@Reference
	public void addModelProvider(IPackageProvider provider) {
		this.packages.addAll(provider.getPackages());
	}

	public void removeModelProvider(IPackageProvider provider) {
	}

	@Reference(cardinality = ReferenceCardinality.MULTIPLE, policy = ReferencePolicy.DYNAMIC)
	public void addChangeListener(IChangeListener listener) {
		listeners.add(listener);
	}

	public void removeChangeListener(IChangeListener listener) {
		listeners.remove(listener);
	}

	@Reference
	public void setMigrationService(IMigratorService migrationService) {
		this.migrationService = migrationService;
	}

	@Reference(cardinality = ReferenceCardinality.OPTIONAL)
	public void setStatusService(IStatusService statusService) {
		this.statusService = statusService;
	}

	public boolean isActive() {
		return this.active;
	}

}
