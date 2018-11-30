package com.specmate.connectors.internal;

import javax.ws.rs.core.Response;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.log.LogService;

import com.specmate.auth.api.IAuthenticationService;
import com.specmate.common.SpecmateException;
import com.specmate.common.SpecmateValidationException;
import com.specmate.connectors.api.IProject;
import com.specmate.connectors.api.IProjectService;
import com.specmate.emfrest.api.IRestService;
import com.specmate.emfrest.api.RestServiceBase;
import com.specmate.model.support.util.SpecmateEcoreUtil;
import com.specmate.model.testspecification.TestProcedure;
import com.specmate.rest.RestResult;
import com.specmate.usermodel.AccessRights;

@Component(immediate = true, service = IRestService.class)
public class ALMExportService extends RestServiceBase {

	/** The log service */
	private LogService logService;

	/** The project service */
	private IProjectService projectService;

	/** The authentication service */
	private IAuthenticationService authService;

	@Override
	public String getServiceName() {
		return "syncalm";
	}

	@Override
	public boolean canPost(Object target, Object object) {
		return target instanceof TestProcedure;
	}

	@Override
	public RestResult<?> post(Object target, Object object, String token)
			throws SpecmateException, SpecmateValidationException {

		if (isAuthorizedToExport(token)) {
			TestProcedure testProcedure = (TestProcedure) target;
			String projectName = SpecmateEcoreUtil.getProjectId(testProcedure);
			logService.log(LogService.LOG_INFO, "Synchronizing test procedure " + testProcedure.getName());
			IProject project = projectService.getProject(projectName);
			project.getExporter().export(testProcedure);
			return new RestResult<>(Response.Status.OK, testProcedure);
		} else {
			return new RestResult<>(Response.Status.FORBIDDEN);
		}
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

	@Reference
	public void setAuthService(IAuthenticationService authService) {
		this.authService = authService;
	}

	private boolean isAuthorizedToExport(String token) throws SpecmateException {
		AccessRights export = authService.getTargetAccessRights(token);
		return export.equals(AccessRights.ALL) || export.equals(AccessRights.WRITE);
	}
}
