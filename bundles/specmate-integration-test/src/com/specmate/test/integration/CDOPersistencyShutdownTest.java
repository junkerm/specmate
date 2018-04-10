package com.specmate.test.integration;

import java.util.Dictionary;

import org.junit.Assert;
import org.junit.Test;

import com.specmate.common.SpecmateException;
import com.specmate.model.base.BaseFactory;
import com.specmate.model.base.Folder;
import com.specmate.persistency.ITransaction;
import com.specmate.persistency.cdo.config.CDOPersistenceConfig;

public class CDOPersistencyShutdownTest extends IntegrationTestBase {

	public CDOPersistencyShutdownTest() throws Exception {
		super();
	}

	@Test
	public void testCDOPersistencyInternalShutdown() throws SpecmateException {
		ITransaction transaction = persistency.openTransaction();
		Folder folder = getTestFolder();

		Assert.assertTrue(transaction.isActive());
		checkWriteIsPossible(transaction);
		checkModifyIsPossible(transaction, folder);

		persistency.shutdown();
		Assert.assertFalse(transaction.isActive());
		checkWriteIsNotPossible(transaction);
		checkModifyIsNotPossible(transaction, folder);

		persistency.start();
		Assert.assertTrue(transaction.isActive());
		checkWriteIsPossible(transaction);
		checkModifyIsPossible(transaction, folder);

	}

	@Test
	public void testReconfigureCDO() throws Exception {
		ITransaction transaction = persistency.openTransaction();
		Folder folder = getTestFolder();

		Assert.assertTrue(transaction.isActive());
		checkWriteIsPossible(transaction);
		checkModifyIsPossible(transaction, folder);

		// Reconfigure persistency service, will trigger a restart of cdo
		configurePersistency(getModifiedPersistencyProperties());

		// Allow reconfiguration to take place
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			throw new SpecmateException(e);
		}

		persistency = getPersistencyService();
		transaction = persistency.openTransaction();
		Assert.assertTrue(transaction.isActive());

		// Check that new persistency is empty
		Assert.assertTrue(transaction.getResource().getContents().size() == 0);
		checkWriteIsPossible(transaction);
		checkModifyIsPossible(transaction, folder);

	}

	private void checkWriteIsNotPossible(ITransaction transaction) {
		try {
			checkWriteIsPossible(transaction);
		} catch (SpecmateException e) {
			// all fine
			return;
		}
		Assert.fail();
	}

	private void checkWriteIsPossible(ITransaction transaction) throws SpecmateException {
		Folder folder = getTestFolder();
		try {
			transaction.getResource().getContents().add(folder);
		} catch (Exception e) {
			throw new SpecmateException("Could not access transaction");
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
		} catch (SpecmateException e) {
			// all fine
			return;
		}
		Assert.fail();
	}

	private void checkModifyIsPossible(ITransaction transaction, Folder folder) throws SpecmateException {
		try {
			folder.setId(Long.toString(System.currentTimeMillis()));
		} catch (Exception e) {
			throw new SpecmateException("Could not access transaction");
		}
		transaction.commit();
	}

	private Dictionary<String, Object> getModifiedPersistencyProperties() {
		Dictionary<String, Object> properties = getPersistencyProperties();
		properties.put(CDOPersistenceConfig.KEY_JDBC_CONNECTION, "jdbc:h2:mem:specmate2;DB_CLOSE_DELAY=-1");
		return properties;
	}
}
