package com.specmate.dbprovider.api.migration;

import com.specmate.common.SpecmateException;

/**
 * This interface defines methods that help to represent the permissible data
 * type conversions.
 */
public interface IDataType {

	/**
	 * @param target
	 *            the target data type
	 * @return <code>true</code> if this data type can be converted into the target
	 *         data type, <code>false</code> otherwise
	 * @throws SpecmateException
	 */
	boolean isConversionPossibleTo(IDataType target) throws SpecmateException;

	/**
	 * @return the name of the data type, as it is defined in the SQL dialect
	 */
	String getTypeName();

	/**
	 * @return the name of the data type, as it is defined in the SQL dialect,
	 *         together with the defined size
	 */
	String getTypeNameWithSize();

	/**
	 * Some data types can be defined with a maximum size (e.g. strings).
	 *
	 * @param size
	 *            the maximum size allowed for this data type
	 */
	void setSize(int size);

	/**
	 * @return the maximum size defined for this data type (-1 if no maximum is
	 *         defined)
	 */
	int getSize();

	/**
	 * @return <code>true</code> if a maximum size is defined, <code>false</code>
	 *         otherwise
	 */
	boolean hasSize();

	/**
	 * @return the ordinal of this enumeration constant
	 */
	int ordinal();
}