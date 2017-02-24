package com.allianz.view.test;

import org.json.JSONArray;
import org.json.JSONObject;

public class EmfRestTestUtil {

	protected static final String URL_KEY = "url";
	protected static final String PROXY_KEY = "___proxy";

	public static boolean compare(JSONObject jsonObject1, JSONObject jsonObject2, boolean ignoreUri) {
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
				JSONArray jsonArray2 = filterNonProxyObjects(jsonObject2.optJSONArray(key));
				if (jsonArray.length() == 0 && jsonArray2 == null) {
					continue;
				}
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
}
