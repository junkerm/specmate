/**
 */
package com.specmate.model.history;

import org.eclipse.emf.cdo.CDOObject;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Entry</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link com.specmate.model.history.HistoryEntry#getTimestamp <em>Timestamp</em>}</li>
 *   <li>{@link com.specmate.model.history.HistoryEntry#getUser <em>User</em>}</li>
 *   <li>{@link com.specmate.model.history.HistoryEntry#getDeletedObjects <em>Deleted Objects</em>}</li>
 *   <li>{@link com.specmate.model.history.HistoryEntry#getComment <em>Comment</em>}</li>
 *   <li>{@link com.specmate.model.history.HistoryEntry#getChanges <em>Changes</em>}</li>
 * </ul>
 *
 * @see com.specmate.model.history.HistoryPackage#getHistoryEntry()
 * @model
 * @extends CDOObject
 * @generated
 */
public interface HistoryEntry extends CDOObject {
	/**
	 * Returns the value of the '<em><b>Timestamp</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Timestamp</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Timestamp</em>' attribute.
	 * @see #setTimestamp(long)
	 * @see com.specmate.model.history.HistoryPackage#getHistoryEntry_Timestamp()
	 * @model
	 * @generated
	 */
	long getTimestamp();

	/**
	 * Sets the value of the '{@link com.specmate.model.history.HistoryEntry#getTimestamp <em>Timestamp</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Timestamp</em>' attribute.
	 * @see #getTimestamp()
	 * @generated
	 */
	void setTimestamp(long value);

	/**
	 * Returns the value of the '<em><b>User</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>User</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>User</em>' attribute.
	 * @see #setUser(String)
	 * @see com.specmate.model.history.HistoryPackage#getHistoryEntry_User()
	 * @model
	 * @generated
	 */
	String getUser();

	/**
	 * Sets the value of the '{@link com.specmate.model.history.HistoryEntry#getUser <em>User</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>User</em>' attribute.
	 * @see #getUser()
	 * @generated
	 */
	void setUser(String value);

	/**
	 * Returns the value of the '<em><b>Deleted Objects</b></em>' attribute list.
	 * The list contents are of type {@link java.lang.String}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Deleted Objects</em>' attribute list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Deleted Objects</em>' attribute list.
	 * @see com.specmate.model.history.HistoryPackage#getHistoryEntry_DeletedObjects()
	 * @model
	 * @generated
	 */
	EList<String> getDeletedObjects();

	/**
	 * Returns the value of the '<em><b>Comment</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Comment</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Comment</em>' attribute.
	 * @see #setComment(String)
	 * @see com.specmate.model.history.HistoryPackage#getHistoryEntry_Comment()
	 * @model
	 * @generated
	 */
	String getComment();

	/**
	 * Sets the value of the '{@link com.specmate.model.history.HistoryEntry#getComment <em>Comment</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Comment</em>' attribute.
	 * @see #getComment()
	 * @generated
	 */
	void setComment(String value);

	/**
	 * Returns the value of the '<em><b>Changes</b></em>' containment reference list.
	 * The list contents are of type {@link com.specmate.model.history.Change}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Changes</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Changes</em>' containment reference list.
	 * @see com.specmate.model.history.HistoryPackage#getHistoryEntry_Changes()
	 * @model containment="true"
	 * @generated
	 */
	EList<Change> getChanges();

} // HistoryEntry
