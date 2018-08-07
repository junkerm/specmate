package com.specmate.dbprovider.api.migration;

import java.util.Date;

import com.specmate.common.SpecmateException;

public interface IAttributeToSQLMapper {

	void migrateNewStringAttribute(String objectName, String attributeName, String defaultValue)
			throws SpecmateException;

	void migrateNewBooleanAttribute(String objectName, String attributeName, Boolean defaultValue)
			throws SpecmateException;

	void migrateNewIntegerAttribute(String objectName, String attributeName, Integer defaultValue)
			throws SpecmateException;

	void migrateNewDoubleAttribute(String objectName, String attributeName, Double defaultValue)
			throws SpecmateException;

	void migrateNewLongAttribute(String objectName, String attributeName, Long defaultValue) throws SpecmateException;

	void migrateNewDateAttribute(String objectName, String attributeName, Date defaultValue) throws SpecmateException;

	void migrateNewReference(String objectName, String attributeName) throws SpecmateException;

	void migrateRenameAttribute(String objectName, String oldAttributeName, String newAttributeName)
			throws SpecmateException;

	void migrateChangeType(String objectName, String attributeName, IDataType targetType) throws SpecmateException;

}