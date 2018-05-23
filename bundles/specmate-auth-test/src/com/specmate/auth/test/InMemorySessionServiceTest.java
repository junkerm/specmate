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
	
	@BeforeClass
	public static void init() throws Exception {
		context = FrameworkUtil.getBundle(InMemorySessionServiceTest.class).getBundleContext();
		sessionService = getSessionService();
	}
	
	@Test
	public void testIsAuthorized() throws SpecmateException {
		String token = sessionService.create(AccessRights.ALL, "test");
		assertTrue(sessionService.isAuthorized(token, "localhost/services/rest/test/resource1"));
		assertTrue(sessionService.isAuthorized(token, "localhost/services/rest/test/resource1/resource2"));
		assertTrue(sessionService.isAuthorized(token, "localhost/services/rest/test/"));
		assertFalse(sessionService.isAuthorized(token, "localhost/services/rest/test"));
		assertFalse(sessionService.isAuthorized(token, "localhost/services/rest/"));
		assertFalse(sessionService.isAuthorized(token, "localhost/services/rest"));
		sessionService.delete(token);
	}
	
	@Test
	public void testRegexInjection() throws SpecmateException {
		String token = sessionService.create(AccessRights.ALL, "test");
		assertFalse(sessionService.isAuthorized(token, "localhost/services/rest/project/resource1"));
		assertFalse(sessionService.isAuthorized(token, "localhost/services/rest/project/"));
		assertFalse(sessionService.isAuthorized(token, "localhost/services/rest/project"));
		assertFalse(sessionService.isAuthorized(token, "localhost/services/rest/"));
		sessionService.delete(token);
		
		token = sessionService.create(AccessRights.ALL, "");
		assertFalse(sessionService.isAuthorized(token, "localhost/services/rest/pro/resource1"));
		sessionService.delete(token);
		
		token = sessionService.create(AccessRights.ALL, "?");
		assertFalse(sessionService.isAuthorized(token, "localhost/services/rest/p/resource1"));
		sessionService.delete(token);
		
		token = sessionService.create(AccessRights.ALL, ".*");
		assertFalse(sessionService.isAuthorized(token, "localhost/services/rest/pr/resource1"));
		sessionService.delete(token);
		
		token = sessionService.create(AccessRights.ALL, ".+");
		assertFalse(sessionService.isAuthorized(token, "localhost/services/rest/pro/resource1"));
		sessionService.delete(token);
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
