/**
 */
package com.specmate.model.processes.impl;

import com.specmate.model.processes.ProcessStep;
import com.specmate.model.processes.ProcessesPackage;

import org.eclipse.emf.ecore.EClass;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Process Step</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link com.specmate.model.processes.impl.ProcessStepImpl#getExpectedOutcome <em>Expected Outcome</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ProcessStepImpl extends ProcessNodeImpl implements ProcessStep {
	/**
	 * The default value of the '{@link #getExpectedOutcome() <em>Expected Outcome</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getExpectedOutcome()
	 * @generated
	 * @ordered
	 */
	protected static final String EXPECTED_OUTCOME_EDEFAULT = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ProcessStepImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ProcessesPackage.Literals.PROCESS_STEP;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getExpectedOutcome() {
		return (String)eDynamicGet(ProcessesPackage.PROCESS_STEP__EXPECTED_OUTCOME, ProcessesPackage.Literals.PROCESS_STEP__EXPECTED_OUTCOME, true, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setExpectedOutcome(String newExpectedOutcome) {
		eDynamicSet(ProcessesPackage.PROCESS_STEP__EXPECTED_OUTCOME, ProcessesPackage.Literals.PROCESS_STEP__EXPECTED_OUTCOME, newExpectedOutcome);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case ProcessesPackage.PROCESS_STEP__EXPECTED_OUTCOME:
				return getExpectedOutcome();
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
			case ProcessesPackage.PROCESS_STEP__EXPECTED_OUTCOME:
				setExpectedOutcome((String)newValue);
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
			case ProcessesPackage.PROCESS_STEP__EXPECTED_OUTCOME:
				setExpectedOutcome(EXPECTED_OUTCOME_EDEFAULT);
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
			case ProcessesPackage.PROCESS_STEP__EXPECTED_OUTCOME:
				return EXPECTED_OUTCOME_EDEFAULT == null ? getExpectedOutcome() != null : !EXPECTED_OUTCOME_EDEFAULT.equals(getExpectedOutcome());
		}
		return super.eIsSet(featureID);
	}

} //ProcessStepImpl
