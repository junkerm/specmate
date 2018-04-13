package com.specmate.migration.test;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.Dictionary;
import java.util.Hashtable;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.junit.Assert;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.service.cm.ConfigurationAdmin;
import org.osgi.service.event.EventAdmin;
import org.osgi.service.log.LogService;
import org.osgi.util.tracker.ServiceTracker;

import com.specmate.common.SpecmateException;
import com.specmate.migration.api.IMigratorService;
import com.specmate.migration.test.baseline.testmodel.artefact.ArtefactFactory;
import com.specmate.migration.test.baseline.testmodel.artefact.Diagram;
import com.specmate.migration.test.baseline.testmodel.artefact.File;
import com.specmate.migration.test.baseline.testmodel.base.BaseFactory;
import com.specmate.migration.test.baseline.testmodel.base.BasePackage;
import com.specmate.migration.test.support.ServiceController;
import com.specmate.migration.test.support.TestMigratorImpl;
import com.specmate.migration.test.support.TestModelProviderImpl;
import com.specmate.model.support.util.SpecmateEcoreUtil;
import com.specmate.persistency.IPackageProvider;
import com.specmate.persistency.IPersistencyService;
import com.specmate.persistency.ITransaction;
import com.specmate.persistency.cdo.internal.CDOPersistencyService;
import com.specmate.persistency.cdo.internal.config.CDOPersistenceConfig;
import com.specmate.urihandler.IURIFactory;

public abstract class MigrationTestBase {
	protected BundleContext context;
	protected ServiceController<CDOPersistencyService> persistencyServiceController;
	protected IPersistencyService persistencyService;
	protected IMigratorService migratorService;
	protected ConfigurationAdmin configurationAdmin;
	private String dbname;
	

	public MigrationTestBase(String dbname) throws Exception {
		context = FrameworkUtil.getBundle(MigrationTestBase.class).getBundleContext();
		this.dbname = dbname;
		configurationAdmin = getConfigurationAdmin();
		setupDBConfiguration(); 
		configureMigrator();
		
		persistencyServiceController = new ServiceController<>(context); 
		migratorService = getMigratorService();
				
		configureTestModel(BasePackage.class.getName());
		activatePersistency();
		addBaselinedata();
		deactivatePersistency();
	}
	
	private void configureMigrator() throws IOException {
		Dictionary<String, Object> properties = new Hashtable<>();
		properties.put(TestMigratorImpl.KEY_MIGRATOR_TEST, this.getClass().getName());
		configurationAdmin.getConfiguration(TestMigratorImpl.PID).update(properties);
	}

	protected void configureTestModel(String basePackageClassName) throws IOException {
		Dictionary<String, Object> properties = new Hashtable<>();
		properties.put(TestModelProviderImpl.KEY_MODEL_NAME, basePackageClassName);
		configurationAdmin.getConfiguration(TestModelProviderImpl.PID).update(properties);
	}
	
	protected void activatePersistency() throws Exception {
		persistencyServiceController.register(IPersistencyService.class, CDOPersistencyService.class, null);
		persistencyService = persistencyServiceController.getService();
		CDOPersistencyService cdop = (CDOPersistencyService) persistencyService;
		startSupportServices(cdop);
		cdop.activateFromTest(dbname);
	}
	
	protected void deactivatePersistency() {
		CDOPersistencyService cdop = (CDOPersistencyService) persistencyService;
		cdop.deactivate();
		persistencyServiceController.unregister();
	}
	
