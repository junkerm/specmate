/**
 */
package com.specmate.model.base.util;

import com.specmate.model.base.*;

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
 * @see com.specmate.model.base.BasePackage
 * @generated
 */
public class BaseSwitch<T> extends Switch<T> {
	/**
	 * The cached model package
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static BasePackage modelPackage;

	/**
	 * Creates an instance of the switch.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public BaseSwitch() {
		if (modelPackage == null) {
			modelPackage = BasePackage.eINSTANCE;
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
			case BasePackage.INAMED: {
				INamed iNamed = (INamed)theEObject;
				T result = caseINamed(iNamed);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case BasePackage.IDESCRIBED: {
				IDescribed iDescribed = (IDescribed)theEObject;
				T result = caseIDescribed(iDescribed);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case BasePackage.IID: {
				IID iid = (IID)theEObject;
				T result = caseIID(iid);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case BasePackage.ICONTENT_ELEMENT: {
				IContentElement iContentElement = (IContentElement)theEObject;
				T result = caseIContentElement(iContentElement);
				if (result == null) result = caseIID(iContentElement);
				if (result == null) result = caseINamed(iContentElement);
				if (result == null) result = caseIDescribed(iContentElement);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case BasePackage.ICONTAINER: {
				IContainer iContainer = (IContainer)theEObject;
				T result = caseIContainer(iContainer);
				if (result == null) result = caseIContentElement(iContainer);
				if (result == null) result = caseIID(iContainer);
				if (result == null) result = caseINamed(iContainer);
				if (result == null) result = caseIDescribed(iContainer);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case BasePackage.ISPECMATE_MODEL_OBJECT: {
				ISpecmateModelObject iSpecmateModelObject = (ISpecmateModelObject)theEObject;
				T result = caseISpecmateModelObject(iSpecmateModelObject);
				if (result == null) result = caseIContainer(iSpecmateModelObject);
				if (result == null) result = caseITracingElement(iSpecmateModelObject);
				if (result == null) result = caseIContentElement(iSpecmateModelObject);
				if (result == null) result = caseIID(iSpecmateModelObject);
				if (result == null) result = caseINamed(iSpecmateModelObject);
				if (result == null) result = caseIDescribed(iSpecmateModelObject);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case BasePackage.FOLDER: {
				Folder folder = (Folder)theEObject;
				T result = caseFolder(folder);
				if (result == null) result = caseISpecmateModelObject(folder);
				if (result == null) result = caseIContainer(folder);
				if (result == null) result = caseITracingElement(folder);
				if (result == null) result = caseIContentElement(folder);
				if (result == null) result = caseIID(folder);
				if (result == null) result = caseINamed(folder);
				if (result == null) result = caseIDescribed(folder);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case BasePackage.IPOSITIONABLE: {
				IPositionable iPositionable = (IPositionable)theEObject;
				T result = caseIPositionable(iPositionable);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case BasePackage.IEXTERNAL: {
				IExternal iExternal = (IExternal)theEObject;
				T result = caseIExternal(iExternal);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case BasePackage.ISPECMATE_POSITIONABLE_MODEL_OBJECT: {
				ISpecmatePositionableModelObject iSpecmatePositionableModelObject = (ISpecmatePositionableModelObject)theEObject;
				T result = caseISpecmatePositionableModelObject(iSpecmatePositionableModelObject);
				if (result == null) result = caseISpecmateModelObject(iSpecmatePositionableModelObject);
				if (result == null) result = caseIContainer(iSpecmatePositionableModelObject);
				if (result == null) result = caseITracingElement(iSpecmatePositionableModelObject);
				if (result == null) result = caseIContentElement(iSpecmatePositionableModelObject);
				if (result == null) result = caseIID(iSpecmatePositionableModelObject);
				if (result == null) result = caseINamed(iSpecmatePositionableModelObject);
				if (result == null) result = caseIDescribed(iSpecmatePositionableModelObject);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case BasePackage.IMODEL_CONNECTION: {
				IModelConnection iModelConnection = (IModelConnection)theEObject;
				T result = caseIModelConnection(iModelConnection);
				if (result == null) result = caseISpecmateModelObject(iModelConnection);
				if (result == null) result = caseIContainer(iModelConnection);
				if (result == null) result = caseITracingElement(iModelConnection);
				if (result == null) result = caseIContentElement(iModelConnection);
				if (result == null) result = caseIID(iModelConnection);
				if (result == null) result = caseINamed(iModelConnection);
				if (result == null) result = caseIDescribed(iModelConnection);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case BasePackage.IMODEL_NODE: {
				IModelNode iModelNode = (IModelNode)theEObject;
				T result = caseIModelNode(iModelNode);
				if (result == null) result = caseISpecmatePositionableModelObject(iModelNode);
				if (result == null) result = caseISpecmateModelObject(iModelNode);
				if (result == null) result = caseIContainer(iModelNode);
				if (result == null) result = caseITracingElement(iModelNode);
				if (result == null) result = caseIContentElement(iModelNode);
				if (result == null) result = caseIID(iModelNode);
				if (result == null) result = caseINamed(iModelNode);
				if (result == null) result = caseIDescribed(iModelNode);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case BasePackage.ITRACING_ELEMENT: {
				ITracingElement iTracingElement = (ITracingElement)theEObject;
				T result = caseITracingElement(iTracingElement);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			default: return defaultCase(theEObject);
		}
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
	 * Returns the result of interpreting the object as an instance of '<em>Folder</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Folder</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseFolder(Folder object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>IPositionable</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>IPositionable</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseIPositionable(IPositionable object) {
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
	 * Returns the result of interpreting the object as an instance of '<em>ITracing Element</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>ITracing Element</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseITracingElement(ITracingElement object) {
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

} //BaseSwitch
