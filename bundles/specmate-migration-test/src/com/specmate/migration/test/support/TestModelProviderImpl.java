package com.specmate.migration.test.support;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Dictionary;

import org.eclipse.emf.ecore.EPackage;
import org.junit.Assert;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.service.cm.ConfigurationAdmin;
import org.osgi.service.component.annotations.Component;
import org.osgi.util.tracker.ServiceTracker;

import com.specmate.persistency.IPackageProvider;

@Component(immediate=true, service = IPackageProvider.class, property= {"service.ranking:Integer=1"})
public class TestModelProviderImpl implements IPackageProvider {
	public static final String PID = "com.specmate.migration.text.support.TestModelProviderImpl";
	public static final String KEY_MODEL_NAME = "modelname";

	@Override
	public Collection<? extends EPackage> getPackages() {
		BundleContext context = FrameworkUtil.getBundle(AttributeAddedMigrator.class).getBundleContext();
		try {
			ConfigurationAdmin ca = getConfigurationAdmin(context);
			Dictionary<String, Object> props = ca.getConfiguration(PID).getProperties();
			Object value = props.get(KEY_MODEL_NAME);
			assertNotNull(value);
			assertTrue(value instanceof String);
			String modelName = (String) value;
			if (modelName.equals(com.specmate.migration.test.baseline.testmodel.base.BasePackage.class.getName())) {
				return getBasemodelPackages();
			} else if (modelName.equals(com.specmate.migration.test.attributeadded.testmodel.base.BasePackage.class.getName())) {
				return getAttributeAddedPackages();
			} else if (modelName.equals(com.specmate.migration.test.severalattributesadded.testmodel.base.BasePackage.class.getName())) {
				return getSeveralAttributesAddedPackages();
			}
		} catch (InterruptedException | IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	private Collection<? extends EPackage> getBasemodelPackages() {
		return Arrays.asList(com.specmate.migration.test.baseline.testmodel.base.BasePackage.eINSTANCE, 
				com.specmate.migration.test.baseline.testmodel.artefact.ArtefactPackage.eINSTANCE);
	}
	
	private Collection<? extends EPackage> getAttributeAddedPackages() {
		return Arrays.asList(com.specmate.migration.test.attributeadded.testmodel.base.BasePackage.eINSTANCE,
				com.specmate.migration.test.attributeadded.testmodel.artefact.ArtefactPackage.eINSTANCE);
	}
	
	private Collection<? extends EPackage> getSeveralAttributesAddedPackages() {
		return Arrays.asList(com.specmate.migration.test.severalattributesadded.testmodel.base.BasePackage.eINSTANCE, 
				com.specmate.migration.test.severalattributesadded.testmodel.artefact.ArtefactPackage.eINSTANCE);
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
