package com.specmate.persistency.cdo.internal;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.emf.cdo.CDOObject;
import org.eclipse.emf.cdo.common.CDOCommonRepository;
import org.eclipse.emf.cdo.common.CDOCommonRepository.State;
import org.eclipse.emf.cdo.common.CDOCommonSession.Options.PassiveUpdateMode;
import org.eclipse.emf.cdo.common.branch.CDOBranch;
import org.eclipse.emf.cdo.common.id.CDOID;
import org.eclipse.emf.cdo.common.revision.CDORevision;
import org.eclipse.emf.cdo.eresource.CDOResource;
import org.eclipse.emf.cdo.net4j.CDONet4jSession;
import org.eclipse.emf.cdo.net4j.CDONet4jSessionConfiguration;
import org.eclipse.emf.cdo.net4j.CDONet4jUtil;
import org.eclipse.emf.cdo.net4j.CDOSessionRecoveryEvent;
import org.eclipse.emf.cdo.server.net4j.CDONet4jServerUtil;
import org.eclipse.emf.cdo.session.CDOSessionInvalidationEvent;
import org.eclipse.emf.cdo.transaction.CDOTransaction;
import org.eclipse.emf.cdo.util.CommitException;
import org.eclipse.emf.cdo.view.CDOAdapterPolicy;
import org.eclipse.emf.cdo.view.CDOView;
import org.eclipse.emf.cdo.view.CDOViewTargetChangedEvent;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.spi.cdo.CDOMergingConflictResolver;
import org.eclipse.emf.spi.cdo.DefaultCDOMerger;
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

	private static final String LAST_BRANCH_ID_FILE_NAME = "lastBranchId";

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

	/** The configured CDO host to connect to */
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

	protected CDOBranch currentOfflineBranch;

	private Object offlineBranchChanging = new Object();

	private String recoveryFolder;

	private boolean wasOffline = true;

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
		this.recoveryFolder = (String) properties.get(CDOPersistencyServiceConfig.KEY_RECOVERY_FOLDER);

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
		if (this.recoveryFolder != null && !Files.isDirectory(Paths.get(this.recoveryFolder))) {
			throw new SpecmateValidationException("Revovery folder " + this.recoveryFolder + " not found");
		}
	}

	@Override
	public synchronized void start() throws SpecmateException {
		startPersistency();
		loadLastBranch();
		mergeIfNecessary();
		updateOpenViews();
		openEventView();
		this.active = true;
	}

	private void mergeIfNecessary() {
		if (currentOfflineBranch != null && currentOfflineBranch.getName().contains("Offline")) {
			if (session.getRepositoryInfo().getState().equals(State.ONLINE)) {
				merge(session, State.ONLINE);
			}
		}
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
		connector = TCPUtil.getConnector(container, this.hostAndPort);

		PasswordCredentialsProvider credentialsProvider = new PasswordCredentialsProvider(this.cdoUser,
				this.cdoPassword);

		CDONet4jSessionConfiguration configuration = CDONet4jUtil.createNet4jSessionConfiguration();
		// configuration.setHeartBeatEnabled(true);
		// configuration.setConnectorTimeout(60000);
		configuration.setSignalTimeout(60000);
		configuration.setCredentialsProvider(credentialsProvider);
		configuration.setConnector(connector);
		configuration.setRepositoryName(this.repositoryName);
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

		session.addListener(new IListener() {

			@Override
			public void notifyEvent(IEvent event) {
				if (event instanceof CDOCommonRepository.StateChangedEvent) {
					CDOCommonRepository.StateChangedEvent e = (CDOCommonRepository.StateChangedEvent) event;
					State newState = e.getNewState();
					logService.log(LogService.LOG_INFO, "State changed to " + newState);
					merge(session, newState);
				}
			}

		});

		registerPackages();
		createModelResource();
	}

	private void merge(final CDONet4jSession session, State newState) {
		if (newState == State.ONLINE && wasOffline) {
			CDOTransaction newTransaction = null;
			try {
				if (this.currentOfflineBranch != null && !this.currentOfflineBranch.getName().equals("MAIN")) {
					newTransaction = session.openTransaction(session.getBranchManager().getMainBranch());
					newTransaction.merge(CDOPersistencyService.this.currentOfflineBranch,
							new DefaultCDOMerger.PerFeature.ManyValued());
					newTransaction.commit();
				}
				setAllTransactionsTo(session.getBranchManager().getMainBranch());
				this.currentOfflineBranch = null;
			} catch (CommitException ex) {
				ex.printStackTrace();
			} finally {
				if (newTransaction != null) {
					newTransaction.close();
				}
				wasOffline = false;
			}
		} else if (newState == State.OFFLINE) {
			wasOffline = true;
		}
	}

	private void setAllTransactionsTo(CDOBranch currentBranch) {
		logService.log(LogService.LOG_INFO, "Switching to branch " + currentBranch.getName());
		persistBranchId(currentBranch);
		for (TransactionImpl transaction : openTransactions) {
			transaction.getInternalTransaction().setBranch(currentBranch);
		}
		for (ViewImpl view : openViews) {
			view.getInternalView().setBranch(currentBranch);
		}
	}

	private void persistBranchId(CDOBranch currentBranch) {
		FileOutputStream fos = null;
		OutputStreamWriter writer = null;
		try {
			fos = new FileOutputStream(getFullBranchIdFilePath(), false);
			writer = new OutputStreamWriter(fos, StandardCharsets.UTF_8);
			writer.write(Integer.toString(currentBranch.getID()));
		} catch (IOException e) {
			logService.log(LogService.LOG_DEBUG, "Could not persist branch id to file " + LAST_BRANCH_ID_FILE_NAME);
		} finally {
			try {
				if (writer != null) {
					writer.close();
				}
				if (fos != null) {
					fos.close();
				}
			} catch (IOException e) {

			}
		}
	}

	private void loadLastBranch() {
		Integer lastBranchId = loadBranchId();
		if (lastBranchId != null) {
			this.currentOfflineBranch = session.getBranchManager().getBranch(lastBranchId);
			logService.log(LogService.LOG_INFO, "Restoring last offline branch " + this.currentOfflineBranch.getName());
			setAllTransactionsTo(currentOfflineBranch);
		}
	}

	private String getFullBranchIdFilePath() {
		String prefix = this.recoveryFolder != null ? recoveryFolder + "/" : "";
		return prefix + LAST_BRANCH_ID_FILE_NAME;
	}

	private Integer loadBranchId() {
		byte[] encoded;
		try {
			encoded = Files.readAllBytes(Paths.get(getFullBranchIdFilePath()));
		} catch (IOException e) {
			return null;
		}
		return new Integer(new String(encoded, StandardCharsets.UTF_8));
	}

	private void createModelResource() {
		String resourceName = this.resourceName;
		CDOTransaction transaction = session.openTransaction();
		transaction.enableDurableLocking();
		transaction.getOrCreateResource(resourceName);
		try {
			if (transaction.isDirty()) {
				transaction.commit();
			}
		} catch (CommitException e) {
			logService.log(LogService.LOG_ERROR, "Could not create resource " + resourceName, e);
		} finally {
			transaction.close();
		}
	}

	private void registerPackages() {
		CDOTransaction transaction = session.openTransaction();
		transaction.enableDurableLocking();
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
			if (transaction.isDirty()) {
				transaction.commit();
			}
		} catch (Exception e) {
			logService.log(LogService.LOG_ERROR, "Could not commit packages to dummy resource", e);
		} finally {
			transaction.close();
		}
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

		synchronized (this.offlineBranchChanging) {
			if (currentOfflineBranch != null) {
				cdoTransaction.setBranch(currentOfflineBranch);
			}
			this.openTransactions.add(transaction);
		}
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

		synchronized (this.offlineBranchChanging) {
			if (currentOfflineBranch != null) {
				cdoView.setBranch(currentOfflineBranch);
			}
			this.openViews.add(view);
		}

		this.transactionGauge.inc();
		return view;
	}

	public void closedView(ViewImpl viewImpl) {
		this.openViews.remove(viewImpl);
		this.transactionGauge.dec();
	}

	/* package */CDOTransaction openCDOTransaction() throws SpecmateException {

		CDOTransaction transaction = session.openTransaction();

		transaction.enableDurableLocking();
		transaction.options().addChangeSubscriptionPolicy(CDOAdapterPolicy.ALL);
		transaction.options().setInvalidationNotificationEnabled(true);
		transaction.options().addConflictResolver(new CDOMergingConflictResolver());
		transaction.options().setCommitInfoTimeout(20000);

		transaction.addListener(new IListener() {

			@Override
			public void notifyEvent(IEvent event) {
				if (event instanceof CDOViewTargetChangedEvent) {
					CDOViewTargetChangedEvent bce = (CDOViewTargetChangedEvent) event;
					CDOBranch branch = bce.getBranchPoint().getBranch();
					handleBranchChange(branch);
				}

			}
		});

		logService.log(LogService.LOG_DEBUG, "Transaction initialized: " + transaction.getViewID());
		return transaction;

	}

	/* package */CDOView openCDOView() throws SpecmateException {
		CDOView view = session.openView();
		view.enableDurableLocking();
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
		view.enableDurableLocking();
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

	private void handleBranchChange(CDOBranch branch) {
		synchronized (this.offlineBranchChanging) {
			String currentOfflineBranchName = "nobranch";
			if (currentOfflineBranch != null) {
				currentOfflineBranchName = currentOfflineBranch.getName();
			}

			if (branch.getName().equals(currentOfflineBranchName)) {
				return;
			}
			if (branch.getName().equals("MAIN")) {
				currentOfflineBranch = null;
				return;
			}
			if (branch.getName().contains("Offline")) {
				logService.log(LogService.LOG_INFO, "New offline branch created: " + branch.getName());
				this.currentOfflineBranch = branch;
				setAllTransactionsTo(this.currentOfflineBranch);
			}
		}
	}
}
