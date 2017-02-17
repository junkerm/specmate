package com.allianz.view.test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.function.Predicate;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.emf.ecore.EObject;
import org.glassfish.jersey.media.sse.EventInput;
import org.glassfish.jersey.media.sse.InboundEvent;
import org.glassfish.jersey.media.sse.SseFeature;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;
import org.osgi.service.log.LogService;
import org.osgi.util.tracker.ServiceTracker;

import com.specmate.common.SpecmateException;
import com.specmate.emfjson.EMFJsonSerializer;
import com.specmate.model.base.BasePackage;
import com.specmate.model.support.urihandler.IURIFactory;
import com.specmate.persistency.IPersistencyService;
import com.specmate.persistency.ITransaction;
import com.specmate.persistency.IView;
import com.specmate.persistency.event.EChangeKind;

public class EmfRestTest {

	private static final String NAME_KEY = "name";
	private static final int EVENT_TIMEOUT = 500;
	protected static final String URL_KEY = "___uri";
	protected static final String NSUEI_KEY = "___nsuri";
	protected static final String PROXY_KEY = "___proxy";
	protected static final String URL_KEY2 = "uri";
	protected static final String VALUE_KEY = "value";
	protected static final String CONTENTS_NAME = BasePackage.Literals.ICONTAINER__CONTENTS.getName();
	private static final String REST_URL = "http://localhost:8088/services/rest";
	protected static final String ECLASS = "___eclass";
	private static final String INDEX_KEY = "index";
	private static final String TYPE_KEY = "type";
	private static BundleContext context;
	private static IPersistencyService persistency;
	private static IURIFactory uriFactory;
	private static Client restClient;
	private static EMFJsonSerializer emfJsonSerializer;
	protected static IView view;

	protected static int counter = 0;
	protected static LogService logService;

	@BeforeClass
	public static void init() throws Exception {
		context = FrameworkUtil.getBundle(EmfRestTest.class).getBundleContext();
		uriFactory = getURIFactory();
		persistency = getPersistencyService();
		view = persistency.openView();
		restClient = getRestClient();
		emfJsonSerializer = new EMFJsonSerializer(uriFactory);
		logService = getLogger();
		clearPersistency();
	}

	private static LogService getLogger() throws InterruptedException {
		ServiceTracker<LogService, LogService> logTracker = new ServiceTracker<>(context, LogService.class.getName(),
				null);
		logTracker.open();
		LogService logService = logTracker.waitForService(10000);
		Assert.assertNotNull(logService);
		return logService;
	}

	private static void clearPersistency() throws SpecmateException {
		ITransaction transaction = persistency.openTransaction();
		transaction.getResource().getContents().clear();
		transaction.commit();
	}

	private static IPersistencyService getPersistencyService() throws InterruptedException, IOException {
		ServiceTracker<ConfigurationAdmin, ConfigurationAdmin> configTracker = new ServiceTracker<>(context,
				ConfigurationAdmin.class.getName(), null);
		configTracker.open();
		ConfigurationAdmin configAdmin = configTracker.getService();
		Configuration config = configAdmin
				.getConfiguration("com.specmate.persistency.cdo.internal.CDOPersistencyService");
		Dictionary<String, Object> properties = new Hashtable<>();
		properties.put("repositoryName", "testRepo");
		properties.put("resourceName", "restResource");
		config.update(properties);
		ServiceTracker<IPersistencyService, IPersistencyService> persistencyTracker = new ServiceTracker<>(context,
				IPersistencyService.class.getName(), null);
		persistencyTracker.open();
		IPersistencyService persistency = persistencyTracker.waitForService(100000);
		Assert.assertNotNull(persistency);
		return persistency;
	}

	private static IURIFactory getURIFactory() throws InterruptedException {
		ServiceTracker<IURIFactory, IURIFactory> factoryTracker = new ServiceTracker<>(context,
				IURIFactory.class.getName(), null);
		factoryTracker.open();
		IURIFactory uriFactory = factoryTracker.waitForService(2000);
		Assert.assertNotNull(uriFactory);
		return uriFactory;
	}

	private static Client getRestClient() {
		Client client = ClientBuilder.newBuilder().register(SseFeature.class).build();
		return client;
	}

