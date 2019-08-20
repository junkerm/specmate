package com.specmate.testspecification.internal.services;

import java.util.List;

import com.specmate.common.AssertUtil;
import com.specmate.common.exception.SpecmateException;
import com.specmate.model.base.IContainer;
import com.specmate.model.base.IModelNode;
import com.specmate.model.support.util.SpecmateEcoreUtil;
import com.specmate.model.testspecification.ParameterAssignment;
import com.specmate.model.testspecification.ParameterType;
import com.specmate.model.testspecification.TestCase;
import com.specmate.model.testspecification.TestParameter;
import com.specmate.model.testspecification.TestSpecification;
import com.specmate.model.testspecification.TestspecificationFactory;

public abstract class TestCaseGeneratorBase<M extends IContainer, N extends IModelNode> {

	protected TestSpecification specification;
	protected M model;
	protected List<IModelNode> nodes;

	@SuppressWarnings("unchecked")
	public TestCaseGeneratorBase(TestSpecification specification, Class<M> modelClass, Class<N> nodeClass) {
		AssertUtil.assertInstanceOf(specification.eContainer(), modelClass);
		this.specification = specification;
		this.model = (M) specification.eContainer();
		this.nodes = SpecmateEcoreUtil.pickInstancesOf(model.getContents(), IModelNode.class);
	}

	/** Adds necessary parameters to the specification */
	protected abstract void generateParameters();

	protected abstract void generateTestCases() throws SpecmateException;

	/** Starts the test case generation */
	public void generate() throws SpecmateException {
		this.adaptSpecificationAndGenerateTestCases();
	}

	/**
	 * Adds necessary input and output parameters to the specification and generates
	 * test cases
	 */
	private void adaptSpecificationAndGenerateTestCases() throws SpecmateException {
		generateParameters();
		generateTestCases();
	}

	/**
	 * Creates a test parameter
	 *
	 * @param name the name of the parameter
	 * @param type the parameter's type
	 * @return the new test parameter.
	 */
	protected TestParameter createTestParameter(String name, ParameterType type) {
		TestParameter parameter = TestspecificationFactory.eINSTANCE.createTestParameter();
		parameter.setId(SpecmateEcoreUtil.getIdForChild(specification, parameter.eClass()));
		parameter.setName(name);
		parameter.setType(type);
		return parameter;
	}

	/**
	 * Creates a test parameter assignment
	 *
	 */
	protected ParameterAssignment createParameterAssignment(TestCase tc, TestParameter parameter, String value) {
		ParameterAssignment assignment = TestspecificationFactory.eINSTANCE.createParameterAssignment();
		assignment.setId(SpecmateEcoreUtil.getIdForChild(tc, assignment.eClass()));
		assignment.setName(parameter.getName());
		assignment.setParameter(parameter);
		assignment.setCondition(value);
		return assignment;
	}

	/**
	 * Creates a new test case
	 *
	 * @param specification the specification to create the test case in.
	 * @return the new test case
	 */
	protected TestCase createTestCase(TestSpecification specification) {
		TestCase testCase = TestspecificationFactory.eINSTANCE.createTestCase();
		testCase.setId(SpecmateEcoreUtil.getIdForChild(specification, testCase.eClass()));
		testCase.setName(testCase.getId());
		return testCase;
	}

}
