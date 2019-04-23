/**
 * generated by Xtext 2.17.1
 */
package org.xtext.specmate.specDSL;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Tree Def</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.xtext.specmate.specDSL.TreeDef#getTrees <em>Trees</em>}</li>
 * </ul>
 *
 * @see org.xtext.specmate.specDSL.SpecDSLPackage#getTreeDef()
 * @model
 * @generated
 */
public interface TreeDef extends AbstractElement
{
  /**
   * Returns the value of the '<em><b>Trees</b></em>' containment reference list.
   * The list contents are of type {@link org.xtext.specmate.specDSL.TreeTag}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Trees</em>' containment reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Trees</em>' containment reference list.
   * @see org.xtext.specmate.specDSL.SpecDSLPackage#getTreeDef_Trees()
   * @model containment="true"
   * @generated
   */
  EList<TreeTag> getTrees();

} // TreeDef
