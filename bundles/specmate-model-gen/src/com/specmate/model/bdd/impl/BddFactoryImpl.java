/**
 */
package com.specmate.model.bdd.impl;

import com.specmate.model.bdd.*;

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
public class BddFactoryImpl extends EFactoryImpl implements BddFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static BddFactory init() {
		try {
			BddFactory theBddFactory = (BddFactory)EPackage.Registry.INSTANCE.getEFactory(BddPackage.eNS_URI);
			if (theBddFactory != null) {
				return theBddFactory;
			}
		}
		catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new BddFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public BddFactoryImpl() {
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
			case BddPackage.BDD_NODE: return (EObject)createBDDNode();
			case BddPackage.BDD_TERMINAL_NODE: return (EObject)createBDDTerminalNode();
			case BddPackage.BDD_NO_TERMINAL_NODE: return (EObject)createBDDNoTerminalNode();
			case BddPackage.BDD_CONNECTION: return (EObject)createBDDConnection();
			case BddPackage.BDD_MODEL: return (EObject)createBDDModel();
			default:
				throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public BDDNode createBDDNode() {
		BDDNodeImpl bddNode = new BDDNodeImpl();
		return bddNode;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public BDDTerminalNode createBDDTerminalNode() {
		BDDTerminalNodeImpl bddTerminalNode = new BDDTerminalNodeImpl();
		return bddTerminalNode;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public BDDNoTerminalNode createBDDNoTerminalNode() {
		BDDNoTerminalNodeImpl bddNoTerminalNode = new BDDNoTerminalNodeImpl();
		return bddNoTerminalNode;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public BDDConnection createBDDConnection() {
		BDDConnectionImpl bddConnection = new BDDConnectionImpl();
		return bddConnection;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public BDDModel createBDDModel() {
		BDDModelImpl bddModel = new BDDModelImpl();
		return bddModel;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public BddPackage getBddPackage() {
		return (BddPackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static BddPackage getPackage() {
		return BddPackage.eINSTANCE;
	}

} //BddFactoryImpl
