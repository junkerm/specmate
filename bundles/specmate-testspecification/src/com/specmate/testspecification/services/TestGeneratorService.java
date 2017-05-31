package com.specmate.testspecification.services;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.BinaryOperator;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.emf.ecore.EObject;
import org.osgi.service.component.annotations.Component;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import com.specmate.common.AssertUtil;
import com.specmate.common.SpecmateException;
import com.specmate.common.SpecmateValidationException;
import com.specmate.emfrest.api.IRestService;
import com.specmate.emfrest.api.RestServiceBase;
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
import com.specmate.testspecification.services.TaggedBoolean.ETag;

/**
 * Service for generating test cases for a test specification that is linked to
 * a CEG model.
 * 
 * @author junkerm
 */
@Component(immediate = true, service = IRestService.class)
public class TestGeneratorService extends RestServiceBase {

	/** {@inheritDoc} */
	@Override
	public String getServiceName() {
		return "generateTests";
	}

	/** {@inheritDoc} */
	@Override
	public boolean canPost(Object target, EObject object) {
		return target instanceof TestSpecification;
	}

	/** {@inheritDoc} */
	@Override
	public Object post(Object target, EObject object) throws SpecmateValidationException, SpecmateException {
		TestSpecification specification = (TestSpecification) target;
		EObject container = specification.eContainer();
		if (!(container instanceof CEGModel)) {
			throw new SpecmateValidationException(
					"To generate test cases, the test specification must be associcated to a ceg model");
		}

		adaptSpecificationAndGenerateTestCases(specification);

		return null;
	}

	/**
	 * Adds necessary input and output parameters to the specification and
	 * generates test cases
	 */
	private void adaptSpecificationAndGenerateTestCases(TestSpecification specification) throws SpecmateException {
		CEGModel cegModel = (CEGModel) specification.eContainer();
		List<CEGNode> nodes = SpecmateEcoreUtil.pickInstancesOf(cegModel.getContents(), CEGNode.class);

		generateParameters(specification, nodes);
		generateTestCases(specification, nodes);
	}

