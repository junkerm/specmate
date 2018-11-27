/**
 */
package com.specmate.migration.test.attributeadded.testmodel.artefact;

import com.specmate.migration.test.attributeadded.testmodel.base.IContainer;
import com.specmate.migration.test.attributeadded.testmodel.base.IModifiable;

import java.util.Date;
import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Diagram</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link com.specmate.migration.test.attributeadded.testmodel.artefact.Diagram#getCreated <em>Created</em>}</li>
 *   <li>{@link com.specmate.migration.test.attributeadded.testmodel.artefact.Diagram#getNotes <em>Notes</em>}</li>
 * </ul>
 *
 * @see com.specmate.migration.test.attributeadded.testmodel.artefact.ArtefactPackage#getDiagram()
 * @model
 * @generated
 */
public interface Diagram extends IModifiable, IContainer {
	/**
	 * Returns the value of the '<em><b>Created</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Created</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Created</em>' attribute.
	 * @see #setCreated(Date)
	 * @see com.specmate.migration.test.attributeadded.testmodel.artefact.ArtefactPackage#getDiagram_Created()
	 * @model
	 * @generated
	 */
	Date getCreated();

	/**
	 * Sets the value of the '{@link com.specmate.migration.test.attributeadded.testmodel.artefact.Diagram#getCreated <em>Created</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Created</em>' attribute.
	 * @see #getCreated()
	 * @generated
	 */
	void setCreated(Date value);

	/**
	 * Returns the value of the '<em><b>Notes</b></em>' attribute list.
	 * The list contents are of type {@link java.lang.String}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Notes</em>' attribute list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Notes</em>' attribute list.
	 * @see com.specmate.migration.test.attributeadded.testmodel.artefact.ArtefactPackage#getDiagram_Notes()
	 * @model unique="false"
	 * @generated
	 */
	EList<String> getNotes();

} // Diagram
