package com.specmate.persistency;

import com.specmate.common.exception.SpecmateException;

/**
 * Encapsulates the access to a model repository.
 *
 * @author junkerm
 *
 */
public interface ITransaction extends IView {
	/*
	 * A comment can consist of several records. Each record consists of one or more
	 * fields. Each field consists of one or more data items.
	 *
	 * Example with the following records (username;deleted objects;comment):
	 * michael;Model1|CEGModel,Library Folder|Folder;deleted empty models
	 */
	public static final String COMMENT_FIELD_SEPARATOR = ",";
	public static final String COMMENT_DATA_SEPARATOR = "|";
	public static final String COMMENT_RECORD_SEPARATOR = ";";

	/**
	 * Rolls back changes made in this transaction since the last commit
	 * 
	 * @throws SpecmateException
	 */
	public void rollback();

	/** Closes the transaction. */
	@Override
	public void close();

	/** Signals if uncommitted changes exist in the transaction */
	boolean isDirty();

	/**
	 * Perform a change and commit
	 *
	 * @throws SpecmateException
	 */
	<T> T doAndCommit(IChange<T> change) throws SpecmateException;

	/**
	 * Signals if the transaction is currently active
	 */
	public boolean isActive();

	public void addValidator(IChangeListener v);

	public void removeValidator(String clazz);

	public void clearValidators();

	public void enableValidators(boolean enable);
}
