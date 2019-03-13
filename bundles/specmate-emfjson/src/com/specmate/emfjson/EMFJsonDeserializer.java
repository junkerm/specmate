package com.specmate.emfjson;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.specmate.common.exception.SpecmateException;
import com.specmate.common.exception.SpecmateInternalException;
import com.specmate.model.administration.ErrorCode;
import com.specmate.urihandler.IObjectResolver;

/**
 * Deserializes JSON objects to EObjects
 *
 * @author junkerm
 *
 */
public class EMFJsonDeserializer {

	/** Resolver to obtain reference EObjects from the resource based on an uri */
	private IObjectResolver resolver;

	/** The underlying resource from where to retrieve referenced objects */
	private Resource resource;

	/**
	 *
	 * @param resolver
	 *            The object resolver to be used
	 * @param resource
	 *            The resource from where to retrieve referenced objects
	 */
	public EMFJsonDeserializer(IObjectResolver resolver, Resource resource) {
		this.resolver = resolver;
		this.resource = resource;
	}

	/**
	 * Deserializes an Eobject from a JSON object
	 *
	 * @param jsonObj
	 *            The JSON object to be deserialized
	 * @return The EObject that is represented by the json object
	 * @throws SpecmateException
	 */
	public EObject deserializeEObject(JSONObject jsonObj) throws SpecmateException {
		if (jsonObj.has(EMFJsonSerializer.KEY_PROXY) && jsonObj.getBoolean(EMFJsonSerializer.KEY_PROXY)) {
			String uriFragment = jsonObj.getString(EMFJsonSerializer.KEY_URI);
			return retrieveFromResource(uriFragment);
		} else {
			return buildFromJson(jsonObj);
		}
	}

	/**
	 * Retrieves an EObject directly from the resource based on an URI
	 *
	 * @param uri
	 *            The URI used to adress the object
	 * @return An EObject that has the given URI
	 * @throws SpecmateException
	 */
	private EObject retrieveFromResource(String uri) throws SpecmateException {
		EObject retrieved = resolver.getObject(uri, resource);
		if (retrieved == null) {
			throw new SpecmateInternalException(ErrorCode.SERALIZATION, "Json contained " + EMFJsonSerializer.KEY_URI
					+ " entry but no object with this fragment url could be found.");
		} else {
			return retrieved;
		}
	}

	/**
	 * Constructs an EObject recursively from the given JSON object
	 *
	 * @param jsonObj
	 *            The JSON object from which to construct the EObject
	 * @return An EObject representing the jsonObject
	 * @throws SpecmateException
	 */
	private EObject buildFromJson(JSONObject jsonObj) throws SpecmateException {
		String nsUri = jsonObj.optString(EMFJsonSerializer.KEY_NSURI);
		String className = jsonObj.optString(EMFJsonSerializer.KEY_ECLASS);
		if (StringUtils.isEmpty(nsUri) || StringUtils.isEmpty(className)) {
			throw new SpecmateInternalException(ErrorCode.SERALIZATION, "No uri or eclass specified.");
		}

		EPackage ePackage = EPackage.Registry.INSTANCE.getEPackage(nsUri);
		if (ePackage == null) {
			throw new SpecmateInternalException(ErrorCode.SERALIZATION, "No package registered for " + nsUri + ".");
		}
		EClassifier classifier = ePackage.getEClassifier(className);
		if (classifier == null || !(classifier instanceof EClass)) {
			throw new SpecmateInternalException(ErrorCode.SERALIZATION,
					"No class with name " + className + " in package.");
		}

		EClass clazz = (EClass) classifier;

		EObject eObj = ePackage.getEFactoryInstance().create(clazz);
		deserializeFeatures(eObj, jsonObj);

		return eObj;
	}

	/**
	 * Deserializes the values of all features of an EObject from the given
	 * JSONObject
	 *
	 * @param eObject
	 *            The EObject for which the feature values should be dserialized
	 * @param jsonObj
	 *            The JSON object from which to serialize the values
	 * @throws SpecmateException
	 */
	private void deserializeFeatures(EObject eObject, JSONObject jsonObj) throws SpecmateException {
		String featureName = null;
		for (EStructuralFeature feature : eObject.eClass().getEAllStructuralFeatures()) {
			featureName = feature.getName();
			if (jsonObj.has(featureName)) {
				Object value;
				try {
					value = jsonObj.get(featureName);
				} catch (JSONException e) {
					throw new SpecmateInternalException(ErrorCode.SERALIZATION, e);
				}
				eObject.eSet(feature, deserializeFeature(feature, value));
			}
		}
	}

	/**
	 * Deserialized the value of a single feature of an EObject from a JSON entity.
	 * This entity is either a {@link JSONArray} or a {@link JSONObject} depending
	 * on the type of the feature.
	 *
	 * @param feature
	 *            The which for the value should be deserialized
	 * @param json
	 *            The json entity ({@link JSONArray} or {@link JSONObject}) from
	 *            which to deserialize
	 * @return The EObject or the EList obtained from the deserialization
	 * @throws SpecmateException
	 */
	private Object deserializeFeature(EStructuralFeature feature, Object json) throws SpecmateException {
		EClassifier type = feature.getEType();
		if (!feature.isMany()) {
			return deserializeValue(json, type);
		} else {
			JSONArray array = (JSONArray) json;
			BasicEList<Object> list = new BasicEList<Object>();
			for (int i = 0; i < array.length(); i++) {
				try {
					list.add(deserializeValue(array.get(i), type));
				} catch (JSONException e) {
					throw new SpecmateInternalException(ErrorCode.SERALIZATION, e);
				}
			}
			return list;
		}
	}

	/**
	 * Deserializs a JSON value, depending on the expected type.
	 *
	 * @param value
	 *            The JSON value to deserialize
	 * @param type
	 *            The expected type
	 * @return The deserialized value, either an EObject or a primitive type such as
	 *         String, etc.
	 * @throws SpecmateException
	 */
	private Object deserializeValue(Object value, EClassifier type) throws SpecmateException {
		if (type instanceof EDataType) {
			String strValue = value.toString();
			if (type.getName().equalsIgnoreCase("EBoolean") && value.toString().equals("")) {
				strValue = "false";
			}
			return EcoreUtil.createFromString((EDataType) type, strValue);
		} else if (type instanceof EClass) {
			return deserializeEObject((JSONObject) value);
		} else {
			throw new SpecmateInternalException(ErrorCode.SERALIZATION, type + " not supported for deserialization.");
		}
	}

}
