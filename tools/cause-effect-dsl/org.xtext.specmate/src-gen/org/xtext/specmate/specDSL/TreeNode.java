/**
 * generated by Xtext 2.17.1
 */
package org.xtext.specmate.specDSL;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Tree Node</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.xtext.specmate.specDSL.TreeNode#getPTag <em>PTag</em>}</li>
 *   <li>{@link org.xtext.specmate.specDSL.TreeNode#getExpr <em>Expr</em>}</li>
 *   <li>{@link org.xtext.specmate.specDSL.TreeNode#isAnyMatch <em>Any Match</em>}</li>
 *   <li>{@link org.xtext.specmate.specDSL.TreeNode#getTree <em>Tree</em>}</li>
 * </ul>
 *
 * @see org.xtext.specmate.specDSL.SpecDSLPackage#getTreeNode()
 * @model
 * @generated
 */
public interface TreeNode extends Node
{
  /**
   * Returns the value of the '<em><b>PTag</b></em>' reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>PTag</em>' reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>PTag</em>' reference.
   * @see #setPTag(POSTag)
   * @see org.xtext.specmate.specDSL.SpecDSLPackage#getTreeNode_PTag()
   * @model
   * @generated
   */
  POSTag getPTag();

  /**
   * Sets the value of the '{@link org.xtext.specmate.specDSL.TreeNode#getPTag <em>PTag</em>}' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>PTag</em>' reference.
   * @see #getPTag()
   * @generated
   */
  void setPTag(POSTag value);

  /**
   * Returns the value of the '<em><b>Expr</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Expr</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Expr</em>' attribute.
   * @see #setExpr(String)
   * @see org.xtext.specmate.specDSL.SpecDSLPackage#getTreeNode_Expr()
   * @model
   * @generated
   */
  String getExpr();

  /**
   * Sets the value of the '{@link org.xtext.specmate.specDSL.TreeNode#getExpr <em>Expr</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Expr</em>' attribute.
   * @see #getExpr()
   * @generated
   */
  void setExpr(String value);

  /**
   * Returns the value of the '<em><b>Any Match</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Any Match</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Any Match</em>' attribute.
   * @see #setAnyMatch(boolean)
   * @see org.xtext.specmate.specDSL.SpecDSLPackage#getTreeNode_AnyMatch()
   * @model
   * @generated
   */
  boolean isAnyMatch();

  /**
   * Sets the value of the '{@link org.xtext.specmate.specDSL.TreeNode#isAnyMatch <em>Any Match</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Any Match</em>' attribute.
   * @see #isAnyMatch()
   * @generated
   */
  void setAnyMatch(boolean value);

  /**
   * Returns the value of the '<em><b>Tree</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Tree</em>' containment reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Tree</em>' containment reference.
   * @see #setTree(Subtree)
   * @see org.xtext.specmate.specDSL.SpecDSLPackage#getTreeNode_Tree()
   * @model containment="true"
   * @generated
   */
  Subtree getTree();

  /**
   * Sets the value of the '{@link org.xtext.specmate.specDSL.TreeNode#getTree <em>Tree</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Tree</em>' containment reference.
   * @see #getTree()
   * @generated
   */
  void setTree(Subtree value);

} // TreeNode
