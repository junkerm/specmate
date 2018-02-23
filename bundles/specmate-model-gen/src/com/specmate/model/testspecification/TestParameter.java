/**
 */
package com.specmate.model.testspecification;

import com.specmate.model.base.IContentElement;

import com.specmate.model.base.IPositionable;
import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Test Parameter</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link com.specmate.model.testspecification.TestParameter#getType <em>Type</em>}</li>
 *   <li>{@link com.specmate.model.testspecification.TestParameter#getAssignments <em>Assignments</em>}</li>
 * </ul>
 *
 * @see com.specmate.model.testspecification.TestspecificationPackage#getTestParameter()
 * @model
 * @generated
 */
public interface TestParameter extends IContentElement, IPositionable {
	/**
	 * Returns the value of the '<em><b>Type</b></em>' attribute.
	 * The literals are from the enumeration {@link com.specmate.model.testspecification.ParameterType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Type</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Type</em>' attribute.
	 * @see com.specmate.model.testspecification.ParameterType
	 * @see #setType(ParameterType)
	 * @see com.specmate.model.testspecification.TestspecificationPackage#getTestParameter_Type()
	 * @model
	 * @generated
	 */
	ParameterType getType();

	/**
	 * Sets the value of the '{@link com.specmate.model.testspecification.TestParameter#getType <em>Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Type</em>' attribute.
	 * @see com.specmate.model.testspecification.ParameterType
	 * @see #getType()
	 * @generated
	 */
	void setType(ParameterType value);

	/**
	 * Returns the value of the '<em><b>Assignments</b></em>' reference list.
	 * The list contents are of type {@link com.specmate.model.testspecification.ParameterAssignment}.
	 * It is bidirectional and its opposite is '{@link com.specmate.model.testspecification.ParameterAssignment#getParameter <em>Parameter</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Assignments</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Assignments</em>' reference list.
	 * @see com.specmate.model.testspecification.TestspecificationPackage#getTestParameter_Assignments()
	 * @see com.specmate.model.testspecification.ParameterAssignment#getParameter
	 * @model opposite="parameter"
	 * @generated
	 */
	EList<ParameterAssignment> getAssignments();

} // TestParameter
