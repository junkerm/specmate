/**
 */
package com.specmate.model.processes;

import com.specmate.model.base.ISpecmatePositionableModelObject;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Process Node</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link com.specmate.model.processes.ProcessNode#getOutgoingConnections <em>Outgoing Connections</em>}</li>
 *   <li>{@link com.specmate.model.processes.ProcessNode#getIncomingConnections <em>Incoming Connections</em>}</li>
 * </ul>
 *
 * @see com.specmate.model.processes.ProcessesPackage#getProcessNode()
 * @model abstract="true"
 * @generated
 */
public interface ProcessNode extends ISpecmatePositionableModelObject {
	/**
	 * Returns the value of the '<em><b>Outgoing Connections</b></em>' reference list.
	 * The list contents are of type {@link com.specmate.model.processes.ProcessConnection}.
	 * It is bidirectional and its opposite is '{@link com.specmate.model.processes.ProcessConnection#getSource <em>Source</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Outgoing Connections</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Outgoing Connections</em>' reference list.
	 * @see com.specmate.model.processes.ProcessesPackage#getProcessNode_OutgoingConnections()
	 * @see com.specmate.model.processes.ProcessConnection#getSource
	 * @model opposite="source"
	 * @generated
	 */
	EList<ProcessConnection> getOutgoingConnections();

	/**
	 * Returns the value of the '<em><b>Incoming Connections</b></em>' reference list.
	 * The list contents are of type {@link com.specmate.model.processes.ProcessConnection}.
	 * It is bidirectional and its opposite is '{@link com.specmate.model.processes.ProcessConnection#getTarget <em>Target</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Incoming Connections</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Incoming Connections</em>' reference list.
	 * @see com.specmate.model.processes.ProcessesPackage#getProcessNode_IncomingConnections()
	 * @see com.specmate.model.processes.ProcessConnection#getTarget
	 * @model opposite="target"
	 * @generated
	 */
	EList<ProcessConnection> getIncomingConnections();

} // ProcessNode
