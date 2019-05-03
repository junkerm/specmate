package specmate.dbprovider.h2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.emf.cdo.server.IStore;
import org.eclipse.emf.cdo.server.db.CDODBUtil;
import org.eclipse.emf.cdo.server.db.mapping.IMappingStrategy;
import org.eclipse.net4j.db.DBUtil;
import org.eclipse.net4j.db.IDBAdapter;
import org.eclipse.net4j.db.IDBConnectionProvider;
import org.eclipse.net4j.db.h2.H2Adapter;
import org.h2.jdbcx.JdbcDataSource;
import org.h2.util.StringUtils;
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

import specmate.dbprovider.h2.config.H2ProviderConfig;

@Component(service = IDBProvider.class, configurationPid = H2ProviderConfig.PID, configurationPolicy = ConfigurationPolicy.REQUIRE, property = {
		"service.ranking:Integer=2" })
public class H2Provider extends DBProviderBase {

	private boolean isVirginDB;
	private Pattern databaseNotFoundPattern = Pattern.compile(".*Database \\\".*\\\" not found.*", Pattern.DOTALL);

	@Activate
	public void activate(Map<String, Object> properties) throws SpecmateException {
		this.isVirginDB = false;
		readConfig(properties);

		try {
			DriverManager.registerDriver(new org.h2.Driver());
		} catch (SQLException e) {
			throw new SpecmateInternalException(ErrorCode.PERSISTENCY, "Could not register H2 JDBC driver.", e);
		}
	}

	@Modified
	public void modified(Map<String, Object> properties) throws SpecmateException {
		closeConnection();
		this.isVirginDB = false;
		readConfig(properties);
		for (DBConfigChangedCallback cb : this.cbRegister) {
			cb.configurationChanged();
		}
	}

	@Deactivate
	public void deactivate() throws SpecmateException {
		closeConnection();
	}

	private void readConfig(Map<String, Object> properties) throws SpecmateException {
		this.jdbcConnection = (String) properties.get(H2ProviderConfig.KEY_JDBC_CONNECTION);

		if (StringUtils.isNullOrEmpty(this.jdbcConnection)) {
			throw new SpecmateInternalException(ErrorCode.PERSISTENCY, "JDBC connection not defined in configuration.");
		}
	}

	@Override
	public Connection getConnection() throws SpecmateException {
		if (this.connection == null) {
			try {
				initiateDBConnection();
				// In development, when specmate or the tests are run for the
				// first time, no database exists (neither on the
				// file system, nor in memory). There is no sane way to check if
				// a database exists, except by connecting
				// to it. In case it does not exist, an SQL exception is thrown.
				// While in all other possible error cases
				// we want the client to handle the error, in the situation that
				// the database does not exist, we want
				// specmate to continue, without performing a migration, because
				// the next step CDO performs is to create the database.
			} catch (SpecmateException e) {
				Matcher matcher = this.databaseNotFoundPattern.matcher(e.getCause().getMessage());
				if (matcher.matches()) {
					this.isVirginDB = true;
				} else {
					throw e;
				}
			}
		}

		return this.connection;
	}

	@Override
	public boolean isVirginDB() throws SpecmateException {
		if (this.connection == null) {
			getConnection();
		}
		return this.isVirginDB;
	}

	@Override
	public IStore createStore() {
		JdbcDataSource jdataSource = new JdbcDataSource();
		jdataSource.setURL(this.jdbcConnection);
	 	IMappingStrategy jmappingStrategy = CDODBUtil.createHorizontalMappingStrategy(true, true);
		jmappingStrategy.getProperties().put(IMappingStrategy.Props.EAGER_TABLE_CREATION,"true");
		IDBAdapter h2dbAdapter = new H2Adapter();
		IDBConnectionProvider jdbConnectionProvider = DBUtil.createConnectionProvider(jdataSource);
		return CDODBUtil.createStore(jmappingStrategy, h2dbAdapter, jdbConnectionProvider);
	}

	@Override
	public IAttributeToSQLMapper getAttributeToSQLMapper(String packageName, String sourceVersion, String targetVersion)
			throws SpecmateException {
		return new AttributeToSQLMapper(getConnection(), packageName, sourceVersion, targetVersion);
	}

	@Override
	public IObjectToSQLMapper getObjectToSQLMapper(String packageName, String sourceVersion, String targetVersion)
			throws SpecmateException {
		return new ObjectToSQLMapper(getConnection(), packageName, sourceVersion, targetVersion);
	}

	private void initiateDBConnection() throws SpecmateException {
		try {
			this.connection = DriverManager.getConnection(this.jdbcConnection + ";IFEXISTS=TRUE", "", "");
			this.isVirginDB = false;
		} catch (SQLException e) {
			throw new SpecmateInternalException(ErrorCode.PERSISTENCY,
					"Could not connect to the H2 database using the connection: " + this.jdbcConnection + ".", e);
		}
	}

	@Override
	public String getTrueLiteral() {
		return "true";
	}
}
