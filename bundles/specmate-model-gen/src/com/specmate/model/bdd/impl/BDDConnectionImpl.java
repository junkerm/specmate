/**
 */
package com.specmate.model.bdd.impl;

import com.specmate.model.base.impl.IModelConnectionImpl;

import com.specmate.model.bdd.BDDConnection;
import com.specmate.model.bdd.BddPackage;

import org.eclipse.emf.ecore.EClass;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>BDD Connection</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link com.specmate.model.bdd.impl.BDDConnectionImpl#isNegate <em>Negate</em>}</li>
 * </ul>
 *
 * @generated
 */
public class BDDConnectionImpl extends IModelConnectionImpl implements BDDConnection {
	/**
	 * The default value of the '{@link #isNegate() <em>Negate</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isNegate()
	 * @generated
	 * @ordered
	 */
	protected static final boolean NEGATE_EDEFAULT = false;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected BDDConnectionImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return BddPackage.Literals.BDD_CONNECTION;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isNegate() {
		return (Boolean)eDynamicGet(BddPackage.BDD_CONNECTION__NEGATE, BddPackage.Literals.BDD_CONNECTION__NEGATE, true, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setNegate(boolean newNegate) {
		eDynamicSet(BddPackage.BDD_CONNECTION__NEGATE, BddPackage.Literals.BDD_CONNECTION__NEGATE, newNegate);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case BddPackage.BDD_CONNECTION__NEGATE:
				return isNegate();
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
			case BddPackage.BDD_CONNECTION__NEGATE:
				setNegate((Boolean)newValue);
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
			case BddPackage.BDD_CONNECTION__NEGATE:
				setNegate(NEGATE_EDEFAULT);
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
			case BddPackage.BDD_CONNECTION__NEGATE:
				return isNegate() != NEGATE_EDEFAULT;
		}
		return super.eIsSet(featureID);
	}

} //BDDConnectionImpl
