package com.specmate.common;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.function.Predicate;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriBuilder;

import org.apache.commons.lang3.StringUtils;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.ClientProperties;
import org.glassfish.jersey.media.sse.EventInput;
import org.glassfish.jersey.media.sse.InboundEvent;
import org.glassfish.jersey.media.sse.SseFeature;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

public class RestClient {

	private Client restClient;
	private String restUrl;
	private int timeout;

	public RestClient(String restUrl, int timeout) {
		this.restClient = initializeClient();
		this.restUrl = restUrl;
		this.timeout = timeout;
	}

	public RestClient(String restUrl) {
		this(restUrl, 5000);
	}

	private Client initializeClient() {
		ClientConfig config = new ClientConfig();
		config.property(ClientProperties.CONNECT_TIMEOUT, timeout);
		config.property(ClientProperties.READ_TIMEOUT, timeout);
		Client client = ClientBuilder.newBuilder().withConfig(config).register(SseFeature.class).build();
		return client;
	}

	private Future<Boolean> startEventListener(String url, Collection<Predicate<JSONObject>> eventPredicates) {
		final WebTarget eventTarget = restClient
				.target(restUrl + (StringUtils.isEmpty(url) ? "" : "/" + url) + "/_events");

		ExecutorService executor = Executors.newFixedThreadPool(1);
		Future<Boolean> future = executor.submit(new Callable<Boolean>() {
			@Override
			public Boolean call() {
				if (eventPredicates.isEmpty()) {
					return true;
				}
				List<Predicate<JSONObject>> predicatesToCheck = new ArrayList<>(eventPredicates);
				final EventInput eventInput = eventTarget.request().get(EventInput.class);
				while (true) {
					if (predicatesToCheck.isEmpty()) {
						return true;
					}
					final InboundEvent inboundEvent = eventInput.read();
					if (inboundEvent == null) {
						return null;
					}
					JSONObject jsonObject = new JSONObject(new JSONTokener(inboundEvent.readData(String.class)));
					Iterator<Predicate<JSONObject>> predicateIterator = predicatesToCheck.iterator();
					while (predicateIterator.hasNext()) {
						if (predicateIterator.next().test(jsonObject)) {
							predicateIterator.remove();
						}
					}
				}
			}
		});
		try {
			Thread.sleep(200);
		} catch (InterruptedException e) {
			// ingore
		}
		return future;
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

	public RestResult<JSONArray> getList(String url) {
		Response response = rawGet(url);
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
		return new RestResult<>(response, url, null);
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

}
