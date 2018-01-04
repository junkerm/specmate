/**
 */
package com.specmate.model.history;

import org.eclipse.emf.cdo.CDOObject;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>History</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link com.specmate.model.history.History#getEntries <em>Entries</em>}</li>
 * </ul>
 *
 * @see com.specmate.model.history.HistoryPackage#getHistory()
 * @model
 * @extends CDOObject
 * @generated
 */
public interface History extends CDOObject {
	/**
	 * Returns the value of the '<em><b>Entries</b></em>' containment reference list.
	 * The list contents are of type {@link com.specmate.model.history.HistoryEntry}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Entries</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Entries</em>' containment reference list.
	 * @see com.specmate.model.history.HistoryPackage#getHistory_Entries()
	 * @model containment="true"
	 * @generated
	 */
	EList<HistoryEntry> getEntries();

} // History
