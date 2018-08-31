package com.specmate.migration.internal.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.emf.cdo.common.model.EMFUtil;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EPackage.Registry;
import org.eclipse.emf.ecore.impl.EPackageRegistryImpl;
import org.osgi.framework.BundleContext;
import org.osgi.framework.Constants;
import org.osgi.framework.Filter;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.log.LogService;
import org.osgi.util.tracker.ServiceTracker;

import com.specmate.common.SpecmateException;
import com.specmate.dbprovider.api.IDBProvider;
import com.specmate.migration.api.IMigrator;
import com.specmate.migration.api.IMigratorService;
import com.specmate.persistency.IPackageProvider;

@Component
public class MigratorService implements IMigratorService {

	private static final int MIGRATOR_TIMEOUT = 1000;
	private static final String TABLE_PACKAGE_UNITS = "CDO_PACKAGE_UNITS";
	private static final String TABLE_PACKAGE_INFOS = "CDO_PACKAGE_INFOS";
	private static final String TABLE_CDO_OBJECTS = "CDO_OBJECTS";

	private LogService logService;
	private IDBProvider dbProviderService;

	private Pattern versionPattern = Pattern.compile("http://specmate.com/(\\d+)/.*");

	private IPackageProvider packageProvider;
	private BundleContext context;

	@Activate
	public void activate(BundleContext context) {
		this.context = context;
	}

	@Override
	public boolean needsMigration() throws SpecmateException {
		if (dbProviderService.isVirginDB()) {
			return false;
		}

		String currentVersion = getCurrentModelVersion();
		if (currentVersion == null) {
			throw new SpecmateException("Migration: Could not determine currently deployed model version");
		}
		String targetVersion = getTargetModelVersion();
		if (targetVersion == null) {
			throw new SpecmateException("Migration: Could not determine target model version");
		}

		boolean needsMigration = !currentVersion.equals(targetVersion);

		if (needsMigration) {
			logService.log(LogService.LOG_INFO,
					"Migration needed. Current version: " + currentVersion + " / Target version: " + targetVersion);
		} else {
			logService.log(LogService.LOG_INFO, "No migration needed. Current version: " + currentVersion);
		}

		return needsMigration;
	}

	@Override
	public void doMigration() throws SpecmateException {
		String currentVersion = getCurrentModelVersion();
		try {
			updatePackageUnits();
			performMigration(currentVersion);
			logService.log(LogService.LOG_INFO, "Migration succeeded.");
		} catch (SpecmateException e) {
			logService.log(LogService.LOG_ERROR, "Migration failed.", e);
			// TODO: handle failed migration
			// rollback
			throw e;
		}
	}

	private String getCurrentModelVersion() throws SpecmateException {
		Connection connection = dbProviderService.getConnection();
		PreparedStatement stmt = null;
		try {
			stmt = connection.prepareStatement("select * from CDO_PACKAGE_INFOS");
			if (stmt.execute()) {
				ResultSet result = stmt.getResultSet();
				while (result.next()) {
					String packageUri = result.getString("URI");
					Matcher matcher = versionPattern.matcher(packageUri);
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

			Matcher matcher = versionPattern.matcher(nsUri);
			if (matcher.matches()) {
				return matcher.group(1);
			}
		}
		return null;
	}

	private void updatePackageUnits() throws SpecmateException {
		removeOldPackageUnits();
		writeCurrentPackageUnits();
		// external refs table not used anymore
		// updateExternalRefs();
		updateCDOObjects();
	}

	// private void updateExternalRefs() throws SpecmateException {
	// Connection connection = dbProviderService.getConnection();
	// PreparedStatement stmt;
	// try {
	// stmt = connection.prepareStatement(
	// "update " + TABLE_EXTERNAL_REFS + " set
	// URI=REGEXP_REPLACE(URI,'http://specmate.com/\\d+',"
	// + "'http://specmate.com/" + getTargetModelVersion() + "')");
	// stmt.execute();
	// stmt.close();
	// } catch (SQLException e) {
	// throw new SpecmateException("Migration: Could not update external references
	// table.");
	// }
	//
	// }

	private void updateCDOObjects() throws SpecmateException {
		Connection connection = dbProviderService.getConnection();
		PreparedStatement stmt;
		try {
			stmt = connection.prepareStatement("update " + TABLE_CDO_OBJECTS
					+ " set CDO_CLASS=REGEXP_REPLACE(CDO_CLASS,'http://specmate.com/\\d+'," + "'http://specmate.com/"
					+ getTargetModelVersion() + "')");
			stmt.execute();
			stmt.close();
		} catch (SQLException e) {
			throw new SpecmateException("Migration: Could not update cdo objects table.", e);
		}

	}

	private void removeOldPackageUnits() throws SpecmateException {
		try {
			Connection connection = dbProviderService.getConnection();
			PreparedStatement stmt = connection.prepareStatement(
					"delete from " + TABLE_PACKAGE_UNITS + " where substr(ID,0,19)='http://specmate.com'");
			stmt.execute();
			stmt.close();
			stmt = connection.prepareStatement(
					"delete from " + TABLE_PACKAGE_INFOS + " where substr(URI,0,19)='http://specmate.com'");
			stmt.execute();
			stmt.close();

		} catch (SQLException e) {
			throw new SpecmateException("Migration: Could not delete old package units.", e);
		}
	}

	private void writeCurrentPackageUnits() throws SpecmateException {
		Connection connection = dbProviderService.getConnection();
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
			migrator.migrate(dbProviderService.getConnection());
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
	public void setLogService(LogService logService) {
		this.logService = logService;
	}

	@Reference
	public void setDBProviderService(IDBProvider dbProviderService) {
		this.dbProviderService = dbProviderService;
	}

}
