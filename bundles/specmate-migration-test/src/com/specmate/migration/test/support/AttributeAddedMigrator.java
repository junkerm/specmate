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
import com.specmate.migration.basemigrators.AttributeAddedBaseMigrator;
import com.specmate.migration.test.AddAttributeTest;
import com.specmate.migration.test.AddServeralAttributesTest;

@Component(property = "sourceVersion=0")
public class AttributeAddedMigrator extends AttributeAddedBaseMigrator implements IMigrator {
	public static final String PID = "com.specmate.migration.text.support.AttributeAddedMigrator";
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
		this.connection = connection;
		BundleContext context = FrameworkUtil.getBundle(AttributeAddedMigrator.class).getBundleContext();
		try {
			ConfigurationAdmin ca = getConfigurationAdmin(context);
			Dictionary<String, Object> props = ca.getConfiguration(PID).getProperties();
			String testcase = (String) props.get(KEY_MIGRATOR_TEST);
			if(testcase.equals(AddAttributeTest.class.getName())) {
				migrateAttributeAdded();
			}
			else if(testcase.equals(AddServeralAttributesTest.class.getName())) {
				migrateSeveralAttributesAdded();
			}
		}
		catch(InterruptedException | IOException e) {
			throw new SpecmateException(e.getMessage());
		}
		
		
		
	}
	
	private void migrateAttributeAdded() throws SpecmateException {
		migrateNewStringAttribute("folder", "name", "");
		migrateNewStringAttribute("diagram", "name", null);
	}
	
	private void migrateSeveralAttributesAdded() throws SpecmateException {
		migrateNewStringAttribute("folder", "name", "");
		migrateNewStringAttribute("diagram", "name", null);
		migrateNewBooleanAttribute("diagram", "linked", false);
		migrateNewDoubleAttribute("diagram", "length", null);
		migrateNewIntegerAttribute("diagram", "amount", -1);
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
