package specmate.dbprovider.h2;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import com.specmate.common.SpecmateException;
import com.specmate.dbprovider.api.migration.IDataType;

public enum H2DataType implements IDataType {
	BYTE(DataTypeConstants.TINYINT, false), SHORT(DataTypeConstants.SMALLINT, false), INT(DataTypeConstants.INT,
			false), CHAR(DataTypeConstants.CHAR, true), LONG(DataTypeConstants.LONG,
					false), FLOAT(DataTypeConstants.FLOAT, false), DOUBLE(DataTypeConstants.DOUBLE,
							false), BOOLEAN(DataTypeConstants.BOOLEAN, false), STRING(DataTypeConstants.STRING, true);

	private String typeName;
	private Set<String> aliases;
	private int size;
	private boolean hasSize;
	private static Boolean[][] possibleConversions = new Boolean[H2DataType.values().length][H2DataType
			.values().length];
	private static HashMap<String, H2DataType> dataTypeLookup = new HashMap<>();

	H2DataType(String[] typeNames, boolean hasSize) {
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

	/*
	 * (non-Javadoc)
	 *
	 * @see com.specmate.migration.h2.IDataType#isConversionPossibleTo(com.specmate.
	 * migration.h2.H2DataType)
	 */
	@Override
	public boolean isConversionPossibleTo(IDataType target) throws SpecmateException {
		if (target.hasSize() && target.getSize() < 0) {
			throw new SpecmateException("Size for type " + getTypeName() + " not set.");
		}
		if (target.hasSize() && this.size > target.getSize()) {
			throw new SpecmateException(
					"Target size (" + target.getSize() + ") is smaller than source size (" + this.size + ").");
		}
		return possibleConversions[this.ordinal()][target.ordinal()];
	}

	@Override
	public String getTypeName() {
		return typeName;
	}

	@Override
	public String getTypeNameWithSize() {
		String t = this.typeName;
		if (hasSize) {
			t = t + "(" + size + ")";
		}
		return t;
	}

	@Override
	public void setSize(int size) {
		this.size = size;
	}

	@Override
	public int getSize() {
		return this.size;
	}

	@Override
	public boolean hasSize() {
		return this.hasSize;
	}

	public static IDataType getFromTypeName(String typeName) {
		return dataTypeLookup.get(typeName);
	}

	private static void definePossibleConversion(H2DataType from, H2DataType to) {
		possibleConversions[from.ordinal()][to.ordinal()] = true;
	}

	static {
		int size = H2DataType.values().length;
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				possibleConversions[i][j] = false;
			}
		}

		/**
		 * The following widening type conversions (see
		 * https://docs.oracle.com/javase/specs/jls/se8/html/jls-5.html#jls-5.1.2) are
		 * allowed: - byte to short, int, long, float, or double - short to int, long,
		 * float, or double - char to int, long, float, double, or string - int to long,
		 * float, or double - long to float or double - float to double
		 *
		 * In addition, we convert boolean types (true, false) to string literals
		 * ("true", "false") and parse the following string literals (case insensitive)
		 * to the corresponding boolean types: "t", "true", "f", "false"
		 */
		definePossibleConversion(H2DataType.BYTE, H2DataType.SHORT);
		definePossibleConversion(H2DataType.BYTE, H2DataType.INT);
		definePossibleConversion(H2DataType.BYTE, H2DataType.LONG);
		definePossibleConversion(H2DataType.BYTE, H2DataType.FLOAT);
		definePossibleConversion(H2DataType.BYTE, H2DataType.DOUBLE);
		definePossibleConversion(H2DataType.SHORT, H2DataType.INT);
		definePossibleConversion(H2DataType.SHORT, H2DataType.LONG);
		definePossibleConversion(H2DataType.SHORT, H2DataType.FLOAT);
		definePossibleConversion(H2DataType.SHORT, H2DataType.DOUBLE);
		definePossibleConversion(H2DataType.CHAR, H2DataType.INT);
		definePossibleConversion(H2DataType.CHAR, H2DataType.LONG);
		definePossibleConversion(H2DataType.CHAR, H2DataType.FLOAT);
		definePossibleConversion(H2DataType.CHAR, H2DataType.DOUBLE);
		definePossibleConversion(H2DataType.CHAR, H2DataType.STRING);
		definePossibleConversion(H2DataType.INT, H2DataType.LONG);
		definePossibleConversion(H2DataType.INT, H2DataType.FLOAT);
		definePossibleConversion(H2DataType.INT, H2DataType.DOUBLE);
		definePossibleConversion(H2DataType.LONG, H2DataType.FLOAT);
		definePossibleConversion(H2DataType.LONG, H2DataType.DOUBLE);
		definePossibleConversion(H2DataType.FLOAT, H2DataType.DOUBLE);
		definePossibleConversion(H2DataType.BOOLEAN, H2DataType.STRING);
		definePossibleConversion(H2DataType.STRING, H2DataType.BOOLEAN);

		Arrays.asList(DataTypeConstants.TINYINT).forEach(typeName -> {
			dataTypeLookup.put(typeName, H2DataType.BYTE);
		});
		Arrays.asList(DataTypeConstants.SMALLINT).forEach(typeName -> {
			dataTypeLookup.put(typeName, H2DataType.SHORT);
		});
		Arrays.asList(DataTypeConstants.INT).forEach(typeName -> {
			dataTypeLookup.put(typeName, H2DataType.INT);
		});
		Arrays.asList(DataTypeConstants.CHAR).forEach(typeName -> {
			dataTypeLookup.put(typeName, H2DataType.CHAR);
		});
		Arrays.asList(DataTypeConstants.LONG).forEach(typeName -> {
			dataTypeLookup.put(typeName, H2DataType.LONG);
		});
		Arrays.asList(DataTypeConstants.FLOAT).forEach(typeName -> {
			dataTypeLookup.put(typeName, H2DataType.FLOAT);
		});
		Arrays.asList(DataTypeConstants.DOUBLE).forEach(typeName -> {
			dataTypeLookup.put(typeName, H2DataType.DOUBLE);
		});
		Arrays.asList(DataTypeConstants.BOOLEAN).forEach(typeName -> {
			dataTypeLookup.put(typeName, H2DataType.BOOLEAN);
		});
		Arrays.asList(DataTypeConstants.STRING).forEach(typeName -> {
			dataTypeLookup.put(typeName, H2DataType.STRING);
		});
	}
}
