package com.specmate.migration.test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Dictionary;
import java.util.Hashtable;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.junit.Assert;
import org.junit.Test;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.service.cm.ConfigurationAdmin;
import org.osgi.util.tracker.ServiceTracker;

import com.specmate.common.OSGiUtil;
import com.specmate.common.SpecmateException;
import com.specmate.migration.api.IMigratorService;
import com.specmate.migration.test.baseline.testmodel.artefact.ArtefactFactory;
import com.specmate.migration.test.baseline.testmodel.artefact.Diagram;
import com.specmate.migration.test.baseline.testmodel.artefact.File;
import com.specmate.migration.test.baseline.testmodel.base.BaseFactory;
import com.specmate.migration.test.baseline.testmodel.base.BasePackage;
import com.specmate.migration.test.support.TestMigratorImpl;
import com.specmate.migration.test.support.TestModelProviderImpl;
import com.specmate.model.support.util.SpecmateEcoreUtil;
import com.specmate.persistency.IPackageProvider;
import com.specmate.persistency.IPersistencyService;
import com.specmate.persistency.ITransaction;
import com.specmate.persistency.cdo.config.CDOPersistenceConfig;

public abstract class MigrationTestBase {
	protected IPersistencyService persistency;
	protected BundleContext context;
	protected IMigratorService migratorService;
	protected String testModelName;

	private String dbname;

	public MigrationTestBase(String dbname, String testModelName) throws Exception {
		this.dbname = dbname;
		this.testModelName = testModelName;

		context = FrameworkUtil.getBundle(MigrationTestBase.class).getBundleContext();

		configurePersistency(getPersistencyProperties());
		configureMigrator();

		addBaselinedata();
	}

	@Test
	public void doMigration() throws Exception {
		checkMigrationPreconditions();

		assertFalse(migratorService.needsMigration());

		TestModelProviderImpl testModel = (TestModelProviderImpl) getTestModelService();
		testModel.setModelName(testModelName);

		assertTrue(migratorService.needsMigration());

		// Initiate the migration
		persistency.shutdown();
		persistency.start();

		checkMigrationPostconditions();

		// Resetting the model to the base model such that all tests start with the same
		// model
		testModel.setModelName(BasePackage.class.getName());
	}

	protected abstract void checkMigrationPostconditions() throws Exception;

	private void configureMigrator() throws Exception {
		ConfigurationAdmin configAdmin = getConfigAdmin();
		Dictionary<String, Object> properties = new Hashtable<>();
		properties.put(TestMigratorImpl.KEY_MIGRATOR_TEST, this.getClass().getName());
		OSGiUtil.configureService(configAdmin, TestMigratorImpl.PID, properties);
		migratorService = getMigratorService();
	}

	protected void configurePersistency(Dictionary<String, Object> properties) throws Exception {
		ConfigurationAdmin configAdmin = getConfigAdmin();
		OSGiUtil.configureService(configAdmin, CDOPersistenceConfig.PID, properties);

		// Allow time for the persistency to be started
		Thread.sleep(2000);

		persistency = getPersistencyService();
	}

	protected Dictionary<String, Object> getPersistencyProperties() {
		Dictionary<String, Object> properties = new Hashtable<>();
		properties.put(CDOPersistenceConfig.KEY_REPOSITORY_NAME, "specmate");
		properties.put(CDOPersistenceConfig.KEY_RESOURCE_NAME, "specmateResource");
		return properties;
	}

	protected void checkMigrationPreconditions() throws Exception {
		ITransaction transaction = persistency.openTransaction();
		Resource resource = transaction.getResource();
		EObject root = SpecmateEcoreUtil.getEObjectWithId("root", resource.getContents());
		assertTrue(root instanceof com.specmate.migration.test.baseline.testmodel.base.Folder);
		EObject diagram = SpecmateEcoreUtil.getEObjectWithId("d0", root.eContents());
		assertTrue(diagram instanceof com.specmate.migration.test.baseline.testmodel.artefact.Diagram);
		assertNull(SpecmateEcoreUtil.getAttributeValue(root, "name", String.class));
		assertNull(SpecmateEcoreUtil.getAttributeValue(diagram, "name", String.class));
		transaction.close();
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

	private IMigratorService getMigratorService() throws SpecmateException {
		ServiceTracker<IMigratorService, IMigratorService> migratorServiceTracker = new ServiceTracker<>(context,
				IMigratorService.class.getName(), null);

		migratorServiceTracker.open();
		IMigratorService migratorService;
		try {
			migratorService = migratorServiceTracker.waitForService(10000);
		} catch (InterruptedException e) {
			throw new SpecmateException(e);
		}
		Assert.assertNotNull(migratorService);
		return migratorService;
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

	protected IPackageProvider getTestModelService() throws SpecmateException {
		ServiceTracker<IPackageProvider, IPackageProvider> testModelTracker = new ServiceTracker<>(context,
				IPackageProvider.class.getName(), null);
		testModelTracker.open();
		IPackageProvider testModel;
		try {
			testModel = testModelTracker.waitForService(10000);
		} catch (InterruptedException e) {
			throw new SpecmateException(e);
		}
		Assert.assertNotNull(testModel);
		return testModel;
	}

	private void addBaselinedata() throws Exception {
		ITransaction transaction = persistency.openTransaction();
		Resource resource = transaction.getResource();
		EObject root = SpecmateEcoreUtil.getEObjectWithName("root", resource.getContents());

		if (root == null) {
			com.specmate.migration.test.baseline.testmodel.base.Folder f = BaseFactory.eINSTANCE.createFolder();
			f.setId("root");
			loadBaselineTestdata(f);

			transaction.getResource().getContents().add(f);
			transaction.commit();
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
