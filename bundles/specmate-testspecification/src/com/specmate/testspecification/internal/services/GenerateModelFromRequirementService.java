package com.specmate.testspecification.internal.services;

import javax.ws.rs.core.Response;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.log.LogService;

import com.specmate.rest.RestResult;
import com.specmate.emfrest.api.IRestService;
import com.specmate.emfrest.api.RestServiceBase;
import com.specmate.model.requirements.CEGModel;
import com.specmate.model.requirements.Requirement;

/**
 * Service to create automatic a CEGModel from a requirement
 * 
 * @author Andreas Wehrle
 *
 */
@Component(immediate = true, service = IRestService.class)
public class GenerateModelFromRequirementService extends RestServiceBase {

	NLPTagger tagger;
	private LogService logService;

	@Override
	public String getServiceName() {
		return "generateModel";
	}

	@Override
	public boolean canPost(Object object2, Object object) {
		return object2 instanceof CEGModel;
	}

	@Override
	public RestResult<?> post(Object parent, Object child, String token) {
		CEGModel model = (CEGModel) parent;
		Requirement req = (Requirement) model.eContainer();
		model = generateModelFromDescription(model, req);
		req.getContents().add(model);
		return new RestResult<>(Response.Status.OK);
	}

	/**
	 * Add the nodes and connections to the model extracted from the text
	 * 
	 * @param model
	 *            CEGModel
	 * @param requirement
	 * @return
	 */
	private CEGModel generateModelFromDescription(CEGModel model, Requirement requirement) {
		String text = requirement.getDescription();
		text = new PersonalPronounsReplacer(tagger).replacePronouns(text);
		new CEGFromRequirementGenerator(logService, tagger).createModel(model, text);

		logService.log(org.osgi.service.log.LogService.LOG_INFO, "Fertig");
		return model;
	}

	@Reference
	public void setLogService(LogService logService) {
		this.logService = logService;
	}

	@Reference
	void setNlptagging(NLPTagger tagger) {
		this.tagger = tagger;
	}

}
