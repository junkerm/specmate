/**
 */
package com.specmate.model.processes.util;

import com.specmate.model.base.IContainer;
import com.specmate.model.base.IContentElement;
import com.specmate.model.base.IDescribed;
import com.specmate.model.base.IID;
import com.specmate.model.base.IModelConnection;
import com.specmate.model.base.IModelNode;
import com.specmate.model.base.INamed;
import com.specmate.model.base.ISpecmateModelObject;
import com.specmate.model.base.ISpecmatePositionableModelObject;

import com.specmate.model.processes.ProcessConnection;
import com.specmate.model.processes.ProcessDecision;
import com.specmate.model.processes.ProcessNode;
import com.specmate.model.processes.ProcessStep;
import com.specmate.model.processes.ProcessesPackage;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.util.Switch;

/**
 * <!-- begin-user-doc -->
 * The <b>Switch</b> for the model's inheritance hierarchy.
 * It supports the call {@link #doSwitch(EObject) doSwitch(object)}
 * to invoke the <code>caseXXX</code> method for each class of the model,
 * starting with the actual class of the object
 * and proceeding up the inheritance hierarchy
 * until a non-null result is returned,
 * which is the result of the switch.
 * <!-- end-user-doc -->
 * @see com.specmate.model.processes.ProcessesPackage
 * @generated
 */
public class ProcessesSwitch<T> extends Switch<T> {
	/**
	 * The cached model package
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static ProcessesPackage modelPackage;

	/**
	 * Creates an instance of the switch.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ProcessesSwitch() {
		if (modelPackage == null) {
			modelPackage = ProcessesPackage.eINSTANCE;
		}
	}

	/**
	 * Checks whether this is a switch for the given package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param ePackage the package in question.
	 * @return whether this is a switch for the given package.
	 * @generated
	 */
	@Override
	protected boolean isSwitchFor(EPackage ePackage) {
		return ePackage == modelPackage;
	}

	/**
	 * Calls <code>caseXXX</code> for each class of the model until one returns a non null result; it yields that result.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the first non-null result returned by a <code>caseXXX</code> call.
	 * @generated
	 */
	@Override
	protected T doSwitch(int classifierID, EObject theEObject) {
		switch (classifierID) {
			case ProcessesPackage.PROCESS: {
				com.specmate.model.processes.Process process = (com.specmate.model.processes.Process)theEObject;
				T result = caseProcess(process);
				if (result == null) result = caseIContainer(process);
				if (result == null) result = caseIContentElement(process);
				if (result == null) result = caseIID(process);
				if (result == null) result = caseINamed(process);
				if (result == null) result = caseIDescribed(process);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ProcessesPackage.PROCESS_NODE: {
				ProcessNode processNode = (ProcessNode)theEObject;
				T result = caseProcessNode(processNode);
				if (result == null) result = caseIModelNode(processNode);
				if (result == null) result = caseISpecmatePositionableModelObject(processNode);
				if (result == null) result = caseISpecmateModelObject(processNode);
				if (result == null) result = caseIContainer(processNode);
				if (result == null) result = caseIContentElement(processNode);
				if (result == null) result = caseIID(processNode);
				if (result == null) result = caseINamed(processNode);
				if (result == null) result = caseIDescribed(processNode);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ProcessesPackage.PROCESS_STEP: {
				ProcessStep processStep = (ProcessStep)theEObject;
				T result = caseProcessStep(processStep);
				if (result == null) result = caseProcessNode(processStep);
				if (result == null) result = caseIModelNode(processStep);
				if (result == null) result = caseISpecmatePositionableModelObject(processStep);
				if (result == null) result = caseISpecmateModelObject(processStep);
				if (result == null) result = caseIContainer(processStep);
				if (result == null) result = caseIContentElement(processStep);
				if (result == null) result = caseIID(processStep);
				if (result == null) result = caseINamed(processStep);
				if (result == null) result = caseIDescribed(processStep);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ProcessesPackage.PROCESS_DECISION: {
				ProcessDecision processDecision = (ProcessDecision)theEObject;
				T result = caseProcessDecision(processDecision);
				if (result == null) result = caseProcessNode(processDecision);
				if (result == null) result = caseIModelNode(processDecision);
				if (result == null) result = caseISpecmatePositionableModelObject(processDecision);
				if (result == null) result = caseISpecmateModelObject(processDecision);
				if (result == null) result = caseIContainer(processDecision);
				if (result == null) result = caseIContentElement(processDecision);
				if (result == null) result = caseIID(processDecision);
				if (result == null) result = caseINamed(processDecision);
				if (result == null) result = caseIDescribed(processDecision);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ProcessesPackage.PROCESS_CONNECTION: {
				ProcessConnection processConnection = (ProcessConnection)theEObject;
				T result = caseProcessConnection(processConnection);
				if (result == null) result = caseIModelConnection(processConnection);
				if (result == null) result = caseISpecmateModelObject(processConnection);
				if (result == null) result = caseIContainer(processConnection);
				if (result == null) result = caseIContentElement(processConnection);
				if (result == null) result = caseIID(processConnection);
				if (result == null) result = caseINamed(processConnection);
				if (result == null) result = caseIDescribed(processConnection);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			default: return defaultCase(theEObject);
		}
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Process</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Process</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseProcess(com.specmate.model.processes.Process object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Process Node</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Process Node</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseProcessNode(ProcessNode object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Process Step</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Process Step</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseProcessStep(ProcessStep object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Process Decision</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Process Decision</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseProcessDecision(ProcessDecision object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Process Connection</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Process Connection</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseProcessConnection(ProcessConnection object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>IID</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>IID</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseIID(IID object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>INamed</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>INamed</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseINamed(INamed object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>IDescribed</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>IDescribed</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseIDescribed(IDescribed object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>IContent Element</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>IContent Element</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseIContentElement(IContentElement object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>IContainer</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>IContainer</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseIContainer(IContainer object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>ISpecmate Model Object</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>ISpecmate Model Object</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseISpecmateModelObject(ISpecmateModelObject object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>ISpecmate Positionable Model Object</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>ISpecmate Positionable Model Object</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseISpecmatePositionableModelObject(ISpecmatePositionableModelObject object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>IModel Node</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>IModel Node</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseIModelNode(IModelNode object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>IModel Connection</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>IModel Connection</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseIModelConnection(IModelConnection object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>EObject</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch, but this is the last case anyway.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>EObject</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject)
	 * @generated
	 */
	@Override
	public T defaultCase(EObject object) {
		return null;
	}

} //ProcessesSwitch
