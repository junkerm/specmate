/**
 */
package com.specmate.model.processes;

import com.specmate.model.base.IModelConnection;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Process Connection</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link com.specmate.model.processes.ProcessConnection#getCondition <em>Condition</em>}</li>
 * </ul>
 *
 * @see com.specmate.model.processes.ProcessesPackage#getProcessConnection()
 * @model
 * @generated
 */
public interface ProcessConnection extends IModelConnection {

	/**
	 * Returns the value of the '<em><b>Condition</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Condition</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Condition</em>' attribute.
	 * @see #setCondition(String)
	 * @see com.specmate.model.processes.ProcessesPackage#getProcessConnection_Condition()
	 * @model annotation="http://specmate.com/form_meta shortDesc='Condition' longDesc='The condition the variable has to fulfil' required='true' type='text' position='2'"
	 * @generated
	 */
	String getCondition();

	/**
	 * Sets the value of the '{@link com.specmate.model.processes.ProcessConnection#getCondition <em>Condition</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Condition</em>' attribute.
	 * @see #getCondition()
	 * @generated
	 */
	void setCondition(String value);
} // ProcessConnection