	private static Future<Boolean> startEventListener(String url, Collection<Predicate<JSONObject>> eventPredicates) {
		final WebTarget eventTarget = restClient
				.target(REST_URL + (StringUtils.isEmpty(url) ? "" : "/" + url) + "/_events");

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

	protected static JSONObject get(String url) {
		return get(url, Status.OK);
	}

	protected static JSONObject get(String url, Response.Status expectedStatus) {
		WebTarget getTarget = restClient.target(REST_URL + "/" + url);
		Invocation.Builder invocationBuilder = getTarget.request();
		Response response = invocationBuilder.get();
		Assert.assertEquals(expectedStatus.getStatusCode(), response.getStatusInfo().getStatusCode());
		String result = response.readEntity(String.class);
		if (response.getStatusInfo().getStatusCode() == Status.OK.getStatusCode()) {
			return new JSONObject(new JSONTokener(result));
		} else {
			return null;
		}
	}

	private static PostResult post(String baseUrl, JSONObject jsonObject) {
		String url = baseUrl + "/list";
		WebTarget getTarget = restClient.target(REST_URL + "/" + url);
		url = url + "/" + jsonObject.getString(NAME_KEY);
		Invocation.Builder invocationBuilder = getTarget.request();
		Response response = invocationBuilder.post(Entity.json(jsonObject.toString()));
		return new PostResult(response, url);
	}

	protected static void put(String url, Object object) throws SpecmateException {
		WebTarget getTarget = restClient.target(REST_URL + "/" + url);
		JSONObject objectJson = ((object instanceof JSONObject) ? (JSONObject) object
				: emfJsonSerializer.serialize((EObject) object));

		Invocation.Builder invocationBuilder = getTarget.request();
		Response response = invocationBuilder.put(Entity.json(objectJson.toString()));
		Assert.assertEquals(Status.NO_CONTENT.getStatusCode(), response.getStatusInfo().getStatusCode());
	}

	private static void delete(String url) {
		WebTarget getTarget = restClient.target(REST_URL + "/" + url);

		Invocation.Builder invocationBuilder = getTarget.request();
		Response response = invocationBuilder.delete();
		Assert.assertEquals(Status.NO_CONTENT.getStatusCode(), response.getStatusInfo().getStatusCode());
	}

	static protected Predicate<JSONObject> expectEventWithValue(final String eventType, JSONObject value,
			final boolean ignoreUri) {
		return o -> {
			if (o.getString("type").equals(eventType)) {
				JSONObject jsonObject = o.optJSONObject(VALUE_KEY);
				if (null != jsonObject) {
					return compare(value, jsonObject, ignoreUri);
				}
			}
			return false;
		};
	}

	protected static Predicate<JSONObject> expectEventWithIndex(final String eventType, int index) {
		return o -> {
			if (o.getString(TYPE_KEY).equals(eventType)) {
				int theIndex = o.optInt(INDEX_KEY);
				return theIndex == index;
			}
			return false;
		};
	}

	protected static Predicate<JSONObject> expectEventWithUrl(final String eventType, final String url) {
		return o -> {
			String eventUrl = o.getString(URL_KEY2);
			if (o.getString("type").equals(eventType) && eventUrl.equals(url)) {
				return true;
			} else {
				return false;
			}
		};
	}

	static protected boolean compare(JSONObject jsonObject1, JSONObject jsonObject2, boolean ignoreUri) {
		for (String key : jsonObject1.keySet()) {
			if (ignoreUri && key.equals(URL_KEY)) {
				continue;
			}
			Object object = jsonObject1.get(key);
			if (object instanceof JSONObject) {
				JSONObject jObj = (JSONObject) object;
				JSONObject compare = jsonObject2.optJSONObject(key);
				if (!compareJsonObj(jObj, compare)) {
					return false;
				}
			} else if (object instanceof JSONArray) {
				JSONArray jsonArray = filterNonProxyObjects((JSONArray) object);
				JSONArray jsonArray2 = filterNonProxyObjects(jsonObject2.getJSONArray(key));
				for (int i = 0; i < jsonArray.length(); i++) {
					if (jsonArray.get(i) instanceof JSONObject) {
						compareJsonObj(jsonArray.getJSONObject(i), jsonArray2.getJSONObject(i));
					} else if (!jsonArray.get(i).equals(jsonArray2.get(i))) {
						return false;
					}
				}
			} else {
				if (!object.equals(jsonObject2.get(key))) {
					return false;
				}
			}
		}
		return true;
	}

	private static JSONArray filterNonProxyObjects(JSONArray jsonArray) {
		JSONArray newArray = new JSONArray();
		if (jsonArray == null || jsonArray.length() == 0) {
			return newArray;
		}
		for (int i = 0; i < jsonArray.length(); i++) {
			if (jsonArray.get(i) instanceof JSONObject) {
				JSONObject obj = (JSONObject) jsonArray.get(i);
				if (obj.optBoolean(PROXY_KEY)) {
					newArray.put(obj);
				}
			} else {
				newArray.put(jsonArray.get(i));
			}
		}
		return newArray;
	}

	private static boolean compareJsonObj(JSONObject jObj, JSONObject compare) {
		if (jObj.optBoolean(PROXY_KEY)) {
			return (jObj.getString(URL_KEY).equals(compare.get(URL_KEY)));
		}
		return false;
	}

	// static protected String postAndVerify(EObject object, String baseUrl,
	// String feature) throws SpecmateException {
	// return postAndVerify(object, baseUrl, feature, Arrays.asList());
	// }

	/**
	 * Posts <code>object</code> to the location <code>baseUrl</code>/
	 * <code>feature</code>. Waits for an event, specified by the predicate
	 * <code>eventPredicate</code>. Verifies that retrieving the object yields
	 * the originally posted one.
	 * 
	 * @param object
	 *            The object to post
	 * @param baseUrl
	 *            The base url
	 * @param feature
	 *            The name of the feature
	 * @param eventPredicate
	 *            predicate to verify the incoming event
	 * @return The url where the object has been posted to
	 * @throws SpecmateException
	 */
	// static protected String postAndVerify(EObject object, String baseUrl,
	// String feature,
	// Collection<Predicate<JSONObject>> eventPredicate) throws
	// SpecmateException {
	// String url = postAndWait(object, baseUrl, feature, eventPredicate);
	// JSONObject retrieved = get(url);
	// Assert.assertTrue(compare(emfJsonSerializer.serialize(object), retrieved,
	// true));
	// return retrieved.getString("___uri");
	// }

	protected static void putAndWait(Object object, String url, Predicate<JSONObject> eventPredicate)
			throws SpecmateException {
		Future<Boolean> future = startEventListener(url, Arrays.asList(eventPredicate));
		put(url, object);
		try {
			future.get(EVENT_TIMEOUT, TimeUnit.SECONDS);
		} catch (InterruptedException | ExecutionException | TimeoutException e) {
			throw new SpecmateException(e);
		}
	}

	protected static void putAndVerify(Object object, String url, Predicate<JSONObject> eventPredicate)
			throws SpecmateException {
		putAndWait(object, url, eventPredicate);
		JSONObject retrievedObject = get(url);
		JSONObject original = ((object instanceof JSONObject) ? (JSONObject) object
				: emfJsonSerializer.serialize((EObject) object));
		Assert.assertTrue(compare(original, retrievedObject, true));
	}

	protected static boolean deleteAndWait(String url) throws SpecmateException {
		Future<Boolean> future = startEventListener(url,
				Arrays.asList(expectEventWithUrl(EChangeKind.DELETE.toString(), url)));
		delete(url);
		try {
			future.get(EVENT_TIMEOUT, TimeUnit.SECONDS);
		} catch (InterruptedException | ExecutionException | TimeoutException e) {
			throw new SpecmateException(e);
		}
		return true;
	}

	protected static void deleteAndVerify(String url) throws SpecmateException {
		deleteAndWait(url);
		get(url, Status.NOT_FOUND);
	}

	protected static String getUrl(JSONObject jsonObject) {
		return jsonObject.getString(URL_KEY);
	}

	protected static String change(String original) {
		return "changed_" + original;
	}

	protected static JSONObject proxy(String url) {
		JSONObject jo = new JSONObject();
		jo.put(PROXY_KEY, true);
		jo.put(URL_KEY, url);
		return jo;
	}

	protected static void set(JSONObject jo, String featureName, Object obj) {
		jo.put(featureName, obj);
	}

	protected static void add(JSONObject jo, String featureName, Object obj) {
		if (jo.optJSONArray(featureName) == null) {
			jo.put(featureName, new JSONArray());
		}
		jo.getJSONArray(featureName).put(obj);
	}

	protected static void remove(JSONObject jo, String featureName, Object obj) {
		if (jo.optJSONArray(featureName) == null) {
			return;
		}
		JSONArray theArray = jo.getJSONArray(featureName);
		for (int i = 0; i <= theArray.length(); i++) {
			if (theArray.get(i) == obj) {
				theArray.remove(i);
				return;
			}
		}
	}

	protected static void addAttribute(String sourceUrl, String attribute, String targetUrl) throws SpecmateException {
		JSONObject sourceJson = get(sourceUrl);
		JSONObject targetProxy = proxy(targetUrl);
		add(sourceJson, attribute, targetProxy);
		putAndVerify(sourceJson, sourceUrl, expectEventWithValue(EChangeKind.ADD.toString(), targetProxy, false));
	}

	protected static void setAttribute(String sourceUrl, String attribute, String targetUrl) throws SpecmateException {
		JSONObject sourceJson = get(sourceUrl);
		JSONObject targetProxy = proxy(targetUrl);
		set(sourceJson, attribute, targetProxy);
		putAndVerify(sourceJson, sourceUrl, expectEventWithValue(EChangeKind.SET.toString(), targetProxy, false));
	}

	private JSONObject createFolder(String folderName) {
		JSONObject folder = new JSONObject();
		folder.put(NSUEI_KEY, "http://specmate.com/20170209/model/base");
		folder.put(ECLASS, "Folder");
		folder.put("name", folderName);
		return folder;
	}

	@Test
	public void testPostFolder() {
		JSONObject folder = createFolder("Test Folder");
		PostResult result = post("", folder);
		Assert.assertEquals(result.getResponse().getStatus(), Status.OK);
	}

}