package com.specmate.migration.h2;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import com.specmate.common.SpecmateException;

public enum EDataType {
	BYTE(DataTypeConstants.TINYINT, false),
	SHORT(DataTypeConstants.SMALLINT, false),
	INT(DataTypeConstants.INT, false),
	CHAR(DataTypeConstants.CHAR, true), 
	LONG(DataTypeConstants.LONG, false),
	FLOAT(DataTypeConstants.FLOAT, false),
	DOUBLE(DataTypeConstants.DOUBLE, false),
	BOOLEAN(DataTypeConstants.BOOLEAN, false),
	STRING(DataTypeConstants.STRING, true);
	
	private String typeName;
	private Set<String> aliases;
	private int size;
	private boolean hasSize;
	private static Boolean[][] possibleConversions = new Boolean[EDataType.values().length][EDataType.values().length];
	private static HashMap<String, EDataType> dataTypeLookup = new HashMap<>();
	
	EDataType(String[] typeNames, boolean hasSize) {
		this.hasSize = hasSize;
		this.size = -1;
		this.aliases = new HashSet<>();
		if (typeNames.length > 0) {
			typeName = typeNames[0];
		}
		for (int i = 1; i < typeNames.length; i++) {
			this.aliases.add(typeNames[i]);
		}
	}
	
	public boolean isConversionPossibleTo(EDataType target) throws SpecmateException {
		if (target.hasSize && target.size < 0) {
			throw new SpecmateException("Size for type " + getTypeName() + " not set.");
		}
		if (target.hasSize && this.size > target.size) {
			throw new SpecmateException("Target size (" + target.size + ") is smaller than source size (" + this.size + ").");
		}
		return possibleConversions[this.ordinal()][target.ordinal()];
	}
	
	public String getTypeName() {
		return typeName;
	}
	
	public String getTypeNameWithSize() {
		String t = this.typeName;
		if (hasSize) {
			t = t + "(" + size + ")";
		}
		return t;
	}
	
	public void setSize(int size) {
		this.size = size;
	}
	
	public static EDataType getFromTypeName(String typeName) {
		return dataTypeLookup.get(typeName);
	}
	
	private static void definePossibleConversion(EDataType from, EDataType to) {
		possibleConversions[from.ordinal()][to.ordinal()] = true;
	}
	
	static {
		int size = EDataType.values().length;
		for(int i = 0; i < size; i++) {
			for(int j = 0; j < size; j++) {
				possibleConversions[i][j] = false;
			}
		}
		
		/**
		 * The following widening type conversions (see 
		 * https://docs.oracle.com/javase/specs/jls/se8/html/jls-5.html#jls-5.1.2)
		 * are allowed:
		 * - byte to short, int, long, float, or double
		 * - short to int, long, float, or double
		 * - char to int, long, float, double, or string
		 * - int to long, float, or double
		 * - long to float or double
		 * - float to double
		 * 
		 * In addition, we convert boolean types (true, false) to string literals ("true", "false")
		 * and parse the following string literals (case insensitive) to the corresponding boolean types: 
		 * "t", "true", "f", "false"
		 */
		definePossibleConversion(EDataType.BYTE, EDataType.SHORT);
		definePossibleConversion(EDataType.BYTE, EDataType.INT);
		definePossibleConversion(EDataType.BYTE, EDataType.LONG);
		definePossibleConversion(EDataType.BYTE, EDataType.FLOAT);
		definePossibleConversion(EDataType.BYTE, EDataType.DOUBLE);
		definePossibleConversion(EDataType.SHORT, EDataType.INT);
		definePossibleConversion(EDataType.SHORT, EDataType.LONG);
		definePossibleConversion(EDataType.SHORT, EDataType.FLOAT);
		definePossibleConversion(EDataType.SHORT, EDataType.DOUBLE);
		definePossibleConversion(EDataType.CHAR, EDataType.INT);
		definePossibleConversion(EDataType.CHAR, EDataType.LONG);
		definePossibleConversion(EDataType.CHAR, EDataType.FLOAT);
		definePossibleConversion(EDataType.CHAR, EDataType.DOUBLE);
		definePossibleConversion(EDataType.CHAR, EDataType.STRING);
		definePossibleConversion(EDataType.INT, EDataType.LONG);
		definePossibleConversion(EDataType.INT, EDataType.FLOAT);
		definePossibleConversion(EDataType.INT, EDataType.DOUBLE);
		definePossibleConversion(EDataType.LONG, EDataType.FLOAT);
		definePossibleConversion(EDataType.LONG, EDataType.DOUBLE);
		definePossibleConversion(EDataType.FLOAT, EDataType.DOUBLE);
		definePossibleConversion(EDataType.BOOLEAN, EDataType.STRING);
		definePossibleConversion(EDataType.STRING, EDataType.BOOLEAN);
		
		Arrays.asList(DataTypeConstants.TINYINT).forEach(typeName -> {dataTypeLookup.put(typeName, EDataType.BYTE);});
		Arrays.asList(DataTypeConstants.SMALLINT).forEach(typeName -> {dataTypeLookup.put(typeName, EDataType.SHORT);});
		Arrays.asList(DataTypeConstants.INT).forEach(typeName -> {dataTypeLookup.put(typeName, EDataType.INT);});
		Arrays.asList(DataTypeConstants.CHAR).forEach(typeName -> {dataTypeLookup.put(typeName, EDataType.CHAR);});
		Arrays.asList(DataTypeConstants.LONG).forEach(typeName -> {dataTypeLookup.put(typeName, EDataType.LONG);});
		Arrays.asList(DataTypeConstants.FLOAT).forEach(typeName -> {dataTypeLookup.put(typeName, EDataType.FLOAT);});
		Arrays.asList(DataTypeConstants.DOUBLE).forEach(typeName -> {dataTypeLookup.put(typeName, EDataType.DOUBLE);});
		Arrays.asList(DataTypeConstants.BOOLEAN).forEach(typeName -> {dataTypeLookup.put(typeName, EDataType.BOOLEAN);});
		Arrays.asList(DataTypeConstants.STRING).forEach(typeName -> {dataTypeLookup.put(typeName, EDataType.STRING);});
	}
}
