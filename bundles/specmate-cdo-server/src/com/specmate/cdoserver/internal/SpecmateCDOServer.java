package com.specmate.cdoserver.internal;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.emf.cdo.server.CDOServerUtil;
import org.eclipse.emf.cdo.server.IRepository;
import org.eclipse.emf.cdo.server.net4j.CDONet4jServerUtil;
import org.eclipse.emf.cdo.spi.server.InternalRepository;
import org.eclipse.emf.cdo.spi.server.InternalSessionManager;
import org.eclipse.net4j.Net4jUtil;
import org.eclipse.net4j.acceptor.IAcceptor;
import org.eclipse.net4j.tcp.TCPUtil;
import org.eclipse.net4j.util.StringUtil;
import org.eclipse.net4j.util.container.IPluginContainer;
import org.eclipse.net4j.util.lifecycle.LifecycleUtil;
import org.eclipse.net4j.util.security.IAuthenticator;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

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

	private String repositoryName;

	private String cdoUser;

	private String cdoPassword;

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

		this.repository = (InternalRepository) CDOServerUtil.createRepository(this.repositoryName,
				dbProviderService.createStore(), props);

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

	/** Creates the TCP acceptor */
	private void createAcceptors() {
		this.acceptorTCP = (IAcceptor) IPluginContainer.INSTANCE.getElement("org.eclipse.net4j.acceptors", "tcp",
				"0.0.0.0:" + port);
	}

	/**
	 * Called by the DB provider when its configuration changes. Triggers a
	 * restart of the server.
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
}
