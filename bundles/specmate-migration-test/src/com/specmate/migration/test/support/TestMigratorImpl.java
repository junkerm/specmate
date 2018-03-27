package com.specmate.migration.test.support;

import java.io.IOException;
import java.sql.Connection;
import java.util.Dictionary;

import org.junit.Assert;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.service.cm.ConfigurationAdmin;
import org.osgi.service.component.annotations.Component;
import org.osgi.util.tracker.ServiceTracker;

import com.specmate.common.SpecmateException;
import com.specmate.migration.api.IMigrator;
import com.specmate.migration.h2.AttributeAddedtoSQLMapper;
import com.specmate.migration.h2.ObjectAddedtoSQLMapper;
import com.specmate.migration.test.AddAttributeTest;
import com.specmate.migration.test.AddObjectTest;
import com.specmate.migration.test.AddSeveralAttributesTest;

@Component(property = "sourceVersion=0")
public class TestMigratorImpl implements IMigrator {
	public static final String PID = "com.specmate.migration.test.support.TestMigratorImpl";
	public static final String KEY_MIGRATOR_TEST = "testcase";

	@Override
	public String getSourceVersion() {
		return "0";
	}

	@Override
	public String getTargetVersion() {
		return "1";
	}

	@Override
	public void migrate(Connection connection) throws SpecmateException {
		BundleContext context = FrameworkUtil.getBundle(TestMigratorImpl.class).getBundleContext();
		try {
			ConfigurationAdmin ca = getConfigurationAdmin(context);
			Dictionary<String, Object> props = ca.getConfiguration(PID).getProperties();
			String testcase = (String) props.get(KEY_MIGRATOR_TEST);
			if (testcase.equals(AddAttributeTest.class.getName())) {
				migrateAttributeAdded(connection);
			} else if (testcase.equals(AddSeveralAttributesTest.class.getName())) {
				migrateSeveralAttributesAdded(connection);
			} else if (testcase.equals(AddObjectTest.class.getName())) {
				migrateObjectAdded(connection);
			}
			
		} catch (InterruptedException | IOException e) {
			throw new SpecmateException(e.getMessage());
		}
	}
	
	private void migrateAttributeAdded(Connection connection) throws SpecmateException {
		AttributeAddedtoSQLMapper aAdded = new AttributeAddedtoSQLMapper(connection);
		aAdded.migrateNewStringAttribute("folder", "name", "");
		aAdded.migrateNewStringAttribute("diagram", "name", null);
	}
	
	private void migrateSeveralAttributesAdded(Connection connection) throws SpecmateException {
		AttributeAddedtoSQLMapper aAdded = new AttributeAddedtoSQLMapper(connection);
		aAdded.migrateNewStringAttribute("folder", "name", "");
		aAdded.migrateNewStringAttribute("diagram", "name", null);
		aAdded.migrateNewBooleanAttribute("diagram", "linked", false);
		aAdded.migrateNewDoubleAttribute("diagram", "length", null);
		aAdded.migrateNewIntegerAttribute("diagram", "amount", -1);
		aAdded.migrateNewIntegerAttribute("diagram", "intamount", -1);
		aAdded.migrateNewDoubleAttribute("diagram", "doublelength", 0.0);
		aAdded.migrateNewBooleanAttribute("diagram", "booleanlinked", false);
	}
	
	private void migrateObjectAdded(Connection connection) throws SpecmateException {
		ObjectAddedtoSQLMapper oAdded = new ObjectAddedtoSQLMapper(connection);
		oAdded.newObject("Document");
		
		AttributeAddedtoSQLMapper aAdded = new AttributeAddedtoSQLMapper(connection);
		aAdded.migrateNewLongAttribute("Document", "length", null);
		aAdded.migrateNewStringAttribute("Document", "owner", null);
	}
	
	private ConfigurationAdmin getConfigurationAdmin(BundleContext context) throws InterruptedException {
		ServiceTracker<ConfigurationAdmin, ConfigurationAdmin> configurationAdminTracker =
				new ServiceTracker<>(context, ConfigurationAdmin.class.getName(), null);
		
		configurationAdminTracker.open();
		ConfigurationAdmin configurationAdmin = configurationAdminTracker.waitForService(10000);
		Assert.assertNotNull(configurationAdmin);
		return configurationAdmin;
	}
}
