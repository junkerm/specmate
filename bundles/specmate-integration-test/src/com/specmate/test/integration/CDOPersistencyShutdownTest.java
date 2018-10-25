package com.specmate.test.integration;

import java.util.Dictionary;

import org.junit.Assert;
import org.junit.Test;

import com.specmate.common.SpecmateException;
import com.specmate.common.SpecmateValidationException;
import com.specmate.model.base.BaseFactory;
import com.specmate.model.base.Folder;
import com.specmate.persistency.ITransaction;

import specmate.dbprovider.h2.config.H2ProviderConfig;

public class CDOPersistencyShutdownTest extends IntegrationTestBase {

	public CDOPersistencyShutdownTest() throws Exception {
		super();
	}

	@Test
	public void testCDOPersistencyInternalShutdown() throws SpecmateException, SpecmateValidationException {
		ITransaction transaction = persistency.openTransaction();
		transaction.enableValidators(false);
		Folder folder = getTestFolder();

		Assert.assertTrue(transaction.isActive());
		checkWriteIsPossible(transaction);
		checkModifyIsPossible(transaction, folder);
		transaction.close();

		persistency.shutdown();
		Assert.assertFalse(transaction.isActive());
		checkWriteIsNotPossible(transaction);
		checkModifyIsNotPossible(transaction, folder);

		persistency.start();
		transaction = persistency.openTransaction();
		transaction.enableValidators(false);
		Assert.assertTrue(transaction.isActive());
		checkWriteIsPossible(transaction);
		checkModifyIsPossible(transaction, folder);
		transaction.close();
	}

	@Test
	public void testReconfigureDBProvider() throws Exception {
		ITransaction transaction = persistency.openTransaction();
		transaction.enableValidators(false);
		Folder folder = getTestFolder();

		Assert.assertTrue(transaction.isActive());
		checkWriteIsPossible(transaction);
		checkModifyIsPossible(transaction, folder);
		transaction.close();

		// Let the DummyDataService startup and add the data before we shutdown
		// persistency
		Thread.sleep(7000);
		persistency.shutdown();

		// Reconfigure DB provider service, will trigger a restart of cdo
		configureDBProvider(getModifiedDBProviderProperties());

		persistency.start();

		transaction = persistency.openTransaction();
		transaction.enableValidators(false);
		Assert.assertTrue(transaction.isActive());

		// Check that new database is empty
		Assert.assertEquals(0, transaction.getResource().getContents().size());
		checkWriteIsPossible(transaction);
		checkModifyIsPossible(transaction, folder);
		transaction.close();
	}

	private void checkWriteIsNotPossible(ITransaction transaction) {
		try {
			checkWriteIsPossible(transaction);
		} catch (SpecmateException | SpecmateValidationException e) {
			// all fine
			return;
		}
		Assert.fail();
	}

	private void checkWriteIsPossible(ITransaction transaction) throws SpecmateException, SpecmateValidationException {
		Folder folder = getTestFolder();
		try {
			transaction.getResource().getContents().add(folder);
		} catch (Exception e) {
			throw new SpecmateException("Could not access transaction", e);
		}
		transaction.commit();
	}

	private Folder getTestFolder() {
		Folder folder = BaseFactory.eINSTANCE.createFolder();
		folder.setId(Long.toString(System.currentTimeMillis()));
		return folder;
	}

	private void checkModifyIsNotPossible(ITransaction transaction, Folder folder) {
		try {
			checkModifyIsPossible(transaction, folder);
		} catch (SpecmateException | SpecmateValidationException e) {
			// all fine
			return;
		}
		Assert.fail();
	}

	private void checkModifyIsPossible(ITransaction transaction, Folder folder)
			throws SpecmateException, SpecmateValidationException {
		try {
			folder.setId(Long.toString(System.currentTimeMillis()));
		} catch (Exception e) {
			throw new SpecmateException("Could not access transaction", e);
		}
		transaction.commit();
	}

	private Dictionary<String, Object> getModifiedDBProviderProperties() throws SpecmateException {
		Dictionary<String, Object> properties = super.getDBProviderProperties();
		properties.put(H2ProviderConfig.KEY_JDBC_CONNECTION,
				"jdbc:h2:mem:specmate2;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE");
		return properties;
	}
}
