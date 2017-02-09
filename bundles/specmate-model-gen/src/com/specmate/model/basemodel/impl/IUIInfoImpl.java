/**
 */
package com.specmate.model.basemodel.impl;

import com.specmate.model.basemodel.BasemodelPackage;
import com.specmate.model.basemodel.IUIInfo;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.internal.cdo.CDOObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>IUI Info</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link com.specmate.model.basemodel.impl.IUIInfoImpl#getX <em>X</em>}</li>
 *   <li>{@link com.specmate.model.basemodel.impl.IUIInfoImpl#getY <em>Y</em>}</li>
 * </ul>
 *
 * @generated
 */
public class IUIInfoImpl extends CDOObjectImpl implements IUIInfo {
	/**
	 * The default value of the '{@link #getX() <em>X</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getX()
	 * @generated
	 * @ordered
	 */
	protected static final int X_EDEFAULT = 0;

	/**
	 * The default value of the '{@link #getY() <em>Y</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getY()
	 * @generated
	 * @ordered
	 */
	protected static final int Y_EDEFAULT = 0;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected IUIInfoImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return BasemodelPackage.Literals.IUI_INFO;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected int eStaticFeatureCount() {
		return 0;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getX() {
		return (Integer)eDynamicGet(BasemodelPackage.IUI_INFO__X, BasemodelPackage.Literals.IUI_INFO__X, true, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setX(int newX) {
		eDynamicSet(BasemodelPackage.IUI_INFO__X, BasemodelPackage.Literals.IUI_INFO__X, newX);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getY() {
		return (Integer)eDynamicGet(BasemodelPackage.IUI_INFO__Y, BasemodelPackage.Literals.IUI_INFO__Y, true, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setY(int newY) {
		eDynamicSet(BasemodelPackage.IUI_INFO__Y, BasemodelPackage.Literals.IUI_INFO__Y, newY);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case BasemodelPackage.IUI_INFO__X:
				return getX();
			case BasemodelPackage.IUI_INFO__Y:
				return getY();
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
			case BasemodelPackage.IUI_INFO__X:
				setX((Integer)newValue);
				return;
			case BasemodelPackage.IUI_INFO__Y:
				setY((Integer)newValue);
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
			case BasemodelPackage.IUI_INFO__X:
				setX(X_EDEFAULT);
				return;
			case BasemodelPackage.IUI_INFO__Y:
				setY(Y_EDEFAULT);
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
			case BasemodelPackage.IUI_INFO__X:
				return getX() != X_EDEFAULT;
			case BasemodelPackage.IUI_INFO__Y:
				return getY() != Y_EDEFAULT;
		}
		return super.eIsSet(featureID);
	}

} //IUIInfoImpl
