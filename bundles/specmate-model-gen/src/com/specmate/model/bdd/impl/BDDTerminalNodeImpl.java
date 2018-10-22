/**
 */
package com.specmate.model.bdd.impl;

import com.specmate.model.bdd.BDDTerminalNode;
import com.specmate.model.bdd.BddPackage;

import org.eclipse.emf.ecore.EClass;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>BDD Terminal Node</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link com.specmate.model.bdd.impl.BDDTerminalNodeImpl#isValue <em>Value</em>}</li>
 * </ul>
 *
 * @generated
 */
public class BDDTerminalNodeImpl extends BDDNodeImpl implements BDDTerminalNode {
	/**
	 * The default value of the '{@link #isValue() <em>Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isValue()
	 * @generated
	 * @ordered
	 */
	protected static final boolean VALUE_EDEFAULT = false;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected BDDTerminalNodeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return BddPackage.Literals.BDD_TERMINAL_NODE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isValue() {
		return (Boolean)eDynamicGet(BddPackage.BDD_TERMINAL_NODE__VALUE, BddPackage.Literals.BDD_TERMINAL_NODE__VALUE, true, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setValue(boolean newValue) {
		eDynamicSet(BddPackage.BDD_TERMINAL_NODE__VALUE, BddPackage.Literals.BDD_TERMINAL_NODE__VALUE, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case BddPackage.BDD_TERMINAL_NODE__VALUE:
				return isValue();
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
			case BddPackage.BDD_TERMINAL_NODE__VALUE:
				setValue((Boolean)newValue);
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
			case BddPackage.BDD_TERMINAL_NODE__VALUE:
				setValue(VALUE_EDEFAULT);
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
			case BddPackage.BDD_TERMINAL_NODE__VALUE:
				return isValue() != VALUE_EDEFAULT;
		}
		return super.eIsSet(featureID);
	}

} //BDDTerminalNodeImpl
