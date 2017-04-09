package com.specmate.testspecification.services;

import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.osgi.service.component.annotations.Component;

import com.specmate.common.SpecmateValidationException;
import com.specmate.emfrest.api.IRestService;
import com.specmate.emfrest.api.RestServiceBase;
import com.specmate.model.requirements.CEGModel;
import com.specmate.model.requirements.CEGNode;
import com.specmate.model.support.util.SpecmateEcoreUtil;
import com.specmate.model.testspecification.ParameterType;
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
	public Object post(Object target, EObject object) throws SpecmateValidationException {
		TestSpecification specification = (TestSpecification) target;
		EObject container = specification.eContainer();
		if (!(container instanceof CEGModel)) {
			throw new SpecmateValidationException(
					"To generate test cases, the test specification must be associcated to a ceg model");
		}

		generateTestCases(specification);

		return null;
	}

	private void generateTestCases(TestSpecification specification) {
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

	private void generateTestCases(TestSpecification specification, List<CEGNode> nodes) {
		// TODO Auto-generated method stub

	}
}
