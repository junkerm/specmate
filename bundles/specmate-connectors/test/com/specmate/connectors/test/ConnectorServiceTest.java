package com.specmate.connectors.test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.mockito.Mockito;
import org.osgi.service.cm.ConfigurationAdmin;
import org.osgi.service.log.LogService;

import com.specmate.common.SpecmateException;
import com.specmate.common.SpecmateValidationException;
import com.specmate.config.api.IConfigService;
import com.specmate.connectors.internal.config.ConnectorServiceConfig;

public class ConnectorServiceTest {

	@Test
	public void testConnectorServiceDisabling()
			throws SpecmateException, SpecmateValidationException, InterruptedException {
		ConnectorServiceConfig connectorConfig = new ConnectorServiceConfig();
		connectorConfig.setLogService(mock(LogService.class));

		IConfigService configServiceMock = mock(IConfigService.class);
		when(configServiceMock.getConfigurationProperty(ConnectorServiceConfig.KEY_POLL_TIME, "20")).thenReturn("-1");
		connectorConfig.setConfigurationService(configServiceMock);

		ConfigurationAdmin configAdminMock = mock(ConfigurationAdmin.class);
		connectorConfig.setConfigurationAdmin(configAdminMock);

		connectorConfig.configureConnectorService();

		verifyZeroInteractions(configAdminMock);
	}

	@Test
	public void testConnectorServiceEnabling()
			throws SpecmateException, SpecmateValidationException, InterruptedException {
		ConnectorServiceConfig connectorConfig = new ConnectorServiceConfig();
		connectorConfig.setLogService(mock(LogService.class));

		IConfigService configServiceMock = mock(IConfigService.class);
		when(configServiceMock.getConfigurationProperty(ConnectorServiceConfig.KEY_POLL_TIME, "20")).thenReturn("20");
		connectorConfig.setConfigurationService(configServiceMock);

		ConfigurationAdmin configAdminMock = mock(ConfigurationAdmin.class);
		connectorConfig.setConfigurationAdmin(configAdminMock);

		connectorConfig.configureConnectorService();

		verify(configAdminMock, Mockito.atLeastOnce());
	}
}
