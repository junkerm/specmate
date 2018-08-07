package com.specmate.dbprovider.api.migration;

import com.specmate.common.SpecmateException;

public interface IObjectToSQLMapper {
	public void newObject(String tableName) throws SpecmateException;
}
