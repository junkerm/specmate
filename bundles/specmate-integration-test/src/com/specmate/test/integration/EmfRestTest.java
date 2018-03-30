package com.specmate.test.integration;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.ws.rs.core.Response.Status;

import org.json.JSONObject;
import org.junit.Assert;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.service.log.LogService;
import org.osgi.util.tracker.ServiceTracker;

import com.specmate.common.RestClient;
import com.specmate.common.RestResult;
import com.specmate.common.SpecmateException;
import com.specmate.emfjson.EMFJsonSerializer;
import com.specmate.model.base.BasePackage;
import com.specmate.model.processes.ProcessesPackage;
import com.specmate.model.requirements.NodeType;
import com.specmate.model.requirements.RequirementsPackage;
import com.specmate.model.testspecification.TestspecificationPackage;
import com.specmate.persistency.IPersistencyService;
import com.specmate.persistency.ITransaction;
import com.specmate.persistency.IView;

public abstract class EmfRestTest {
	static final String ID_KEY = "id";
	static final String REST_ENDPOINT = "http://localhost:8088/services/rest";
	static final String NSURI_KEY = EMFJsonSerializer.KEY_NSURI;
	static final String ECLASS = EMFJsonSerializer.KEY_ECLASS;
	static BundleContext context;
	static IPersistencyService persistency;
	static IView view;
	static LogService logService;
	static RestClient restClient;
	private static int counter = 0;

	public EmfRestTest() throws Exception {
		if (context == null) {
			context = FrameworkUtil.getBundle(EmfRestTest.class).getBundleContext();
		}
		if (persistency == null) {
			persistency = getPersistencyService();
		}
		if (view == null) {
			view = persistency.openView();
		}
		if (logService == null) {
			logService = getLogger();
		}
		if (restClient == null) {
			restClient = new RestClient(REST_ENDPOINT, logService);
		}

		Thread.sleep(2000);
		clearPersistency();
	}

	private LogService getLogger() throws InterruptedException {
		ServiceTracker<LogService, LogService> logTracker = new ServiceTracker<>(context, LogService.class.getName(),
				null);
		logTracker.open();
		LogService logService = logTracker.waitForService(10000);
		Assert.assertNotNull(logService);
		return logService;
	}

	protected void clearPersistency() throws SpecmateException {
		ITransaction transaction = persistency.openTransaction();
		transaction.getResource().getContents().clear();
		transaction.commit();
	}

	private IPersistencyService getPersistencyService() throws InterruptedException, SpecmateException {
		ServiceTracker<IPersistencyService, IPersistencyService> persistencyTracker = new ServiceTracker<>(context,
				IPersistencyService.class.getName(), null);
		persistencyTracker.open();
		IPersistencyService persistency = persistencyTracker.waitForService(10000);
		Assert.assertNotNull(persistency);
		return persistency;
	}

	protected JSONObject createTestFolder(String folderId, String folderName) {
		JSONObject folder = new JSONObject();
		folder.put(NSURI_KEY, BasePackage.eNS_URI);
		folder.put(ECLASS, BasePackage.Literals.FOLDER.getName());
		folder.put(BasePackage.Literals.IID__ID.getName(), folderId);
		folder.put(BasePackage.Literals.INAMED__NAME.getName(), folderId);
		return folder;
	}

	protected JSONObject createTestFolder() {
		String folderName = "TestFolder" + counter++;
		return createTestFolder(folderName, folderName);
	}

	protected JSONObject createTestRequirement() {
		String requirementsName = "TestRequirement" + counter++;
		JSONObject requirement = new JSONObject();
		requirement.put(NSURI_KEY, RequirementsPackage.eNS_URI);
		requirement.put(ECLASS, RequirementsPackage.Literals.REQUIREMENT.getName());
		requirement.put(BasePackage.Literals.INAMED__NAME.getName(), requirementsName);
		requirement.put(BasePackage.Literals.IID__ID.getName(), requirementsName);
		requirement.put(BasePackage.Literals.IDESCRIBED__DESCRIPTION.getName(), "description");
		requirement.put(BasePackage.Literals.IEXTERNAL__EXT_ID.getName(), "extid123");
		requirement.put(BasePackage.Literals.IEXTERNAL__EXT_ID2.getName(), "extid456");
		requirement.put(BasePackage.Literals.IEXTERNAL__LIVE.getName(), false);
		requirement.put(RequirementsPackage.Literals.REQUIREMENT__NUMBER_OF_TESTS.getName(), "0");
		requirement.put(RequirementsPackage.Literals.REQUIREMENT__TAC.getName(), "tac");
		requirement.put(RequirementsPackage.Literals.REQUIREMENT__IMPLEMENTING_UNIT.getName(), "unit1");
		requirement.put(RequirementsPackage.Literals.REQUIREMENT__IMPLEMENTING_BO_TEAM.getName(), "bo2");
		requirement.put(RequirementsPackage.Literals.REQUIREMENT__IMPLEMENTING_IT_TEAM.getName(), "it1");
		requirement.put(RequirementsPackage.Literals.REQUIREMENT__PLANNED_RELEASE.getName(), "release1");
		requirement.put(RequirementsPackage.Literals.REQUIREMENT__STATUS.getName(), "status");
		requirement.put(RequirementsPackage.Literals.REQUIREMENT__IS_REGRESSION_REQUIREMENT.getName(), true);
		return requirement;
	}

