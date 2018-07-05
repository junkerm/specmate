package com.specmate.test.integration;

import java.util.Dictionary;
import java.util.Hashtable;

import org.junit.Assert;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.service.cm.ConfigurationAdmin;
import org.osgi.util.tracker.ServiceTracker;

import com.specmate.common.OSGiUtil;
import com.specmate.common.SpecmateException;
import com.specmate.persistency.IPersistencyService;
import com.specmate.persistency.ITransaction;
import com.specmate.persistency.cdo.config.CDOPersistenceConfig;

public class IntegrationTestBase {

	// JUnits creates a new object for every test. Making these fields static
	// avoids that
	// the services are created over and over again.
	static IPersistencyService persistency;
	static BundleContext context;

	public IntegrationTestBase() throws Exception {
		if (context == null) {
			context = FrameworkUtil.getBundle(EmfRestTest.class).getBundleContext();
		}
		if (persistency == null) {
			configurePersistency(getPersistencyProperties());
		}

		clearPersistency();

	}

	protected void configurePersistency(Dictionary<String, Object> properties) throws Exception {
		ConfigurationAdmin configAdmin = getConfigAdmin();
		OSGiUtil.configureService(configAdmin, CDOPersistenceConfig.PID, properties);

		// Alow time for the persistency to be started
		Thread.sleep(2000);

		persistency = getPersistencyService();
	}

	protected void clearPersistency() throws SpecmateException {
		ITransaction transaction = persistency.openTransaction();
		transaction.getResource().getContents().clear();
		transaction.commit();
		try {
			Thread.sleep(200);
		} catch (InterruptedException e) {

		}
	}

	protected Dictionary<String, Object> getPersistencyProperties() {
		Dictionary<String, Object> properties = new Hashtable<>();
		properties.put(CDOPersistenceConfig.KEY_REPOSITORY_NAME, "specmate");
		properties.put(CDOPersistenceConfig.KEY_RESOURCE_NAME, "specmateResource");
		properties.put(CDOPersistenceConfig.KEY_USER_RESOURCE_NAME, "userResource");
		return properties;
	}

	protected ConfigurationAdmin getConfigAdmin() throws SpecmateException {
		ServiceTracker<ConfigurationAdmin, ConfigurationAdmin> configAdminTracker = new ServiceTracker<>(context,
				ConfigurationAdmin.class.getName(), null);
		configAdminTracker.open();
		ConfigurationAdmin configAdmin;
		try {
			configAdmin = configAdminTracker.waitForService(10000);
		} catch (InterruptedException e) {
			throw new SpecmateException(e);
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
			persistency = persistencyTracker.waitForService(10000);
		} catch (InterruptedException e) {
			throw new SpecmateException(e);
		}
		Assert.assertNotNull(persistency);
		return persistency;
	}

}
