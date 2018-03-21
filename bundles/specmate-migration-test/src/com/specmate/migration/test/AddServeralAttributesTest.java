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
import org.junit.Test;

import com.specmate.migration.test.severalattributesadded.testmodel.artefact.ArtefactFactory;
import com.specmate.migration.test.severalattributesadded.testmodel.artefact.Diagram;
import com.specmate.migration.test.severalattributesadded.testmodel.base.Folder;
import com.specmate.migration.test.support.AttributeAddedMigrator;
import com.specmate.migration.test.support.ServiceController;
import com.specmate.migration.test.support.SeveralAttributesAddedModelProviderImpl;
import com.specmate.model.support.util.SpecmateEcoreUtil;
import com.specmate.persistency.IPackageProvider;
import com.specmate.persistency.ITransaction;

public class AddServeralAttributesTest extends MigrationTestBase {
	private ServiceController<SeveralAttributesAddedModelProviderImpl> newModelController;
	
	public AddServeralAttributesTest() throws Exception {
		super("severalattributestest");
		newModelController = new ServiceController<>(context);
		newModelController.register(IPackageProvider.class, SeveralAttributesAddedModelProviderImpl.class, null); 
		
		Dictionary<String, Object> properties = new Hashtable<>();
		properties.put(AttributeAddedMigrator.KEY_MIGRATOR_TEST, AddServeralAttributesTest.class.getName());
		configurationAdmin.getConfiguration(AttributeAddedMigrator.PID).update(properties);
	}
	
	@Test 
	public void testNeedsMigration() throws Exception {
		activatePersistency(baselineModelController.getService());
		assertFalse(migratorService.needsMigration());
		migratorService.setModelProviderService(newModelController.getService());
		assertTrue(migratorService.needsMigration());
		deactivatePersistency();
	}
	
	@Test
	public void doMigration() throws Exception {
		checkMigrationPreconditions();
		migratorService.setModelProviderService(newModelController.getService());
		migratorService.doMigration();  
		checkMigrationPostconditions();
	}
	
	private void checkMigrationPostconditions() throws Exception {
		activatePersistency(newModelController.getService());
		
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
		assertFalse(d0.isLinked());
		
		
		Diagram d1 = ArtefactFactory.eINSTANCE.createDiagram();
		assertNull(d1.getName());
		d1.setName("d1");
		d1.setId("d1");
		
		rootFolder.getContents().add(d1);
		transaction.commit();
		deactivatePersistency();
	}

}
