package com.specmate.testspecification.internal.services;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;
import org.sat4j.core.VecInt;
import org.sat4j.maxsat.SolverFactory;
import org.sat4j.maxsat.WeightedMaxSatDecorator;
import org.sat4j.pb.IPBSolver;
import org.sat4j.pb.tools.DependencyHelper;
import org.sat4j.specs.ContradictionException;
import org.sat4j.specs.IConstr;
import org.sat4j.specs.ISolver;
import org.sat4j.specs.IVecInt;
import org.sat4j.specs.TimeoutException;
import org.sat4j.tools.GateTranslator;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import com.specmate.common.AssertUtil;
import com.specmate.common.SpecmateException;
import com.specmate.model.requirements.CEGConnection;
import com.specmate.model.requirements.CEGModel;
import com.specmate.model.requirements.CEGNode;
import com.specmate.model.requirements.NodeType;
import com.specmate.model.support.util.SpecmateEcoreUtil;
import com.specmate.model.testspecification.ParameterAssignment;
import com.specmate.model.testspecification.ParameterType;
import com.specmate.model.testspecification.TestCase;
import com.specmate.model.testspecification.TestParameter;
import com.specmate.model.testspecification.TestSpecification;
import com.specmate.model.testspecification.TestspecificationFactory;
import com.specmate.testspecification.internal.services.TaggedBoolean.ETag;

public class TestCaseGenerator {

	private TestSpecification specification;
	private CEGModel cegModel;
	private List<CEGNode> nodes;

	public TestCaseGenerator(TestSpecification specification) {
		AssertUtil.assertInstanceOf(specification.eContainer(), CEGModel.class);
		this.specification = specification;
		this.cegModel = (CEGModel) specification.eContainer();
		this.nodes = SpecmateEcoreUtil.pickInstancesOf(cegModel.getContents(), CEGNode.class);
	}

	/** Starts the test case generation */
	public void generate() throws SpecmateException {
		this.adaptSpecificationAndGenerateTestCases();
	}

	/**
	 * Adds necessary input and output parameters to the specification and
	 * generates test cases
	 */
	private void adaptSpecificationAndGenerateTestCases() throws SpecmateException {
		generateParameters();
		generateTestCases();
	}

	/** Adds necessary parameters to the specification */
	private void generateParameters() {
		for (CEGNode node : nodes) {
			String name = node.getVariable();
			ParameterType type = determineParameterTypeForNode(node);
			if (type != null && !parameterExists(specification, name, type)) {
				TestParameter parameter = TestspecificationFactory.eINSTANCE.createTestParameter();
				parameter.setId(SpecmateEcoreUtil.getIdForChild(specification, parameter.eClass()));
				parameter.setName(name);
				parameter.setType(type);
				specification.getContents().add(parameter);
			}
		}
	}

	/**
	 * Determines if a node is an input, output or intermediate node.
	 * 
	 * @param node
	 * @return ParameterType.INPUT, ir the nodes is an input node,
	 *         ParameterType.OUTPUT, if the node is an output node,
	 *         <code>null</code> if the node is an intermediate node.
	 */
	private ParameterType determineParameterTypeForNode(CEGNode node) {
		if (node.getIncomingConnections().isEmpty()) {
			return ParameterType.INPUT;
		} else if (node.getOutgoingConnections().isEmpty()) {
			return ParameterType.OUTPUT;
		} else {
			return null;
		}
	}

	/** Checks if a parameter already exists in a specification. */
	private boolean parameterExists(TestSpecification specification, String name, ParameterType type) {
		List<TestParameter> parameters = SpecmateEcoreUtil.pickInstancesOf(specification.getContents(),
				TestParameter.class);
		for (TestParameter parameter : parameters) {
			if (parameter.getName().equals(name) && parameter.getType().equals(type)) {
				return true;
			}
		}
		return false;
	}

	/** Generates test cases for the nodes of a CEG. */
	private void generateTestCases() throws SpecmateException {
		Set<NodeEvaluation> evaluations = computeEvaluations();
		for (NodeEvaluation evaluation : evaluations) {
			TestCase testCase = createTestCase(evaluation, specification);
			specification.getContents().add(testCase);
		}
	}

