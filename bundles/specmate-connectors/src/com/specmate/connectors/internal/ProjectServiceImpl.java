package com.specmate.connectors.internal;

import java.util.HashMap;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;

import com.specmate.connectors.api.IProject;
import com.specmate.connectors.api.IProjectService;

@Component
public class ProjectServiceImpl implements IProjectService {
	
	Map<String,IProject> projects = new HashMap<>();

	@Override
	public IProject getProject(String projectName) {
		return projects.get(projectName);
	}
	
	@Reference(cardinality=ReferenceCardinality.MULTIPLE, policy=ReferencePolicy.DYNAMIC)
	public void addProject(IProject project) {
		this.projects.put(project.getName(),project);
	}
	
	public void removeProject(IProject project) {
		this.projects.remove(project.getName());
	}

}
