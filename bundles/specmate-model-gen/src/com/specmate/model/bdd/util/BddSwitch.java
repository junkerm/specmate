/**
 */
package com.specmate.model.bdd.util;

import com.specmate.model.base.IContainer;
import com.specmate.model.base.IContentElement;
import com.specmate.model.base.IDescribed;
import com.specmate.model.base.IID;
import com.specmate.model.base.IModelConnection;
import com.specmate.model.base.IModelNode;
import com.specmate.model.base.INamed;
import com.specmate.model.base.ISpecmateModelObject;
import com.specmate.model.base.ISpecmatePositionableModelObject;
import com.specmate.model.base.ITracingElement;

import com.specmate.model.bdd.*;

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
 * @see com.specmate.model.bdd.BddPackage
 * @generated
 */
public class BddSwitch<T> extends Switch<T> {
	/**
	 * The cached model package
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static BddPackage modelPackage;

	/**
	 * Creates an instance of the switch.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public BddSwitch() {
		if (modelPackage == null) {
			modelPackage = BddPackage.eINSTANCE;
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
			case BddPackage.BDD_NODE: {
				BDDNode bddNode = (BDDNode)theEObject;
				T result = caseBDDNode(bddNode);
				if (result == null) result = caseIModelNode(bddNode);
				if (result == null) result = caseISpecmatePositionableModelObject(bddNode);
				if (result == null) result = caseISpecmateModelObject(bddNode);
				if (result == null) result = caseIContainer(bddNode);
				if (result == null) result = caseITracingElement(bddNode);
				if (result == null) result = caseIContentElement(bddNode);
				if (result == null) result = caseIID(bddNode);
				if (result == null) result = caseINamed(bddNode);
				if (result == null) result = caseIDescribed(bddNode);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case BddPackage.BDD_TERMINAL_NODE: {
				BDDTerminalNode bddTerminalNode = (BDDTerminalNode)theEObject;
				T result = caseBDDTerminalNode(bddTerminalNode);
				if (result == null) result = caseBDDNode(bddTerminalNode);
				if (result == null) result = caseIModelNode(bddTerminalNode);
				if (result == null) result = caseISpecmatePositionableModelObject(bddTerminalNode);
				if (result == null) result = caseISpecmateModelObject(bddTerminalNode);
				if (result == null) result = caseIContainer(bddTerminalNode);
				if (result == null) result = caseITracingElement(bddTerminalNode);
				if (result == null) result = caseIContentElement(bddTerminalNode);
				if (result == null) result = caseIID(bddTerminalNode);
				if (result == null) result = caseINamed(bddTerminalNode);
				if (result == null) result = caseIDescribed(bddTerminalNode);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case BddPackage.BDD_NO_TERMINAL_NODE: {
				BDDNoTerminalNode bddNoTerminalNode = (BDDNoTerminalNode)theEObject;
				T result = caseBDDNoTerminalNode(bddNoTerminalNode);
				if (result == null) result = caseBDDNode(bddNoTerminalNode);
				if (result == null) result = caseIModelNode(bddNoTerminalNode);
				if (result == null) result = caseISpecmatePositionableModelObject(bddNoTerminalNode);
				if (result == null) result = caseISpecmateModelObject(bddNoTerminalNode);
				if (result == null) result = caseIContainer(bddNoTerminalNode);
				if (result == null) result = caseITracingElement(bddNoTerminalNode);
				if (result == null) result = caseIContentElement(bddNoTerminalNode);
				if (result == null) result = caseIID(bddNoTerminalNode);
				if (result == null) result = caseINamed(bddNoTerminalNode);
				if (result == null) result = caseIDescribed(bddNoTerminalNode);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case BddPackage.BDD_CONNECTION: {
				BDDConnection bddConnection = (BDDConnection)theEObject;
				T result = caseBDDConnection(bddConnection);
				if (result == null) result = caseIModelConnection(bddConnection);
				if (result == null) result = caseISpecmateModelObject(bddConnection);
				if (result == null) result = caseIContainer(bddConnection);
				if (result == null) result = caseITracingElement(bddConnection);
				if (result == null) result = caseIContentElement(bddConnection);
				if (result == null) result = caseIID(bddConnection);
				if (result == null) result = caseINamed(bddConnection);
				if (result == null) result = caseIDescribed(bddConnection);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case BddPackage.BDD_MODEL: {
				BDDModel bddModel = (BDDModel)theEObject;
				T result = caseBDDModel(bddModel);
				if (result == null) result = caseISpecmateModelObject(bddModel);
				if (result == null) result = caseIContainer(bddModel);
				if (result == null) result = caseITracingElement(bddModel);
				if (result == null) result = caseIContentElement(bddModel);
				if (result == null) result = caseIID(bddModel);
				if (result == null) result = caseINamed(bddModel);
				if (result == null) result = caseIDescribed(bddModel);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			default: return defaultCase(theEObject);
		}
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>BDD Node</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>BDD Node</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseBDDNode(BDDNode object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>BDD Terminal Node</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>BDD Terminal Node</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseBDDTerminalNode(BDDTerminalNode object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>BDD No Terminal Node</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>BDD No Terminal Node</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseBDDNoTerminalNode(BDDNoTerminalNode object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>BDD Connection</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>BDD Connection</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseBDDConnection(BDDConnection object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>BDD Model</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>BDD Model</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseBDDModel(BDDModel object) {
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

} //BddSwitch
