package com.specmate.emfrest.internal.auth;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.HttpHeaders;

public class AuthorizationHeader {
	public static final String REALM = "specmate";
	public static final String AUTHENTICATION_SCHEME = "Token";

	public static String getFrom(ContainerRequestContext requestContext) {
		return requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);
	}

	public static String getFrom(HttpHeaders headers) {
		return headers.getHeaderString(HttpHeaders.AUTHORIZATION);
	}

	public static boolean isTokenBasedAuthentication(String authorizationHeader) {
		// Check if the Authorization header is valid
		// It must not be null and must be prefixed with "Token" plus a whitespace
		// The authentication scheme comparison must be case-insensitive
		return authorizationHeader != null
				&& authorizationHeader.toLowerCase().startsWith(AUTHENTICATION_SCHEME.toLowerCase() + " ");
	}

	public static String extractTokenFrom(String authorizationHeader) {
		return authorizationHeader.substring(AUTHENTICATION_SCHEME.length()).trim();
	}
}
