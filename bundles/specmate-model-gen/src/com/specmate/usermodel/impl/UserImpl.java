/**
 */
package com.specmate.usermodel.impl;

import com.specmate.usermodel.User;
import com.specmate.usermodel.UsermodelPackage;

import java.util.Collection;

import org.eclipse.emf.common.util.EList;

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
 *   <li>{@link com.specmate.usermodel.impl.UserImpl#getUserName <em>User Name</em>}</li>
 *   <li>{@link com.specmate.usermodel.impl.UserImpl#getPassWord <em>Pass Word</em>}</li>
 *   <li>{@link com.specmate.usermodel.impl.UserImpl#getProjectName <em>Project Name</em>}</li>
 * </ul>
 *
 * @generated
 */
public class UserImpl extends CDOObjectImpl implements User {
	/**
	 * The default value of the '{@link #getUserName() <em>User Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getUserName()
	 * @generated
	 * @ordered
	 */
	protected static final String USER_NAME_EDEFAULT = null;

	/**
	 * The default value of the '{@link #getPassWord() <em>Pass Word</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPassWord()
	 * @generated
	 * @ordered
	 */
	protected static final String PASS_WORD_EDEFAULT = null;

	/**
	 * The default value of the '{@link #getProjectName() <em>Project Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getProjectName()
	 * @generated
	 * @ordered
	 */
	protected static final String PROJECT_NAME_EDEFAULT = null;

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
	@SuppressWarnings("unchecked")
	@Override
	public EList<String> getAllowedUrls() {
		return (EList<String>)eDynamicGet(UsermodelPackage.USER__ALLOWED_URLS, UsermodelPackage.Literals.USER__ALLOWED_URLS, true, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getUserName() {
		return (String)eDynamicGet(UsermodelPackage.USER__USER_NAME, UsermodelPackage.Literals.USER__USER_NAME, true, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setUserName(String newUserName) {
		eDynamicSet(UsermodelPackage.USER__USER_NAME, UsermodelPackage.Literals.USER__USER_NAME, newUserName);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getPassWord() {
		return (String)eDynamicGet(UsermodelPackage.USER__PASS_WORD, UsermodelPackage.Literals.USER__PASS_WORD, true, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setPassWord(String newPassWord) {
		eDynamicSet(UsermodelPackage.USER__PASS_WORD, UsermodelPackage.Literals.USER__PASS_WORD, newPassWord);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getProjectName() {
		return (String)eDynamicGet(UsermodelPackage.USER__PROJECT_NAME, UsermodelPackage.Literals.USER__PROJECT_NAME, true, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setProjectName(String newProjectName) {
		eDynamicSet(UsermodelPackage.USER__PROJECT_NAME, UsermodelPackage.Literals.USER__PROJECT_NAME, newProjectName);
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
			case UsermodelPackage.USER__USER_NAME:
				return getUserName();
			case UsermodelPackage.USER__PASS_WORD:
				return getPassWord();
			case UsermodelPackage.USER__PROJECT_NAME:
				return getProjectName();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case UsermodelPackage.USER__ALLOWED_URLS:
				getAllowedUrls().clear();
				getAllowedUrls().addAll((Collection<? extends String>)newValue);
				return;
			case UsermodelPackage.USER__USER_NAME:
				setUserName((String)newValue);
				return;
			case UsermodelPackage.USER__PASS_WORD:
				setPassWord((String)newValue);
				return;
			case UsermodelPackage.USER__PROJECT_NAME:
				setProjectName((String)newValue);
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
				getAllowedUrls().clear();
				return;
			case UsermodelPackage.USER__USER_NAME:
				setUserName(USER_NAME_EDEFAULT);
				return;
			case UsermodelPackage.USER__PASS_WORD:
				setPassWord(PASS_WORD_EDEFAULT);
				return;
			case UsermodelPackage.USER__PROJECT_NAME:
				setProjectName(PROJECT_NAME_EDEFAULT);
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
				return !getAllowedUrls().isEmpty();
			case UsermodelPackage.USER__USER_NAME:
				return USER_NAME_EDEFAULT == null ? getUserName() != null : !USER_NAME_EDEFAULT.equals(getUserName());
			case UsermodelPackage.USER__PASS_WORD:
				return PASS_WORD_EDEFAULT == null ? getPassWord() != null : !PASS_WORD_EDEFAULT.equals(getPassWord());
			case UsermodelPackage.USER__PROJECT_NAME:
				return PROJECT_NAME_EDEFAULT == null ? getProjectName() != null : !PROJECT_NAME_EDEFAULT.equals(getProjectName());
		}
		return super.eIsSet(featureID);
	}

} //UserImpl
