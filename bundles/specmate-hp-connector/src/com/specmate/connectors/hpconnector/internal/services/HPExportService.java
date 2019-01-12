package com.specmate.connectors.hpconnector.internal.services;

import java.util.Map;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;

import com.specmate.common.exception.SpecmateException;
import com.specmate.connectors.api.IExportService;
import com.specmate.connectors.hpconnector.internal.config.HPServerProxyConfig;
import com.specmate.connectors.hpconnector.internal.util.HPProxyConnection;
import com.specmate.model.testspecification.TestProcedure;

@Component(service = IExportService.class, configurationPid = HPServerProxyConfig.EXPORTER_PID, configurationPolicy = ConfigurationPolicy.REQUIRE)
public class HPExportService implements IExportService {

	private HPProxyConnection hpConnection;

	@Activate
	public void activate(Map<String, Object> properties) throws SpecmateException {
		String host = (String) properties.get(HPServerProxyConfig.KEY_HOST);
		String port = (String) properties.get(HPServerProxyConfig.KEY_PORT);
		int timeout = Integer.parseInt((String) properties.get(HPServerProxyConfig.KEY_TIMEOUT));
		this.hpConnection = new HPProxyConnection(host, port, timeout);
	}

	@Override
	public void export(TestProcedure testProcedure) throws SpecmateException {
		hpConnection.exportTestProcedure(testProcedure);
	}

	@Override
	public boolean isAuthorizedToExport(String username, String password) {
		return hpConnection.authenticateExport(username, password);
	}

}
