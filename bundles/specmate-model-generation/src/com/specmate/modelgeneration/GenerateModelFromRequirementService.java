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
import com.specmate.nlp.util.NLPUtil;
import com.specmate.rest.RestResult;

/**
 * Service to create automatic a CEGModel from a requirement
 *
 * @author Andreas Wehrle
 *
 */
@Component(immediate = true, service = IRestService.class)
public class GenerateModelFromRequirementService extends RestServiceBase {

	INLPService tagger;
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
		try {
			model = generateModelFromDescription(model);
		} catch (SpecmateException e) {
			return new RestResult<>(Response.Status.INTERNAL_SERVER_ERROR);
		}
		return new RestResult<>(Response.Status.OK);
	}

	/**
	 * Add the nodes and connections to the model extracted from the text
	 *
	 * @param model
	 *            CEGModel
	 * @return
	 */
	private CEGModel generateModelFromDescription(CEGModel model) throws SpecmateException {
		String text = model.getModelRequirements();
		if (text == null || StringUtils.isEmpty(text)) {
			return model;
		}
		text = new PersonalPronounsReplacer(tagger).replacePronouns(text);
		ELanguage lang = NLPUtil.detectLanguage(text);
		CEGFromRequirementGenerator generator;
		switch (lang) {
		case DE:
			generator = new GermanCEGFromRequirementGenerator(logService, tagger);
			break;
		default:
			generator = new EnglishCEGFromRequirementGenerator(logService, tagger);
		}
		generator.createModel(model, text);
		return model;
	}

	@Reference
	public void setLogService(LogService logService) {
		this.logService = logService;
	}

	@Reference
	void setNlptagging(INLPService tagger) {
		this.tagger = tagger;
	}

}
