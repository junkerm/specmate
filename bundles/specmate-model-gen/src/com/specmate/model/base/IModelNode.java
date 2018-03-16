/**
 */
package com.specmate.model.base;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>IModel Node</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link com.specmate.model.base.IModelNode#getOutgoingConnections <em>Outgoing Connections</em>}</li>
 *   <li>{@link com.specmate.model.base.IModelNode#getIncomingConnections <em>Incoming Connections</em>}</li>
 * </ul>
 *
 * @see com.specmate.model.base.BasePackage#getIModelNode()
 * @model
 * @generated
 */
public interface IModelNode extends ISpecmatePositionableModelObject {
	/**
	 * Returns the value of the '<em><b>Outgoing Connections</b></em>' reference list.
	 * The list contents are of type {@link com.specmate.model.base.IModelConnection}.
	 * It is bidirectional and its opposite is '{@link com.specmate.model.base.IModelConnection#getSource <em>Source</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Outgoing Connections</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Outgoing Connections</em>' reference list.
	 * @see com.specmate.model.base.BasePackage#getIModelNode_OutgoingConnections()
	 * @see com.specmate.model.base.IModelConnection#getSource
	 * @model opposite="source"
	 * @generated
	 */
	EList<IModelConnection> getOutgoingConnections();

	/**
	 * Returns the value of the '<em><b>Incoming Connections</b></em>' reference list.
	 * The list contents are of type {@link com.specmate.model.base.IModelConnection}.
	 * It is bidirectional and its opposite is '{@link com.specmate.model.base.IModelConnection#getTarget <em>Target</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Incoming Connections</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Incoming Connections</em>' reference list.
	 * @see com.specmate.model.base.BasePackage#getIModelNode_IncomingConnections()
	 * @see com.specmate.model.base.IModelConnection#getTarget
	 * @model opposite="target"
	 * @generated
	 */
	EList<IModelConnection> getIncomingConnections();

} // IModelNode
