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
import com.specmate.migration.test.support.TestModelProviderImpl;
import com.specmate.model.support.util.SpecmateEcoreUtil;
import com.specmate.persistency.ITransaction;


public class RenamedAttributeTest extends MigrationTestBase {

	public RenamedAttributeTest() throws Exception {
		super("renamedattributetest", BasePackage.class.getName());
	}
	
	@Test
	public void testNeedsMigration() throws Exception {
		assertFalse(migratorService.needsMigration());
		
		TestModelProviderImpl testModel = (TestModelProviderImpl) getTestModelService();
		testModel.setModelName(testModelName);
		
		assertTrue(migratorService.needsMigration());
	}

	@Test
	public void doMigration() throws Exception {
		checkMigrationPreconditions();
				
		TestModelProviderImpl testModel = (TestModelProviderImpl) getTestModelService();
		testModel.setModelName(testModelName);
		
		// Initiate the migration
		persistency.shutdown();
		persistency.start();
		
		checkMigrationPostconditions();
	}
	
	protected void checkMigrationPostconditions() throws Exception {	
		ITransaction transaction = persistency.openTransaction();
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
	}
}
