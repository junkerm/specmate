package org.xtext.specmate.resolve;

import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.resource.XtextResourceSet;
import org.eclipse.xtext.util.CancelIndicator;
import org.eclipse.xtext.validation.CheckMode;
import org.eclipse.xtext.validation.IResourceValidator;
import org.eclipse.xtext.validation.Issue;
import org.xtext.specmate.SpecDSLStandaloneSetup;
import org.xtext.specmate.resolve.matcher.MatchRule;
import org.xtext.specmate.resolve.matcher.Matcher;
import org.xtext.specmate.resolve.matcher.MatcherException;
import org.xtext.specmate.resolve.util.RuleUtility;
import org.xtext.specmate.specDSL.Model;
import org.xtext.specmate.specDSL.Rule;

import com.google.inject.Injector;

public class XTextUtil {
	public static List<MatchRule> generateMatchers(URI resourcePath, URI... definitionPaths) {
		// do this only once per application
		Injector injector = new SpecDSLStandaloneSetup().createInjectorAndDoEMFRegistration();
		 
		// obtain a resourceset from the injector
		XtextResourceSet resourceSet = injector.getInstance(XtextResourceSet.class);
		 
		// load a resource by URI, in this case from the file system
		Resource resource = resourceSet.getResource(resourcePath, true);
		//Add any other models here
		for(URI res: definitionPaths) {
			resourceSet.getResource(res, true);
		}
		
		// Validation
		IResourceValidator validator = ((XtextResource)resource).getResourceServiceProvider().getResourceValidator();
		List<Issue> issues = validator.validate(resource, CheckMode.ALL, CancelIndicator.NullImpl);
		for (Issue issue : issues) {
		  System.err.println(issue.getMessage());
		}
		if(issues.size() > 0) {
			return null;
		}
		
		List<MatchRule> out = resource.getContents().stream()
				.filter(e -> e instanceof Model)
				.flatMap(m -> ((Model)m).getElements().stream())
				.filter(e -> e instanceof Rule)
				.map(r -> resolveRule((Rule)r))
				.filter(e -> e != null)
				.collect(Collectors.toList());
		
		return out;
	}
	
	private static MatchRule resolveRule(Rule r) {
		try {
			Matcher match = RuleUtility.transform(r);
			return new MatchRule(match, r.getName());	
		} catch (MatcherException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static void main(String[] args) {
		URI res = URI.createFileURI("../../runtime-EclipseXtext/testproject/test.depp");
		//Create URI works with spaces, wile createFileURI doesn't... Who knew?
		URI pos = URI.createURI("../../runtime-EclipseXtext/testproject/Pos EN.depp");
		URI dep = URI.createURI("../../runtime-EclipseXtext/testproject/Dep EN.depp");
		
		for(MatchRule r:generateMatchers(res, pos, dep)) {
			System.out.println(r);
			System.out.println();
		}
	}
}
