/**
 */
package com.specmate.model.requirements.impl;

import com.specmate.model.base.impl.IModelConnectionImpl;

import com.specmate.model.requirements.CEGConnection;
import com.specmate.model.requirements.RequirementsPackage;

import org.eclipse.emf.ecore.EClass;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>CEG Connection</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link com.specmate.model.requirements.impl.CEGConnectionImpl#isNegate <em>Negate</em>}</li>
 * </ul>
 *
 * @generated
 */
public class CEGConnectionImpl extends IModelConnectionImpl implements CEGConnection {
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
	protected CEGConnectionImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return RequirementsPackage.Literals.CEG_CONNECTION;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isNegate() {
		return (Boolean)eDynamicGet(RequirementsPackage.CEG_CONNECTION__NEGATE, RequirementsPackage.Literals.CEG_CONNECTION__NEGATE, true, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setNegate(boolean newNegate) {
		eDynamicSet(RequirementsPackage.CEG_CONNECTION__NEGATE, RequirementsPackage.Literals.CEG_CONNECTION__NEGATE, newNegate);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case RequirementsPackage.CEG_CONNECTION__NEGATE:
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
			case RequirementsPackage.CEG_CONNECTION__NEGATE:
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
			case RequirementsPackage.CEG_CONNECTION__NEGATE:
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
			case RequirementsPackage.CEG_CONNECTION__NEGATE:
				return isNegate() != NEGATE_EDEFAULT;
		}
		return super.eIsSet(featureID);
	}

} //CEGConnectionImpl