	protected JSONObject createTestCegModel() {
		String cegName = "TestCeg" + counter++;
		JSONObject ceg = new JSONObject();
		ceg.put(NSURI_KEY, RequirementsPackage.eNS_URI);
		ceg.put(ECLASS, RequirementsPackage.Literals.CEG_MODEL.getName());
		ceg.put(BasePackage.Literals.IID__ID.getName(), cegName);
		ceg.put(BasePackage.Literals.INAMED__NAME.getName(), cegName);
		return ceg;
	}

	protected JSONObject createTestProcessModel() {
		String processName = "TestProcess" + counter++;
		JSONObject process = new JSONObject();
		process.put(NSURI_KEY, ProcessesPackage.eNS_URI);
		process.put(ECLASS, ProcessesPackage.Literals.PROCESS.getName());
		process.put(BasePackage.Literals.IID__ID.getName(), processName);
		process.put(BasePackage.Literals.INAMED__NAME.getName(), processName);
		return process;
	}

	protected JSONObject createTestCegNode() {
		String cegName = "TestCegNode" + counter++;
		JSONObject cegNode = new JSONObject();
		cegNode.put(NSURI_KEY, RequirementsPackage.eNS_URI);
		cegNode.put(ECLASS, RequirementsPackage.Literals.CEG_NODE.getName());
		cegNode.put(BasePackage.Literals.IID__ID.getName(), cegName);
		cegNode.put(BasePackage.Literals.INAMED__NAME.getName(), cegName);
		cegNode.put(RequirementsPackage.Literals.CEG_NODE__VARIABLE.getName(), cegName);
		cegNode.put(RequirementsPackage.Literals.CEG_NODE__CONDITION.getName(), "5");
		cegNode.put(RequirementsPackage.Literals.CEG_NODE__TYPE.getName(), NodeType.OR.getLiteral());
		return cegNode;
	}

	protected JSONObject createTestStartNode() {
		String nodeName = "TestStartNode" + counter++;
		JSONObject startNode = new JSONObject();
		startNode.put(NSURI_KEY, ProcessesPackage.eNS_URI);
		startNode.put(ECLASS, ProcessesPackage.Literals.PROCESS_START.getName());
		startNode.put(BasePackage.Literals.IID__ID.getName(), nodeName);
		startNode.put(BasePackage.Literals.INAMED__NAME.getName(), nodeName);
		return startNode;
	}

	protected JSONObject createTestEndNode() {
		String nodeName = "TestEndNode" + counter++;
		JSONObject endNode = new JSONObject();
		endNode.put(NSURI_KEY, ProcessesPackage.eNS_URI);
		endNode.put(ECLASS, ProcessesPackage.Literals.PROCESS_END.getName());
		endNode.put(BasePackage.Literals.IID__ID.getName(), nodeName);
		endNode.put(BasePackage.Literals.INAMED__NAME.getName(), nodeName);
		return endNode;
	}

	protected JSONObject createTestStepNode() {
		String nodeName = "TestActivityNode" + counter++;
		JSONObject stepNode = new JSONObject();
		stepNode.put(NSURI_KEY, ProcessesPackage.eNS_URI);
		stepNode.put(ECLASS, ProcessesPackage.Literals.PROCESS_STEP.getName());
		stepNode.put(BasePackage.Literals.IID__ID.getName(), nodeName);
		stepNode.put(BasePackage.Literals.INAMED__NAME.getName(), nodeName);
		return stepNode;
	}

