package com.specmate.testspecification.internal.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.jgrapht.DirectedGraph;
import org.jgrapht.GraphPath;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.DirectedMultigraph;
import org.jgrapht.graph.GraphWalk;

import com.specmate.common.AssertUtil;
import com.specmate.common.exception.SpecmateException;
import com.specmate.model.base.IContainer;
import com.specmate.model.base.IModelNode;
import com.specmate.model.processes.Process;
import com.specmate.model.processes.ProcessConnection;
import com.specmate.model.processes.ProcessDecision;
import com.specmate.model.processes.ProcessEnd;
import com.specmate.model.processes.ProcessStart;
import com.specmate.model.processes.ProcessStep;
import com.specmate.model.support.util.SpecmateEcoreUtil;
import com.specmate.model.testspecification.ParameterAssignment;
import com.specmate.model.testspecification.ParameterType;
import com.specmate.model.testspecification.TestCase;
import com.specmate.model.testspecification.TestParameter;
import com.specmate.model.testspecification.TestSpecification;
import com.specmate.model.testspecification.TestStep;
import com.specmate.model.testspecification.TestspecificationFactory;

/** Generates tests from a process model */
public class ProcessTestCaseGenerator2 extends TestCaseGeneratorBase<Process, IModelNode> {

	/** All connections of the process */
	private List<ProcessConnection> connections;

	/**
	 * Map from parameter names to parameters of the resulting test case
	 * specification
	 */
	private Map<String, TestParameter> testParameters = new HashMap<>();

	public ProcessTestCaseGenerator2(TestSpecification specification) {
		super(specification, Process.class, IModelNode.class);
		connections = SpecmateEcoreUtil.pickInstancesOf(model.getContents(), ProcessConnection.class);
	}

	@Override
	protected void generateParameters() {
		List<ProcessDecision> decisions = SpecmateEcoreUtil.pickInstancesOf(model.getContents(), ProcessDecision.class);
		for (ProcessDecision decision : decisions) {
			String testParameterName = decision.getName();
			TestParameter testParameter = createTestParameter(testParameterName, ParameterType.INPUT);
			testParameters.put(testParameterName, testParameter);
			specification.getContents().add(testParameter);
		}

		List<ProcessConnection> connections = SpecmateEcoreUtil.pickInstancesOf(model.getContents(),
				ProcessConnection.class);
		for (ProcessConnection connection : connections) {
			if (connection.getSource() instanceof ProcessDecision) {
				// in this case the test parameter is defined by the decision node
				continue;
			}
			String testParameterName = extractVariableAndConditionFromExpression(connection.getCondition()).name;
			TestParameter testParameter = createTestParameter(testParameterName, ParameterType.INPUT);
			testParameters.put(testParameterName, testParameter);
			specification.getContents().add(testParameter);
		}

		List<ProcessStep> steps = SpecmateEcoreUtil.pickInstancesOf(model.getContents(), ProcessStep.class);
		for (ProcessStep step : steps) {
			String expectedOutcome = step.getExpectedOutcome();
			if (!StringUtils.isEmpty(expectedOutcome)) {
				String variable = extractVariableAndConditionFromExpression(expectedOutcome).name;
				TestParameter testParameter = createTestParameter(variable, ParameterType.OUTPUT);
				testParameters.put(variable, testParameter);
				specification.getContents().add(testParameter);
			}
		}
	}

	private AssigmentValues extractVariableAndConditionFromExpression(String outcome) {
		// split only at first occurence of "=", result will have length <= 2
		String[] splitted = outcome.split("=", 2);
		String variable = splitted[0];
		String condition = splitted.length > 1 && !StringUtils.isEmpty(splitted[1]) ? splitted[1] : "is present";
		return new AssigmentValues(variable, condition);
	}

	@Override
	protected void generateTestCases() throws SpecmateException {
		List<GraphPath<IModelNode, ProcessConnection>> allPaths = getAllPaths();
		List<GraphPath<IModelNode, ProcessConnection>> filteredPaths = filterDuplicatePaths(allPaths);
		createTestCases(filteredPaths);

	}

