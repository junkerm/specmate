package com.specmate.migration.test.support;

import java.io.IOException;
import java.sql.Connection;
import java.util.Date;
import java.util.Dictionary;

import org.junit.Assert;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.service.cm.ConfigurationAdmin;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.util.tracker.ServiceTracker;

import com.specmate.common.SpecmateException;
import com.specmate.dbprovider.api.IDBProvider;
import com.specmate.dbprovider.api.migration.IAttributeToSQLMapper;
import com.specmate.dbprovider.api.migration.IDataType;
import com.specmate.dbprovider.api.migration.IObjectToSQLMapper;
import com.specmate.migration.api.IMigrator;
import com.specmate.migration.test.AddAttributeTest;
import com.specmate.migration.test.AddObjectTest;
import com.specmate.migration.test.AddSeveralAttributesTest;
import com.specmate.migration.test.ChangedTypesTest;
import com.specmate.migration.test.OnlyMetaChangeTest;
import com.specmate.migration.test.RenamedAttributeTest;

import specmate.dbprovider.h2.H2DataType;

@Component(property = "sourceVersion=0")
public class TestMigratorImpl implements IMigrator {
	public static final String PID = "com.specmate.migration.test.support.TestMigratorImpl";
	public static final String KEY_MIGRATOR_TEST = "testcase";
	public static final Date DEFAULT_DATE = new Date(118, 5, 10);
	private String packageName = "testmodel/artefact";
	private IDBProvider dbProvider;

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
			} else if (testcase.equals(ChangedTypesTest.class.getName())) {
				migrateTypesChanged(connection);
			} else if (testcase.equals(OnlyMetaChangeTest.class.getName())) {
				migrateOnlyMetaDataChange();
			}

		} catch (InterruptedException | IOException e) {
			throw new SpecmateException(e.getMessage());
		}
	}

	private void migrateAttributeAdded(Connection connection) throws SpecmateException {
		IAttributeToSQLMapper aAdded = dbProvider.getAttributeToSQLMapper(packageName, getSourceVersion(),
				getTargetVersion());

		String folder = com.specmate.migration.test.attributeadded.testmodel.base.BasePackage.Literals.FOLDER.getName();

		String diagram = com.specmate.migration.test.attributeadded.testmodel.artefact.ArtefactPackage.Literals.DIAGRAM
				.getName();
		String sketch = com.specmate.migration.test.attributeadded.testmodel.artefact.ArtefactPackage.Literals.SKETCH
				.getName();
		String name = com.specmate.migration.test.attributeadded.testmodel.base.BasePackage.Literals.INAMED__NAME
				.getName();
		String created = com.specmate.migration.test.attributeadded.testmodel.artefact.ArtefactPackage.Literals.DIAGRAM__CREATED
				.getName();

		aAdded.migrateNewStringAttribute(folder, name, "");
		aAdded.migrateNewStringAttribute(diagram, name, null);
		aAdded.migrateNewStringAttribute(sketch, name, null);
		aAdded.migrateNewDateAttribute(diagram, created, DEFAULT_DATE);
	}

	private void migrateSeveralAttributesAdded(Connection connection) throws SpecmateException {
		IAttributeToSQLMapper aAdded = dbProvider.getAttributeToSQLMapper(packageName, getSourceVersion(),
				getTargetVersion());

		String folder = com.specmate.migration.test.severalattributesadded.testmodel.base.BasePackage.Literals.FOLDER
				.getName();
		String diagram = com.specmate.migration.test.severalattributesadded.testmodel.artefact.ArtefactPackage.Literals.DIAGRAM
				.getName();
		String sketch = com.specmate.migration.test.severalattributesadded.testmodel.artefact.ArtefactPackage.Literals.SKETCH
				.getName();
		String name = com.specmate.migration.test.severalattributesadded.testmodel.base.BasePackage.Literals.INAMED__NAME
				.getName();
		String linked = com.specmate.migration.test.severalattributesadded.testmodel.artefact.ArtefactPackage.Literals.DIAGRAM__LINKED
				.getName();
		String length = com.specmate.migration.test.severalattributesadded.testmodel.artefact.ArtefactPackage.Literals.DIAGRAM__LENGTH
				.getName();
		String amount = com.specmate.migration.test.severalattributesadded.testmodel.artefact.ArtefactPackage.Literals.DIAGRAM__AMOUNT
				.getName();
		String intamount = com.specmate.migration.test.severalattributesadded.testmodel.artefact.ArtefactPackage.Literals.DIAGRAM__INTAMOUNT
				.getName();
		String doublelength = com.specmate.migration.test.severalattributesadded.testmodel.artefact.ArtefactPackage.Literals.DIAGRAM__DOUBLELENGTH
				.getName();
		String booleanlinked = com.specmate.migration.test.severalattributesadded.testmodel.artefact.ArtefactPackage.Literals.DIAGRAM__BOOLEANLINKED
				.getName();

		aAdded.migrateNewStringAttribute(folder, name, "");
		aAdded.migrateNewStringAttribute(diagram, name, null);
		aAdded.migrateNewStringAttribute(sketch, name, null);
		aAdded.migrateNewBooleanAttribute(diagram, linked, false);
		aAdded.migrateNewDoubleAttribute(diagram, length, null);
		aAdded.migrateNewIntegerAttribute(diagram, amount, -1);
		aAdded.migrateNewIntegerAttribute(diagram, intamount, -1);
		aAdded.migrateNewDoubleAttribute(diagram, doublelength, 0.0);
		aAdded.migrateNewBooleanAttribute(diagram, booleanlinked, false);
	}

	private void migrateObjectAdded(Connection connection) throws SpecmateException {
		String document = com.specmate.migration.test.objectadded.testmodel.artefact.ArtefactPackage.Literals.DOCUMENT
				.getName();
		String id = com.specmate.migration.test.objectadded.testmodel.base.BasePackage.Literals.IID__ID.getName();
		String tested = com.specmate.migration.test.objectadded.testmodel.base.BasePackage.Literals.ITESTABLE__TESTED
				.getName();
		String length = com.specmate.migration.test.objectadded.testmodel.artefact.ArtefactPackage.Literals.DOCUMENT__LENGTH
				.getName();
		String owner = com.specmate.migration.test.objectadded.testmodel.artefact.ArtefactPackage.Literals.DOCUMENT__OWNER
				.getName();
		String contents = com.specmate.migration.test.objectadded.testmodel.base.BasePackage.Literals.ICONTAINER__CONTENTS
				.getName();

		IObjectToSQLMapper oAdded = dbProvider.getObjectToSQLMapper(packageName, getSourceVersion(),
				getTargetVersion());
		oAdded.newObject(document);

		IAttributeToSQLMapper aAdded = dbProvider.getAttributeToSQLMapper(packageName, getSourceVersion(),
				getTargetVersion());
		aAdded.migrateNewStringAttribute(document, id, "");
		aAdded.migrateNewBooleanAttribute(document, tested, false);
		aAdded.migrateNewLongAttribute(document, length, null);
		aAdded.migrateNewStringAttribute(document, owner, null);
		aAdded.migrateNewReference(document, contents);
	}

	private void migrateAttributeRenamed(Connection connection) throws SpecmateException {
		IAttributeToSQLMapper aRenamed = dbProvider.getAttributeToSQLMapper(packageName, getSourceVersion(),
				getTargetVersion());

		String diagram = com.specmate.migration.test.attributerenamed.testmodel.artefact.ArtefactPackage.Literals.DIAGRAM
				.getName();
		String sketch = com.specmate.migration.test.attributerenamed.testmodel.artefact.ArtefactPackage.Literals.SKETCH
				.getName();
		String tested = com.specmate.migration.test.baseline.testmodel.base.BasePackage.Literals.ITESTABLE__TESTED
				.getName();
		String istested = com.specmate.migration.test.attributerenamed.testmodel.base.BasePackage.Literals.ITESTABLE__ISTESTED
				.getName();

		aRenamed.migrateRenameAttribute(diagram, tested, istested);
		aRenamed.migrateRenameAttribute(sketch, tested, istested);
	}

	private void migrateTypesChanged(Connection connection) throws SpecmateException {
		IAttributeToSQLMapper aTypeChanged = dbProvider.getAttributeToSQLMapper(packageName, getSourceVersion(),
				getTargetVersion());

		String sketch = com.specmate.migration.test.changedtypes.testmodel.artefact.ArtefactPackage.Literals.SKETCH
				.getName();
		String shortvar1 = com.specmate.migration.test.changedtypes.testmodel.artefact.ArtefactPackage.Literals.SKETCH__SHORT_VAR1
				.getName();
		String shortvar2 = com.specmate.migration.test.changedtypes.testmodel.artefact.ArtefactPackage.Literals.SKETCH__SHORT_VAR2
				.getName();
		String shortvar3 = com.specmate.migration.test.changedtypes.testmodel.artefact.ArtefactPackage.Literals.SKETCH__SHORT_VAR3
				.getName();
		String shortvar4 = com.specmate.migration.test.changedtypes.testmodel.artefact.ArtefactPackage.Literals.SKETCH__SHORT_VAR4
				.getName();
		String charvar1 = com.specmate.migration.test.changedtypes.testmodel.artefact.ArtefactPackage.Literals.SKETCH__CHAR_VAR1
				.getName();
		String charvar2 = com.specmate.migration.test.changedtypes.testmodel.artefact.ArtefactPackage.Literals.SKETCH__CHAR_VAR2
				.getName();
		String charvar3 = com.specmate.migration.test.changedtypes.testmodel.artefact.ArtefactPackage.Literals.SKETCH__CHAR_VAR3
				.getName();
		String charvar4 = com.specmate.migration.test.changedtypes.testmodel.artefact.ArtefactPackage.Literals.SKETCH__CHAR_VAR4
				.getName();
		String charvar5 = com.specmate.migration.test.changedtypes.testmodel.artefact.ArtefactPackage.Literals.SKETCH__CHAR_VAR5
				.getName();
		String intvar1 = com.specmate.migration.test.changedtypes.testmodel.artefact.ArtefactPackage.Literals.SKETCH__INT_VAR1
				.getName();
		String intvar2 = com.specmate.migration.test.changedtypes.testmodel.artefact.ArtefactPackage.Literals.SKETCH__INT_VAR2
				.getName();
		String intvar3 = com.specmate.migration.test.changedtypes.testmodel.artefact.ArtefactPackage.Literals.SKETCH__INT_VAR3
				.getName();
		String longvar1 = com.specmate.migration.test.changedtypes.testmodel.artefact.ArtefactPackage.Literals.SKETCH__LONG_VAR1
				.getName();
		String longvar2 = com.specmate.migration.test.changedtypes.testmodel.artefact.ArtefactPackage.Literals.SKETCH__LONG_VAR2
				.getName();
		String floatvar1 = com.specmate.migration.test.changedtypes.testmodel.artefact.ArtefactPackage.Literals.SKETCH__FLOAT_VAR1
				.getName();
		String booleanvar1 = com.specmate.migration.test.changedtypes.testmodel.artefact.ArtefactPackage.Literals.SKETCH__BOOLEAN_VAR1
				.getName();
		String stringvar1 = com.specmate.migration.test.changedtypes.testmodel.artefact.ArtefactPackage.Literals.SKETCH__STRING_VAR1
				.getName();
		String stringvar2 = com.specmate.migration.test.changedtypes.testmodel.artefact.ArtefactPackage.Literals.SKETCH__STRING_VAR2
				.getName();
		String stringvar3 = com.specmate.migration.test.changedtypes.testmodel.artefact.ArtefactPackage.Literals.SKETCH__STRING_VAR3
				.getName();
		String stringvar4 = com.specmate.migration.test.changedtypes.testmodel.artefact.ArtefactPackage.Literals.SKETCH__STRING_VAR4
				.getName();
		String stringvar5 = com.specmate.migration.test.changedtypes.testmodel.artefact.ArtefactPackage.Literals.SKETCH__STRING_VAR5
				.getName();

		aTypeChanged.migrateChangeType(sketch, shortvar1, H2DataType.INT);
		aTypeChanged.migrateChangeType(sketch, shortvar2, H2DataType.LONG);
		aTypeChanged.migrateChangeType(sketch, shortvar3, H2DataType.FLOAT);
		aTypeChanged.migrateChangeType(sketch, shortvar4, H2DataType.DOUBLE);

		aTypeChanged.migrateChangeType(sketch, charvar1, H2DataType.INT);
		aTypeChanged.migrateChangeType(sketch, charvar2, H2DataType.LONG);
		aTypeChanged.migrateChangeType(sketch, charvar3, H2DataType.FLOAT);
		aTypeChanged.migrateChangeType(sketch, charvar4, H2DataType.DOUBLE);
		IDataType charVar5 = H2DataType.STRING;
		charVar5.setSize(1);
		aTypeChanged.migrateChangeType(sketch, charvar5, charVar5);

		aTypeChanged.migrateChangeType(sketch, intvar1, H2DataType.LONG);
		aTypeChanged.migrateChangeType(sketch, intvar2, H2DataType.FLOAT);
		aTypeChanged.migrateChangeType(sketch, intvar3, H2DataType.DOUBLE);

		aTypeChanged.migrateChangeType(sketch, longvar1, H2DataType.FLOAT);
		aTypeChanged.migrateChangeType(sketch, longvar2, H2DataType.DOUBLE);

		aTypeChanged.migrateChangeType(sketch, floatvar1, H2DataType.DOUBLE);

		IDataType booleanVar1 = H2DataType.STRING;
		booleanVar1.setSize(16);
		aTypeChanged.migrateChangeType(sketch, booleanvar1, booleanVar1);

		aTypeChanged.migrateChangeType(sketch, stringvar1, H2DataType.BOOLEAN);
		aTypeChanged.migrateChangeType(sketch, stringvar2, H2DataType.BOOLEAN);
		aTypeChanged.migrateChangeType(sketch, stringvar3, H2DataType.BOOLEAN);
		aTypeChanged.migrateChangeType(sketch, stringvar4, H2DataType.BOOLEAN);
		aTypeChanged.migrateChangeType(sketch, stringvar5, H2DataType.BOOLEAN);
	}

	private void migrateOnlyMetaDataChange() {
		// Nothing to do
	}

	private ConfigurationAdmin getConfigurationAdmin(BundleContext context) throws InterruptedException {
		ServiceTracker<ConfigurationAdmin, ConfigurationAdmin> configurationAdminTracker = new ServiceTracker<>(context,
				ConfigurationAdmin.class.getName(), null);

		configurationAdminTracker.open();
		ConfigurationAdmin configurationAdmin = configurationAdminTracker.waitForService(10000);
		Assert.assertNotNull(configurationAdmin);
		return configurationAdmin;
	}

	@Reference
	public void setDBProvider(IDBProvider dbProvider) {
		this.dbProvider = dbProvider;
	}
}