	protected JSONObject createTestDecisionNode() {
		String nodeName = "TestDecisionNode" + counter++;
		JSONObject decisionNode = new JSONObject();
		decisionNode.put(NSURI_KEY, ProcessesPackage.eNS_URI);
		decisionNode.put(ECLASS, ProcessesPackage.Literals.PROCESS_DECISION.getName());
		decisionNode.put(BasePackage.Literals.IID__ID.getName(), nodeName);
		decisionNode.put(BasePackage.Literals.INAMED__NAME.getName(), nodeName);
		return decisionNode;
	}

	protected JSONObject createTestCEGConnection(JSONObject node1, JSONObject node2) {
		String connectionName = "TestConnection" + counter++;
		JSONObject connection = new JSONObject();
		connection.put(NSURI_KEY, RequirementsPackage.eNS_URI);
		connection.put(ECLASS, RequirementsPackage.Literals.CEG_CONNECTION.getName());
		connection.put(BasePackage.Literals.IID__ID.getName(), connectionName);
		connection.put(BasePackage.Literals.IMODEL_CONNECTION__SOURCE.getName(), EmfRestTestUtil.proxy(node1));
		connection.put(BasePackage.Literals.IMODEL_CONNECTION__TARGET.getName(), EmfRestTestUtil.proxy(node2));
		connection.put(RequirementsPackage.Literals.CEG_CONNECTION__NEGATE.getName(), true);
		return connection;
	}

	protected JSONObject createTestStepConnection(JSONObject node1, JSONObject node2) {
		String connectionName = "TestProcessStepConnection" + counter++;
		JSONObject connection = new JSONObject();
		connection.put(NSURI_KEY, ProcessesPackage.eNS_URI);
		connection.put(ECLASS, ProcessesPackage.Literals.PROCESS_CONNECTION.getName());
		connection.put(BasePackage.Literals.IID__ID.getName(), connectionName);
		connection.put(BasePackage.Literals.IMODEL_CONNECTION__SOURCE.getName(), EmfRestTestUtil.proxy(node1));
		connection.put(BasePackage.Literals.IMODEL_CONNECTION__TARGET.getName(), EmfRestTestUtil.proxy(node2));
		return connection;
	}

	protected JSONObject createTestDecisionConnection(JSONObject node1, JSONObject node2) {
		String connectionName = "TestProcessDecisionConnection" + counter++;
		JSONObject connection = new JSONObject();
		connection.put(NSURI_KEY, ProcessesPackage.eNS_URI);
		connection.put(ECLASS, ProcessesPackage.Literals.PROCESS_CONNECTION.getName());
		connection.put(BasePackage.Literals.IID__ID.getName(), connectionName);
		connection.put(BasePackage.Literals.IMODEL_CONNECTION__SOURCE.getName(), EmfRestTestUtil.proxy(node1));
		connection.put(BasePackage.Literals.IMODEL_CONNECTION__TARGET.getName(), EmfRestTestUtil.proxy(node2));
		connection.put(ProcessesPackage.Literals.PROCESS_CONNECTION__CONDITION.getName(), "condition" + counter++);
		return connection;
	}

	protected JSONObject createTestTestSpecification() {
		String testSpecName = "TestSpecification" + counter++;
		JSONObject testSpecification = new JSONObject();
		testSpecification.put(NSURI_KEY, TestspecificationPackage.eNS_URI);
		testSpecification.put(ECLASS, TestspecificationPackage.Literals.TEST_SPECIFICATION.getName());
		testSpecification.put(BasePackage.Literals.IID__ID.getName(), testSpecName);
		testSpecification.put(BasePackage.Literals.INAMED__NAME.getName(), testSpecName);
		return testSpecification;
	}

	protected String buildUrl(String service, String... segments) {
		StringBuilder builder = new StringBuilder();
		for (String segment : segments) {
			builder.append("/").append(segment);
		}
		return builder.toString() + "/" + service;
	}

	protected String getId(JSONObject requirement) {
		return requirement.getString(ID_KEY);
	}

	protected JSONObject postFolderToRoot() {
		JSONObject folder = createTestFolder();
		return postObject(folder);
	}

	protected JSONObject postFolder(String... segments) {
		JSONObject folder = createTestFolder();
		return postObject(folder, segments);
	}

	protected JSONObject postRequirementToRoot() {
		return postRequirement();
	}

	protected JSONObject postRequirement(String... segments) {
		JSONObject requirement = createTestRequirement();
		return postObject(requirement, segments);
	}

