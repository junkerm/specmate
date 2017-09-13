/**
 */
package com.specmate.model.requirements.util;

import com.specmate.model.base.IContainer;
import com.specmate.model.base.IContentElement;
import com.specmate.model.base.IDescribed;
import com.specmate.model.base.IExternal;
import com.specmate.model.base.IID;
import com.specmate.model.base.INamed;
import com.specmate.model.base.ISpecmateModelObject;

import com.specmate.model.base.ISpecmatePositionableModelObject;
import com.specmate.model.requirements.*;

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
 * @see com.specmate.model.requirements.RequirementsPackage
 * @generated
 */
public class RequirementsSwitch<T> extends Switch<T> {
	/**
	 * The cached model package
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static RequirementsPackage modelPackage;

	/**
	 * Creates an instance of the switch.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public RequirementsSwitch() {
		if (modelPackage == null) {
			modelPackage = RequirementsPackage.eINSTANCE;
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
			case RequirementsPackage.REQUIREMENT: {
				Requirement requirement = (Requirement)theEObject;
				T result = caseRequirement(requirement);
				if (result == null) result = caseISpecmateModelObject(requirement);
				if (result == null) result = caseIExternal(requirement);
				if (result == null) result = caseIContainer(requirement);
				if (result == null) result = caseIContentElement(requirement);
				if (result == null) result = caseIID(requirement);
				if (result == null) result = caseINamed(requirement);
				if (result == null) result = caseIDescribed(requirement);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case RequirementsPackage.CEG_MODEL: {
				CEGModel cegModel = (CEGModel)theEObject;
				T result = caseCEGModel(cegModel);
				if (result == null) result = caseISpecmateModelObject(cegModel);
				if (result == null) result = caseIContainer(cegModel);
				if (result == null) result = caseIContentElement(cegModel);
				if (result == null) result = caseIID(cegModel);
				if (result == null) result = caseINamed(cegModel);
				if (result == null) result = caseIDescribed(cegModel);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case RequirementsPackage.CEG_NODE: {
				CEGNode cegNode = (CEGNode)theEObject;
				T result = caseCEGNode(cegNode);
				if (result == null) result = caseISpecmatePositionableModelObject(cegNode);
				if (result == null) result = caseISpecmateModelObject(cegNode);
				if (result == null) result = caseIContainer(cegNode);
				if (result == null) result = caseIContentElement(cegNode);
				if (result == null) result = caseIID(cegNode);
				if (result == null) result = caseINamed(cegNode);
				if (result == null) result = caseIDescribed(cegNode);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case RequirementsPackage.CEG_CONNECTION: {
				CEGConnection cegConnection = (CEGConnection)theEObject;
				T result = caseCEGConnection(cegConnection);
				if (result == null) result = caseISpecmateModelObject(cegConnection);
				if (result == null) result = caseIContainer(cegConnection);
				if (result == null) result = caseIContentElement(cegConnection);
				if (result == null) result = caseIID(cegConnection);
				if (result == null) result = caseINamed(cegConnection);
				if (result == null) result = caseIDescribed(cegConnection);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case RequirementsPackage.CEG_CAUSE_NODE: {
				CEGCauseNode cegCauseNode = (CEGCauseNode)theEObject;
				T result = caseCEGCauseNode(cegCauseNode);
				if (result == null) result = caseCEGNode(cegCauseNode);
				if (result == null) result = caseISpecmatePositionableModelObject(cegCauseNode);
				if (result == null) result = caseISpecmateModelObject(cegCauseNode);
				if (result == null) result = caseIContainer(cegCauseNode);
				if (result == null) result = caseIContentElement(cegCauseNode);
				if (result == null) result = caseIID(cegCauseNode);
				if (result == null) result = caseINamed(cegCauseNode);
				if (result == null) result = caseIDescribed(cegCauseNode);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case RequirementsPackage.CEG_EFFECT_NODE: {
				CEGEffectNode cegEffectNode = (CEGEffectNode)theEObject;
				T result = caseCEGEffectNode(cegEffectNode);
				if (result == null) result = caseCEGNode(cegEffectNode);
				if (result == null) result = caseISpecmatePositionableModelObject(cegEffectNode);
				if (result == null) result = caseISpecmateModelObject(cegEffectNode);
				if (result == null) result = caseIContainer(cegEffectNode);
				if (result == null) result = caseIContentElement(cegEffectNode);
				if (result == null) result = caseIID(cegEffectNode);
				if (result == null) result = caseINamed(cegEffectNode);
				if (result == null) result = caseIDescribed(cegEffectNode);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			default: return defaultCase(theEObject);
		}
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Requirement</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Requirement</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseRequirement(Requirement object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>CEG Model</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>CEG Model</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseCEGModel(CEGModel object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>CEG Node</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>CEG Node</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseCEGNode(CEGNode object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>CEG Connection</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>CEG Connection</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseCEGConnection(CEGConnection object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>CEG Cause Node</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>CEG Cause Node</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseCEGCauseNode(CEGCauseNode object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>CEG Effect Node</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>CEG Effect Node</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseCEGEffectNode(CEGEffectNode object) {
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
	 * Returns the result of interpreting the object as an instance of '<em>IExternal</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>IExternal</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseIExternal(IExternal object) {
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

} //RequirementsSwitch
