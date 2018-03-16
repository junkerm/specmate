/**
 */
package com.specmate.usermodel;

import org.eclipse.emf.cdo.CDOObject;

import org.eclipse.emf.common.util.EList;

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
 *   <li>{@link com.specmate.usermodel.User#getName <em>Name</em>}</li>
 *   <li>{@link com.specmate.usermodel.User#getPasswordHash <em>Password Hash</em>}</li>
 *   <li>{@link com.specmate.usermodel.User#getSalt <em>Salt</em>}</li>
 * </ul>
 *
 * @see com.specmate.usermodel.UsermodelPackage#getUser()
 * @model
 * @extends CDOObject
 * @generated
 */
public interface User extends CDOObject {
	/**
	 * Returns the value of the '<em><b>Allowed Urls</b></em>' attribute list.
	 * The list contents are of type {@link java.lang.String}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Allowed Urls</em>' attribute list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Allowed Urls</em>' attribute list.
	 * @see com.specmate.usermodel.UsermodelPackage#getUser_AllowedUrls()
	 * @model
	 * @generated
	 */
	EList<String> getAllowedUrls();

	/**
	 * Returns the value of the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Name</em>' attribute.
	 * @see #setName(String)
	 * @see com.specmate.usermodel.UsermodelPackage#getUser_Name()
	 * @model
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link com.specmate.usermodel.User#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

	/**
	 * Returns the value of the '<em><b>Password Hash</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Password Hash</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Password Hash</em>' attribute.
	 * @see #setPasswordHash(String)
	 * @see com.specmate.usermodel.UsermodelPackage#getUser_PasswordHash()
	 * @model
	 * @generated
	 */
	String getPasswordHash();

	/**
	 * Sets the value of the '{@link com.specmate.usermodel.User#getPasswordHash <em>Password Hash</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Password Hash</em>' attribute.
	 * @see #getPasswordHash()
	 * @generated
	 */
	void setPasswordHash(String value);

	/**
	 * Returns the value of the '<em><b>Salt</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Salt</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Salt</em>' attribute.
	 * @see #setSalt(String)
	 * @see com.specmate.usermodel.UsermodelPackage#getUser_Salt()
	 * @model
	 * @generated
	 */
	String getSalt();

	/**
	 * Sets the value of the '{@link com.specmate.usermodel.User#getSalt <em>Salt</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Salt</em>' attribute.
	 * @see #getSalt()
	 * @generated
	 */
	void setSalt(String value);

} // User
