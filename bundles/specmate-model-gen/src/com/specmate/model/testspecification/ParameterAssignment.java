/**
 */
package com.specmate.model.testspecification;

import com.specmate.model.base.IContentElement;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Parameter Assignment</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link com.specmate.model.testspecification.ParameterAssignment#getParameter <em>Parameter</em>}</li>
 *   <li>{@link com.specmate.model.testspecification.ParameterAssignment#getValue <em>Value</em>}</li>
 * </ul>
 *
 * @see com.specmate.model.testspecification.TestspecificationPackage#getParameterAssignment()
 * @model
 * @generated
 */
public interface ParameterAssignment extends IContentElement {
	/**
	 * Returns the value of the '<em><b>Parameter</b></em>' reference.
	 * It is bidirectional and its opposite is '{@link com.specmate.model.testspecification.TestParameter#getAssignments <em>Assignments</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Parameter</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Parameter</em>' reference.
	 * @see #setParameter(TestParameter)
	 * @see com.specmate.model.testspecification.TestspecificationPackage#getParameterAssignment_Parameter()
	 * @see com.specmate.model.testspecification.TestParameter#getAssignments
	 * @model opposite="assignments"
	 * @generated
	 */
	TestParameter getParameter();

	/**
	 * Sets the value of the '{@link com.specmate.model.testspecification.ParameterAssignment#getParameter <em>Parameter</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Parameter</em>' reference.
	 * @see #getParameter()
	 * @generated
	 */
	void setParameter(TestParameter value);

	/**
	 * Returns the value of the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Value</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Value</em>' attribute.
	 * @see #setValue(String)
	 * @see com.specmate.model.testspecification.TestspecificationPackage#getParameterAssignment_Value()
	 * @model
	 * @generated
	 */
	String getValue();

	/**
	 * Sets the value of the '{@link com.specmate.model.testspecification.ParameterAssignment#getValue <em>Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Value</em>' attribute.
	 * @see #getValue()
	 * @generated
	 */
	void setValue(String value);

} // ParameterAssignment
