package com.specmate.testspecification.internal.services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.sat4j.core.VecInt;
import org.sat4j.maxsat.SolverFactory;
import org.sat4j.maxsat.WeightedMaxSatDecorator;
import org.sat4j.pb.IPBSolver;
import org.sat4j.specs.ContradictionException;
import org.sat4j.specs.ISolver;
import org.sat4j.specs.IVecInt;
import org.sat4j.specs.TimeoutException;
import org.sat4j.tools.GateTranslator;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import com.specmate.common.AssertUtil;
import com.specmate.common.SpecmateException;
import com.specmate.model.base.IContainer;
import com.specmate.model.base.IModelConnection;
import com.specmate.model.base.IModelNode;
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

public class CEGTestCaseGenerator extends TestCaseGeneratorBase<CEGModel, CEGNode> {

	public CEGTestCaseGenerator(TestSpecification specification) {
		super(specification, CEGModel.class, CEGNode.class);
	}

	@Override
	protected void generateParameters() {
		for (IModelNode node : nodes) {
			String name = ((CEGNode) node).getVariable();
			ParameterType type = determineParameterTypeForNode(node);
			if (type != null && !parameterExists(specification, name, type)) {
				TestParameter parameter = createTestParameter(name, type);
				specification.getContents().add(parameter);
			}
		}
	}

