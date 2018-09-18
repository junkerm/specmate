package com.specmate.connectors.api;

import com.specmate.common.SpecmateException;
import com.specmate.common.SpecmateValidationException;

public interface IProjectConfigService {
	public void configureProjects(String[] projectNames) throws SpecmateException, SpecmateValidationException;
}
