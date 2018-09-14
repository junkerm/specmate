package com.specmate.connectors.api;

import com.specmate.common.SpecmateException;

public interface IProjectConfigService {
	public void configureProjects(String[] projectNames) throws SpecmateException;
}
