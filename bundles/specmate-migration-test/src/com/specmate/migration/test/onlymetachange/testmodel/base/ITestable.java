/**
 */
package com.specmate.migration.test.onlymetachange.testmodel.base;

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
 *   <li>{@link com.specmate.migration.test.onlymetachange.testmodel.base.ITestable#isTested <em>Tested</em>}</li>
 * </ul>
 *
 * @see com.specmate.migration.test.onlymetachange.testmodel.base.BasePackage#getITestable()
 * @model interface="true" abstract="true"
 * @generated
 */
public interface ITestable extends EObject {
	/**
	 * Returns the value of the '<em><b>Tested</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Tested</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Tested</em>' attribute.
	 * @see #setTested(boolean)
	 * @see com.specmate.migration.test.onlymetachange.testmodel.base.BasePackage#getITestable_Tested()
	 * @model
	 * @generated
	 */
	boolean isTested();

	/**
	 * Sets the value of the '{@link com.specmate.migration.test.onlymetachange.testmodel.base.ITestable#isTested <em>Tested</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Tested</em>' attribute.
	 * @see #isTested()
	 * @generated
	 */
	void setTested(boolean value);

} // ITestable
