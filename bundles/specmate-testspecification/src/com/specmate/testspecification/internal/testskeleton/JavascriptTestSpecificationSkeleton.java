package com.specmate.testspecification.internal.testskeleton;

import java.util.List;

import com.specmate.model.support.util.SpecmateEcoreUtil;
import com.specmate.model.testspecification.TestCase;

public class JavascriptTestSpecificationSkeleton extends BaseSkeleton {

	public JavascriptTestSpecificationSkeleton(String language) {
		super(language);
	}

	@Override
	protected String generateCode(StringBuilder sb) {
		appendDateComment(sb);
		sb.append("describe('");
		sb.append(replaceInvalidChars(testArea));
		sb.append("', () => {\n\n");
		List<TestCase> testCases = SpecmateEcoreUtil.pickInstancesOf(testSpecification.getContents(), TestCase.class);
		for (TestCase tc : testCases) {
			sb.append("\t/*\n");
			sb.append("\t * Testfall: ");
			sb.append(tc.getName());
			sb.append("\n\t */\n");
			sb.append("\tit('");
			sb.append(replaceInvalidChars(testArea));
			appendTestCaseMethodName(sb, tc);
			sb.append("', () => {\n");
			sb.append("\t\tthrow new Error('not implemented yet');\n");
			sb.append("\t});\n\n");
		}

		sb.append("});");

		return sb.toString();
	}

	@Override
	protected String generateFileName() {
		return replaceInvalidChars(testArea) + ".js";
	}
}
