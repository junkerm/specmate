/**
 */
package com.specmate.model.basemodel;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see com.specmate.model.basemodel.BasemodelPackage
 * @generated
 */
public interface BasemodelFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	BasemodelFactory eINSTANCE = com.specmate.model.basemodel.impl.BasemodelFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>IUI Info</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>IUI Info</em>'.
	 * @generated
	 */
	IUIInfo createIUIInfo();

	/**
	 * Returns a new object of class '<em>UI Annotation</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>UI Annotation</em>'.
	 * @generated
	 */
	UIAnnotation createUIAnnotation();

	/**
	 * Returns a new object of class '<em>Base Model Node</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Base Model Node</em>'.
	 * @generated
	 */
	BaseModelNode createBaseModelNode();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	BasemodelPackage getBasemodelPackage();

} //BasemodelFactory
