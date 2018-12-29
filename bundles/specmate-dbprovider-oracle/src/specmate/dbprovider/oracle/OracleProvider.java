package specmate.dbprovider.oracle;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.emf.cdo.server.IStore;
import org.eclipse.emf.cdo.server.db.CDODBUtil;
import org.eclipse.emf.cdo.server.db.mapping.IMappingStrategy;
import org.eclipse.net4j.db.DBUtil;
import org.eclipse.net4j.db.IDBAdapter;
import org.eclipse.net4j.db.IDBConnectionProvider;
import org.eclipse.net4j.db.oracle.OracleAdapter;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Modified;

import com.specmate.common.exception.SpecmateException;
import com.specmate.common.exception.SpecmateInternalException;
import com.specmate.dbprovider.api.DBConfigChangedCallback;
import com.specmate.dbprovider.api.DBProviderBase;
import com.specmate.dbprovider.api.IDBProvider;
import com.specmate.dbprovider.api.migration.IAttributeToSQLMapper;
import com.specmate.dbprovider.api.migration.IObjectToSQLMapper;
import com.specmate.model.administration.ErrorCode;

import oracle.jdbc.pool.OracleDataSource;
import specmate.dbprovider.oracle.config.OracleProviderConfig;

@Component(service = IDBProvider.class, configurationPid = OracleProviderConfig.PID, configurationPolicy = ConfigurationPolicy.REQUIRE, property = {
		"service.ranking:Integer=1" })
public class OracleProvider extends DBProviderBase {

	private String username;
	private String password;

	@Activate
	public void activate(Map<String, Object> properties) throws SpecmateException {
		readConfig(properties);
	}

	@Modified
	public void modified(Map<String, Object> properties) throws SpecmateException {
		closeConnection();
		readConfig(properties);
		for (DBConfigChangedCallback cb : cbRegister) {
			cb.configurationChanged();
		}
	}

	@Deactivate
	public void deactivate() throws SpecmateException {
		closeConnection();
	}

	@Override
	public Connection getConnection() throws SpecmateException {
		if (this.connection == null) {
			initiateDBConnection();
		}

		return this.connection;
	}

	private void readConfig(Map<String, Object> properties) throws SpecmateInternalException {
		this.jdbcConnection = (String) properties.get(OracleProviderConfig.KEY_JDBC_CONNECTION);
		this.username = (String) properties.get(OracleProviderConfig.KEY_USERNAME);
		this.password = (String) properties.get(OracleProviderConfig.KEY_PASSWORD);

		String failmsg = " not defined in configuration.";

		if (!StringUtils.isNotEmpty(this.jdbcConnection)) {
			throw new SpecmateInternalException(ErrorCode.CONFIGURATION, "JDBC connection" + failmsg);
		}

		if (!StringUtils.isNotEmpty(this.username)) {
			throw new SpecmateInternalException(ErrorCode.CONFIGURATION, "Database user name" + failmsg);
		}

		if (!StringUtils.isNotEmpty(this.password)) {
			throw new SpecmateInternalException(ErrorCode.CONFIGURATION, "Database password" + failmsg);
		}
	}

	@Override
	public IStore createStore() throws SpecmateException {
		IStore store;
		try {
			DriverManager.registerDriver(new oracle.jdbc.OracleDriver());
			OracleDataSource odataSource = new OracleDataSource();
			odataSource.setURL(this.jdbcConnection);
			odataSource.setUser(this.username);
			odataSource.setPassword(this.password);
			IMappingStrategy omappingStrategy = CDODBUtil.createHorizontalMappingStrategy(true);
			IDBAdapter odbAdapter = new OracleAdapter();
			IDBConnectionProvider odbConnectionProvider = DBUtil.createConnectionProvider(odataSource);
			store = CDODBUtil.createStore(omappingStrategy, odbAdapter, odbConnectionProvider);
		} catch (SQLException e) {
			throw new SpecmateInternalException(ErrorCode.PERSISTENCY, "Could not create Oracle data store.", e);
		}

		return store;
	}

	@Override
	public boolean isVirginDB() throws SpecmateException {
		Connection connection = getConnection();
		try {
			PreparedStatement stmt = connection.prepareStatement("select * from CDO_PACKAGE_INFOS");
			stmt.execute();
		} catch (SQLException e) {
			return true;
		}

		return false;
	}

	@Override
	public IAttributeToSQLMapper getAttributeToSQLMapper(String packageName, String sourceVersion, String targetVersion)
			throws SpecmateException {
		return new AttributeToSQLMapper(connection, packageName, sourceVersion, targetVersion);
	}

	@Override
	public IObjectToSQLMapper getObjectToSQLMapper(String packageName, String sourceVersion, String targetVersion)
			throws SpecmateException {
		return new ObjectToSQLMapper(connection, packageName, sourceVersion, targetVersion);
	}

	private void initiateDBConnection() throws SpecmateException {
		try {
			DriverManager.registerDriver(new oracle.jdbc.OracleDriver());
		} catch (SQLException e) {
			throw new SpecmateInternalException(ErrorCode.PERSISTENCY, "Could not register Oracle driver.", e);
		}

		try {
			this.connection = DriverManager.getConnection(this.jdbcConnection, this.username, this.password);
		} catch (SQLException e) {
			throw new SpecmateInternalException(ErrorCode.PERSISTENCY,
					"Could not connect to the Oracle database using the connection: " + this.jdbcConnection + ".", e);
		}
	}
}
