package com.specmate.testspecification.internal.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.emf.common.util.EList;
import org.jgrapht.DirectedGraph;
import org.jgrapht.GraphPath;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.DirectedMultigraph;
import org.jgrapht.graph.GraphWalk;

import com.specmate.common.AssertUtil;
import com.specmate.common.exception.SpecmateException;
import com.specmate.model.base.IContainer;
import com.specmate.model.base.IModelConnection;
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
import com.specmate.model.testspecification.TestProcedure;
import com.specmate.model.testspecification.TestSpecification;
import com.specmate.model.testspecification.TestStep;
import com.specmate.model.testspecification.TestspecificationFactory;
import com.specmate.testspecification.internal.services.TaggedBoolean.ETag;

public class ProcessTestCaseGenerator extends TestCaseGeneratorBase<Process, IModelNode> {

	private List<ProcessConnection> connections;
	private Map<String, TestParameter> testParameters = new HashMap<>();

	public ProcessTestCaseGenerator(TestSpecification specification) {
		super(specification, Process.class, IModelNode.class);
		this.connections = SpecmateEcoreUtil.pickInstancesOf(this.model.getContents(), ProcessConnection.class);
	}

	@Override
	protected void generateParameters() {
		List<ProcessDecision> decisions = SpecmateEcoreUtil.pickInstancesOf(this.model.getContents(),
				ProcessDecision.class);
		for (ProcessDecision decision : decisions) {
			EList<IModelConnection> outgoingConnections = decision.getOutgoingConnections();
			for (IModelConnection connection : outgoingConnections) {
				String testParameterName = getTestParameterName((ProcessConnection) connection);
				TestParameter testParameter = createTestParameter(testParameterName, ParameterType.INPUT);
				this.testParameters.put(testParameterName, testParameter);
				this.specification.getContents().add(testParameter);
			}
		}
	}

	private String getTestParameterName(ProcessConnection connection) {
		return connection.getCondition();
	}

