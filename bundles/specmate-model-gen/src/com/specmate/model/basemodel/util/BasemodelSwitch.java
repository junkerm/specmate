/**
 */
package com.specmate.model.basemodel.util;

import com.specmate.model.basemodel.*;

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
 * @see com.specmate.model.basemodel.BasemodelPackage
 * @generated
 */
public class BasemodelSwitch<T> extends Switch<T> {
	/**
	 * The cached model package
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static BasemodelPackage modelPackage;

	/**
	 * Creates an instance of the switch.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public BasemodelSwitch() {
		if (modelPackage == null) {
			modelPackage = BasemodelPackage.eINSTANCE;
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
			case BasemodelPackage.INAMED: {
				INamed iNamed = (INamed)theEObject;
				T result = caseINamed(iNamed);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case BasemodelPackage.IDESCRIBED: {
				IDescribed iDescribed = (IDescribed)theEObject;
				T result = caseIDescribed(iDescribed);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case BasemodelPackage.IID: {
				IID iid = (IID)theEObject;
				T result = caseIID(iid);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case BasemodelPackage.ICONTENT_ELEMENT: {
				IContentElement iContentElement = (IContentElement)theEObject;
				T result = caseIContentElement(iContentElement);
				if (result == null) result = caseIID(iContentElement);
				if (result == null) result = caseINamed(iContentElement);
				if (result == null) result = caseIDescribed(iContentElement);
				if (result == null) result = caseIAnnotationContainer(iContentElement);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case BasemodelPackage.IUI_INFO: {
				IUIInfo iuiInfo = (IUIInfo)theEObject;
				T result = caseIUIInfo(iuiInfo);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case BasemodelPackage.IUI_CONTENT_ELEMENT: {
				IUIContentElement iuiContentElement = (IUIContentElement)theEObject;
				T result = caseIUIContentElement(iuiContentElement);
				if (result == null) result = caseIContentElement(iuiContentElement);
				if (result == null) result = caseIUIInfo(iuiContentElement);
				if (result == null) result = caseIID(iuiContentElement);
				if (result == null) result = caseINamed(iuiContentElement);
				if (result == null) result = caseIDescribed(iuiContentElement);
				if (result == null) result = caseIAnnotationContainer(iuiContentElement);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case BasemodelPackage.ICONTAINER: {
				IContainer iContainer = (IContainer)theEObject;
				T result = caseIContainer(iContainer);
				if (result == null) result = caseIContentElement(iContainer);
				if (result == null) result = caseIID(iContainer);
				if (result == null) result = caseINamed(iContainer);
				if (result == null) result = caseIDescribed(iContainer);
				if (result == null) result = caseIAnnotationContainer(iContainer);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case BasemodelPackage.IANNOTATION_CONTAINER: {
				IAnnotationContainer iAnnotationContainer = (IAnnotationContainer)theEObject;
				T result = caseIAnnotationContainer(iAnnotationContainer);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case BasemodelPackage.IANNOTATION: {
				IAnnotation iAnnotation = (IAnnotation)theEObject;
				T result = caseIAnnotation(iAnnotation);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case BasemodelPackage.UI_ANNOTATION: {
				UIAnnotation uiAnnotation = (UIAnnotation)theEObject;
				T result = caseUIAnnotation(uiAnnotation);
				if (result == null) result = caseIUIInfo(uiAnnotation);
				if (result == null) result = caseIAnnotation(uiAnnotation);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case BasemodelPackage.BASE_MODEL_NODE: {
				BaseModelNode baseModelNode = (BaseModelNode)theEObject;
				T result = caseBaseModelNode(baseModelNode);
				if (result == null) result = caseIContainer(baseModelNode);
				if (result == null) result = caseIContentElement(baseModelNode);
				if (result == null) result = caseIID(baseModelNode);
				if (result == null) result = caseINamed(baseModelNode);
				if (result == null) result = caseIDescribed(baseModelNode);
				if (result == null) result = caseIAnnotationContainer(baseModelNode);
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
	 * Returns the result of interpreting the object as an instance of '<em>IUI Info</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>IUI Info</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseIUIInfo(IUIInfo object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>IUI Content Element</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>IUI Content Element</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseIUIContentElement(IUIContentElement object) {
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
	 * Returns the result of interpreting the object as an instance of '<em>IAnnotation Container</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>IAnnotation Container</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseIAnnotationContainer(IAnnotationContainer object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>IAnnotation</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>IAnnotation</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseIAnnotation(IAnnotation object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>UI Annotation</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>UI Annotation</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseUIAnnotation(UIAnnotation object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Base Model Node</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Base Model Node</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseBaseModelNode(BaseModelNode object) {
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

} //BasemodelSwitch
