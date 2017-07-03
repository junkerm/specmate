package com.specmate.emfrest.internal;

import java.io.IOException;
import java.util.List;
import java.util.StringTokenizer;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.internal.util.Base64;

public class AuthenticationFilter implements ContainerRequestFilter {

	private static final String AUTHORIZATION_PROPERTY = "Authorization";
	private static final String AUTHENTICATION_SCHEME = "Basic";
	private static final Response ACCESS_DENIED = Response.status(Response.Status.UNAUTHORIZED)
			.entity("You cannot access this resource").build();
	private static final Response ACCESS_FORBIDDEN = Response.status(Response.Status.FORBIDDEN)
			.entity("Access blocked for all users !!").build();

	@Override
	public void filter(ContainerRequestContext requestContext) throws IOException {

		// //If no authorization information present; block access
		// if(authorization == null || authorization.isEmpty())
		// {
		// requestContext.abortWith(ACCESS_DENIED);
		// return;
		// }

		// Get request headers
		final MultivaluedMap<String, String> headers = requestContext.getHeaders();

		final List<String> authorization = headers.get(AUTHORIZATION_PROPERTY);

		// Get encoded username and password
		final String encodedUserPassword = authorization.get(0).replaceFirst(AUTHENTICATION_SCHEME + " ", "");

		// Decode username and password
		String usernameAndPassword = new String(Base64.decode(encodedUserPassword.getBytes()));
		;

		// Split username and password tokens
		final StringTokenizer tokenizer = new StringTokenizer(usernameAndPassword, ":");
		final String username = tokenizer.nextToken();
		final String password = tokenizer.nextToken();

		// Verifying Username and password
		System.out.println(username);
		System.out.println(password);
	}

}