	/** Adds necessary parameters to the specification */
	private void generateParameters(TestSpecification specification, List<CEGNode> nodes) {
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
	private void generateTestCases(TestSpecification specification, List<CEGNode> nodes) throws SpecmateException {
		Set<NodeEvaluation> evaluations = computeEvaluations(nodes);
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
	private Set<NodeEvaluation> computeEvaluations(List<CEGNode> nodes) throws SpecmateException {
		Set<NodeEvaluation> evaluationList = getInitialEvaluations(nodes);
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
		Set<NodeEvaluation> merged = mergeAllEvaluations(evaluationList);
		return merged;
	}

	private Set<NodeEvaluation> mergeAllEvaluations(Set<NodeEvaluation> evaluationList) {
		Set<NodeEvaluation> from = new HashSet<>(evaluationList);
		Set<NodeEvaluation> to = new HashSet<>();
		boolean mergeHappened = true;
		while (mergeHappened) {
			mergeHappened = false;
			for (NodeEvaluation evaluation : from) {
				boolean evaluationMerged = false;
				for (NodeEvaluation check : to) {
					if (canBeMerged(evaluation, check)) {
						check.putAll(evaluation);
						mergeHappened = true;
						evaluationMerged = true;
						break;
					}
				}
				if (!evaluationMerged) {
					to.add(evaluation);
				}
			}
			from.clear();
			from.addAll(to);
			to.clear();
		}
		return from;
	}

	private boolean canBeMerged(NodeEvaluation from, NodeEvaluation to) {
		for (CEGNode node : from.keySet()) {
			if (to.containsKey(node)) {
				TaggedBoolean toTaggedValue = to.get(node);
				TaggedBoolean fromTaggedValue = from.get(node);
				if ((toTaggedValue.tag != ETag.DONT_CARE || fromTaggedValue.tag != ETag.DONT_CARE)
						&& (toTaggedValue.value != fromTaggedValue.value)) {
					return false;
				}
			}
		}
		NodeEvaluation test = new NodeEvaluation();
		test.putAll(to);
		test.putAll(from);
		return checkConsistency(test);
	}

	private boolean checkConsistency(NodeEvaluation test) {
		for (CEGNode node : test.keySet()) {
			if (node.getIncomingConnections().isEmpty()) {
				continue;
			}
			boolean value = test.get(node).value;
			BinaryOperator<Boolean> operator;
			boolean init;
			if (node.getType() == NodeType.AND) {
				operator = (a, b) -> a && b;
				init = true;
			} else {
				operator = (a, b) -> a || b;
				init = false;
			}

			boolean testValue = node.getIncomingConnections().stream().map(c -> test.get(c.getSource()).value)
					.reduce(init, operator);
			if (testValue != value) {
				return false;
			}
		}
		return true;
	}

	private Optional<CEGNode> getAnyIntermediateNode(NodeEvaluation evaluation) {
		for (CEGNode node : evaluation.keySet()) {
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

	private Set<NodeEvaluation> getIntermediateEvaluations(Set<NodeEvaluation> evaluations) {
		HashSet<NodeEvaluation> intermediate = new HashSet<>();
		for (NodeEvaluation evaluation : evaluations) {
			if (getAnyIntermediateNode(evaluation).isPresent()) {
				intermediate.add(evaluation);
			}
		}
		return intermediate;
	}

	private Set<NodeEvaluation> iterateEvaluation(NodeEvaluation evaluation, CEGNode node) throws SpecmateException {
		Set<NodeEvaluation> result = new HashSet<>();
		switch (node.getType()) {
		case AND:
			if (evaluation.get(node).tag == ETag.ALL) {
				handleAllCase(true, evaluation, node, result);
			} else {
				handleAnyCase(true, evaluation, node, result);
			}
			break;
		case OR:
			if (evaluation.get(node).tag == ETag.ALL) {
				handleAllCase(false, evaluation, node, result);
			} else {
				handleAnyCase(false, evaluation, node, result);
			}
			break;
		}
		return result;
	}

	private void handleAllCase(boolean isAnd, NodeEvaluation evaluation, CEGNode node, Set<NodeEvaluation> result)
			throws SpecmateException {
		if (isAnd ^ (!evaluation.get(node).value)) {
			for (CEGConnection selectedConn : node.getIncomingConnections()) {
				NodeEvaluation newEvaluation = (NodeEvaluation) evaluation.clone();
				for (CEGConnection conn : node.getIncomingConnections()) {
					boolean value = isAnd ^ conn.isNegate();
					ETag tag = conn == selectedConn ? ETag.ALL : ETag.ANY;
					checkAndSet(newEvaluation, conn.getSource(), new TaggedBoolean(value, tag));
				}
				result.add(newEvaluation);
			}
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

	private void handleAnyCase(boolean isAnd, NodeEvaluation evaluation, CEGNode node, Set<NodeEvaluation> result)
			throws SpecmateException {
		if (isAnd ^ (!evaluation.get(node).value)) {

			for (CEGConnection conn : node.getIncomingConnections()) {
				boolean value = isAnd ^ conn.isNegate();
				ETag tag = ETag.DONT_CARE;
				checkAndSet(evaluation, conn.getSource(), new TaggedBoolean(value, tag));
			}
			result.add(evaluation);

		} else {
			for (CEGConnection selectedConn : node.getIncomingConnections()) {
				NodeEvaluation newEvaluation = (NodeEvaluation) evaluation.clone();
				for (CEGConnection conn : node.getIncomingConnections()) {
					boolean value = ((conn == selectedConn) ^ (isAnd ^ conn.isNegate()));
					ETag tag = ETag.DONT_CARE;
					checkAndSet(newEvaluation, conn.getSource(), new TaggedBoolean(value, tag));
				}
				result.add(newEvaluation);
			}
		}
	}

	private void checkAndSet(NodeEvaluation evaluation, CEGNode node, TaggedBoolean effectiveValue)
			throws SpecmateException {
		if (evaluation.containsKey(node) && evaluation.get(node).value != effectiveValue.value) {
			throw new SpecmateException("Inconsistent value in evaluation");
		} else {
			evaluation.put(node, effectiveValue);
		}
	}

	private Set<NodeEvaluation> getInitialEvaluations(List<CEGNode> nodes) {
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
}
