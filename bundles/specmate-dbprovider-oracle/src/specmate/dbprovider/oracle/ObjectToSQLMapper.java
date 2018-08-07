package specmate.dbprovider.oracle;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import com.specmate.common.SpecmateException;
import com.specmate.dbprovider.api.migration.IObjectToSQLMapper;
import com.specmate.dbprovider.api.migration.SQLMapper;
import com.specmate.dbprovider.api.migration.SQLUtil;

public class ObjectToSQLMapper extends SQLMapper implements IObjectToSQLMapper {

	public ObjectToSQLMapper(Connection connection, String packageName, String sourceVersion, String targetVersion) {
		super(connection, packageName, sourceVersion, targetVersion);
	}

	@Override
	public void newObject(String tableName) throws SpecmateException {
		String failmsg = "Migration: Could not add table " + tableName + ".";
		List<String> queries = new ArrayList<>();

		queries.add("CREATE TABLE " + tableName + "(" + "CDO_ID NUMBER NOT NULL, " + "CDO_VERSION NUMBER NOT NULL, "
				+ "CDO_CREATED NUMBER NOT NULL, " + "CDO_REVISED NUMBER NOT NULL, " + "CDO_RESOURCE NUMBER NOT NULL, "
				+ "CDO_CONTAINER NUMBER NOT NULL, " + "CDO_FEATURE NUMBER NOT NULL)");
		queries.add("CREATE UNIQUE INDEX " + SQLUtil.createTimebasedIdentifier("PK") + " ON " + tableName
				+ " (CDO_ID ASC, CDO_VERSION ASC)");
		queries.add(
				"CREATE INDEX " + SQLUtil.createTimebasedIdentifier("I") + " ON " + tableName + " (CDO_REVISED ASC)");
		queries.add("ALTER TABLE " + tableName + " ADD CONSTRAINT " + SQLUtil.createTimebasedIdentifier("C")
				+ " PRIMARY KEY (CDO_ID, CDO_VERSION)");
		queries.add(insertExternalObjectReference(tableName));
		SQLUtil.executeStatements(queries, connection, failmsg);
	}

}
