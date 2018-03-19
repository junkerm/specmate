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
 *   <li>{@link com.specmate.migration.test.severalattributesadded.testmodel.artefact.Diagram#isLinked <em>Linked</em>}</li>
 *   <li>{@link com.specmate.migration.test.severalattributesadded.testmodel.artefact.Diagram#getLength <em>Length</em>}</li>
 *   <li>{@link com.specmate.migration.test.severalattributesadded.testmodel.artefact.Diagram#getCount <em>Count</em>}</li>
 * </ul>
 *
 * @see com.specmate.migration.test.severalattributesadded.testmodel.artefact.ArtefactPackage#getDiagram()
 * @model
 * @generated
 */
public interface Diagram extends IModifiable, IContainer {
	/**
	 * Returns the value of the '<em><b>Linked</b></em>' attribute.
	 * The default value is <code>"false"</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Linked</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Linked</em>' attribute.
	 * @see #setLinked(boolean)
	 * @see com.specmate.migration.test.severalattributesadded.testmodel.artefact.ArtefactPackage#getDiagram_Linked()
	 * @model default="false"
	 * @generated
	 */
	boolean isLinked();

	/**
	 * Sets the value of the '{@link com.specmate.migration.test.severalattributesadded.testmodel.artefact.Diagram#isLinked <em>Linked</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Linked</em>' attribute.
	 * @see #isLinked()
	 * @generated
	 */
	void setLinked(boolean value);

	/**
	 * Returns the value of the '<em><b>Length</b></em>' attribute.
	 * The default value is <code>"0.0"</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Length</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Length</em>' attribute.
	 * @see #setLength(double)
	 * @see com.specmate.migration.test.severalattributesadded.testmodel.artefact.ArtefactPackage#getDiagram_Length()
	 * @model default="0.0"
	 * @generated
	 */
	double getLength();

	/**
	 * Sets the value of the '{@link com.specmate.migration.test.severalattributesadded.testmodel.artefact.Diagram#getLength <em>Length</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Length</em>' attribute.
	 * @see #getLength()
	 * @generated
	 */
	void setLength(double value);

	/**
	 * Returns the value of the '<em><b>Count</b></em>' attribute.
	 * The default value is <code>"0"</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Count</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Count</em>' attribute.
	 * @see #setCount(int)
	 * @see com.specmate.migration.test.severalattributesadded.testmodel.artefact.ArtefactPackage#getDiagram_Count()
	 * @model default="0"
	 * @generated
	 */
	int getCount();

	/**
	 * Sets the value of the '{@link com.specmate.migration.test.severalattributesadded.testmodel.artefact.Diagram#getCount <em>Count</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Count</em>' attribute.
	 * @see #getCount()
	 * @generated
	 */
	void setCount(int value);

} // Diagram
