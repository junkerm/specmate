/**
 */
package com.specmate.usermodel;

import org.eclipse.emf.cdo.CDOObject;
import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>User Session</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link com.specmate.usermodel.UserSession#getId <em>Id</em>}</li>
 *   <li>{@link com.specmate.usermodel.UserSession#getAllowedPathPattern <em>Allowed Path Pattern</em>}</li>
 *   <li>{@link com.specmate.usermodel.UserSession#getUserName <em>User Name</em>}</li>
 *   <li>{@link com.specmate.usermodel.UserSession#getLastActive <em>Last Active</em>}</li>
 *   <li>{@link com.specmate.usermodel.UserSession#getSourceSystem <em>Source System</em>}</li>
 *   <li>{@link com.specmate.usermodel.UserSession#getTargetSystem <em>Target System</em>}</li>
 *   <li>{@link com.specmate.usermodel.UserSession#getLibraryFolders <em>Library Folders</em>}</li>
 *   <li>{@link com.specmate.usermodel.UserSession#isIsDeleted <em>Is Deleted</em>}</li>
 * </ul>
 *
 * @see com.specmate.usermodel.UsermodelPackage#getUserSession()
 * @model
 * @extends CDOObject
 * @generated
 */
public interface UserSession extends CDOObject {
	/**
	 * Returns the value of the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Id</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Id</em>' attribute.
	 * @see #setId(String)
	 * @see com.specmate.usermodel.UsermodelPackage#getUserSession_Id()
	 * @model
	 * @generated
	 */
	String getId();

	/**
	 * Sets the value of the '{@link com.specmate.usermodel.UserSession#getId <em>Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Id</em>' attribute.
	 * @see #getId()
	 * @generated
	 */
	void setId(String value);

	/**
	 * Returns the value of the '<em><b>Allowed Path Pattern</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Allowed Path Pattern</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Allowed Path Pattern</em>' attribute.
	 * @see #setAllowedPathPattern(String)
	 * @see com.specmate.usermodel.UsermodelPackage#getUserSession_AllowedPathPattern()
	 * @model
	 * @generated
	 */
	String getAllowedPathPattern();

	/**
	 * Sets the value of the '{@link com.specmate.usermodel.UserSession#getAllowedPathPattern <em>Allowed Path Pattern</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Allowed Path Pattern</em>' attribute.
	 * @see #getAllowedPathPattern()
	 * @generated
	 */
	void setAllowedPathPattern(String value);

	/**
	 * Returns the value of the '<em><b>User Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>User Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>User Name</em>' attribute.
	 * @see #setUserName(String)
	 * @see com.specmate.usermodel.UsermodelPackage#getUserSession_UserName()
	 * @model
	 * @generated
	 */
	String getUserName();

	/**
	 * Sets the value of the '{@link com.specmate.usermodel.UserSession#getUserName <em>User Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>User Name</em>' attribute.
	 * @see #getUserName()
	 * @generated
	 */
	void setUserName(String value);

	/**
	 * Returns the value of the '<em><b>Last Active</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Last Active</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Last Active</em>' attribute.
	 * @see #setLastActive(long)
	 * @see com.specmate.usermodel.UsermodelPackage#getUserSession_LastActive()
	 * @model
	 * @generated
	 */
	long getLastActive();

	/**
	 * Sets the value of the '{@link com.specmate.usermodel.UserSession#getLastActive <em>Last Active</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Last Active</em>' attribute.
	 * @see #getLastActive()
	 * @generated
	 */
	void setLastActive(long value);

	/**
	 * Returns the value of the '<em><b>Source System</b></em>' attribute.
	 * The literals are from the enumeration {@link com.specmate.usermodel.AccessRights}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Source System</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Source System</em>' attribute.
	 * @see com.specmate.usermodel.AccessRights
	 * @see #setSourceSystem(AccessRights)
	 * @see com.specmate.usermodel.UsermodelPackage#getUserSession_SourceSystem()
	 * @model
	 * @generated
	 */
	AccessRights getSourceSystem();

	/**
	 * Sets the value of the '{@link com.specmate.usermodel.UserSession#getSourceSystem <em>Source System</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Source System</em>' attribute.
	 * @see com.specmate.usermodel.AccessRights
	 * @see #getSourceSystem()
	 * @generated
	 */
	void setSourceSystem(AccessRights value);

	/**
	 * Returns the value of the '<em><b>Target System</b></em>' attribute.
	 * The literals are from the enumeration {@link com.specmate.usermodel.AccessRights}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Target System</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Target System</em>' attribute.
	 * @see com.specmate.usermodel.AccessRights
	 * @see #setTargetSystem(AccessRights)
	 * @see com.specmate.usermodel.UsermodelPackage#getUserSession_TargetSystem()
	 * @model
	 * @generated
	 */
	AccessRights getTargetSystem();

	/**
	 * Sets the value of the '{@link com.specmate.usermodel.UserSession#getTargetSystem <em>Target System</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Target System</em>' attribute.
	 * @see com.specmate.usermodel.AccessRights
	 * @see #getTargetSystem()
	 * @generated
	 */
	void setTargetSystem(AccessRights value);

	/**
	 * Returns the value of the '<em><b>Library Folders</b></em>' attribute list.
	 * The list contents are of type {@link java.lang.String}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Library Folders</em>' attribute list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Library Folders</em>' attribute list.
	 * @see com.specmate.usermodel.UsermodelPackage#getUserSession_LibraryFolders()
	 * @model
	 * @generated
	 */
	EList<String> getLibraryFolders();

	/**
	 * Returns the value of the '<em><b>Is Deleted</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Is Deleted</em>' attribute.
	 * @see #setIsDeleted(boolean)
	 * @see com.specmate.usermodel.UsermodelPackage#getUserSession_IsDeleted()
	 * @model
	 * @generated
	 */
	boolean isIsDeleted();

	/**
	 * Sets the value of the '{@link com.specmate.usermodel.UserSession#isIsDeleted <em>Is Deleted</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Is Deleted</em>' attribute.
	 * @see #isIsDeleted()
	 * @generated
	 */
	void setIsDeleted(boolean value);

} // UserSession
