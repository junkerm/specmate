package com.specmate.systemtest.masterclonetest;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriBuilder;

import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.ClientProperties;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

public class RestClient {

	private Client restClient;
	private String restUrl;
	private int timeout;
	private Map<String, String> cookies = new HashMap<>();

	public RestClient(String restUrl, int timeout) {
		restClient = initializeClient();
		this.restUrl = restUrl;
		this.timeout = timeout;
	}

	public void close() {
		restClient.close();
	}

	public RestClient(String restUrl) {
		this(restUrl, 5000);
	}

	private Client initializeClient() {
		ClientConfig config = new ClientConfig();
		config.property(ClientProperties.CONNECT_TIMEOUT, timeout);
		config.property(ClientProperties.READ_TIMEOUT, timeout);
		Client client = ClientBuilder.newBuilder().withConfig(config).build();
		return client;
	}

	private Response rawGet(String url, String... params) {
		Invocation.Builder invocationBuilder = getInvocationBuilder(url, params);
		Response response = invocationBuilder.get();
		return response;
	}

	private Invocation.Builder getInvocationBuilder(String url, String... params) {
		UriBuilder uriBuilder = UriBuilder.fromUri(restUrl);
		uriBuilder.path(url);
		for (int i = 0; i < params.length; i += 2) {
			if (i < params.length - 1) {
				uriBuilder.queryParam(params[i], params[i + 1]);
			}
		}
		WebTarget getTarget = restClient.target(uriBuilder);
		Invocation.Builder invocationBuilder = getTarget.request();
		for (Entry<String, String> cookie : cookies.entrySet()) {
			invocationBuilder.cookie(cookie.getKey(), cookie.getValue());
		}
		return invocationBuilder;
	}

	public RestResult<JSONObject> get(String url, String... params) {
		Response response = rawGet(url, params);
		String result = response.readEntity(String.class);
		if (response.getStatusInfo().getStatusCode() == Status.OK.getStatusCode()) {
			return new RestResult<>(response, url, new JSONObject(new JSONTokener(result)));
		} else {
			return new RestResult<>(response, url, null);
		}
	}

	public RestResult<JSONArray> getList(String url, String... params) {
		Response response = rawGet(url, params);
		String result = response.readEntity(String.class);
		if (response.getStatusInfo().getStatusCode() == Status.OK.getStatusCode()) {
			return new RestResult<>(response, url, new JSONArray(new JSONTokener(result)));
		} else {
			return new RestResult<>(response, url, null);
		}
	}

	public RestResult<JSONObject> post(String url, JSONObject jsonObject) {
		Invocation.Builder invocationBuilder = getInvocationBuilder(url);
		Entity<String> entity;
		if (jsonObject == null) {
			entity = null;
		} else {
			entity = Entity.entity(jsonObject.toString(), "application/json;charset=utf-8");
		}
		Response response = invocationBuilder.post(entity);
		String result = response.readEntity(String.class);
		if (response.getStatusInfo().getStatusCode() == Status.OK.getStatusCode()) {
			return new RestResult<>(response, url, new JSONObject(new JSONTokener(result)));
		} else {
			return new RestResult<>(response, url, null);
		}
	}

	public RestResult<JSONObject> put(String url, JSONObject objectJson) {
		Invocation.Builder invocationBuilder = getInvocationBuilder(url);
		Response response = invocationBuilder.put(Entity.json(objectJson.toString()));
		return new RestResult<>(response, url, null);
	}

	public RestResult<Object> delete(String url) {
		Invocation.Builder invocationBuilder = getInvocationBuilder(url);
		Response response = invocationBuilder.delete();
		return new RestResult<>(response, url, null);
	}

	public void setCookie(String key, String value) {
		cookies.put(key, value);
	}

}