	/** Creates a test case for a single node evaluation. */
	private TestCase createTestCase(NodeEvaluation evaluation, TestSpecification specification) {
		TestCase testCase = TestspecificationFactory.eINSTANCE.createTestCase();
		testCase.setId(SpecmateEcoreUtil.getIdForChild(specification, testCase.eClass()));
		testCase.setName(testCase.getId());
		List<TestParameter> parameters = SpecmateEcoreUtil.pickInstancesOf(specification.getContents(),
				TestParameter.class);
		Multimap<String, CEGNode> variableToNodeMap = ArrayListMultimap.create();
		evaluation.keySet().stream().forEach(n -> variableToNodeMap.put(n.getVariable(), n));
		for (TestParameter parameter : parameters) {
			List<String> constraints = new ArrayList<>();
			for (CEGNode node : variableToNodeMap.get(parameter.getName())) {
				TaggedBoolean nodeEval = evaluation.get(node);
				String condition = node.getCondition();
				if (nodeEval != null) {
					String parameterValue = buildParameterValue(condition, nodeEval.value);
					constraints.add(parameterValue);
				}
			}
			String parameterValue = StringUtils.join(constraints, ",");
			ParameterAssignment assignment = TestspecificationFactory.eINSTANCE.createParameterAssignment();
			assignment.setId(SpecmateEcoreUtil.getIdForChild(testCase, assignment.eClass()));
			assignment.setParameter(parameter);
			assignment.setValue(parameterValue);
			assignment.setCondition(parameterValue);
			testCase.getContents().add(assignment);
		}
		return testCase;
	}

	/**
	 * Creates the string representation of an operator and a value. Negates the
	 * operator if necessary.
	 */
	private String buildParameterValue(String condition, Boolean nodeEval) {
		if (!nodeEval) {
			return negateCondition(condition);
		}
		return condition;
	}

	/** Negates a condition. */
	private String negateCondition(String condition) {
		return "not " + condition;
	}

	/**
	 * Node evaluations are a precursor to test cases. This method computes the
	 * node evaluations according to the rules in the Specmate systems
	 * requirements documentation.
	 * 
	 * @param nodes
	 * @return
	 * @throws SpecmateException
	 */
	private Set<NodeEvaluation> computeEvaluations() throws SpecmateException {
		Set<NodeEvaluation> evaluationList = getInitialEvaluations();
		Set<NodeEvaluation> intermediateEvaluations = getIntermediateEvaluations(evaluationList);
		while (!intermediateEvaluations.isEmpty()) {
			for (NodeEvaluation evaluation : intermediateEvaluations) {
				evaluationList.remove(evaluation);
				Optional<CEGNode> intermediateNodeOpt = getAnyIntermediateNode(evaluation);
				AssertUtil.assertTrue(intermediateNodeOpt.isPresent());
				CEGNode node = intermediateNodeOpt.get();
				evaluationList.addAll(iterateEvaluation(evaluation, node));
			}
			intermediateEvaluations = getIntermediateEvaluations(evaluationList);
		}
		Set<NodeEvaluation> merged = mergeCompatibleEvaluations(evaluationList);
		Set<NodeEvaluation> filled = new HashSet<>();
		for (NodeEvaluation eval : merged) {
			filled.add(fill(eval));
		}
		return filled;
	}

	/**
	 * Returns the inital evaluations for the CEG model, where all output nodes
	 * are set one time true and one time false.
	 */
	private Set<NodeEvaluation> getInitialEvaluations() {
		Set<NodeEvaluation> evaluations = new HashSet<>();
		nodes.stream().filter(node -> (determineParameterTypeForNode(node) == ParameterType.OUTPUT)).forEach(node -> {
			NodeEvaluation positiveEvaluation = new NodeEvaluation();
			positiveEvaluation.put(node, new TaggedBoolean(true, TaggedBoolean.ETag.ALL));
			evaluations.add(positiveEvaluation);
			NodeEvaluation negativeEvaluation = new NodeEvaluation();
			negativeEvaluation.put(node, new TaggedBoolean(false, TaggedBoolean.ETag.ALL));
			evaluations.add(negativeEvaluation);
		});

		return evaluations;
	}

