/**
 */
package com.specmate.model.base;

import org.eclipse.emf.cdo.CDOObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>INamed</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link com.specmate.model.base.INamed#getName <em>Name</em>}</li>
 * </ul>
 *
 * @see com.specmate.model.base.BasePackage#getINamed()
 * @model interface="true" abstract="true"
 * @extends CDOObject
 * @generated
 */
public interface INamed extends CDOObject {
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
	 * @see com.specmate.model.base.BasePackage#getINamed_Name()
	 * @model annotation="http://specmate.com/form_meta shortDesc='Name' longDesc='' required='true' type='text' position='0' allowedPattern='^[^,;|]*$'"
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link com.specmate.model.base.INamed#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

} // INamed
