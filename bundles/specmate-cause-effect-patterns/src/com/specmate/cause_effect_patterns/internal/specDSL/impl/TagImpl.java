/**
 * generated by Xtext 2.17.1
 */
package com.specmate.cause_effect_patterns.internal.specDSL.impl;

import com.specmate.cause_effect_patterns.internal.specDSL.SpecDSLPackage;
import com.specmate.cause_effect_patterns.internal.specDSL.Tag;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Tag</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link com.specmate.cause_effect_patterns.specDSL.impl.TagImpl#getName <em>Name</em>}</li>
 *   <li>{@link com.specmate.cause_effect_patterns.specDSL.impl.TagImpl#getTagname <em>Tagname</em>}</li>
 * </ul>
 *
 * @generated
 */
public class TagImpl extends MinimalEObjectImpl.Container implements Tag
{
  /**
   * The default value of the '{@link #getName() <em>Name</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getName()
   * @generated
   * @ordered
   */
  protected static final String NAME_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getName() <em>Name</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getName()
   * @generated
   * @ordered
   */
  protected String name = NAME_EDEFAULT;

  /**
   * The default value of the '{@link #getTagname() <em>Tagname</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getTagname()
   * @generated
   * @ordered
   */
  protected static final String TAGNAME_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getTagname() <em>Tagname</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getTagname()
   * @generated
   * @ordered
   */
  protected String tagname = TAGNAME_EDEFAULT;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected TagImpl()
  {
    super();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  protected EClass eStaticClass()
  {
    return SpecDSLPackage.Literals.TAG;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public String getName()
  {
    return name;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setName(String newName)
  {
    String oldName = name;
    name = newName;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, SpecDSLPackage.TAG__NAME, oldName, name));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public String getTagname()
  {
    return tagname;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setTagname(String newTagname)
  {
    String oldTagname = tagname;
    tagname = newTagname;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, SpecDSLPackage.TAG__TAGNAME, oldTagname, tagname));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public Object eGet(int featureID, boolean resolve, boolean coreType)
  {
    switch (featureID)
    {
      case SpecDSLPackage.TAG__NAME:
        return getName();
      case SpecDSLPackage.TAG__TAGNAME:
        return getTagname();
    }
    return super.eGet(featureID, resolve, coreType);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void eSet(int featureID, Object newValue)
  {
    switch (featureID)
    {
      case SpecDSLPackage.TAG__NAME:
        setName((String)newValue);
        return;
      case SpecDSLPackage.TAG__TAGNAME:
        setTagname((String)newValue);
        return;
    }
    super.eSet(featureID, newValue);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void eUnset(int featureID)
  {
    switch (featureID)
    {
      case SpecDSLPackage.TAG__NAME:
        setName(NAME_EDEFAULT);
        return;
      case SpecDSLPackage.TAG__TAGNAME:
        setTagname(TAGNAME_EDEFAULT);
        return;
    }
    super.eUnset(featureID);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public boolean eIsSet(int featureID)
  {
    switch (featureID)
    {
      case SpecDSLPackage.TAG__NAME:
        return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
      case SpecDSLPackage.TAG__TAGNAME:
        return TAGNAME_EDEFAULT == null ? tagname != null : !TAGNAME_EDEFAULT.equals(tagname);
    }
    return super.eIsSet(featureID);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public String toString()
  {
    if (eIsProxy()) return super.toString();

    StringBuffer result = new StringBuffer(super.toString());
    result.append(" (name: ");
    result.append(name);
    result.append(", tagname: ");
    result.append(tagname);
    result.append(')');
    return result.toString();
  }

} //TagImpl
