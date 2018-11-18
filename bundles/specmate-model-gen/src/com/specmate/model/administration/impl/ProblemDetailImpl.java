/**
 */
package com.specmate.model.administration.impl;

import com.specmate.model.administration.AdministrationPackage;
import com.specmate.model.administration.ProblemDetail;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.internal.cdo.CDOObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Problem Detail</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link com.specmate.model.administration.impl.ProblemDetailImpl#getType <em>Type</em>}</li>
 *   <li>{@link com.specmate.model.administration.impl.ProblemDetailImpl#getStatus <em>Status</em>}</li>
 *   <li>{@link com.specmate.model.administration.impl.ProblemDetailImpl#getDetail <em>Detail</em>}</li>
 *   <li>{@link com.specmate.model.administration.impl.ProblemDetailImpl#getInstance <em>Instance</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ProblemDetailImpl extends CDOObjectImpl implements ProblemDetail {
	/**
	 * The default value of the '{@link #getType() <em>Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getType()
	 * @generated
	 * @ordered
	 */
	protected static final String TYPE_EDEFAULT = null;

	/**
	 * The default value of the '{@link #getStatus() <em>Status</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getStatus()
	 * @generated
	 * @ordered
	 */
	protected static final String STATUS_EDEFAULT = null;

	/**
	 * The default value of the '{@link #getDetail() <em>Detail</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDetail()
	 * @generated
	 * @ordered
	 */
	protected static final String DETAIL_EDEFAULT = null;

	/**
	 * The default value of the '{@link #getInstance() <em>Instance</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getInstance()
	 * @generated
	 * @ordered
	 */
	protected static final String INSTANCE_EDEFAULT = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ProblemDetailImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return AdministrationPackage.Literals.PROBLEM_DETAIL;
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
	public String getType() {
		return (String)eDynamicGet(AdministrationPackage.PROBLEM_DETAIL__TYPE, AdministrationPackage.Literals.PROBLEM_DETAIL__TYPE, true, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setType(String newType) {
		eDynamicSet(AdministrationPackage.PROBLEM_DETAIL__TYPE, AdministrationPackage.Literals.PROBLEM_DETAIL__TYPE, newType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getStatus() {
		return (String)eDynamicGet(AdministrationPackage.PROBLEM_DETAIL__STATUS, AdministrationPackage.Literals.PROBLEM_DETAIL__STATUS, true, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setStatus(String newStatus) {
		eDynamicSet(AdministrationPackage.PROBLEM_DETAIL__STATUS, AdministrationPackage.Literals.PROBLEM_DETAIL__STATUS, newStatus);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getDetail() {
		return (String)eDynamicGet(AdministrationPackage.PROBLEM_DETAIL__DETAIL, AdministrationPackage.Literals.PROBLEM_DETAIL__DETAIL, true, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDetail(String newDetail) {
		eDynamicSet(AdministrationPackage.PROBLEM_DETAIL__DETAIL, AdministrationPackage.Literals.PROBLEM_DETAIL__DETAIL, newDetail);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getInstance() {
		return (String)eDynamicGet(AdministrationPackage.PROBLEM_DETAIL__INSTANCE, AdministrationPackage.Literals.PROBLEM_DETAIL__INSTANCE, true, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setInstance(String newInstance) {
		eDynamicSet(AdministrationPackage.PROBLEM_DETAIL__INSTANCE, AdministrationPackage.Literals.PROBLEM_DETAIL__INSTANCE, newInstance);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case AdministrationPackage.PROBLEM_DETAIL__TYPE:
				return getType();
			case AdministrationPackage.PROBLEM_DETAIL__STATUS:
				return getStatus();
			case AdministrationPackage.PROBLEM_DETAIL__DETAIL:
				return getDetail();
			case AdministrationPackage.PROBLEM_DETAIL__INSTANCE:
				return getInstance();
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
			case AdministrationPackage.PROBLEM_DETAIL__TYPE:
				setType((String)newValue);
				return;
			case AdministrationPackage.PROBLEM_DETAIL__STATUS:
				setStatus((String)newValue);
				return;
			case AdministrationPackage.PROBLEM_DETAIL__DETAIL:
				setDetail((String)newValue);
				return;
			case AdministrationPackage.PROBLEM_DETAIL__INSTANCE:
				setInstance((String)newValue);
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
			case AdministrationPackage.PROBLEM_DETAIL__TYPE:
				setType(TYPE_EDEFAULT);
				return;
			case AdministrationPackage.PROBLEM_DETAIL__STATUS:
				setStatus(STATUS_EDEFAULT);
				return;
			case AdministrationPackage.PROBLEM_DETAIL__DETAIL:
				setDetail(DETAIL_EDEFAULT);
				return;
			case AdministrationPackage.PROBLEM_DETAIL__INSTANCE:
				setInstance(INSTANCE_EDEFAULT);
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
			case AdministrationPackage.PROBLEM_DETAIL__TYPE:
				return TYPE_EDEFAULT == null ? getType() != null : !TYPE_EDEFAULT.equals(getType());
			case AdministrationPackage.PROBLEM_DETAIL__STATUS:
				return STATUS_EDEFAULT == null ? getStatus() != null : !STATUS_EDEFAULT.equals(getStatus());
			case AdministrationPackage.PROBLEM_DETAIL__DETAIL:
				return DETAIL_EDEFAULT == null ? getDetail() != null : !DETAIL_EDEFAULT.equals(getDetail());
			case AdministrationPackage.PROBLEM_DETAIL__INSTANCE:
				return INSTANCE_EDEFAULT == null ? getInstance() != null : !INSTANCE_EDEFAULT.equals(getInstance());
		}
		return super.eIsSet(featureID);
	}

} //ProblemDetailImpl
