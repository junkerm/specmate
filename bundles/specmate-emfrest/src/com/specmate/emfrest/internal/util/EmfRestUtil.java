package com.specmate.emfrest.internal.util;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response.Status;

public class EmfRestUtil {

	public static WebApplicationException throwInternalServerError(
			String message) {
		throw new WebApplicationException(message, Status.INTERNAL_SERVER_ERROR);
	}

	public static WebApplicationException throwBadRequest(String message) {
		throw new WebApplicationException(message,
				Status.BAD_REQUEST.getStatusCode());
	}

	public static WebApplicationException throwNotFound(String message) {
		throw new WebApplicationException(message, Status.NOT_FOUND);
	}
	
	public static WebApplicationException throwNotFound() {
		return throwNotFound("Resource not found");
	}
	
	public static WebApplicationException throwMethodNotAllowed(){
		throw new WebApplicationException("Method not allowed", Status.METHOD_NOT_ALLOWED);
	}
	


}
