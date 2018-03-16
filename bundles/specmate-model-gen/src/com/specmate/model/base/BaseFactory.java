/**
 */
package com.specmate.model.base;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see com.specmate.model.base.BasePackage
 * @generated
 */
public interface BaseFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	BaseFactory eINSTANCE = com.specmate.model.base.impl.BaseFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>Folder</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Folder</em>'.
	 * @generated
	 */
	Folder createFolder();

	/**
	 * Returns a new object of class '<em>IExternal</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>IExternal</em>'.
	 * @generated
	 */
	IExternal createIExternal();

	/**
	 * Returns a new object of class '<em>IModel Connection</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>IModel Connection</em>'.
	 * @generated
	 */
	IModelConnection createIModelConnection();

	/**
	 * Returns a new object of class '<em>IModel Node</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>IModel Node</em>'.
	 * @generated
	 */
	IModelNode createIModelNode();

	/**
	 * Returns a new object of class '<em>ITracing Element</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>ITracing Element</em>'.
	 * @generated
	 */
	ITracingElement createITracingElement();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	BasePackage getBasePackage();

} //BaseFactory
