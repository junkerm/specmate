package com.specmate.emfrest.internal.batch;

import javax.ws.rs.core.Response;

import org.eclipse.emf.ecore.EObject;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import com.specmate.auth.api.IAuthenticationService;
import com.specmate.common.RestResult;
import com.specmate.common.SpecmateException;
import com.specmate.common.SpecmateValidationException;
import com.specmate.emfrest.api.IRestService;
import com.specmate.emfrest.api.RestServiceBase;
import com.specmate.emfrest.crud.CrudUtil;
import com.specmate.model.base.Folder;
import com.specmate.model.batch.BatchOperation;
import com.specmate.model.batch.Operation;
import com.specmate.model.support.util.SpecmateEcoreUtil;

@Component(immediate = true, service = IRestService.class)
public class BatchService extends RestServiceBase {

	private static final String SERVICE_NAME = "batch";
	private IAuthenticationService authService;

	@Override
	public String getServiceName() {
		return SERVICE_NAME;
	}

	@Override
	public boolean canPost(Object project, EObject batchOperation) {
		return (project instanceof Folder) && (SpecmateEcoreUtil.isProject((Folder) project))
				&& (batchOperation instanceof BatchOperation);
	}

	@Override
	public RestResult<?> post(Object projectObj, EObject batchOperationObj, String token)
			throws SpecmateValidationException, SpecmateException {
		BatchOperation batch = (BatchOperation) batchOperationObj;
		String userName = authService.getUserName(token);

		for (Operation op : batch.getOperations()) {

			switch (op.getType()) {
			case CREATE:
				CrudUtil.create(op.getTarget(), op.getValue(), userName);
				break;
			case UPDATE:
				CrudUtil.update(op.getTarget(), op.getValue(), userName);
				break;
			case DELETE:
				CrudUtil.delete(op.getTarget(), userName);
				break;
			}
		}
		return new RestResult<>(Response.Status.OK, null, userName);
	}

	@Reference
	public void setAuthenticationService(IAuthenticationService authService) {
		this.authService = authService;
	}

}
