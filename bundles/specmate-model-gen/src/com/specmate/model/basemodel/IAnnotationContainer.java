/**
 */
package com.specmate.model.basemodel;

import org.eclipse.emf.cdo.CDOObject;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>IAnnotation Container</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link com.specmate.model.basemodel.IAnnotationContainer#getAnnotations <em>Annotations</em>}</li>
 * </ul>
 *
 * @see com.specmate.model.basemodel.BasemodelPackage#getIAnnotationContainer()
 * @model interface="true" abstract="true"
 * @extends CDOObject
 * @generated
 */
public interface IAnnotationContainer extends CDOObject {
	/**
	 * Returns the value of the '<em><b>Annotations</b></em>' containment reference list.
	 * The list contents are of type {@link com.specmate.model.basemodel.IAnnotation}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Annotations</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Annotations</em>' containment reference list.
	 * @see com.specmate.model.basemodel.BasemodelPackage#getIAnnotationContainer_Annotations()
	 * @model containment="true"
	 * @generated
	 */
	EList<IAnnotation> getAnnotations();

} // IAnnotationContainer
