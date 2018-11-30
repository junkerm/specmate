package com.specmate.persistency.cdo.internal;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.emf.cdo.CDOObject;
import org.eclipse.emf.cdo.common.CDOCommonSession.Options.PassiveUpdateMode;
import org.eclipse.emf.cdo.common.id.CDOID;
import org.eclipse.emf.cdo.common.revision.CDORevision;
import org.eclipse.emf.cdo.eresource.CDOResource;
import org.eclipse.emf.cdo.net4j.CDONet4jSession;
import org.eclipse.emf.cdo.net4j.CDONet4jUtil;
import org.eclipse.emf.cdo.net4j.CDOSessionRecoveryEvent;
import org.eclipse.emf.cdo.net4j.ReconnectingCDOSessionConfiguration;
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
import org.eclipse.net4j.connector.IConnector;
import org.eclipse.net4j.tcp.TCPUtil;
import org.eclipse.net4j.util.StringUtil;
import org.eclipse.net4j.util.container.IManagedContainer;
import org.eclipse.net4j.util.container.IPluginContainer;
import org.eclipse.net4j.util.event.IEvent;
import org.eclipse.net4j.util.event.IListener;
import org.eclipse.net4j.util.lifecycle.LifecycleUtil;
import org.eclipse.net4j.util.om.OMPlatform;
import org.eclipse.net4j.util.om.log.PrintLogHandler;
import org.eclipse.net4j.util.om.trace.PrintTraceHandler;
import org.eclipse.net4j.util.security.PasswordCredentialsProvider;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.event.EventAdmin;
import org.osgi.service.log.LogService;

import com.specmate.administration.api.IStatusService;
import com.specmate.common.SpecmateException;
import com.specmate.common.SpecmateValidationException;
import com.specmate.metrics.IGauge;
import com.specmate.metrics.IMetricsService;
import com.specmate.model.support.util.SpecmateEcoreUtil;
import com.specmate.persistency.IChangeListener;
import com.specmate.persistency.IPackageProvider;
import com.specmate.persistency.IPersistencyService;
import com.specmate.persistency.ITransaction;
import com.specmate.persistency.IView;
import com.specmate.persistency.event.EChangeKind;
import com.specmate.persistency.event.ModelEvent;
import com.specmate.urihandler.IURIFactory;

@Component(service = IPersistencyService.class, configurationPolicy = ConfigurationPolicy.REQUIRE, configurationPid = CDOPersistencyServiceConfig.PID)
public class CDOPersistencyService implements IPersistencyService, IListener {

	/** The CDO container */
	private IManagedContainer container;

	/** The CDO connector */
	private IConnector connector;

	/** The CDO session */
	private CDONet4jSession session;

	private List<IChangeListener> listeners = new ArrayList<>();

	/** Flag to signal if the persistence is active */
	private boolean active;

	/** List of open views */
	private List<ViewImpl> openViews = new ArrayList<>();

	/** The list of open transactions */
	private List<TransactionImpl> openTransactions = new ArrayList<>();

	/** Gauge to count open transactions */
	private IGauge transactionGauge;

	/** The name of the repository to open */
	private String repositoryName;

	/** The name of the resource to use */
	private String resourceName;

	/** The configured CDO host and port to connect to */
	private String hostAndPort;

	/** Reference to the log servcie */
	private LogService logService;

	/** Reference to the event admin */
	private EventAdmin eventAdmin;

	/** Reference to an uri factory */
	private IURIFactory uriFactory;

	/** Reference to the metrics service */
	private IMetricsService metricsService;

	/** Reference to the status service */
	private IStatusService statusService;

	/** Reference to a package provider */
	private IPackageProvider packageProvider;

	private CDOView eventView;

	private String cdoUser;

	private String cdoPassword;

	@Activate
	public void activate(Map<String, Object> properties) throws SpecmateException, SpecmateValidationException {
		readConfig(properties);
		this.transactionGauge = metricsService.createGauge("Transactions", "The number of open transactions");
		start();
	}

	@Deactivate
	public void deactivate() {
		this.shutdown();
	}

