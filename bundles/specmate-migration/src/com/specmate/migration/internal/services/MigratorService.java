package com.specmate.migration.internal.services;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.emf.cdo.common.model.EMFUtil;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.impl.EPackageRegistryImpl;
import org.h2.Driver;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;

import com.specmate.common.SpecmateException;
import com.specmate.migration.api.IMigratorService;
import com.specmate.model.base.BasePackage;

@Component(immediate = true)
public class MigratorService implements IMigratorService {

	private Connection connection;

	Pattern pattern = Pattern.compile("http://specmate.com/(\\d+)/.*");

	@Activate
	public void activate() throws SpecmateException {
		initiateDBConnection();
	}

	private void initiateDBConnection() throws SpecmateException {
		Class<Driver> h2driver = org.h2.Driver.class;

		try {
			this.connection = DriverManager.getConnection("jdbc:h2:./database/specmate", "", "");
		} catch (SQLException e) {
			throw new SpecmateException("Migration failed: Could not obtain connection", e);
		}
	}

	@Override
	public boolean needsMigration() throws SpecmateException {
		String currentVersion = getCurrentModelVersion();
		if (currentVersion == null) {
			throw new SpecmateException("Could not determine currently deployed model version");
		}
		String targetVersion = getTargetModelVersion();
		if (targetVersion == null) {
			throw new SpecmateException("Could not determine target model version");
		}
		writeCurrentPackageUnits();
		return !currentVersion.equals(targetVersion);
	}

	private String getCurrentModelVersion() {
		try {
			PreparedStatement stmt = connection.prepareStatement("select * from CDO_PACKAGE_INFOS");
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
			}
		}
		return null;
	}

	private String getTargetModelVersion() {
		String nsUri = BasePackage.eNS_URI;
		Matcher matcher = pattern.matcher(nsUri);
		if (matcher.matches()) {
			return matcher.group(1);
		}
		return null;
	}

	private void writeCurrentPackageUnits() {
		byte[] result = EMFUtil.getEPackageBytes((EPackage) BasePackage.eINSTANCE, true, new EPackageRegistryImpl());
		StringBuffer sb = new StringBuffer();
		for (byte b : result) {
			sb.append(String.format("%02X ", b));
		}
		System.out.println(sb);
	}

}
