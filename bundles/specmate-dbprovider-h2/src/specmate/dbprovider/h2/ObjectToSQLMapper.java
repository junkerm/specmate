package specmate.dbprovider.h2;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import com.specmate.common.SpecmateException;
import com.specmate.dbprovider.api.migration.IObjectToSQLMapper;
import com.specmate.dbprovider.api.migration.SQLMapper;
import com.specmate.dbprovider.api.migration.SQLUtil;

import specmate.dbprovider.h2.config.H2ProviderConfig;

public class ObjectToSQLMapper extends SQLMapper implements IObjectToSQLMapper {

	public ObjectToSQLMapper(Connection connection, String packageName, String sourceVersion, String targetVersion) {
		super(connection, packageName, sourceVersion, targetVersion);
	}

	@Override
	public void newObject(String tableName) throws SpecmateException {
		String failmsg = "Migration: Could not add table " + tableName + ".";
		List<String> queries = new ArrayList<>();

		queries.add("CREATE TABLE " + tableName + "(" + "CDO_ID VARCHAR(255) NOT NULL, "
				+ "CDO_VERSION INTEGER NOT NULL, " + "CDO_BRANCH INTEGER NOT NULL, " + "CDO_CREATED BIGINT NOT NULL, "
				+ "CDO_REVISED BIGINT NOT NULL, " + "CDO_RESOURCE VARCHAR(255) NOT NULL, "
				+ "CDO_CONTAINER VARCHAR(255) NOT NULL, " + "CDO_FEATURE INTEGER NOT NULL)");

		queries.add("CREATE UNIQUE INDEX " + SQLUtil.createTimebasedIdentifier("PK", H2ProviderConfig.MAX_ID_LENGTH)
				+ " ON " + tableName + " (CDO_ID ASC, CDO_VERSION ASC, CDO_BRANCH ASC)");

		queries.add("CREATE INDEX " + SQLUtil.createTimebasedIdentifier("I", H2ProviderConfig.MAX_ID_LENGTH) + " ON "
				+ tableName + " (CDO_REVISED ASC)");

		queries.add("ALTER TABLE " + tableName + " ADD CONSTRAINT "
				+ SQLUtil.createTimebasedIdentifier("C", H2ProviderConfig.MAX_ID_LENGTH)
				+ " PRIMARY KEY (CDO_ID, CDO_VERSION, CDO_BRANCH)");

		SQLUtil.executeStatements(queries, connection, failmsg);
	}
}
