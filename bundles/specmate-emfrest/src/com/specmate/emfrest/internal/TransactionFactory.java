package com.specmate.emfrest.internal;

import org.glassfish.hk2.api.Factory;
import org.glassfish.hk2.api.PerThread;
import org.osgi.service.log.LogService;

import com.specmate.common.SpecmateException;
import com.specmate.persistency.IPersistencyService;
import com.specmate.persistency.ITransaction;

public class TransactionFactory implements Factory<ITransaction> {

	private IPersistencyService persistencyService;
	private LogService logService;

	public TransactionFactory(IPersistencyService persistencyService, LogService logService) {
		this.persistencyService = persistencyService;
		this.logService = logService;
	}

	@Override
	public void dispose(ITransaction transaction) {
		transaction.close();
	}

	@PerThread
	@Override
	public ITransaction provide() {
		try {
			logService.log(LogService.LOG_DEBUG, "Create new transaction.");
			return persistencyService.openTransaction();

		} catch (SpecmateException e) {
			logService.log(LogService.LOG_ERROR, "Transaction factory could not create new transaction", e);
			return null;
		}

	}
}