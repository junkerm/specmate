package com.specmate.test.integration;

import static org.junit.Assert.fail;

import org.junit.Assert;
import org.junit.Test;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;

import com.mifmif.common.regex.Generex;
import com.specmate.common.SpecmateException;
import com.specmate.common.SpecmateValidationException;
import com.specmate.model.base.BaseFactory;
import com.specmate.model.base.Folder;
import com.specmate.model.support.api.IAttributeValidationService;

public class AttributeValidationServiceTest {
	private static BundleContext context;
	private static IAttributeValidationService validationService;

	public AttributeValidationServiceTest() throws SpecmateException {
		if (context == null) {
			context = FrameworkUtil.getBundle(EmfRestTest.class).getBundleContext();
		}

		if (validationService == null) {
			validationService = getValidationService();
		}
	}

	@Test
	public void testIDValidCharacters() {
		Folder folder = BaseFactory.eINSTANCE.createFolder();
		folder.setId("tEst_1-2");

		try {
			validationService.validateID(folder);
		} catch (SpecmateValidationException e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void testIDInvalidCharacters() {
		Folder folder = BaseFactory.eINSTANCE.createFolder();

		Generex generex = new Generex("test-[^a-zA-Z_0-9\\-]_case");
		generex.setSeed(System.currentTimeMillis());
		for (int i = 0; i < 50000; i++) {
			String id = generex.random(1, 1);
			folder.setId(id);
			try {
				validationService.validateID(folder);
				fail("Invalid character not detected: " + id);
			} catch (SpecmateValidationException e) {
				// All OK
			}
		}
	}

	@Test
	public void testIDEmpty() {
		Folder folder = BaseFactory.eINSTANCE.createFolder();

		try {
			validationService.validateID(folder);
			fail("Empty id not detected");
		} catch (SpecmateValidationException e) {
			// All OK
		}

		folder.setId("");
		try {
			validationService.validateID(folder);
			fail("Empty id not detected");
		} catch (SpecmateValidationException e) {
			// All OK
		}

		folder.setId(" ");
		try {
			validationService.validateID(folder);
			fail("Empty id not detected");
		} catch (SpecmateValidationException e) {
			// All OK
		}
	}

	@Test
	public void testFolderNameEmpty() {
		Folder folder = BaseFactory.eINSTANCE.createFolder();

		try {
			validationService.validateFolderName(folder);
			fail("Empty folder name not detected");
		} catch (SpecmateValidationException e) {
			// All OK
		}

		folder.setName("");
		try {
			validationService.validateFolderName(folder);
			fail("Empty folder name not detected");
		} catch (SpecmateValidationException e) {
			// All OK
		}

		folder.setName(" ");
		try {
			validationService.validateFolderName(folder);
			fail("Empty folder name not detected");
		} catch (SpecmateValidationException e) {
			// All OK
		}
	}

	@Test
	public void testUniqueID() {
		Folder parent = BaseFactory.eINSTANCE.createFolder();
		parent.setId("parent");

		Folder child1 = BaseFactory.eINSTANCE.createFolder();
		child1.setId("child1");

		Folder child2 = BaseFactory.eINSTANCE.createFolder();
		child1.setId("child2");

		Folder grandchild = BaseFactory.eINSTANCE.createFolder();
		grandchild.setId("grandchild");

		parent.getContents().add(child1);
		parent.getContents().add(child2);

		try {
			validationService.validateUniqueID(child1, grandchild);
		} catch (SpecmateValidationException e) {
			fail(e.getMessage());
		}

		try {
			validationService.validateUniqueID(parent, child1);
			fail("Add the same node twice in tree");
		} catch (SpecmateValidationException e) {
			// All OK
		}

		child1.getContents().add(grandchild);

		try {
			validationService.validateUniqueID(child2, grandchild);
		} catch (SpecmateValidationException e) {
			fail("Siblings can have children with the same id"); // Is this really what we want?
		}

		child2.getContents().add(grandchild);

		try {
			validationService.validateUniqueID(child1, grandchild);
			fail("Could add the same node twice in tree");
		} catch (SpecmateValidationException e) {
			// All OK
		}

		try {
			validationService.validateUniqueID(child2, grandchild);
			fail("Could add the same node twice in tree");
		} catch (SpecmateValidationException e) {
			// All OK
		}

	}

	private IAttributeValidationService getValidationService() throws SpecmateException {
		ServiceTracker<IAttributeValidationService, IAttributeValidationService> validationServiceTracker = new ServiceTracker<>(
				context, IAttributeValidationService.class.getName(), null);
		validationServiceTracker.open();
		IAttributeValidationService validationService;
		try {
			validationService = validationServiceTracker.waitForService(10000);
		} catch (InterruptedException e) {
			throw new SpecmateException(e);
		}
		Assert.assertNotNull(validationService);
		return validationService;
	}
}