	/** Retrieves a node that has predecessors with out a set value */
	private Optional<CEGNode> getAnyIntermediateNode(NodeEvaluation evaluation) {
		for (Entry<CEGNode, TaggedBoolean> entry : evaluation.entrySet()) {
			if (entry.getValue().tag == ETag.ANY) {
				continue;
			}
			CEGNode node = entry.getKey();
			if (determineParameterTypeForNode(node) != ParameterType.INPUT) {
				boolean handled = node.getIncomingConnections().stream().map(conn -> conn.getSource())
						.allMatch(n -> evaluation.containsKey(n));
				if (!handled) {
					return Optional.of(node);
				}
			}
		}
		return Optional.empty();
	}

	/**
	 * Returns evaluations that have intermediate nodes (i.e. nodes that have to
	 * be evaluated)
	 */
	private Set<NodeEvaluation> getIntermediateEvaluations(Set<NodeEvaluation> evaluations) {
		HashSet<NodeEvaluation> intermediate = new HashSet<>();
		for (NodeEvaluation evaluation : evaluations) {
			if (getAnyIntermediateNode(evaluation).isPresent()) {
				intermediate.add(evaluation);
			}
		}
		return intermediate;
	}

	/**
	 * Takes evaluation and a node and computes the evaluations of the nodes
	 * predecessors
	 */
	private Set<NodeEvaluation> iterateEvaluation(NodeEvaluation evaluation, CEGNode node) throws SpecmateException {
		Set<NodeEvaluation> result = new HashSet<>();
		AssertUtil.assertEquals(evaluation.get(node).tag, ETag.ALL);
		switch (node.getType()) {
		case AND:
			handleAllCase(true, evaluation, node, result);
			break;
		case OR:
			handleAllCase(false, evaluation, node, result);
			break;
		}
		return result;
	}

	private void handleAllCase(boolean isAnd, NodeEvaluation evaluation, CEGNode node, Set<NodeEvaluation> result)
			throws SpecmateException {
		boolean nodeValue = evaluation.get(node).value;
		// case where node is true in AND case or node is false in OR case
		if ((isAnd && nodeValue) || (!isAnd && !nodeValue)) {
			for (CEGConnection selectedConn : node.getIncomingConnections()) {
				NodeEvaluation newEvaluation = (NodeEvaluation) evaluation.clone();
				for (CEGConnection conn : node.getIncomingConnections()) {
					boolean value = isAnd ^ conn.isNegate();
					ETag tag = conn == selectedConn ? ETag.ALL : ETag.ANY;
					checkAndSet(newEvaluation, conn.getSource(), new TaggedBoolean(value, tag));
				}
				result.add(newEvaluation);
			}
			// case where node is false in AND case or node is true in OR case
		} else {
			for (CEGConnection selectedConn : node.getIncomingConnections()) {
				NodeEvaluation newEvaluation = (NodeEvaluation) evaluation.clone();
				for (CEGConnection conn : node.getIncomingConnections()) {
					boolean value = ((conn == selectedConn) ^ (isAnd ^ conn.isNegate()));
					ETag tag = conn == selectedConn ? ETag.ALL : ETag.ANY;
					checkAndSet(newEvaluation, conn.getSource(), new TaggedBoolean(value, tag));
				}
				result.add(newEvaluation);
			}
		}
	}

	/**
	 * Sets the value of a node in an evaluation but checks first if it is
	 * already set with a different value *
	 */
	private void checkAndSet(NodeEvaluation evaluation, CEGNode node, TaggedBoolean effectiveValue)
			throws SpecmateException {
		if (evaluation.containsKey(node) && evaluation.get(node).value != effectiveValue.value) {
			throw new SpecmateException("Inconsistent value in evaluation");
		} else {
			evaluation.put(node, effectiveValue);
		}
	}

