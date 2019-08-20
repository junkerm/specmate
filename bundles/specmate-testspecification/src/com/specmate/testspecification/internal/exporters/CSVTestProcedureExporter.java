package com.specmate.testspecification.internal.exporters;

import static com.specmate.testspecification.internal.exporters.ExportUtil.CSV_COL_SEP;
import static com.specmate.testspecification.internal.exporters.ExportUtil.CSV_LINE_SEP;

import java.util.List;
import java.util.StringJoiner;

import org.osgi.service.component.annotations.Component;

import com.specmate.model.support.util.SpecmateEcoreUtil;
import com.specmate.model.testspecification.TestProcedure;
import com.specmate.model.testspecification.TestSpecificationSkeleton;
import com.specmate.model.testspecification.TestStep;
import com.specmate.model.testspecification.TestspecificationFactory;
import com.specmate.testspecification.api.ITestExporter;

@Component(immediate = true)
public class CSVTestProcedureExporter implements ITestExporter {
	private static final String HEADER = "Step Name" + CSV_COL_SEP + "Action" + CSV_COL_SEP + "Expected Outcome";

	@Override
	public boolean canExportTestProcedure() {
		return true;
	}

	@Override
	public boolean canExportTestSpecification() {
		return false;
	}

	@Override
	public String getLanguage() {
		return "csv";
	}

	@Override
	public TestSpecificationSkeleton generate(Object object) {
		TestProcedure testprocedure = (TestProcedure) object;
		StringJoiner joiner = new StringJoiner(CSV_LINE_SEP);
		joiner.add(HEADER);
		List<TestStep> testSteps = SpecmateEcoreUtil.pickInstancesOf(testprocedure.getContents(), TestStep.class);
		for (TestStep step : testSteps) {
			joiner.add(step.getName() + CSV_COL_SEP + step.getDescription() + CSV_COL_SEP + step.getExpectedOutcome());
		}
		TestSpecificationSkeleton skelleton = TestspecificationFactory.eINSTANCE.createTestSpecificationSkeleton();
		skelleton.setName(ExportUtil.replaceInvalidChars(testprocedure.getName()) + ".csv");
		skelleton.setLanguage(getLanguage());
		skelleton.setCode(joiner.toString());
		return skelleton;

	}

}
