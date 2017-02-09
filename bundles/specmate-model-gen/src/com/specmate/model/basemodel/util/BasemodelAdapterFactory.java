/**
 */
package com.specmate.model.basemodel.util;

import com.specmate.model.basemodel.*;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;

import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * The <b>Adapter Factory</b> for the model.
 * It provides an adapter <code>createXXX</code> method for each class of the model.
 * <!-- end-user-doc -->
 * @see com.specmate.model.basemodel.BasemodelPackage
 * @generated
 */
public class BasemodelAdapterFactory extends AdapterFactoryImpl {
	/**
	 * The cached model package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static BasemodelPackage modelPackage;

	/**
	 * Creates an instance of the adapter factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public BasemodelAdapterFactory() {
		if (modelPackage == null) {
			modelPackage = BasemodelPackage.eINSTANCE;
		}
	}

	/**
	 * Returns whether this factory is applicable for the type of the object.
	 * <!-- begin-user-doc -->
	 * This implementation returns <code>true</code> if the object is either the model's package or is an instance object of the model.
	 * <!-- end-user-doc -->
	 * @return whether this factory is applicable for the type of the object.
	 * @generated
	 */
	@Override
	public boolean isFactoryForType(Object object) {
		if (object == modelPackage) {
			return true;
		}
		if (object instanceof EObject) {
			return ((EObject)object).eClass().getEPackage() == modelPackage;
		}
		return false;
	}

	/**
	 * The switch that delegates to the <code>createXXX</code> methods.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected BasemodelSwitch<Adapter> modelSwitch =
		new BasemodelSwitch<Adapter>() {
			@Override
			public Adapter caseINamed(INamed object) {
				return createINamedAdapter();
			}
			@Override
			public Adapter caseIDescribed(IDescribed object) {
				return createIDescribedAdapter();
			}
			@Override
			public Adapter caseIID(IID object) {
				return createIIDAdapter();
			}
			@Override
			public Adapter caseIContentElement(IContentElement object) {
				return createIContentElementAdapter();
			}
			@Override
			public Adapter caseIUIInfo(IUIInfo object) {
				return createIUIInfoAdapter();
			}
			@Override
			public Adapter caseIUIContentElement(IUIContentElement object) {
				return createIUIContentElementAdapter();
			}
			@Override
			public Adapter caseIContainer(IContainer object) {
				return createIContainerAdapter();
			}
			@Override
			public Adapter caseIAnnotationContainer(IAnnotationContainer object) {
				return createIAnnotationContainerAdapter();
			}
			@Override
			public Adapter caseIAnnotation(IAnnotation object) {
				return createIAnnotationAdapter();
			}
			@Override
			public Adapter caseUIAnnotation(UIAnnotation object) {
				return createUIAnnotationAdapter();
			}
			@Override
			public Adapter caseBaseModelNode(BaseModelNode object) {
				return createBaseModelNodeAdapter();
			}
			@Override
			public Adapter defaultCase(EObject object) {
				return createEObjectAdapter();
			}
		};

	/**
	 * Creates an adapter for the <code>target</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param target the object to adapt.
	 * @return the adapter for the <code>target</code>.
	 * @generated
	 */
	@Override
	public Adapter createAdapter(Notifier target) {
		return modelSwitch.doSwitch((EObject)target);
	}


	/**
	 * Creates a new adapter for an object of class '{@link com.specmate.model.basemodel.INamed <em>INamed</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.specmate.model.basemodel.INamed
	 * @generated
	 */
	public Adapter createINamedAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.specmate.model.basemodel.IDescribed <em>IDescribed</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.specmate.model.basemodel.IDescribed
	 * @generated
	 */
	public Adapter createIDescribedAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.specmate.model.basemodel.IID <em>IID</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.specmate.model.basemodel.IID
	 * @generated
	 */
	public Adapter createIIDAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.specmate.model.basemodel.IContentElement <em>IContent Element</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.specmate.model.basemodel.IContentElement
	 * @generated
	 */
	public Adapter createIContentElementAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.specmate.model.basemodel.IUIInfo <em>IUI Info</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.specmate.model.basemodel.IUIInfo
	 * @generated
	 */
	public Adapter createIUIInfoAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.specmate.model.basemodel.IUIContentElement <em>IUI Content Element</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.specmate.model.basemodel.IUIContentElement
	 * @generated
	 */
	public Adapter createIUIContentElementAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.specmate.model.basemodel.IContainer <em>IContainer</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.specmate.model.basemodel.IContainer
	 * @generated
	 */
	public Adapter createIContainerAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.specmate.model.basemodel.IAnnotationContainer <em>IAnnotation Container</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.specmate.model.basemodel.IAnnotationContainer
	 * @generated
	 */
	public Adapter createIAnnotationContainerAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.specmate.model.basemodel.IAnnotation <em>IAnnotation</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.specmate.model.basemodel.IAnnotation
	 * @generated
	 */
	public Adapter createIAnnotationAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.specmate.model.basemodel.UIAnnotation <em>UI Annotation</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.specmate.model.basemodel.UIAnnotation
	 * @generated
	 */
	public Adapter createUIAnnotationAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.specmate.model.basemodel.BaseModelNode <em>Base Model Node</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.specmate.model.basemodel.BaseModelNode
	 * @generated
	 */
	public Adapter createBaseModelNodeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for the default case.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @generated
	 */
	public Adapter createEObjectAdapter() {
		return null;
	}

} //BasemodelAdapterFactory
