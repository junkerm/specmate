package com.specmate.migration.test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;

import com.specmate.migration.test.onlymetachange.testmodel.artefact.Diagram;
import com.specmate.migration.test.onlymetachange.testmodel.base.BasePackage;
import com.specmate.migration.test.onlymetachange.testmodel.base.Folder;
import com.specmate.model.support.util.SpecmateEcoreUtil;
import com.specmate.persistency.ITransaction;

public class OnlyMetaChangeTest extends MigrationTestBase {

	public OnlyMetaChangeTest() throws Exception {
		super("onlymetachangetest", BasePackage.class.getName());
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
	}

}
