/**
 */
package com.specmate.model.history.impl;

import com.specmate.model.history.Change;
import com.specmate.model.history.HistoryPackage;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.internal.cdo.CDOObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Change</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link com.specmate.model.history.impl.ChangeImpl#getObjectName <em>Object Name</em>}</li>
 *   <li>{@link com.specmate.model.history.impl.ChangeImpl#getObjectType <em>Object Type</em>}</li>
 *   <li>{@link com.specmate.model.history.impl.ChangeImpl#getOldValue <em>Old Value</em>}</li>
 *   <li>{@link com.specmate.model.history.impl.ChangeImpl#getNewValue <em>New Value</em>}</li>
 *   <li>{@link com.specmate.model.history.impl.ChangeImpl#getFeature <em>Feature</em>}</li>
 *   <li>{@link com.specmate.model.history.impl.ChangeImpl#isIsCreate <em>Is Create</em>}</li>
 *   <li>{@link com.specmate.model.history.impl.ChangeImpl#isIsDelete <em>Is Delete</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ChangeImpl extends CDOObjectImpl implements Change {
	/**
	 * The default value of the '{@link #getObjectName() <em>Object Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getObjectName()
	 * @generated
	 * @ordered
	 */
	protected static final String OBJECT_NAME_EDEFAULT = null;

	/**
	 * The default value of the '{@link #getObjectType() <em>Object Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getObjectType()
	 * @generated
	 * @ordered
	 */
	protected static final String OBJECT_TYPE_EDEFAULT = "";

	/**
	 * The default value of the '{@link #getOldValue() <em>Old Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOldValue()
	 * @generated
	 * @ordered
	 */
	protected static final String OLD_VALUE_EDEFAULT = null;

	/**
	 * The default value of the '{@link #getNewValue() <em>New Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getNewValue()
	 * @generated
	 * @ordered
	 */
	protected static final String NEW_VALUE_EDEFAULT = null;

	/**
	 * The default value of the '{@link #getFeature() <em>Feature</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFeature()
	 * @generated
	 * @ordered
	 */
	protected static final String FEATURE_EDEFAULT = null;

	/**
	 * The default value of the '{@link #isIsCreate() <em>Is Create</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isIsCreate()
	 * @generated
	 * @ordered
	 */
	protected static final boolean IS_CREATE_EDEFAULT = false;

	/**
	 * The default value of the '{@link #isIsDelete() <em>Is Delete</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isIsDelete()
	 * @generated
	 * @ordered
	 */
	protected static final boolean IS_DELETE_EDEFAULT = false;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ChangeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return HistoryPackage.Literals.CHANGE;
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
	public String getObjectName() {
		return (String)eDynamicGet(HistoryPackage.CHANGE__OBJECT_NAME, HistoryPackage.Literals.CHANGE__OBJECT_NAME, true, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setObjectName(String newObjectName) {
		eDynamicSet(HistoryPackage.CHANGE__OBJECT_NAME, HistoryPackage.Literals.CHANGE__OBJECT_NAME, newObjectName);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getObjectType() {
		return (String)eDynamicGet(HistoryPackage.CHANGE__OBJECT_TYPE, HistoryPackage.Literals.CHANGE__OBJECT_TYPE, true, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setObjectType(String newObjectType) {
		eDynamicSet(HistoryPackage.CHANGE__OBJECT_TYPE, HistoryPackage.Literals.CHANGE__OBJECT_TYPE, newObjectType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getOldValue() {
		return (String)eDynamicGet(HistoryPackage.CHANGE__OLD_VALUE, HistoryPackage.Literals.CHANGE__OLD_VALUE, true, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setOldValue(String newOldValue) {
		eDynamicSet(HistoryPackage.CHANGE__OLD_VALUE, HistoryPackage.Literals.CHANGE__OLD_VALUE, newOldValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getNewValue() {
		return (String)eDynamicGet(HistoryPackage.CHANGE__NEW_VALUE, HistoryPackage.Literals.CHANGE__NEW_VALUE, true, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setNewValue(String newNewValue) {
		eDynamicSet(HistoryPackage.CHANGE__NEW_VALUE, HistoryPackage.Literals.CHANGE__NEW_VALUE, newNewValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getFeature() {
		return (String)eDynamicGet(HistoryPackage.CHANGE__FEATURE, HistoryPackage.Literals.CHANGE__FEATURE, true, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setFeature(String newFeature) {
		eDynamicSet(HistoryPackage.CHANGE__FEATURE, HistoryPackage.Literals.CHANGE__FEATURE, newFeature);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isIsCreate() {
		return (Boolean)eDynamicGet(HistoryPackage.CHANGE__IS_CREATE, HistoryPackage.Literals.CHANGE__IS_CREATE, true, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setIsCreate(boolean newIsCreate) {
		eDynamicSet(HistoryPackage.CHANGE__IS_CREATE, HistoryPackage.Literals.CHANGE__IS_CREATE, newIsCreate);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isIsDelete() {
		return (Boolean)eDynamicGet(HistoryPackage.CHANGE__IS_DELETE, HistoryPackage.Literals.CHANGE__IS_DELETE, true, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setIsDelete(boolean newIsDelete) {
		eDynamicSet(HistoryPackage.CHANGE__IS_DELETE, HistoryPackage.Literals.CHANGE__IS_DELETE, newIsDelete);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case HistoryPackage.CHANGE__OBJECT_NAME:
				return getObjectName();
			case HistoryPackage.CHANGE__OBJECT_TYPE:
				return getObjectType();
			case HistoryPackage.CHANGE__OLD_VALUE:
				return getOldValue();
			case HistoryPackage.CHANGE__NEW_VALUE:
				return getNewValue();
			case HistoryPackage.CHANGE__FEATURE:
				return getFeature();
			case HistoryPackage.CHANGE__IS_CREATE:
				return isIsCreate();
			case HistoryPackage.CHANGE__IS_DELETE:
				return isIsDelete();
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
			case HistoryPackage.CHANGE__OBJECT_NAME:
				setObjectName((String)newValue);
				return;
			case HistoryPackage.CHANGE__OBJECT_TYPE:
				setObjectType((String)newValue);
				return;
			case HistoryPackage.CHANGE__OLD_VALUE:
				setOldValue((String)newValue);
				return;
			case HistoryPackage.CHANGE__NEW_VALUE:
				setNewValue((String)newValue);
				return;
			case HistoryPackage.CHANGE__FEATURE:
				setFeature((String)newValue);
				return;
			case HistoryPackage.CHANGE__IS_CREATE:
				setIsCreate((Boolean)newValue);
				return;
			case HistoryPackage.CHANGE__IS_DELETE:
				setIsDelete((Boolean)newValue);
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
			case HistoryPackage.CHANGE__OBJECT_NAME:
				setObjectName(OBJECT_NAME_EDEFAULT);
				return;
			case HistoryPackage.CHANGE__OBJECT_TYPE:
				setObjectType(OBJECT_TYPE_EDEFAULT);
				return;
			case HistoryPackage.CHANGE__OLD_VALUE:
				setOldValue(OLD_VALUE_EDEFAULT);
				return;
			case HistoryPackage.CHANGE__NEW_VALUE:
				setNewValue(NEW_VALUE_EDEFAULT);
				return;
			case HistoryPackage.CHANGE__FEATURE:
				setFeature(FEATURE_EDEFAULT);
				return;
			case HistoryPackage.CHANGE__IS_CREATE:
				setIsCreate(IS_CREATE_EDEFAULT);
				return;
			case HistoryPackage.CHANGE__IS_DELETE:
				setIsDelete(IS_DELETE_EDEFAULT);
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
			case HistoryPackage.CHANGE__OBJECT_NAME:
				return OBJECT_NAME_EDEFAULT == null ? getObjectName() != null : !OBJECT_NAME_EDEFAULT.equals(getObjectName());
			case HistoryPackage.CHANGE__OBJECT_TYPE:
				return OBJECT_TYPE_EDEFAULT == null ? getObjectType() != null : !OBJECT_TYPE_EDEFAULT.equals(getObjectType());
			case HistoryPackage.CHANGE__OLD_VALUE:
				return OLD_VALUE_EDEFAULT == null ? getOldValue() != null : !OLD_VALUE_EDEFAULT.equals(getOldValue());
			case HistoryPackage.CHANGE__NEW_VALUE:
				return NEW_VALUE_EDEFAULT == null ? getNewValue() != null : !NEW_VALUE_EDEFAULT.equals(getNewValue());
			case HistoryPackage.CHANGE__FEATURE:
				return FEATURE_EDEFAULT == null ? getFeature() != null : !FEATURE_EDEFAULT.equals(getFeature());
			case HistoryPackage.CHANGE__IS_CREATE:
				return isIsCreate() != IS_CREATE_EDEFAULT;
			case HistoryPackage.CHANGE__IS_DELETE:
				return isIsDelete() != IS_DELETE_EDEFAULT;
		}
		return super.eIsSet(featureID);
	}

} //ChangeImpl
