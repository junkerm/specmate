package com.specmate.cause_effect_patterns.resolve;

import java.util.List;
import java.util.Vector;
import java.util.stream.Collectors;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.resource.XtextResourceSet;
import org.eclipse.xtext.util.CancelIndicator;
import org.eclipse.xtext.validation.CheckMode;
import org.eclipse.xtext.validation.IResourceValidator;
import org.eclipse.xtext.validation.Issue;

import com.google.inject.Injector;
import com.specmate.cause_effect_patterns.internal.SpecDSLStandaloneSetup;
import com.specmate.cause_effect_patterns.internal.specDSL.Model;
import com.specmate.cause_effect_patterns.internal.specDSL.Rule;
import com.specmate.cause_effect_patterns.parse.matcher.MatchRule;
import com.specmate.cause_effect_patterns.parse.matcher.MatcherBase;
import com.specmate.cause_effect_patterns.parse.matcher.MatcherException;
import com.specmate.cause_effect_patterns.resolve.util.RuleUtility;

public class XTextUtil {
	public static List<MatchRule> generateMatchers(URI resourcePath, URI... definitionPaths) throws XTextException {
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
		if(issues.size() > 0) {
			String issueText = issues.stream().map(Issue::getMessage).collect(Collectors.joining("\n"));
			throw new XTextException("Validation encountered issues:\n"+issueText);
		}
		
		List<Rule> rules = resource.getContents().stream()
				.filter(e -> e instanceof Model)
				.flatMap(m -> ((Model)m).getElements().stream())
				.filter(e -> e instanceof Rule)
				.map(r -> (Rule) r)
				.collect(Collectors.toList());
		List<MatchRule> result = new Vector<MatchRule>();
		for (Rule r:rules) {
			try {
				result.add(resolveRule(r));
			} catch (MatcherException e) {
				throw new XTextException(e.getMessage());
			}
		}
		
		return result;
	}
	
	private static MatchRule resolveRule(Rule r) throws MatcherException {
		MatcherBase match = RuleUtility.transform(r);
		return new MatchRule(match, r.getName());	
	}
}
