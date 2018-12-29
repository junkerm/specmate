package com.specmate.cdoserver.internal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.cdo.common.CDOCommonRepository.State;
import org.eclipse.emf.cdo.common.revision.CDORevisionCache;
import org.eclipse.emf.cdo.common.revision.CDORevisionUtil;
import org.eclipse.emf.cdo.common.util.RepositoryStateChangedEvent;
import org.eclipse.emf.cdo.eresource.EresourcePackage;
import org.eclipse.emf.cdo.etypes.EtypesPackage;
import org.eclipse.emf.cdo.net4j.CDONet4jSessionConfiguration;
import org.eclipse.emf.cdo.net4j.CDONet4jUtil;
import org.eclipse.emf.cdo.net4j.ReconnectingCDOSessionConfiguration;
import org.eclipse.emf.cdo.server.CDOServerUtil;
import org.eclipse.emf.cdo.server.IRepository;
import org.eclipse.emf.cdo.server.IRepositorySynchronizer;
import org.eclipse.emf.cdo.server.IStore;
import org.eclipse.emf.cdo.server.net4j.CDONet4jServerUtil;
import org.eclipse.emf.cdo.session.CDOSession;
import org.eclipse.emf.cdo.session.CDOSessionConfiguration.SessionOpenedEvent;
import org.eclipse.emf.cdo.session.CDOSessionConfigurationFactory;
import org.eclipse.emf.cdo.spi.server.InternalRepository;
import org.eclipse.emf.cdo.spi.server.InternalSessionManager;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.net4j.Net4jUtil;
import org.eclipse.net4j.acceptor.IAcceptor;
import org.eclipse.net4j.connector.IConnector;
import org.eclipse.net4j.tcp.TCPUtil;
import org.eclipse.net4j.util.StringUtil;
import org.eclipse.net4j.util.container.IPluginContainer;
import org.eclipse.net4j.util.event.IEvent;
import org.eclipse.net4j.util.event.IListener;
import org.eclipse.net4j.util.lifecycle.ILifecycle;
import org.eclipse.net4j.util.lifecycle.LifecycleEventAdapter;
import org.eclipse.net4j.util.lifecycle.LifecycleUtil;
import org.eclipse.net4j.util.om.OMPlatform;
import org.eclipse.net4j.util.om.trace.PrintTraceHandler;
import org.eclipse.net4j.util.security.IAuthenticator;
import org.eclipse.net4j.util.security.PasswordCredentialsProvider;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.log.LogService;

import com.specmate.cdoserver.ICDOServer;
import com.specmate.cdoserver.config.SpecmateCDOServerConfig;
import com.specmate.common.SpecmateException;
import com.specmate.common.SpecmateValidationException;
import com.specmate.dbprovider.api.DBConfigChangedCallback;
import com.specmate.dbprovider.api.IDBProvider;
import com.specmate.migration.api.IMigratorService;
import com.specmate.persistency.IPackageProvider;

@Component(immediate = true, configurationPid = SpecmateCDOServerConfig.PID, configurationPolicy = ConfigurationPolicy.REQUIRE)
public class SpecmateCDOServer implements DBConfigChangedCallback, ICDOServer {

	/** The configured tcp port */
	private String hostAndPort;

	/** The tcp acceptor */
	private IAcceptor acceptorTCP;

	/** The CDO repository */
	private InternalRepository repository;

	/** The CDO container */
	private IPluginContainer container;

	/** Reference to the DB provider service */
	private IDBProvider dbProviderService;

	/** Reference to the migration service */
	private IMigratorService migrationService;

	/** The name of the repository */
	private String repositoryName;

	/** User name for the cdo server */
	private String cdoUser;

	/** Password for the cdo server */
	private String cdoPassword;

	protected String masterHostAndPort;

	private LogService logService;

	private String masterRepositoryNme;

	private String masterUser;

	private String masterPassword;

	private IPackageProvider packageProvider;

	private boolean isClone;

	private boolean active = false;

	@Activate
	public void activate(Map<String, Object> properties) throws SpecmateValidationException, SpecmateException {
		readConfig(properties);
		start();
	}

	@Deactivate
	public void deactivate() {
		shutdown();
	}

