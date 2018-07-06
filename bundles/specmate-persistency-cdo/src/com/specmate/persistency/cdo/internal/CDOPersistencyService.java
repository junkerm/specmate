package com.specmate.persistency.cdo.internal;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.emf.cdo.CDOObject;
import org.eclipse.emf.cdo.common.id.CDOID;
import org.eclipse.emf.cdo.common.revision.CDORevision;
import org.eclipse.emf.cdo.eresource.CDOResource;
import org.eclipse.emf.cdo.internal.net4j.protocol.CDOClientProtocolFactory;
import org.eclipse.emf.cdo.net4j.CDONet4jSession;
import org.eclipse.emf.cdo.net4j.CDONet4jSessionConfiguration;
import org.eclipse.emf.cdo.net4j.CDONet4jUtil;
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
import org.eclipse.net4j.FactoriesProtocolProvider;
import org.eclipse.net4j.Net4jUtil;
import org.eclipse.net4j.buffer.IBufferProvider;
import org.eclipse.net4j.connector.IConnector;
import org.eclipse.net4j.internal.tcp.TCPSelector;
import org.eclipse.net4j.protocol.IProtocolProvider;
import org.eclipse.net4j.tcp.TCPUtil;
import org.eclipse.net4j.util.concurrent.ThreadPool;
import org.eclipse.net4j.util.container.IManagedContainer;
import org.eclipse.net4j.util.container.IPluginContainer;
import org.eclipse.net4j.util.event.IEvent;
import org.eclipse.net4j.util.event.IListener;
import org.eclipse.net4j.util.lifecycle.LifecycleUtil;
import org.eclipse.net4j.util.om.OMPlatform;
import org.eclipse.net4j.util.om.log.PrintLogHandler;
import org.eclipse.net4j.util.om.trace.PrintTraceHandler;
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
import com.specmate.model.support.util.SpecmateEcoreUtil;
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

	/** The container for the CDO client */
	private IManagedContainer container;

	/** The connector to the CDO server */
	private IConnector connector;

	/** The CDO session */
	private CDONet4jSession session;

	/** Name of the resource */
	private String resourceName;

	/** The repository name */
	private String repositoryName;

	/** Host string (host:port) */
	private String host;

	/** The name of the host */
	private String hostName;

	/** The port of the server */
	private int port;

	/** The commit listeners that are attached to the transactions */
	private List<IChangeListener> attachedCommitListeners = new ArrayList<>();

	/** Flag that signals if the service is active */
	private boolean active;

	/** Flag that signals if the service is connected to the server */
	private boolean connected;

	/** List of open views */
	private List<ViewImpl> openViews = new ArrayList<>();

	/** List of open transactions */
	private List<TransactionImpl> openTransactions = new ArrayList<>();

	/** Executor for the check connection thread */
	private ScheduledExecutorService checkConnectionEexcutor;

	/** Reference to the event admin service */
	private EventAdmin eventAdmin;

	/** Reference to an URIFactory */
	private IURIFactory uriFactory;

	/** Reference to the log service */
	private LogService logService;

	/** Reference to the status service */
	private IStatusService statusService;

	/** Reference to the package providers */
	private IPackageProvider packageProvider;

	@ObjectClassDefinition(name = "")
	@interface Config {
		String cdoHost()

		default "localhost:2036";

		String cdoRepositoryName()

		default "specmate_repository";

		String cdoResourceName()

		default "specmate_resource";
	};

	@Activate
	public void activate(Config config) throws SpecmateException {
		if (!readConfig(config)) {
			logService.log(LogService.LOG_ERROR, "Invalid configuration of cdo persistency. Fall back to defaults.");
			throw new SpecmateException("Invalid configuration of cdo persistency. Fall back to defaults.");
		}
		start();
		startMonitoringThread();
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

	@Override
	public ITransaction openTransaction() throws SpecmateException {
		return openTransaction(true);
	}

	@Override
	public ITransaction openTransaction(boolean attachCommitListeners) throws SpecmateException {
		return openTransaction(attachCommitListeners, this.resourceName);
	}

	@Override
	public IView openView() throws SpecmateException {
		if (!this.active) {
			throw new SpecmateException("Attempt to open transaction when persistency service is not active");
		}
		CDOView cdoView = openCDOView();
		ViewImpl view = new ViewImpl(this, cdoView, resourceName, logService);
		this.openViews.add(view);
		return view;
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

	/**
	 * Starts a thread that periodically checks if the CDO server is still reachable
	 */
	private void startMonitoringThread() {

		this.checkConnectionEexcutor = Executors.newScheduledThreadPool(1);
		checkConnectionEexcutor.scheduleWithFixedDelay(() -> {

			if (this.connected) {
				if (!checkConnection()) {
					shutdown();
					this.connected = false;
					logService.log(LogService.LOG_WARNING, "Lost connection to CDO server.");
				}
			} else {
				if (checkConnection()) {
					try {
						logService.log(LogService.LOG_INFO, "Connection to CDO server re-established.");
						start();
						this.connected = true;
					} catch (SpecmateException e) {
						logService.log(LogService.LOG_ERROR, "Could not restart persistency.");
					}
				}
			}

		}, 5, 5, TimeUnit.SECONDS);

	}

	/** Checks if the CDO sever is reachable */
	private boolean checkConnection() {
		try (Socket socket = new Socket()) {
			socket.connect(new InetSocketAddress(this.hostName, this.port), 5000);
			socket.close();
			return true;
		} catch (IOException e) {
			return false; // Either timeout or unreachable or failed DNS lookup.
		}
	}

	/** Starts the CDO client */
	@Override
	public synchronized void start() throws SpecmateException {
		startPersistency();
		updateOpenViews();
		this.connected = true;
		this.active = true;
	}

	/**
	 * Updates the open views and transactions by injecting fresh views and
	 * transactions
	 */
	private void updateOpenViews() throws SpecmateException {
		for (ViewImpl view : this.openViews) {
			view.update(openCDOView());
		}

		for (TransactionImpl transaction : this.openTransactions) {
			transaction.update(openCDOTransaction());
		}
	}

	/** Shuts down the CDO client */
	@Override
	public synchronized void shutdown() {
		if (!active) {
			return;
		}
		session.removeListener(this);
		this.active = false;
		closeOpenViews();
		LifecycleUtil.deactivate(this.session);
		LifecycleUtil.deactivate(this.connector);
		LifecycleUtil.deactivate(this.container);
	}

	/** Closes all open views and transactions */
	private void closeOpenViews() {
		for (ViewImpl view : this.openViews) {
			view.close();
		}

		for (TransactionImpl transaction : this.openTransactions) {
			transaction.close();
		}
	}

	/** Brings up the CDO container and opens a session */
	private void startPersistency() {
		OMPlatform.INSTANCE.setDebugging(true);
		OMPlatform.INSTANCE.addLogHandler(PrintLogHandler.CONSOLE);
		OMPlatform.INSTANCE.addTraceHandler(PrintTraceHandler.CONSOLE);
		createContainer();
		createSession();
		installListener();
	}

	/** Creates the CDO container */
	private void createContainer() {
		container = IPluginContainer.INSTANCE;
		Net4jUtil.prepareContainer(container);
		TCPUtil.prepareContainer(container);
	}

	/** Opens the CDO session */
	private void createSession() {

		// Prepare receiveExecutor
		ExecutorService receiveExecutor = ThreadPool.create();

		// Prepare bufferProvider
		IBufferProvider bufferProvider = Net4jUtil.createBufferPool();
		LifecycleUtil.activate(bufferProvider);

		IProtocolProvider protocolProvider = new FactoriesProtocolProvider(new CDOClientProtocolFactory());

		// Prepare selector
		TCPSelector selector = new TCPSelector();
		selector.activate();

		// Prepare connector
		org.eclipse.net4j.internal.tcp.TCPClientConnector connector = new org.eclipse.net4j.internal.tcp.TCPClientConnector();
		connector.getConfig().setBufferProvider(bufferProvider);
		connector.getConfig().setReceiveExecutor(receiveExecutor);
		connector.getConfig().setProtocolProvider(protocolProvider);
		connector.getConfig().setNegotiator(null);
		connector.setSelector(selector);
		connector.setHost(this.hostName); // $NON-NLS-1$
		connector.setPort(this.port);
		connector.activate();

		CDONet4jSessionConfiguration configuration = CDONet4jUtil.createNet4jSessionConfiguration();
		configuration.setConnector(connector);
		configuration.setRepositoryName(this.repositoryName);
		session = configuration.openNet4jSession();
		registerPackages();
		createModelResource();
	}

	/** Creates the main resource if not already present */
	private void createModelResource() {
		CDOTransaction transaction = session.openTransaction();
		transaction.getOrCreateResource(resourceName);
		try {
			transaction.commit();
		} catch (CommitException e) {
			logService.log(LogService.LOG_ERROR, "Could not create resource " + resourceName);
		}
	}

	/** Registers the packages and creates instances from each package */
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
			logService.log(LogService.LOG_ERROR, "Could not commit packages to dummy resource");
		}
		transaction.close();
	}

	/** Insalls this class as session listern */
	private void installListener() {
		session.addListener(this);
	}

	public ITransaction openTransaction(boolean attachCommitListeners, String alterantiveResourceName)
			throws SpecmateException {
		if (!this.active) {
			throw new SpecmateException("Attempt to open transaction when persistency service is not active");
		}
		CDOTransaction cdoTransaction = openCDOTransaction();
		TransactionImpl transaction = new TransactionImpl(this, cdoTransaction, alterantiveResourceName, logService,
				statusService, attachCommitListeners ? attachedCommitListeners : Collections.emptyList());
		this.openTransactions.add(transaction);
		return transaction;
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

	private boolean readConfig(Config config) {
		this.host = config.cdoHost();
		String[] hostport = StringUtils.split(this.host, ":");
		if (!(hostport.length == 2)) {
			return false;
		}
		this.hostName = hostport[0];
		this.port = Integer.parseInt(hostport[1]);
		this.repositoryName = config.cdoRepositoryName();
		this.resourceName = config.cdoResourceName();
		if (StringUtils.isEmpty(this.host) || StringUtils.isEmpty(this.repositoryName)
				|| StringUtils.isEmpty(this.resourceName)) {
			return false;
		}
		logService.log(LogService.LOG_INFO,
				"Configured CDO with [repository=" + this.repositoryName + ", resource=" + this.resourceName + "]");
		return true;
	}

	public boolean isActive() {
		return this.active;
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

	public void removeModelProvider(IPackageProvider provider) {
	}

	@Reference(cardinality = ReferenceCardinality.MULTIPLE, policy = ReferencePolicy.DYNAMIC)
	public void addChangeListener(IChangeListener listener) {
		attachedCommitListeners.add(listener);
	}

	public void removeChangeListener(IChangeListener listener) {
		attachedCommitListeners.remove(listener);
	}

	@Reference(cardinality = ReferenceCardinality.OPTIONAL)
	public void setStatusService(IStatusService statusService) {
		this.statusService = statusService;
	}

	@Reference
	public void setLogService(LogService logService) {
		this.logService = logService;
	}

}
