package com.specmate.emfrest.internal;

import org.glassfish.hk2.api.Factory;

import com.specmate.persistency.IPersistencyService;
import com.specmate.persistency.ITransaction;

public class TransactionFactory implements Factory<ITransaction>{

	private IPersistencyService persistencyService;
	
	public TransactionFactory(IPersistencyService persistencyService) {
		this.persistencyService=persistencyService;
	}

	@Override
	public void dispose(ITransaction transaction) {
		transaction.close();
	}

	@Override
	public ITransaction provide() {
		return persistencyService.openTransaction();

	}
}