package com.specmate.test.integration;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.junit.Assert;
import org.junit.Test;
import org.osgi.util.tracker.ServiceTracker;

import com.specmate.common.SpecmateException;
import com.specmate.connectors.api.IProjectConfigService;
import com.specmate.model.base.BaseFactory;
import com.specmate.model.base.Folder;
import com.specmate.model.support.util.SpecmateEcoreUtil;
import com.specmate.persistency.ITransaction;
import com.specmate.persistency.IView;

public class ProjectConfigServiceTest extends IntegrationTestBase {
	private static IProjectConfigService projectConfigService;

	private final String libid1 = "libfolder1";
	private final String libid2 = "libfolder2";
	private final String libid3 = "libfolder3";
	private final String somefolderid = "somefolderid";
	private final String libname1 = "Library folder 1";
	private final String libname2 = "Library folder 2";
	private final String libname3 = "Library folder 3";
	private final String somefoldername = "Some name";
	private final String libdesc1 = "Templates for type 1 requirements";
	private final String libdesc2 = "Templates for type 2 requirements";
	private final String libdesc3 = "Templates for type 3 requirements";
	private final String somefolderdesc = "This is some folder";
	private final String[] projectNames = { "testA", "testB" };

	public ProjectConfigServiceTest() throws Exception {
		super();

		if (projectConfigService == null) {
			projectConfigService = getProjectConfigService();
		}
	}

	@Test
	public void testAllNewLibraryFolders() throws Exception {
		testAllNewLibraryFolders_initData();
		projectConfigService.configureProjects(projectNames);
		testLibraryFolders_verify();
	}

	@Test
	public void testSomeNewLibraryFolders() throws Exception {
		testSomeNewLibraryFolders_initData();
		projectConfigService.configureProjects(projectNames);
		testLibraryFolders_verify();
	}

	@Test
	public void testNoNewLibraryFolders() throws Exception {
		testNoNewLibraryFolders_initData();
		projectConfigService.configureProjects(projectNames);
		testLibraryFolders_verify();
	}

	@Test
	public void testModifyLibraryFolders() throws Exception {
		testModifyLibraryFolders_initData();
		projectConfigService.configureProjects(projectNames);
		testLibraryFolders_verify();
	}

	@Test
	public void testNewAndModifyLibraryFolders() throws Exception {
		testNewAndModifyLibraryFolders_initData();
		projectConfigService.configureProjects(projectNames);
		testLibraryFolders_verify();
	}

	private void testAllNewLibraryFolders_initData() throws SpecmateException {
		ITransaction trans = null;

		try {
			trans = persistency.openTransaction();
			EList<EObject> root = trans.getResource().getContents();

			assertTrue(root.isEmpty());

			Folder testA = BaseFactory.eINSTANCE.createFolder();
			testA.setId(projectNames[0].toLowerCase());
			testA.setName(projectNames[0]);
			root.add(testA);

			Folder someFolderA = BaseFactory.eINSTANCE.createFolder();
			someFolderA.setId(somefolderid);
			someFolderA.setName(somefoldername);
			someFolderA.setDescription(somefolderdesc);
			testA.getContents().add(someFolderA);

			Folder testB = BaseFactory.eINSTANCE.createFolder();
			testB.setId(projectNames[1].toLowerCase());
			testB.setName(projectNames[1]);
			root.add(testB);

			trans.commit();
		} finally {
			if (trans != null) {
				trans.close();
			}
		}
	}

