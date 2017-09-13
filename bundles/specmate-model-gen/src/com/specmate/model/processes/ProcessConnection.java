/**
 */
package com.specmate.model.processes;

import com.specmate.model.base.ISpecmateModelObject;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Process Connection</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link com.specmate.model.processes.ProcessConnection#getSource <em>Source</em>}</li>
 *   <li>{@link com.specmate.model.processes.ProcessConnection#getTarget <em>Target</em>}</li>
 * </ul>
 *
 * @see com.specmate.model.processes.ProcessesPackage#getProcessConnection()
 * @model
 * @generated
 */
public interface ProcessConnection extends ISpecmateModelObject {
	/**
	 * Returns the value of the '<em><b>Source</b></em>' reference.
	 * It is bidirectional and its opposite is '{@link com.specmate.model.processes.ProcessNode#getOutgoingConnections <em>Outgoing Connections</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Source</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Source</em>' reference.
	 * @see #setSource(ProcessNode)
	 * @see com.specmate.model.processes.ProcessesPackage#getProcessConnection_Source()
	 * @see com.specmate.model.processes.ProcessNode#getOutgoingConnections
	 * @model opposite="outgoingConnections"
	 * @generated
	 */
	ProcessNode getSource();

	/**
	 * Sets the value of the '{@link com.specmate.model.processes.ProcessConnection#getSource <em>Source</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Source</em>' reference.
	 * @see #getSource()
	 * @generated
	 */
	void setSource(ProcessNode value);

	/**
	 * Returns the value of the '<em><b>Target</b></em>' reference.
	 * It is bidirectional and its opposite is '{@link com.specmate.model.processes.ProcessNode#getIncomingConnections <em>Incoming Connections</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Target</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Target</em>' reference.
	 * @see #setTarget(ProcessNode)
	 * @see com.specmate.model.processes.ProcessesPackage#getProcessConnection_Target()
	 * @see com.specmate.model.processes.ProcessNode#getIncomingConnections
	 * @model opposite="incomingConnections"
	 * @generated
	 */
	ProcessNode getTarget();

	/**
	 * Sets the value of the '{@link com.specmate.model.processes.ProcessConnection#getTarget <em>Target</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Target</em>' reference.
	 * @see #getTarget()
	 * @generated
	 */
	void setTarget(ProcessNode value);

} // ProcessConnection
