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
import com.specmate.model.support.util.SpecmateEcoreUtil;
import com.specmate.persistency.ITransaction;

public class ChangedTypesTest extends MigrationTestBase {

	public ChangedTypesTest() throws Exception {
		super("changetypes");
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
		// Once we know how to call the standard activate method of the persistency service, 
		// we do not need to initiate the migration here as it is already initiated in the activate method
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
		
		// Short to X conversion
		assertEquals(3, f0.getShortVar1());
		assertEquals(3L, f0.getShortVar2());
		assertEquals(3.0f, f0.getShortVar3(), 0.0);
		assertEquals(3.0, f0.getShortVar4(), 0.0);
		
		// Char to X conversion
		assertEquals(3, f0.getCharVar1());
		assertEquals(3L, f0.getCharVar2());
		assertEquals(3.0f,  f0.getCharVar3(), 0.0);
		assertEquals(3.0,  f0.getCharVar4(), 0.0);
		assertEquals("3", f0.getCharVar5());
		
		// Int to X conversion
		assertEquals(3L, f0.getIntVar1());
		assertEquals(3.0f, f0.getIntVar2(), 0.0);
		assertEquals(3.0, f0.getIntVar3(), 0.0);
		
		// Long to X conversion
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
		deactivatePersistency();
	}
}
