package com.specmate.migration.internal.services;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.emf.cdo.common.model.EMFUtil;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EPackage.Registry;
import org.eclipse.emf.ecore.impl.EPackageRegistryImpl;
import org.h2.Driver;
import org.osgi.framework.BundleContext;
import org.osgi.framework.Constants;
import org.osgi.framework.Filter;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.service.cm.ConfigurationAdmin;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.util.tracker.ServiceTracker;

import com.specmate.common.SpecmateException;
import com.specmate.migration.api.IMigrator;
import com.specmate.migration.api.IMigratorService;
import com.specmate.persistency.IPackageProvider;
import com.specmate.persistency.cdo.config.CDOPersistenceConfig;

@Component
public class MigratorService implements IMigratorService {

	private static final int MIGRATOR_TIMEOUT = 1000;
	private static final String TABLE_PACKAGE_UNITS = "CDO_PACKAGE_UNITS";
	private static final String TABLE_PACKAGE_INFOS = "CDO_PACKAGE_INFOS";
	private static final String TABLE_EXTERNAL_REFS = "CDO_EXTERNAL_REFS";

	private Connection connection;
	private ConfigurationAdmin configurationAdmin;

	Pattern pattern = Pattern.compile("http://specmate.com/(\\d+)/.*");

	private IPackageProvider packageProvider;
	private BundleContext context;

	@Activate
	public void activate(BundleContext context) throws SpecmateException {
		this.context = context;
	}

	private void initiateDBConnection() throws SpecmateException {
		Class<Driver> h2driver = org.h2.Driver.class;

		try {
			Dictionary<String, Object> properties = configurationAdmin.getConfiguration(CDOPersistenceConfig.PID)
					.getProperties();
			String jdbcConnection = (String) properties.get(CDOPersistenceConfig.KEY_JDBC_CONNECTION);
			this.connection = DriverManager.getConnection(jdbcConnection + ";IFEXISTS=TRUE", "", "");
		} catch (SQLException e) {
			throw new SpecmateException("Migration: Could not obtain connection", e);
		} catch (IOException e) {
			throw new SpecmateException("Migration: Could not obtain database configuration", e);
		}
	}

	private void closeConnection() throws SpecmateException {
		if (connection != null) {
			try {
				connection.close();
				connection = null;
			} catch (SQLException e) {
				throw new SpecmateException("Migration: Could not close connection.");
			}
		}
	}

	@Override
	public boolean needsMigration() throws SpecmateException {
		try {
			initiateDBConnection();
		} catch (SpecmateException e) {
			// new database, no migration needed
			return false;
		}
		try {
			String currentVersion = getCurrentModelVersion();
			if (currentVersion == null) {
				throw new SpecmateException("Migration: Could not determine currently deployed model version");
			}
			String targetVersion = getTargetModelVersion();
			if (targetVersion == null) {
				throw new SpecmateException("Migration: Could not determine target model version");
			}
			return !currentVersion.equals(targetVersion);
		} finally {
			closeConnection();
		}
	}

	@Override
	public boolean doMigration() throws SpecmateException {
		initiateDBConnection();
		String currentVersion = getCurrentModelVersion();
		try {
			updatePackageUnits();
			performMigration(currentVersion);
			return true;
		} catch (SpecmateException e) {
			// TODO: handle failed migration
			// log
			// rollback
			// throw exception
			throw e;
		} finally {
			closeConnection();
		}
	}

	private String getCurrentModelVersion() throws SpecmateException {
		PreparedStatement stmt = null;
		try {
			stmt = connection.prepareStatement("select * from CDO_PACKAGE_INFOS");
			if (stmt.execute()) {
				ResultSet result = stmt.getResultSet();
				while (result.next()) {
					String packageUri = result.getString("URI");
					Matcher matcher = pattern.matcher(packageUri);
					if (matcher.matches()) {
						return matcher.group(1);
					}
				}
			}
		} catch (SQLException e) {
			throw new SpecmateException(
					"Migration: Exception while determining current model version " + "from database.", e);
		} finally {
			try {
				if (stmt != null) {
					stmt.close();
				}
			} catch (SQLException e) {
				throw new SpecmateException("Migration: Exception while closing sql statement.", e);
			}
		}
		return null;
	}

	private String getTargetModelVersion() {
		List<EPackage> packages = new ArrayList<>();
		packages.addAll(packageProvider.getPackages());

		if (!packages.isEmpty()) {
			EPackage ep = packages.get(0);
			String nsUri = ep.getNsURI();

			Matcher matcher = pattern.matcher(nsUri);
			if (matcher.matches()) {
				return matcher.group(1);
			}
		}
		return null;
	}

