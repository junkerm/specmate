package com.specmate.migration.test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;

import com.specmate.migration.test.objectadded.testmodel.artefact.ArtefactFactory;
import com.specmate.migration.test.objectadded.testmodel.artefact.Diagram;
import com.specmate.migration.test.objectadded.testmodel.artefact.Document;
import com.specmate.migration.test.objectadded.testmodel.base.BasePackage;
import com.specmate.migration.test.objectadded.testmodel.base.Folder;
import com.specmate.model.support.util.SpecmateEcoreUtil;
import com.specmate.persistency.ITransaction;

public class AddObjectTest extends MigrationTestBase {

	public AddObjectTest() throws Exception {
		super("addobjecttest", BasePackage.class.getName());
	}

	@Override
	protected void checkMigrationPostconditions() throws Exception {
		ITransaction transaction = persistency.openTransaction();
		transaction.enableValidators(false);
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

		// Test that we can save the new object type
		Document doc0 = ArtefactFactory.eINSTANCE.createDocument();
		doc0.setId("doc0");
		doc0.setLength(12);
		doc0.setOwner("Pelle");
		doc0.getContents().add(d1);

		rootFolder.getContents().add(doc0);
		transaction.commit();

		// Test that we can also retrieve the new object type
		EObject document = SpecmateEcoreUtil.getEObjectWithId("doc0", rootFolder.eContents());
		assertNotNull(document);
		assertTrue(document instanceof Document);
		doc0 = (Document) document;

		diagram = SpecmateEcoreUtil.getEObjectWithId("d1", doc0.eContents());
		assertNotNull(diagram);
		assertTrue(diagram instanceof Diagram);
	}
}
