package com.specmate.connectors.api;

import com.specmate.common.exception.SpecmateException;
import com.specmate.model.testspecification.TestProcedure;

public interface IExportService {

	void export(TestProcedure testProcedure) throws SpecmateException;

	boolean isAuthorizedToExport(String username, String password);
}
