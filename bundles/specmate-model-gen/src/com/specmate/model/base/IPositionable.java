/**
 */
package com.specmate.model.base;

import org.eclipse.emf.cdo.CDOObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>IPositionable</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link com.specmate.model.base.IPositionable#getPosition <em>Position</em>}</li>
 * </ul>
 *
 * @see com.specmate.model.base.BasePackage#getIPositionable()
 * @model interface="true" abstract="true"
 * @extends CDOObject
 * @generated
 */
public interface IPositionable extends CDOObject {
	/**
	 * Returns the value of the '<em><b>Position</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Position</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Position</em>' attribute.
	 * @see #setPosition(int)
	 * @see com.specmate.model.base.BasePackage#getIPositionable_Position()
	 * @model
	 * @generated
	 */
	int getPosition();

	/**
	 * Sets the value of the '{@link com.specmate.model.base.IPositionable#getPosition <em>Position</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Position</em>' attribute.
	 * @see #getPosition()
	 * @generated
	 */
	void setPosition(int value);

} // IPositionable