	/**
	 * Runs through the list of evaluations and merges the ones that can be
	 * merged.
	 * 
	 * @throws SpecmateException
	 */
	private Set<NodeEvaluation> mergeCompatibleEvaluations(Set<NodeEvaluation> evaluations) throws SpecmateException {
		IPBSolver solver = org.sat4j.pb.SolverFactory.newResolution();
		GateTranslator translator = new GateTranslator(solver);
		WeightedMaxSatDecorator maxSat = new WeightedMaxSatDecorator(solver);
		int switchVar = getAdditionalVar(evaluations.size() + 1);
		int n = maxSat.newVar(switchVar);
		int i = 1;
		try {
			pushCEGStructure(translator);
			for (NodeEvaluation evaluation : evaluations) {
				int varForEval = getAdditionalVar(i);
				// maxSat.newVar(varForEval);
				i++;
				for (CEGNode node : nodes) {
					int varForNode = getVarForNode(node);
					// maxSat.newVar(varForNode);
					TaggedBoolean value = evaluation.get(node);
					if (value != null) {
						if (value.value) {
							IConstr[] cosnt = translator.or(switchVar, getVectorForVariables(-varForEval, varForNode));
						} else {
							IConstr[] cosnt = translator.or(switchVar, getVectorForVariables(-varForEval, -varForNode));
						}
					}
				}
				maxSat.addSoftClause(1, getVectorForVariables(varForEval));
			}
			translator.gateTrue(switchVar);
		} catch (ContradictionException c) {
			throw new SpecmateException(c);
		}
		try {
			DependencyHelper<T, C> helper;
			helper.
			int[] fm;
			while (solver.admitBetterSolution()) {

			}
			int[] model = maxSat.findModel();
			System.out.println(maxSat.getStat());
			System.out.println(model);

		} catch (TimeoutException e) {
			throw new SpecmateException(e);
		}
		return null;
		// Set<NodeEvaluation> result = new HashSet<>();
		// Graph<NodeEvaluation, DefaultEdge> compatibilityGraph =
		// createCompatibilityGraph(evaluationList);
		// BronKerboschCliqueFinder<NodeEvaluation, DefaultEdge> cliqueFinder =
		// new BronKerboschCliqueFinder<>(
		// compatibilityGraph);
		// Collection<Set<NodeEvaluation>> maximalCliques =
		// cliqueFinder.getBiggestMaximalCliques();
		//
		// compatibilityGraph.vertexSet().stream().filter(v ->
		// compatibilityGraph.edgesOf(v).isEmpty())
		// .forEach(v -> result.add(v));
		//
		// for (Set<NodeEvaluation> clique : maximalCliques) {
		// result.add(mergeAllEvaluations(clique));
		// }
		//
		// return result;
	}

	private int getAdditionalVar(int i) {
		return nodes.size() + i;
	}

	private IVecInt getVectorForVariables(int... vars) {
		IVecInt vector = new VecInt(vars.length + 1);
		for (int i = 0; i < vars.length; i++)
			vector.push(vars[i]);
		return vector;
	}

	private Graph<NodeEvaluation, DefaultEdge> createCompatibilityGraph(Set<NodeEvaluation> evaluationList) {
		List<NodeEvaluation> evalList = new ArrayList<>(evaluationList);
		Graph<NodeEvaluation, DefaultEdge> compatibilityGraph = new SimpleGraph<>(DefaultEdge.class);
		for (int fromIndex = 0; fromIndex < evalList.size(); fromIndex++) {
			NodeEvaluation from = evalList.get(fromIndex);
			compatibilityGraph.addVertex(from);
			for (int toIndex = 0; toIndex < evalList.size(); toIndex++) {
				NodeEvaluation to = evalList.get(toIndex);
				if (fromIndex != toIndex && canBeMerged(from, to)) {
					// add to graph if not already present
					compatibilityGraph.addVertex(to);
					compatibilityGraph.addEdge(from, to);
				}
			}
		}
		return compatibilityGraph;
	}

	private NodeEvaluation mergeAllEvaluations(Set<NodeEvaluation> clique) {
		NodeEvaluation evaluation = new NodeEvaluation();
		for (NodeEvaluation toMerge : clique) {
			evaluation.putAll(toMerge);
		}
		return evaluation;
	}

