package com.specmate.migration.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.junit.Test;

import com.specmate.migration.test.attributeadded.testmodel.artefact.ArtefactFactory;
import com.specmate.migration.test.attributeadded.testmodel.artefact.Diagram;
import com.specmate.migration.test.attributeadded.testmodel.base.Folder;
import com.specmate.migration.test.support.AttributeAddedModelProviderImpl;
import com.specmate.migration.test.support.ServiceController;
import com.specmate.model.support.util.SpecmateEcoreUtil;
import com.specmate.persistency.IPackageProvider;
import com.specmate.persistency.ITransaction;

public class AddAttributeTest extends MigrationTestBase {
	private ServiceController<AttributeAddedModelProviderImpl> attributeAddedModelController;
	
	public AddAttributeTest() throws Exception {
		super("attributetest");
		attributeAddedModelController = new ServiceController<>(context);
		attributeAddedModelController.register(IPackageProvider.class, AttributeAddedModelProviderImpl.class, null);
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
}
