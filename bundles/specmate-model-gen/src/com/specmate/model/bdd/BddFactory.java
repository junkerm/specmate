/**
 */
package com.specmate.model.bdd;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see com.specmate.model.bdd.BddPackage
 * @generated
 */
public interface BddFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	BddFactory eINSTANCE = com.specmate.model.bdd.impl.BddFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>BDD Node</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>BDD Node</em>'.
	 * @generated
	 */
	BDDNode createBDDNode();

	/**
	 * Returns a new object of class '<em>BDD Terminal Node</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>BDD Terminal Node</em>'.
	 * @generated
	 */
	BDDTerminalNode createBDDTerminalNode();

	/**
	 * Returns a new object of class '<em>BDD No Terminal Node</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>BDD No Terminal Node</em>'.
	 * @generated
	 */
	BDDNoTerminalNode createBDDNoTerminalNode();

	/**
	 * Returns a new object of class '<em>BDD Connection</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>BDD Connection</em>'.
	 * @generated
	 */
	BDDConnection createBDDConnection();

	/**
	 * Returns a new object of class '<em>BDD Model</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>BDD Model</em>'.
	 * @generated
	 */
	BDDModel createBDDModel();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	BddPackage getBddPackage();

} //BddFactory
