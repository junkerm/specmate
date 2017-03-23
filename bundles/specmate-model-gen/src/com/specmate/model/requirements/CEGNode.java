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
 *   <li>{@link com.specmate.model.requirements.CEGNode#getX <em>X</em>}</li>
 *   <li>{@link com.specmate.model.requirements.CEGNode#getY <em>Y</em>}</li>
 *   <li>{@link com.specmate.model.requirements.CEGNode#getValue <em>Value</em>}</li>
 *   <li>{@link com.specmate.model.requirements.CEGNode#getOperator <em>Operator</em>}</li>
 *   <li>{@link com.specmate.model.requirements.CEGNode#getVariable <em>Variable</em>}</li>
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
	 * The list contents are of type {@link com.specmate.model.requirements.CEGConnection}.
	 * It is bidirectional and its opposite is '{@link com.specmate.model.requirements.CEGConnection#getSource <em>Source</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Outgoing Connections</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Outgoing Connections</em>' reference list.
	 * @see com.specmate.model.requirements.RequirementsPackage#getCEGNode_OutgoingConnections()
	 * @see com.specmate.model.requirements.CEGConnection#getSource
	 * @model opposite="source"
	 * @generated
	 */
	EList<CEGConnection> getOutgoingConnections();

	/**
	 * Returns the value of the '<em><b>Incoming Connection</b></em>' reference list.
	 * The list contents are of type {@link com.specmate.model.requirements.CEGConnection}.
	 * It is bidirectional and its opposite is '{@link com.specmate.model.requirements.CEGConnection#getTarget <em>Target</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Incoming Connection</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Incoming Connection</em>' reference list.
	 * @see com.specmate.model.requirements.RequirementsPackage#getCEGNode_IncomingConnection()
	 * @see com.specmate.model.requirements.CEGConnection#getTarget
	 * @model opposite="target"
	 * @generated
	 */
	EList<CEGConnection> getIncomingConnection();

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
	 * @see com.specmate.model.requirements.RequirementsPackage#getCEGNode_X()
	 * @model
	 * @generated
	 */
	double getX();

	/**
	 * Sets the value of the '{@link com.specmate.model.requirements.CEGNode#getX <em>X</em>}' attribute.
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
	 * @see com.specmate.model.requirements.RequirementsPackage#getCEGNode_Y()
	 * @model
	 * @generated
	 */
	double getY();

	/**
	 * Sets the value of the '{@link com.specmate.model.requirements.CEGNode#getY <em>Y</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Y</em>' attribute.
	 * @see #getY()
	 * @generated
	 */
	void setY(double value);

	/**
	 * Returns the value of the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Value</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Value</em>' attribute.
	 * @see #setValue(String)
	 * @see com.specmate.model.requirements.RequirementsPackage#getCEGNode_Value()
	 * @model
	 * @generated
	 */
	String getValue();

	/**
	 * Sets the value of the '{@link com.specmate.model.requirements.CEGNode#getValue <em>Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Value</em>' attribute.
	 * @see #getValue()
	 * @generated
	 */
	void setValue(String value);

	/**
	 * Returns the value of the '<em><b>Operator</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Operator</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Operator</em>' attribute.
	 * @see #setOperator(String)
	 * @see com.specmate.model.requirements.RequirementsPackage#getCEGNode_Operator()
	 * @model
	 * @generated
	 */
	String getOperator();

	/**
	 * Sets the value of the '{@link com.specmate.model.requirements.CEGNode#getOperator <em>Operator</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Operator</em>' attribute.
	 * @see #getOperator()
	 * @generated
	 */
	void setOperator(String value);

	/**
	 * Returns the value of the '<em><b>Variable</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Variable</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Variable</em>' attribute.
	 * @see #setVariable(String)
	 * @see com.specmate.model.requirements.RequirementsPackage#getCEGNode_Variable()
	 * @model
	 * @generated
	 */
	String getVariable();

	/**
	 * Sets the value of the '{@link com.specmate.model.requirements.CEGNode#getVariable <em>Variable</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Variable</em>' attribute.
	 * @see #getVariable()
	 * @generated
	 */
	void setVariable(String value);

} // CEGNode
