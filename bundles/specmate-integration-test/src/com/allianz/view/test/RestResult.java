package com.allianz.view.test;

import javax.ws.rs.core.Response;

public class RestResult<T>  {

	private Response response;
	private String url;
	private T payload;

	public RestResult(Response response, String url, T payload) {
		super();
		this.response = response;
		this.url = url;
		this.payload = payload;
	}

	public Response getResponse() {
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
	
	

}
