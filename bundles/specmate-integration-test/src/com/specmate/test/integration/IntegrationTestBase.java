package com.specmate.test.integration;

import java.io.IOException;
import java.util.Dictionary;

import org.junit.Assert;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.service.cm.ConfigurationAdmin;
import org.osgi.util.tracker.ServiceTracker;

import com.specmate.common.OSGiUtil;
import com.specmate.common.SpecmateException;
import com.specmate.common.SpecmateValidationException;
import com.specmate.model.base.BaseFactory;
import com.specmate.model.base.Folder;
import com.specmate.persistency.IChange;
import com.specmate.persistency.IPersistencyService;
import com.specmate.persistency.ITransaction;

import specmate.dbprovider.h2.config.H2ProviderConfig;

public class IntegrationTestBase {

	// JUnits creates a new object for every test. Making these fields static
	// avoids that
	// the services are created over and over again.
	protected static IPersistencyService persistency;
	protected static ConfigurationAdmin configAdmin;
	protected static BundleContext context;
	private static boolean firstTestRun;

	private final String integrationTestProjectnameBase = "iproject";
	private final String integrationTestTopFoldernameBase = "itopfolder";
	private int numProjects = 2;
	protected String[] integrationTestProjects = new String[numProjects];
	protected String[] integrationTestTopFolders = new String[numProjects];
	private int selectedProject = 0;

	public IntegrationTestBase() throws Exception {
		init();

		// Give all services some time to startup before running the first test
		if (firstTestRun) {
			Thread.sleep(5000);
			firstTestRun = false;
		}
	}

	private void init() throws Exception {
		if (context == null) {
			context = FrameworkUtil.getBundle(EmfRestTest.class).getBundleContext();
		}

		if (configAdmin == null) {
			configAdmin = getConfigAdmin();
		}

		if (persistency == null) {
			persistency = getPersistencyService();
		}

		preparePersistency();
	}

	protected void configureDBProvider(Dictionary<String, Object> properties) throws Exception {
		OSGiUtil.configureService(configAdmin, H2ProviderConfig.PID, properties);

		// Allow time for the database provider to be started
		Thread.sleep(2000);
	}

	protected Dictionary<String, Object> getDBProviderProperties() throws SpecmateException {
		Dictionary<String, Object> properties = null;
		try {
			properties = configAdmin.getConfiguration(H2ProviderConfig.PID).getProperties();
		} catch (IOException e) {
			throw new SpecmateException("Could not retrieve configuration properties for H2 database provider", e);
		}

		return properties;
	}

	private void preparePersistency() throws SpecmateException, SpecmateValidationException {
		ITransaction transaction = persistency.openTransaction();
		transaction.enableValidators(false);

		transaction.doAndCommit(new IChange<Object>() {
			@Override
			public Object doChange() throws SpecmateException {
				transaction.getResource().getContents().clear();

				for (int i = 0; i < numProjects; i++) {
					String integrationTestProject = integrationTestProjectnameBase + i;
					integrationTestProjects[i] = integrationTestProject;

					String integrationTestTopFolder = integrationTestTopFoldernameBase + i;
					integrationTestTopFolders[i] = integrationTestTopFolder;

					Folder projectRoot = BaseFactory.eINSTANCE.createFolder();
					projectRoot.setId(integrationTestProject);
					projectRoot.setName(integrationTestProject);

					Folder projectTopFolder = BaseFactory.eINSTANCE.createFolder();
					projectTopFolder.setId(integrationTestTopFolder);
					projectTopFolder.setName(integrationTestTopFolder);

					projectRoot.getContents().add(projectTopFolder);

					transaction.getResource().getContents().add(projectRoot);
				}

				return null;
			}
		});

		try {
			Thread.sleep(200);
		} catch (InterruptedException e) {
			throw new SpecmateException(e);
		}
		transaction.close();
	}

	private ConfigurationAdmin getConfigAdmin() throws SpecmateException {
		ServiceTracker<ConfigurationAdmin, ConfigurationAdmin> configAdminTracker = new ServiceTracker<>(context,
				ConfigurationAdmin.class.getName(), null);
		configAdminTracker.open();
		ConfigurationAdmin configAdmin;
		try {
			configAdmin = configAdminTracker.waitForService(20000);
		} catch (InterruptedException e) {
			throw new SpecmateException(e);
		}
		Assert.assertNotNull(configAdmin);
		return configAdmin;
	}

	protected IPersistencyService getPersistencyService() throws SpecmateException {
		ServiceTracker<IPersistencyService, IPersistencyService> persistencyTracker = new ServiceTracker<>(context,
				IPersistencyService.class.getName(), null);
		persistencyTracker.open();
		IPersistencyService persistency;
		try {
			persistency = persistencyTracker.waitForService(20000);
		} catch (InterruptedException e) {
			throw new SpecmateException(e);
		}
		Assert.assertNotNull(persistency);
		return persistency;
	}

	protected String getSelectedProject() {
		return integrationTestProjects[selectedProject];
	}

	protected String getSelectedTopFolder() {
		return integrationTestTopFolders[selectedProject];
	}

	protected void nextProject() {
		if (selectedProject < numProjects) {
			selectedProject++;
		} else {
			selectedProject = 0;
		}
	}

	protected void resetSelectedProject() {
		selectedProject = 0;
	}
}