	private void testSomeNewLibraryFolders_initData() throws SpecmateException {
		ITransaction trans = null;

		try {
			trans = persistency.openTransaction();
			EList<EObject> root = trans.getResource().getContents();

			assertTrue(root.isEmpty());

			Folder testA = BaseFactory.eINSTANCE.createFolder();
			testA.setId(projectNames[0].toLowerCase());
			testA.setName(projectNames[0]);
			root.add(testA);

			Folder someFolderA = BaseFactory.eINSTANCE.createFolder();
			someFolderA.setId(somefolderid);
			someFolderA.setName(somefoldername);
			someFolderA.setDescription(somefolderdesc);
			testA.getContents().add(someFolderA);

			Folder libraryFolder2 = BaseFactory.eINSTANCE.createFolder();
			libraryFolder2.setId(libid2);
			libraryFolder2.setName(libname2);
			libraryFolder2.setDescription(libdesc2);
			testA.getContents().add(libraryFolder2);

			Folder testB = BaseFactory.eINSTANCE.createFolder();
			testB.setId(projectNames[1].toLowerCase());
			testB.setName(projectNames[1]);
			root.add(testB);

			trans.commit();
		} finally {
			if (trans != null) {
				trans.close();
			}
		}
	}

	private void testNoNewLibraryFolders_initData() throws SpecmateException {
		ITransaction trans = null;

		try {
			trans = persistency.openTransaction();
			EList<EObject> root = trans.getResource().getContents();

			assertTrue(root.isEmpty());

			Folder testA = BaseFactory.eINSTANCE.createFolder();
			testA.setId(projectNames[0].toLowerCase());
			testA.setName(projectNames[0]);
			root.add(testA);

			Folder someFolderA = BaseFactory.eINSTANCE.createFolder();
			someFolderA.setId(somefolderid);
			someFolderA.setName(somefoldername);
			someFolderA.setDescription(somefolderdesc);
			testA.getContents().add(someFolderA);

			Folder libraryFolder1 = BaseFactory.eINSTANCE.createFolder();
			libraryFolder1.setId(libid1);
			libraryFolder1.setName(libname1);
			libraryFolder1.setDescription(libdesc1);
			testA.getContents().add(libraryFolder1);

			Folder libraryFolder2 = BaseFactory.eINSTANCE.createFolder();
			libraryFolder2.setId(libid2);
			libraryFolder2.setName(libname2);
			libraryFolder2.setDescription(libdesc2);
			testA.getContents().add(libraryFolder2);

			Folder libraryFolder3 = BaseFactory.eINSTANCE.createFolder();
			libraryFolder3.setId(libid3);
			libraryFolder3.setName(libname3);
			libraryFolder3.setDescription(libdesc3);
			testA.getContents().add(libraryFolder3);

			Folder testB = BaseFactory.eINSTANCE.createFolder();
			testB.setId(projectNames[1].toLowerCase());
			testB.setName(projectNames[1]);
			root.add(testB);

			trans.commit();
		} finally {
			if (trans != null) {
				trans.close();
			}
		}
	}

	private void testModifyLibraryFolders_initData() throws SpecmateException {
		ITransaction trans = null;

		try {
			trans = persistency.openTransaction();
			EList<EObject> root = trans.getResource().getContents();

			assertTrue(root.isEmpty());

			Folder testA = BaseFactory.eINSTANCE.createFolder();
			testA.setId(projectNames[0].toLowerCase());
			testA.setName(projectNames[0]);
			root.add(testA);

			Folder someFolderA = BaseFactory.eINSTANCE.createFolder();
			someFolderA.setId(somefolderid);
			someFolderA.setName(somefoldername);
			someFolderA.setDescription(somefolderdesc);
			testA.getContents().add(someFolderA);

			Folder libraryFolder1 = BaseFactory.eINSTANCE.createFolder();
			libraryFolder1.setId(libid1);
			libraryFolder1.setName(libname1 + "_old");
			libraryFolder1.setDescription(libdesc1);
			testA.getContents().add(libraryFolder1);

			Folder libraryFolder2 = BaseFactory.eINSTANCE.createFolder();
			libraryFolder2.setId(libid2);
			libraryFolder2.setName(libname2);
			libraryFolder2.setDescription(libdesc2 + "_old");
			testA.getContents().add(libraryFolder2);

			Folder libraryFolder3 = BaseFactory.eINSTANCE.createFolder();
			libraryFolder3.setId(libid3);
			libraryFolder3.setName(libname3 + "_old");
			libraryFolder3.setDescription(libdesc3 + "_old");
			testA.getContents().add(libraryFolder3);

			Folder testB = BaseFactory.eINSTANCE.createFolder();
			testB.setId(projectNames[1].toLowerCase());
			testB.setName(projectNames[1]);
			root.add(testB);

			trans.commit();
		} finally {
			if (trans != null) {
				trans.close();
			}
		}
	}