	private void createTestCases(List<GraphPath<IModelNode, ProcessConnection>> paths) {
		for (GraphPath<IModelNode, ProcessConnection> path : paths) {
			List<AssigmentValues> variableConditionList = new ArrayList<>();
			for (int i = 0; i < path.getVertexList().size(); i++) {
				IModelNode currentNode = path.getVertexList().get(i);
				ProcessConnection currentConnection = null;
				if (i < path.getEdgeList().size()) {
					currentConnection = path.getEdgeList().get(i);
				}

				if (currentNode instanceof ProcessDecision) {
					if (!StringUtils.isEmpty(currentNode.getName())) {
						String condition = currentConnection.getCondition();
						if (StringUtils.isEmpty(condition)) {
							condition = "is present";
						}
						variableConditionList
								.add(new AssigmentValues(currentNode.getName(), condition, ParameterType.INPUT));
					}
				} else {
					if (currentNode instanceof ProcessStep) {
						ProcessStep step = (ProcessStep) currentNode;
						if (!StringUtils.isEmpty(step.getExpectedOutcome())) {
							AssigmentValues varCond = extractVariableAndConditionFromExpression(
									step.getExpectedOutcome());
							varCond.type = ParameterType.OUTPUT;
							variableConditionList.add(varCond);
						}
					}
					if (!StringUtils.isEmpty(currentConnection.getCondition())) {
						AssigmentValues varCond = extractVariableAndConditionFromExpression(
								currentConnection.getCondition());
						varCond.type = ParameterType.INPUT;
						variableConditionList.add(varCond);
					}

				}
			}
			List<String> seenParameterNames = new ArrayList<>();
			TestCase tc = TestspecificationFactory.eINSTANCE.createTestCase();
			for (AssigmentValues varCond : variableConditionList) {
				String variable = varCond.name;
				int i = 1;
				while (seenParameterNames.contains(variable)) {
					i++;
					variable = variable + " " + i;
				}
				TestParameter parameter = testParameters.get(variable);
				if (parameter == null) {
					parameter = createTestParameter(variable, varCond.type);
					testParameters.put(parameter.getName(), parameter);
					specification.getContents().add(parameter);
				}
				ParameterAssignment assignment = createParameterAssignment(tc, parameter, varCond.condition);
				tc.getContents().add(assignment);
			}
			specification.getContents().add(tc);
		}
	}

	private List<GraphPath<IModelNode, ProcessConnection>> filterDuplicatePaths(
			List<GraphPath<IModelNode, ProcessConnection>> paths) {
		Set<GraphPath<IModelNode, ProcessConnection>> obsoletePaths = new HashSet<>();

		for (int i = 0; i < paths.size(); i++) {
			GraphPath<IModelNode, ProcessConnection> path1 = paths.get(i);
			Set<ProcessConnection> connectionSet1 = new HashSet<>(path1.getEdgeList());
			for (int j = 0; j < paths.size(); j++) {
				GraphPath<IModelNode, ProcessConnection> path2 = paths.get(j);

				if (i == j || obsoletePaths.contains(path2)) {
					continue;
				}

				Set<ProcessConnection> connectionSet2 = new HashSet<>(path2.getEdgeList());
				connectionSet1.removeAll(connectionSet2);

				if (connectionSet1.isEmpty()) {
					obsoletePaths.add(path1);
					break;
				}
			}
		}

		List<GraphPath<IModelNode, ProcessConnection>> filteredPaths = paths.stream()
				.filter((GraphPath<IModelNode, ProcessConnection> path) -> !obsoletePaths.contains(path))
				.collect(Collectors.toList());
		return filteredPaths;
	}

	private String makePrecondition(ProcessConnection connection) {
		return "Establish precondition: " + connection.getCondition();
	}

	private String makeAction(IModelNode node) {
		return node.getName();
	}

	private String makeAction(ProcessConnection connection) {
		if (!hasCondition(connection)) {
			return "";
		}
		return "Establish condition: " + connection.getCondition();
	}

	private String makeCheck(ProcessStep step, ProcessConnection connection) {
		List<String> checkParts = new ArrayList<>();

		if (hasExpectedOutcome(step)) {
			checkParts.add(step.getExpectedOutcome());
		}

		if (hasCondition(connection)) {
			checkParts.add(connection.getCondition());
		}
		return StringUtils.join(checkParts, ", ");
	}

	private String makeCheck(ProcessConnection connection) {

		if (hasCondition(connection)) {
			return connection.getCondition();
		}
		return "";
	}

	private boolean hasCondition(ProcessConnection connection) {
		return connection.getCondition() != null && !connection.getCondition().equals("");
	}

	private boolean hasExpectedOutcome(ProcessStep step) {
		return step.getExpectedOutcome() != null && !step.getExpectedOutcome().equals("");
	}

