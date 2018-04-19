package com.specmate.migration.test.support;

import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.List;

import org.junit.Assert;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.service.cm.ConfigurationAdmin;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.log.LogService;
import org.osgi.util.tracker.ServiceTracker;

import com.specmate.common.SpecmateException;
import com.specmate.migration.api.IMigrator;
import com.specmate.migration.h2.AttributeToSQLMapper;
import com.specmate.migration.h2.ObjectToSQLMapper;
import com.specmate.migration.test.AddAttributeTest;
import com.specmate.migration.test.AddObjectTest;
import com.specmate.migration.test.AddSeveralAttributesTest;
import com.specmate.migration.test.RenamedAttributeTest;

@Component(property = "sourceVersion=0")
public class TestMigratorImpl implements IMigrator {
	public static final String PID = "com.specmate.migration.test.support.TestMigratorImpl";
	public static final String KEY_MIGRATOR_TEST = "testcase";
	private String packageName = "testmodel/artefact";
	private LogService logService;

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
			} else if (testcase.equals(RenamedAttributeTest.class.getName())) {
				migrateAttributeRenamed(connection);
			}
			
		} catch (InterruptedException | IOException e) {
			throw new SpecmateException(e.getMessage());
		}
	}
	
	private void migrateAttributeAdded(Connection connection) throws SpecmateException {
		AttributeToSQLMapper aAdded = new AttributeToSQLMapper(connection, logService, packageName, getSourceVersion(), 
				getTargetVersion());
		aAdded.migrateNewStringAttribute("folder", "name", "");
		aAdded.migrateNewStringAttribute("diagram", "name", null);
	}
	
	private void migrateSeveralAttributesAdded(Connection connection) throws SpecmateException {
		AttributeToSQLMapper aAdded = new AttributeToSQLMapper(connection, logService, packageName, getSourceVersion(), 
				getTargetVersion());
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
		String objectName = "Document";
		List<String> attributeNames = new ArrayList<>();
		attributeNames.add("length");
		attributeNames.add("owner");
		
		ObjectToSQLMapper oAdded = new ObjectToSQLMapper(connection, logService, packageName, getSourceVersion(), 
				getTargetVersion());
		oAdded.newObject(objectName, attributeNames);
		
		AttributeToSQLMapper aAdded = new AttributeToSQLMapper(connection, logService, packageName, getSourceVersion(), 
				getTargetVersion());
		aAdded.migrateNewStringAttribute(objectName, "id", "");
		aAdded.migrateNewBooleanAttribute(objectName, "tested", false);
		aAdded.migrateNewLongAttribute(objectName, "length", null);
		aAdded.migrateNewStringAttribute(objectName, "owner", null);
		aAdded.migrateNewReference(objectName, "contents");
	}
	
	private void migrateAttributeRenamed(Connection connection) throws SpecmateException {
		AttributeToSQLMapper aRenamed = new AttributeToSQLMapper(connection, logService, packageName, 
				getSourceVersion(), getTargetVersion());
		aRenamed.migrateRenameAttribute("Diagram", "tested", "istested");
	}
	
	private ConfigurationAdmin getConfigurationAdmin(BundleContext context) throws InterruptedException {
		ServiceTracker<ConfigurationAdmin, ConfigurationAdmin> configurationAdminTracker =
				new ServiceTracker<>(context, ConfigurationAdmin.class.getName(), null);
		
		configurationAdminTracker.open();
		ConfigurationAdmin configurationAdmin = configurationAdminTracker.waitForService(10000);
		Assert.assertNotNull(configurationAdmin);
		return configurationAdmin;
	}
	
	@Reference
	public void setLogService(LogService logService) {
		this.logService = logService;
	}

}
