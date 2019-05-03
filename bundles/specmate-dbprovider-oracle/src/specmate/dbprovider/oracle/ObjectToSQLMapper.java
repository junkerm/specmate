package specmate.dbprovider.oracle;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import com.specmate.common.exception.SpecmateException;
import com.specmate.dbprovider.api.migration.IObjectToSQLMapper;
import com.specmate.dbprovider.api.migration.SQLMapper;
import com.specmate.dbprovider.api.migration.SQLUtil;

import specmate.dbprovider.oracle.config.OracleProviderConfig;

public class ObjectToSQLMapper extends SQLMapper implements IObjectToSQLMapper {

	public ObjectToSQLMapper(Connection connection, String packageName, String sourceVersion, String targetVersion) {
		super(connection, packageName, sourceVersion, targetVersion);
	}

	@Override
	public void newObject(String tableName) throws SpecmateException {
		String failmsg = "Migration: Could not add table " + tableName + ".";
		List<String> queries = new ArrayList<>();

		queries.add("CREATE TABLE " + tableName + "(" + "CDO_ID VARCHAR2(255) NOT NULL, "
				+ "CDO_VERSION NUMBER NOT NULL, " + "CDO_BRANCH NUMBER NOT NULL, " + "CDO_CREATED NUMBER NOT NULL, "
				+ "CDO_REVISED NUMBER NOT NULL, " + "CDO_RESOURCE VARCHAR2(255) NOT NULL, "
				+ "CDO_CONTAINER VARCHAR2(255) NOT NULL, " + "CDO_FEATURE NUMBER NOT NULL)");
		queries.add("CREATE UNIQUE INDEX " + SQLUtil.createTimebasedIdentifier("PK", OracleProviderConfig.MAX_ID_LENGTH)
				+ " ON " + tableName + " (CDO_ID ASC, CDO_VERSION ASC, CDO_BRANCH ASC)");
		queries.add("CREATE INDEX " + SQLUtil.createTimebasedIdentifier("I", OracleProviderConfig.MAX_ID_LENGTH)
				+ " ON " + tableName + " (CDO_REVISED ASC)");
		queries.add("ALTER TABLE " + tableName + " ADD CONSTRAINT "
				+ SQLUtil.createTimebasedIdentifier("C", OracleProviderConfig.MAX_ID_LENGTH)
				+ " PRIMARY KEY (CDO_ID, CDO_VERSION, CDO_BRANCH)");
		SQLUtil.executeStatements(queries, connection, failmsg);
	}

}