	protected void checkMigrationPreconditions() throws Exception {
		configureTestModel(BasePackage.class.getName());
		activatePersistency();
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
	
	private ConfigurationAdmin getConfigurationAdmin() throws InterruptedException {
		ServiceTracker<ConfigurationAdmin, ConfigurationAdmin> configurationAdminTracker =
				new ServiceTracker<>(context, ConfigurationAdmin.class.getName(), null);
		
		configurationAdminTracker.open();
		ConfigurationAdmin configurationAdmin = configurationAdminTracker.waitForService(10000);
		Assert.assertNotNull(configurationAdmin);
		return configurationAdmin;
	}
	
	private IMigratorService getMigratorService() throws InterruptedException {
		ServiceTracker<IMigratorService, IMigratorService> migratorServiceTracker =
				new ServiceTracker<>(context, IMigratorService.class.getName(), null);
		
		migratorServiceTracker.open();
		IMigratorService migratorService = migratorServiceTracker.waitForService(10000);
		Assert.assertNotNull(migratorService);
		return migratorService;
	}
	
	private void setupDBConfiguration() throws InterruptedException, IOException {
		Dictionary<String, Object> properties = new Hashtable<>();
		properties.put(CDOPersistenceConfig.KEY_JDBC_CONNECTION, "jdbc:h2:mem:" + this.dbname + ";DB_CLOSE_DELAY=-1");
		properties.put(CDOPersistenceConfig.KEY_REPOSITORY_NAME, "testrepo");
		properties.put(CDOPersistenceConfig.KEY_RESOURCE_NAME, "r1");
		properties.put(CDOPersistenceConfig.KEY_USER_RESOURCE_NAME, "r2");	
		configurationAdmin.getConfiguration(CDOPersistenceConfig.PID).update(properties);
	}
	
	private void startSupportServices(CDOPersistencyService ps) throws InterruptedException {
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
		
		ServiceTracker<IPackageProvider, IPackageProvider> packageProviderTracker =
				new ServiceTracker<>(context, IPackageProvider.class.getName(), null);
		packageProviderTracker.open();
		IPackageProvider packageProvider = packageProviderTracker.waitForService(1000);
		Assert.assertNotNull(packageProvider);
		
		ps.setEventAdmin(eventAdminService);
		ps.setLogService(logService);
		ps.setUriFactory(iuriService);
		ps.addModelProvider(packageProvider);
	}
	
	private void addBaselinedata() throws SpecmateException, InterruptedException {
		ITransaction transaction = persistencyService.openTransaction();
		Resource resource = transaction.getResource();
		EObject root = SpecmateEcoreUtil.getEObjectWithName("root", resource.getContents());
		
		if (root == null) {
			com.specmate.migration.test.baseline.testmodel.base.Folder f = BaseFactory.eINSTANCE.createFolder();
			f.setId("root");
			loadBaselineTestdata(f);
			
			transaction.getResource().getContents().add(f);
			
			try {
				transaction.commit();
			} catch (SpecmateException e) {
				System.out.println(e.getMessage());
			}
			
		}
	}
	
	private void loadBaselineTestdata(com.specmate.migration.test.baseline.testmodel.base.Folder root) {
		Diagram d0 = ArtefactFactory.eINSTANCE.createDiagram();
		d0.setId("d0");
		d0.setTested(true);
		
		File f0 = ArtefactFactory.eINSTANCE.createFile();
		f0.setId("f0");
		f0.setTested(false);
		f0.setBooleanVar1(true); 
		f0.setByteVar1((byte) 3);
		f0.setCharVar1('3');
		f0.setDoubleVar1(3.14);
		f0.setFloatVar1(3.14f);
		f0.setIntVar1(3);
		f0.setLongVar1(3L);
		f0.setShortVar1((short) 3);
		f0.setStringVar1("t");
		
		f0.setBooleanVar2(true); 
		f0.setByteVar2((byte) 3);
		f0.setCharVar2('3');
		f0.setDoubleVar2(3.14);
		f0.setFloatVar2(3.14f);
		f0.setIntVar2(3);
		f0.setLongVar2(3L);
		f0.setShortVar2((short) 3);
		f0.setStringVar2("true");
		
		f0.setBooleanVar3(true); 
		f0.setByteVar3((byte) 3);
		f0.setCharVar3('3');
		f0.setDoubleVar3(3.14);
		f0.setFloatVar3(3.14f);
		f0.setIntVar3(3);
		f0.setLongVar3(3L);
		f0.setShortVar3((short) 3);
		f0.setStringVar3("T");
		
		f0.setBooleanVar4(true); 
		f0.setByteVar4((byte) 3);
		f0.setCharVar4('3');
		f0.setDoubleVar4(3.14);
		f0.setFloatVar4(3.14f);
		f0.setIntVar4(3);
		f0.setLongVar4(3L);
		f0.setShortVar4((short) 3);
		f0.setStringVar4("TRUE");
		
		f0.setBooleanVar5(true); 
		f0.setByteVar5((byte) 3);
		f0.setCharVar5('3');
		f0.setDoubleVar5(3.14);
		f0.setFloatVar5(3.14f);
		f0.setIntVar5(3);
		f0.setLongVar5(3L);
		f0.setShortVar5((short) 3);
		f0.setStringVar5("false");
		
		root.getContents().add(d0);
		root.getContents().add(f0);
	}
}
