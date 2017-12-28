package com.specmate.emfrest.search;

import java.util.List;
import java.util.stream.Collectors;

import javax.ws.rs.core.MultivaluedMap;

import org.osgi.service.component.annotations.Component;

import com.specmate.common.SpecmateException;
import com.specmate.emfrest.api.IRestService;
import com.specmate.emfrest.api.RestServiceBase;
import com.specmate.model.processes.ProcessStep;
import com.specmate.model.requirements.Requirement;
import com.specmate.model.support.util.SpecmateEcoreUtil;

/**
 * Implements a service for retrieving related requirements. Requirements are
 * related when there exists a
 * 
 * @author junkerm
 *
 */
@Component(immediate = true, service = IRestService.class)
public class RelatedRequirementsService extends RestServiceBase {

	@Override
	public String getServiceName() {
		return "related";
	}

	@Override
	public boolean canGet(Object object) {
		return object instanceof Requirement;
	}

	@Override
	public Object get(Object target, MultivaluedMap<String, String> queryParams) throws SpecmateException {
		Requirement requirement = (Requirement) target;
		List<ProcessStep> processSteps = SpecmateEcoreUtil.pickInstancesOf(requirement.getTracesFrom(),
				ProcessStep.class);
		return processSteps.stream().flatMap(step -> step.getTracesTo().stream())
				.filter(related -> (related instanceof Requirement) && related != requirement).distinct()
				.collect(Collectors.toList());
	}
}
