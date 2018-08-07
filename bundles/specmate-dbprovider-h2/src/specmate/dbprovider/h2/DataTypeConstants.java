package specmate.dbprovider.h2;

public class DataTypeConstants {
	public static final String[] TINYINT = new String[] {"TINYINT"};
	public static final String[] SMALLINT = new String[] {"SMALLINT", "INT2", "YEAR"};
	public static final String[] INT = new String[] {"INT", "INTEGER", "MEDIUMINT", "INT4", "SIGNED"};
	public static final String[] CHAR = new String[] {"CHAR", "CHARACTER", "NCHAR"};
	public static final String[] LONG = new String[] {"BIGINT", "INT8"};
	public static final String[] FLOAT = new String[] {"REAL", "FLOAT4"};
	public static final String[] DOUBLE = new String[] {"DOUBLE", "DOUBLE PRECISION", "FLOAT", "FLOAT8"};
	public static final String[] BOOLEAN = new String[] {"BOOLEAN", "BIT", "BOOL"};
	public static final String[] STRING = new String[] {"VARCHAR", "LONGVARCHAR", "VARCHAR2", "NVARCHAR", "NVARCHAR2", "VARCHAR_CASESENSITIVE"};
}
