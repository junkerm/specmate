package com.specmate.modelgeneration;

import java.net.URISyntaxException;

import javax.ws.rs.core.Response;

import org.apache.commons.lang3.StringUtils;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.log.LogService;

import com.specmate.cause_effect_patterns.resolve.XTextException;
import com.specmate.common.exception.SpecmateException;
import com.specmate.common.exception.SpecmateInternalException;
import com.specmate.emfrest.api.IRestService;
import com.specmate.emfrest.api.RestServiceBase;
import com.specmate.model.administration.ErrorCode;
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
		model.getContents().clear(); // Delete Contents
		
		Requirement req = (Requirement) model.eContainer();
		try {
			this.logService.log(LogService.LOG_INFO, "Model Generation STARTED");
			model = generateModelFromDescription(model, req);
			this.logService.log(LogService.LOG_INFO, "Model Generation FINISHED");
		} catch (SpecmateException e) {
			this.logService.log(LogService.LOG_ERROR, "Model Generation failed with following error:\n"+e.getMessage());
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
	 * @throws XTextException 
	 * @throws URISyntaxException 
	 */
	private CEGModel generateModelFromDescription(CEGModel model, Requirement requirement) throws SpecmateException {
		String text = model.getModelRequirements();
		if (text == null || StringUtils.isEmpty(text)) {
			return model;
		}
		text = new PersonalPronounsReplacer(tagger).replacePronouns(text);
		ELanguage lang = NLPUtil.detectLanguage(text);
		ICEGFromRequirementGenerator generator;
		
		try {
			generator = new PatternbasedCEGGenerator(lang, tagger);
		} catch (URISyntaxException | XTextException e) {
			throw new SpecmateInternalException(ErrorCode.INTERNAL_PROBLEM,
					"An error occured during creating the PatternbasedCEGGenerator:\n" + e.getMessage());
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
