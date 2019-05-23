package com.specmate.xtext;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.xtext.ISetup;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.resource.XtextResourceSet;
import org.eclipse.xtext.util.CancelIndicator;
import org.eclipse.xtext.validation.CheckMode;
import org.eclipse.xtext.validation.IResourceValidator;
import org.eclipse.xtext.validation.Issue;

import com.google.inject.Injector;

public class XTextUtil {
	public static EList<EObject> loadXTextResources(ISetup grammar ,URI mainFile, URI... paths) throws XTextException{
		// do this only once per application
		Injector injector = grammar.createInjectorAndDoEMFRegistration();
		 
		// obtain a resourceset from the injector
		XtextResourceSet resourceSet = injector.getInstance(XtextResourceSet.class);
		 
		// load a resource by URI, in this case from the file system
		Resource resource = resourceSet.getResource(mainFile, true);
		for(URI res: paths) {
			resourceSet.getResource(res, true);
		}
		
		// Validation
		IResourceValidator validator = ((XtextResource)resource).getResourceServiceProvider().getResourceValidator();
		List<Issue> issues = validator.validate(resource, CheckMode.ALL, CancelIndicator.NullImpl);
		if(issues.size() > 0) {
			String issueText = issues.stream().map(Issue::getMessage).collect(Collectors.joining("\n"));
			throw new XTextException("Validation encountered issues:\n"+issueText);
		}
		
		return resource.getContents();
	}
	
	public static EList<EObject> parseXTextResource(ISetup grammar, String data) throws XTextException {
		Injector injector = grammar.createInjectorAndDoEMFRegistration();
		XtextResourceSet resourceSet = injector.getInstance(XtextResourceSet.class);
		
		Resource resource = resourceSet.createResource(URI.createURI("dummy:/string.objectif"));
		InputStream in = new ByteArrayInputStream(data.getBytes());
		try {
			resource.load(in, resourceSet.getLoadOptions());
		} catch (IOException e) {
			throw new XTextException("Validation encountered issues:\n"+e.getMessage());
		}
		
		// Validation
		IResourceValidator validator = ((XtextResource)resource).getResourceServiceProvider().getResourceValidator();
		List<Issue> issues = validator.validate(resource, CheckMode.ALL, CancelIndicator.NullImpl);
		if(issues.size() > 0) {
			String issueText = issues.stream().map(Issue::getMessage).collect(Collectors.joining("\n"));
			throw new XTextException("Validation encountered issues:\n"+issueText);
		}
		return resource.getContents();
	}
}
