package com.specmate.testspecification.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.emf.ecore.EObject;
import org.osgi.service.component.annotations.Component;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import com.specmate.common.SpecmateException;
import com.specmate.common.SpecmateValidationException;
import com.specmate.emfrest.api.IRestService;
import com.specmate.emfrest.api.RestServiceBase;
import com.specmate.model.requirements.CEGConnection;
import com.specmate.model.requirements.CEGModel;
import com.specmate.model.requirements.CEGNode;
import com.specmate.model.support.util.SpecmateEcoreUtil;
import com.specmate.model.testspecification.ParameterAssignment;
import com.specmate.model.testspecification.ParameterType;
import com.specmate.model.testspecification.TestCase;
import com.specmate.model.testspecification.TestParameter;
import com.specmate.model.testspecification.TestSpecification;
import com.specmate.model.testspecification.TestspecificationFactory;

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
				Boolean nodeEval = evaluation.get(node);
				String condition = node.getCondition();
				if (nodeEval != null) {
					String parameterValue = buildParameterValue(condition, nodeEval);
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
		// Set<NodeEvaluation> intermediateEvaluations =
		// getIntermediateEvaluations(evaluationList);
		// while (!intermediateEvaluations.isEmpty()) {
		for (NodeEvaluation evaluation : evaluationList) {
			evaluationList.remove(evaluation);
			for (CEGNode node : getIntermediateNodes(evaluation)) {
				evaluationList.addAll(iterateEvaluation(evaluation, node));
			}
		}
		// intermediateEvaluations = getIntermediateEvaluations(evaluationList);
		// }
		return mergeAllEvaluations(evaluationList);
	}

	private Set<NodeEvaluation> mergeAllEvaluations(Set<NodeEvaluation> evaluationList) {
		Set<NodeEvaluation> from = evaluationList;
		Set<NodeEvaluation> to = new HashSet<>();
		while (true) {
			boolean mergeHappened = true;
			for (NodeEvaluation evaluation : from) {
				for (NodeEvaluation check : to) {
					if (canBeMerged(from, to)) {
						to.addAll(from);
					}
				}
			}
		}
	}

	private Set<NodeEvaluation> mergeEvaluationsWithSameInput(Set<NodeEvaluation> evaluations)
			throws SpecmateException {
		Map<NodeEvaluation, NodeEvaluation> inputMap = new HashMap<>();
		for (NodeEvaluation evaluation : evaluations) {
			NodeEvaluation input = extractInput(evaluation);
			NodeEvaluation matchingInput = getMatchingInput(inputMap, input);
			if (matchingInput != null) {
				NodeEvaluation matchedEvaluation = inputMap.get(matchingInput);
				inputMap.remove(matchingInput);
				matchingInput.putAll(input);
				matchedEvaluation.putAll(input);
				evaluation.putAll(matchedEvaluation);
				inputMap.put(matchedEvaluation, mergeEvaluations(matchedEvaluation, evaluation));
			} else {
				inputMap.put(input, evaluation);
			}
		}
		return new HashSet<NodeEvaluation>(inputMap.values());
	}

	private NodeEvaluation getMatchingInput(Map<NodeEvaluation, NodeEvaluation> inputMap, NodeEvaluation input) {
		for (NodeEvaluation existingInput : inputMap.keySet()) {
			boolean matches = true;
			for (CEGNode node : existingInput.keySet()) {
				if (input.containsKey(node)) {
					matches = matches && input.get(node).equals(existingInput.get(node));
				}
			}
			if (matches) {
				return existingInput;
			}
		}
		return null;
	}

	private NodeEvaluation mergeEvaluations(NodeEvaluation existing, NodeEvaluation evaluation)
			throws SpecmateException {
		NodeEvaluation merged = new NodeEvaluation();
		Set<CEGNode> allNodes = new HashSet<CEGNode>();
		allNodes.addAll(existing.keySet());
		allNodes.addAll(evaluation.keySet());
		for (CEGNode node : allNodes) {
			ParameterType type = determineParameterTypeForNode(node);
			if (type == ParameterType.OUTPUT) {
				if (existing.containsKey(node)) {
					if (evaluation.containsKey(node) && !existing.get(node).equals(evaluation.get(node))) {
						throw new SpecmateException("Test Case with incompatible outputs but same inputs");
					}
					merged.put(node, existing.get(node));
				} else {
					merged.put(node, evaluation.get(node));
				}
			} else {
				merged.put(node, existing.get(node));
			}
		}
		return merged;
	}

	private NodeEvaluation extractInput(NodeEvaluation evaluation) {
		NodeEvaluation input = new NodeEvaluation();
		for (CEGNode node : evaluation.keySet()) {
			ParameterType type = determineParameterTypeForNode(node);
			if (type == ParameterType.INPUT) {
				input.put(node, evaluation.get(node));
			}
		}
		return input;
	}

	private Set<CEGNode> getIntermediateNodes(NodeEvaluation evaluation) {
		Set<CEGNode> result = new HashSet<>();
		for (CEGNode node : evaluation.keySet()) {
			if (determineParameterTypeForNode(node) != ParameterType.INPUT) {
				boolean handled = node.getIncomingConnections().stream().map(conn -> conn.getSource())
						.allMatch(n -> evaluation.containsKey(n));
				if (!handled) {
					result.add(node);
				}
			}
		}
		return result;
	}

	private Set<NodeEvaluation> getIntermediateEvaluations(Set<NodeEvaluation> evaluations) {
		HashSet<NodeEvaluation> intermediate = new HashSet<>();
		for (NodeEvaluation evaluation : evaluations) {
			if (!getIntermediateNodes(evaluation).isEmpty()) {
				intermediate.add(evaluation);
			}
		}
		return intermediate;
	}

	private Set<NodeEvaluation> iterateEvaluation(NodeEvaluation evaluation, CEGNode node) throws SpecmateException {
		Set<NodeEvaluation> result = new HashSet<>();
		switch (node.getType()) {
		case AND:
			if (evaluation.get(node)) {
				NodeEvaluation newEvaluation = (NodeEvaluation) evaluation.clone();
				for (CEGConnection conn : node.getIncomingConnections()) {
					checkAndSet(newEvaluation, conn.getSource(), !conn.isNegate());
				}
				result.add(newEvaluation);
			} else {
				for (CEGConnection selectedConn : node.getIncomingConnections()) {
					NodeEvaluation newEvaluation = (NodeEvaluation) evaluation.clone();
					for (CEGConnection conn : node.getIncomingConnections()) {
						checkAndSet(newEvaluation, conn.getSource(), ((conn == selectedConn) ^ (!conn.isNegate())));
					}
					result.add(newEvaluation);
				}
			}
			break;
		case OR:
			if (!evaluation.get(node)) {
				NodeEvaluation newEvaluation = (NodeEvaluation) evaluation.clone();
				for (CEGConnection conn : node.getIncomingConnections()) {
					checkAndSet(newEvaluation, conn.getSource(), conn.isNegate());
				}
				result.add(newEvaluation);
			} else {
				for (CEGConnection selectedConn : node.getIncomingConnections()) {
					NodeEvaluation newEvaluation = (NodeEvaluation) evaluation.clone();
					for (CEGConnection conn : node.getIncomingConnections()) {
						checkAndSet(newEvaluation, conn.getSource(), ((conn == selectedConn) ^ (conn.isNegate())));
					}
					result.add(newEvaluation);
				}
			}
			break;
		}
		return result;
	}

	private void checkAndSet(NodeEvaluation evaluation, CEGNode node, Boolean value) throws SpecmateException {
		if (evaluation.containsKey(node) && evaluation.get(node) != value) {
			throw new SpecmateException("Inconsistent value in evaluation");
		} else {
			evaluation.put(node, value);
		}
	}

	private Set<NodeEvaluation> getInitialEvaluations(List<CEGNode> nodes) {
		Set<NodeEvaluation> evaluations = new HashSet<>();
		nodes.stream().filter(node -> (determineParameterTypeForNode(node) != ParameterType.INPUT)).forEach(node -> {
			NodeEvaluation positiveEvaluation = new NodeEvaluation();
			positiveEvaluation.put(node, true);
			evaluations.add(positiveEvaluation);
			NodeEvaluation negativeEvaluation = new NodeEvaluation();
			negativeEvaluation.put(node, false);
			evaluations.add(negativeEvaluation);
		});

		return evaluations;
	}

	private class NodeEvaluation extends HashMap<CEGNode, Boolean> {

	}
}
