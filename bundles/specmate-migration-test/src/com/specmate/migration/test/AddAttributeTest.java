package com.specmate.migration.test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Dictionary;
import java.util.Hashtable;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.service.cm.ConfigurationAdmin;
import org.osgi.util.tracker.ServiceTracker;

import com.specmate.common.OSGiUtil;
import com.specmate.dummydata.api.IDataService;
import com.specmate.migration.api.IMigratorService;
import com.specmate.migration.test.support.AttributeAddedModelProviderImpl;
import com.specmate.persistency.IPersistencyService;
import com.specmate.persistency.cdo.internal.config.CDOPersistenceConfig;


public class AddAttributeTest {
	private static BundleContext context;
	private static IDataService dataService;
	
	@BeforeClass
	public static void init() throws Exception {
		context = FrameworkUtil.getBundle(AddAttributeTest.class).getBundleContext();
		
		/*ConfigurationAdmin ca = getConfigurationAdmin();
		Dictionary<String, Object> properties = new Hashtable<>();
		properties.put(CDOPersistenceConfig.KEY_JDBC_CONNECTION, "jdbc:h2:mem:testdb");
		properties.put(CDOPersistenceConfig.KEY_REPOSITORY_NAME, "testrepo");
		properties.put(CDOPersistenceConfig.KEY_RESOURCE_NAME, "r1");
		properties.put(CDOPersistenceConfig.KEY_USER_RESOURCE_NAME, "r2");
		OSGiUtil.configureService(ca, CDOPersistenceConfig.PID, properties);*/
		
		// Need to explicitly load persistency service since we are in a test
		IPersistencyService ps = getPersistencyService();
	}
	
	@Test 
	public void testNeedsMigration() throws Exception {
		IMigratorService migratorService = getMigratorService();
		assertFalse(migratorService.needsMigration());
		migratorService.setModelProviderService(new AttributeAddedModelProviderImpl());
		assertTrue(migratorService.needsMigration());
	}
	
	private static ConfigurationAdmin getConfigurationAdmin() throws InterruptedException {
		ServiceTracker<ConfigurationAdmin, ConfigurationAdmin> configurationAdminService =
				new ServiceTracker<>(context, ConfigurationAdmin.class.getName(), null);
		
		configurationAdminService.open();
		ConfigurationAdmin configurationAdmin = configurationAdminService.waitForService(10000);
		Assert.assertNotNull(configurationAdmin);
		return configurationAdmin;
	}
	
	private static IPersistencyService getPersistencyService() throws InterruptedException {
		ServiceTracker<IPersistencyService, IPersistencyService> persistencyServiceTracker = 
				new ServiceTracker<>(context, IPersistencyService.class.getName(), null);
		
		persistencyServiceTracker.open();
		IPersistencyService persistencyService = persistencyServiceTracker.waitForService(10000);
		Assert.assertNotNull(persistencyService);
		return persistencyService;
	}
	
	private static IMigratorService getMigratorService() throws InterruptedException {
		ServiceTracker<IMigratorService, IMigratorService> migratorServiceTracker =
				new ServiceTracker<>(context, IMigratorService.class.getName(), null);
		
		migratorServiceTracker.open();
		IMigratorService migratorService = migratorServiceTracker.waitForService(10000);
		Assert.assertNotNull(migratorService);
		return migratorService;
	}
	
}
