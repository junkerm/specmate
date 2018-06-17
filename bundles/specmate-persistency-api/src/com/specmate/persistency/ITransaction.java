package com.specmate.persistency;

import com.specmate.common.RestResult;
import com.specmate.common.SpecmateException;
import com.specmate.common.SpecmateValidationException;

/**
 * Encapsulates the access to a model repository.
 *
 * @author junkerm
 *
 */
public interface ITransaction extends IView {

	/**
	 * Commits changes done in this transaction to the repository
	 *
	 * @throws SpecmateException
	 *             If the commit could not be performed successfully
	 */
	public void commit() throws SpecmateException;

	public void commit(String userName) throws SpecmateException;

	/** Rolls back changes made in this transaction since the last commit */
	public void rollback();

	/** Closes the transaction. */
	public void close();

	/** Signals if uncommitted changes exist in the transaction */
	boolean isDirty();

	/**
	 * Perform a change and commit
	 *
	 * @throws SpecmateValidationException
	 */
	<T> RestResult<T> doAndCommit(IChange<T> change) throws SpecmateException, SpecmateValidationException;

	/**
	 * Signals if the transaction is currently active
	 */
	public boolean isActive();
}
