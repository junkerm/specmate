package com.specmate.connectors.test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collection;

import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.osgi.service.log.LogService;

import com.specmate.common.exception.SpecmateException;
import com.specmate.connectors.api.IRequirementsSource;
import com.specmate.connectors.internal.ConnectorTask;
import com.specmate.model.base.BaseFactory;
import com.specmate.model.base.Folder;
import com.specmate.model.base.IContainer;
import com.specmate.model.requirements.Requirement;
import com.specmate.model.requirements.RequirementsFactory;
import com.specmate.persistency.IChange;
import com.specmate.persistency.ITransaction;

public class ConnectorTaskTest {

	public ConnectorTaskTest() throws Exception {
		super();
	}

	@Test
	public void testConnectorService() throws SpecmateException {

		IRequirementsSource reqSource = new TestRequirementSource();
		ITransaction transactoin = mock(ITransaction.class);
		Resource resource = mock(Resource.class);
		BasicEList<EObject> contentList = new BasicEList<EObject>();
		when(resource.getContents()).thenReturn(contentList);
		when(transactoin.getResource()).thenReturn(resource);
		when(transactoin.doAndCommit(Mockito.any(IChange.class))).thenAnswer(new Answer<Void>() {

			@Override
			public Void answer(InvocationOnMock inv) throws Throwable {
				IChange change = inv.getArgument(0);
				change.doChange();
				return null;
			}

		});

		ConnectorTask task = new ConnectorTask(Arrays.asList(reqSource), transactoin, mock(LogService.class));
		task.run();

		Folder folder = (Folder) contentList.get(0);
		assertEquals(reqSource.getId(), folder.getId());

		assertEquals(reqSource.getId(), folder.getId());
		assertEquals(reqSource.getId(), folder.getName());

		Folder subfolder = (Folder) folder.getContents().get(0);
		assertEquals(TestRequirementSource.FOLDER_NAME, subfolder.getId());
		assertEquals(TestRequirementSource.FOLDER_NAME, subfolder.getName());

		Requirement req = (Requirement) subfolder.getContents().get(0);
		assertEquals("id1", req.getId());
		assertEquals(TestRequirementSource.REQ_NAME + "   ", req.getName());

	}

	private class TestRequirementSource implements IRequirementsSource {
		public static final String REQ_NAME = "req";
		private static final String BAD_CHARS = ",|;";
		public static final String FOLDER_NAME = "folder";

		@Override
		public Collection<Requirement> getRequirements() throws SpecmateException {
			Requirement req1 = RequirementsFactory.eINSTANCE.createRequirement();
			req1.setName(REQ_NAME + BAD_CHARS);
			req1.setId("id1");
			req1.setExtId("id1");

			Requirement req2 = RequirementsFactory.eINSTANCE.createRequirement();
			req2.setName(null);
			req2.setId("id2");
			req1.setExtId("id2");

			Requirement req3 = RequirementsFactory.eINSTANCE.createRequirement();
			req3.setName("");
			req3.setId("id3");
			req1.setExtId("id3");

			return Arrays.asList(req1, req2, req3);
		}

		@Override
		public String getId() {
			return "testSource";
		}

		@Override
		public IContainer getContainerForRequirement(Requirement requirement) throws SpecmateException {
			Folder folder = BaseFactory.eINSTANCE.createFolder();
			folder.setId(FOLDER_NAME);
			folder.setName(FOLDER_NAME);
			return folder;
		}

		@Override
		public boolean authenticate(String username, String password) throws SpecmateException {
			return true;
		}
	}

}
