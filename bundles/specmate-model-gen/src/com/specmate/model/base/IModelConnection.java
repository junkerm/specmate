/**
 */
package com.specmate.model.base;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>IModel Connection</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link com.specmate.model.base.IModelConnection#getSource <em>Source</em>}</li>
 *   <li>{@link com.specmate.model.base.IModelConnection#getTarget <em>Target</em>}</li>
 * </ul>
 *
 * @see com.specmate.model.base.BasePackage#getIModelConnection()
 * @model
 * @generated
 */
public interface IModelConnection extends ISpecmateModelObject {
	/**
	 * Returns the value of the '<em><b>Source</b></em>' reference.
	 * It is bidirectional and its opposite is '{@link com.specmate.model.base.IModelNode#getOutgoingConnections <em>Outgoing Connections</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Source</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Source</em>' reference.
	 * @see #setSource(IModelNode)
	 * @see com.specmate.model.base.BasePackage#getIModelConnection_Source()
	 * @see com.specmate.model.base.IModelNode#getOutgoingConnections
	 * @model opposite="outgoingConnections"
	 * @generated
	 */
	IModelNode getSource();

	/**
	 * Sets the value of the '{@link com.specmate.model.base.IModelConnection#getSource <em>Source</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Source</em>' reference.
	 * @see #getSource()
	 * @generated
	 */
	void setSource(IModelNode value);

	/**
	 * Returns the value of the '<em><b>Target</b></em>' reference.
	 * It is bidirectional and its opposite is '{@link com.specmate.model.base.IModelNode#getIncomingConnections <em>Incoming Connections</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Target</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Target</em>' reference.
	 * @see #setTarget(IModelNode)
	 * @see com.specmate.model.base.BasePackage#getIModelConnection_Target()
	 * @see com.specmate.model.base.IModelNode#getIncomingConnections
	 * @model opposite="incomingConnections"
	 * @generated
	 */
	IModelNode getTarget();

	/**
	 * Sets the value of the '{@link com.specmate.model.base.IModelConnection#getTarget <em>Target</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Target</em>' reference.
	 * @see #getTarget()
	 * @generated
	 */
	void setTarget(IModelNode value);

} // IModelConnection
