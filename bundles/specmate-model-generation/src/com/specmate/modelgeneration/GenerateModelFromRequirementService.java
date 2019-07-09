package com.specmate.modelgeneration;

import java.net.URISyntaxException;

import javax.ws.rs.core.Response;

import org.apache.commons.lang3.StringUtils;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.log.LogService;

import com.specmate.common.exception.SpecmateException;
import com.specmate.config.api.IConfigService;
import com.specmate.emfrest.api.IRestService;
import com.specmate.emfrest.api.RestServiceBase;
import com.specmate.metrics.ICounter;
import com.specmate.metrics.IMetricsService;
import com.specmate.model.requirements.CEGModel;
import com.specmate.modelgeneration.legacy.EnglishCEGFromRequirementGenerator;
import com.specmate.modelgeneration.legacy.GermanCEGFromRequirementGenerator;
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
	private IConfigService configService;
	private IMetricsService metricsService; 
	private ICounter modelGenCounter;
	
	@Activate
	public void activate() throws SpecmateException {
		this.modelGenCounter = metricsService.createCounter("model_generation_counter", "Total number of generated models");
	}

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
		
		try {
			this.logService.log(LogService.LOG_INFO, "Model Generation STARTED");
			model = generateModelFromDescription(model);
			this.logService.log(LogService.LOG_INFO, "Model Generation FINISHED");
			this.modelGenCounter.inc();
		} catch (SpecmateException e) {
			this.logService.log(LogService.LOG_ERROR, "Model Generation failed with following error:\n"+e.getMessage());			
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
	 * @throws XTextException 
	 * @throws URISyntaxException 
	 */
	private CEGModel generateModelFromDescription(CEGModel model) throws SpecmateException {
		String text = model.getModelRequirements();
		if (text == null || StringUtils.isEmpty(text)) {
			return model;
		}
		text = new PersonalPronounsReplacer(tagger).replacePronouns(text);
		ELanguage lang = NLPUtil.detectLanguage(text);
		ICEGFromRequirementGenerator generator;
		

		if(lang == ELanguage.PSEUDO) {
			generator = new GenerateModelFromPseudoCode();
		} else {
			generator = new PatternbasedCEGGenerator(lang, tagger); 
		}

		
		try {
			generator.createModel(model, text);
		} catch( SpecmateException e) {
			// Generation Backof
			this.logService.log(LogService.LOG_INFO, "NLP model generation failed with the following error: \""+e.getMessage()+"\"");
			this.logService.log(LogService.LOG_INFO, "Backing off to rule based generation...");
			
			if(lang == ELanguage.DE) {
				generator = new GermanCEGFromRequirementGenerator(logService, tagger);
			} else {
				generator = new EnglishCEGFromRequirementGenerator(logService, tagger);
			}
			generator.createModel(model, text);
		}
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
	
	/** Service reference for config service */
	@Reference
	public void setConfigurationService(IConfigService configService) {
		this.configService = configService;
  }
  
	@Reference
	public void setMetricsService(IMetricsService metricsService) {
		this.metricsService = metricsService;
	}
}
