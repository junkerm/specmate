/**
 */
package com.specmate.model.administration.impl;

import com.specmate.model.administration.AdministrationPackage;
import com.specmate.model.administration.ErrorCode;
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
 *   <li>{@link com.specmate.model.administration.impl.ProblemDetailImpl#getEcode <em>Ecode</em>}</li>
 *   <li>{@link com.specmate.model.administration.impl.ProblemDetailImpl#getStatus <em>Status</em>}</li>
 *   <li>{@link com.specmate.model.administration.impl.ProblemDetailImpl#getDetail <em>Detail</em>}</li>
 *   <li>{@link com.specmate.model.administration.impl.ProblemDetailImpl#getInstance <em>Instance</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ProblemDetailImpl extends CDOObjectImpl implements ProblemDetail {
	/**
	 * The default value of the '{@link #getEcode() <em>Ecode</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEcode()
	 * @generated
	 * @ordered
	 */
	protected static final ErrorCode ECODE_EDEFAULT = ErrorCode.NO_SUCH_SERVICE;

	/**
	 * The default value of the '{@link #getStatus() <em>Status</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getStatus()
	 * @generated
	 * @ordered
	 */
	protected static final int STATUS_EDEFAULT = 0;

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
	@Override
	public ErrorCode getEcode() {
		return (ErrorCode)eDynamicGet(AdministrationPackage.PROBLEM_DETAIL__ECODE, AdministrationPackage.Literals.PROBLEM_DETAIL__ECODE, true, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setEcode(ErrorCode newEcode) {
		eDynamicSet(AdministrationPackage.PROBLEM_DETAIL__ECODE, AdministrationPackage.Literals.PROBLEM_DETAIL__ECODE, newEcode);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int getStatus() {
		return (Integer)eDynamicGet(AdministrationPackage.PROBLEM_DETAIL__STATUS, AdministrationPackage.Literals.PROBLEM_DETAIL__STATUS, true, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setStatus(int newStatus) {
		eDynamicSet(AdministrationPackage.PROBLEM_DETAIL__STATUS, AdministrationPackage.Literals.PROBLEM_DETAIL__STATUS, newStatus);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getDetail() {
		return (String)eDynamicGet(AdministrationPackage.PROBLEM_DETAIL__DETAIL, AdministrationPackage.Literals.PROBLEM_DETAIL__DETAIL, true, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setDetail(String newDetail) {
		eDynamicSet(AdministrationPackage.PROBLEM_DETAIL__DETAIL, AdministrationPackage.Literals.PROBLEM_DETAIL__DETAIL, newDetail);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getInstance() {
		return (String)eDynamicGet(AdministrationPackage.PROBLEM_DETAIL__INSTANCE, AdministrationPackage.Literals.PROBLEM_DETAIL__INSTANCE, true, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
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
			case AdministrationPackage.PROBLEM_DETAIL__ECODE:
				return getEcode();
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
			case AdministrationPackage.PROBLEM_DETAIL__ECODE:
				setEcode((ErrorCode)newValue);
				return;
			case AdministrationPackage.PROBLEM_DETAIL__STATUS:
				setStatus((Integer)newValue);
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
			case AdministrationPackage.PROBLEM_DETAIL__ECODE:
				setEcode(ECODE_EDEFAULT);
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
			case AdministrationPackage.PROBLEM_DETAIL__ECODE:
				return getEcode() != ECODE_EDEFAULT;
			case AdministrationPackage.PROBLEM_DETAIL__STATUS:
				return getStatus() != STATUS_EDEFAULT;
			case AdministrationPackage.PROBLEM_DETAIL__DETAIL:
				return DETAIL_EDEFAULT == null ? getDetail() != null : !DETAIL_EDEFAULT.equals(getDetail());
			case AdministrationPackage.PROBLEM_DETAIL__INSTANCE:
				return INSTANCE_EDEFAULT == null ? getInstance() != null : !INSTANCE_EDEFAULT.equals(getInstance());
		}
		return super.eIsSet(featureID);
	}

} //ProblemDetailImpl
