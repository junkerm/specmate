package com.specmate.testspecification.api;

import com.specmate.model.testspecification.TestSpecificationSkeleton;

public interface ITestExporter {

	/** getter for language */
	String getLanguage();

	/** Generates an export for the test specification */
	TestSpecificationSkeleton generate(Object object);

	/** Signals that this exporter can export test specifications */
	boolean canExportTestSpecification();

	/** Signals that this exporter can export test procedures */
	boolean canExportTestProcedure();

}