	/** Checks if two evaluations can be merged */
	private boolean canBeMerged(NodeEvaluation from, NodeEvaluation to) {
		for (CEGNode node : from.keySet()) {
			TaggedBoolean fromTaggedValue = from.get(node);

			// Check if evaluations have different values for same node
			if (to.containsKey(node)) {
				TaggedBoolean toTaggedValue = to.get(node);
				if (toTaggedValue.value != fromTaggedValue.value) {
					return false;
				}
			}

			// Check if evaluations have set two output nodes with the same
			// variable
			ParameterType parameterType = determineParameterTypeForNode(node);
			if (parameterType == ParameterType.OUTPUT && fromTaggedValue.value) {
				boolean conflict = to.entrySet().stream().anyMatch(entry -> {
					return !entry.getKey().equals(node) && entry.getKey().getVariable().equals(node.getVariable())
							&& entry.getValue().value;
				});
				if (conflict) {
					return false;
				}
			}
		}

		// Check if merging the two evaluations can lead to an inconsistency
		NodeEvaluation test = new NodeEvaluation();
		test.putAll(to);
		test.putAll(from);
		return checkConsistency(test);
	}

	/** Checks if a node evaluation is consistent */
	private boolean checkConsistency(NodeEvaluation test) {
		try {
			return fill(test) != null;
		} catch (SpecmateException e) {
			return false;
		}
	}

	/** Fills out all unset nodes in the given node evaluation */
	private NodeEvaluation fill(NodeEvaluation evaluation) throws SpecmateException {
		ISolver solver = initSolver(evaluation);
		try {
			NodeEvaluation filled = new NodeEvaluation();
			int[] model = solver.findModel();
			if (model == null) {
				throw new SpecmateException("Could not determine consistent test values.");
			}
			for (int v : model) {
				setModelValue(evaluation, filled, v);
			}
			return filled;
		} catch (TimeoutException e) {
			throw new SpecmateException(e);
		}
	}

	/**
	 * Sets the value in an evaluation based on an original evaluation and a
	 * model value.
	 */
	private void setModelValue(NodeEvaluation originalEvaluation, NodeEvaluation targetEvaluation, int varNameValue) {
		boolean value = varNameValue > 0;
		int varName = (value ? 1 : -1) * varNameValue;
		CEGNode node = getNodeForVar(varName);
		TaggedBoolean originalValue = originalEvaluation.get(node);
		if (originalValue != null) {
			targetEvaluation.put(node, originalValue);
		} else {
			targetEvaluation.put(node, new TaggedBoolean(value, ETag.AUTO));
		}
	}

	/** Initializes the SAT4J solver. */
	private GateTranslator initSolver(NodeEvaluation evaluation) throws SpecmateException {
		GateTranslator translator = new GateTranslator(SolverFactory.newLight());
		try {
			pushCEGStructure(translator);
			pushEvaluation(evaluation, translator);
		} catch (ContradictionException e) {
			throw new SpecmateException(e);
		}
		return translator;
	}

	private void pushEvaluation(NodeEvaluation evaluation, GateTranslator translator) throws ContradictionException {
		for (CEGNode node : nodes) {
			int varForNode = getVarForNode(node);
			TaggedBoolean value = evaluation.get(node);
			if (value != null) {
				if (value.value) {
					translator.gateTrue(varForNode);
				} else {
					translator.gateFalse(varForNode);
				}
			}
		}
	}

	private void pushCEGStructure(GateTranslator translator) throws ContradictionException {
		for (CEGNode node : nodes) {
			int varForNode = getVarForNode(node);
			IVecInt vector = getPredecessorVector(node);
			if (vector.size() > 0) {
				if (node.getType() == NodeType.AND) {
					translator.and(varForNode, vector);
				} else {
					translator.or(varForNode, vector);
				}
			}
		}
	}

	/** Returns the CEG node for a given variable (given as int) */
	private CEGNode getNodeForVar(int i) {
		return nodes.get(i - 1);
	}

	/** Returns a variable (usable for SAT4J) for a given CEG node. */
	private int getVarForNode(CEGNode pre) {
		return nodes.indexOf(pre) + 1;
	}

	/** Returns a variable/value vector for all predeccessors of a node */
	private IVecInt getPredecessorVector(CEGNode node) {
		IVecInt vector = new VecInt();
		for (CEGConnection conn : node.getIncomingConnections()) {
			CEGNode pre = conn.getSource();
			int var = getVarForNode(pre);
			if (conn.isNegate()) {
				var *= -1;
			}
			vector.push(var);
		}
		return vector;
	}

}
