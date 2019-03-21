package com.specmate.testspecification.internal.testskeleton;

import java.util.List;
import java.util.StringJoiner;

import org.apache.commons.lang3.StringUtils;

import com.specmate.model.testspecification.ParameterAssignment;
import com.specmate.model.testspecification.TestCase;
import com.specmate.model.testspecification.TestParameter;
import com.specmate.model.testspecification.TestSpecification;

public class CSVTestSpecificationSkeleton extends BaseSkeleton {

	private static final String TEXT_WRAP = "\"";
	private static final String COL_SEP = ";";

	public CSVTestSpecificationSkeleton(String language) {
		super(language);
	}

	@Override
	protected void generateHeader(StringBuilder sb, TestSpecification testSpecification2,
			List<TestParameter> parameters) {
		StringJoiner joiner = new StringJoiner(COL_SEP);
		joiner.add("\"TC\"");
		for (TestParameter param : parameters) {
			joiner.add(StringUtils.wrap(param.getType().toString() + " - " + param.getName(), TEXT_WRAP));
		}
		sb.append(joiner).append("\n");
	}

	@Override
	protected void generateFooter(StringBuilder sb, TestSpecification testSpecification) {

	}

	@Override
	protected void generateTestCaseFooter(StringBuilder sb, TestCase tc) {
		sb.append("\n");
	}

	@Override
	protected void generateTestCaseHeader(StringBuilder sb, TestSpecification ts, TestCase tc) {
		sb.append(tc.getName() + COL_SEP);
	}

	@Override
	protected void generateTestCaseParameterAssignments(StringBuilder sb, List<ParameterAssignment> assignments) {
		StringJoiner joiner = new StringJoiner(COL_SEP);
		for (ParameterAssignment assignment : assignments) {
			String assignmentValue = assignment.getValue();
			String characterToEscape = "=";
			joiner.add(StringUtils.wrap(escapeString(assignmentValue, characterToEscape) + assignmentValue, TEXT_WRAP));
		}
		sb.append(joiner.toString());
	}

	@Override
	protected String generateFileName(TestSpecification testSpecification) {
		return replaceInvalidChars(testSpecification.getName()) + ".csv";
	}
	
	protected String escapeString(String stringToCheck, String characterToEscape) {
		String escapeCharacter = (stringToCheck.substring(0, 1).equals(characterToEscape)) ? "'" : "";
		return escapeCharacter;
	}

}
