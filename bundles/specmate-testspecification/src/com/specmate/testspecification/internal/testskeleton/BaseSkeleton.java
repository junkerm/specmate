package com.specmate.testspecification.internal.testskeleton;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

import com.specmate.model.support.util.SpecmateEcoreUtil;
import com.specmate.model.testspecification.ParameterAssignment;
import com.specmate.model.testspecification.ParameterType;
import com.specmate.model.testspecification.TestCase;
import com.specmate.model.testspecification.TestSpecification;
import com.specmate.model.testspecification.TestSpecificationSkeleton;
import com.specmate.model.testspecification.TestspecificationFactory;

public abstract class BaseSkeleton {
	private static Pattern startsNumerical = Pattern.compile("^[0-9]");
	private static Pattern invalidChars = Pattern.compile("[^a-zA-Z_0-9\\_]");
	protected String language;
	protected String testArea;
	protected Date generationDate;
	protected TestSpecification testSpecification;

	public BaseSkeleton(String language) {
		this.language = language;
		this.generationDate = new Date();
	}

	public TestSpecificationSkeleton generate(TestSpecification testSpecification) {
		StringBuilder sb = new StringBuilder();
		this.testSpecification = testSpecification;
		this.testArea = testSpecification.getName();
		TestSpecificationSkeleton tss = TestspecificationFactory.eINSTANCE.createTestSpecificationSkeleton();
		tss.setLanguage(language);
		tss.setName(generateFileName());
		tss.setCode(generateCode(sb));

		return tss;
	}

	protected void appendTestCaseMethodName(StringBuilder sb, TestCase tc) {
		List<ParameterAssignment> pAssignments = SpecmateEcoreUtil.pickInstancesOf(tc.getContents(),
				ParameterAssignment.class);

		ParameterAssignment output = null;
		for (ParameterAssignment pAssignment : pAssignments) {
			if (pAssignment.getParameter().getType().equals(ParameterType.OUTPUT)) {
				output = pAssignment;
			} else {
				appendParameterValue(sb, pAssignment);
			}
		}

		if (output != null) {
			appendParameterValue(sb, output);
		}
	}

	protected void appendDateComment(StringBuilder sb) {
		sb.append("/*\n");
		sb.append(" * Datum: ");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		sb.append(sdf.format(generationDate));
		sb.append("\n */\n\n");
	}

	protected String replaceInvalidChars(String r) {
		r = startsNumerical.matcher(r).replaceAll("");
		return invalidChars.matcher(r).replaceAll("_");
	}

	private void appendParameterValue(StringBuilder sb, ParameterAssignment pa) {
		sb.append("___");
		sb.append(replaceInvalidChars(pa.getParameter().getName()));
		sb.append("__");
		sb.append(replaceInvalidChars(pa.getValue()));
	}

	protected abstract String generateCode(StringBuilder sb);

	protected abstract String generateFileName();
}
