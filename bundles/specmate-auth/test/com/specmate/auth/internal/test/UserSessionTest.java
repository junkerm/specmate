package com.specmate.auth.internal.test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.specmate.auth.internal.AccessRights;
import com.specmate.auth.internal.UserSession;

class UserSessionTest {

	@Test
	void testIsAuthorized() {
		UserSession u1 = new UserSession(AccessRights.AUTHENTICATE_ALL, 1, "test");
		assertTrue(u1.isAuthorized("localhost/services/rest/test/resource1"));
		assertTrue(u1.isAuthorized("localhost/services/rest/test/resource1/resource2"));
		assertTrue(u1.isAuthorized("localhost/services/rest/test/"));
		assertFalse(u1.isAuthorized("localhost/services/rest/test"));
		assertFalse(u1.isAuthorized("localhost/services/rest/"));
		assertFalse(u1.isAuthorized("localhost/services/rest"));
	}
}
