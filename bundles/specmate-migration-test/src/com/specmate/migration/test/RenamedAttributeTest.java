package com.specmate.migration.test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;

import com.specmate.common.SpecmateException;
import com.specmate.migration.test.attributerenamed.testmodel.artefact.Diagram;
import com.specmate.migration.test.attributerenamed.testmodel.base.BasePackage;
import com.specmate.migration.test.attributerenamed.testmodel.base.Folder;
import com.specmate.model.support.util.SpecmateEcoreUtil;
import com.specmate.persistency.IChange;
import com.specmate.persistency.ITransaction;

public class RenamedAttributeTest extends MigrationTestBase {

	public RenamedAttributeTest() throws Exception {
		super("renamedattributetest", BasePackage.class.getName());
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
		Diagram d0 = (Diagram) diagram;
		assertTrue(d0.isIstested());

		transaction.doAndCommit(new IChange<Object>() {
			@Override
			public Object doChange() throws SpecmateException {
				d0.setIstested(false);
				return null;
			}
		});

		transaction.close();
	}
}
