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

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;

import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * The <b>Adapter Factory</b> for the model.
 * It provides an adapter <code>createXXX</code> method for each class of the model.
 * <!-- end-user-doc -->
 * @see com.specmate.model.bdd.BddPackage
 * @generated
 */
public class BddAdapterFactory extends AdapterFactoryImpl {
	/**
	 * The cached model package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static BddPackage modelPackage;

	/**
	 * Creates an instance of the adapter factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public BddAdapterFactory() {
		if (modelPackage == null) {
			modelPackage = BddPackage.eINSTANCE;
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
	protected BddSwitch<Adapter> modelSwitch =
		new BddSwitch<Adapter>() {
			@Override
			public Adapter caseBDDNode(BDDNode object) {
				return createBDDNodeAdapter();
			}
			@Override
			public Adapter caseBDDTerminalNode(BDDTerminalNode object) {
				return createBDDTerminalNodeAdapter();
			}
			@Override
			public Adapter caseBDDNoTerminalNode(BDDNoTerminalNode object) {
				return createBDDNoTerminalNodeAdapter();
			}
			@Override
			public Adapter caseBDDConnection(BDDConnection object) {
				return createBDDConnectionAdapter();
			}
			@Override
			public Adapter caseBDDModel(BDDModel object) {
				return createBDDModelAdapter();
			}
			@Override
			public Adapter caseIID(IID object) {
				return createIIDAdapter();
			}
			@Override
			public Adapter caseINamed(INamed object) {
				return createINamedAdapter();
			}
			@Override
			public Adapter caseIDescribed(IDescribed object) {
				return createIDescribedAdapter();
			}
			@Override
			public Adapter caseIContentElement(IContentElement object) {
				return createIContentElementAdapter();
			}
			@Override
			public Adapter caseIContainer(IContainer object) {
				return createIContainerAdapter();
			}
			@Override
			public Adapter caseITracingElement(ITracingElement object) {
				return createITracingElementAdapter();
			}
			@Override
			public Adapter caseISpecmateModelObject(ISpecmateModelObject object) {
				return createISpecmateModelObjectAdapter();
			}
			@Override
			public Adapter caseISpecmatePositionableModelObject(ISpecmatePositionableModelObject object) {
				return createISpecmatePositionableModelObjectAdapter();
			}
			@Override
			public Adapter caseIModelNode(IModelNode object) {
				return createIModelNodeAdapter();
			}
			@Override
			public Adapter caseIModelConnection(IModelConnection object) {
				return createIModelConnectionAdapter();
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
	 * Creates a new adapter for an object of class '{@link com.specmate.model.bdd.BDDNode <em>BDD Node</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.specmate.model.bdd.BDDNode
	 * @generated
	 */
	public Adapter createBDDNodeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.specmate.model.bdd.BDDTerminalNode <em>BDD Terminal Node</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.specmate.model.bdd.BDDTerminalNode
	 * @generated
	 */
	public Adapter createBDDTerminalNodeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.specmate.model.bdd.BDDNoTerminalNode <em>BDD No Terminal Node</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.specmate.model.bdd.BDDNoTerminalNode
	 * @generated
	 */
	public Adapter createBDDNoTerminalNodeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.specmate.model.bdd.BDDConnection <em>BDD Connection</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.specmate.model.bdd.BDDConnection
	 * @generated
	 */
	public Adapter createBDDConnectionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.specmate.model.bdd.BDDModel <em>BDD Model</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.specmate.model.bdd.BDDModel
	 * @generated
	 */
	public Adapter createBDDModelAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.specmate.model.base.IID <em>IID</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.specmate.model.base.IID
	 * @generated
	 */
	public Adapter createIIDAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.specmate.model.base.INamed <em>INamed</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.specmate.model.base.INamed
	 * @generated
	 */
	public Adapter createINamedAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.specmate.model.base.IDescribed <em>IDescribed</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.specmate.model.base.IDescribed
	 * @generated
	 */
	public Adapter createIDescribedAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.specmate.model.base.IContentElement <em>IContent Element</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.specmate.model.base.IContentElement
	 * @generated
	 */
	public Adapter createIContentElementAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.specmate.model.base.IContainer <em>IContainer</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.specmate.model.base.IContainer
	 * @generated
	 */
	public Adapter createIContainerAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.specmate.model.base.ITracingElement <em>ITracing Element</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.specmate.model.base.ITracingElement
	 * @generated
	 */
	public Adapter createITracingElementAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.specmate.model.base.ISpecmateModelObject <em>ISpecmate Model Object</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.specmate.model.base.ISpecmateModelObject
	 * @generated
	 */
	public Adapter createISpecmateModelObjectAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.specmate.model.base.ISpecmatePositionableModelObject <em>ISpecmate Positionable Model Object</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.specmate.model.base.ISpecmatePositionableModelObject
	 * @generated
	 */
	public Adapter createISpecmatePositionableModelObjectAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.specmate.model.base.IModelNode <em>IModel Node</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.specmate.model.base.IModelNode
	 * @generated
	 */
	public Adapter createIModelNodeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.specmate.model.base.IModelConnection <em>IModel Connection</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.specmate.model.base.IModelConnection
	 * @generated
	 */
	public Adapter createIModelConnectionAdapter() {
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

} //BddAdapterFactory
