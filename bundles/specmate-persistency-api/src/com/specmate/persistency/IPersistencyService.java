package com.specmate.persistency;

import java.util.List;

import com.specmate.common.SpecmateException;

/** Interface to access the persistent models */
public interface IPersistencyService {

	/**
	 * Opens a model transaction with default validators.
	 *
	 * @throws SpecmateException
	 */
	public ITransaction openTransaction() throws SpecmateException;

	/**
	 * Opens a model transaction.
	 *
	 * @throws SpecmateException
	 */
	public ITransaction openTransaction(List<IChangeListener> validators) throws SpecmateException;

	/**
	 * Opens a model transaction
	 *
	 * @param attachCommitListeners
	 *            If <code>true</code> the registered commit listeners will be
	 *            attached to the transaction
	 * @return
	 * @throws SpecmateException
	 */
	ITransaction openTransaction(boolean attachCommitListeners) throws SpecmateException;

	/**
	 * Open a read only view
	 *
	 * @return
	 * @throws SpecmateException
	 */
	public IView openView() throws SpecmateException;

	public void shutdown();

	public void start() throws SpecmateException;

	/**
	 * @param validator
	 *            type
	 * @return an instance of the given validator type
	 */
	public IChangeListener getValidator(IValidator.Type type);

}