	/**
	 * Reads the config properties
	 *
	 * @param properties
	 * @throws SpecmateValidationException
	 *             if the configuration is invalid
	 */
	private void readConfig(Map<String, Object> properties) throws SpecmateValidationException {
		this.hostAndPort = (String) properties.get(SpecmateCDOServerConfig.KEY_SERVER_HOST_PORT);
		if (StringUtil.isEmpty(this.hostAndPort)) {
			throw new SpecmateValidationException("No server host and port given");
		}

		this.repositoryName = (String) properties.get(SpecmateCDOServerConfig.KEY_REPOSITORY_NAME);
		if (StringUtil.isEmpty(this.repositoryName)) {
			throw new SpecmateValidationException("No repository name given");
		}

		this.cdoUser = (String) properties.get(SpecmateCDOServerConfig.KEY_CDO_USER);
		if (StringUtil.isEmpty(this.cdoUser)) {
			throw new SpecmateValidationException("No CDO user name given");
		}

		this.cdoPassword = (String) properties.get(SpecmateCDOServerConfig.KEY_CDO_PASSWORD);
		if (StringUtil.isEmpty(this.cdoPassword)) {
			throw new SpecmateValidationException("No CDO password given");
		}

		this.masterHostAndPort = (String) properties.get(SpecmateCDOServerConfig.KEY_CDO_MASTER);
		if (!StringUtil.isEmpty(this.masterHostAndPort)) {
			this.isClone = true;
			this.masterRepositoryNme = (String) properties.get(SpecmateCDOServerConfig.KEY_CDO_MASTER_REPOSITORY);
			if (StringUtil.isEmpty(this.masterRepositoryNme)) {
				throw new SpecmateValidationException(
						"Server should be configured as clone, but no master repository is given.");
			}

			this.masterUser = (String) properties.get(SpecmateCDOServerConfig.KEY_CDO_MASTER_USER);
			if (StringUtil.isEmpty(this.masterUser)) {
				throw new SpecmateValidationException(
						"Server should be configured as clone, but no master user is given.");
			}

			this.masterPassword = (String) properties.get(SpecmateCDOServerConfig.KEY_CDO_MASTER_PASSWORD);
			if (StringUtil.isEmpty(this.masterPassword)) {
				throw new SpecmateValidationException(
						"Server should be configured as clone, but no master password is given.");
			}
		}
	}

	/**
	 * Starts the CDO server, performs the migration if necessary.
	 */
	@Override
	public void start() throws SpecmateException {
		if (active) {
			return;
		}
		if (migrationService.needsMigration()) {
			migrationService.doMigration();
		}
		createServer();
		active = true;
	}

	/** Shuts the server down */
	@Override
	public void shutdown() {
		if (!active) {
			return;
		}
		LifecycleUtil.deactivate(acceptorTCP);
		LifecycleUtil.deactivate(repository);
		active = false;
	}

	/** Creates the server instance */
	private void createServer() throws SpecmateException {
		createContainer();
		createRepository();
		if (!isClone) {
			createAcceptors();
		}
	}

	/** Creates and prepares the container */
	private void createContainer() {
		this.container = IPluginContainer.INSTANCE;
		OMPlatform.INSTANCE.setDebugging(true);
		OMPlatform.INSTANCE.addTraceHandler(PrintTraceHandler.CONSOLE);
		Net4jUtil.prepareContainer(container);
		TCPUtil.prepareContainer(container);
		CDONet4jServerUtil.prepareContainer(container);
	}

	/** Create a CDO repository */
	private void createRepository() throws SpecmateException {
		Map<String, String> props = new HashMap<>();
		props.put(IRepository.Props.OVERRIDE_UUID, this.repositoryName);
		props.put(IRepository.Props.SUPPORTING_AUDITS, "true");
		props.put(IRepository.Props.SUPPORTING_BRANCHES, "true");
		props.put(IRepository.Props.ID_GENERATION_LOCATION, "CLIENT");

		IStore store = dbProviderService.createStore();

		if (!StringUtil.isEmpty(this.masterHostAndPort)) {
			if (StringUtil.isEmpty(this.masterRepositoryNme)) {
				throw new SpecmateException("Should be configured as clone bu not master repository name is given.");
			}

			logService.log(LogService.LOG_INFO, "Configuring as clone of " + this.masterHostAndPort);
			IRepositorySynchronizer synchronizer = createRepositorySynchronizer(this.masterHostAndPort,
					this.masterRepositoryNme);
			this.repository = (InternalRepository) CDOServerUtil.createOfflineClone(this.repositoryName, store, props,
					synchronizer);
		} else {
			logService.log(LogService.LOG_INFO, "Configuring as master");
			this.repository = (InternalRepository) CDOServerUtil.createRepository(this.repositoryName, store, props);
		}

		InternalSessionManager sessionManager = (InternalSessionManager) CDOServerUtil.createSessionManager();
		sessionManager.setAuthenticator(new IAuthenticator() {
			@Override
			public void authenticate(String userID, char[] password) throws SecurityException {
				if (!cdoUser.equals(userID) || !cdoPassword.equals(new String(password))) {
					throw new SecurityException();
				}
			}
		});
		repository.setSessionManager(sessionManager);
		if (isClone) {
			List<EPackage> packages = new ArrayList<>();
			packages.addAll(packageProvider.getPackages());
			packages.add(EcorePackage.eINSTANCE);
			packages.add(EresourcePackage.eINSTANCE);
			packages.add(EtypesPackage.eINSTANCE);
			repository.setInitialPackages(packages.toArray(new EPackage[0]));
			repository.addListener(new IListener() {
				@Override
				public void notifyEvent(IEvent event) {
					if (event instanceof RepositoryStateChangedEvent) {
						RepositoryStateChangedEvent rsce = (RepositoryStateChangedEvent) event;
						State newState = rsce.getNewState();
						if (newState == State.OFFLINE || newState == State.ONLINE) {
							createAcceptors();
						}
					}

				}
			});
		}
		CDOServerUtil.addRepository(IPluginContainer.INSTANCE, repository);
	}

