package com.specmate.testspecification.internal.testskeleton;

import java.util.Date;
import java.util.List;

import com.specmate.model.support.util.SpecmateEcoreUtil;
import com.specmate.model.testspecification.ParameterAssignment;
import com.specmate.model.testspecification.ParameterType;
import com.specmate.model.testspecification.TestCase;
import com.specmate.model.testspecification.TestSpecification;
import com.specmate.model.testspecification.TestSpecificationSkeleton;
import com.specmate.model.testspecification.TestspecificationFactory;

public abstract class BaseSkeleton {
	protected String language;
	protected String testArea;
	protected Date generationDate;
	protected TestSpecification testSpecification;

	public BaseSkeleton(String language) {
		this.language = language;
		this.generationDate = new Date();
		this.testArea = "AAA";
	}

	public TestSpecificationSkeleton generate(TestSpecification testSpecification) {
		this.testSpecification = testSpecification;
		TestSpecificationSkeleton tss = TestspecificationFactory.eINSTANCE.createTestSpecificationSkeleton();
		tss.setLanguage(language);
		tss.setName(testSpecification.getName());
		tss.setCode(generateCode());
		return tss;
	}

	protected StringBuilder getTestCaseMethodName(TestCase tc) {
		StringBuilder sb = new StringBuilder();

		List<ParameterAssignment> pAssignments = SpecmateEcoreUtil.pickInstancesOf(tc.getContents(),
				ParameterAssignment.class);

		ParameterAssignment output = null;
		for (ParameterAssignment pAssignment : pAssignments) {
			if (pAssignment.getParameter().getType().equals(ParameterType.OUTPUT)) {
				output = pAssignment;
			} else {
				sb.append(generateParameterValue(pAssignment));
			}
		}

		if (output != null) {
			sb.append(generateParameterValue(output));
		}

		return sb;
	}

	private StringBuffer generateParameterValue(ParameterAssignment pa) {
		StringBuffer sb = new StringBuffer();
		sb.append("___");
		sb.append(replaceSpace(pa.getParameter().getName()));
		sb.append("__");
		sb.append(replaceSpace(pa.getValue()));

		return sb;
	}

	private String replaceSpace(String r) {
		return r.replaceAll("\\s", "_");
	}

	protected abstract String generateCode();
}
