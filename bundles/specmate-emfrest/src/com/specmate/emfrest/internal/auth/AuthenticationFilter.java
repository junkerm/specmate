package com.specmate.emfrest.internal.auth;

import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.inject.Inject;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.Provider;

import org.osgi.service.log.LogService;

import com.specmate.auth.api.IAuthenticationService;
import com.specmate.common.exception.SpecmateException;
import com.specmate.emfrest.authentication.Login;
import com.specmate.emfrest.authentication.Logout;
import com.specmate.emfrest.authentication.ProjectNames;
import com.specmate.model.administration.AdministrationFactory;
import com.specmate.model.administration.ErrorCode;
import com.specmate.model.administration.ProblemDetail;

@Secured
@Provider
public class AuthenticationFilter implements ContainerRequestFilter {
	private static final String REINDEX_SERVICE_NAME = "reindex";
	private final String HEARTBEAT_PARAMETER = "heartbeat";
	private final String REST_URL = ".+services/rest/";
	private Pattern loginPattern = Pattern.compile(REST_URL + Login.SERVICE_NAME);
	private Pattern logoutPattern = Pattern.compile(REST_URL + Logout.SERVICE_NAME);
	private Pattern projectNamesPattern = Pattern.compile(REST_URL + ProjectNames.SERVICE_NAME);
	private Pattern reindexPattern = Pattern.compile(REST_URL + REINDEX_SERVICE_NAME);

	@Inject
	IAuthenticationService authService;

	@Inject
	LogService logService;

	@Override
	public void filter(ContainerRequestContext requestContext) throws IOException {
		// Certain URIs should not be secured, e.g. login, in order to be of
		// use. Logout
		// does not need to be secured
		// since this operation is not dependent on project authorization.
		if (isNotSecured(requestContext)) {
			return;
		}

		// Get the Authorization header from the request
		String authorizationHeader = AuthorizationHeader.getFrom(requestContext);

		// Validate the Authorization header
		if (!AuthorizationHeader.isTokenBasedAuthentication(authorizationHeader)) {
			logService.log(LogService.LOG_INFO, "Invalid authorization header: " + authorizationHeader + " on path "
					+ requestContext.getUriInfo().getAbsolutePath().toString());
			abortWithUnauthorized(requestContext);
			return;
		}

		// Extract the token from the Authorization header
		String token = AuthorizationHeader.extractTokenFrom(authorizationHeader);

		try {
			String path = requestContext.getUriInfo().getAbsolutePath().getPath();
			boolean refresh = !isHeartBeat(requestContext);
			authService.validateToken(token, path, refresh);
		} catch (SpecmateException e) {
			logService.log(LogService.LOG_INFO, e.getMessage());
			abortWithUnauthorized(requestContext);
		}
	}

	private boolean isHeartBeat(ContainerRequestContext requestContext) {
		MultivaluedMap<String, String> parameters = requestContext.getUriInfo().getQueryParameters();
		if (!parameters.containsKey(HEARTBEAT_PARAMETER)) {
			return false;
		}

		List<String> values = parameters.get(HEARTBEAT_PARAMETER);
		if (values.isEmpty()) {
			return false;
		}

		return Boolean.parseBoolean(values.get(0));
	}

	private void abortWithUnauthorized(ContainerRequestContext requestContext) {
		Status status = Status.UNAUTHORIZED;
		ProblemDetail pd = AdministrationFactory.eINSTANCE.createProblemDetail();
		pd.setStatus(status.getStatusCode());
		pd.setEcode(ErrorCode.NO_AUTHORIZATION);
		pd.setDetail(requestContext.getUriInfo().getAbsolutePath().toString());
		// Abort the filter chain with a 401 status code response
		// The WWW-Authenticate header is sent along with the response
		requestContext.abortWith(Response.status(status)
				.header(HttpHeaders.WWW_AUTHENTICATE,
						AuthorizationHeader.AUTHENTICATION_SCHEME + " realm=\"" + AuthorizationHeader.REALM + "\"")
				.entity(pd).build());
	}

	private boolean isNotSecured(ContainerRequestContext requestContext) {
		String path = requestContext.getUriInfo().getAbsolutePath().toString();
		Matcher matcherLogin = loginPattern.matcher(path);
		Matcher matcherLogout = logoutPattern.matcher(path);
		Matcher matcherProjectNames = projectNamesPattern.matcher(path);
		Matcher matcherReindex = reindexPattern.matcher(path);
		return matcherLogin.matches() || matcherLogout.matches() || matcherProjectNames.matches()
				|| matcherReindex.matches();
	}
}
