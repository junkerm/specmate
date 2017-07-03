/**
 */
package com.specmate.usermodel;

import org.eclipse.emf.cdo.CDOObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>User</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link com.specmate.usermodel.User#getAllowedUrls <em>Allowed Urls</em>}</li>
 * </ul>
 *
 * @see com.specmate.usermodel.UsermodelPackage#getUser()
 * @model
 * @extends CDOObject
 * @generated
 */
public interface User extends CDOObject {
	/**
	 * Returns the value of the '<em><b>Allowed Urls</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Allowed Urls</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Allowed Urls</em>' attribute.
	 * @see #setAllowedUrls(String)
	 * @see com.specmate.usermodel.UsermodelPackage#getUser_AllowedUrls()
	 * @model
	 * @generated
	 */
	String getAllowedUrls();

	/**
	 * Sets the value of the '{@link com.specmate.usermodel.User#getAllowedUrls <em>Allowed Urls</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Allowed Urls</em>' attribute.
	 * @see #getAllowedUrls()
	 * @generated
	 */
	void setAllowedUrls(String value);

} // User