	private void updatePackageUnits() throws SpecmateException {
		removeOldPackageUnits();
		writeCurrentPackageUnits();
		updateExternalRefs();
	}

	private void updateExternalRefs() throws SpecmateException {
		PreparedStatement stmt;
		try {
			stmt = connection.prepareStatement(
					"update " + TABLE_EXTERNAL_REFS + " set URI=REGEXP_REPLACE(URI,'http://specmate.com/\\d+',"
							+ "'http://specmate.com/" + getTargetModelVersion() + "')");
			stmt.execute();
			stmt.close();
		} catch (SQLException e) {
			throw new SpecmateException("Migration: Could not update external references table.");
		}

	}

	private void removeOldPackageUnits() throws SpecmateException {
		try {
			PreparedStatement stmt = connection.prepareStatement(
					"delete from " + TABLE_PACKAGE_UNITS + " where left(ID,19)='http://specmate.com'");
			stmt.execute();
			stmt.close();
			stmt = connection.prepareStatement(
					"delete from " + TABLE_PACKAGE_INFOS + " where left(URI,19)='http://specmate.com'");
			stmt.execute();
			stmt.close();

		} catch (SQLException e) {
			throw new SpecmateException("Migration: Could not delete old package units.");
		}
	}

	private void writeCurrentPackageUnits() throws SpecmateException {
		Registry registry = new EPackageRegistryImpl();
		long timestamp = System.currentTimeMillis();
		PreparedStatement unitsStatement = null;
		PreparedStatement infosStatement = null;
		try {
			unitsStatement = connection.prepareStatement("insert into " + TABLE_PACKAGE_UNITS
					+ " (ID, ORIGINAL_TYPE, TIME_STAMP, PACKAGE_DATA) values (?, 0, " + timestamp + ",?)");
			infosStatement = connection
					.prepareStatement("insert into " + TABLE_PACKAGE_INFOS + " (URI, UNIT) values (?, ?)");
			for (EPackage pkg : packageProvider.getPackages()) {
				byte[] packageBytes = EMFUtil.getEPackageBytes(pkg, true, registry);
				StringBuilder sb = new StringBuilder();
				for (byte b : packageBytes) {
					sb.append(String.format("%02X ", b));
				}
				unitsStatement.setString(1, pkg.getNsURI());
				unitsStatement.setBytes(2, packageBytes);
				unitsStatement.addBatch();
				infosStatement.setString(1, pkg.getNsURI());
				infosStatement.setString(2, pkg.getNsURI());
				infosStatement.addBatch();
			}
			infosStatement.executeBatch();
			unitsStatement.executeBatch();
		} catch (SQLException e) {
			throw new SpecmateException("Exception while writing package units.", e);
		} finally {
			try {
				if (unitsStatement != null) {
					unitsStatement.close();
				}
				if (infosStatement != null) {
					infosStatement.close();
				}
			} catch (SQLException e) {
				throw new SpecmateException("Could not close statement.", e);
			}
		}

	}

	private void performMigration(String fromVersion) throws SpecmateException {

		String currentModelVersion = fromVersion;
		String targetModelVersion = getTargetModelVersion();

		while (!currentModelVersion.equals(targetModelVersion)) {
			IMigrator migrator = getMigratorForVersion(currentModelVersion);
			if (migrator == null) {
				throw new SpecmateException(
						"Migration: Could not find migrator for model version " + currentModelVersion);
			}
			migrator.migrate(connection);
			currentModelVersion = migrator.getTargetVersion();
		}

	}

	private IMigrator getMigratorForVersion(String currentModelVersion) {
		Filter filter;
		try {
			filter = context.createFilter("(&(" + Constants.OBJECTCLASS + "=" + IMigrator.class.getName() + ")"
					+ "(sourceVersion=" + currentModelVersion + "))");
			ServiceTracker<IMigrator, IMigrator> tracker = new ServiceTracker<>(context, filter, null);
			tracker.open();
			IMigrator migrator = tracker.waitForService(MIGRATOR_TIMEOUT);
			return migrator;
		} catch (InvalidSyntaxException | InterruptedException e) {
			return null;
		}
	}

	@Reference
	public void setModelProviderService(IPackageProvider packageProvider) {
		this.packageProvider = packageProvider;
	}

	@Reference
	public void setConfigurationAdmin(ConfigurationAdmin configurationAdmin) {
		this.configurationAdmin = configurationAdmin;
	}

}
