package com.specmate.emfjson;

import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.specmate.common.AssertUtil;
import com.specmate.common.ISerializationConfiguration;
import com.specmate.common.exception.SpecmateException;
import com.specmate.common.exception.SpecmateInternalException;
import com.specmate.model.administration.ErrorCode;
import com.specmate.urihandler.IURIFactory;

/**
 * Serializer for EMF models to JSON
 *
 * @author junkerm
 *
 */
public class EMFJsonSerializer {

	/** namespace URI JSON key */
	public static final String KEY_NSURI = "___nsuri";

	/** eclass JSON key */
	public static final String KEY_ECLASS = "className";

	/** uri JSON key */
	public static final String KEY_URI = "url";

	/** proxy JSON key */
	public static final String KEY_PROXY = "___proxy";

	/** uri factory for generating URIs from EObjects */
	private IURIFactory uriFactory;

	/** configuration of this serializer */
	private ISerializationConfiguration config;

	/** constructor */
	public EMFJsonSerializer() {
		this(new IURIFactory() {

			@Override
			public String getURI(EObject object) {
				return object.eResource().getURIFragment(object);
			}
		}, new ISerializationConfiguration() {

			@Override
			public boolean serializeContainedElements(EObject object) {
				return false;
			}
		});
	}

	/**
	 * constructor
	 */
	public EMFJsonSerializer(IURIFactory uriFactory) {
		this(uriFactory, new ISerializationConfiguration() {

			@Override
			public boolean serializeContainedElements(EObject object) {
				return false;
			}
		});
	}

	/**
	 * constructor
	 *
	 * @param uriFactory
	 *            The IURIFactory that is used for generating URIs from EObjects
	 * @param stopPredicate
	 *            The stop predicate to indicate where to stop serializing
	 */
	public EMFJsonSerializer(IURIFactory uriFactory, ISerializationConfiguration config) {
		this.uriFactory = uriFactory;
		this.config = config;
	}

	/**
	 * Serializes an {@link EObject} to JSON
	 *
	 * @param eObject
	 *            The {@link EObject} to serialize
	 * @return The JSON representation of <code>object</code>
	 * @throws JSONException
	 * @throws SpecmateException
	 */
	public JSONObject serialize(EObject eObject) throws JSONException, SpecmateException {
		try {
			return serializeObject(eObject);
		} catch (Exception e) {
			throw new SpecmateInternalException(ErrorCode.SERALIZATION, e);
		}
	}

	/**
	 * Serializes a list of {@link EObject} to JSON
	 *
	 * @param list
	 *            The list of {@link EObject}s to serialize
	 * @return The JSON representation of <code>list</code>
	 * @throws SpecmateException
	 *             If the object cannot be serialized
	 */
	public JSONArray serialize(List<?> list) throws JSONException, SpecmateException {
		try {
			return serializeList(list);
		} catch (Exception e) {
			throw new SpecmateInternalException(ErrorCode.SERALIZATION, e);
		}
	}

	/**
	 * Serializes an {@link EObject} to JSON at certain serializing depth. Stops
	 * serializing if indicated by {@link ISerializerStopPredicate.stopAtDepth} from
	 * the currently set stop predicate.
	 *
	 * @param eObject
	 *            The {@link EObject} to serialize
	 * @return The JSON representation of <code>eObject</code>
	 * @throws SpecmateException
	 */
	private JSONObject serializeObject(EObject eObject) throws SpecmateException {
		JSONObject result = new JSONObject();
		serializeType(eObject, result);
		serializeUri(eObject, result);
		serializeFeatures(eObject, result);

		return result;
	}

	/**
	 * Serializes the type informations (namespace URI and class name) of eObject to
	 * the given {@link JSONObject}.
	 *
	 * @param eObject
	 *            The {@link EObject} for which to serialize the type information
	 * @param jsonObj
	 *            The JSON object where to put the serialized type information.
	 * @throws JSONException
	 */
	private void serializeType(EObject eObject, JSONObject jsonObj) throws JSONException {
		EClass eClass = eObject.eClass();
		String uri = eClass.getEPackage().getNsURI();
		String className = eClass.getName();
		jsonObj.put(KEY_ECLASS, className);
		jsonObj.put(KEY_NSURI, uri);
	}

