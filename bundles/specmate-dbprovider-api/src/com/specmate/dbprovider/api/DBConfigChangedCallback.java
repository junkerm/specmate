package com.specmate.dbprovider.api;

import com.specmate.common.SpecmateException;

public interface DBConfigChangedCallback {
	public void configurationChanged() throws SpecmateException;
}
