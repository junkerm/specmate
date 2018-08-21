package com.specmate.rest;

import javax.ws.rs.core.Response;

public class RestResult<T> {

	private Response response;
	private String url;
	private T payload;
	private String userName;
	private Response.Status status;

	public RestResult(Response response, String url, T payload) {
		super();
		this.response = response;
		this.url = url;
		this.payload = payload;
	}

	public RestResult(Response.Status status, T payload, String userName) {
		this.status = status;
		this.payload = payload;
		this.userName = userName;
	}

	public RestResult(Response.Status status, T payload) {
		this(status, payload, null);
	}

	public RestResult(Response.Status status) {
		this(status, null, null);
	}

	public Response getResponse() {
		if (this.response == null && this.payload == null) {
			return Response.status(this.status).build();
		}
		if (this.response == null) {
			return Response.status(this.status).entity(this.payload).build();
		}
		return response;
	}

	public void setResponse(Response response) {
		this.response = response;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public T getPayload() {
		return payload;
	}

	public void setPayload(T payload) {
		this.payload = payload;
	}

	public String getUserName() {
		return this.userName;
	}

}
