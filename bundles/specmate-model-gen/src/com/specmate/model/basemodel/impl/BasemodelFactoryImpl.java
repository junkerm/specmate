/**
 */
package com.specmate.model.basemodel.impl;

import com.specmate.model.basemodel.*;

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
public class BasemodelFactoryImpl extends EFactoryImpl implements BasemodelFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static BasemodelFactory init() {
		try {
			BasemodelFactory theBasemodelFactory = (BasemodelFactory)EPackage.Registry.INSTANCE.getEFactory(BasemodelPackage.eNS_URI);
			if (theBasemodelFactory != null) {
				return theBasemodelFactory;
			}
		}
		catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new BasemodelFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public BasemodelFactoryImpl() {
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
			case BasemodelPackage.IUI_INFO: return (EObject)createIUIInfo();
			case BasemodelPackage.UI_ANNOTATION: return (EObject)createUIAnnotation();
			case BasemodelPackage.BASE_MODEL_NODE: return (EObject)createBaseModelNode();
			default:
				throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IUIInfo createIUIInfo() {
		IUIInfoImpl iuiInfo = new IUIInfoImpl();
		return iuiInfo;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public UIAnnotation createUIAnnotation() {
		UIAnnotationImpl uiAnnotation = new UIAnnotationImpl();
		return uiAnnotation;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public BaseModelNode createBaseModelNode() {
		BaseModelNodeImpl baseModelNode = new BaseModelNodeImpl();
		return baseModelNode;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public BasemodelPackage getBasemodelPackage() {
		return (BasemodelPackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static BasemodelPackage getPackage() {
		return BasemodelPackage.eINSTANCE;
	}

} //BasemodelFactoryImpl