	/**
	 * Creates a repository synchronizer which connects to the master repository to
	 * synchronize between master and client..
	 */
	protected IRepositorySynchronizer createRepositorySynchronizer(String connectorDescription, String repositoryName) {
		CDOSessionConfigurationFactory factory = createSessionConfigurationFactory(connectorDescription,
				repositoryName);

		IRepositorySynchronizer synchronizer = CDOServerUtil.createRepositorySynchronizer(factory);
		synchronizer.setRetryInterval(2);
		synchronizer.setMaxRecommits(10);
		synchronizer.setRecommitInterval(2);

		return synchronizer;
	}

	/**
	 * creates a CDOSessionConfigurationFactory for the offline clone. It
	 * instantiates a connection to the master repository.
	 */
	protected CDOSessionConfigurationFactory createSessionConfigurationFactory(final String connectorDescription,
			final String repositoryName) {
		return new CDOSessionConfigurationFactory() {
			@Override
			public CDONet4jSessionConfiguration createSessionConfiguration() {
//				IConnector connector = createConnector(SpecmateCDOServer.this.masterHostAndPort);
//				connector.setOpenChannelTimeout(6000000);
				return SpecmateCDOServer.this.createSessionConfiguration(repositoryName);
			}
		};
	}

	protected CDONet4jSessionConfiguration createSessionConfiguration(String repositoryName) {
		 ReconnectingCDOSessionConfiguration configuration = CDONet4jUtil.createReconnectingSessionConfiguration(this.masterHostAndPort, repositoryName, container);
//		configuration.setConnector(connector);
//		configuration.setRepositoryName(repositoryName);
		configuration.setHeartBeatEnabled(false);
		configuration.setHeartBeatPeriod(5000);
		configuration.setHeartBeatTimeout(10000);
		configuration.setConnectorTimeout(10000);
		configuration.setReconnectInterval(2000);
		configuration.setMaxReconnectAttempts(10);
		configuration.setRevisionManager(CDORevisionUtil.createRevisionManager(CDORevisionCache.NOOP));
		configuration.setCredentialsProvider(new PasswordCredentialsProvider(this.masterUser, this.masterPassword));
		configuration.addListener(new IListener() {
			@Override
			public void notifyEvent(IEvent event) {
				if (event instanceof SessionOpenedEvent) {
					SessionOpenedEvent e = (SessionOpenedEvent) event;
					CDOSession session = e.getOpenedSession();
					logService.log(LogService.LOG_INFO, "Opened master session " + session);

					session.addListener(new LifecycleEventAdapter() {
						@Override
						protected void onAboutToDeactivate(ILifecycle lifecycle) {
							logService.log(LogService.LOG_INFO, "Closing master session " + lifecycle);
						}
					});
				}
			}
		});

		return configuration;
	}

	/** Creates the TCP acceptor */
	private void createAcceptors() {
		logService.log(LogService.LOG_INFO, "Starting server on " + this.hostAndPort);
		this.acceptorTCP = (IAcceptor) IPluginContainer.INSTANCE.getElement("org.eclipse.net4j.acceptors", "tcp",
				hostAndPort);
		logService.log(LogService.LOG_INFO, "Server started");
	}

	/**
	 * Called by the DB provider when its configuration changes. Triggers a restart
	 * of the server.
	 */
	@Override
	public void configurationChanged() throws SpecmateException {
		shutdown();
		start();
	}

	@Reference
	public void setDBProviderService(IDBProvider dbProviderService) {
		this.dbProviderService = dbProviderService;
		dbProviderService.registerDBConfigChangedCallback(this);
	}

	public void unsetDBProviderService(IDBProvider dbProviderService) {
		dbProviderService.unregisterDBConfigChangedCallback(this);
	}

	@Reference
	public void setMigrationService(IMigratorService migrationService) {
		this.migrationService = migrationService;
	}

	@Reference
	public void setLogService(LogService logService) {
		this.logService = logService;
	}

	@Reference
	public void setModelProvider(IPackageProvider provider) {
		this.packageProvider = provider;
	}
}
