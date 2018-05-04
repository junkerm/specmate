/**
 */
package com.specmate.migration.test.baseline.testmodel.artefact.util;

import com.specmate.migration.test.baseline.testmodel.artefact.*;

import com.specmate.migration.test.baseline.testmodel.base.IContainer;
import com.specmate.migration.test.baseline.testmodel.base.IContentElement;
import com.specmate.migration.test.baseline.testmodel.base.IID;
import com.specmate.migration.test.baseline.testmodel.base.IModifiable;
import com.specmate.migration.test.baseline.testmodel.base.ITestable;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;

import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * The <b>Adapter Factory</b> for the model.
 * It provides an adapter <code>createXXX</code> method for each class of the model.
 * <!-- end-user-doc -->
 * @see com.specmate.migration.test.baseline.testmodel.artefact.ArtefactPackage
 * @generated
 */
public class ArtefactAdapterFactory extends AdapterFactoryImpl {
	/**
	 * The cached model package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static ArtefactPackage modelPackage;

	/**
	 * Creates an instance of the adapter factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ArtefactAdapterFactory() {
		if (modelPackage == null) {
			modelPackage = ArtefactPackage.eINSTANCE;
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
	protected ArtefactSwitch<Adapter> modelSwitch =
		new ArtefactSwitch<Adapter>() {
			@Override
			public Adapter caseDiagram(Diagram object) {
				return createDiagramAdapter();
			}
			@Override
			public Adapter caseFile(File object) {
				return createFileAdapter();
			}
			@Override
			public Adapter caseITestable(ITestable object) {
				return createITestableAdapter();
			}
			@Override
			public Adapter caseIModifiable(IModifiable object) {
				return createIModifiableAdapter();
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
	 * Creates a new adapter for an object of class '{@link com.specmate.migration.test.baseline.testmodel.artefact.Diagram <em>Diagram</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.specmate.migration.test.baseline.testmodel.artefact.Diagram
	 * @generated
	 */
	public Adapter createDiagramAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.specmate.migration.test.baseline.testmodel.artefact.File <em>File</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.specmate.migration.test.baseline.testmodel.artefact.File
	 * @generated
	 */
	public Adapter createFileAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.specmate.migration.test.baseline.testmodel.base.ITestable <em>ITestable</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.specmate.migration.test.baseline.testmodel.base.ITestable
	 * @generated
	 */
	public Adapter createITestableAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.specmate.migration.test.baseline.testmodel.base.IModifiable <em>IModifiable</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.specmate.migration.test.baseline.testmodel.base.IModifiable
	 * @generated
	 */
	public Adapter createIModifiableAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.specmate.migration.test.baseline.testmodel.base.IID <em>IID</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.specmate.migration.test.baseline.testmodel.base.IID
	 * @generated
	 */
	public Adapter createIIDAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.specmate.migration.test.baseline.testmodel.base.IContentElement <em>IContent Element</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.specmate.migration.test.baseline.testmodel.base.IContentElement
	 * @generated
	 */
	public Adapter createIContentElementAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.specmate.migration.test.baseline.testmodel.base.IContainer <em>IContainer</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.specmate.migration.test.baseline.testmodel.base.IContainer
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

} //ArtefactAdapterFactory
