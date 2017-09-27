/**
 */
package com.specmate.model.base;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>ISpecmate Positionable Model Object</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link com.specmate.model.base.ISpecmatePositionableModelObject#getX <em>X</em>}</li>
 *   <li>{@link com.specmate.model.base.ISpecmatePositionableModelObject#getY <em>Y</em>}</li>
 * </ul>
 *
 * @see com.specmate.model.base.BasePackage#getISpecmatePositionableModelObject()
 * @model interface="true" abstract="true"
 * @generated
 */
public interface ISpecmatePositionableModelObject extends ISpecmateModelObject {
	/**
	 * Returns the value of the '<em><b>X</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>X</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>X</em>' attribute.
	 * @see #setX(double)
	 * @see com.specmate.model.base.BasePackage#getISpecmatePositionableModelObject_X()
	 * @model
	 * @generated
	 */
	double getX();

	/**
	 * Sets the value of the '{@link com.specmate.model.base.ISpecmatePositionableModelObject#getX <em>X</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>X</em>' attribute.
	 * @see #getX()
	 * @generated
	 */
	void setX(double value);

	/**
	 * Returns the value of the '<em><b>Y</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Y</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Y</em>' attribute.
	 * @see #setY(double)
	 * @see com.specmate.model.base.BasePackage#getISpecmatePositionableModelObject_Y()
	 * @model
	 * @generated
	 */
	double getY();

	/**
	 * Sets the value of the '{@link com.specmate.model.base.ISpecmatePositionableModelObject#getY <em>Y</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Y</em>' attribute.
	 * @see #getY()
	 * @generated
	 */
	void setY(double value);

} // ISpecmatePositionableModelObject
