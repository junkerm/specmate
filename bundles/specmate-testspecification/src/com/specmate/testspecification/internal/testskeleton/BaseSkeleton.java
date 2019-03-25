package com.specmate.testspecification.internal.testskeleton;

import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import com.specmate.model.support.util.SpecmateEcoreUtil;
import com.specmate.model.testspecification.ParameterAssignment;
import com.specmate.model.testspecification.TestCase;
import com.specmate.model.testspecification.TestParameter;
import com.specmate.model.testspecification.TestSpecification;
import com.specmate.model.testspecification.TestSpecificationSkeleton;
import com.specmate.model.testspecification.TestspecificationFactory;

/** Base class for Test Specification Exporters (aka "Skeletons") */
public abstract class BaseSkeleton {

	protected String language;

	private static Pattern startsNumerical = Pattern.compile("^[0-9]");
	private static Pattern invalidChars = Pattern.compile("[^a-zA-Z_0-9\\_]");

	Comparator<TestParameter> parameterComparator = (p1, p2) -> compareParameter(p1, p2);
	Comparator<ParameterAssignment> assignmentComparator = (a1, a2) -> compareParameter(a1.getParameter(),
			a2.getParameter());

	public BaseSkeleton(String language) {
		this.language = language;
	}

	private static int compareParameter(TestParameter p1, TestParameter p2) {
		return p1.getType().compareTo(p2.getType()) * 10 + Integer.signum(p1.getName().compareTo(p2.getName()));
	}

	public TestSpecificationSkeleton generate(TestSpecification testSpecification) {
		StringBuilder sb = new StringBuilder();

		TestSpecificationSkeleton tss = TestspecificationFactory.eINSTANCE.createTestSpecificationSkeleton();
		tss.setLanguage(this.language);
		tss.setName(generateFileName(testSpecification));

		List<TestParameter> parameters = getParameters(testSpecification);

		generateHeader(sb, testSpecification, parameters);
		for (TestCase tc : getTestCases(testSpecification)) {
			generateTestCaseHeader(sb, testSpecification, tc);
			List<ParameterAssignment> assignments = getTestCaseParameterAssignments(tc);
			generateTestCaseParameterAssignments(sb, assignments);
			generateTestCaseFooter(sb, tc);
		}
		generateFooter(sb, testSpecification);
		tss.setCode(sb.toString());

		return tss;
	}

	/**
	 * Return Test parameter assignments sorted by type (input before output)
	 */
	private List<ParameterAssignment> getTestCaseParameterAssignments(TestCase tc) {
		return SpecmateEcoreUtil.pickInstancesOf(tc.getContents(), ParameterAssignment.class).stream()
				.sorted(this.assignmentComparator).collect(Collectors.toList());
	}

	/** Return Test parameters sorted by type (input before output) */
	private List<TestParameter> getParameters(TestSpecification testSpecification) {
		return SpecmateEcoreUtil.pickInstancesOf(testSpecification.getContents(), TestParameter.class).stream()
				.sorted(this.parameterComparator).collect(Collectors.toList());
	}

	private List<TestCase> getTestCases(TestSpecification testSpecification) {
		return SpecmateEcoreUtil.pickInstancesOf(testSpecification.getContents(), TestCase.class);
	}

	protected String replaceInvalidChars(String r) {
		r = startsNumerical.matcher(r).replaceAll("");
		return invalidChars.matcher(r).replaceAll("_");
	}

	protected void appendParameterValue(StringBuilder sb, ParameterAssignment pa) {
		sb.append("___");
		sb.append(replaceInvalidChars(pa.getParameter().getName()));
		sb.append("__");
		sb.append(replaceInvalidChars(pa.getValue()));
	}

	protected void appendDateComment(StringBuilder sb) {
		sb.append("/*\n");
		sb.append(" * Datum: ");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		sb.append(sdf.format(new Date()));
		sb.append("\n */\n\n");
	}

	protected abstract void generateHeader(StringBuilder sb, TestSpecification testSpecification,
			List<TestParameter> parameters);

	protected abstract void generateFooter(StringBuilder sb, TestSpecification testSpecification);

	protected abstract void generateTestCaseFooter(StringBuilder sb, TestCase tc);

	protected abstract void generateTestCaseHeader(StringBuilder sb, TestSpecification ts, TestCase tc);

	protected abstract void generateTestCaseParameterAssignments(StringBuilder sb,
			List<ParameterAssignment> assignments);

	protected abstract String generateFileName(TestSpecification testSpecification);
}
