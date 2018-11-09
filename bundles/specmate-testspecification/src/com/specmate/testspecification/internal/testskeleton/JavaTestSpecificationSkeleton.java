package com.specmate.testspecification.internal.testskeleton;

import java.util.List;

import com.specmate.model.support.util.SpecmateEcoreUtil;
import com.specmate.model.testspecification.TestCase;

public class JavaTestSpecificationSkeleton extends BaseSkeleton {

	public JavaTestSpecificationSkeleton(String language) {
		super(language);
	}

	@Override
	protected String generateCode() {
		sb.append("import org.junit.Test;\n");
		sb.append("import org.junit.Assert;\n\n");
		generateDateComment();
		sb.append("public class ");
		sb.append(generateClassname());
		sb.append(" {\n\n");
		List<TestCase> testCases = SpecmateEcoreUtil.pickInstancesOf(testSpecification.getContents(), TestCase.class);
		for (TestCase tc : testCases) {
			sb.append("\t/*\n");
			sb.append("\t * Testfall: ");
			sb.append(tc.getName());
			sb.append("\n\t */\n");
			sb.append("\t@Test\n");
			sb.append("\tpublic void ");
			sb.append(generateClassname());
			generateTestCaseMethodName(tc);
			sb.append("() {\n");
			sb.append("\t\tAssert.throw();\n");
			sb.append("\t}\n\n");
		}

		sb.append("}");

		return sb.toString();
	}

	@Override
	protected String generateFileName() {
		return generateClassname() + ".java";
	}

	private String generateClassname() {
		return replaceInvalidChars(testArea) + "Test";
	}
}
