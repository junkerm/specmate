package com.specmate.cdoserver.internal;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.emf.cdo.server.CDOServerUtil;
import org.eclipse.emf.cdo.server.IRepository;
import org.eclipse.emf.cdo.server.net4j.CDONet4jServerUtil;
import org.eclipse.net4j.Net4jUtil;
import org.eclipse.net4j.acceptor.IAcceptor;
import org.eclipse.net4j.tcp.TCPUtil;
import org.eclipse.net4j.util.container.IPluginContainer;
import org.eclipse.net4j.util.lifecycle.LifecycleUtil;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

import com.specmate.common.SpecmateException;
import com.specmate.dbprovider.api.DBConfigChangedCallback;
import com.specmate.dbprovider.api.IDBProvider;
import com.specmate.migration.api.IMigratorService;

@Component(immediate = true/* , configurationPid = SpecmateCDOServerConfig.PID */)
public class SpecmateCDOServer implements DBConfigChangedCallback {

	private static final String LOCAL_HOST_PORT = "localhost:2036";
	private IAcceptor acceptorTCP;
	private IRepository repository;
	private IPluginContainer container;

	private IDBProvider dbProviderService;
	private IMigratorService migrationService;

	@Activate
	public void activate() throws SpecmateException {
		start();
	}

	@Deactivate
	public void deactivate() {

	}

	private void start() throws SpecmateException {
		if (migrationService.needsMigration()) {
			migrationService.doMigration();
		}
		createServer();
	}

	private void shutdown() {
		LifecycleUtil.deactivate(acceptorTCP);
		LifecycleUtil.deactivate(repository);
	}

	private void createServer() throws SpecmateException {
		createContainer();
		createRepository();
		createAcceptors();
	}

	private void createContainer() {
		this.container = IPluginContainer.INSTANCE;
		Net4jUtil.prepareContainer(container);
		TCPUtil.prepareContainer(container);
		CDONet4jServerUtil.prepareContainer(container);
	}

	private void createRepository() throws SpecmateException {
		Map<String, String> props = new HashMap<>();
		props.put(IRepository.Props.OVERRIDE_UUID, "specmate");
		props.put(IRepository.Props.SUPPORTING_AUDITS, "true");
		props.put(IRepository.Props.SUPPORTING_BRANCHES, "true");
		this.repository = CDOServerUtil.createRepository(dbProviderService.getRepository(),
				dbProviderService.createStore(), props);
		CDOServerUtil.addRepository(IPluginContainer.INSTANCE, repository);
	}

	private void createAcceptors() {
		this.acceptorTCP = (IAcceptor) IPluginContainer.INSTANCE.getElement("org.eclipse.net4j.acceptors", "tcp",
				LOCAL_HOST_PORT);
	}

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
