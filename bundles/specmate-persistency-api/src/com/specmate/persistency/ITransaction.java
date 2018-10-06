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
	public static final String COMMENT_FIELD_SEPARATOR = ";";
	public static final String COMMENT_DATA_SEPARATOR = ",";

	/** Rolls back changes made in this transaction since the last commit */
	public void rollback();

	/** Closes the transaction. */
	@Override
	public void close();

	/** Signals if uncommitted changes exist in the transaction */
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
