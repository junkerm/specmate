/**
 */
package com.specmate.model.bdd;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>BDD No Terminal Node</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link com.specmate.model.bdd.BDDNoTerminalNode#getVariable <em>Variable</em>}</li>
 *   <li>{@link com.specmate.model.bdd.BDDNoTerminalNode#getCondition <em>Condition</em>}</li>
 * </ul>
 *
 * @see com.specmate.model.bdd.BddPackage#getBDDNoTerminalNode()
 * @model
 * @generated
 */
public interface BDDNoTerminalNode extends BDDNode {
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
	 * @see com.specmate.model.bdd.BddPackage#getBDDNoTerminalNode_Variable()
	 * @model
	 * @generated
	 */
	String getVariable();

	/**
	 * Sets the value of the '{@link com.specmate.model.bdd.BDDNoTerminalNode#getVariable <em>Variable</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Variable</em>' attribute.
	 * @see #getVariable()
	 * @generated
	 */
	void setVariable(String value);

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
	 * @see com.specmate.model.bdd.BddPackage#getBDDNoTerminalNode_Condition()
	 * @model
	 * @generated
	 */
	String getCondition();

	/**
	 * Sets the value of the '{@link com.specmate.model.bdd.BDDNoTerminalNode#getCondition <em>Condition</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Condition</em>' attribute.
	 * @see #getCondition()
	 * @generated
	 */
	void setCondition(String value);

} // BDDNoTerminalNode
