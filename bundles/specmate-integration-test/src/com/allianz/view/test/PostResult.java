package com.allianz.view.test;

import javax.ws.rs.core.Response;

public class PostResult {

	private Response response;
	private String url;

	public PostResult(Response response, String url) {
		super();
		this.response = response;
		this.url = url;
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

}
