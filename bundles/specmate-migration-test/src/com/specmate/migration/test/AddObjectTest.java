package com.specmate.migration.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.junit.Test;

import com.specmate.migration.test.objectadded.testmodel.artefact.ArtefactFactory;
import com.specmate.migration.test.objectadded.testmodel.artefact.Diagram;
import com.specmate.migration.test.objectadded.testmodel.artefact.Document;
import com.specmate.migration.test.objectadded.testmodel.base.BasePackage;
import com.specmate.migration.test.objectadded.testmodel.base.Folder;
import com.specmate.model.support.util.SpecmateEcoreUtil;
import com.specmate.persistency.ITransaction;

public class AddObjectTest extends MigrationTestBase {

	public AddObjectTest() throws Exception {
		super("addobjecttest");
	}
	
	@Test 
	public void testNeedsMigration() throws Exception {
		activatePersistency();
		assertFalse(migratorService.needsMigration());
		configureTestModel(BasePackage.class.getName());
		assertTrue(migratorService.needsMigration());
		deactivatePersistency();
	}
	
	@Test
	public void doMigration() throws Exception {
		checkMigrationPreconditions();
		configureTestModel(BasePackage.class.getName());
		migratorService.doMigration();  
		checkMigrationPostconditions();
	}
	
	private void checkMigrationPostconditions() throws Exception {
		activatePersistency();
		
		ITransaction transaction = persistencyService.openTransaction();
		Resource resource = transaction.getResource();
		EObject root = SpecmateEcoreUtil.getEObjectWithId("root", resource.getContents());
		assertNotNull(root);
		
		assertTrue(root instanceof Folder);
		Folder rootFolder = (Folder) root;
		
		EObject diagram = SpecmateEcoreUtil.getEObjectWithId("d0", rootFolder.eContents());
		assertNotNull(diagram);
		assertTrue(diagram instanceof Diagram);
		
		Diagram d1 = ArtefactFactory.eINSTANCE.createDiagram();
		d1.setId("d1");
		
		Document doc0 = ArtefactFactory.eINSTANCE.createDocument();
		doc0.setId("doc0");
		doc0.setLength(12);
		doc0.setOwner("Pelle");
		
		rootFolder.getContents().add(d1);
		rootFolder.getContents().add(doc0);
		transaction.commit();
		deactivatePersistency();
	}
}
