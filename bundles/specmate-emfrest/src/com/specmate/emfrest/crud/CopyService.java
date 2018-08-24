package com.specmate.emfrest.crud;

import org.osgi.service.component.annotations.Component;

import com.specmate.common.SpecmateException;
import com.specmate.common.SpecmateValidationException;
import com.specmate.emfrest.api.IRestService;
import com.specmate.emfrest.api.RestServiceBase;
import com.specmate.model.processes.Process;
import com.specmate.model.requirements.CEGModel;
import com.specmate.model.testspecification.TestSpecification;
import com.specmate.rest.RestResult;

@Component(immediate = true, service = IRestService.class)
public class CopyService extends RestServiceBase {
	@Override
	public String getServiceName() {
		return "duplicate";
	}
	
	@Override
	public boolean canPost(Object target, Object object) {
		return target instanceof CEGModel || target instanceof Process || target instanceof TestSpecification;
	}

	@Override
	public RestResult<?> post(Object target, Object child, String token)
			throws SpecmateException, SpecmateValidationException {
		return CrudUtil.duplicate(target);
	}
}
