package com.specmate.connectors.hpconnector.internal.services;

import javax.ws.rs.core.MultivaluedMap;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.log.LogService;

import com.specmate.common.SpecmateException;
import com.specmate.connectors.hpconnector.internal.util.HPProxyConnection;
import com.specmate.emfrest.api.IRestService;
import com.specmate.emfrest.crud.DetailsService;
import com.specmate.model.requirements.Requirement;
import com.specmate.model.support.util.SpecmateEcoreUtil;

/**
 * This service replace the default details service. It fetches requirements
 * details directly from the HP server.
 *
 * @author junkerm
 *
 */
@Component(immediate = true, service = IRestService.class)
public class HPConnectorDetailsRestService extends DetailsService {

	/** The log service */
	private LogService logService;

	/** The connection to the hp proxy. */
	private HPProxyConnection hpConnection;

	/**
	 * Priority of this service is one higher than the default details service.
	 */
	@Override
	public int getPriority() {
		return super.getPriority() + 1;
	}

	/**
	 * Behavior for GET requests. For requirements the current data is fetched from
	 * the HP server.
	 */
	@Override
	public Object get(Object target, MultivaluedMap<String, String> queryParams, String token)
			throws SpecmateException {
		if (!(target instanceof Requirement)) {
			return super.get(target, queryParams, token);
		}
		Requirement localRequirement = (Requirement) target;

		// TODO: We should check the source of the requirment, there might be
		// more sources in future
		if (localRequirement.getExtId() == null) {
			return localRequirement;
		}
		try {
			Requirement retrievedRequirement = this.hpConnection.getRequirementsDetails(localRequirement.getExtId());
			SpecmateEcoreUtil.copyAttributeValues(retrievedRequirement, localRequirement);
		} catch (SpecmateException e) {
			logService.log(LogService.LOG_ERROR, e.getMessage());
		}

		return localRequirement;

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
