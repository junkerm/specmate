package com.specmate.emfrest.search;

import static com.specmate.model.support.util.SpecmateEcoreUtil.pickInstancesOf;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.ws.rs.core.MultivaluedMap;

import org.eclipse.emf.ecore.EObject;
import org.osgi.service.component.annotations.Component;

import com.specmate.common.SpecmateException;
import com.specmate.emfrest.api.IRestService;
import com.specmate.emfrest.api.RestServiceBase;
import com.specmate.model.base.ITracingElement;
import com.specmate.model.processes.ProcessStep;
import com.specmate.model.requirements.Requirement;
import com.specmate.model.support.util.SpecmateEcoreUtil;

/**
 * Implements a service for retrieving related requirements. Two requirements
 * are related when there exists a process step that traces to both
 * requirements.
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
	public Object get(Object target, MultivaluedMap<String, String> queryParams, String token)
			throws SpecmateException {
		Requirement requirement = (Requirement) target;
		String projectId = SpecmateEcoreUtil.getProjectId(requirement);

		List<ProcessStep> ownedProcessSteps = SpecmateEcoreUtil.pickInstancesOf(requirement.eAllContents(),
				ProcessStep.class);
		Stream<EObject> relatedRequirementsFromOwnedProcess = extractRelatedRequirementsFromSteps(requirement,
				ownedProcessSteps);

		List<ProcessStep> tracedProcessSteps = pickInstancesOf(requirement.getTracesFrom(), ProcessStep.class);
		Stream<EObject> relatedRequirementsFromTraces = extractRelatedRequirementsFromSteps(requirement,
				tracedProcessSteps);

		return Stream.concat(relatedRequirementsFromOwnedProcess, relatedRequirementsFromTraces).distinct()
				.filter(r -> SpecmateEcoreUtil.getProjectId(r).equals(projectId)).collect(Collectors.toList());
	}

	private Stream<EObject> extractRelatedRequirementsFromSteps(Requirement owningRequirement,
			List<ProcessStep> steps) {
		return steps.stream().flatMap(this::getRelatedRequirements)
				.filter(related -> (related instanceof Requirement) && related != owningRequirement).distinct();
	}

	private Stream<EObject> getRelatedRequirements(ProcessStep step) {
		return Stream.concat(step.getTracesTo().stream(),
				Stream.of((ITracingElement) SpecmateEcoreUtil.getFirstAncestorOfType(step, Requirement.class)));
	}
}
