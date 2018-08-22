package com.specmate.dbprovider.api.migration;

import java.util.Date;

import com.specmate.common.SpecmateException;

/**
 * This interface defines operations that reflect changes of object attributes
 * in an EMF Model. Implementations for specific database providers determine
 * how these changes map to particular SQL dialects and features.
 */
public interface IAttributeToSQLMapper {

	/**
	 * Creates a new String attribute.
	 *
	 * @param objectName
	 *            the name of the object where the attribute is added
	 * @param attributeName
	 *            the name of the attribute that is added
	 * @param defaultValue
	 *            the value that is stored in the database if the value in the
	 *            object is not defined
	 * @throws SpecmateException
	 */
	void migrateNewStringAttribute(String objectName, String attributeName, String defaultValue)
			throws SpecmateException;

	/**
	 * Creates a new Boolean attribute.
	 *
	 * @param objectName
	 *            the name of the object where the attribute is added
	 * @param attributeName
	 *            the name of the attribute that is added
	 * @param defaultValue
	 *            the value that is stored in the database if the value in the
	 *            object is not defined
	 * @throws SpecmateException
	 */
	void migrateNewBooleanAttribute(String objectName, String attributeName, Boolean defaultValue)
			throws SpecmateException;

	/**
	 * Creates a new Integer attribute.
	 *
	 * @param objectName
	 *            the name of the object where the attribute is added
	 * @param attributeName
	 *            the name of the attribute that is added
	 * @param defaultValue
	 *            the value that is stored in the database if the value in the
	 *            object is not defined
	 * @throws SpecmateException
	 */
	void migrateNewIntegerAttribute(String objectName, String attributeName, Integer defaultValue)
			throws SpecmateException;

	/**
	 * Creates a new Double attribute.
	 *
	 * @param objectName
	 *            the name of the object where the attribute is added
	 * @param attributeName
	 *            the name of the attribute that is added
	 * @param defaultValue
	 *            the value that is stored in the database if the value in the
	 *            object is not defined
	 * @throws SpecmateException
	 */
	void migrateNewDoubleAttribute(String objectName, String attributeName, Double defaultValue)
			throws SpecmateException;

	/**
	 * Creates a new Long attribute.
	 *
	 * @param objectName
	 *            the name of the object where the attribute is added
	 * @param attributeName
	 *            the name of the attribute that is added
	 * @param defaultValue
	 *            the value that is stored in the database if the value in the
	 *            object is not defined
	 * @throws SpecmateException
	 */
	void migrateNewLongAttribute(String objectName, String attributeName, Long defaultValue) throws SpecmateException;

	/**
	 * Creates a new Date attribute.
	 *
	 * @param objectName
	 *            the name of the object where the attribute is added
	 * @param attributeName
	 *            the name of the attribute that is added
	 * @param defaultValue
	 *            the value that is stored in the database if the value in the
	 *            object is not defined
	 * @throws SpecmateException
	 */
	void migrateNewDateAttribute(String objectName, String attributeName, Date defaultValue) throws SpecmateException;

	/**
	 * Creates an attribute that represents a reference to another object.
	 *
	 * @param objectName
	 *            the name of the object where the reference is added
	 * @param attributeName
	 *            the name of the reference attribute that is added
	 * @throws SpecmateException
	 */
	void migrateNewReference(String objectName, String attributeName) throws SpecmateException;

	/**
	 * Renames an attribute.
	 *
	 * @param objectName
	 *            the name of the object where the attribute is added
	 * @param oldAttributeName
	 *            the old attribute name
	 * @param newAttributeName
	 *            the new attribute name
	 * @throws SpecmateException
	 */
	void migrateRenameAttribute(String objectName, String oldAttributeName, String newAttributeName)
			throws SpecmateException;

	/**
	 * Changes the data type of an attribute if a conversion is allowed.
	 *
	 * @param objectName
	 *            the name of the object where the attribute is changed
	 * @param attributeName
	 *            the name of the attribute whose type is changed
	 * @param targetType
	 *            the target data type
	 * @throws SpecmateException
	 */
	void migrateChangeType(String objectName, String attributeName, IDataType targetType) throws SpecmateException;

}