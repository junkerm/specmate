/**
 */
package com.specmate.model.processes.util;

import com.specmate.model.base.IContainer;
import com.specmate.model.base.IContentElement;
import com.specmate.model.base.IDescribed;
import com.specmate.model.base.IID;
import com.specmate.model.base.INamed;

import com.specmate.model.processes.ProcessesPackage;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;

import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * The <b>Adapter Factory</b> for the model.
 * It provides an adapter <code>createXXX</code> method for each class of the model.
 * <!-- end-user-doc -->
 * @see com.specmate.model.processes.ProcessesPackage
 * @generated
 */
public class ProcessesAdapterFactory extends AdapterFactoryImpl {
	/**
	 * The cached model package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static ProcessesPackage modelPackage;

	/**
	 * Creates an instance of the adapter factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ProcessesAdapterFactory() {
		if (modelPackage == null) {
			modelPackage = ProcessesPackage.eINSTANCE;
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
	protected ProcessesSwitch<Adapter> modelSwitch =
		new ProcessesSwitch<Adapter>() {
			@Override
			public Adapter caseProcess(com.specmate.model.processes.Process object) {
				return createProcessAdapter();
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
	 * Creates a new adapter for an object of class '{@link com.specmate.model.processes.Process <em>Process</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.specmate.model.processes.Process
	 * @generated
	 */
	public Adapter createProcessAdapter() {
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

} //ProcessesAdapterFactory
