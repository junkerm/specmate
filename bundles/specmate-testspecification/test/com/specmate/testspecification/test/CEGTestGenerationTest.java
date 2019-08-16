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
	public void testCEGTestGeneration() throws SpecmateException {
		TestSpecification spec = getTestSpecification();
		CEGTestCaseGenerator generator = new CEGTestCaseGenerator(spec);
		generator.generate();
		List<TestCase> testcases = SpecmateEcoreUtil.pickInstancesOf(spec.getContents(), TestCase.class);
		Assert.assertTrue(testcases.stream().noneMatch(tc -> {
			List<ParameterAssignment> assignments = SpecmateEcoreUtil.pickInstancesOf(tc.getContents(),
					ParameterAssignment.class);
			return assignments.stream().anyMatch(a -> a.getCondition().startsWith("not ="));
		}));
	}

	private TestSpecification getTestSpecification() {
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
}
