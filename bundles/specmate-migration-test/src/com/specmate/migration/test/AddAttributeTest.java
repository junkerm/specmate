package com.specmate.migration.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Dictionary;
import java.util.Hashtable;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.service.cm.ConfigurationAdmin;
import org.osgi.service.event.EventAdmin;
import org.osgi.service.log.LogService;
import org.osgi.util.tracker.ServiceTracker;

import com.specmate.common.SpecmateException;
import com.specmate.migration.api.IMigratorService;
import com.specmate.migration.test.attributeadded.testmodel.artefact.ArtefactFactory;
import com.specmate.migration.test.attributeadded.testmodel.artefact.Diagram;
import com.specmate.migration.test.attributeadded.testmodel.base.Folder;
import com.specmate.migration.test.baseline.testmodel.base.BaseFactory;
import com.specmate.migration.test.support.AttributeAddedModelProviderImpl;
import com.specmate.migration.test.support.BaselineModelProviderImpl;
import com.specmate.migration.test.support.ServiceController;
import com.specmate.model.support.util.SpecmateEcoreUtil;
import com.specmate.persistency.IPackageProvider;
import com.specmate.persistency.IPersistencyService;
import com.specmate.persistency.ITransaction;
import com.specmate.persistency.cdo.internal.CDOPersistencyService;
import com.specmate.persistency.cdo.internal.config.CDOPersistenceConfig;
import com.specmate.urihandler.IURIFactory;

public class AddAttributeTest {
	private static BundleContext context;
	private static ServiceController<CDOPersistencyService> persistencyServiceController;
	private static ServiceController<BaselineModelProviderImpl> baselineModelController;
	private static ServiceController<AttributeAddedModelProviderImpl> attributeAddedModelController;
	private static ConfigurationAdmin configurationAdmin;
	private static IPersistencyService persistencyService;
	private static IMigratorService migratorService;
	
	@BeforeClass
	public static void init() throws Exception {
		context = FrameworkUtil.getBundle(AddAttributeTest.class).getBundleContext();
		
		configurationAdmin = getConfigurationAdmin();
		Dictionary<String, Object> properties = new Hashtable<>();
		properties.put(CDOPersistenceConfig.KEY_JDBC_CONNECTION, "jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1");
		//properties.put(CDOPersistenceConfig.KEY_JDBC_CONNECTION, "jdbc:h2:./database/specmate");
		properties.put(CDOPersistenceConfig.KEY_REPOSITORY_NAME, "testrepo");
		properties.put(CDOPersistenceConfig.KEY_RESOURCE_NAME, "r1");
		properties.put(CDOPersistenceConfig.KEY_USER_RESOURCE_NAME, "r2");	
		configurationAdmin.getConfiguration(CDOPersistenceConfig.PID).update(properties);
		
		baselineModelController = new ServiceController<>(context);
		baselineModelController.register(IPackageProvider.class, BaselineModelProviderImpl.class, null);
		attributeAddedModelController = new ServiceController<>(context);
		attributeAddedModelController.register(IPackageProvider.class, AttributeAddedModelProviderImpl.class, null);
		persistencyServiceController = new ServiceController<>(context); 
		migratorService = getMigratorService();
				
		activatePersistency(baselineModelController.getService());
		addBaselinedata();
		deactivatePersistency();
	}
	
	@AfterClass
	public static void shutdown() {
		baselineModelController.unregister();
		attributeAddedModelController.unregister();
	}
	
	private static void activatePersistency(IPackageProvider p) throws Exception {
		persistencyServiceController.register(IPersistencyService.class, CDOPersistencyService.class, null);
		persistencyService = persistencyServiceController.getService();
		CDOPersistencyService cdop = (CDOPersistencyService) persistencyService;
		startSupportServices(cdop, p);
		cdop.activate();
	}
	
	private static void deactivatePersistency() {
		CDOPersistencyService cdop = (CDOPersistencyService) persistencyService;
		cdop.deactivate();
		persistencyServiceController.unregister();
	}
	
	@Test 
	public void testNeedsMigration() throws Exception {
		activatePersistency(baselineModelController.getService());
		assertFalse(migratorService.needsMigration());
		migratorService.setModelProviderService(attributeAddedModelController.getService());
		assertTrue(migratorService.needsMigration());
		deactivatePersistency();
	}
	
	@Test
	public void doMigration() throws Exception {
		checkMigrationPreconditions();
		
		migratorService.setModelProviderService(attributeAddedModelController.getService());
		// Once we know how to call the standard activate method of the persistency service, 
		// we do not need to initiate the migration here as it is already initiated in the activate method
		migratorService.doMigration();  
		
		checkMigrationPostconditions();
	}
	
