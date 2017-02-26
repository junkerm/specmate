/**
 */
package com.specmate.model.requirements;

import com.specmate.model.base.ISpecmateModelObject;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>CEG Node</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link com.specmate.model.requirements.CEGNode#getType <em>Type</em>}</li>
 *   <li>{@link com.specmate.model.requirements.CEGNode#getOutgoingConnections <em>Outgoing Connections</em>}</li>
 *   <li>{@link com.specmate.model.requirements.CEGNode#getIncomingConnection <em>Incoming Connection</em>}</li>
 * </ul>
 *
 * @see com.specmate.model.requirements.RequirementsPackage#getCEGNode()
 * @model
 * @generated
 */
public interface CEGNode extends ISpecmateModelObject {
	/**
	 * Returns the value of the '<em><b>Type</b></em>' attribute.
	 * The literals are from the enumeration {@link com.specmate.model.requirements.NodeType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Type</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Type</em>' attribute.
	 * @see com.specmate.model.requirements.NodeType
	 * @see #setType(NodeType)
	 * @see com.specmate.model.requirements.RequirementsPackage#getCEGNode_Type()
	 * @model
	 * @generated
	 */
	NodeType getType();

	/**
	 * Sets the value of the '{@link com.specmate.model.requirements.CEGNode#getType <em>Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Type</em>' attribute.
	 * @see com.specmate.model.requirements.NodeType
	 * @see #getType()
	 * @generated
	 */
	void setType(NodeType value);

	/**
	 * Returns the value of the '<em><b>Outgoing Connections</b></em>' reference list.
	 * The list contents are of type {@link com.specmate.model.requirements.CEGConection}.
	 * It is bidirectional and its opposite is '{@link com.specmate.model.requirements.CEGConection#getSource <em>Source</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Outgoing Connections</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Outgoing Connections</em>' reference list.
	 * @see com.specmate.model.requirements.RequirementsPackage#getCEGNode_OutgoingConnections()
	 * @see com.specmate.model.requirements.CEGConection#getSource
	 * @model opposite="source"
	 * @generated
	 */
	EList<CEGConection> getOutgoingConnections();

	/**
	 * Returns the value of the '<em><b>Incoming Connection</b></em>' reference list.
	 * The list contents are of type {@link com.specmate.model.requirements.CEGConection}.
	 * It is bidirectional and its opposite is '{@link com.specmate.model.requirements.CEGConection#getTarget <em>Target</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Incoming Connection</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Incoming Connection</em>' reference list.
	 * @see com.specmate.model.requirements.RequirementsPackage#getCEGNode_IncomingConnection()
	 * @see com.specmate.model.requirements.CEGConection#getTarget
	 * @model opposite="target"
	 * @generated
	 */
	EList<CEGConection> getIncomingConnection();

} // CEGNode
