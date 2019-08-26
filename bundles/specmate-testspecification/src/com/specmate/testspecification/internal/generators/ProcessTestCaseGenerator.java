package com.specmate.testspecification.internal.generators;

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
import com.specmate.model.testspecification.TestProcedure;
import com.specmate.model.testspecification.TestSpecification;
import com.specmate.model.testspecification.TestStep;
import com.specmate.model.testspecification.TestspecificationFactory;

/** Generates tests from a process model */
public class ProcessTestCaseGenerator extends TestCaseGeneratorBase<Process, IModelNode> {

	/** All connections of the process */
	private List<ProcessConnection> connections;

	/**
	 * Map from parameter names to parameters of the resulting test case
	 * specification
	 */
	private Map<String, TestParameter> testParameters = new HashMap<>();

	/** constructor */
	public ProcessTestCaseGenerator(TestSpecification specification) {
		super(specification, Process.class, IModelNode.class);
		connections = SpecmateEcoreUtil.pickInstancesOf(model.getContents(), ProcessConnection.class);
	}

	/** {@inheritDoc} */
	@Override
	protected void generateParameters() {
		List<ProcessDecision> decisions = SpecmateEcoreUtil.pickInstancesOf(model.getContents(), ProcessDecision.class);
		for (ProcessDecision decision : decisions) {
			String testParameterName = decision.getName();
			createAndAddTestParameterIfNecessary(testParameterName, ParameterType.INPUT);
		}

		List<ProcessConnection> connections = SpecmateEcoreUtil.pickInstancesOf(model.getContents(),
				ProcessConnection.class);
		for (ProcessConnection connection : connections) {
			if (connection.getSource() instanceof ProcessDecision || !hasCondition(connection)) {
				// in this case the test parameter is defined by the decision node
				continue;
			}
			String testParameterName = extractVariableAndConditionFromExpression(connection.getCondition()).name;
			createAndAddTestParameterIfNecessary(testParameterName, ParameterType.INPUT);
		}

		List<ProcessStep> steps = SpecmateEcoreUtil.pickInstancesOf(model.getContents(), ProcessStep.class);
		for (ProcessStep step : steps) {
			if (hasExpectedOutcome(step)) {
				String expectedOutcome = step.getExpectedOutcome();
				String variable = extractVariableAndConditionFromExpression(expectedOutcome).name;
				createAndAddTestParameterIfNecessary(variable, ParameterType.OUTPUT);
			}
		}
	}

	/** {@inheritDoc} */
	@Override
	protected void generateTestCases() throws SpecmateException {
		List<GraphPath<IModelNode, ProcessConnection>> allPaths = getAllPaths();
		List<GraphPath<IModelNode, ProcessConnection>> filteredPaths = filterDuplicatePaths(allPaths);
		createTestCases(filteredPaths);
	}

	/**
	 * Looks up a if a test parameter with the given name has already been created.
	 * If yes, returns the parameter. If not, creates a new one and saves it.
	 */
	private TestParameter createAndAddTestParameterIfNecessary(String testParameterName, ParameterType parameterType) {
		TestParameter testParameter = testParameters.get(testParameterName);
		if (testParameter == null) {
			testParameter = createTestParameter(testParameterName, parameterType);
			testParameters.put(testParameterName, testParameter);
			specification.getContents().add(testParameter);
		}
		return testParameter;
	}

