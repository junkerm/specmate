package com.specmate.migration.test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.junit.Test;

import com.specmate.migration.test.attributerenamed.testmodel.artefact.Diagram;
import com.specmate.migration.test.attributerenamed.testmodel.base.BasePackage;
import com.specmate.migration.test.attributerenamed.testmodel.base.Folder;
import com.specmate.model.support.util.SpecmateEcoreUtil;
import com.specmate.persistency.ITransaction;


public class RenamedAttributeTest extends MigrationTestBase {

	public RenamedAttributeTest() throws Exception {
		super("renamedattributetest");
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
		Diagram d0 = (Diagram) diagram;
		assertTrue(d0.isIstested());
		d0.setIstested(false);
		
		transaction.commit();
		deactivatePersistency();
	}
}
