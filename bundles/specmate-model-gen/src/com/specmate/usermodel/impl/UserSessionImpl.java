/**
 */
package com.specmate.usermodel.impl;

import com.specmate.usermodel.AccessRights;
import com.specmate.usermodel.UserSession;
import com.specmate.usermodel.UsermodelPackage;

import java.util.Collection;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.internal.cdo.CDOObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>User Session</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link com.specmate.usermodel.impl.UserSessionImpl#getId <em>Id</em>}</li>
 *   <li>{@link com.specmate.usermodel.impl.UserSessionImpl#getAllowedPathPattern <em>Allowed Path Pattern</em>}</li>
 *   <li>{@link com.specmate.usermodel.impl.UserSessionImpl#getUserName <em>User Name</em>}</li>
 *   <li>{@link com.specmate.usermodel.impl.UserSessionImpl#getLastActive <em>Last Active</em>}</li>
 *   <li>{@link com.specmate.usermodel.impl.UserSessionImpl#getSourceSystem <em>Source System</em>}</li>
 *   <li>{@link com.specmate.usermodel.impl.UserSessionImpl#getTargetSystem <em>Target System</em>}</li>
 *   <li>{@link com.specmate.usermodel.impl.UserSessionImpl#getLibraryFolders <em>Library Folders</em>}</li>
 *   <li>{@link com.specmate.usermodel.impl.UserSessionImpl#isIsDeleted <em>Is Deleted</em>}</li>
 * </ul>
 *
 * @generated
 */
public class UserSessionImpl extends CDOObjectImpl implements UserSession {
	/**
	 * The default value of the '{@link #getId() <em>Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getId()
	 * @generated
	 * @ordered
	 */
	protected static final String ID_EDEFAULT = null;

	/**
	 * The default value of the '{@link #getAllowedPathPattern() <em>Allowed Path Pattern</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAllowedPathPattern()
	 * @generated
	 * @ordered
	 */
	protected static final String ALLOWED_PATH_PATTERN_EDEFAULT = null;

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
	 * The default value of the '{@link #getLastActive() <em>Last Active</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLastActive()
	 * @generated
	 * @ordered
	 */
	protected static final long LAST_ACTIVE_EDEFAULT = 0L;

	/**
	 * The default value of the '{@link #getSourceSystem() <em>Source System</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSourceSystem()
	 * @generated
	 * @ordered
	 */
	protected static final AccessRights SOURCE_SYSTEM_EDEFAULT = AccessRights.NONE;

	/**
	 * The default value of the '{@link #getTargetSystem() <em>Target System</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTargetSystem()
	 * @generated
	 * @ordered
	 */
	protected static final AccessRights TARGET_SYSTEM_EDEFAULT = AccessRights.NONE;

	/**
	 * The default value of the '{@link #isIsDeleted() <em>Is Deleted</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isIsDeleted()
	 * @generated
	 * @ordered
	 */
	protected static final boolean IS_DELETED_EDEFAULT = false;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected UserSessionImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return UsermodelPackage.Literals.USER_SESSION;
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
	@Override
	public String getId() {
		return (String)eDynamicGet(UsermodelPackage.USER_SESSION__ID, UsermodelPackage.Literals.USER_SESSION__ID, true, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setId(String newId) {
		eDynamicSet(UsermodelPackage.USER_SESSION__ID, UsermodelPackage.Literals.USER_SESSION__ID, newId);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getAllowedPathPattern() {
		return (String)eDynamicGet(UsermodelPackage.USER_SESSION__ALLOWED_PATH_PATTERN, UsermodelPackage.Literals.USER_SESSION__ALLOWED_PATH_PATTERN, true, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setAllowedPathPattern(String newAllowedPathPattern) {
		eDynamicSet(UsermodelPackage.USER_SESSION__ALLOWED_PATH_PATTERN, UsermodelPackage.Literals.USER_SESSION__ALLOWED_PATH_PATTERN, newAllowedPathPattern);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getUserName() {
		return (String)eDynamicGet(UsermodelPackage.USER_SESSION__USER_NAME, UsermodelPackage.Literals.USER_SESSION__USER_NAME, true, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setUserName(String newUserName) {
		eDynamicSet(UsermodelPackage.USER_SESSION__USER_NAME, UsermodelPackage.Literals.USER_SESSION__USER_NAME, newUserName);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public long getLastActive() {
		return (Long)eDynamicGet(UsermodelPackage.USER_SESSION__LAST_ACTIVE, UsermodelPackage.Literals.USER_SESSION__LAST_ACTIVE, true, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setLastActive(long newLastActive) {
		eDynamicSet(UsermodelPackage.USER_SESSION__LAST_ACTIVE, UsermodelPackage.Literals.USER_SESSION__LAST_ACTIVE, newLastActive);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public AccessRights getSourceSystem() {
		return (AccessRights)eDynamicGet(UsermodelPackage.USER_SESSION__SOURCE_SYSTEM, UsermodelPackage.Literals.USER_SESSION__SOURCE_SYSTEM, true, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setSourceSystem(AccessRights newSourceSystem) {
		eDynamicSet(UsermodelPackage.USER_SESSION__SOURCE_SYSTEM, UsermodelPackage.Literals.USER_SESSION__SOURCE_SYSTEM, newSourceSystem);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public AccessRights getTargetSystem() {
		return (AccessRights)eDynamicGet(UsermodelPackage.USER_SESSION__TARGET_SYSTEM, UsermodelPackage.Literals.USER_SESSION__TARGET_SYSTEM, true, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setTargetSystem(AccessRights newTargetSystem) {
		eDynamicSet(UsermodelPackage.USER_SESSION__TARGET_SYSTEM, UsermodelPackage.Literals.USER_SESSION__TARGET_SYSTEM, newTargetSystem);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public EList<String> getLibraryFolders() {
		return (EList<String>)eDynamicGet(UsermodelPackage.USER_SESSION__LIBRARY_FOLDERS, UsermodelPackage.Literals.USER_SESSION__LIBRARY_FOLDERS, true, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isIsDeleted() {
		return (Boolean)eDynamicGet(UsermodelPackage.USER_SESSION__IS_DELETED, UsermodelPackage.Literals.USER_SESSION__IS_DELETED, true, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setIsDeleted(boolean newIsDeleted) {
		eDynamicSet(UsermodelPackage.USER_SESSION__IS_DELETED, UsermodelPackage.Literals.USER_SESSION__IS_DELETED, newIsDeleted);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case UsermodelPackage.USER_SESSION__ID:
				return getId();
			case UsermodelPackage.USER_SESSION__ALLOWED_PATH_PATTERN:
				return getAllowedPathPattern();
			case UsermodelPackage.USER_SESSION__USER_NAME:
				return getUserName();
			case UsermodelPackage.USER_SESSION__LAST_ACTIVE:
				return getLastActive();
			case UsermodelPackage.USER_SESSION__SOURCE_SYSTEM:
				return getSourceSystem();
			case UsermodelPackage.USER_SESSION__TARGET_SYSTEM:
				return getTargetSystem();
			case UsermodelPackage.USER_SESSION__LIBRARY_FOLDERS:
				return getLibraryFolders();
			case UsermodelPackage.USER_SESSION__IS_DELETED:
				return isIsDeleted();
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
			case UsermodelPackage.USER_SESSION__ID:
				setId((String)newValue);
				return;
			case UsermodelPackage.USER_SESSION__ALLOWED_PATH_PATTERN:
				setAllowedPathPattern((String)newValue);
				return;
			case UsermodelPackage.USER_SESSION__USER_NAME:
				setUserName((String)newValue);
				return;
			case UsermodelPackage.USER_SESSION__LAST_ACTIVE:
				setLastActive((Long)newValue);
				return;
			case UsermodelPackage.USER_SESSION__SOURCE_SYSTEM:
				setSourceSystem((AccessRights)newValue);
				return;
			case UsermodelPackage.USER_SESSION__TARGET_SYSTEM:
				setTargetSystem((AccessRights)newValue);
				return;
			case UsermodelPackage.USER_SESSION__LIBRARY_FOLDERS:
				getLibraryFolders().clear();
				getLibraryFolders().addAll((Collection<? extends String>)newValue);
				return;
			case UsermodelPackage.USER_SESSION__IS_DELETED:
				setIsDeleted((Boolean)newValue);
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
			case UsermodelPackage.USER_SESSION__ID:
				setId(ID_EDEFAULT);
				return;
			case UsermodelPackage.USER_SESSION__ALLOWED_PATH_PATTERN:
				setAllowedPathPattern(ALLOWED_PATH_PATTERN_EDEFAULT);
				return;
			case UsermodelPackage.USER_SESSION__USER_NAME:
				setUserName(USER_NAME_EDEFAULT);
				return;
			case UsermodelPackage.USER_SESSION__LAST_ACTIVE:
				setLastActive(LAST_ACTIVE_EDEFAULT);
				return;
			case UsermodelPackage.USER_SESSION__SOURCE_SYSTEM:
				setSourceSystem(SOURCE_SYSTEM_EDEFAULT);
				return;
			case UsermodelPackage.USER_SESSION__TARGET_SYSTEM:
				setTargetSystem(TARGET_SYSTEM_EDEFAULT);
				return;
			case UsermodelPackage.USER_SESSION__LIBRARY_FOLDERS:
				getLibraryFolders().clear();
				return;
			case UsermodelPackage.USER_SESSION__IS_DELETED:
				setIsDeleted(IS_DELETED_EDEFAULT);
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
			case UsermodelPackage.USER_SESSION__ID:
				return ID_EDEFAULT == null ? getId() != null : !ID_EDEFAULT.equals(getId());
			case UsermodelPackage.USER_SESSION__ALLOWED_PATH_PATTERN:
				return ALLOWED_PATH_PATTERN_EDEFAULT == null ? getAllowedPathPattern() != null : !ALLOWED_PATH_PATTERN_EDEFAULT.equals(getAllowedPathPattern());
			case UsermodelPackage.USER_SESSION__USER_NAME:
				return USER_NAME_EDEFAULT == null ? getUserName() != null : !USER_NAME_EDEFAULT.equals(getUserName());
			case UsermodelPackage.USER_SESSION__LAST_ACTIVE:
				return getLastActive() != LAST_ACTIVE_EDEFAULT;
			case UsermodelPackage.USER_SESSION__SOURCE_SYSTEM:
				return getSourceSystem() != SOURCE_SYSTEM_EDEFAULT;
			case UsermodelPackage.USER_SESSION__TARGET_SYSTEM:
				return getTargetSystem() != TARGET_SYSTEM_EDEFAULT;
			case UsermodelPackage.USER_SESSION__LIBRARY_FOLDERS:
				return !getLibraryFolders().isEmpty();
			case UsermodelPackage.USER_SESSION__IS_DELETED:
				return isIsDeleted() != IS_DELETED_EDEFAULT;
		}
		return super.eIsSet(featureID);
	}

} //UserSessionImpl
