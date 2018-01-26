package com.specmate.persistency.event;

import java.util.Collections;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.osgi.service.event.Event;

public class ModelEvent extends Event {

	private Map<EStructuralFeature, Object> featureMap;
	private EChangeKind type;
	private String url;
	private int index;

	private String id;
	private String className;

	public ModelEvent(String id, String className, String url, Map<EStructuralFeature, Object> featureMap,
			EChangeKind kind, int index) {
		super("com/specmate/model/notification" + (StringUtils.isEmpty(url) ? "" : "/" + url.replace(".", "_")),
				Collections.emptyMap());
		this.id = id;
		this.url = url;
		this.featureMap = featureMap;
		this.type = kind;
		this.index = index;
		this.className = className;
	}

	public ModelEvent(String id, String className, String url, Map<EStructuralFeature, Object> featureMap,
			EChangeKind kind) {
		this(id, className, url, featureMap, kind, 0);
	}

	public ModelEvent(String id, String className, String url, EStructuralFeature feature, EChangeKind type,
			Object newValue, int index) {
		this(id, className, url, Collections.singletonMap(feature, newValue), type, index);
	}

	public ModelEvent(String id, String className, String url, EStructuralFeature feature, EChangeKind type,
			Object newValue) {
		this(id, className, url, Collections.singletonMap(feature, newValue), type, 0);
	}

	public String getClassName() {
		return className;
	}

	public Map<EStructuralFeature, Object> getFeatureMap() {
		return featureMap;
	}

	public String getUrl() {
		return this.url;
	}

	public EChangeKind getType() {
		return this.type;
	}

	public int getIndex() {
		return this.index;
	}

	public String getId() {
		return this.id;
	}

}
