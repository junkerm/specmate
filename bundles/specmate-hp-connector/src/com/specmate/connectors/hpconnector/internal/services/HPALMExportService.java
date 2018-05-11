package com.specmate.connectors.hpconnector.internal.services;

import org.eclipse.emf.ecore.EObject;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.log.LogService;

import com.specmate.common.SpecmateException;
import com.specmate.common.SpecmateValidationException;
import com.specmate.connectors.api.IProjectService;
import com.specmate.connectors.api.Project;
import com.specmate.connectors.hpconnector.internal.util.HPProxyConnection;
import com.specmate.emfrest.api.IRestService;
import com.specmate.emfrest.api.RestServiceBase;
import com.specmate.model.testspecification.TestProcedure;

@Component(immediate = true, service = IRestService.class)
public class HPALMExportService extends RestServiceBase {

	/** The log service */
	private LogService logService;

	/** The connection to the hp proxy. */
	private HPProxyConnection hpConnection;

	private IProjectService projectService;

	@Override
	public String getServiceName() {
		return "syncalm";
	}

	@Override
	public boolean canPost(Object target, EObject object) {
		return target instanceof TestProcedure;
	}

	@Override
	public Object post(Object target, EObject object) throws SpecmateException, SpecmateValidationException {
		Project project; //
		TestProcedure testProcedure = (TestProcedure) target;
		logService.log(LogService.LOG_INFO, "Synchronizing test procedure " + testProcedure.getName());
		project.getExporter();
		return testProcedure;
	}

	/** Service reference */
	@Reference
	public void setLogService(LogService logService) {
		this.logService = logService;
	}

	@Reference
	public void setProjectService(IProjectService projectService) {
		this.projectService = projectService;
	}
}
