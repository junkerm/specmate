package com.specmate.testspecification.internal.services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.util.EcoreUtil.EqualityHelper;
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
import com.specmate.common.exception.SpecmateException;
import com.specmate.common.exception.SpecmateInternalException;
import com.specmate.model.administration.ErrorCode;
import com.specmate.model.base.BasePackage;
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
	
	private Comparator<NodeEvaluation> setComparator;

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
		Pair<SortedSet<NodeEvaluation>, SortedSet<NodeEvaluation>> evaluations = computeEvaluations();
		SortedSet<NodeEvaluation> consistent = evaluations.getLeft();
		SortedSet<NodeEvaluation> inconsistent = evaluations.getRight();
		int position = 0;
		for (NodeEvaluation evaluation : consistent) {
			TestCase testCase = createTestCase(evaluation, specification, true);
			testCase.setPosition(position++);
			specification.getContents().add(testCase);
		}
		List<TestCase> inconsistentTestCases = new ArrayList<TestCase>();
		for (NodeEvaluation evaluation : inconsistent) {
			TestCase testCase = createTestCase(evaluation, specification, false);
			boolean newTc = !inconsistentTestCases.stream().anyMatch(tc -> {
				EqualityHelper helper = new IdNamePositionIgnoreEqualityHelper();
				return helper.equals(tc, testCase);
			});
			if (newTc) {
				inconsistentTestCases.add(testCase);
				testCase.setPosition(position++);
				specification.getContents().add(testCase);
			} else {
				SpecmateEcoreUtil.detach(testCase);
			}
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
			assignment.setName(assignment.getId());
			assignment.setParameter(parameter);
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
	 * Node evaluations are a precursor to test cases. This method computes the node
	 * evaluations according to the rules in the Specmate systems requirements
	 * documentation.
	 *
	 * @param nodes
	 * @return
	 * @throws SpecmateException
	 */
	private Pair<SortedSet<NodeEvaluation>, SortedSet<NodeEvaluation>> computeEvaluations() throws SpecmateException {
		// TODO: fix ordering, SortedSet instead of Set
		SortedSet<NodeEvaluation> consistentEvaluations = getInitialEvaluations();
		SortedSet<NodeEvaluation> inconsistentEvaluations = new TreeSet<NodeEvaluation>(setComparator);
		SortedSet<NodeEvaluation> intermediateEvaluations = getIntermediateEvaluations(consistentEvaluations);
		while (!intermediateEvaluations.isEmpty()) {
			for (NodeEvaluation evaluation : intermediateEvaluations) {
				consistentEvaluations.remove(evaluation);
				Optional<IModelNode> intermediateNodeOpt = getAnyIntermediateNode(evaluation);
				AssertUtil.assertTrue(intermediateNodeOpt.isPresent());
				IModelNode node = intermediateNodeOpt.get();
				Pair<SortedSet<NodeEvaluation>, SortedSet<NodeEvaluation>> iterationResult = iterateEvaluation(evaluation, node);
				consistentEvaluations.addAll(iterationResult.getLeft());
				inconsistentEvaluations.addAll(iterationResult.getRight());
			}
			intermediateEvaluations = getIntermediateEvaluations(consistentEvaluations);
		}

		Pair<SortedSet<NodeEvaluation>, SortedSet<NodeEvaluation>> refinedEvaluations = refineEvaluations(consistentEvaluations);
		refinedEvaluations.getRight().addAll(inconsistentEvaluations);
		return refinedEvaluations;
	}

	private Pair<SortedSet<NodeEvaluation>, SortedSet<NodeEvaluation>> refineEvaluations(SortedSet<NodeEvaluation> evaluationList)
			throws SpecmateException {
		Pair<SortedSet<NodeEvaluation>, SortedSet<NodeEvaluation>> mergedEvals = mergeCompatibleEvaluations(evaluationList);
		SortedSet<NodeEvaluation> merged = mergedEvals.getLeft();
		SortedSet<NodeEvaluation> inconsistent = mergedEvals.getRight();
		SortedSet<NodeEvaluation> filled = new TreeSet<NodeEvaluation>(setComparator);
		for (NodeEvaluation eval : merged) {
			filled.add(fill(eval));
		}
		return Pair.of(filled, inconsistent);
	}

	/**
	 * Returns the inital evaluations for the CEG model, where all output nodes are
	 * set one time true and one time false.
	 */
	private SortedSet<NodeEvaluation> getInitialEvaluations() {
		initComparator();
		SortedSet<NodeEvaluation> evaluations = new TreeSet<NodeEvaluation>(setComparator);
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
	
	/**
	 * Initializes a comparator which defines the ordering of the SortedSets 
	 * containing the different evaluations
	 */
	private void initComparator () {
		setComparator = new Comparator<NodeEvaluation>() {

			@Override
			public int compare(NodeEvaluation nodeEval1, NodeEvaluation nodeEval2) {
				Set<Entry<IContainer, TaggedBoolean>> sortedSet1 = nodeEval1.entrySet();
				Set<Entry<IContainer, TaggedBoolean>> sortedSet2 = nodeEval2.entrySet();
				
				Iterator<Entry<IContainer, TaggedBoolean>> it1 = sortedSet1.iterator();
				Iterator<Entry<IContainer, TaggedBoolean>> it2 = sortedSet2.iterator();
				
				while(it1.hasNext() && it2.hasNext()) {
					Entry<IContainer, TaggedBoolean> entry1 = it1.next();
					Entry<IContainer, TaggedBoolean> entry2 = it2.next();
					CEGNode n1 = (CEGNode)entry1.getKey();
					CEGNode n2 = (CEGNode)entry2.getKey();
					
					int result = n1.getVariable().compareTo(n2.getVariable());
					
					/*
					 * Compare properties until the differ
					 * Check order: 
					 * 		1. Compare names of nodes 
					 * 		2. Compare conditions of nodes
					 * 		3. Compare taggedBoolean value property of nodes
					 * 		4. Compare taggedBoolean tag property of nodes
					 * */
					
					if(result != 0) {
						return result;
					} else {
						// Check if the condition of the nodes is also the same
						int conditionResult = n1.getCondition().compareTo(n2.getCondition());
						if(result != 0) {
							return conditionResult;
						} else {
							TaggedBoolean taggedBoolean1 = entry1.getValue();
							TaggedBoolean taggedBoolean2 = entry2.getValue();
							/*	
							 * Check if the taggedBoolean values are also equal
							 * Order: false < true
							 */
							int boolResult = Boolean.compare(taggedBoolean1.value, taggedBoolean2.value);
							if (boolResult != 0) {
								return boolResult;
							} else {
								/*
								 * Values are also equal, check if the tags are equal
								 * Order: all < any < auto
								 */
								int tagResult = taggedBoolean1.tag.compareTo(taggedBoolean2.tag);
								if (tagResult != 0) {
									return tagResult;
								}
							}
						}
					}	 
				}
				/*
				 * Both or one of the sets has come to an end
				 * Either one set of evaluation is a subset of the other, or they are the same
				 */

				if (!it1.hasNext() && !it2.hasNext()) {
					/*
					 * Both sets are the same
					 * Even if the current test generation heuristic does not create two sets which are the same,
					 * we still need this in order to delete evaluations from the set
					 */ 
					return 0;
				} else if (!it1.hasNext()) {
					// set1 is smaller, set nodeEval1 < nodeEval2
					return -1;
				} else {
					// set2 is smaller, set nodeEval2 < nodeEval1
					return 1;
				}
			}
		};
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
	 * Returns evaluations that have intermediate nodes (i.e. nodes that have to be
	 * evaluated)
	 */
	private SortedSet<NodeEvaluation> getIntermediateEvaluations(SortedSet<NodeEvaluation> evaluations) {
		SortedSet<NodeEvaluation> intermediate = new TreeSet<NodeEvaluation>(setComparator);
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
	private Pair<SortedSet<NodeEvaluation>, SortedSet<NodeEvaluation>> iterateEvaluation(NodeEvaluation evaluation, IModelNode node)
			throws SpecmateException {
		SortedSet<NodeEvaluation> consistent = new TreeSet<NodeEvaluation>(setComparator);
		SortedSet<NodeEvaluation> inconsistent = new TreeSet<NodeEvaluation>(setComparator);
		AssertUtil.assertEquals(evaluation.get(node).tag, ETag.ALL);
		switch (((CEGNode) node).getType()) {
		case AND:
			handleAllCase(true, evaluation, node, consistent, inconsistent);
			break;
		case OR:
			handleAllCase(false, evaluation, node, consistent, inconsistent);
			break;
		}
		return Pair.of(consistent, inconsistent);
	}

	private void handleAllCase(boolean isAnd, NodeEvaluation evaluation, IModelNode node,
			SortedSet<NodeEvaluation> consistent, SortedSet<NodeEvaluation> inconsistent) throws SpecmateException {
		boolean nodeValue = evaluation.get(node).value;
		boolean failure;
		// case where node is true in AND case or node is false in OR case
		if ((isAnd && nodeValue) || (!isAnd && !nodeValue)) {
			for (IModelConnection selectedConn : node.getIncomingConnections()) {
				NodeEvaluation newEvaluation = (NodeEvaluation) evaluation.clone();
				failure = false;
				for (IModelConnection conn : node.getIncomingConnections()) {
					boolean value = isAnd ^ ((CEGConnection) conn).isNegate();
					ETag tag = conn == selectedConn ? ETag.ALL : ETag.ANY;
					failure = failure
							|| !checkAndSet(newEvaluation, (CEGNode) conn.getSource(), new TaggedBoolean(value, tag));
				}
				if (!failure) {
					consistent.add(newEvaluation);
				} else {
					inconsistent.add(newEvaluation);
				}
			}
			// case where node is false in AND case or node is true in OR case
		} else {
			for (IModelConnection selectedConn : node.getIncomingConnections()) {
				NodeEvaluation newEvaluation = (NodeEvaluation) evaluation.clone();
				failure = false;
				for (IModelConnection conn : node.getIncomingConnections()) {
					boolean value = ((conn == selectedConn) ^ (isAnd ^ ((CEGConnection) conn).isNegate()));
					ETag tag = conn == selectedConn ? ETag.ALL : ETag.ANY;
					failure = failure
							|| !checkAndSet(newEvaluation, (CEGNode) conn.getSource(), new TaggedBoolean(value, tag));
				}
				if (!failure) {
					consistent.add(newEvaluation);
				} else {
					inconsistent.add(newEvaluation);
				}
			}
		}
	}

	/**
	 * Sets the value of a node in an evaluation but checks first if it is already
	 * set with a different value
	 *
	 * @return false if an inconsistent value would be set in the node
	 */
	private boolean checkAndSet(NodeEvaluation evaluation, CEGNode node, TaggedBoolean effectiveValue)
			throws SpecmateException {
		if (evaluation.containsKey(node) && evaluation.get(node).value != effectiveValue.value) {
			return false;
		} else {
			evaluation.put(node, effectiveValue);
			return true;
		}
	}

	/**
	 * Runs through the list of evaluations and merges the ones that can be merged.
	 * Identify inconsistent evaluations
	 *
	 * @throws SpecmateException
	 */
	private Pair<SortedSet<NodeEvaluation>, SortedSet<NodeEvaluation>> mergeCompatibleEvaluations(SortedSet<NodeEvaluation> evaluations)
			throws SpecmateException {
		SortedSet<NodeEvaluation> result = new TreeSet<NodeEvaluation>(setComparator);
		while (evaluations.size() > 0) {
			SortedSet<NodeEvaluation> candidates = getMergeCandiate(evaluations);

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
		return Pair.of(result, new TreeSet<>(setComparator));
	}

	private SortedSet<NodeEvaluation> getMergeCandiate(SortedSet<NodeEvaluation> evaluations) throws SpecmateException {
		// Map to track between logical variables and evaluations
		TreeMap<Integer, NodeEvaluation> var2EvalMap = new TreeMap<Integer, NodeEvaluation>(new Comparator<Integer>() {
			@Override 
			public int compare(Integer i1, Integer i2) {
				return Integer.compare(i1, i2);
			}
		});

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
			throw new SpecmateInternalException(ErrorCode.TESTGENERATION, c);
		}
		try {
			int[] model = maxSat.findModel();
			return extractEnabledEvaluations(var2EvalMap, model);
		} catch (TimeoutException e) {
			throw new SpecmateInternalException(ErrorCode.TESTGENERATION, e);
		}
	}

	private SortedSet<NodeEvaluation> extractEnabledEvaluations(TreeMap<Integer, NodeEvaluation> var2EvalMap, int[] model) {
		SortedSet<NodeEvaluation> toMerge = new TreeSet<>(setComparator);
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

	private TreeMap<Integer, NodeEvaluation> pushEvaluations(SortedSet<NodeEvaluation> evaluations, GateTranslator translator,
			WeightedMaxSatDecorator maxSat, int maxVar) throws ContradictionException {
		TreeMap<Integer, NodeEvaluation> var2EvalMap = new TreeMap<Integer, NodeEvaluation>(new Comparator<Integer>() {
			@Override 
			public int compare(Integer i1, Integer i2) {
				return Integer.compare(i1, i2);
			}
		});

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

	private IVecInt getVectorForVariables(Integer... vars) {
		IVecInt vector = new VecInt(vars.length + 1);
		for (int i = 0; i < vars.length; i++) {
			vector.push(vars[i]);
		}
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
				throw new SpecmateInternalException(ErrorCode.TESTGENERATION,
						"Could not determine consistent test values.");
			}
			for (int v : model) {
				setModelValue(evaluation, filled, v);
			}
			return filled;
		} catch (TimeoutException e) {
			throw new SpecmateInternalException(ErrorCode.TESTGENERATION, e);
		}
	}

	/**
	 * Sets the value in an evaluation based on an original evaluation and a model
	 * value.
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
			throw new SpecmateInternalException(ErrorCode.TESTGENERATION, e);
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

	/** Feeds constraints representing the CEG structure to the solver */
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
		pushMutualExclusiveConstraints(translator);
	}

	private void pushMutualExclusiveConstraints(GateTranslator translator) throws ContradictionException {
		Collection<Collection<CEGNode>> mutualExclusiveNodeSets = getMutualExclusiveNodeSets();
		for (Collection<CEGNode> mutexSet : mutualExclusiveNodeSets) {
			Integer[] variables = mutexSet.stream().map(node -> getVarForNode(node)).collect(Collectors.toList())
					.toArray(new Integer[0]);
			translator.addAtMost(getVectorForVariables(variables), 1);
		}
	}

	private Collection<Collection<CEGNode>> getMutualExclusiveNodeSets() {
		Collection<Collection<CEGNode>> result = new ArrayList<>();
		Map<String, Set<CEGNode>> multiMap = new HashMap<String, Set<CEGNode>>();
		for (IModelNode node : nodes) {
			CEGNode cegNode = (CEGNode) node;
			if (cegNode.getCondition().trim().startsWith("=")) {
				String variable = cegNode.getVariable();
				if (!multiMap.containsKey(variable)) {
					multiMap.put(variable, new HashSet<CEGNode>());
				}
				multiMap.get(variable).add(cegNode);
			}
		}
		for (String key : multiMap.keySet()) {
			result.add(multiMap.get(key));
		}
		return result;
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
			int var = getVarForNode(pre);
			if (((CEGConnection) conn).isNegate()) {
				var *= -1;
			}
			vector.push(var);
		}
		return vector;
	}

	/**
	 * Equality checker that ignores differences in the fields id, name and position
	 */
	@SuppressWarnings("serial")
	private class IdNamePositionIgnoreEqualityHelper extends EqualityHelper {
		@Override
		protected boolean haveEqualFeature(EObject eObject1, EObject eObject2, EStructuralFeature feature) {
			if (feature == BasePackage.Literals.IID__ID) {
				return true;
			}
			if (feature == BasePackage.Literals.INAMED__NAME) {
				return true;
			}
			if (feature == BasePackage.Literals.IPOSITIONABLE__POSITION) {
				return true;
			}
			return super.haveEqualFeature(eObject1, eObject2, feature);
		}
	}

}
