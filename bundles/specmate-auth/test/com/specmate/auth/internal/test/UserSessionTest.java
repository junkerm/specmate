package com.specmate.auth.internal.test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.specmate.usermodel.AccessRights;

public class UserSessionTest {

	/*@Test
	public void testIsAuthorized() {
		UserSession u1 = new UserSession(AccessRights.ALL, 1, "test");
		assertTrue(u1.isAuthorized("localhost/services/rest/test/resource1"));
		assertTrue(u1.isAuthorized("localhost/services/rest/test/resource1/resource2"));
		assertTrue(u1.isAuthorized("localhost/services/rest/test/"));
		assertFalse(u1.isAuthorized("localhost/services/rest/test"));
		assertFalse(u1.isAuthorized("localhost/services/rest/"));
		assertFalse(u1.isAuthorized("localhost/services/rest"));
	}
	
	@Test
	public void testRegexInjection() {
		UserSession u1 = new UserSession(AccessRights.ALL, 1, ".*");
		assertFalse(u1.isAuthorized("localhost/services/rest/project/resource1"));
		assertFalse(u1.isAuthorized("localhost/services/rest/project/"));
		assertFalse(u1.isAuthorized("localhost/services/rest/project"));
		assertFalse(u1.isAuthorized("localhost/services/rest/"));
		
		u1 = new UserSession(AccessRights.ALL, 1, "???");
		assertFalse(u1.isAuthorized("localhost/services/rest/pro/resource1"));
	}*/
}
