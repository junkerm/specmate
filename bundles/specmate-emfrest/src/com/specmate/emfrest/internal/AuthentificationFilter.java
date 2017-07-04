package com.specmate.emfrest.internal;

import java.io.IOException;
import java.util.List;
import java.util.StringTokenizer;

import javax.inject.Inject;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.glassfish.jersey.internal.util.Base64;

import com.specmate.auth.api.IAuthentificationService;

public class AuthentificationFilter implements ContainerRequestFilter {

	private static final String AUTHORIZATION_PROPERTY = "Authorization";
	private static final String AUTHENTICATION_SCHEME = "Basic";
	private static final Response ACCESS_DENIED = Response.status(Response.Status.UNAUTHORIZED)
			.entity("You cannot access this resource").build();
	private static final Response ACCESS_FORBIDDEN = Response.status(Response.Status.FORBIDDEN)
			.entity("Access blocked for all users !!").build();

	@Inject
	private IAuthentificationService authenticationService;

	@Override
	public void filter(ContainerRequestContext requestContext) throws IOException {

		// Get request headers
		final MultivaluedMap<String, String> headers = requestContext.getHeaders();

		final List<String> authorization = headers.get(AUTHORIZATION_PROPERTY);
		// If no authorization information present; block access
		// if (authorization == null || authorization.isEmpty()) {
		// requestContext.abortWith(ACCESS_DENIED);
		// return;
		// }
		//
		// Pair<String, String> userNameAndPassword =
		// extractUserAndPassword(authorization.get(0));

		String url = requestContext.getUriInfo().getMatchedURIs(true).get(0);

		if (!authenticationService.authenticate("admin", "admin", url)) {
			requestContext.abortWith(ACCESS_DENIED);
			return;
		}
	}

	private Pair<String, String> extractUserAndPassword(String authorization) {
		// Get encoded username and password
		final String encodedUserPassword = authorization.replaceFirst(AUTHENTICATION_SCHEME + " ", "");

		// Decode username and password
		String decodedUsernameAndPassword = new String(Base64.decode(encodedUserPassword.getBytes()));

		// Split username and password tokens
		final StringTokenizer tokenizer = new StringTokenizer(decodedUsernameAndPassword, ":");
		final String username = tokenizer.nextToken();
		final String password = tokenizer.nextToken();
		return new ImmutablePair<>(username, password);

	}

}
