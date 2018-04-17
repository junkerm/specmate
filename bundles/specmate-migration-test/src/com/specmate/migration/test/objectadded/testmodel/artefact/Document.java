/**
 */
package com.specmate.migration.test.objectadded.testmodel.artefact;

import com.specmate.migration.test.objectadded.testmodel.base.IContainer;
import com.specmate.migration.test.objectadded.testmodel.base.IModifiable;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Document</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link com.specmate.migration.test.objectadded.testmodel.artefact.Document#getLength <em>Length</em>}</li>
 *   <li>{@link com.specmate.migration.test.objectadded.testmodel.artefact.Document#getOwner <em>Owner</em>}</li>
 * </ul>
 *
 * @see com.specmate.migration.test.objectadded.testmodel.artefact.ArtefactPackage#getDocument()
 * @model
 * @generated
 */
public interface Document extends IModifiable, IContainer {
	/**
	 * Returns the value of the '<em><b>Length</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Length</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Length</em>' attribute.
	 * @see #setLength(long)
	 * @see com.specmate.migration.test.objectadded.testmodel.artefact.ArtefactPackage#getDocument_Length()
	 * @model
	 * @generated
	 */
	long getLength();

	/**
	 * Sets the value of the '{@link com.specmate.migration.test.objectadded.testmodel.artefact.Document#getLength <em>Length</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Length</em>' attribute.
	 * @see #getLength()
	 * @generated
	 */
	void setLength(long value);

	/**
	 * Returns the value of the '<em><b>Owner</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Owner</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Owner</em>' attribute.
	 * @see #setOwner(String)
	 * @see com.specmate.migration.test.objectadded.testmodel.artefact.ArtefactPackage#getDocument_Owner()
	 * @model
	 * @generated
	 */
	String getOwner();

	/**
	 * Sets the value of the '{@link com.specmate.migration.test.objectadded.testmodel.artefact.Document#getOwner <em>Owner</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Owner</em>' attribute.
	 * @see #getOwner()
	 * @generated
	 */
	void setOwner(String value);

} // Document
