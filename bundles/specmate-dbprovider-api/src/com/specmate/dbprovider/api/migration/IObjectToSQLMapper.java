package com.specmate.dbprovider.api.migration;

import com.specmate.common.SpecmateException;

/**
 * This interface defines operations that reflect creating a new object in an
 * EMF Model. Implementations for specific database providers determine how
 * these changes map to particular SQL dialects and features.
 */
public interface IObjectToSQLMapper {

	/**
	 * Create a new Object
	 * 
	 * @param objectName
	 *            the name of the new object
	 * @throws SpecmateException
	 */
	public void newObject(String objectName) throws SpecmateException;
}
