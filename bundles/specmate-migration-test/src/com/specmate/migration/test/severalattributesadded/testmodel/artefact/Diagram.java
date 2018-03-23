/**
 */
package com.specmate.migration.test.severalattributesadded.testmodel.artefact;

import com.specmate.migration.test.severalattributesadded.testmodel.base.IContainer;
import com.specmate.migration.test.severalattributesadded.testmodel.base.IModifiable;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Diagram</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link com.specmate.migration.test.severalattributesadded.testmodel.artefact.Diagram#getAmount <em>Amount</em>}</li>
 *   <li>{@link com.specmate.migration.test.severalattributesadded.testmodel.artefact.Diagram#getLength <em>Length</em>}</li>
 *   <li>{@link com.specmate.migration.test.severalattributesadded.testmodel.artefact.Diagram#getLinked <em>Linked</em>}</li>
 * </ul>
 *
 * @see com.specmate.migration.test.severalattributesadded.testmodel.artefact.ArtefactPackage#getDiagram()
 * @model
 * @generated
 */
public interface Diagram extends IModifiable, IContainer {
	/**
	 * Returns the value of the '<em><b>Amount</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Amount</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Amount</em>' attribute.
	 * @see #setAmount(Integer)
	 * @see com.specmate.migration.test.severalattributesadded.testmodel.artefact.ArtefactPackage#getDiagram_Amount()
	 * @model
	 * @generated
	 */
	Integer getAmount();

	/**
	 * Sets the value of the '{@link com.specmate.migration.test.severalattributesadded.testmodel.artefact.Diagram#getAmount <em>Amount</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Amount</em>' attribute.
	 * @see #getAmount()
	 * @generated
	 */
	void setAmount(Integer value);

	/**
	 * Returns the value of the '<em><b>Length</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Length</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Length</em>' attribute.
	 * @see #setLength(Double)
	 * @see com.specmate.migration.test.severalattributesadded.testmodel.artefact.ArtefactPackage#getDiagram_Length()
	 * @model
	 * @generated
	 */
	Double getLength();

	/**
	 * Sets the value of the '{@link com.specmate.migration.test.severalattributesadded.testmodel.artefact.Diagram#getLength <em>Length</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Length</em>' attribute.
	 * @see #getLength()
	 * @generated
	 */
	void setLength(Double value);

	/**
	 * Returns the value of the '<em><b>Linked</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Linked</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Linked</em>' attribute.
	 * @see #setLinked(Boolean)
	 * @see com.specmate.migration.test.severalattributesadded.testmodel.artefact.ArtefactPackage#getDiagram_Linked()
	 * @model
	 * @generated
	 */
	Boolean getLinked();

	/**
	 * Sets the value of the '{@link com.specmate.migration.test.severalattributesadded.testmodel.artefact.Diagram#getLinked <em>Linked</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Linked</em>' attribute.
	 * @see #getLinked()
	 * @generated
	 */
	void setLinked(Boolean value);

} // Diagram