	private void checkMigrationPreconditions() throws Exception {
		activatePersistency(baselineModelController.getService());
		ITransaction transaction = persistencyService.openTransaction();
		Resource resource = transaction.getResource();
		EObject root = SpecmateEcoreUtil.getEObjectWithId("root", resource.getContents());
		assertTrue(root instanceof com.specmate.migration.test.baseline.testmodel.base.Folder);
		EObject diagram = SpecmateEcoreUtil.getEObjectWithId("d0", root.eContents());
		assertTrue(diagram instanceof com.specmate.migration.test.baseline.testmodel.artefact.Diagram);
		assertNull(SpecmateEcoreUtil.getAttributeValue(root, "name", String.class));
		assertNull(SpecmateEcoreUtil.getAttributeValue(diagram, "name", String.class));
		transaction.close();
		deactivatePersistency();
	}

	private void checkMigrationPostconditions() throws Exception {
		activatePersistency(attributeAddedModelController.getService());
		
		ITransaction transaction = persistencyService.openTransaction();
		Resource resource = transaction.getResource();
		EObject root = SpecmateEcoreUtil.getEObjectWithId("root", resource.getContents());
		assertNotNull(root);
		
		assertTrue(root instanceof Folder);
		Folder rootFolder = (Folder) root;
		assertEquals("", rootFolder.getName());
		rootFolder.setName("f0");
		
		EObject diagram = SpecmateEcoreUtil.getEObjectWithId("d0", rootFolder.eContents());
		assertNotNull(diagram);
		assertTrue(diagram instanceof Diagram);
		Diagram d0 = (Diagram) diagram;
		assertNull(d0.getName());
		d0.setName("d0");
		
		Diagram d1 = ArtefactFactory.eINSTANCE.createDiagram();
		assertNull(d1.getName());
		d1.setName("d1");
		d1.setId("d1");
		
		rootFolder.getContents().add(d1);
		transaction.commit();
		deactivatePersistency();
	}
	
	private static IMigratorService getMigratorService() throws InterruptedException {
		ServiceTracker<IMigratorService, IMigratorService> migratorServiceTracker =
				new ServiceTracker<>(context, IMigratorService.class.getName(), null);
		
		migratorServiceTracker.open();
		IMigratorService migratorService = migratorServiceTracker.waitForService(10000);
		Assert.assertNotNull(migratorService);
		return migratorService;
	}
	
	private static ConfigurationAdmin getConfigurationAdmin() throws InterruptedException {
		ServiceTracker<ConfigurationAdmin, ConfigurationAdmin> configurationAdminTracker =
				new ServiceTracker<>(context, ConfigurationAdmin.class.getName(), null);
		
		configurationAdminTracker.open();
		ConfigurationAdmin configurationAdmin = configurationAdminTracker.waitForService(10000);
		Assert.assertNotNull(configurationAdmin);
		return configurationAdmin;
	}
	
	private static void startSupportServices(CDOPersistencyService ps, IPackageProvider ip) throws InterruptedException {
		ServiceTracker<LogService, LogService> logServiceTracker =
				new ServiceTracker<>(context, LogService.class.getName(), null);
		
		logServiceTracker.open();
		LogService logService = logServiceTracker.waitForService(10000);
		Assert.assertNotNull(logService);
		
		ServiceTracker<IURIFactory, IURIFactory> iuriServiceTracker =
				new ServiceTracker<>(context, IURIFactory.class.getName(), null);
		
		iuriServiceTracker.open();
		IURIFactory iuriService = iuriServiceTracker.waitForService(10000);
		Assert.assertNotNull(iuriService);
		
		ServiceTracker<EventAdmin, EventAdmin> eventAdminTracker =
				new ServiceTracker<>(context, EventAdmin.class.getName(), null);
		
		eventAdminTracker.open();
		EventAdmin eventAdminService = eventAdminTracker.waitForService(10000);
		Assert.assertNotNull(eventAdminService);
		
		ps.setEventAdmin(eventAdminService);
		ps.setLogService(logService);
		ps.setUriFactory(iuriService);
		ps.addModelProvider(ip);
	}
	
	private static void addBaselinedata() throws SpecmateException, InterruptedException {
		ITransaction transaction = persistencyService.openTransaction();
		Resource resource = transaction.getResource();
		EObject root = SpecmateEcoreUtil.getEObjectWithName("root", resource.getContents());
		
		if(root == null) {
			com.specmate.migration.test.baseline.testmodel.base.Folder f = BaseFactory.eINSTANCE.createFolder();
			f.setId("root");
			loadBaselineTestdata(f);
			
			transaction.getResource().getContents().add(f);
			
			try {
				transaction.commit();
			}
			catch(SpecmateException e) {
				System.out.println(e.getMessage());
			}
			
		}
	}
	
	private static void loadBaselineTestdata(com.specmate.migration.test.baseline.testmodel.base.Folder root) {
		com.specmate.migration.test.baseline.testmodel.artefact.Diagram d0 = com.specmate.migration.test.baseline.testmodel.artefact.ArtefactFactory.eINSTANCE.createDiagram();
		d0.setId("d0");
		d0.setTested(true);
		
		root.getContents().add(d0);
	}
	
}
