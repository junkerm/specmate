/**
 */
package com.specmate.model.processes.impl;

import com.specmate.model.processes.ProcessConnection;
import com.specmate.model.processes.ProcessDecision;
import com.specmate.model.processes.ProcessEnd;
import com.specmate.model.processes.ProcessStart;
import com.specmate.model.processes.ProcessStep;
import com.specmate.model.processes.ProcessesFactory;
import com.specmate.model.processes.ProcessesPackage;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

import org.eclipse.emf.ecore.plugin.EcorePlugin;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class ProcessesFactoryImpl extends EFactoryImpl implements ProcessesFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static ProcessesFactory init() {
		try {
			ProcessesFactory theProcessesFactory = (ProcessesFactory)EPackage.Registry.INSTANCE.getEFactory(ProcessesPackage.eNS_URI);
			if (theProcessesFactory != null) {
				return theProcessesFactory;
			}
		}
		catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new ProcessesFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ProcessesFactoryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EObject create(EClass eClass) {
		switch (eClass.getClassifierID()) {
			case ProcessesPackage.PROCESS: return (EObject)createProcess();
			case ProcessesPackage.PROCESS_STEP: return (EObject)createProcessStep();
			case ProcessesPackage.PROCESS_DECISION: return (EObject)createProcessDecision();
			case ProcessesPackage.PROCESS_CONNECTION: return (EObject)createProcessConnection();
			case ProcessesPackage.PROCESS_START: return (EObject)createProcessStart();
			case ProcessesPackage.PROCESS_END: return (EObject)createProcessEnd();
			default:
				throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public com.specmate.model.processes.Process createProcess() {
		ProcessImpl process = new ProcessImpl();
		return process;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ProcessStep createProcessStep() {
		ProcessStepImpl processStep = new ProcessStepImpl();
		return processStep;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ProcessDecision createProcessDecision() {
		ProcessDecisionImpl processDecision = new ProcessDecisionImpl();
		return processDecision;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ProcessConnection createProcessConnection() {
		ProcessConnectionImpl processConnection = new ProcessConnectionImpl();
		return processConnection;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ProcessStart createProcessStart() {
		ProcessStartImpl processStart = new ProcessStartImpl();
		return processStart;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ProcessEnd createProcessEnd() {
		ProcessEndImpl processEnd = new ProcessEndImpl();
		return processEnd;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ProcessesPackage getProcessesPackage() {
		return (ProcessesPackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static ProcessesPackage getPackage() {
		return ProcessesPackage.eINSTANCE;
	}

} //ProcessesFactoryImpl
