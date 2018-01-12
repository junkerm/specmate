package com.specmate.persistency.event;

import java.util.Collections;

import org.apache.commons.lang3.StringUtils;
import org.osgi.service.event.Event;

public class ModelEvent extends Event {

	private String featureName;
	private EChangeKind type;
	protected Object newValue;
	private String url;
	private int index;
	private boolean containment;
	private Object id;

	public ModelEvent(Object id, String url, String featureName, boolean containment, EChangeKind add, Object newValue,
			int index) {
		super("com/specmate/model/notification" + (StringUtils.isEmpty(url) ? "" : "/" + url.replace(".", "_")),
				Collections.emptyMap());
		this.id = id;
		this.url = url;
		this.featureName = featureName;
		this.type = add;
		this.newValue = newValue;
		this.index = index;
		this.containment = containment;
	}

	public ModelEvent(Object id, String url, String featureName, boolean containment, EChangeKind type,
			Object newValue) {
		this(id, url, featureName, containment, type, newValue, 0);
	}

	public String getFeatureName() {
		return featureName;
	}

	public boolean isContainment() {
		return containment;
	}

	public Object getNewValue() {
		return newValue;
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

	public Object getId() {
		return this.id;
	}

}
