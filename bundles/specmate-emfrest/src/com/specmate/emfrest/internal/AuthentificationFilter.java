package com.specmate.emfrest.internal;

import java.io.IOException;

import javax.inject.Inject;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.log.LogService;

import com.specmate.auth.api.IAuthentificationService;
import com.specmate.common.SpecmateException;
import com.specmate.emfrest.authentication.Login;

@Secured
@Provider
@Component(immediate=true, service=ContainerRequestFilter.class)
public class AuthentificationFilter implements ContainerRequestFilter {
	private static final String REALM = "specmate";
   
    @Inject
    IAuthentificationService authService;
    
    @Inject
    LogService logService;
    
	@Override
	public void filter(ContainerRequestContext requestContext) throws IOException {
		// Certain URIs should not be secured, e.g. login.
		if (isNotSecured(requestContext)) {
			return;
		}
		
		// Get the Authorization header from the request
        String authorizationHeader =
                requestContext.getHeaderString(HttpHeaders.WWW_AUTHENTICATE);

        // Validate the Authorization header
        if (authorizationHeader == null) {
        	logService.log(LogService.LOG_INFO, "Invalid authorization header: " + authorizationHeader);
            abortWithUnauthorized(requestContext);
            return;
        }

        // Extract the token from the Authorization header
        String token = authorizationHeader.trim();

        try {
            // Validate the token
            authService.validateToken(token);

        } catch (SpecmateException e) {
        	logService.log(LogService.LOG_INFO, e.getMessage());
            abortWithUnauthorized(requestContext);
        }
	}
	
    private void abortWithUnauthorized(ContainerRequestContext requestContext) {
        // Abort the filter chain with a 401 status code response
        // The WWW-Authenticate header is sent along with the response
        requestContext.abortWith(
                Response.status(Response.Status.UNAUTHORIZED)
                        .header(HttpHeaders.WWW_AUTHENTICATE, 
                                "realm=\"" + REALM + "\"")
                        .build());
    }
    
    private boolean isNotSecured(ContainerRequestContext requestContext) {
    	return requestContext.getUriInfo().getPath().endsWith(Login.SERVICE_NAME);
    }
}
