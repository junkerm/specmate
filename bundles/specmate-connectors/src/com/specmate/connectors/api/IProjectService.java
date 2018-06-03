package com.specmate.connectors.api;

import java.util.List;

public interface IProjectService {

	IProject getProject(String projectName);
	List<String> getProjectNames();

}
