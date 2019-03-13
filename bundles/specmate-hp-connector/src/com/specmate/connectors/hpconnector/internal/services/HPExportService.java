package com.specmate.connectors.hpconnector.internal.services;

import java.util.Map;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.log.LogService;

import com.specmate.common.exception.SpecmateException;
import com.specmate.connectors.api.IExportService;
import com.specmate.connectors.hpconnector.internal.config.HPServerProxyConfig;
import com.specmate.connectors.hpconnector.internal.util.HPProxyConnection;
import com.specmate.model.testspecification.TestProcedure;

@Component(service = IExportService.class, configurationPid = HPServerProxyConfig.EXPORTER_PID, configurationPolicy = ConfigurationPolicy.REQUIRE)
public class HPExportService implements IExportService {

	private HPProxyConnection hpConnection;
	private LogService logService;

	@Activate
	public void activate(Map<String, Object> properties) throws SpecmateException {
		String host = (String) properties.get(HPServerProxyConfig.KEY_HOST);
		String port = (String) properties.get(HPServerProxyConfig.KEY_PORT);
		int timeout = Integer.parseInt((String) properties.get(HPServerProxyConfig.KEY_TIMEOUT));
		this.hpConnection = new HPProxyConnection(host, port, timeout, this.logService);
	}

	@Override
	public void export(TestProcedure testProcedure) throws SpecmateException {
		this.hpConnection.exportTestProcedure(testProcedure);
	}

	@Override
	public boolean isAuthorizedToExport(String username, String password) {
		return this.hpConnection.authenticateExport(username, password);
	}

	@Reference
	public void setLogService(LogService logService) {
		this.logService = logService;
	}

}
