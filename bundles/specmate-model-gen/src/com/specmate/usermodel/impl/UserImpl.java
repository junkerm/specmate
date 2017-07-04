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
 *   <li>{@link com.specmate.usermodel.impl.UserImpl#getName <em>Name</em>}</li>
 *   <li>{@link com.specmate.usermodel.impl.UserImpl#getPasswordHash <em>Password Hash</em>}</li>
 *   <li>{@link com.specmate.usermodel.impl.UserImpl#getSalt <em>Salt</em>}</li>
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
	 * The default value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected static final String NAME_EDEFAULT = null;
	/**
	 * The default value of the '{@link #getPasswordHash() <em>Password Hash</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPasswordHash()
	 * @generated
	 * @ordered
	 */
	protected static final String PASSWORD_HASH_EDEFAULT = null;
	/**
	 * The default value of the '{@link #getSalt() <em>Salt</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSalt()
	 * @generated
	 * @ordered
	 */
	protected static final String SALT_EDEFAULT = null;

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
	public String getName() {
		return (String)eDynamicGet(UsermodelPackage.USER__NAME, UsermodelPackage.Literals.USER__NAME, true, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setName(String newName) {
		eDynamicSet(UsermodelPackage.USER__NAME, UsermodelPackage.Literals.USER__NAME, newName);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getPasswordHash() {
		return (String)eDynamicGet(UsermodelPackage.USER__PASSWORD_HASH, UsermodelPackage.Literals.USER__PASSWORD_HASH, true, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setPasswordHash(String newPasswordHash) {
		eDynamicSet(UsermodelPackage.USER__PASSWORD_HASH, UsermodelPackage.Literals.USER__PASSWORD_HASH, newPasswordHash);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getSalt() {
		return (String)eDynamicGet(UsermodelPackage.USER__SALT, UsermodelPackage.Literals.USER__SALT, true, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSalt(String newSalt) {
		eDynamicSet(UsermodelPackage.USER__SALT, UsermodelPackage.Literals.USER__SALT, newSalt);
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
			case UsermodelPackage.USER__NAME:
				return getName();
			case UsermodelPackage.USER__PASSWORD_HASH:
				return getPasswordHash();
			case UsermodelPackage.USER__SALT:
				return getSalt();
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
			case UsermodelPackage.USER__NAME:
				setName((String)newValue);
				return;
			case UsermodelPackage.USER__PASSWORD_HASH:
				setPasswordHash((String)newValue);
				return;
			case UsermodelPackage.USER__SALT:
				setSalt((String)newValue);
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
			case UsermodelPackage.USER__NAME:
				setName(NAME_EDEFAULT);
				return;
			case UsermodelPackage.USER__PASSWORD_HASH:
				setPasswordHash(PASSWORD_HASH_EDEFAULT);
				return;
			case UsermodelPackage.USER__SALT:
				setSalt(SALT_EDEFAULT);
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
			case UsermodelPackage.USER__NAME:
				return NAME_EDEFAULT == null ? getName() != null : !NAME_EDEFAULT.equals(getName());
			case UsermodelPackage.USER__PASSWORD_HASH:
				return PASSWORD_HASH_EDEFAULT == null ? getPasswordHash() != null : !PASSWORD_HASH_EDEFAULT.equals(getPasswordHash());
			case UsermodelPackage.USER__SALT:
				return SALT_EDEFAULT == null ? getSalt() != null : !SALT_EDEFAULT.equals(getSalt());
		}
		return super.eIsSet(featureID);
	}

} //UserImpl
