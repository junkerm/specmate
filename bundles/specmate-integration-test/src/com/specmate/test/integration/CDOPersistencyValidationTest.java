package com.specmate.test.integration;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.eclipse.emf.ecore.resource.Resource;
import org.junit.Test;

import com.mifmif.common.regex.Generex;
import com.specmate.common.SpecmateException;
import com.specmate.common.SpecmateValidationException;
import com.specmate.model.base.BaseFactory;
import com.specmate.model.base.Folder;
import com.specmate.model.requirements.CEGConnection;
import com.specmate.model.requirements.CEGNode;
import com.specmate.model.requirements.RequirementsFactory;
import com.specmate.persistency.IChange;
import com.specmate.persistency.ITransaction;
import com.specmate.persistency.validation.ConnectionValidator;
import com.specmate.persistency.validation.IDValidator;
import com.specmate.persistency.validation.NameValidator;
import com.specmate.persistency.validation.TextLengthValidator;
import com.specmate.persistency.validation.TopLevelValidator;

public class CDOPersistencyValidationTest extends IntegrationTestBase {

	public CDOPersistencyValidationTest() throws Exception {
		super();
	}

	@Test
	public void testIDValidCharacters() {
		try {
			ITransaction t = persistency.openTransaction();
			t.resetValidarors();
			t.addValidator(new IDValidator());

			t.doAndCommit(new IChange<Object>() {
				@Override
				public Object doChange() throws SpecmateException {
					Folder folder = BaseFactory.eINSTANCE.createFolder();
					folder.setId("tEst_1-2");
					t.getResource().getContents().add(folder);
					return null;
				}
			});
			t.close();
		} catch (SpecmateException | SpecmateValidationException e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void testIDInvalidCharacters() {
		Generex generex = new Generex("test-[^a-zA-Z_0-9\\-]_case");
		generex.setSeed(System.currentTimeMillis());
		ITransaction t = null;

		for (int i = 0; i < 10; i++) {
			try {
				t = persistency.openTransaction();
				t.resetValidarors();
				t.addValidator(new IDValidator());
				Resource r = t.getResource();
				t.doAndCommit(new IChange<Object>() {
					@Override
					public Object doChange() throws SpecmateException {
						Folder folder = BaseFactory.eINSTANCE.createFolder();
						String id = generex.random(1, 1);
						folder.setId(id);
						r.getContents().add(folder);
						return null;
					}
				});
				fail("Invalid id not detected");
			} catch (SpecmateException | SpecmateValidationException e) {
				// All OK
			} finally {
				if (t != null) {
					t.close();
				}
			}
		}
	}

	@Test
	public void testIDEmptyNull() {
		ITransaction t = null;

		try {
			t = persistency.openTransaction();
			t.resetValidarors();
			t.addValidator(new IDValidator());
			Resource r = t.getResource();
			t.doAndCommit(new IChange<Object>() {
				@Override
				public Object doChange() throws SpecmateException {
					Folder folder = BaseFactory.eINSTANCE.createFolder();
					r.getContents().add(folder);
					return null;
				}
			});
			fail("Null id not detected");
		} catch (SpecmateException | SpecmateValidationException e) {
			// All OK
		} finally {
			if (t != null) {
				t.close();
			}
		}
	}

	@Test
	public void testIDEmptyString() {
		ITransaction t = null;

		try {
			t = persistency.openTransaction();
			t.resetValidarors();
			t.addValidator(new IDValidator());
			Resource r = t.getResource();
			t.doAndCommit(new IChange<Object>() {
				@Override
				public Object doChange() throws SpecmateException {
					Folder folder = BaseFactory.eINSTANCE.createFolder();
					folder.setId("");
					r.getContents().add(folder);
					return null;
				}
			});
			fail("Empty id not detected");
		} catch (SpecmateException | SpecmateValidationException e) {
			// All OK
		} finally {
			t.close();
		}
	}

	@Test
	public void testIDEmptySpace() {
		ITransaction t = null;

		try {
			t = persistency.openTransaction();
			t.resetValidarors();
			t.addValidator(new IDValidator());
			Resource r = t.getResource();
			t.doAndCommit(new IChange<Object>() {
				@Override
				public Object doChange() throws SpecmateException {
					Folder folder = BaseFactory.eINSTANCE.createFolder();
					folder.setId(" ");
					r.getContents().add(folder);
					return null;
				}
			});
			fail("Space id not detected");
		} catch (SpecmateException | SpecmateValidationException e) {
			// All OK
		} finally {
			t.close();
		}
	}

	@Test
	public void testNameNull() {
		ITransaction t = null;

		try {
			t = persistency.openTransaction();
			t.resetValidarors();
			t.addValidator(new NameValidator());
			Resource r = t.getResource();
			t.doAndCommit(new IChange<Object>() {
				@Override
				public Object doChange() throws SpecmateException {
					Folder folder = BaseFactory.eINSTANCE.createFolder();
					r.getContents().add(folder);
					return null;
				}
			});
			fail("Null folder name not detected");
		} catch (SpecmateException | SpecmateValidationException e) {
			// All OK
		} finally {
			if (t != null) {
				t.close();
			}
		}
	}

	@Test
	public void testNameEmptyString() {
		ITransaction t = null;

		try {
			t = persistency.openTransaction();
			t.resetValidarors();
			t.addValidator(new NameValidator());
			Resource r = t.getResource();
			t.doAndCommit(new IChange<Object>() {
				@Override
				public Object doChange() throws SpecmateException {
					Folder folder = BaseFactory.eINSTANCE.createFolder();
					folder.setName("");
					r.getContents().add(folder);
					return null;
				}
			});
			fail("Empty folder name not detected");
		} catch (SpecmateException | SpecmateValidationException e) {
			// All OK
		} finally {
			if (t != null) {
				t.close();
			}
		}
	}

	@Test
	public void testNameSpace() {
		ITransaction t = null;

		try {
			t = persistency.openTransaction();
			t.resetValidarors();
			t.addValidator(new NameValidator());
			Resource r = t.getResource();
			t.doAndCommit(new IChange<Object>() {
				@Override
				public Object doChange() throws SpecmateException {
					Folder folder = BaseFactory.eINSTANCE.createFolder();
					folder.setName(" ");
					r.getContents().add(folder);
					return null;
				}
			});
			fail("Space folder name not detected");
		} catch (SpecmateException | SpecmateValidationException e) {
			// All OK
		} finally {
			if (t != null) {
				t.close();
			}
		}
	}

	@Test
	public void testNameInvalidChars() {
		ITransaction t = null;

		try {
			t = persistency.openTransaction();
			t.resetValidarors();
			t.addValidator(new NameValidator());
			Resource r = t.getResource();
			t.doAndCommit(new IChange<Object>() {
				@Override
				public Object doChange() throws SpecmateException {
					Folder folder = BaseFactory.eINSTANCE.createFolder();
					folder.setName("This;");
					r.getContents().add(folder);
					return null;
				}
			});
			fail("Invalid name not detected");
		} catch (SpecmateException | SpecmateValidationException e) {
			// All OK
		} finally {
			if (t != null) {
				t.close();
			}
		}
	}

	@Test
	public void testUniqueID() {
		ITransaction t = null;

		try {
			t = persistency.openTransaction();
			t.resetValidarors();
			t.addValidator(new IDValidator());
			Resource r = t.getResource();
			t.doAndCommit(new IChange<Object>() {
				@Override
				public Object doChange() throws SpecmateException {
					Folder parent = BaseFactory.eINSTANCE.createFolder();
					parent.setId("parent");
					Folder child1 = BaseFactory.eINSTANCE.createFolder();
					child1.setId("child1");
					Folder child2 = BaseFactory.eINSTANCE.createFolder();
					child2.setId("child2");

					parent.getContents().add(child1);
					parent.getContents().add(child2);
					r.getContents().add(parent);
					return null;
				}
			});
		} catch (SpecmateException | SpecmateValidationException e) {
			fail(e.getCause().getMessage());
		} finally {
			if (t != null) {
				t.close();
			}
		}
	}

	@Test
	public void testUniqueIDUnderSameParent() {
		ITransaction t = null;

		try {
			t = persistency.openTransaction();
			t.resetValidarors();
			t.addValidator(new IDValidator());
			Resource r = t.getResource();
			t.doAndCommit(new IChange<Object>() {
				@Override
				public Object doChange() throws SpecmateException {
					Folder parent = BaseFactory.eINSTANCE.createFolder();
					parent.setId("parent");
					Folder child1 = BaseFactory.eINSTANCE.createFolder();
					child1.setId("child1");
					Folder child2 = BaseFactory.eINSTANCE.createFolder();
					child2.setId("child2");

					Folder child_clone = BaseFactory.eINSTANCE.createFolder();
					child_clone.setId(child1.getId());

					parent.getContents().add(child1);
					parent.getContents().add(child2);
					parent.getContents().add(child_clone);
					r.getContents().add(parent);
					return null;
				}
			});
			fail("Add the same node twice in tree");
		} catch (SpecmateException | SpecmateValidationException e) {
			// All OK
		} finally {
			if (t != null) {
				t.close();
			}
		}
	}

	@Test
	public void testSameIDinDifferentBranch() {
		ITransaction t = null;

		try {
			t = persistency.openTransaction();
			t.resetValidarors();
			t.addValidator(new IDValidator());
			Resource r = t.getResource();
			t.doAndCommit(new IChange<Object>() {
				@Override
				public Object doChange() throws SpecmateException {
					Folder parent = BaseFactory.eINSTANCE.createFolder();
					parent.setId("parent");
					Folder child1 = BaseFactory.eINSTANCE.createFolder();
					child1.setId("child1");
					Folder child2 = BaseFactory.eINSTANCE.createFolder();
					child2.setId("child2");

					Folder grandchild = BaseFactory.eINSTANCE.createFolder();
					grandchild.setId("grandchild");

					parent.getContents().add(child1);
					parent.getContents().add(child2);
					child1.getContents().add(grandchild);
					child2.getContents().add(grandchild);
					r.getContents().add(parent);
					return null;
				}
			});
		} catch (SpecmateException | SpecmateValidationException e) {
			fail("Siblings can have children with the same id");
		} finally {
			if (t != null) {
				t.close();
			}
		}
	}

	@Test
	public void testAddIdenticalObject() {
		ITransaction t = null;

		try {
			t = persistency.openTransaction();
			t.resetValidarors();
			t.addValidator(new IDValidator());
			Resource r = t.getResource();
			t.doAndCommit(new IChange<Object>() {
				@Override
				public Object doChange() throws SpecmateException {
					Folder parent = BaseFactory.eINSTANCE.createFolder();
					parent.setId("parent");
					Folder child1 = BaseFactory.eINSTANCE.createFolder();
					child1.setId("child1");
					Folder child2 = BaseFactory.eINSTANCE.createFolder();
					child2.setId("child2");

					Folder grandchild = BaseFactory.eINSTANCE.createFolder();
					grandchild.setId("grandchild");

					parent.getContents().add(child1);
					parent.getContents().add(child2);
					child1.getContents().add(grandchild);
					child1.getContents().add(grandchild); // Adding the same object to the list has no effect, i.e. the
															// list is rather a set
					assertEquals(1, child1.getContents().size());
					r.getContents().add(parent);
					return null;
				}
			});
		} catch (SpecmateException | SpecmateValidationException e) {
			fail(e.getMessage());
		} finally {
			if (t != null) {
				t.close();
			}
		}

	}

	@Test
	public void testUniqueIDinSameBranch() {
		ITransaction t = null;

		try {
			t = persistency.openTransaction();
			t.resetValidarors();
			t.addValidator(new IDValidator());
			Resource r = t.getResource();
			t.doAndCommit(new IChange<Object>() {
				@Override
				public Object doChange() throws SpecmateException {
					Folder parent = BaseFactory.eINSTANCE.createFolder();
					parent.setId("parent");
					Folder child1 = BaseFactory.eINSTANCE.createFolder();
					child1.setId("child1");
					Folder child2 = BaseFactory.eINSTANCE.createFolder();
					child2.setId("child2");

					Folder grandchild = BaseFactory.eINSTANCE.createFolder();
					grandchild.setId("grandchild");
					Folder grandchild_clone = BaseFactory.eINSTANCE.createFolder();
					grandchild_clone.setId(grandchild.getId());

					parent.getContents().add(child1);
					parent.getContents().add(child2);
					child1.getContents().add(grandchild);
					child1.getContents().add(grandchild_clone);
					assertEquals(2, child1.getContents().size());
					r.getContents().add(parent);
					return null;
				}
			});
			fail("Could add the same node twice in tree");
		} catch (SpecmateException | SpecmateValidationException e) {
			// All OK
		} finally {
			if (t != null) {
				t.close();
			}
		}
	}

	@Test
	public void testTopLevelFolder() {
		ITransaction t = null;

		try {
			t = persistency.openTransaction();
			t.resetValidarors();
			Resource r = t.getResource();
			t.doAndCommit(new IChange<Object>() {
				@Override
				public Object doChange() throws SpecmateException {
					Folder parent = BaseFactory.eINSTANCE.createFolder();
					parent.setId("parent");
					Folder child1 = BaseFactory.eINSTANCE.createFolder();
					child1.setId("child1");
					Folder child2 = BaseFactory.eINSTANCE.createFolder();
					child2.setId("child2");

					parent.getContents().add(child1);
					parent.getContents().add(child2);
					r.getContents().add(parent);
					return null;

				}
			});
		} catch (SpecmateException | SpecmateValidationException e) {
			fail(e.getMessage());
		} finally {
			if (t != null) {
				t.close();
			}
		}

		try {
			t = persistency.openTransaction();
			t.resetValidarors();
			t.addValidator(new TopLevelValidator());
			Resource r = t.getResource();
			t.doAndCommit(new IChange<Object>() {
				@Override
				public Object doChange() throws SpecmateException {
					Folder child3 = BaseFactory.eINSTANCE.createFolder();
					child3.setId("child3");

					Folder project = (Folder) r.getContents().get(0);
					Folder topLevelFolder = (Folder) project.getContents().get(0);
					assertTrue(topLevelFolder.getContents().add(child3)); // This is allowed
					assertTrue(project.getContents().add(child3)); // This not
					return null;
				}
			});
			fail("Top level folder violation not detected");
		} catch (SpecmateException | SpecmateValidationException e) {
			// All OK
		} finally {
			if (t != null) {
				t.close();
			}
		}
	}

	@Test
	public void testTextLengthTooLong() {
		ITransaction t = null;

		try {
			t = persistency.openTransaction();
			t.resetValidarors();
			t.addValidator(new TextLengthValidator());
			Resource r = t.getResource();
			t.doAndCommit(new IChange<Object>() {
				@Override
				public Object doChange() throws SpecmateException {
					Folder f = BaseFactory.eINSTANCE.createFolder();
					StringBuilder s = new StringBuilder();
					for (int i = 0; i < TextLengthValidator.MAX_LENGTH + 1; i++) {
						s.append(".");
					}
					f.setDescription(s.toString());
					r.getContents().add(f);
					return null;
				}
			});
			fail("Could add object with too large text content");
		} catch (SpecmateException | SpecmateValidationException e) {
			// All OK
		} finally {
			if (t != null) {
				t.close();
			}
		}
	}

	@Test
	public void testMissingSourceTarget() {
		ITransaction t = null;

		try {
			t = persistency.openTransaction();
			t.resetValidarors();
			t.addValidator(new ConnectionValidator());
			Resource r = t.getResource();
			t.doAndCommit(new IChange<Object>() {
				@Override
				public Object doChange() throws SpecmateException {
					CEGConnection c = RequirementsFactory.eINSTANCE.createCEGConnection();
					c.setId("c1");
					c.setDescription("c1");
					r.getContents().add(c);
					return null;
				}
			});
			fail("Could store connection without source or target");
		} catch (SpecmateException | SpecmateValidationException e) {
			// All OK
		} finally {
			if (t != null) {
				t.close();
			}
		}
	}

	@Test
	public void testMissingSource() {
		ITransaction t = null;

		try {
			t = persistency.openTransaction();
			t.resetValidarors();
			t.addValidator(new ConnectionValidator());
			Resource r = t.getResource();
			t.doAndCommit(new IChange<Object>() {
				@Override
				public Object doChange() throws SpecmateException {
					CEGConnection c = RequirementsFactory.eINSTANCE.createCEGConnection();
					c.setId("c1");
					c.setDescription("c1");
					CEGNode n = RequirementsFactory.eINSTANCE.createCEGNode();
					n.setId("n1");
					n.setDescription("n1");
					c.setTarget(n);
					r.getContents().add(c);
					r.getContents().add(n);
					return null;
				}
			});
			fail("Could store connection without source");
		} catch (SpecmateException | SpecmateValidationException e) {
			assertTrue(e.getMessage().contains("source"));
		} finally {
			if (t != null) {
				t.close();
			}
		}
	}

	@Test
	public void testMissingTarget() {
		ITransaction t = null;

		try {
			t = persistency.openTransaction();
			t.resetValidarors();
			t.addValidator(new ConnectionValidator());
			Resource r = t.getResource();
			t.doAndCommit(new IChange<Object>() {
				@Override
				public Object doChange() throws SpecmateException {
					CEGConnection c = RequirementsFactory.eINSTANCE.createCEGConnection();
					c.setId("c1");
					c.setDescription("c1");
					CEGNode n = RequirementsFactory.eINSTANCE.createCEGNode();
					n.setId("n1");
					n.setDescription("n1");
					c.setSource(n);
					r.getContents().add(c);
					r.getContents().add(n);
					return null;
				}
			});
			fail("Could store connection without target");
		} catch (SpecmateException | SpecmateValidationException e) {
			assertTrue(e.getMessage().contains("target"));
		} finally {
			if (t != null) {
				t.close();
			}
		}
	}

	@Test
	public void testConnection() {
		ITransaction t = null;

		try {
			t = persistency.openTransaction();
			t.resetValidarors();
			t.addValidator(new ConnectionValidator());
			Resource r = t.getResource();
			t.doAndCommit(new IChange<Object>() {
				@Override
				public Object doChange() throws SpecmateException {
					CEGConnection c = RequirementsFactory.eINSTANCE.createCEGConnection();
					c.setId("c1");
					c.setDescription("c1");
					CEGNode s = RequirementsFactory.eINSTANCE.createCEGNode();
					s.setId("s");
					s.setDescription("s");
					c.setSource(s);
					CEGNode t = RequirementsFactory.eINSTANCE.createCEGNode();
					t.setId("t");
					t.setDescription("t");
					c.setTarget(t);
					r.getContents().add(c);
					r.getContents().add(s);
					r.getContents().add(t);
					return null;
				}
			});
		} catch (SpecmateException | SpecmateValidationException e) {
			fail(e.getCause().getMessage());
		} finally {
			if (t != null) {
				t.close();
			}
		}
	}
}