	@Override
	protected void generateTestCases() throws SpecmateException {
		List<GraphPath<IModelNode, ProcessConnection>> allPaths = getAllPaths();
		List<GraphPath<IModelNode, ProcessConnection>> filteredPaths = filterDuplicatePaths(allPaths);
		List<NodeEvaluation> evaluations = computeEvaluations(filteredPaths);
		createTestCases(evaluations, filteredPaths);
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

	private void createTestCases(List<NodeEvaluation> evaluations,
			List<GraphPath<IModelNode, ProcessConnection>> paths) {
		for (int i = 0; i < evaluations.size(); i++) {
			NodeEvaluation evaluation = evaluations.get(i);
			GraphPath<IModelNode, ProcessConnection> path = paths.get(i);
			TestCase testCase = createTestCase(evaluation, this.specification);
			testCase.setConsistent(true);
			testCase.setPosition(i);
			this.specification.getContents().add(testCase);

			TestProcedure procedure = TestspecificationFactory.eINSTANCE.createTestProcedure();
			procedure.setName("Test Procedure " + (i + 1));
			procedure.setId(SpecmateEcoreUtil.getIdForChild(testCase, procedure.eClass()));
			testCase.getContents().add(procedure);

			List<IModelNode> nodes = path.getVertexList();
			List<ProcessConnection> connections = path.getEdgeList();
			for (int j = 0; j < nodes.size(); j++) {
				IModelNode node = nodes.get(j);
				ProcessConnection connection = j < connections.size() ? connections.get(j) : null;
				TestParameter testParameter = j < connections.size()
						? this.testParameters.get(getTestParameterName(connection)) : null;
				if (node instanceof ProcessStart && hasCondition(connection)) {
					createTestStep(makePrecondition(connection), makeCheck(connection), j, procedure, testParameter);
				} else if (node instanceof ProcessEnd) {
					// SKIP
				} else if (node instanceof ProcessStep) {
					createTestStep(makeAction(node), makeCheck((ProcessStep) node, connection), j, procedure,
							testParameter);
				} else if (node instanceof ProcessDecision) {
					createTestStep(makeAction(connection), makeCheck(connection), j, procedure, testParameter);
				}
			}
		}
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
			TestParameter testParameter) {
		TestStep testStep = TestspecificationFactory.eINSTANCE.createTestStep();
		testStep.setName("Generated");
		testStep.setDescription(action);
		testStep.setPosition(position);
		testStep.setExpectedOutcome(check);
		testStep.setId(SpecmateEcoreUtil.getIdForChild(procedure, testStep.eClass()));
		if (testParameter != null) {
			testStep.getReferencedTestParameters().add(testParameter);
		}
		procedure.getContents().add(testStep);
	}

	private List<NodeEvaluation> computeEvaluations(List<GraphPath<IModelNode, ProcessConnection>> allPaths) {
		List<NodeEvaluation> evaluations = new ArrayList<>();
		for (GraphPath<IModelNode, ProcessConnection> path : allPaths) {
			NodeEvaluation evaluation = new NodeEvaluation();
			Set<IModelNode> decisions = path.getVertexList().stream()
					.filter((IModelNode node) -> node instanceof ProcessDecision).collect(Collectors.toSet());
			for (IModelNode decision : decisions) {
				List<IModelConnection> outgoingConnections = decision.getOutgoingConnections();
				for (IModelConnection connection : outgoingConnections) {
					boolean isFulfilled = path.getEdgeList().contains(connection);
					evaluation.put(connection, new TaggedBoolean(isFulfilled, ETag.ALL));
				}
			}
			evaluations.add(evaluation);
		}
		return evaluations;
	}

	private TestCase createTestCase(NodeEvaluation evaluation, TestSpecification specification) {
		TestCase testCase = super.createTestCase(specification);

		List<TestParameter> parameters = SpecmateEcoreUtil.pickInstancesOf(specification.getContents(),
				TestParameter.class);

		for (TestParameter testParameter : parameters) {
			ParameterAssignment assignment = TestspecificationFactory.eINSTANCE.createParameterAssignment();
			assignment.setId(SpecmateEcoreUtil.getIdForChild(testCase, assignment.eClass()));
			assignment.setName(assignment.getId());
			assignment.setParameter(testParameter);

			Optional<Entry<IContainer, TaggedBoolean>> evaluationEntry = Optional.empty();

			String testParameterName = testParameter.getName();
			for (Entry<IContainer, TaggedBoolean> currentEvaluationEntry : evaluation.entrySet()) {
				IContainer currentEvaluationConnection = currentEvaluationEntry.getKey();
				String testParameterNameFromEvaluation = getTestParameterName(
						(ProcessConnection) currentEvaluationConnection);
				if (testParameterNameFromEvaluation == null) {
					getTestParameterName((ProcessConnection) currentEvaluationConnection);
				}
				if (testParameterNameFromEvaluation.equals(testParameterName)) {
					evaluationEntry = Optional.of(currentEvaluationEntry);
					break;
				}
			}

			String value = "";
			if (evaluationEntry.isPresent()) {
				value = String.valueOf(evaluationEntry.get().getValue().value ? "true" : "");
			}

			assignment.setCondition(String.valueOf(value));
			testCase.getContents().add(assignment);

		}
		return testCase;
	}

	private List<GraphPath<IModelNode, ProcessConnection>> getAllPaths() {
		Set<IModelNode> processStarts = getStartNodes();
		Set<IModelNode> processEnds = getEndNodes();
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

	private Set<IModelNode> getStartNodes() {
		Set<IModelNode> startNodes = this.nodes.stream().filter((IModelNode node) -> node instanceof ProcessStart)
				.collect(Collectors.toSet());
		AssertUtil.assertEquals(startNodes.size(), 1, "Number of start nodes in process is different to 1.");
		return startNodes;
	}

	private Set<IModelNode> getEndNodes() {
		Set<IModelNode> endNodes = this.nodes.stream().filter((IModelNode node) -> node instanceof ProcessEnd)
				.collect(Collectors.toSet());
		AssertUtil.assertTrue(endNodes.size() > 0, "No end nodes in process were found");
		return endNodes;
	}

	private DirectedGraph<IModelNode, ProcessConnection> getGraph() {
		DirectedGraph<IModelNode, ProcessConnection> graph = new DirectedMultigraph<>(ProcessConnection.class);
		for (IModelNode node : this.nodes) {
			graph.addVertex(node);
		}

		for (ProcessConnection connection : this.connections) {
			IModelNode source = connection.getSource();
			IModelNode target = connection.getTarget();
			graph.addEdge(source, target, connection);
		}

		return graph;
	}
}
