package com.specmate.migration.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Date;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;

import com.specmate.migration.test.attributeadded.testmodel.artefact.ArtefactFactory;
import com.specmate.migration.test.attributeadded.testmodel.artefact.Diagram;
import com.specmate.migration.test.attributeadded.testmodel.base.BasePackage;
import com.specmate.migration.test.attributeadded.testmodel.base.Folder;
import com.specmate.migration.test.support.TestMigratorImpl;
import com.specmate.model.support.util.SpecmateEcoreUtil;
import com.specmate.persistency.ITransaction;

public class AddAttributeTest extends MigrationTestBase {
	
	
	public AddAttributeTest() throws Exception {
		super("attributetest", BasePackage.class.getName());
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
		Date date = d0.getCreated();
		assertEquals(TestMigratorImpl.DEFAULT_DATE.getTime(), date.getTime());
		assertTrue(d0.getNotes().isEmpty());
		d0.getNotes().add("Test Note");

		Diagram d1 = ArtefactFactory.eINSTANCE.createDiagram();
		assertNull(d1.getName());
		d1.setName("d1");
		d1.setId("d1");

		rootFolder.getContents().add(d1);
		transaction.commit();
	}
}
