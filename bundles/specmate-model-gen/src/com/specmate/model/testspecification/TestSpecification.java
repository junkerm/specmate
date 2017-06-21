/**
 */
package com.specmate.model.testspecification;

import com.specmate.model.base.IContainer;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Test Specification</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link com.specmate.model.testspecification.TestSpecification#isGenerated <em>Generated</em>}</li>
 * </ul>
 *
 * @see com.specmate.model.testspecification.TestspecificationPackage#getTestSpecification()
 * @model
 * @generated
 */
public interface TestSpecification extends IContainer {

	/**
	 * Returns the value of the '<em><b>Generated</b></em>' attribute.
	 * The default value is <code>""</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Generated</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Generated</em>' attribute.
	 * @see #setGenerated(boolean)
	 * @see com.specmate.model.testspecification.TestspecificationPackage#getTestSpecification_Generated()
	 * @model default=""
	 * @generated
	 */
	boolean isGenerated();

	/**
	 * Sets the value of the '{@link com.specmate.model.testspecification.TestSpecification#isGenerated <em>Generated</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Generated</em>' attribute.
	 * @see #isGenerated()
	 * @generated
	 */
	void setGenerated(boolean value);
} // TestSpecification