	protected JSONObject postCEG(String... segments) {
		JSONObject cegModel = createTestCegModel();
		return postObject(cegModel, segments);
	}

	protected JSONObject postProcess(String... segments) {
		JSONObject processModel = createTestProcessModel();
		return postObject(processModel, segments);
	}

	protected JSONObject postCEGNode(String... segments) {
		JSONObject cegNode = createTestCegNode();
		return postObject(cegNode, segments);
	}

	protected JSONObject postStartNode(String... segments) {
		JSONObject node = createTestStartNode();
		return postObject(node, segments);
	}

	protected JSONObject postEndNode(String... segments) {
		JSONObject node = createTestEndNode();
		return postObject(node, segments);
	}

	protected JSONObject postStepNode(String... segments) {
		JSONObject node = createTestStepNode();
		return postObject(node, segments);
	}

	protected void setStepTrace(JSONObject step, JSONObject... requirements) {
		step.put(BasePackage.Literals.ITRACING_ELEMENT__TRACES_TO.getName(),
				Stream.of(requirements).map(r -> EmfRestTestUtil.proxy(r)).collect(Collectors.toList()));
	}

	protected JSONObject postDecisionNode(String... segments) {
		JSONObject node = createTestDecisionNode();
		return postObject(node, segments);
	}

	protected JSONObject postCEGConnection(JSONObject node1, JSONObject node2, String... segments) {
		JSONObject cegConnection = createTestCEGConnection(node1, node2);
		return postObject(cegConnection, segments);
	}

	protected JSONObject postStepConnection(JSONObject node1, JSONObject node2, String... segments) {
		JSONObject stepConnection = createTestStepConnection(node1, node2);
		return postObject(stepConnection, segments);
	}

	protected JSONObject postDecisionConnection(JSONObject node1, JSONObject node2, String... segments) {
		JSONObject decisionConnection = createTestDecisionConnection(node1, node2);
		return postObject(decisionConnection, segments);
	}

	protected JSONObject postTestSpecification(String... segments) {
		JSONObject testSpecification = createTestTestSpecification();
		return postObject(testSpecification, segments);
	}

	protected JSONObject postObject(JSONObject object, String... segments) {
		return postObject(Status.OK.getStatusCode(), object, segments);
	}

	protected JSONObject postObject(int statusCode, JSONObject object, String... segments) {
		String postUrl = listUrl(segments);
		logService.log(LogService.LOG_DEBUG, "Posting the object " + object.toString() + " to url " + postUrl);
		RestResult<JSONObject> result = restClient.post(postUrl, object);
		Assert.assertEquals(result.getResponse().getStatus(), statusCode);
		return object;
	}

	protected void updateObject(JSONObject object, String... segments) {
		String updateUrl = detailUrl(segments);
		logService.log(LogService.LOG_DEBUG, "Updateing the object " + object.toString() + " at url " + updateUrl);
		RestResult<JSONObject> putResult = restClient.put(updateUrl, object);
		Assert.assertEquals(Status.OK.getStatusCode(), putResult.getResponse().getStatus());
	}

	protected JSONObject getObject(int statusCode, String... segments) {
		String retrieveUrl = detailUrl(segments);
		RestResult<JSONObject> getResult = restClient.get(retrieveUrl);
		JSONObject retrievedObject = getResult.getPayload();
		if (retrievedObject != null) {
			logService.log(LogService.LOG_DEBUG,
					"Retrieved the object " + retrievedObject.toString() + " from url " + retrieveUrl);
		} else {
			logService.log(LogService.LOG_DEBUG, "Empty result from url " + retrieveUrl);
		}
		Assert.assertEquals(statusCode, getResult.getResponse().getStatus());
		return retrievedObject;
	}

	protected JSONObject getObject(String... segments) {
		return getObject(Status.OK.getStatusCode(), segments);
	}

	protected void deleteObject(String... segments) {
		// Delete folder
		String deleteUrl = deleteUrl(segments);
		logService.log(LogService.LOG_DEBUG, "Deleting object with URL " + deleteUrl);
		RestResult<Object> deleteResult = restClient.delete(deleteUrl);
		Assert.assertEquals(Status.OK.getStatusCode(), deleteResult.getResponse().getStatus());
	}

	protected String listUrl(String... segments) {
		return buildUrl("list", segments);
	}

	protected String detailUrl(String... segments) {
		return buildUrl("details", segments);
	}

	protected String deleteUrl(String... segments) {
		return buildUrl("delete", segments);
	}
}