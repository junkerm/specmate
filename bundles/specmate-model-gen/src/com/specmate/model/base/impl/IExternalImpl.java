/**
 */
package com.specmate.model.base.impl;

import com.specmate.model.base.BasePackage;
import com.specmate.model.base.IExternal;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.internal.cdo.CDOObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>IExternal</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link com.specmate.model.base.impl.IExternalImpl#getExtId <em>Ext Id</em>}</li>
 *   <li>{@link com.specmate.model.base.impl.IExternalImpl#getExtId2 <em>Ext Id2</em>}</li>
 *   <li>{@link com.specmate.model.base.impl.IExternalImpl#getSource <em>Source</em>}</li>
 * </ul>
 *
 * @generated
 */
public class IExternalImpl extends CDOObjectImpl implements IExternal {
	/**
	 * The default value of the '{@link #getExtId() <em>Ext Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getExtId()
	 * @generated
	 * @ordered
	 */
	protected static final String EXT_ID_EDEFAULT = null;

	/**
	 * The default value of the '{@link #getExtId2() <em>Ext Id2</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getExtId2()
	 * @generated
	 * @ordered
	 */
	protected static final String EXT_ID2_EDEFAULT = null;

	/**
	 * The default value of the '{@link #getSource() <em>Source</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSource()
	 * @generated
	 * @ordered
	 */
	protected static final String SOURCE_EDEFAULT = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected IExternalImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return BasePackage.Literals.IEXTERNAL;
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
	public String getExtId() {
		return (String)eDynamicGet(BasePackage.IEXTERNAL__EXT_ID, BasePackage.Literals.IEXTERNAL__EXT_ID, true, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setExtId(String newExtId) {
		eDynamicSet(BasePackage.IEXTERNAL__EXT_ID, BasePackage.Literals.IEXTERNAL__EXT_ID, newExtId);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getExtId2() {
		return (String)eDynamicGet(BasePackage.IEXTERNAL__EXT_ID2, BasePackage.Literals.IEXTERNAL__EXT_ID2, true, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setExtId2(String newExtId2) {
		eDynamicSet(BasePackage.IEXTERNAL__EXT_ID2, BasePackage.Literals.IEXTERNAL__EXT_ID2, newExtId2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getSource() {
		return (String)eDynamicGet(BasePackage.IEXTERNAL__SOURCE, BasePackage.Literals.IEXTERNAL__SOURCE, true, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSource(String newSource) {
		eDynamicSet(BasePackage.IEXTERNAL__SOURCE, BasePackage.Literals.IEXTERNAL__SOURCE, newSource);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case BasePackage.IEXTERNAL__EXT_ID:
				return getExtId();
			case BasePackage.IEXTERNAL__EXT_ID2:
				return getExtId2();
			case BasePackage.IEXTERNAL__SOURCE:
				return getSource();
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
			case BasePackage.IEXTERNAL__EXT_ID:
				setExtId((String)newValue);
				return;
			case BasePackage.IEXTERNAL__EXT_ID2:
				setExtId2((String)newValue);
				return;
			case BasePackage.IEXTERNAL__SOURCE:
				setSource((String)newValue);
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
			case BasePackage.IEXTERNAL__EXT_ID:
				setExtId(EXT_ID_EDEFAULT);
				return;
			case BasePackage.IEXTERNAL__EXT_ID2:
				setExtId2(EXT_ID2_EDEFAULT);
				return;
			case BasePackage.IEXTERNAL__SOURCE:
				setSource(SOURCE_EDEFAULT);
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
			case BasePackage.IEXTERNAL__EXT_ID:
				return EXT_ID_EDEFAULT == null ? getExtId() != null : !EXT_ID_EDEFAULT.equals(getExtId());
			case BasePackage.IEXTERNAL__EXT_ID2:
				return EXT_ID2_EDEFAULT == null ? getExtId2() != null : !EXT_ID2_EDEFAULT.equals(getExtId2());
			case BasePackage.IEXTERNAL__SOURCE:
				return SOURCE_EDEFAULT == null ? getSource() != null : !SOURCE_EDEFAULT.equals(getSource());
		}
		return super.eIsSet(featureID);
	}

} //IExternalImpl