	private void createTestStep(String action, String check, int position, IContainer procedure,
			TestParameter testParameter, String description) {
		TestStep testStep = TestspecificationFactory.eINSTANCE.createTestStep();
		testStep.setName(action);
		testStep.setDescription(description);
		testStep.setPosition(position);
		testStep.setExpectedOutcome(check);
		testStep.setId(SpecmateEcoreUtil.getIdForChild(procedure, testStep.eClass()));
		if (testParameter != null) {
			testStep.getReferencedTestParameters().add(testParameter);
		}
		procedure.getContents().add(testStep);
	}

	private List<GraphPath<IModelNode, ProcessConnection>> getAllPaths() {
		Set<ProcessStart> processStarts = getStartNodes();
		Set<ProcessEnd> processEnds = getEndNodes();
		DirectedGraph<IModelNode, ProcessConnection> graph = getGraph();

		// AllDirectedPaths<IModelNode, ProcessConnection> allDirectedPaths =
		// new AllDirectedPaths<>(graph);
		// List<GraphPath<IModelNode, ProcessConnection>> allPaths =
		// allDirectedPaths.getAllPaths(processStarts, processEnds, true, null);

		List<GraphPath<IModelNode, ProcessConnection>> allPaths = new ArrayList<>();
		Set<ProcessConnection> uncoveredConnections = new HashSet<>(graph.edgeSet());

		IModelNode startNode = processStarts.stream().findAny().get();
		while (uncoveredConnections.stream().findAny().isPresent()) {
			ProcessConnection currentUncoveredConnection = uncoveredConnections.stream().findAny().get();
			IModelNode sourceNode = currentUncoveredConnection.getSource();
			IModelNode targetNode = currentUncoveredConnection.getTarget();
			DijkstraShortestPath<IModelNode, ProcessConnection> dsp = new DijkstraShortestPath<>(graph);

			GraphPath<IModelNode, ProcessConnection> startPath = dsp.getPath(startNode, sourceNode);
			GraphPath<IModelNode, ProcessConnection> endPath = null;
			IModelNode bestEndNode = null;
			int minimalEndPathLength = Integer.MAX_VALUE;
			for (IModelNode endNode : processEnds) {
				GraphPath<IModelNode, ProcessConnection> currentEndPath = dsp.getPath(targetNode, endNode);
				if (currentEndPath != null) {
					int currentEndPathLength = currentEndPath.getLength();
					if (currentEndPathLength < minimalEndPathLength) {
						minimalEndPathLength = currentEndPathLength;
						endPath = currentEndPath;
						bestEndNode = endNode;
					}
				}
			}

			AssertUtil.assertNotNull(endPath, "Could not find path to end node!");

			List<ProcessConnection> connections = new ArrayList<>();
			connections.addAll(startPath.getEdgeList());
			connections.add(currentUncoveredConnection);
			connections.addAll(endPath.getEdgeList());
			GraphPath<IModelNode, ProcessConnection> constructedPath = new GraphWalk<>(graph, startNode, bestEndNode,
					connections, 0d);
			allPaths.add(constructedPath);
			uncoveredConnections.removeAll(connections);
		}

		return allPaths;
	}

	private Set<ProcessStart> getStartNodes() {
		Set<ProcessStart> startNodes = SpecmateEcoreUtil.uniqueInstancesOf(nodes, ProcessStart.class);
		AssertUtil.assertEquals(startNodes.size(), 1, "Number of start nodes in process is different to 1.");
		return startNodes;
	}

	private Set<ProcessEnd> getEndNodes() {
		Set<ProcessEnd> endNodes = SpecmateEcoreUtil.uniqueInstancesOf(nodes, ProcessEnd.class);
		AssertUtil.assertTrue(endNodes.size() > 0, "No end nodes in process were found");
		return endNodes;
	}

	private DirectedGraph<IModelNode, ProcessConnection> getGraph() {
		DirectedGraph<IModelNode, ProcessConnection> graph = new DirectedMultigraph<>(ProcessConnection.class);
		for (IModelNode node : nodes) {
			graph.addVertex(node);
		}

		for (ProcessConnection connection : connections) {
			IModelNode source = connection.getSource();
			IModelNode target = connection.getTarget();
			graph.addEdge(source, target, connection);
		}

		return graph;
	}

	private class AssigmentValues {
		public String name;
		public String condition;
		public ParameterType type;

		public AssigmentValues(String name, String condition, ParameterType type) {
			this.name = name;
			this.condition = condition;
			this.type = type;
		}

		public AssigmentValues(String name, String condition) {
			this.name = name;
			this.condition = condition;
		}

	}
}
