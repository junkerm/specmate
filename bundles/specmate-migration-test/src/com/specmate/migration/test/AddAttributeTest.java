package com.specmate.migration.test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Dictionary;
import java.util.Hashtable;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.service.cm.ConfigurationAdmin;
import org.osgi.util.tracker.ServiceTracker;

import com.specmate.common.SpecmateException;
import com.specmate.migration.api.IMigratorService;
import com.specmate.migration.test.attributeadded.testmodel.artefact.ArtefactFactory;
import com.specmate.migration.test.attributeadded.testmodel.artefact.Diagram;
import com.specmate.migration.test.attributeadded.testmodel.base.Folder;
import com.specmate.migration.test.baseline.testmodel.base.BaseFactory;
import com.specmate.migration.test.support.AttributeAddedModelProviderImpl;
import com.specmate.model.support.util.SpecmateEcoreUtil;
import com.specmate.persistency.IPersistencyService;
import com.specmate.persistency.ITransaction;
import com.specmate.persistency.cdo.internal.config.CDOPersistenceConfig;


public class AddAttributeTest {
	private static BundleContext context;
	private static IPersistencyService persistencyService;
	private static IMigratorService migratorService;
	
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
		
		persistencyService = getPersistencyService();
		migratorService = getMigratorService();
	
		addBaselinedata();
	}
	
	@Test 
	public void testNeedsMigration() throws Exception {
		assertFalse(migratorService.needsMigration());
		migratorService.setModelProviderService(new AttributeAddedModelProviderImpl());
		assertTrue(migratorService.needsMigration());
	}
	
	@Test
	public void doMigration() throws Exception {
		ITransaction transaction = persistencyService.openTransaction();
		Resource resource = transaction.getResource();
		EObject root = SpecmateEcoreUtil.getEObjectWithName("root", resource.getContents());
		assertNotNull(root);
		EObject diagram = SpecmateEcoreUtil.getEObjectWithName("d0", root.eContents());
		assertNotNull(diagram);
		assertNull(SpecmateEcoreUtil.getAttributeValue(root, "id", String.class));
		assertNull(SpecmateEcoreUtil.getAttributeValue(diagram, "id", String.class));
		transaction.close();
		
		
		Bundle[] bundles = context.getBundles();
		for(int i = 0; i < bundles.length; i++) {
			if(bundles[i].getSymbolicName().equals("specmate-persistency-cdo")) {
				bundles[i].stop();
				
				// Increase rank of AttributeAddedModelProviderImpl
				
				bundles[i].start();
				persistencyService = getPersistencyService();
				break;
			}
		}
	
		
		
		migratorService.setModelProviderService(new AttributeAddedModelProviderImpl());
		migratorService.doMigration();
		
		transaction = persistencyService.openTransaction();
		resource = transaction.getResource();
		root = SpecmateEcoreUtil.getEObjectWithName("root", resource.getContents());
		assertNotNull(root);
		
		assertTrue(root instanceof Folder);
		Folder rootFolder = (Folder) root;
		rootFolder.setId("f0");
		
		diagram = SpecmateEcoreUtil.getEObjectWithName("d0", resource.getContents());
		assertNotNull(diagram);
		assertTrue(diagram instanceof Diagram);
		Diagram d0 = (Diagram) diagram;
		d0.setId("d0");
		
		Diagram d1 = ArtefactFactory.eINSTANCE.createDiagram();
		d1.setName("d1");
		d1.setId("d1");
		
		rootFolder.getContents().add(d1);

		transaction.commit();
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
	
	private static void addBaselinedata() throws SpecmateException, InterruptedException {
		ITransaction transaction = persistencyService.openTransaction();
		Resource resource = transaction.getResource();
		EObject root = SpecmateEcoreUtil.getEObjectWithName("root", resource.getContents());
		
		if(root == null) {
			com.specmate.migration.test.baseline.testmodel.base.Folder f = BaseFactory.eINSTANCE.createFolder();
			f.setName("root");
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
		com.specmate.migration.test.baseline.testmodel.artefact.Diagram d1 = com.specmate.migration.test.baseline.testmodel.artefact.ArtefactFactory.eINSTANCE.createDiagram();
		d1.setName("d0");
		d1.setTested(true);
		
		root.getContents().add(d1);
	}
	
}
