package com.specmate.test.integration;

import java.util.Dictionary;
import java.util.Hashtable;

import org.junit.Assert;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.service.cm.ConfigurationAdmin;
import org.osgi.util.tracker.ServiceTracker;

import com.specmate.cdoserver.config.SpecmateCDOServerConfig;
import com.specmate.common.OSGiUtil;
import com.specmate.common.SpecmateException;
import com.specmate.persistency.IPersistencyService;
import com.specmate.persistency.ITransaction;
import com.specmate.persistency.cdo.internal.CDOPersistencyServiceConfig;

import specmate.dbprovider.h2.config.H2ProviderConfig;

public class IntegrationTestBase {

	private static final String SPECMATE_RESOURCE = "specmate_resource";
	private static final String SPECMATE_REPOSITORY = "specmate_repository";
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
			configureDBProvider(getDBProviderProperites());
			configureCDOServer(getCDOServerProperties());
			configurePersistency(getPersistencyProperties());
		}

		clearPersistency();

	}

	private void configureCDOServer(Dictionary<String, Object> cdoServerProperties) throws Exception {
		ConfigurationAdmin configAdmin = getConfigAdmin();
		OSGiUtil.configureService(configAdmin, SpecmateCDOServerConfig.PID, cdoServerProperties);

		// Alow time for the persistency to be started
		Thread.sleep(2000);

	}

	private Dictionary<String, Object> getCDOServerProperties() {
		Dictionary<String, Object> properties = new Hashtable<>();
		properties.put(SpecmateCDOServerConfig.KEY_SERVER_PORT, "2036");
		properties.put(SpecmateCDOServerConfig.KEY_REPOSITORY_NAME, SPECMATE_REPOSITORY);

		return properties;
	}

	protected void configureDBProvider(Dictionary<String, Object> properties) throws Exception {
		ConfigurationAdmin configAdmin = getConfigAdmin();
		OSGiUtil.configureService(configAdmin, H2ProviderConfig.PID, properties);

		// Alow time for the persistency to be started
		Thread.sleep(2000);
	}

	protected void configurePersistency(Dictionary<String, Object> properties) throws Exception {
		ConfigurationAdmin configAdmin = getConfigAdmin();
		OSGiUtil.configureService(configAdmin, CDOPersistencyServiceConfig.PID, properties);

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
		properties.put(CDOPersistencyServiceConfig.KEY_HOST, "localhost:2036");
		properties.put(CDOPersistencyServiceConfig.KEY_REPOSITORY_NAME, SPECMATE_REPOSITORY);
		properties.put(CDOPersistencyServiceConfig.KEY_RESOURCE_NAME, SPECMATE_RESOURCE);

		return properties;
	}

	protected Dictionary<String, Object> getDBProviderProperites() {
		Dictionary<String, Object> properties = new Hashtable<>();
		properties.put(H2ProviderConfig.KEY_JDBC_CONNECTION, "jdbc:h2:mem:specmate;DB_CLOSE_DELAY=-1");
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
