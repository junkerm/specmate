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
import com.specmate.model.testspecification.TestProcedure;
import com.specmate.model.testspecification.TestProcedureSkeleton;
import com.specmate.model.testspecification.TestSpecification;
import com.specmate.model.testspecification.TestSpecificationSkeleton;
import com.specmate.model.testspecification.TestStep;
import com.specmate.model.testspecification.TestspecificationFactory;

/** Base class for Test Specification Exporters (aka "Skeletons") */
public abstract class BaseSkeletonTestProcedure {

	protected String language;

	private static Pattern startsNumerical = Pattern.compile("^[0-9]");
	private static Pattern invalidChars = Pattern.compile("[^a-zA-Z_0-9\\_]");

	Comparator<TestParameter> parameterComparator = (p1, p2) -> compareParameter(p1, p2);
	Comparator<ParameterAssignment> assignmentComparator = (a1, a2) -> compareParameter(a1.getParameter(),
			a2.getParameter());

	public BaseSkeletonTestProcedure(String language) {
		this.language = language;
	}

	private static int compareParameter(TestParameter p1, TestParameter p2) {
		return p1.getType().compareTo(p2.getType()) * 10 + Integer.signum(p1.getName().compareTo(p2.getName()));
	}

	public TestProcedureSkeleton generate(TestProcedure testProcedure) {
		StringBuilder sb = new StringBuilder();

		TestProcedureSkeleton tss = TestspecificationFactory.eINSTANCE.createTestProcedureSkeleton();
		tss.setLanguage(language);
		tss.setName(generateFileName(testProcedure));

		List<TestParameter> parameters = getParameters(testProcedure);

		generateHeader(sb, testProcedure, parameters);
		for (TestStep tc : getTestSteps(testProcedure)) {
			generateTestStepHeader(sb, testProcedure, tc);
			List<ParameterAssignment> assignments = getTestCaseParameterAssignments(tc);
			generateTestStepParameterAssignments(sb, assignments);
			generateTestStepFooter(sb, tc);
		}
		generateFooter(sb, testProcedure);
		tss.setCode(sb.toString());

		return tss;
	}

	/**
	 * Return Test parameter assignments sorted by type (input before output)
	 */
	private List<ParameterAssignment> getTestCaseParameterAssignments(TestStep ts) {
		return SpecmateEcoreUtil.pickInstancesOf(ts.getReferencedTestParameters(), ParameterAssignment.class).stream()
				.sorted(assignmentComparator).collect(Collectors.toList());
	}

	/** Return Test parameters sorted by type (input before output) */
	private List<TestParameter> getParameters(TestProcedure testProcedure) {
		return SpecmateEcoreUtil.pickInstancesOf(testProcedure.getContents(), TestParameter.class).stream()
				.sorted(parameterComparator).collect(Collectors.toList());
	}

	private List<TestStep> getTestSteps(TestProcedure testProcedure) {
		return SpecmateEcoreUtil.pickInstancesOf(testProcedure.getContents(), TestStep.class);
	}

	protected String replaceInvalidChars(String name) {
		name = startsNumerical.matcher(name).replaceAll("");
		return invalidChars.matcher(name).replaceAll("_");
	}

	protected void appendParameterValue(StringBuilder sb, ParameterAssignment pa) {
		sb.append("___");
		sb.append(replaceInvalidChars(pa.getParameter().getName()));
		sb.append("__");
		sb.append(replaceInvalidChars(pa.getCondition()));
	}

	protected void appendDateComment(StringBuilder sb) {
		sb.append("/*\n");
		sb.append(" * Datum: ");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		sb.append(sdf.format(new Date()));
		sb.append("\n */\n\n");
	}

	protected abstract void generateHeader(StringBuilder sb, TestProcedure testProcedure,
			List<TestParameter> parameters);

	protected abstract void generateFooter(StringBuilder sb, TestProcedure testProcedure);

	protected abstract void generateTestStepFooter(StringBuilder sb, TestStep ts);

	protected abstract void generateTestStepHeader(StringBuilder sb, TestProcedure testProcedure, TestStep ts);

	protected abstract void generateTestStepParameterAssignments(StringBuilder sb,
			List<ParameterAssignment> assignments);

	protected abstract String generateFileName(TestProcedure testProcedure);
}
