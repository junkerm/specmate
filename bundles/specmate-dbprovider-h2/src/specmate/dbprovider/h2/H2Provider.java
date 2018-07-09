package specmate.dbprovider.h2;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Dictionary;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.emf.cdo.server.IStore;
import org.eclipse.emf.cdo.server.db.CDODBUtil;
import org.eclipse.emf.cdo.server.db.mapping.IMappingStrategy;
import org.eclipse.net4j.db.DBUtil;
import org.eclipse.net4j.db.IDBAdapter;
import org.eclipse.net4j.db.IDBConnectionProvider;
import org.eclipse.net4j.db.h2.H2Adapter;
import org.h2.Driver;
import org.h2.jdbcx.JdbcDataSource;
import org.h2.util.StringUtils;
import org.osgi.service.cm.ConfigurationAdmin;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;
import org.osgi.service.component.annotations.Reference;

import com.specmate.common.SpecmateException;
import com.specmate.dbprovider.api.IDBProvider;

import specmate.dbprovider.h2.config.H2ProviderConfig;

@Component(service = IDBProvider.class, configurationPid = H2ProviderConfig.PID, configurationPolicy = ConfigurationPolicy.REQUIRE, property = {
		"service.ranking:Integer=2" })
public class H2Provider implements IDBProvider {

	private ConfigurationAdmin configurationAdmin;
	private Connection connection;
	private String jdbcConnection;
	private String repository;
	private String resource;
	private boolean isVirginDB;
	private Pattern databaseNotFoundPattern = Pattern.compile(".*Database \\\".*\\\" not found.*", Pattern.DOTALL);

	@Activate
	public void activate() throws SpecmateException {
		this.isVirginDB = false;
		readConfig();
	}

	@Override
	public void readConfig() throws SpecmateException {
		try {
			Dictionary<String, Object> configProperties = configurationAdmin.getConfiguration(H2ProviderConfig.PID)
					.getProperties();
			this.jdbcConnection = (String) configProperties.get(H2ProviderConfig.KEY_JDBC_CONNECTION);
			this.repository = (String) configProperties.get(H2ProviderConfig.KEY_REPOSITORY_NAME);
			this.resource = (String) configProperties.get(H2ProviderConfig.KEY_RESOURCE_NAME);

			String failmsg = " not defined in configuration.";
			if (StringUtils.isNullOrEmpty(this.jdbcConnection)) {
				throw new SpecmateException("JDBC connection" + failmsg);
			}

			if (StringUtils.isNullOrEmpty(this.repository)) {
				throw new SpecmateException("Database repository" + failmsg);
			}

			if (StringUtils.isNullOrEmpty(this.resource)) {
				throw new SpecmateException("Database resource" + failmsg);
			}
		} catch (IOException e) {
			throw new SpecmateException("Could not obtain database configuration.", e);
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
				Matcher matcher = databaseNotFoundPattern.matcher(e.getCause().getMessage());
				if (matcher.matches()) {
					this.isVirginDB = true;
				} else {
					throw e;
				}
			}
		}

		return connection;
	}

	@Override
	public boolean isVirginDB() throws SpecmateException {
		if (this.connection == null) {
			getConnection();
		}
		return isVirginDB;
	}

	@Override
	public IStore getStore() {
		JdbcDataSource jdataSource = new JdbcDataSource();
		jdataSource.setURL(this.jdbcConnection);
		IMappingStrategy jmappingStrategy = CDODBUtil.createHorizontalMappingStrategy(true);
		IDBAdapter h2dbAdapter = new H2Adapter();
		IDBConnectionProvider jdbConnectionProvider = DBUtil.createConnectionProvider(jdataSource);
		return CDODBUtil.createStore(jmappingStrategy, h2dbAdapter, jdbConnectionProvider);
	}

	private void initiateDBConnection() throws SpecmateException {
		Class<Driver> h2driver = org.h2.Driver.class;

		try {
			this.connection = DriverManager.getConnection(this.jdbcConnection + ";IFEXISTS=TRUE", "", "");
			this.isVirginDB = false;
		} catch (SQLException e) {
			throw new SpecmateException(
					"Could not connect to the H2 database using the connection: " + this.jdbcConnection + ".", e);
		}
	}

	@Override
	public void closeConnection() throws SpecmateException {
		if (this.connection != null) {
			try {
				connection.close();
				connection = null;
			} catch (SQLException e) {
				throw new SpecmateException("Could not close connection.", e);
			}
		}
	}

	@Override
	public String getResource() {
		return this.resource;
	}

	@Override
	public String getRepository() {
		return this.repository;
	}

	@Reference
	public void setConfigurationAdmin(ConfigurationAdmin configurationAdmin) {
		this.configurationAdmin = configurationAdmin;
	}

}
