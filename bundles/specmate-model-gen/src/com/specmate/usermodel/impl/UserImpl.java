/**
 */
package com.specmate.usermodel.impl;

import com.specmate.usermodel.User;
import com.specmate.usermodel.UsermodelPackage;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.internal.cdo.CDOObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>User</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link com.specmate.usermodel.impl.UserImpl#getAllowedUrls <em>Allowed Urls</em>}</li>
 * </ul>
 *
 * @generated
 */
public class UserImpl extends CDOObjectImpl implements User {
	/**
	 * The default value of the '{@link #getAllowedUrls() <em>Allowed Urls</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAllowedUrls()
	 * @generated
	 * @ordered
	 */
	protected static final String ALLOWED_URLS_EDEFAULT = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected UserImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return UsermodelPackage.Literals.USER;
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
	public String getAllowedUrls() {
		return (String)eDynamicGet(UsermodelPackage.USER__ALLOWED_URLS, UsermodelPackage.Literals.USER__ALLOWED_URLS, true, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setAllowedUrls(String newAllowedUrls) {
		eDynamicSet(UsermodelPackage.USER__ALLOWED_URLS, UsermodelPackage.Literals.USER__ALLOWED_URLS, newAllowedUrls);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case UsermodelPackage.USER__ALLOWED_URLS:
				return getAllowedUrls();
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
			case UsermodelPackage.USER__ALLOWED_URLS:
				setAllowedUrls((String)newValue);
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
			case UsermodelPackage.USER__ALLOWED_URLS:
				setAllowedUrls(ALLOWED_URLS_EDEFAULT);
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
			case UsermodelPackage.USER__ALLOWED_URLS:
				return ALLOWED_URLS_EDEFAULT == null ? getAllowedUrls() != null : !ALLOWED_URLS_EDEFAULT.equals(getAllowedUrls());
		}
		return super.eIsSet(featureID);
	}

} //UserImpl
