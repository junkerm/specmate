package com.specmate.testspecification.test;

import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.specmate.common.exception.SpecmateException;
import com.specmate.model.requirements.CEGConnection;
import com.specmate.model.requirements.CEGModel;
import com.specmate.model.requirements.CEGNode;
import com.specmate.model.requirements.RequirementsFactory;
import com.specmate.model.support.util.SpecmateEcoreUtil;
import com.specmate.model.testspecification.ParameterAssignment;
import com.specmate.model.testspecification.TestCase;
import com.specmate.model.testspecification.TestSpecification;
import com.specmate.model.testspecification.TestspecificationFactory;
import com.specmate.testspecification.internal.services.CEGTestCaseGenerator;

public class CEGTestGenerationTest {

	@Test
	public void testCEGTestGenerationRemoveNot() throws SpecmateException {
		TestSpecification spec = getTestSpecificationRemoveNot();
		CEGTestCaseGenerator generator = new CEGTestCaseGenerator(spec);
		generator.generate();
		List<TestCase> testcases = SpecmateEcoreUtil.pickInstancesOf(spec.getContents(), TestCase.class);
		Assert.assertTrue(testcases.stream().noneMatch(tc -> {
			List<ParameterAssignment> assignments = SpecmateEcoreUtil.pickInstancesOf(tc.getContents(),
					ParameterAssignment.class);
			return assignments.stream().anyMatch(a -> a.getCondition().startsWith("not ="));
		}));
	}

	private TestSpecification getTestSpecificationRemoveNot() {
		RequirementsFactory f = RequirementsFactory.eINSTANCE;
		CEGModel ceg = f.createCEGModel();
		ceg.setId("ceg");

		CEGNode trueNode = f.createCEGNode();
		trueNode.setId("trueNode");
		trueNode.setVariable("A");
		trueNode.setCondition("=true");

		CEGNode falseNode = f.createCEGNode();
		falseNode.setId("falseNode");
		falseNode.setVariable("A");
		falseNode.setCondition("=false");

		CEGNode trueEffect = f.createCEGNode();
		trueEffect.setId("trueEffect");
		trueEffect.setVariable("trueEffect");
		trueEffect.setCondition("true");

		CEGNode falseEffect = f.createCEGNode();
		falseEffect.setId("falseEffect");
		falseEffect.setVariable("falseEffect");
		falseEffect.setCondition("true");

		CEGConnection conn1 = f.createCEGConnection();
		conn1.setSource(trueNode);
		conn1.setTarget(trueEffect);

		CEGConnection conn2 = f.createCEGConnection();
		conn2.setSource(falseNode);
		conn2.setTarget(falseEffect);

		TestSpecification spec = TestspecificationFactory.eINSTANCE.createTestSpecification();
		spec.setId("testspec");

		ceg.getContents().addAll(Arrays.asList(trueNode, falseNode, trueEffect, falseEffect, conn1, conn2, spec));
		return spec;
	}

	@Test
	public void testCEGTestGenerationSameVariable() throws SpecmateException {
		TestSpecification spec = getTestSpecificationSameVariable();
		CEGTestCaseGenerator generator = new CEGTestCaseGenerator(spec);
		generator.generate();
		List<TestCase> testcases = SpecmateEcoreUtil.pickInstancesOf(spec.getContents(), TestCase.class);
		Assert.assertTrue(testcases.stream().anyMatch(tc -> {
			List<ParameterAssignment> assignments = SpecmateEcoreUtil.pickInstancesOf(tc.getContents(),
					ParameterAssignment.class);
			return assignments.stream().anyMatch(a -> a.getCondition().startsWith("=A"));
		}));
		Assert.assertTrue(testcases.stream().anyMatch(tc -> {
			List<ParameterAssignment> assignments = SpecmateEcoreUtil.pickInstancesOf(tc.getContents(),
					ParameterAssignment.class);
			return assignments.stream().anyMatch(a -> a.getCondition().startsWith("=B"));
		}));
		Assert.assertTrue(testcases.stream().anyMatch(tc -> {
			List<ParameterAssignment> assignments = SpecmateEcoreUtil.pickInstancesOf(tc.getContents(),
					ParameterAssignment.class);
			return assignments.stream().anyMatch(a -> a.getCondition().startsWith("not =A,not =B"));
		}));
	}

	private TestSpecification getTestSpecificationSameVariable() {
		RequirementsFactory f = RequirementsFactory.eINSTANCE;
		CEGModel ceg = f.createCEGModel();
		ceg.setId("ceg");

		CEGNode aNode = f.createCEGNode();
		aNode.setId("aNode");
		aNode.setVariable("a");
		aNode.setCondition("present");

		CEGNode bNode = f.createCEGNode();
		bNode.setId("bNode");
		bNode.setVariable("b");
		bNode.setCondition("present");

		CEGNode cNode = f.createCEGNode();
		cNode.setId("cNode");
		cNode.setVariable("c");
		cNode.setCondition("present");

		CEGNode aEffect = f.createCEGNode();
		aEffect.setId("aEffect");
		aEffect.setVariable("v");
		aEffect.setCondition("=A");

		CEGNode bEffect = f.createCEGNode();
		bEffect.setId("bEffect");
		bEffect.setVariable("v");
		bEffect.setCondition("=B");

		CEGConnection conn1 = f.createCEGConnection();
		conn1.setSource(aNode);
		conn1.setTarget(aEffect);

		CEGConnection conn2 = f.createCEGConnection();
		conn2.setSource(bNode);
		conn2.setTarget(bEffect);

		CEGConnection conn3 = f.createCEGConnection();
		conn3.setSource(cNode);
		conn3.setTarget(aEffect);

		CEGConnection conn4 = f.createCEGConnection();
		conn4.setSource(cNode);
		conn4.setTarget(bEffect);

		TestSpecification spec = TestspecificationFactory.eINSTANCE.createTestSpecification();
		spec.setId("testspec");

		ceg.getContents()
				.addAll(Arrays.asList(aNode, bNode, cNode, aEffect, bEffect, conn1, conn2, conn3, conn4, spec));
		return spec;
	}
}
