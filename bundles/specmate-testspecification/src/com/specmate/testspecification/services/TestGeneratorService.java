package com.specmate.testspecification.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
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

@Component(immediate = true, service = IRestService.class)
public class TestGeneratorService extends RestServiceBase {

	@Override
	public String getServiceName() {
		return "generateTests";
	}

	@Override
	public boolean canPost(Object target, EObject object) {
		return target instanceof TestSpecification;
	}

	@Override
	public Object post(Object target, EObject object) throws SpecmateValidationException, SpecmateException {
		TestSpecification specification = (TestSpecification) target;
		EObject container = specification.eContainer();
		if (!(container instanceof CEGModel)) {
			throw new SpecmateValidationException(
					"To generate test cases, the test specification must be associcated to a ceg model");
		}

		generateTestCases(specification);

		return null;
	}

	private void generateTestCases(TestSpecification specification) throws SpecmateException {
		CEGModel cegModel = (CEGModel) specification.eContainer();
		List<CEGNode> nodes = SpecmateEcoreUtil.pickInstancesOf(cegModel.getContents(), CEGNode.class);

		generateParameters(specification, nodes);
		generateTestCases(specification, nodes);
	}

	private void generateParameters(TestSpecification specification, List<CEGNode> nodes) {
		for (CEGNode node : nodes) {
			String name = node.getVariable();
			ParameterType type = determineParameterTypeForNode(node);
			if (type != null && !parameterExists(specification, name, type)) {
				TestParameter parameter = TestspecificationFactory.eINSTANCE.createTestParameter();
				parameter.setName(name);
				parameter.setType(type);
				specification.getContents().add(parameter);
			}
		}
	}

	private ParameterType determineParameterTypeForNode(CEGNode node) {
		if (node.getIncomingConnections().isEmpty()) {
			return ParameterType.INPUT;
		} else if (node.getOutgoingConnections().isEmpty()) {
			return ParameterType.OUTPUT;
		} else {
			return null;
		}
	}

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

	private void generateTestCases(TestSpecification specification, List<CEGNode> nodes) throws SpecmateException {
		Set<NodeEvaluation> evaluations = computeEvaluations(nodes);
		for (NodeEvaluation evaluation : evaluations) {
			TestCase testCase = createTestCase(evaluation, specification);
			specification.getContents().add(testCase);
		}
	}

	private TestCase createTestCase(NodeEvaluation evaluation, TestSpecification specification) {
		TestCase testCase = TestspecificationFactory.eINSTANCE.createTestCase();
		List<TestParameter> parameters = SpecmateEcoreUtil.pickInstancesOf(specification.getContents(),
				TestParameter.class);
		Multimap<String, CEGNode> variableToNodeMap = ArrayListMultimap.create();
		evaluation.keySet().stream().forEach(n -> variableToNodeMap.put(n.getVariable(), n));
		for (TestParameter parameter : parameters) {
			List<String> constraints = new ArrayList<>();
			for (CEGNode node : variableToNodeMap.get(parameter.getName())) {
				Boolean nodeEval = evaluation.get(node);
				String value = node.getValue();
				String operator = node.getOperator();
				String parameterValue = buildParameterValue(operator, value, nodeEval);
				constraints.add(parameterValue);
			}
			String parameterValue = StringUtils.join(constraints, ",");
			ParameterAssignment assignment = TestspecificationFactory.eINSTANCE.createParameterAssignment();
			assignment.setParameter(parameter);
			assignment.setValue(parameterValue);
			testCase.getContents().add(assignment);
		}
		return testCase;
	}

	private String buildParameterValue(String operator, String value, Boolean nodeEval) {
		List<String> knownOperators = Arrays.asList("<", "<=", ">", ">=", "=", "is");
		if (!nodeEval) {
			if (knownOperators.contains(operator)) {
				return negateOperator(operator) + value;
			} else {
				return "not " + operator + value;
			}
		} else {
			return operator + value;
		}

	}

	private String negateOperator(String operator) {
		switch (operator) {
		case "<":
			operator = ">=";
			break;
		case "<=":
			operator = ">";
			break;
		case ">":
			operator = "<=";
			break;
		case ">=":
			operator = "<";
			break;
		case "=":
			operator = "!=";
			break;
		case "is":
			operator = "is not";
			break;
		}
		return operator;
	}

	private Set<NodeEvaluation> computeEvaluations(List<CEGNode> nodes) throws SpecmateException {
		Set<NodeEvaluation> evaluationList = getInitialEvaluations(nodes);
		Set<NodeEvaluation> intermediateEvaluations = getIntermediateEvaluations(evaluationList);
		while (!intermediateEvaluations.isEmpty()) {
			for (NodeEvaluation evaluation : intermediateEvaluations) {
				evaluationList.remove(evaluation);
				for (CEGNode node : getIntermediateNodes(evaluation)) {
					evaluationList.addAll(iterateEvaluation(evaluation, node));
				}
			}
			intermediateEvaluations = getIntermediateEvaluations(evaluationList);
		}
		return evaluationList;
	}

	private Set<CEGNode> getIntermediateNodes(NodeEvaluation evaluation) {
		Set<CEGNode> result = new HashSet<CEGNode>();
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
				for (CEGConnection conn : node.getIncomingConnections()) {
					NodeEvaluation newEvaluation = (NodeEvaluation) evaluation.clone();
					checkAndSet(newEvaluation, conn.getSource(), conn.isNegate());
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
				for (CEGConnection conn : node.getIncomingConnections()) {
					NodeEvaluation newEvaluation = (NodeEvaluation) evaluation.clone();
					checkAndSet(newEvaluation, conn.getSource(), !conn.isNegate());
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
		NodeEvaluation initialEvaluation = new NodeEvaluation();
		nodes.stream().filter(node -> (determineParameterTypeForNode(node) == ParameterType.OUTPUT))
				.forEach(node -> initialEvaluation.put(node, true));
		Set<NodeEvaluation> evaluations = new HashSet<>();
		evaluations.add(initialEvaluation);
		return evaluations;
	}

	private class NodeEvaluation extends HashMap<CEGNode, Boolean> {

	}
}
