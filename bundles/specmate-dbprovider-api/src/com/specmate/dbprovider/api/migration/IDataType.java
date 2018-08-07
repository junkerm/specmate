package com.specmate.dbprovider.api.migration;

import com.specmate.common.SpecmateException;

public interface IDataType {

	boolean isConversionPossibleTo(IDataType target) throws SpecmateException;

	String getTypeName();

	String getTypeNameWithSize();

	void setSize(int size);

	int getSize();

	boolean hasSize();

	int ordinal();
}