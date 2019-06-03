package com.specmate.modelgeneration;

import com.specmate.common.exception.SpecmateException;
import com.specmate.model.requirements.CEGModel;

public interface ICEGFromRequirementGenerator {
	public abstract CEGModel createModel(CEGModel model, String text) throws SpecmateException;
}
