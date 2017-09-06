/**
 */
package com.specmate.model.base;

import org.eclipse.emf.cdo.CDOObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>IExternal</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link com.specmate.model.base.IExternal#getExtId <em>Ext Id</em>}</li>
 *   <li>{@link com.specmate.model.base.IExternal#getExtId2 <em>Ext Id2</em>}</li>
 *   <li>{@link com.specmate.model.base.IExternal#getSource <em>Source</em>}</li>
 * </ul>
 *
 * @see com.specmate.model.base.BasePackage#getIExternal()
 * @model
 * @extends CDOObject
 * @generated
 */
public interface IExternal extends CDOObject {
	/**
	 * Returns the value of the '<em><b>Ext Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Ext Id</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Ext Id</em>' attribute.
	 * @see #setExtId(String)
	 * @see com.specmate.model.base.BasePackage#getIExternal_ExtId()
	 * @model
	 * @generated
	 */
	String getExtId();

	/**
	 * Sets the value of the '{@link com.specmate.model.base.IExternal#getExtId <em>Ext Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Ext Id</em>' attribute.
	 * @see #getExtId()
	 * @generated
	 */
	void setExtId(String value);

	/**
	 * Returns the value of the '<em><b>Ext Id2</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Ext Id2</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Ext Id2</em>' attribute.
	 * @see #setExtId2(String)
	 * @see com.specmate.model.base.BasePackage#getIExternal_ExtId2()
	 * @model
	 * @generated
	 */
	String getExtId2();

	/**
	 * Sets the value of the '{@link com.specmate.model.base.IExternal#getExtId2 <em>Ext Id2</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Ext Id2</em>' attribute.
	 * @see #getExtId2()
	 * @generated
	 */
	void setExtId2(String value);

	/**
	 * Returns the value of the '<em><b>Source</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Source</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Source</em>' attribute.
	 * @see #setSource(String)
	 * @see com.specmate.model.base.BasePackage#getIExternal_Source()
	 * @model
	 * @generated
	 */
	String getSource();

	/**
	 * Sets the value of the '{@link com.specmate.model.base.IExternal#getSource <em>Source</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Source</em>' attribute.
	 * @see #getSource()
	 * @generated
	 */
	void setSource(String value);

} // IExternal