	/**
	 * Generates paths through the model such that every edge is taken at least once
	 */
	private List<GraphPath<IModelNode, ProcessConnection>> getAllPaths() {
		Set<ProcessStart> processStarts = getStartNodes();
		Set<ProcessEnd> processEnds = getEndNodes();
		DirectedGraph<IModelNode, ProcessConnection> graph = getGraph();

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

	/** Retrieves the start nodes of the model */
	private Set<ProcessStart> getStartNodes() {
		Set<ProcessStart> startNodes = SpecmateEcoreUtil.uniqueInstancesOf(nodes, ProcessStart.class);
		AssertUtil.assertEquals(startNodes.size(), 1, "Number of start nodes in process is different to 1.");
		return startNodes;
	}

	/** Retrieves the end nodes of the model */
	private Set<ProcessEnd> getEndNodes() {
		Set<ProcessEnd> endNodes = SpecmateEcoreUtil.uniqueInstancesOf(nodes, ProcessEnd.class);
		AssertUtil.assertTrue(endNodes.size() > 0, "No end nodes in process were found");
		return endNodes;
	}

	/** Creates a graph representing the model */
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

	/** Removes duplicate paths from the list */
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

	/** Extracts variable and conditoin from a single string by splitting at "=" */
	private AssigmentValues extractVariableAndConditionFromExpression(String outcome) {
		// split only at first occurence of "=", result will have length <= 2
		String[] splitted = outcome.split("=", 2);
		String variable = splitted[0];
		String condition = splitted.length > 1 && !StringUtils.isEmpty(splitted[1]) ? splitted[1] : "is present";
		return new AssigmentValues(variable.trim(), condition.trim());
	}

	/** Creates testcases from a list of paths through the model */
	private void createTestCases(List<GraphPath<IModelNode, ProcessConnection>> paths) {
		for (GraphPath<IModelNode, ProcessConnection> path : paths) {
			List<AssigmentValues> variableConditionList = extractVariablesAndConditionsFromPath(path);
			TestCase tc = createTestCaseFromVariableConditionList(variableConditionList);
			TestProcedure proc = createTestProcedureForPath(tc, path);
			tc.getContents().add(proc);
			specification.getContents().add(tc);
		}
	}

	/** Extracts a list of variable/condition pairs reflecting a certain path */
	private List<AssigmentValues> extractVariablesAndConditionsFromPath(GraphPath<IModelNode, ProcessConnection> path) {
		List<AssigmentValues> variableConditionList = new ArrayList<>();
		iteratePath(path, new PathConsumer() {
			@Override
			public void consumeProcessStart(ProcessStart start, ProcessConnection outgoingConnection,
					int vertexNumber) {
				consumeGenericConnection(outgoingConnection);
			}

			@Override
			public void consumeProcessEnd(ProcessEnd end, int vertexNumber) {
				// nothing to do
			}

			@Override
			public void consumeProcessDecision(ProcessDecision decision, ProcessConnection outgoingConnection,
					int vertexNumber) {
				if (!StringUtils.isEmpty(decision.getName())) {
					String condition = outgoingConnection.getCondition();
					if (StringUtils.isEmpty(condition)) {
						condition = "is present";
					}
					variableConditionList.add(new AssigmentValues(decision.getName(), condition, ParameterType.INPUT));
				}
			}

			@Override
			public void consumeProcessStep(ProcessStep step, ProcessConnection outgoingConnection, int vertexNumber) {
				if (hasExpectedOutcome(step)) {
					AssigmentValues varCond = extractVariableAndConditionFromExpression(step.getExpectedOutcome());
					varCond.type = ParameterType.OUTPUT;
					variableConditionList.add(varCond);
				}
				consumeGenericConnection(outgoingConnection);
			}

			private void consumeGenericConnection(ProcessConnection outgoingConnection) {
				if (outgoingConnection != null && hasCondition(outgoingConnection)) {
					AssigmentValues varCond = extractVariableAndConditionFromExpression(
							outgoingConnection.getCondition());
					varCond.type = ParameterType.INPUT;
					variableConditionList.add(varCond);
				}
			}
		});
		return variableConditionList;
	}

	/**
	 * Iterates through a graph path and for each node/connection pair calls the
	 * method of the consumer that matches the node type.
	 */
	private void iteratePath(GraphPath<IModelNode, ProcessConnection> path, PathConsumer consumer) {
		for (int i = 0; i < path.getVertexList().size(); i++) {
			IModelNode currentNode = path.getVertexList().get(i);
			ProcessConnection currentConnection = null;
			if (i < path.getEdgeList().size()) {
				currentConnection = path.getEdgeList().get(i);
			}
			if (currentNode instanceof ProcessStep) {
				consumer.consumeProcessStep((ProcessStep) currentNode, currentConnection, i);
			} else if (currentNode instanceof ProcessDecision) {
				consumer.consumeProcessDecision((ProcessDecision) currentNode, currentConnection, i);
			} else if (currentNode instanceof ProcessStart) {
				consumer.consumeProcessStart((ProcessStart) currentNode, currentConnection, i);
			} else if (currentNode instanceof ProcessEnd) {
				consumer.consumeProcessEnd((ProcessEnd) currentNode, i);
			}
		}
	}

	/**
	 * Creates a test case from a a list of variables and conditions reflecting a
	 * certain path through the model
	 */
	private TestCase createTestCaseFromVariableConditionList(List<AssigmentValues> variableConditionList) {
		List<String> seenParameterNames = new ArrayList<>();
		TestCase tc = createTestCase(specification);
		tc.setConsistent(true);
		for (AssigmentValues varCond : variableConditionList) {
			String variable = varCond.name;
			variable = getCountingParameterName(seenParameterNames, variable);
			TestParameter parameter = createAndAddTestParameterIfNecessary(variable, varCond.type);
			ParameterAssignment assignment = createParameterAssignment(tc, parameter, varCond.condition);
			tc.getContents().add(assignment);
		}
		return tc;
	}

	/**
	 * Returns [variable]-X where X is the smalles number such that [Variable]-X is
	 * not in the seenParameterNames list. If X is 0, the "-X" part is omitted.
	 */
	private String getCountingParameterName(List<String> seenParameterNames, String variable) {
		int i = 1;
		while (seenParameterNames.contains(variable)) {
			i++;
			variable = variable + " " + i;
		}
		seenParameterNames.add(variable);
		return variable;
	}

	/** Extracts a list of variable/condition pairs reflecting a certain path */
	private TestProcedure createTestProcedureForPath(TestCase tc, GraphPath<IModelNode, ProcessConnection> path) {
		List<String> seenParameterNames = new ArrayList<>();
		TestProcedure procedure = createTestProcedure(tc);
		iteratePath(path, new PathConsumer() {

			@Override
			public void consumeProcessStart(ProcessStart start, ProcessConnection outgoingConnection,
					int vertexNumber) {
				if (!hasCondition(outgoingConnection)) {
					return;
				}
				String description = start.getDescription();
				AssigmentValues varCond = extractVariableAndConditionFromExpression(outgoingConnection.getCondition());
				String parameterName = getCountingParameterName(seenParameterNames, varCond.name);
				TestParameter testParameter = testParameters.get(parameterName);
				createTestStep(makePrecondition(outgoingConnection), makeExpectedOutcome(outgoingConnection),
						vertexNumber, procedure, testParameter, description);

			}

			@Override
			public void consumeProcessEnd(ProcessEnd end, int vertexNumber) {
				// Nothing to do
			}

			@Override
			public void consumeProcessDecision(ProcessDecision decision, ProcessConnection outgoingConnection,
					int vertexNumber) {
				String description = decision.getDescription();
				String parameterName = getCountingParameterName(seenParameterNames, decision.getName());
				TestParameter testParameter = testParameters.get(parameterName);
				createTestStep(makeAction(decision, outgoingConnection), makeExpectedOutcome(outgoingConnection),
						vertexNumber, procedure, testParameter, description);
			}

			@Override
			public void consumeProcessStep(ProcessStep step, ProcessConnection outgoingConnection, int vertexNumber) {
				if (!hasExpectedOutcome(step)) {
					return;
				}
				String description = step.getDescription();
				AssigmentValues varCond = extractVariableAndConditionFromExpression(step.getExpectedOutcome());
				String parameterName = getCountingParameterName(seenParameterNames, varCond.name);
				TestParameter testParameter = testParameters.get(parameterName);

				createTestStep(makeAction(step), makeExpectedOutcome(step, outgoingConnection), vertexNumber, procedure,
						testParameter, description);
			}
		});
		return procedure;
	}

	/** Create a test procedure for the given test case. */
	private TestProcedure createTestProcedure(TestCase testCase) {
		TestProcedure procedure = TestspecificationFactory.eINSTANCE.createTestProcedure();
		procedure.setId(SpecmateEcoreUtil.getIdForChild(testCase, procedure.eClass()));
		procedure.setName(procedure.getId());
		return procedure;
	}

	/** Creates a precondition text from a connection */
	private String makePrecondition(ProcessConnection connection) {
		return "Establish precondition: " + connection.getCondition();
	}

	/** Creates an action text from a process step */
	private String makeAction(ProcessStep step) {
		return step.getName();
	}

	/** Creates an action text from a process decision and a following connection */
	private String makeAction(ProcessDecision decision, ProcessConnection connection) {
		if (!hasCondition(connection)) {
			return "";
		}
		return "Establish condition: " + decision.getName() + "=" + connection.getCondition();
	}

	/** Creates an expected outcome text from a step and a following connection */
	private String makeExpectedOutcome(ProcessStep step, ProcessConnection connection) {
		List<String> checkParts = new ArrayList<>();

		if (hasExpectedOutcome(step)) {
			checkParts.add(step.getExpectedOutcome());
		}

		if (hasCondition(connection)) {
			checkParts.add(connection.getCondition());
		}
		return StringUtils.join(checkParts, ", ");
	}

	/** Creates an expected outcome text from a connection */
	private String makeExpectedOutcome(ProcessConnection connection) {
		if (hasCondition(connection)) {
			return connection.getCondition();
		}
		return "";
	}

	/** Return true if the connection has a non-empty condition */
	private boolean hasCondition(ProcessConnection connection) {
		return !StringUtils.isEmpty(connection.getCondition());
	}

	/** Returns true if the step has a non-empty expected outcome */
	private boolean hasExpectedOutcome(ProcessStep step) {
		return step.getExpectedOutcome() != null && !step.getExpectedOutcome().equals("");
	}

	/**
	 * Creates a new test step and adds it to the procedure at the given position
	 */
	private void createTestStep(String action, String expectedOutcome, int position, IContainer procedure,
			TestParameter testParameter, String description) {
		TestStep testStep = TestspecificationFactory.eINSTANCE.createTestStep();
		testStep.setName(action);
		testStep.setDescription(description);
		testStep.setPosition(position);
		testStep.setExpectedOutcome(expectedOutcome);
		testStep.setId(SpecmateEcoreUtil.getIdForChild(procedure, testStep.eClass()));
		if (testParameter != null) {
			testStep.getReferencedTestParameters().add(testParameter);
		}
		procedure.getContents().add(testStep);
	}

	/** Class to store values for a parameter assignment */
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

	/** Interface for sequentially processing a graph path */
	private interface PathConsumer {
		void consumeProcessStart(ProcessStart start, ProcessConnection outgoingConnection, int vertexNumber);

		void consumeProcessEnd(ProcessEnd end, int vertexNumber);

		void consumeProcessDecision(ProcessDecision decision, ProcessConnection outgoingConnection, int vertexNumber);

		void consumeProcessStep(ProcessStep step, ProcessConnection outgoingConnection, int vertexNumber);
	}
}
