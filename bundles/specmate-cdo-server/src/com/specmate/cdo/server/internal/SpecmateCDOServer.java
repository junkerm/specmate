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

@Component(immediate = true, configurationPid = SpecmateCDOServerConfig.PID, configurationPolicy = ConfigurationPolicy.REQUIRE)
public class SpecmateCDOServer {

	private IAcceptor acceptorTCP;
	private String repository;
	private String jdbcConnection;
	private IPluginContainer container;
	private IRepository theRepository;
	private LogService logService;

	@Activate
	private void startServer(Map<String, Object> properties) throws SpecmateException {
		if (!readConfig(properties)) {
			logService.log(LogService.LOG_ERROR, "Invalid configuration of cdo persistency. Fall back to defaults.");
			throw new SpecmateException("Invalid configuration of cdo persistency. Fall back to defaults.");
		}
		createContainer();
		createServer();
	}

	private boolean readConfig(Map<String, Object> properties) {
		this.repository = (String) properties.get(SpecmateCDOServerConfig.KEY_REPOSITORY);
		this.jdbcConnection = (String) properties.get(SpecmateCDOServerConfig.KEY_JDBC_CONNECTION);
		return (this.repository != null && this.jdbcConnection != null);
	}

	@Deactivate
	public synchronized void shutdown() {
		LifecycleUtil.deactivate(acceptorTCP);
		LifecycleUtil.deactivate(theRepository);
	}

	private void createContainer() {
		container = IPluginContainer.INSTANCE;
		OMPlatform.INSTANCE.setDebugging(true);
		OMPlatform.INSTANCE.addLogHandler(PrintLogHandler.CONSOLE);
		OMPlatform.INSTANCE.addTraceHandler(PrintTraceHandler.CONSOLE);
		Net4jUtil.prepareContainer(container);
		TCPUtil.prepareContainer(container);
		CDONet4jServerUtil.prepareContainer(container);
	}

	private void createServer() {
		IStore store = createStore();
		createRepository(store);
		createAcceptors();
	}

	private void createAcceptors() {
		this.acceptorTCP = (IAcceptor) IPluginContainer.INSTANCE.getElement("org.eclipse.net4j.acceptors", "tcp",
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
		IDBConnectionProvider dbConnectionProvider = dbAdapter.createConnectionProvider(dataSource);
		IStore store = CDODBUtil.createStore(mappingStrategy, dbAdapter, dbConnectionProvider);
		return store;
	}

	@Reference
	public void setLogService(LogService logService) {
		this.logService = logService;
	}

}
