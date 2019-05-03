package specmate.dbprovider.oracle;

import java.sql.Connection;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.specmate.common.exception.SpecmateException;
import com.specmate.common.exception.SpecmateInternalException;
import com.specmate.dbprovider.api.migration.IAttributeToSQLMapper;
import com.specmate.dbprovider.api.migration.IDataType;
import com.specmate.dbprovider.api.migration.SQLMapper;
import com.specmate.dbprovider.api.migration.SQLUtil;
import com.specmate.model.administration.ErrorCode;

import specmate.dbprovider.oracle.config.OracleProviderConfig;

public class AttributeToSQLMapper extends SQLMapper implements IAttributeToSQLMapper {

	private static final int ORACLE_MAX_TABLE_NAME_LENGTH = 30;

	public AttributeToSQLMapper(Connection connection, String packageName, String sourceVersion, String targetVersion) {
		super(connection, packageName, sourceVersion, targetVersion);
	}

	@Override
	public void migrateNewStringAttribute(String objectName, String attributeName, String defaultValue)
			throws SpecmateException {

		// TODO I'm not sure if CDO uses VARCHAR or CLOB [1] for strings. 4000 is the
		// maximum number of characters supported by oracle. I think that might not be
		// enough, e.g. for requirements texts.
		// [1]
		// https://docs.oracle.com/cd/B28359_01/server.111/b28318/datatype.htm#CNCPT1843

		String alterString = "ALTER TABLE " + objectName + " ADD " + attributeName + " VARCHAR2(4000)";
		if (hasDefault(defaultValue) && defaultValue.length() > 0) {
			alterString += " DEFAULT '" + defaultValue + "'";
		}

		executeChange(alterString, objectName, attributeName, hasDefault(defaultValue));
	}

	@Override
	public void migrateNewBooleanAttribute(String objectName, String attributeName, Boolean defaultValue)
			throws SpecmateException {

		String alterString = "ALTER TABLE " + objectName + " ADD " + attributeName + " NUMBER";

		if (hasDefault(defaultValue)) {
			alterString += " DEFAULT " + (defaultValue == true ? 1 : 0);
		}

		executeChange(alterString, objectName, attributeName, hasDefault(defaultValue));
	}

	@Override
	public void migrateNewIntegerAttribute(String objectName, String attributeName, Integer defaultValue)
			throws SpecmateException {

		String alterString = "ALTER TABLE " + objectName + " ADD " + attributeName + " NUMBER";

		if (hasDefault(defaultValue)) {
			alterString += " DEFAULT " + defaultValue.intValue();
		}

		executeChange(alterString, objectName, attributeName, hasDefault(defaultValue));
	}

	@Override
	public void migrateNewDoubleAttribute(String objectName, String attributeName, Double defaultValue)
			throws SpecmateException {

		String alterString = "ALTER TABLE " + objectName + " ADD " + attributeName + " NUMBER";

		if (hasDefault(defaultValue)) {
			alterString += " DEFAULT " + defaultValue;
		}

		executeChange(alterString, objectName, attributeName, hasDefault(defaultValue));
	}

	@Override
	public void migrateNewLongAttribute(String objectName, String attributeName, Long defaultValue)
			throws SpecmateException {

		String alterString = "ALTER TABLE " + objectName + " ADD " + attributeName + " NUMBER";

		if (hasDefault(defaultValue)) {
			alterString += " DEFAULT " + defaultValue;
		}

		executeChange(alterString, objectName, attributeName, hasDefault(defaultValue));
	}

	@Override
	public void migrateNewDateAttribute(String objectName, String attributeName, Date defaultValue)
			throws SpecmateException {

		String alterString = "ALTER TABLE " + objectName + " ADD " + attributeName + " DATE";
		if (hasDefault(defaultValue)) {
			DateFormat df = new SimpleDateFormat("dd.MM.yy");
			alterString += " DEFAULT '" + df.format(defaultValue) + "'";
		}

		executeChange(alterString, objectName, attributeName, hasDefault(defaultValue));
	}

	@Override
	public void migrateNewObjectReference(String objectName, String attributeName) throws SpecmateException {
		migrateNewReference(objectName, attributeName, "NUMBER");
	}

	@Override
	public void migrateNewStringReference(String objectName, String attributeName) throws SpecmateException {
		migrateNewReference(objectName, attributeName, "VARCHAR2(4000)");
	}

	private void migrateNewReference(String objectName, String attributeName, String type) throws SpecmateException {
		String failmsg = "Migration: Could not add column " + attributeName + " to table " + objectName + ".";
		String tableNameList = getListTableName(objectName, attributeName);
		List<String> queries = new ArrayList<>();

		queries.add("ALTER TABLE " + objectName + " ADD " + attributeName + " NUMBER");
		queries.add("CREATE TABLE " + tableNameList + " (" + "CDO_SOURCE VARCHAR2(255) NOT NULL, "
				+ "CDO_BRANCH NUMBER NOT NULL, " + "CDO_VERSION NUMBER NOT NULL, " + "CDO_IDX NUMBER NOT NULL, "
				+ "CDO_VALUE " + type + ")");
		queries.add("CREATE UNIQUE INDEX " + SQLUtil.createTimebasedIdentifier("PK", OracleProviderConfig.MAX_ID_LENGTH)
				+ " ON " + tableNameList + " (CDO_SOURCE ASC, CDO_BRANCH ASC, CDO_VERSION ASC, CDO_IDX ASC)");
		queries.add("ALTER TABLE " + tableNameList + " ADD CONSTRAINT "
				+ SQLUtil.createTimebasedIdentifier("C", OracleProviderConfig.MAX_ID_LENGTH)
				+ " PRIMARY KEY (CDO_SOURCE, CDO_BRANCH, CDO_VERSION, CDO_IDX)");
		queries.add(insertExternalAttributeReference(objectName, attributeName));
		SQLUtil.executeStatements(queries, connection, failmsg);
	}

	private String getListTableName(String objectName, String attributeName) throws SpecmateException {
		String firstShot = objectName + "_" + attributeName + "_LIST";
		if (firstShot.length() <= ORACLE_MAX_TABLE_NAME_LENGTH) {
			return firstShot;
		}
		int id = Math.abs(getLatestId());
		id++;
		String idStr = Integer.toString(id);

		String suffix = "_FLS" + idStr;
		if (suffix.length() > attributeName.length()) {
			throw new SpecmateInternalException(ErrorCode.MIGRATION,
					"Could not shorten list table name for attribute " + attributeName + ".");
		}
		String tableName = firstShot.substring(0, ORACLE_MAX_TABLE_NAME_LENGTH - suffix.length()) + suffix;
		return tableName;
	}

	@Override
	public void migrateRenameAttribute(String objectName, String oldAttributeName, String newAttributeName)
			throws SpecmateException {

		String failmsg = "Migration: Could not rename column " + oldAttributeName + " in table " + objectName + ".";
		List<String> queries = new ArrayList<>();
		queries.add("ALTER TABLE " + objectName + " RENAME COLUMN " + oldAttributeName + " TO " + newAttributeName);
		queries.add(renameExternalReference(objectName, oldAttributeName, newAttributeName));
		SQLUtil.executeStatements(queries, connection, failmsg);
	}

	@Override
	public void migrateChangeType(String objectName, String attributeName, IDataType targetType)
			throws SpecmateException {
		throw new SpecmateInternalException(ErrorCode.MIGRATION, "Not yet supported for oracle DB.");
	}
}