	private void readConfig(Map<String, Object> properties) throws SpecmateValidationException {
		this.repositoryName = (String) properties.get(CDOPersistencyServiceConfig.KEY_REPOSITORY_NAME);
		this.resourceName = (String) properties.get(CDOPersistencyServiceConfig.KEY_RESOURCE_NAME);
		this.hostAndPort = (String) properties.get(CDOPersistencyServiceConfig.KEY_SERVER_HOST_PORT);
		this.cdoUser = (String) properties.get(CDOPersistencyServiceConfig.KEY_CDO_USER);
		this.cdoPassword = (String) properties.get(CDOPersistencyServiceConfig.KEY_CDO_PASSWORD);

		if (StringUtils.isEmpty(this.repositoryName)) {
			throw new SpecmateValidationException("Repository name is empty.");
		}
		if (StringUtils.isEmpty(this.resourceName)) {
			throw new SpecmateValidationException("Resource name is empty.");
		}
		if (StringUtils.isEmpty(this.hostAndPort)) {
			throw new SpecmateValidationException("Host and port is empty.");
		}

		if (StringUtil.isEmpty(this.cdoUser)) {
			throw new SpecmateValidationException("No CDO user name given");
		}

		if (StringUtil.isEmpty(this.cdoPassword)) {
			throw new SpecmateValidationException("No CDO password given");
		}
	}

	@Override
	public synchronized void start() throws SpecmateException {
		startPersistency();
		updateOpenViews();
		openEventView();
		this.active = true;
	}

	private void openEventView() throws SpecmateException {
		this.eventView = openCDOView();
	}

	private void updateOpenViews() throws SpecmateException {
		for (ViewImpl view : this.openViews) {
			view.update(openCDOView());
		}

		for (TransactionImpl transaction : this.openTransactions) {
			transaction.update(openCDOTransaction());
		}
	}

	@Override
	public synchronized void shutdown() {
		if (!active) {
			return;
		}
		session.removeListener(this);
		this.active = false;
		LifecycleUtil.deactivate(session);
		LifecycleUtil.deactivate(connector);
	}

	private void startPersistency() throws SpecmateException {
		OMPlatform.INSTANCE.setDebugging(true);
		OMPlatform.INSTANCE.addLogHandler(PrintLogHandler.CONSOLE);
		OMPlatform.INSTANCE.addTraceHandler(PrintTraceHandler.CONSOLE);
		createContainer();
		createSession();
		installListener();
	}

	private void createContainer() {
		container = IPluginContainer.INSTANCE;
		Net4jUtil.prepareContainer(container);
		TCPUtil.prepareContainer(container);
		CDONet4jServerUtil.prepareContainer(container);
	}

	private void createSession() {
		PasswordCredentialsProvider credentialsProvider = new PasswordCredentialsProvider(this.cdoUser,
				this.cdoPassword);

		ReconnectingCDOSessionConfiguration configuration = CDONet4jUtil
				.createReconnectingSessionConfiguration(this.hostAndPort, this.repositoryName, container);
		configuration.setHeartBeatEnabled(false);
		configuration.setConnectorTimeout(60000);
		configuration.setSignalTimeout(60000);
		configuration.setCredentialsProvider(credentialsProvider);
		configuration.setPassiveUpdateEnabled(true);
		configuration.setPassiveUpdateMode(PassiveUpdateMode.ADDITIONS);
		session = configuration.openNet4jSession();

		session.addListener(new IListener() {
			@Override
			public void notifyEvent(final IEvent event) {
				if (event instanceof CDOSessionRecoveryEvent) {
					CDOSessionRecoveryEvent recoveryEvent = (CDOSessionRecoveryEvent) event;
					switch (recoveryEvent.getType()) {
					case STARTED:
						logService.log(LogService.LOG_WARNING, "Reconnecting CDO session started.");
						break;
					case FINISHED:
						logService.log(LogService.LOG_WARNING, "Reconnecting CDO session finished.");
						break;
					}
				}
			}
		});
		registerPackages();
		createModelResource();
	}

