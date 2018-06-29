/**
 */
package com.specmate.model.testspecification;

import com.specmate.model.base.IContainer;
import com.specmate.model.base.IPositionable;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Test Case</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link com.specmate.model.testspecification.TestCase#isConsistent <em>Consistent</em>}</li>
 * </ul>
 *
 * @see com.specmate.model.testspecification.TestspecificationPackage#getTestCase()
 * @model
 * @generated
 */
public interface TestCase extends IContainer, IPositionable {
	/**
	 * Returns the value of the '<em><b>Consistent</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Consistent</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Consistent</em>' attribute.
	 * @see #setConsistent(boolean)
	 * @see com.specmate.model.testspecification.TestspecificationPackage#getTestCase_Consistent()
	 * @model
	 * @generated
	 */
	boolean isConsistent();

	/**
	 * Sets the value of the '{@link com.specmate.model.testspecification.TestCase#isConsistent <em>Consistent</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Consistent</em>' attribute.
	 * @see #isConsistent()
	 * @generated
	 */
	void setConsistent(boolean value);

} // TestCase
