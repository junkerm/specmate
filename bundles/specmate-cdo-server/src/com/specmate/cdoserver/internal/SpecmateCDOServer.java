package com.specmate.cdoserver.internal;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.emf.cdo.common.revision.CDORevisionCache;
import org.eclipse.emf.cdo.common.revision.CDORevisionUtil;
import org.eclipse.emf.cdo.net4j.CDONet4jSessionConfiguration;
import org.eclipse.emf.cdo.net4j.CDONet4jUtil;
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
import org.eclipse.net4j.util.security.IAuthenticator;
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

@Component(immediate = true, configurationPid = SpecmateCDOServerConfig.PID, configurationPolicy = ConfigurationPolicy.REQUIRE)
public class SpecmateCDOServer implements DBConfigChangedCallback, ICDOServer {

	/** The configured tcp port */
	private int port;

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
		String portString = (String) properties.get(SpecmateCDOServerConfig.KEY_SERVER_PORT);
		try {
			this.port = Integer.parseInt(portString);
		} catch (Exception e) {
			throw new SpecmateValidationException("Invalid port format: " + portString);
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
			this.masterRepositoryNme = (String) properties.get(SpecmateCDOServerConfig.KEY_CDO_MASTER_REPOSITORY);
			if (StringUtil.isEmpty(this.masterRepositoryNme)) {
				throw new SpecmateValidationException(
						"Server should be configured as clone, but no master repository is given.");
			}
		}
	}

	/**
	 * Starts the CDO server, performs the migration if necessary.
	 */
	@Override
	public void start() throws SpecmateException {
		if (migrationService.needsMigration()) {
			migrationService.doMigration();
		}
		createServer();
	}

	/** Shuts the server down */
	@Override
	public void shutdown() {
		LifecycleUtil.deactivate(acceptorTCP);
		LifecycleUtil.deactivate(repository);
	}

	/** Creates the server instance */
	private void createServer() throws SpecmateException {
		createContainer();
		createRepository();
		createAcceptors();
	}

	/** Creates and prepares the container */
	private void createContainer() {
		this.container = IPluginContainer.INSTANCE;
		Net4jUtil.prepareContainer(container);
		TCPUtil.prepareContainer(container);
		CDONet4jServerUtil.prepareContainer(container);
	}

	/** Create a CDO repository */
	private void createRepository() throws SpecmateException {
		Map<String, String> props = new HashMap<>();
		props.put(IRepository.Props.OVERRIDE_UUID, "specmate");
		props.put(IRepository.Props.SUPPORTING_AUDITS, "true");
		props.put(IRepository.Props.SUPPORTING_BRANCHES, "true");

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
		CDOServerUtil.addRepository(IPluginContainer.INSTANCE, repository);
	}

	/**
	 * Creates a repository synchronizer which connects to the master repository to
	 * synchronize between master and client.
	 */
	protected IRepositorySynchronizer createRepositorySynchronizer(String connectorDescription, String repositoryName) {
		CDOSessionConfigurationFactory factory = createSessionConfigurationFactory(connectorDescription,
				repositoryName);

		IRepositorySynchronizer synchronizer = CDOServerUtil.createRepositorySynchronizer(factory);
		synchronizer.setRetryInterval(2);
		synchronizer.setMaxRecommits(10);
		synchronizer.setRecommitInterval(2);
		// synchronizer.setRawReplication(true);
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
				IConnector connector = createConnector(SpecmateCDOServer.this.masterHostAndPort);
				return SpecmateCDOServer.this.createSessionConfiguration(connector, repositoryName);
			}
		};
	}

	protected CDONet4jSessionConfiguration createSessionConfiguration(IConnector connector, String repositoryName) {
		CDONet4jSessionConfiguration configuration = CDONet4jUtil.createNet4jSessionConfiguration();
		configuration.setConnector(connector);
		configuration.setRepositoryName(repositoryName);
		configuration.setRevisionManager(CDORevisionUtil.createRevisionManager(CDORevisionCache.NOOP));
		configuration.addListener(new IListener() {
			@Override
			public void notifyEvent(IEvent event) {
				if (event instanceof SessionOpenedEvent) {
					SessionOpenedEvent e = (SessionOpenedEvent) event;
					CDOSession session = e.getOpenedSession();
					System.out.println("Opened " + session);

					session.addListener(new LifecycleEventAdapter() {
						@Override
						protected void onAboutToDeactivate(ILifecycle lifecycle) {
							System.out.println("Closing " + lifecycle);
						}
					});
				}
			}
		});

		return configuration;
	}

	protected IConnector createConnector(String description) {
		return Net4jUtil.getConnector(container, "tcp", description);
	}

	/** Creates the TCP acceptor */
	private void createAcceptors() {
		this.acceptorTCP = (IAcceptor) IPluginContainer.INSTANCE.getElement("org.eclipse.net4j.acceptors", "tcp",
				"0.0.0.0:" + port);
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
}
