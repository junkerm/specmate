package com.specmate.cdo.server.internal;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.emf.cdo.server.CDOServerUtil;
import org.eclipse.emf.cdo.server.IRepository;
import org.eclipse.emf.cdo.server.IStore;
import org.eclipse.emf.cdo.server.db.CDODBUtil;
import org.eclipse.emf.cdo.server.db.mapping.IMappingStrategy;
import org.eclipse.emf.cdo.server.net4j.CDONet4jServerUtil;
import org.eclipse.net4j.Net4jUtil;
import org.eclipse.net4j.acceptor.IAcceptor;
import org.eclipse.net4j.db.IDBAdapter;
import org.eclipse.net4j.db.IDBConnectionProvider;
import org.eclipse.net4j.db.h2.H2Adapter;
import org.eclipse.net4j.tcp.TCPUtil;
import org.eclipse.net4j.util.container.IPluginContainer;
import org.eclipse.net4j.util.lifecycle.LifecycleUtil;
import org.eclipse.net4j.util.om.OMPlatform;
import org.eclipse.net4j.util.om.log.EclipseLoggingBridge;
import org.eclipse.net4j.util.om.log.OSGiLoggingBridge;
import org.eclipse.net4j.util.om.log.PrintLogHandler;
import org.eclipse.net4j.util.om.trace.PrintTraceHandler;
import org.h2.jdbcx.JdbcDataSource;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.log.LogService;

import com.specmate.cdo.server.config.SpecmateCDOServerConfig;
import com.specmate.common.SpecmateException;
import com.specmate.migration.api.IMigratorService;

/** Starts a CDO server that accepts connections via TCP */
@Component(immediate = true, configurationPid = SpecmateCDOServerConfig.PID, configurationPolicy = ConfigurationPolicy.REQUIRE)
public class SpecmateCDOServer {

	/** The store should support audits */
	private static final boolean AUDIT_SUPPORT = true;

	/** The store should support branches */
	private static final boolean BRANCH_SUPPORT = true;

	/** The id of the repository */
	private static final String REPOSITORY_ID = "specmate";

	/** The adress to which the server is bound to */
	private static final String LOCALHOST = "0.0.0.0:2036";

	/** The type of the acceptor */
	private static final String ACCEPTOR_TYPE_TCP = "tcp";

	/** Key of the acceptor element in the container */
	private static final String KEY_ACCEPTOR = "org.eclipse.net4j.acceptors";

	/** The repository name */
	private String repositoryName;

	/** The jdbc connection string */
	private String jdbcConnectionString;

	/** The tcp acceptor */
	private IAcceptor acceptorTCP;

	/** The container for the cdo server. */
	private IPluginContainer container;

	/** The actual repository */
	private IRepository theRepository;

	/** The OSGI logging serice */
	private LogService logService;

	/** Reference to the migration service */
	private IMigratorService migrationService;

	@Activate
	public void activate(Map<String, Object> properties) throws SpecmateException {
		if (!readConfig(properties)) {
			logService.log(LogService.LOG_ERROR, "Invalid configuration of cdo server.");
			throw new SpecmateException("Invalid configuration of cdo persistency.");
		}
		if (migrationService.needsMigration()) {
			migrationService.doMigration();
		}
		createContainer();
		createServer();
	}

	@Deactivate
	public void deactivate() {
		logService.log(LogService.LOG_WARNING, "Specmate CDO shutting down");
		LifecycleUtil.deactivate(acceptorTCP);
		LifecycleUtil.deactivate(theRepository);
	}

	/**
	 * Reads the necessary values from the configuration.
	 *
	 * @return true if all necessar config parameters are present
	 */
	private boolean readConfig(Map<String, Object> properties) {
		this.repositoryName = (String) properties.get(SpecmateCDOServerConfig.KEY_REPOSITORY);
		this.jdbcConnectionString = (String) properties.get(SpecmateCDOServerConfig.KEY_JDBC_CONNECTION);
		return (this.repositoryName != null && this.jdbcConnectionString != null);
	}

	/** Prepare the container */
	private void createContainer() {
		OMPlatform.INSTANCE.removeLogHandler(EclipseLoggingBridge.INSTANCE);
		OMPlatform.INSTANCE.removeLogHandler(PrintLogHandler.CONSOLE);
		OMPlatform.INSTANCE.removeTraceHandler(PrintTraceHandler.CONSOLE);
		OMPlatform.INSTANCE.addLogHandler(OSGiLoggingBridge.INSTANCE);

		container = IPluginContainer.INSTANCE;
		Net4jUtil.prepareContainer(container);
		TCPUtil.prepareContainer(container);
		CDONet4jServerUtil.prepareContainer(container);
	}

	/** Create the store and the acceptor */
	private void createServer() {
		IStore store = createStore();
		createRepository(store);
		createAcceptors();
	}

	/** Create the CDO repository. */
	private void createRepository(IStore store) {
		Map<String, String> props = new HashMap<>();
		props.put(IRepository.Props.OVERRIDE_UUID, REPOSITORY_ID);
		props.put(IRepository.Props.SUPPORTING_AUDITS, Boolean.toString(AUDIT_SUPPORT));
		props.put(IRepository.Props.SUPPORTING_BRANCHES, Boolean.toString(BRANCH_SUPPORT));
		theRepository = CDOServerUtil.createRepository(repositoryName, store, props);
		CDOServerUtil.addRepository(IPluginContainer.INSTANCE, theRepository);
	}

	/** Create the TCP Acceptor */
	private void createAcceptors() {
		this.acceptorTCP = (IAcceptor) container.getElement(KEY_ACCEPTOR, ACCEPTOR_TYPE_TCP, LOCALHOST);
	}

	/** Create the DB store */
	private IStore createStore() {
		JdbcDataSource dataSource = new JdbcDataSource();
		dataSource.setURL(this.jdbcConnectionString);
		IMappingStrategy mappingStrategy = CDODBUtil.createHorizontalMappingStrategy(true);
		IDBAdapter dbAdapter = new H2Adapter();
		IDBConnectionProvider dbConnectionProvider = dbAdapter.createConnectionProvider(dataSource);
		IStore store = CDODBUtil.createStore(mappingStrategy, dbAdapter, dbConnectionProvider);
		return store;
	}

	@Reference
	public void setLogService(LogService logService) {
		this.logService = logService;
	}

	@Reference
	public void setMigrationService(IMigratorService migrationService) {
		this.migrationService = migrationService;
	}

}