	public void testNewAndModifyLibraryFolders_initData() throws SpecmateException {
		ITransaction trans = null;

		try {
			trans = persistency.openTransaction();
			EList<EObject> root = trans.getResource().getContents();

			assertTrue(root.isEmpty());

			Folder testA = BaseFactory.eINSTANCE.createFolder();
			testA.setId(projectNames[0].toLowerCase());
			testA.setName(projectNames[0]);
			root.add(testA);

			Folder someFolderA = BaseFactory.eINSTANCE.createFolder();
			someFolderA.setId(somefolderid);
			someFolderA.setName(somefoldername);
			someFolderA.setDescription(somefolderdesc);
			testA.getContents().add(someFolderA);

			Folder libraryFolder2 = BaseFactory.eINSTANCE.createFolder();
			libraryFolder2.setId(libid2);
			libraryFolder2.setName(libname2);
			libraryFolder2.setDescription(libdesc2 + "_old");
			testA.getContents().add(libraryFolder2);

			Folder libraryFolder3 = BaseFactory.eINSTANCE.createFolder();
			libraryFolder3.setId(libid3);
			libraryFolder3.setName(libname3 + "_old");
			libraryFolder3.setDescription(libdesc3);
			testA.getContents().add(libraryFolder3);

			Folder testB = BaseFactory.eINSTANCE.createFolder();
			testB.setId(projectNames[1].toLowerCase());
			testB.setName(projectNames[1]);
			root.add(testB);

			trans.commit();
		} finally {
			if (trans != null) {
				trans.close();
			}
		}
	}

	private void testLibraryFolders_verify() throws SpecmateException {
		IView view = null;

		try {
			view = persistency.openView();
			EList<EObject> root = view.getResource().getContents();

			assertEquals(2, root.size());
			Folder testA = (Folder) SpecmateEcoreUtil.getEObjectWithName(projectNames[0], root);
			assertEquals(4, testA.eContents().size());
			Folder libfolder1 = (Folder) SpecmateEcoreUtil.getEObjectWithId(libid1, testA.eContents());
			Folder libfolder2 = (Folder) SpecmateEcoreUtil.getEObjectWithId(libid2, testA.eContents());
			Folder libfolder3 = (Folder) SpecmateEcoreUtil.getEObjectWithId(libid3, testA.eContents());
			Folder somefolder = (Folder) SpecmateEcoreUtil.getEObjectWithId(somefolderid, testA.eContents());
			assertNotNull(libfolder1);
			assertNotNull(libfolder2);
			assertNotNull(libfolder3);
			assertNotNull(somefolder);
			assertEquals(libname1, libfolder1.getName());
			assertEquals(libname2, libfolder2.getName());
			assertEquals(libname3, libfolder3.getName());
			assertEquals(somefoldername, somefolder.getName());
			assertEquals(libdesc1, libfolder1.getDescription());
			assertEquals(libdesc2, libfolder2.getDescription());
			assertEquals(libdesc3, libfolder3.getDescription());
			assertEquals(somefolderdesc, somefolder.getDescription());

			Folder testB = (Folder) SpecmateEcoreUtil.getEObjectWithName(projectNames[1], root);
			assertEquals(0, testB.eContents().size());
		} finally {
			if (view != null) {
				view.close();
			}
		}
	}

	private IProjectConfigService getProjectConfigService() throws SpecmateException {
		ServiceTracker<IProjectConfigService, IProjectConfigService> projectConfigTracker = new ServiceTracker<>(
				context, IProjectConfigService.class.getName(), null);
		projectConfigTracker.open();
		IProjectConfigService projectConfigService;
		try {
			projectConfigService = projectConfigTracker.waitForService(10000);
		} catch (InterruptedException e) {
			throw new SpecmateException(e);
		}
		Assert.assertNotNull(projectConfigService);
		return projectConfigService;
	}
}