	/**
	 * Determines if a node is an input, output or intermediate node.
	 * 
	 * @param node
	 * @return ParameterType.INPUT, if the nodes is an input node,
	 *         ParameterType.OUTPUT, if the node is an output node,
	 *         <code>null</code> if the node is an intermediate node.
	 */
	private ParameterType determineParameterTypeForNode(IModelNode node) {
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
	@Override
	protected void generateTestCases() throws SpecmateException {
		Pair<Set<NodeEvaluation>, Set<NodeEvaluation>> evaluations = computeEvaluations();
		Set<NodeEvaluation> consistent = evaluations.getLeft();
		Set<NodeEvaluation> inconsistent = evaluations.getRight();
		int position = 0;
		for (NodeEvaluation evaluation : consistent) {
			TestCase testCase = createTestCase(evaluation, specification, true);
			testCase.setPosition(position++);
			specification.getContents().add(testCase);
		}
		for (NodeEvaluation evaluation : inconsistent) {
			TestCase testCase = createTestCase(evaluation, specification, false);
			testCase.setPosition(position++);
			specification.getContents().add(testCase);
		}
	}

	/** Creates a test case for a single node evaluation. */
	private TestCase createTestCase(NodeEvaluation evaluation, TestSpecification specification, boolean isConsistent) {
		TestCase testCase = super.createTestCase(specification);
		testCase.setConsistent(isConsistent);
		List<TestParameter> parameters = SpecmateEcoreUtil.pickInstancesOf(specification.getContents(),
				TestParameter.class);
		Multimap<String, IContainer> variableToNodeMap = ArrayListMultimap.create();
		evaluation.keySet().stream().forEach(n -> variableToNodeMap.put(((CEGNode) n).getVariable(), n));
		for (TestParameter parameter : parameters) {
			List<String> constraints = new ArrayList<>();
			for (IContainer node : variableToNodeMap.get(parameter.getName())) {
				TaggedBoolean nodeEval = evaluation.get(node);
				String condition = ((CEGNode) node).getCondition();
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
	private Pair<Set<NodeEvaluation>, Set<NodeEvaluation>> computeEvaluations() throws SpecmateException {
		Set<NodeEvaluation> evaluationList = getInitialEvaluations();
		Set<NodeEvaluation> intermediateEvaluations = getIntermediateEvaluations(evaluationList);
		while (!intermediateEvaluations.isEmpty()) {
			for (NodeEvaluation evaluation : intermediateEvaluations) {
				evaluationList.remove(evaluation);
				Optional<IModelNode> intermediateNodeOpt = getAnyIntermediateNode(evaluation);
				AssertUtil.assertTrue(intermediateNodeOpt.isPresent());
				IModelNode node = intermediateNodeOpt.get();
				evaluationList.addAll(iterateEvaluation(evaluation, node));
			}
			intermediateEvaluations = getIntermediateEvaluations(evaluationList);
		}
		return getEvaluations(evaluationList);
	}

	private Pair<Set<NodeEvaluation>, Set<NodeEvaluation>> getEvaluations(Set<NodeEvaluation> evaluationList)
			throws SpecmateException {
		Pair<Set<NodeEvaluation>, Set<NodeEvaluation>> mergedEvals = mergeCompatibleEvaluations(evaluationList);
		Set<NodeEvaluation> merged = mergedEvals.getLeft();
		Set<NodeEvaluation> inconsistent = mergedEvals.getRight();
		Set<NodeEvaluation> filled = new HashSet<>();
		for (NodeEvaluation eval : merged) {
			filled.add(fill(eval));
		}
		return Pair.of(filled, inconsistent);
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
	private Optional<IModelNode> getAnyIntermediateNode(NodeEvaluation evaluation) {
		for (Entry<IContainer, TaggedBoolean> entry : evaluation.entrySet()) {
			if (entry.getValue().tag == ETag.ANY) {
				continue;
			}
			IModelNode node = (IModelNode) entry.getKey();
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
	private Set<NodeEvaluation> iterateEvaluation(NodeEvaluation evaluation, IModelNode node) throws SpecmateException {
		Set<NodeEvaluation> result = new HashSet<>();
		AssertUtil.assertEquals(evaluation.get(node).tag, ETag.ALL);
		switch (((CEGNode) node).getType()) {
		case AND:
			handleAllCase(true, evaluation, node, result);
			break;
		case OR:
			handleAllCase(false, evaluation, node, result);
			break;
		}
		return result;
	}

	private void handleAllCase(boolean isAnd, NodeEvaluation evaluation, IModelNode node, Set<NodeEvaluation> result)
			throws SpecmateException {
		boolean nodeValue = evaluation.get(node).value;
		// case where node is true in AND case or node is false in OR case
		if ((isAnd && nodeValue) || (!isAnd && !nodeValue)) {
			for (IModelConnection selectedConn : node.getIncomingConnections()) {
				NodeEvaluation newEvaluation = (NodeEvaluation) evaluation.clone();
				for (IModelConnection conn : node.getIncomingConnections()) {
					boolean value = isAnd ^ ((CEGConnection) conn).isNegate();
					ETag tag = conn == selectedConn ? ETag.ALL : ETag.ANY;
					checkAndSet(newEvaluation, (CEGNode) conn.getSource(), new TaggedBoolean(value, tag));
				}
				result.add(newEvaluation);
			}
			// case where node is false in AND case or node is true in OR case
		} else {
			for (IModelConnection selectedConn : node.getIncomingConnections()) {
				NodeEvaluation newEvaluation = (NodeEvaluation) evaluation.clone();
				for (IModelConnection conn : node.getIncomingConnections()) {
					boolean value = ((conn == selectedConn) ^ (isAnd ^ ((CEGConnection) conn).isNegate()));
					ETag tag = conn == selectedConn ? ETag.ALL : ETag.ANY;
					checkAndSet(newEvaluation, (CEGNode) conn.getSource(), new TaggedBoolean(value, tag));
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
	 * merged. Identifiey inconsistent evaluations
	 * 
	 * @throws SpecmateException
	 */
	private Pair<Set<NodeEvaluation>, Set<NodeEvaluation>> mergeCompatibleEvaluations(Set<NodeEvaluation> evaluations)
			throws SpecmateException {
		Set<NodeEvaluation> result = new HashSet<>();
		while (evaluations.size() > 0) {
			Set<NodeEvaluation> candidates = getMergeCandiate(evaluations);

			if (candidates.isEmpty()) {
				// There is no merge candidate:
				// The model has contradictory constraints e.g. (A ==> X) & (A
				// ==> !X)
				// Remaining evaluations are inconsistent
				return Pair.of(result, evaluations);
			}

			evaluations.removeAll(candidates);
			NodeEvaluation merged = mergeAllEvaluations(candidates);
			result.add(merged);
		}
		return Pair.of(result, Collections.emptySet());
	}

	private Set<NodeEvaluation> getMergeCandiate(Set<NodeEvaluation> evaluations) throws SpecmateException {
		// Map to track between logical variables and evaluations
		Map<Integer, NodeEvaluation> var2EvalMap = new HashMap<>();

		// Inititalize solver infrastructure
		IPBSolver solver = org.sat4j.pb.SolverFactory.newResolution();
		GateTranslator translator = new GateTranslator(solver);
		WeightedMaxSatDecorator maxSat = new WeightedMaxSatDecorator(solver);

		// We will need evaluations.size()+1 new variables, one set of varibles
		// e_n to switch on and off each evaluation and one variable s to enable
		// the implications s <==> (e=>n) where n is the evaluation result for a
		// certain node.
		// see pushEvaluations for the details
		int maxVar = getAdditionalVar(evaluations.size() + 1);
		maxSat.newVar(maxVar);

		try {
			pushCEGStructure(translator);
			var2EvalMap = pushEvaluations(evaluations, translator, maxSat, maxVar);
		} catch (ContradictionException c) {
			throw new SpecmateException(c);
		}
		try {
			int[] model = maxSat.findModel();
			return extractEnabledEvaluations(var2EvalMap, model);
		} catch (TimeoutException e) {
			throw new SpecmateException(e);
		}
	}

	private Set<NodeEvaluation> extractEnabledEvaluations(Map<Integer, NodeEvaluation> var2EvalMap, int[] model) {
		Set<NodeEvaluation> toMerge = new HashSet<>();
		for (int i = 0; i < model.length; i++) {
			int var = model[i];
			if (var <= 0) {
				continue;
			}
			NodeEvaluation eval = var2EvalMap.get(var);
			if (eval != null) {
				toMerge.add(eval);
			}
		}
		return toMerge;
	}

	private Map<Integer, NodeEvaluation> pushEvaluations(Set<NodeEvaluation> evaluations, GateTranslator translator,
			WeightedMaxSatDecorator maxSat, int maxVar) throws ContradictionException {
		Map<Integer, NodeEvaluation> var2EvalMap = new HashMap<>();

		int nextVar = 1;
		for (NodeEvaluation evaluation : evaluations) {
			int varForEval = getAdditionalVar(nextVar);
			var2EvalMap.put(varForEval, evaluation);
			nextVar++;
			for (IModelNode node : nodes) {
				int varForNode = getVarForNode(node);
				// maxSat.newVar(varForNode);
				TaggedBoolean value = evaluation.get(node);
				if (value != null) {
					if (value.value) {
						translator.or(maxVar, getVectorForVariables(-varForEval, varForNode));
					} else {
						translator.or(maxVar, getVectorForVariables(-varForEval, -varForNode));
					}
				}
			}
			maxSat.addSoftClause(1, getVectorForVariables(varForEval));
		}
		translator.gateTrue(maxVar);
		return var2EvalMap;
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

	private NodeEvaluation mergeAllEvaluations(Set<NodeEvaluation> clique) {
		NodeEvaluation evaluation = new NodeEvaluation();
		for (NodeEvaluation toMerge : clique) {
			evaluation.putAll(toMerge);
		}
		return evaluation;
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
		IModelNode node = getNodeForVar(varName);
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
		for (IModelNode node : nodes) {
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
		for (IModelNode node : nodes) {
			int varForNode = getVarForNode(node);
			IVecInt vector = getPredecessorVector(node);
			if (vector.size() > 0) {
				if (((CEGNode) node).getType() == NodeType.AND) {
					translator.and(varForNode, vector);
				} else {
					translator.or(varForNode, vector);
				}
			}
		}
	}

	/** Returns the CEG node for a given variable (given as int) */
	private IModelNode getNodeForVar(int i) {
		return nodes.get(i - 1);
	}

	/** Returns a variable (usable for SAT4J) for a given CEG node. */
	private int getVarForNode(IModelNode node) {
		return nodes.indexOf(node) + 1;
	}

	/** Returns a variable/value vector for all predeccessors of a node */
	private IVecInt getPredecessorVector(IModelNode node) {
		IVecInt vector = new VecInt();
		for (IModelConnection conn : node.getIncomingConnections()) {
			IModelNode pre = conn.getSource();
			int var = getVarForNode((CEGNode) pre);
			if (((CEGConnection) conn).isNegate()) {
				var *= -1;
			}
			vector.push(var);
		}
		return vector;
	}

}
