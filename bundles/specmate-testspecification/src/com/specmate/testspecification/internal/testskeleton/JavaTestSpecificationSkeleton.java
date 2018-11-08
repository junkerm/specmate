package com.specmate.testspecification.internal.testskeleton;

import java.text.SimpleDateFormat;
import java.util.List;

import com.specmate.model.support.util.SpecmateEcoreUtil;
import com.specmate.model.testspecification.TestCase;

public class JavaTestSpecificationSkeleton extends BaseSkeleton {

	public JavaTestSpecificationSkeleton(String language) {
		super(language);
	}

	@Override
	protected String generateCode() {
		StringBuilder sb = new StringBuilder();
		sb.append("import org.junit.Test;\n");
		sb.append("import org.junit.Assert;\n\n");
		sb.append("/*\n");
		sb.append(" * Datum: ");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		sb.append(sdf.format(generationDate));
		sb.append("\n */\n\n");
		sb.append("public class ");
		sb.append(testArea);
		sb.append("Test {\n\n");
		List<TestCase> testCases = SpecmateEcoreUtil.pickInstancesOf(testSpecification.getContents(), TestCase.class);
		for (TestCase tc : testCases) {
			sb.append("\t/*\n");
			sb.append("\t * Testfall: ");
			sb.append(tc.getName());
			sb.append("\n\t */\n");
			sb.append("\t@Test\n");
			sb.append("\tpublic void ");
			sb.append(testArea);
			sb.append(getTestCaseMethodName(tc));
			sb.append("() {\n");
			sb.append("\t\tAssert.throw();\n");
			sb.append("\t}\n\n");
		}

		sb.append("}");

		return sb.toString();
	}
}
