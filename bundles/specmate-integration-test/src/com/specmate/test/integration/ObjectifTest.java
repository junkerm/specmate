package com.specmate.test.integration;

import java.net.URISyntaxException;
import java.util.List;

import org.eclipse.emf.common.util.URI;
import org.junit.Test;
import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

import com.specmate.objectif.resolve.BusinessRuleUtil;
import com.specmate.objectif.resolve.rule.BusinessRuleNode;
import com.specmate.xtext.XTextException;

public class ObjectifTest {
	@Test
	public void testLoadRules() throws URISyntaxException, XTextException {
		loadRules("/resources/test_rules.objectif");
	}
	
	private void loadRules(String mainFile) throws URISyntaxException, XTextException {
		URI main = getLocalFile(mainFile);
		List<BusinessRuleNode> rules = new BusinessRuleUtil().loadXTextResources(main);
		rules.forEach(System.out::println);
	}
	
	private URI getLocalFile(String fileName) throws URISyntaxException {
		Bundle bundle = FrameworkUtil.getBundle(NLPServiceTest.class);
		return URI.createURI(bundle.getResource(fileName).toURI().toString());
	}
}
