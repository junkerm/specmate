package specmate.dbprovider.oracle;

import java.sql.Connection;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.specmate.common.SpecmateException;
import com.specmate.dbprovider.api.migration.IAttributeToSQLMapper;
import com.specmate.dbprovider.api.migration.IDataType;
import com.specmate.dbprovider.api.migration.SQLMapper;
import com.specmate.dbprovider.api.migration.SQLUtil;

public class AttributeToSQLMapper extends SQLMapper implements IAttributeToSQLMapper {

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

		String alterString = "ALTER TABLE " + objectName + " ADD " + attributeName + " VARCHAR(4000)";
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
	public void migrateNewReference(String objectName, String attributeName) throws SpecmateException {
		String failmsg = "Migration: Could not add column " + attributeName + " to table " + objectName + ".";
		String tableNameList = objectName + "_" + attributeName + "_LIST";
		List<String> queries = new ArrayList<>();

		queries.add("ALTER TABLE " + objectName + " ADD " + attributeName + " NUMBER");
		queries.add("CREATE TABLE " + tableNameList + " (" + "CDO_SOURCE NUMBER NOT NULL, "
				+ "CDO_VERSION NUMBER NOT NULL, " + "CDO_IDX NUMBER NOT NULL, " + "CDO_VALUE NUMBER)");
		queries.add("CREATE UNIQUE INDEX " + SQLUtil.createTimebasedIdentifier("PK") + " ON " + tableNameList
				+ " (CDO_SOURCE ASC, CDO_VERSION ASC, CDO_IDX ASC)");
		queries.add("ALTER TABLE " + tableNameList + " ADD CONSTRAINT " + SQLUtil.createTimebasedIdentifier("C")
				+ " PRIMARY KEY (CDO_SOURCE, CDO_VERSION, CDO_IDX)");

		SQLUtil.executeStatements(queries, connection, failmsg);
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
		throw new SpecmateException("Not yet supported for oracle DB");
	}
}
