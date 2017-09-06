/**
 */
package com.specmate.model.testspecification;

import com.specmate.model.base.IContainer;
import com.specmate.model.base.IExternal;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Test Procedure</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link com.specmate.model.testspecification.TestProcedure#isIsRegressionTest <em>Is Regression Test</em>}</li>
 * </ul>
 *
 * @see com.specmate.model.testspecification.TestspecificationPackage#getTestProcedure()
 * @model
 * @generated
 */
<<<<<<< HEAD
public interface TestProcedure extends IContainer, IExternal {
=======
public interface TestProcedure extends IContainer {

	/**
	 * Returns the value of the '<em><b>Is Regression Test</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Is Regression Test</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Is Regression Test</em>' attribute.
	 * @see #setIsRegressionTest(boolean)
	 * @see com.specmate.model.testspecification.TestspecificationPackage#getTestProcedure_IsRegressionTest()
	 * @model annotation="http://specmate.com/form_meta shortDesc='Regression Test' type='checkbox' position='3' longDesc=''"
	 * @generated
	 */
	boolean isIsRegressionTest();

	/**
	 * Sets the value of the '{@link com.specmate.model.testspecification.TestProcedure#isIsRegressionTest <em>Is Regression Test</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Is Regression Test</em>' attribute.
	 * @see #isIsRegressionTest()
	 * @generated
	 */
	void setIsRegressionTest(boolean value);
>>>>>>> refs/remotes/origin/develop
} // TestProcedure
