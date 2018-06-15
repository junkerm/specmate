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
import com.specmate.usermodel.UserSession;

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
		UserSession session = sessionService.create(AccessRights.ALL, AccessRights.ALL, projectName);
		assertTrue(sessionService.isAuthorized(session.getId(), baseURL + projectName + "/resource1"));
		assertTrue(sessionService.isAuthorized(session.getId(), baseURL + projectName + "/resource1/resource2"));
		assertTrue(sessionService.isAuthorized(session.getId(), baseURL + projectName + "/"));
		assertFalse(sessionService.isAuthorized(session.getId(), baseURL + projectName));
		assertFalse(sessionService.isAuthorized(session.getId(), baseURL));
		assertFalse(sessionService.isAuthorized(session.getId(), baseURL.substring(0, baseURL.length() - 1)));
	}

	@Test
	public void testRegexInjection() throws SpecmateException {
		UserSession session = sessionService.create(AccessRights.ALL, AccessRights.ALL, "testRegexInjection");
		assertFalse(sessionService.isAuthorized(session.getId(), baseURL + "project/resource1"));
		assertFalse(sessionService.isAuthorized(session.getId(), baseURL + "project/"));
		assertFalse(sessionService.isAuthorized(session.getId(), baseURL + "project"));

		session = sessionService.create(AccessRights.ALL, AccessRights.ALL, "");
		assertFalse(sessionService.isAuthorized(session.getId(), baseURL + "pro/resource1"));
		sessionService.delete(session.getId());

		session = sessionService.create(AccessRights.ALL, AccessRights.ALL, "?");
		assertFalse(sessionService.isAuthorized(session.getId(), baseURL + "p/resource1"));
		sessionService.delete(session.getId());

		session = sessionService.create(AccessRights.ALL, AccessRights.ALL, ".*");
		assertFalse(sessionService.isAuthorized(session.getId(), baseURL + "pr/resource1"));
		sessionService.delete(session.getId());

		session = sessionService.create(AccessRights.ALL, AccessRights.ALL, ".+");
		assertFalse(sessionService.isAuthorized(session.getId(), baseURL + "pro/resource1"));
	}

	@Test
	public void testDeleteSession() throws SpecmateException {
		boolean thrown = false;
		String projectName = "testDeleteSession";
		UserSession session = sessionService.create(AccessRights.ALL, AccessRights.ALL, projectName);
		assertTrue(sessionService.isAuthorized(session.getId(), baseURL + projectName + "/resource1"));
		sessionService.delete(session.getId());
		try {
			assertFalse(sessionService.isAuthorized(session.getId(), baseURL + projectName + "/resource1"));
		} catch (SpecmateException e) {
			thrown = true;
		}

		assertTrue(thrown);
	}

	private static ISessionService getSessionService() throws Exception {
		Filter sessionFilter = context.createFilter("(impl=volatile)");
		ServiceTracker<ISessionService, ISessionService> sessionTracker = new ServiceTracker<>(context, sessionFilter,
				null);
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
