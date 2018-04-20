package com.specmate.migration.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;


import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.junit.Test;

import com.specmate.migration.test.severalattributesadded.testmodel.artefact.ArtefactFactory;
import com.specmate.migration.test.severalattributesadded.testmodel.artefact.Diagram;
import com.specmate.migration.test.severalattributesadded.testmodel.base.BasePackage;
import com.specmate.migration.test.severalattributesadded.testmodel.base.Folder;
import com.specmate.migration.test.support.TestModelProviderImpl;
import com.specmate.model.support.util.SpecmateEcoreUtil;
import com.specmate.persistency.ITransaction;

public class AddSeveralAttributesTest extends MigrationTestBase {
	
	public AddSeveralAttributesTest() throws Exception {
		super("severalattributestest", BasePackage.class.getName());
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
		assertEquals("", rootFolder.getName());
		rootFolder.setName("f0");
		
		EObject diagram = SpecmateEcoreUtil.getEObjectWithId("d0", rootFolder.eContents());
		assertNotNull(diagram);
		assertTrue(diagram instanceof Diagram);
		Diagram d0 = (Diagram) diagram;
		assertNull(d0.getName());
		d0.setName("d0");
		assertFalse(d0.getLinked());
		assertNull(d0.getLength());
		assertEquals(new Integer(-1), d0.getAmount());
		assertEquals(-1,  d0.getIntamount());
		assertEquals(0.0, d0.getDoublelength(), 0.0);
		assertFalse(d0.isBooleanlinked());
		
		Diagram d1 = ArtefactFactory.eINSTANCE.createDiagram();
		assertNull(d1.getName());
		d1.setName("d1");
		d1.setId("d1");
		d1.setAmount(20);
		d1.setIntamount(5);
		d1.setLength(3.14);
		d1.setDoublelength(3.14);
		d1.setLinked(true);
		d1.setBooleanlinked(true);
		
		rootFolder.getContents().add(d1);
		transaction.commit();
	}
}
