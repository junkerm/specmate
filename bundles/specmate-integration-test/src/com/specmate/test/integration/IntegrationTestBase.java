package com.specmate.test.integration;

import java.io.IOException;
import java.util.Dictionary;

import org.junit.Assert;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.service.cm.ConfigurationAdmin;
import org.osgi.util.tracker.ServiceTracker;

import com.specmate.common.OSGiUtil;
import com.specmate.common.exception.SpecmateException;
import com.specmate.common.exception.SpecmateInternalException;
import com.specmate.model.administration.ErrorCode;
import com.specmate.persistency.IChange;
import com.specmate.persistency.IPersistencyService;
import com.specmate.persistency.ITransaction;

import specmate.dbprovider.h2.config.H2ProviderConfig;

public class IntegrationTestBase {

	// JUnits creates a new object for every test. Making these fields static
	// avoids that
	// the services are created over and over again.
	protected static IPersistencyService persistency;
	protected static ConfigurationAdmin configAdmin;
	protected static BundleContext context;
	private static boolean firstTestRun;

	public IntegrationTestBase() throws Exception {
		init();

		// Give all services some time to startup before running the first test
		if (firstTestRun) {
			Thread.sleep(5000);
			firstTestRun = false;
		}
	}

	private void init() throws Exception {
		if (context == null) {
			context = FrameworkUtil.getBundle(EmfRestTest.class).getBundleContext();
		}

		if (configAdmin == null) {
			configAdmin = getConfigAdmin();
		}

		if (persistency == null) {
			persistency = getPersistencyService();
		}

		clearPersistency();
	}

	protected void configureDBProvider(Dictionary<String, Object> properties) throws Exception {
		OSGiUtil.configureService(configAdmin, H2ProviderConfig.PID, properties);

		// Allow time for the database provider to be started
		Thread.sleep(2000);
	}

	protected Dictionary<String, Object> getDBProviderProperties() throws SpecmateException {
		Dictionary<String, Object> properties = null;
		try {
			properties = configAdmin.getConfiguration(H2ProviderConfig.PID).getProperties();
		} catch (IOException e) {
			throw new SpecmateInternalException(ErrorCode.CONFIGURATION,
					"Could not retrieve configuration properties for H2 database provider.", e);
		}

		return properties;
	}

	private void clearPersistency() throws SpecmateException {
		ITransaction transaction = persistency.openTransaction();
		transaction.doAndCommit(new IChange<Object>() {
			@Override
			public Object doChange() throws SpecmateException {
				transaction.getResource().getContents().clear();
				return null;
			}
		});

		transaction.close();

		try {
			Thread.sleep(200);
		} catch (InterruptedException e) {

		}
	}

	private ConfigurationAdmin getConfigAdmin() throws SpecmateException {
		ServiceTracker<ConfigurationAdmin, ConfigurationAdmin> configAdminTracker = new ServiceTracker<>(context,
				ConfigurationAdmin.class.getName(), null);
		configAdminTracker.open();
		ConfigurationAdmin configAdmin;
		try {
			configAdmin = configAdminTracker.waitForService(20000);
		} catch (InterruptedException e) {
			throw new SpecmateInternalException(ErrorCode.CONFIGURATION, e);
		}
		Assert.assertNotNull(configAdmin);
		return configAdmin;
	}

	protected IPersistencyService getPersistencyService() throws SpecmateException {
		ServiceTracker<IPersistencyService, IPersistencyService> persistencyTracker = new ServiceTracker<>(context,
				IPersistencyService.class.getName(), null);
		persistencyTracker.open();
		IPersistencyService persistency;
		try {
			persistency = persistencyTracker.waitForService(20000);
		} catch (InterruptedException e) {
			throw new SpecmateInternalException(ErrorCode.CONFIGURATION, e);
		}
		Assert.assertNotNull(persistency);
		return persistency;
	}
}
