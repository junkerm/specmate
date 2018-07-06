/**
 */
package com.specmate.model.batch;

import com.specmate.model.base.IContainer;

import org.eclipse.emf.cdo.CDOObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Operation</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link com.specmate.model.batch.Operation#getType <em>Type</em>}</li>
 *   <li>{@link com.specmate.model.batch.Operation#getTarget <em>Target</em>}</li>
 *   <li>{@link com.specmate.model.batch.Operation#getValue <em>Value</em>}</li>
 * </ul>
 *
 * @see com.specmate.model.batch.BatchPackage#getOperation()
 * @model
 * @extends CDOObject
 * @generated
 */
public interface Operation extends CDOObject {
	/**
	 * Returns the value of the '<em><b>Type</b></em>' attribute.
	 * The literals are from the enumeration {@link com.specmate.model.batch.OperationType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Type</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Type</em>' attribute.
	 * @see com.specmate.model.batch.OperationType
	 * @see #setType(OperationType)
	 * @see com.specmate.model.batch.BatchPackage#getOperation_Type()
	 * @model
	 * @generated
	 */
	OperationType getType();

	/**
	 * Sets the value of the '{@link com.specmate.model.batch.Operation#getType <em>Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Type</em>' attribute.
	 * @see com.specmate.model.batch.OperationType
	 * @see #getType()
	 * @generated
	 */
	void setType(OperationType value);

	/**
	 * Returns the value of the '<em><b>Target</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Target</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Target</em>' reference.
	 * @see #setTarget(IContainer)
	 * @see com.specmate.model.batch.BatchPackage#getOperation_Target()
	 * @model
	 * @generated
	 */
	IContainer getTarget();

	/**
	 * Sets the value of the '{@link com.specmate.model.batch.Operation#getTarget <em>Target</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Target</em>' reference.
	 * @see #getTarget()
	 * @generated
	 */
	void setTarget(IContainer value);

	/**
	 * Returns the value of the '<em><b>Value</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Value</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Value</em>' containment reference.
	 * @see #setValue(IContainer)
	 * @see com.specmate.model.batch.BatchPackage#getOperation_Value()
	 * @model containment="true"
	 * @generated
	 */
	IContainer getValue();

	/**
	 * Sets the value of the '{@link com.specmate.model.batch.Operation#getValue <em>Value</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Value</em>' containment reference.
	 * @see #getValue()
	 * @generated
	 */
	void setValue(IContainer value);

} // Operation
