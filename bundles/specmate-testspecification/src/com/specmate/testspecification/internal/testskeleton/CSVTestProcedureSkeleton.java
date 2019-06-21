package com.specmate.testspecification.internal.testskeleton;

import java.util.List;
import java.util.StringJoiner;

import org.apache.commons.lang3.StringUtils;

import com.specmate.model.testspecification.ParameterAssignment;
import com.specmate.model.testspecification.TestCase;
import com.specmate.model.testspecification.TestParameter;
import com.specmate.model.testspecification.TestProcedure;
import com.specmate.model.testspecification.TestSpecification;
import com.specmate.model.testspecification.TestStep;

public class CSVTestProcedureSkeleton extends BaseSkeletonTestProcedure {

	private static final String TEXT_WRAP = "\"";
	private static final String COL_SEP = ";";

	public CSVTestProcedureSkeleton(String language) {
		super(language);
	}

	@Override
	protected void generateHeader(StringBuilder sb, TestProcedure testProcedure,
			List<TestParameter> parameters) {
		StringJoiner joiner = new StringJoiner(COL_SEP);
		joiner.add("\"TC\"");
		for (TestParameter param : parameters) {
			joiner.add(StringUtils.wrap(param.getType().toString() + " - " + param.getName(), TEXT_WRAP));
		}
		sb.append(joiner).append("\n");
	}

	@Override
	protected void generateFooter(StringBuilder sb, TestProcedure testProcedure) {

	}

	@Override
	protected void generateTestStepFooter(StringBuilder sb, TestStep ts) {
		sb.append("\n");
	}

	@Override
	protected void generateTestStepHeader(StringBuilder sb, TestProcedure tp, TestStep ts) {
		sb.append(ts.getName() + COL_SEP);
	}

	@Override
	protected void generateTestStepParameterAssignments(StringBuilder sb, List<ParameterAssignment> assignments) {
		StringJoiner joiner = new StringJoiner(COL_SEP);
		for (ParameterAssignment assignment : assignments) {
			String assignmentValue = assignment.getCondition();
			String characterToEscape = "=";
			String escapeString = StringUtils.isEmpty(assignmentValue) ? "" : escapeString(assignmentValue, characterToEscape);
			joiner.add(StringUtils.wrap(escapeString + assignmentValue, TEXT_WRAP));
		}
		sb.append(joiner.toString());
	}

	@Override
	protected String generateFileName(TestProcedure testProcedure) {
		return replaceInvalidChars(testProcedure.getName()) + ".csv";
	}

	protected String escapeString(String stringToCheck, String characterToEscape) {
		String escapeCharacter = (stringToCheck.substring(0, 1).equals(characterToEscape)) ? "'" : "";
		return escapeCharacter;
	}

}
