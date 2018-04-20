package com.specmate.persistency;

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
	 *             If the commit could not be perfomed successfully
	 */
	public void commit() throws SpecmateException;

	/** Rolls back changes made in this transaction since the last commit */
	public void rollback();

	/** Closes the transcation. */
	public void close();

	/** Signals if uncomitted changes exist in the transaction */
	boolean isDirty();

	/**
	 * Perform a change and commit
	 * 
	 * @throws SpecmateValidationException
	 */
	<T> T doAndCommit(IChange<T> change) throws SpecmateException, SpecmateValidationException;

	/**
	 * Signals if the transaction is currently active
	 */
	public boolean isActive();

}
