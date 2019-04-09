package com.specmate.testspecification.test;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;

import com.specmate.model.testspecification.ParameterAssignment;
import com.specmate.model.testspecification.ParameterType;
import com.specmate.model.testspecification.TestCase;
import com.specmate.model.testspecification.TestParameter;
import com.specmate.model.testspecification.TestSpecification;
import com.specmate.model.testspecification.TestSpecificationSkeleton;
import com.specmate.model.testspecification.TestspecificationFactory;
import com.specmate.testspecification.internal.testskeleton.JavaTestSpecificationSkeleton;

public class TestSkeletonTest {
	@Test
	public void testJavaSkeleton() {
		TestSpecification ts = getTestSpecification();

		JavaTestSpecificationSkeleton skel = new JavaTestSpecificationSkeleton("JAVA");
		TestSpecificationSkeleton result = skel.generate(ts);
		String code = result.getCode();

		Assert.assertTrue(code.contains("public void TSTest___in1__in11___in2__in12___out1__out11___out2__out12()"));
		Assert.assertTrue(code.contains("public void TSTest___in1__in21___in2__in22___out1__out21___out2__out22()"));
	}

	private TestSpecification getTestSpecification() {
		TestspecificationFactory f = TestspecificationFactory.eINSTANCE;
		TestSpecification ts = f.createTestSpecification();
		ts.setName("TS");

		TestParameter input1 = f.createTestParameter();
		input1.setId("in1");
		input1.setName("in1");
		input1.setType(ParameterType.INPUT);

		TestParameter input2 = f.createTestParameter();
		input2.setId("in2");
		input2.setName("in2");
		input2.setType(ParameterType.INPUT);

		TestParameter output1 = f.createTestParameter();
		output1.setId("out1");
		output1.setName("out1");
		output1.setType(ParameterType.OUTPUT);

		TestParameter output2 = f.createTestParameter();
		output2.setId("out2");
		output2.setName("out2");
		output2.setType(ParameterType.OUTPUT);

		ts.getContents().addAll(Arrays.asList(input1, input2, output1, output2));

		TestCase tc1 = f.createTestCase();
		tc1.setId("tc1");
		tc1.setName("tc1");

		ParameterAssignment assIn11 = f.createParameterAssignment();
		assIn11.setParameter(input1);
		assIn11.setValue("in11");
		assIn11.setCondition("in11");

		ParameterAssignment assIn12 = f.createParameterAssignment();
		assIn12.setParameter(input2);
		assIn12.setValue("in12");
		assIn12.setCondition("in12");

		ParameterAssignment assOut11 = f.createParameterAssignment();
		assOut11.setParameter(output1);
		assOut11.setValue("out11");
		assOut11.setCondition("out11");

		ParameterAssignment assOut12 = f.createParameterAssignment();
		assOut12.setParameter(output2);
		assOut12.setValue("out12");
		assOut12.setCondition("out12");
		tc1.getContents().addAll(Arrays.asList(assIn11, assIn12, assOut11, assOut12));

		TestCase tc2 = f.createTestCase();
		tc2.setId("tc2");
		tc2.setName("tc2");

		ParameterAssignment assIn21 = f.createParameterAssignment();
		assIn21.setParameter(input1);
		assIn21.setValue("in21");
		assIn21.setCondition("in21");

		ParameterAssignment assIn22 = f.createParameterAssignment();
		assIn22.setParameter(input2);
		assIn22.setValue("in22");
		assIn22.setCondition("in22");

		ParameterAssignment assOut21 = f.createParameterAssignment();
		assOut21.setParameter(output1);
		assOut21.setValue("out21");
		assOut21.setCondition("out21");

		ParameterAssignment assOut22 = f.createParameterAssignment();
		assOut22.setParameter(output2);
		assOut22.setValue("out22");
		assOut22.setCondition("out22");
		tc2.getContents().addAll(Arrays.asList(assIn21, assIn22, assOut21, assOut22));

		ts.getContents().addAll(Arrays.asList(tc1, tc2));
		return ts;
	}
}
