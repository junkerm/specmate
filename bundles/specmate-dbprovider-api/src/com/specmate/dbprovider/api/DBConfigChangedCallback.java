package com.specmate.dbprovider.api;

import com.specmate.common.exception.SpecmateException;

public interface DBConfigChangedCallback {
	public void configurationChanged() throws SpecmateException;
}
