package com.specmate.modelgeneration;

import javax.ws.rs.core.Response;

import org.apache.commons.lang3.StringUtils;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.log.LogService;

import com.specmate.common.exception.SpecmateException;
import com.specmate.emfrest.api.IRestService;
import com.specmate.emfrest.api.RestServiceBase;
import com.specmate.model.requirements.CEGModel;
import com.specmate.model.requirements.Requirement;
import com.specmate.nlp.api.ELanguage;
import com.specmate.nlp.api.INLPService;
import com.specmate.rest.RestResult;

/**
 * Service to create automatic a CEGModel from a requirement
 *
 * @author Andreas Wehrle
 *
 */
@Component(immediate = true, service = IRestService.class)
public class GenerateModelFromRequirementService extends RestServiceBase {

	INLPService nlpService;
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
		try {
			model = generateModelFromDescription(model, req);
		} catch (SpecmateException e) {
			return new RestResult<>(Response.Status.INTERNAL_SERVER_ERROR);
		}
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
	private CEGModel generateModelFromDescription(CEGModel model, Requirement requirement) throws SpecmateException {
		String text = model.getModelRequirements();
		if (text == null || StringUtils.isEmpty(text)) {
			return model;
		}
		ELanguage language = nlpService.detectLanguage(text);
		// text = new PersonalPronounsReplacer(nlpService).replacePronouns(text);
		CEGFromRequirementGenerator generator = null;
		switch (language) {
		case DE:
			generator = new GermanCEGFromRequirementGenerator(logService, nlpService);
			break;
		case EN:
			generator = new EnglishCEGFromRequirementGenerator(logService, nlpService);
			break;
		default:
			return model;
		}

		return generator.createModel(model, text);
	}

	@Reference
	public void setLogService(LogService logService) {
		this.logService = logService;
	}

	@Reference
	void setNlptagging(INLPService tagger) {
		nlpService = tagger;
	}

}
