package com.specmate.emfrest.internal;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.inject.Inject;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.log.LogService;

import com.specmate.auth.api.IAuthenticationService;
import com.specmate.common.SpecmateException;
import com.specmate.emfrest.authentication.Login;
import com.specmate.emfrest.authentication.Logout;

@Secured
@Provider
@Component(immediate=true, service=ContainerRequestFilter.class)
public class AuthenticationFilter implements ContainerRequestFilter {
	private static final String REALM = "specmate";
	private static final String AUTHENTICATION_SCHEME = "Token";
	private Pattern loginPattern = Pattern.compile(".+services/rest/" + Login.SERVICE_NAME);
	private Pattern logoutPattern = Pattern.compile(".+services/rest/" + Logout.SERVICE_NAME);
   
    @Inject
    IAuthenticationService authService;
    
    @Inject
    LogService logService;
    
	@Override
	public void filter(ContainerRequestContext requestContext) throws IOException {
		// Certain URIs should not be secured, e.g. login, in order to be of use. Logout does not need to be secured 
		// since this operation is not dependent on project authorization.
		if (isNotSecured(requestContext)) {
			return;
		}
		
		// Get the Authorization header from the request
        String authorizationHeader =
                requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);

        // Validate the Authorization header
        if (!isTokenBasedAuthentication(authorizationHeader)) {
        	logService.log(LogService.LOG_INFO, "Invalid authorization header: " + authorizationHeader);
            abortWithUnauthorized(requestContext);
            return;
        }

        // Extract the token from the Authorization header
        String token = authorizationHeader.substring(AUTHENTICATION_SCHEME.length()).trim();

        try {
            // Validate the token
            authService.validateToken(token, requestContext.getUriInfo().getAbsolutePath().getPath());
        } catch (SpecmateException e) {
        	logService.log(LogService.LOG_INFO, e.getMessage());
            abortWithUnauthorized(requestContext);
        }
	}
	
	private boolean isTokenBasedAuthentication(String authorizationHeader) {
        // Check if the Authorization header is valid
        // It must not be null and must be prefixed with "Token" plus a whitespace
        // The authentication scheme comparison must be case-insensitive
        return authorizationHeader != null && authorizationHeader.toLowerCase()
                    .startsWith(AUTHENTICATION_SCHEME.toLowerCase() + " ");
	}
	
    private void abortWithUnauthorized(ContainerRequestContext requestContext) {
        // Abort the filter chain with a 401 status code response
        // The WWW-Authenticate header is sent along with the response
        requestContext.abortWith(
                Response.status(Response.Status.UNAUTHORIZED)
                        .header(HttpHeaders.WWW_AUTHENTICATE, AUTHENTICATION_SCHEME +
                                " realm=\"" + REALM + "\"")
                        .build());
    }
    
    private boolean isNotSecured(ContainerRequestContext requestContext) {
    	String path = requestContext.getUriInfo().getAbsolutePath().toString();
    	Matcher matcherLogin = loginPattern.matcher(path);
    	Matcher matcherLogout = logoutPattern.matcher(path);
    	return matcherLogin.matches() || matcherLogout.matches();
    }
}
