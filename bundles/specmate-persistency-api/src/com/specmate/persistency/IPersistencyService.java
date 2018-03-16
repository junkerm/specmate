package com.specmate.persistency;

/** Interface to access the persistent models */
public interface IPersistencyService {

	/** Opens a model transaction. */
	public ITransaction openTransaction();

	/**
	 * Opens a model transaction
	 * 
	 * @param attachCommitListeners
	 *            If <code>true</code> the registered commit listeners will be
	 *            attached to the transaction
	 * @return
	 */
	ITransaction openTransaction(boolean attachCommitListeners);

	/**
	 * Open a read only view
	 * 
	 * @return
	 */
	public IView openView();

	ITransaction openUserTransaction();

}