	/**
	 * Serializes the URI of eObject into the given {@link JSONObject}.
	 *
	 * @param eObject
	 *            The {@link EObject} of which to serialize the URI
	 * @param jsonObj
	 *            The {@link JSONObject} where to put the serialized URI
	 * @throws SpecmateException
	 */
	private void serializeUri(EObject eObject, JSONObject jsonObj) throws SpecmateException {
		jsonObj.put(KEY_URI, uriFactory.getURI(eObject));
	}

	/**
	 * Serializes a value. EObjects and Lists are handled recursively, any other
	 * type of object is serialized as String.
	 *
	 * @param value
	 *            The value to serialize
	 * @return The JSON representation of <code>value</code>
	 * @throws SpecmateException
	 */
	private Object serializeValue(Object value) throws SpecmateException {
		if (value instanceof EList) {
			return serializeList((EList<?>) value);
		} else if (value instanceof EObject) {
			return serializeObject((EObject) value);
		} else if (value instanceof Double) {
			return value;
		} else if (value instanceof Boolean) {
			return value;
		} else {
			return value.toString();
		}
	}

	/**
	 * Serializes a list of objects
	 *
	 * @param list
	 *            The list of objects to serialize
	 * @return A {@link JSONArray} containing the JSON representation of all members
	 *         of <code>list</code>
	 * @throws SpecmateException
	 */
	private JSONArray serializeList(List<?> list) throws SpecmateException {
		JSONArray array = new JSONArray();
		for (Object value : list) {
			array.put(serializeValue(value));
		}
		return array;
	}

	/**
	 * Serializes an EObject or a List as proxy JSON structures. That means
	 * value<code>value</code> is not completely serialized but only the URI
	 * inforamtion.
	 *
	 * @param value
	 *            The value to serialize as proxy
	 * @return A JSON proxy structure
	 * @throws SpecmateException
	 */
	private Object serializeProxy(Object value) throws SpecmateException {
		if (value instanceof EObject) {
			return getProxy((EObject) value);
		} else if (value instanceof EList) {
			JSONArray jsonArray = new JSONArray();
			for (Object element : (EList<?>) value) {
				jsonArray.put(serializeProxy(element));
			}
			return jsonArray;
		}
		AssertUtil.assertTrue(false, "No other type than EList or EObject " + "expected for json proxy serialization");

		return null;

	}

	/**
	 * Transforms an {@link EObject} into a JSON proxy structure, i.e. a
	 * {@link JSONObject} that contains the uri of the {@link EObject}.
	 *
	 * @param eObject
	 *            The {@link EObject} for which to obtain a proxy
	 * @return The JSON proxy structure
	 * @throws SpecmateException
	 */
	public Object getProxy(EObject eObject) throws SpecmateException {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put(KEY_PROXY, true);
		jsonObject.put(KEY_URI, uriFactory.getURI(eObject));
		jsonObject.put(KEY_ECLASS, eObject.eClass().getName());
		return jsonObject;
	}

	/**
	 * Serializes all feature of an {@link EObject} into the JSON object
	 * <code>jsonObj</code>. For references the method obtains proxies.
	 *
	 * @param eObject
	 *            The {@link EObject} for which to serialize all features
	 * @param jsonObj
	 *            The JSON object where to put all serialization results
	 * @throws JSONException
	 * @throws SpecmateException
	 */
	private void serializeFeatures(EObject eObject, JSONObject jsonObj) throws JSONException, SpecmateException {
		EClass eClass = eObject.eClass();
		for (EStructuralFeature feature : eClass.getEAllStructuralFeatures()) {
			Object value = eObject.eGet(feature, true);
			if (value != null) {
				String referenceName = feature.getName();
				if (feature instanceof EReference) {
					if (((EReference) feature).isContainment()) {
						if (config.serializeContainedElements(eObject)) {
							jsonObj.put(referenceName, serializeValue(value));
						}
					} else {
						jsonObj.put(referenceName, serializeProxy(value));
					}
				} else {
					jsonObj.put(referenceName, serializeValue(value));
				}
			}
		}
	}

}
