/**
 */
package com.specmate.model.bdd.impl;

import com.specmate.model.bdd.BDDNoTerminalNode;
import com.specmate.model.bdd.BddPackage;

import org.eclipse.emf.ecore.EClass;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>BDD No Terminal Node</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link com.specmate.model.bdd.impl.BDDNoTerminalNodeImpl#getVariable <em>Variable</em>}</li>
 *   <li>{@link com.specmate.model.bdd.impl.BDDNoTerminalNodeImpl#getCondition <em>Condition</em>}</li>
 * </ul>
 *
 * @generated
 */
public class BDDNoTerminalNodeImpl extends BDDNodeImpl implements BDDNoTerminalNode {
	/**
	 * The default value of the '{@link #getVariable() <em>Variable</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getVariable()
	 * @generated
	 * @ordered
	 */
	protected static final String VARIABLE_EDEFAULT = null;

	/**
	 * The default value of the '{@link #getCondition() <em>Condition</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCondition()
	 * @generated
	 * @ordered
	 */
	protected static final String CONDITION_EDEFAULT = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected BDDNoTerminalNodeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return BddPackage.Literals.BDD_NO_TERMINAL_NODE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getVariable() {
		return (String)eDynamicGet(BddPackage.BDD_NO_TERMINAL_NODE__VARIABLE, BddPackage.Literals.BDD_NO_TERMINAL_NODE__VARIABLE, true, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setVariable(String newVariable) {
		eDynamicSet(BddPackage.BDD_NO_TERMINAL_NODE__VARIABLE, BddPackage.Literals.BDD_NO_TERMINAL_NODE__VARIABLE, newVariable);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getCondition() {
		return (String)eDynamicGet(BddPackage.BDD_NO_TERMINAL_NODE__CONDITION, BddPackage.Literals.BDD_NO_TERMINAL_NODE__CONDITION, true, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setCondition(String newCondition) {
		eDynamicSet(BddPackage.BDD_NO_TERMINAL_NODE__CONDITION, BddPackage.Literals.BDD_NO_TERMINAL_NODE__CONDITION, newCondition);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case BddPackage.BDD_NO_TERMINAL_NODE__VARIABLE:
				return getVariable();
			case BddPackage.BDD_NO_TERMINAL_NODE__CONDITION:
				return getCondition();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case BddPackage.BDD_NO_TERMINAL_NODE__VARIABLE:
				setVariable((String)newValue);
				return;
			case BddPackage.BDD_NO_TERMINAL_NODE__CONDITION:
				setCondition((String)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case BddPackage.BDD_NO_TERMINAL_NODE__VARIABLE:
				setVariable(VARIABLE_EDEFAULT);
				return;
			case BddPackage.BDD_NO_TERMINAL_NODE__CONDITION:
				setCondition(CONDITION_EDEFAULT);
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case BddPackage.BDD_NO_TERMINAL_NODE__VARIABLE:
				return VARIABLE_EDEFAULT == null ? getVariable() != null : !VARIABLE_EDEFAULT.equals(getVariable());
			case BddPackage.BDD_NO_TERMINAL_NODE__CONDITION:
				return CONDITION_EDEFAULT == null ? getCondition() != null : !CONDITION_EDEFAULT.equals(getCondition());
		}
		return super.eIsSet(featureID);
	}

} //BDDNoTerminalNodeImpl
