package com.specmate.migration.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull; 
import static org.junit.Assert.assertTrue;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.junit.Test;

import com.specmate.migration.test.changedtypes.testmodel.artefact.File;
import com.specmate.migration.test.changedtypes.testmodel.base.BasePackage;
import com.specmate.migration.test.changedtypes.testmodel.base.Folder;
import com.specmate.migration.test.support.TestModelProviderImpl;
import com.specmate.model.support.util.SpecmateEcoreUtil;
import com.specmate.persistency.ITransaction;

public class ChangedTypesTest extends MigrationTestBase {

	public ChangedTypesTest() throws Exception {
		super("changetypes", BasePackage.class.getName());
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
		
		EObject file = SpecmateEcoreUtil.getEObjectWithId("f0", rootFolder.eContents());
		assertNotNull(file);
		assertTrue(file instanceof File);
		File f0 = (File) file;
		
		/*
		 * Contrary to the documentation [1], variables of type byte are *not* stored as 
		 * TINYINT, but as SMALLINT. Hence we omit here the test for the
		 * conversion of type byte to X. 
		 * 
		 * [1] http://www.h2database.com/html/datatypes.html#tinyint_type
		 */
		
		/*
		 * The following assertions check whether the conversion from X to Y has been performed successfully. Note that 
		 * these are all positive tests. Negative test, i.e. tests that cover failure cases, would require a new 
		 * test model with invalid conversions and are currently not implemented.  
		 */
		
		// Short to int, long, float and long conversion. 
		assertEquals(3, f0.getShortVar1());
		assertEquals(3L, f0.getShortVar2());
		assertEquals(3.0f, f0.getShortVar3(), 0.0);
		assertEquals(3.0, f0.getShortVar4(), 0.0);
		
		// Char to int, long, float, long and string conversion
		assertEquals(3, f0.getCharVar1());
		assertEquals(3L, f0.getCharVar2());
		assertEquals(3.0f,  f0.getCharVar3(), 0.0);
		assertEquals(3.0,  f0.getCharVar4(), 0.0);
		assertEquals("3", f0.getCharVar5());
		
		// Int to long, float and double conversion
		assertEquals(3L, f0.getIntVar1());
		assertEquals(3.0f, f0.getIntVar2(), 0.0);
		assertEquals(3.0, f0.getIntVar3(), 0.0);
		
		// Long to float and double conversion
		assertEquals(3.0f, f0.getLongVar1(), 0.0);
		assertEquals(3.0, f0.getLongVar2(), 0.0);
		
		// Float to double conversion
		assertEquals(3.14, f0.getFloatVar1(), 0.0001);
		
		// Boolean to String conversion 
		assertEquals("TRUE", f0.getBooleanVar1());
		
		// String to boolean conversion
		assertTrue(f0.isStringVar1());
		assertTrue(f0.isStringVar2());
		assertTrue(f0.isStringVar3());
		assertTrue(f0.isStringVar4());
		assertFalse(f0.isStringVar5());
		
		transaction.commit();
	}
}
