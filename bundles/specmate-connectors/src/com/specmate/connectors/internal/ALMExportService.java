package com.specmate.connectors.internal;

import org.eclipse.emf.ecore.EObject;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.log.LogService;

import com.specmate.common.SpecmateException;
import com.specmate.common.SpecmateValidationException;
import com.specmate.connectors.api.IProjectService;
import com.specmate.connectors.api.IProject;
import com.specmate.emfrest.api.IRestService;
import com.specmate.emfrest.api.RestServiceBase;
import com.specmate.model.support.util.SpecmateEcoreUtil;
import com.specmate.model.testspecification.TestProcedure;

@Component(immediate = true, service = IRestService.class)
public class ALMExportService extends RestServiceBase {

	/** The log service */
	private LogService logService;

	/** The project service */
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
		TestProcedure testProcedure = (TestProcedure) target;
		String projectName = SpecmateEcoreUtil.getProjectId(testProcedure);
		logService.log(LogService.LOG_INFO, "Synchronizing test procedure " + testProcedure.getName());
		IProject project = projectService.getProject(projectName);
		project.getExporter().export(testProcedure);
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
