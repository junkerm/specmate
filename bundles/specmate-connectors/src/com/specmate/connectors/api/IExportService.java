package com.specmate.connectors.api;

import com.specmate.common.SpecmateException;
import com.specmate.model.testspecification.TestProcedure;

public interface IExportService {

	void export(TestProcedure testProcedure) throws SpecmateException;

}