	private void createModelResource() {
		String resourceName = this.resourceName;
		CDOTransaction transaction = session.openTransaction();
		transaction.getOrCreateResource(resourceName);
		try {
			transaction.commit();
		} catch (CommitException e) {
			logService.log(LogService.LOG_ERROR, "Could not create resource " + resourceName, e);
		}
	}

	private void registerPackages() {
		CDOTransaction transaction = session.openTransaction();
		CDOResource resource = transaction.getOrCreateResource("dummy");
		for (EPackage pack : packageProvider.getPackages()) {
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
			logService.log(LogService.LOG_ERROR, "Could not commit packages to dummy resource", e);
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

	public ITransaction openTransaction(boolean attachCommitListeners, String alterantiveResourceName)
			throws SpecmateException {
		if (!this.active) {
			throw new SpecmateException("Attempt to open transaction when persistency service is not active");
		}
		CDOTransaction cdoTransaction = openCDOTransaction();
		TransactionImpl transaction = new TransactionImpl(this, cdoTransaction, alterantiveResourceName, logService,
				statusService, attachCommitListeners ? listeners : Collections.emptyList());
		this.openTransactions.add(transaction);
		this.transactionGauge.inc();
		return transaction;
	}

	public void closedTransaction(TransactionImpl transactionImpl) {
		this.openTransactions.remove(transactionImpl);
		this.transactionGauge.dec();
	}

	@Override
	public IView openView() throws SpecmateException {
		if (!this.active) {
			throw new SpecmateException("Attempt to open transaction when persistency service is not active");
		}
		CDOView cdoView = openCDOView();
		ViewImpl view = new ViewImpl(this, cdoView, this.resourceName, logService);

		this.openViews.add(view);
		this.transactionGauge.inc();
		return view;
	}

	public void closedView(ViewImpl viewImpl) {
		this.openViews.remove(viewImpl);
		this.transactionGauge.dec();
	}

	/* package */CDOTransaction openCDOTransaction() throws SpecmateException {
		CDOTransaction transaction = session.openTransaction();
		transaction.options().addChangeSubscriptionPolicy(CDOAdapterPolicy.ALL);
		transaction.options().setInvalidationNotificationEnabled(true);
		transaction.options().addConflictResolver(new CDOMergingConflictResolver());
		logService.log(LogService.LOG_DEBUG, "Transaction initialized: " + transaction.getViewID());
		return transaction;
	}

	/* package */CDOView openCDOView() throws SpecmateException {
		CDOView view = session.openView();
		view.options().addChangeSubscriptionPolicy(CDOAdapterPolicy.ALL);
		view.options().setInvalidationNotificationEnabled(true);
		logService.log(LogService.LOG_DEBUG, "View initialized: " + view.getViewID());
		return view;
	}

	@Override
	public void notifyEvent(IEvent event) {
		if (!(event instanceof CDOSessionInvalidationEvent)) {
			return;
		}
		CDOSessionInvalidationEvent invalEvent = (CDOSessionInvalidationEvent) event;
		CDOView localView = invalEvent.getLocalTransaction();
		final CDOView view = localView != null ? localView : eventView;

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
					Object newValue, int index, String objectClassName) {
				postEvent(view, id, objectClassName, 0, Collections.singletonMap(feature, newValue), changeKind, index);
			}
		};
		processor.process();
	}

	public boolean isActive() {
		return this.active;
	}

	public void removeChangeListener(IChangeListener listener) {
		listeners.remove(listener);
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

		String idAsString = SpecmateEcoreUtil.buildStringId(id);

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

	public void removeModelProvider(IPackageProvider provider) {
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
	public void setModelProvider(IPackageProvider provider) {
		this.packageProvider = provider;
	}

	@Reference(cardinality = ReferenceCardinality.MULTIPLE, policy = ReferencePolicy.DYNAMIC)
	public void addChangeListener(IChangeListener listener) {
		listeners.add(listener);
	}

	@Reference(cardinality = ReferenceCardinality.OPTIONAL)
	public void setStatusService(IStatusService statusService) {
		this.statusService = statusService;
	}

	@Reference
	public void setMetricsService(IMetricsService metricsService) {
		this.metricsService = metricsService;
	}

	@Reference
	public void setLogService(LogService logService) {
		this.logService = logService;
	}
}
