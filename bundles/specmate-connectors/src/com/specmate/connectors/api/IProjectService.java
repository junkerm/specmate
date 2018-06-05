package com.specmate.connectors.api;

import java.util.Set;

public interface IProjectService {

	IProject getProject(String projectName);
	Set<String> getProjectNames();

}
