/**
 */
package com.specmate.migration.test.attributerenamed.testmodel.base;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>ITestable</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link com.specmate.migration.test.attributerenamed.testmodel.base.ITestable#isIstested <em>Istested</em>}</li>
 * </ul>
 *
 * @see com.specmate.migration.test.attributerenamed.testmodel.base.BasePackage#getITestable()
 * @model interface="true" abstract="true"
 * @generated
 */
public interface ITestable extends EObject {
	/**
	 * Returns the value of the '<em><b>Istested</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Istested</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Istested</em>' attribute.
	 * @see #setIstested(boolean)
	 * @see com.specmate.migration.test.attributerenamed.testmodel.base.BasePackage#getITestable_Istested()
	 * @model
	 * @generated
	 */
	boolean isIstested();

	/**
	 * Sets the value of the '{@link com.specmate.migration.test.attributerenamed.testmodel.base.ITestable#isIstested <em>Istested</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Istested</em>' attribute.
	 * @see #isIstested()
	 * @generated
	 */
	void setIstested(boolean value);

} // ITestable
