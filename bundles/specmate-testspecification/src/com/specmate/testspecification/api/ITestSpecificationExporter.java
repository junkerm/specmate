package com.specmate.testspecification.api;

import com.specmate.model.testspecification.TestSpecification;
import com.specmate.model.testspecification.TestSpecificationSkeleton;

public interface ITestSpecificationExporter {

	/** getter for language */
	String getLanguage();

	/** Generates an export for the test specification */
	TestSpecificationSkeleton generate(TestSpecification testSpecification);

}