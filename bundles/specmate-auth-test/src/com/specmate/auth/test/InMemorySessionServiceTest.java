package com.specmate.auth.test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.osgi.framework.BundleContext;
import org.osgi.framework.Filter;
import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;

import com.specmate.auth.api.ISessionService;
import com.specmate.common.SpecmateException;
import com.specmate.usermodel.AccessRights;

public class InMemorySessionServiceTest {
	private static ISessionService sessionService;
	private static BundleContext context;
	private String baseURL = "localhost/services/rest/";
	
	@BeforeClass
	public static void init() throws Exception {
		context = FrameworkUtil.getBundle(InMemorySessionServiceTest.class).getBundleContext();
		sessionService = getSessionService();
	}
	
	@Test
	public void testIsAuthorized() throws SpecmateException {
		String projectName = "testIsAuthorized";
		String token = sessionService.create(AccessRights.ALL, projectName);
		assertTrue(sessionService.isAuthorized(token, baseURL + projectName + "/resource1"));
		assertTrue(sessionService.isAuthorized(token, baseURL + projectName + "/resource1/resource2"));
		assertTrue(sessionService.isAuthorized(token, baseURL + projectName + "/"));
		assertFalse(sessionService.isAuthorized(token, baseURL + projectName));
		assertFalse(sessionService.isAuthorized(token, baseURL));
		assertFalse(sessionService.isAuthorized(token, baseURL.substring(0, baseURL.length() - 1)));
	}
	
	@Test
	public void testRegexInjection() throws SpecmateException {
		String token = sessionService.create(AccessRights.ALL, "testRegexInjection");
		assertFalse(sessionService.isAuthorized(token, baseURL + "project/resource1"));
		assertFalse(sessionService.isAuthorized(token, baseURL + "project/"));
		assertFalse(sessionService.isAuthorized(token, baseURL + "project"));
		
		token = sessionService.create(AccessRights.ALL, "");
		assertFalse(sessionService.isAuthorized(token, baseURL + "pro/resource1"));
		sessionService.delete(token);
		
		token = sessionService.create(AccessRights.ALL, "?");
		assertFalse(sessionService.isAuthorized(token, baseURL + "p/resource1"));
		sessionService.delete(token);
		
		token = sessionService.create(AccessRights.ALL, ".*");
		assertFalse(sessionService.isAuthorized(token, baseURL + "pr/resource1"));
		sessionService.delete(token);
		
		token = sessionService.create(AccessRights.ALL, ".+");
		assertFalse(sessionService.isAuthorized(token,  baseURL + "pro/resource1"));
	}
	
	@Test
	public void testDeleteSession() throws SpecmateException {
		boolean thrown = false;
		String projectName = "testDeleteSession";
		String token = sessionService.create(AccessRights.ALL, projectName);
		assertTrue(sessionService.isAuthorized(token, baseURL + projectName + "/resource1"));
		sessionService.delete(token);
		try {
			assertFalse(sessionService.isAuthorized(token, baseURL + projectName + "/resource1"));
		} catch (SpecmateException e) {
			thrown = true;
		}
		
		assertTrue(thrown);
	}
	
	private static ISessionService getSessionService() throws Exception {
		Filter sessionFilter = context.createFilter("(impl=volatile)");
		ServiceTracker<ISessionService, ISessionService> sessionTracker = new ServiceTracker<>(context,
				sessionFilter, null);
		sessionTracker.open();
		ISessionService sessionService;
		try {
			sessionService = sessionTracker.waitForService(10000);
		} catch (InterruptedException e) {
			throw new SpecmateException(e);
		}
		Assert.assertNotNull(sessionService);
		return sessionService;
	}
}
