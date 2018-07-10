package specmate.dbprovider.oracle;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Dictionary;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.emf.cdo.server.IStore;
import org.eclipse.emf.cdo.server.db.CDODBUtil;
import org.eclipse.emf.cdo.server.db.mapping.IMappingStrategy;
import org.eclipse.net4j.db.DBUtil;
import org.eclipse.net4j.db.IDBAdapter;
import org.eclipse.net4j.db.IDBConnectionProvider;
import org.eclipse.net4j.db.oracle.OracleAdapter;
import org.osgi.service.cm.ConfigurationAdmin;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;

import com.specmate.common.SpecmateException;
import com.specmate.dbprovider.api.DBConfigChangedCallback;
import com.specmate.dbprovider.api.DBProviderBase;
import com.specmate.dbprovider.api.IDBProvider;

import oracle.jdbc.pool.OracleDataSource;
import specmate.dbprovider.oracle.config.OracleProviderConfig;

@Component(service = IDBProvider.class, configurationPid = OracleProviderConfig.PID, configurationPolicy = ConfigurationPolicy.REQUIRE, property = {
		"service.ranking:Integer=1" })
public class OracleProvider extends DBProviderBase {

	private ConfigurationAdmin configurationAdmin;
	private String username;
	private String password;

	@Activate
	public void activate() throws SpecmateException {
		readConfig();
	}

	@Modified
	public void modified() throws SpecmateException {
		closeConnection();
		readConfig();
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

	private void readConfig() throws SpecmateException {
		try {
			Dictionary<String, Object> configProperties = configurationAdmin.getConfiguration(OracleProviderConfig.PID)
					.getProperties();
			this.jdbcConnection = (String) configProperties.get(OracleProviderConfig.KEY_JDBC_CONNECTION);
			this.repository = (String) configProperties.get(OracleProviderConfig.KEY_REPOSITORY_NAME);
			this.resource = (String) configProperties.get(OracleProviderConfig.KEY_RESOURCE_NAME);
			this.username = (String) configProperties.get(OracleProviderConfig.KEY_USERNAME);
			this.password = (String) configProperties.get(OracleProviderConfig.KEY_PASSWORD);

			String failmsg = " not defined in configuration.";

			if (!StringUtils.isNotEmpty(this.jdbcConnection)) {
				throw new SpecmateException("JDBC connection" + failmsg);
			}

			if (!StringUtils.isNotEmpty(this.repository)) {
				throw new SpecmateException("Database repository" + failmsg);
			}

			if (!StringUtils.isNotEmpty(this.resource)) {
				throw new SpecmateException("Database resource" + failmsg);
			}

			if (!StringUtils.isNotEmpty(this.username)) {
				throw new SpecmateException("Database user name" + failmsg);
			}

			if (!StringUtils.isNotEmpty(this.password)) {
				throw new SpecmateException("Database password" + failmsg);
			}
		} catch (IOException e) {
			throw new SpecmateException("Could not obtain database configuration.", e);
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
			throw new SpecmateException("Could not create Oracle data store.", e);
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

	private void initiateDBConnection() throws SpecmateException {
		try {
			DriverManager.registerDriver(new oracle.jdbc.OracleDriver());
		} catch (SQLException e) {
			throw new SpecmateException("Migration: Could not register Oracle driver.", e);
		}

		try {
			this.connection = DriverManager.getConnection(this.jdbcConnection, this.username, this.password);
		} catch (SQLException e) {
			throw new SpecmateException("Migration: Could not connect to the Oracle database using the connection: "
					+ this.jdbcConnection + ".", e);
		}
	}

	@Reference
	public void setConfigurationAdmin(ConfigurationAdmin configurationAdmin) {
		this.configurationAdmin = configurationAdmin;
	}
}
