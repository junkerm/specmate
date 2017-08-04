package com.specmate.connectors.hpconnector.internal.services;

import org.eclipse.emf.ecore.EObject;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.log.LogService;

import com.specmate.common.SpecmateException;
import com.specmate.common.SpecmateValidationException;
import com.specmate.connectors.hpconnector.internal.HPProxyConnection;
import com.specmate.emfrest.api.IRestService;
import com.specmate.emfrest.api.RestServiceBase;
import com.specmate.model.testspecification.TestProcedure;

@Component(immediate = true, service = IRestService.class)
public class HPALMSyncService extends RestServiceBase {

	/** The log service */
	private LogService logService;

	/** The connection to the hp proxy. */
	private HPProxyConnection hpConnection;

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

	}

	/** Service reference */
	@Reference
	public void setLogService(LogService logService) {
		this.logService = logService;
	}

	/** Service reference */
	@Reference
	public void setHPServerProxy(HPProxyConnection serverProxy) {
		this.hpConnection = serverProxy;
	}
}
