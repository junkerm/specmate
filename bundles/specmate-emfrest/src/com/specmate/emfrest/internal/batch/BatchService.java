package com.specmate.emfrest.internal.batch;

import javax.ws.rs.core.Response;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import com.specmate.auth.api.IAuthenticationService;
import com.specmate.common.exception.SpecmateException;
import com.specmate.emfjson.EMFJsonDeserializer;
import com.specmate.emfrest.api.IRestService;
import com.specmate.emfrest.api.RestServiceBase;
import com.specmate.emfrest.crud.CrudUtil;
import com.specmate.metrics.ICounter;
import com.specmate.metrics.IMetricsService;
import com.specmate.model.base.Folder;
import com.specmate.model.batch.BatchPackage;
import com.specmate.model.batch.Operation;
import com.specmate.model.support.util.SpecmateEcoreUtil;
import com.specmate.rest.RestResult;
import com.specmate.urihandler.IObjectResolver;

@Component(immediate = true, service = IRestService.class)
public class BatchService extends RestServiceBase {

	private static final String SERVICE_NAME = "batch";
	private IAuthenticationService authService;
	private IObjectResolver resolver;
	private IMetricsService metricsService; 
	private ICounter saveCounter;
	
	@Activate
	public void activate() throws SpecmateException {
		this.saveCounter = metricsService.createCounter("save_counter", "The total number of save operations");
	}

	@Override
	public String getServiceName() {
		return SERVICE_NAME;
	}

	@Override
	public boolean canPost(Object project, Object batchOperation) {
		return (project instanceof Folder) && (SpecmateEcoreUtil.isProject((Folder) project))
				&& (batchOperation instanceof String);
	}

	@Override
	public RestResult<?> post(Object projectObj, Object batchOperationObj, String token) throws SpecmateException {
		Folder project = (Folder) projectObj;
		EMFJsonDeserializer emfJsonDeserializer = new EMFJsonDeserializer(resolver, project.eResource());
		JSONObject batchObj = new JSONObject(new JSONTokener((String) batchOperationObj));
		JSONArray batchOps = batchObj.getJSONArray(BatchPackage.Literals.BATCH_OPERATION__OPERATIONS.getName());
		String userName = authService.getUserName(token);
		
		if(batchOps.length()>0) {
			saveCounter.inc();
		}

		for (int i = 0; i < batchOps.length(); i++) {
			JSONObject batchOp = batchOps.getJSONObject(i);
			Operation op = (Operation) emfJsonDeserializer.deserializeEObject(batchOp);
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

	@Reference
	public void setObjectResolver(IObjectResolver resolver) {
		this.resolver = resolver;
	}
	
	@Reference
	public void setMetricsService(IMetricsService metricsService) {
		this.metricsService = metricsService;
	}

}
