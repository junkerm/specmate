/**
 */
package com.specmate.model.requirements.impl;

import com.specmate.model.base.BasePackage;
import com.specmate.model.base.IContentElement;
import com.specmate.model.base.IDescribed;
import com.specmate.model.base.INamed;
import com.specmate.model.requirements.CEGConnection;
import com.specmate.model.requirements.CEGNode;
import com.specmate.model.requirements.RequirementsPackage;

import java.util.Collection;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.emf.internal.cdo.CDOObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>CEG Connection</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link com.specmate.model.requirements.impl.CEGConnectionImpl#getId <em>Id</em>}</li>
 *   <li>{@link com.specmate.model.requirements.impl.CEGConnectionImpl#getName <em>Name</em>}</li>
 *   <li>{@link com.specmate.model.requirements.impl.CEGConnectionImpl#getDescription <em>Description</em>}</li>
 *   <li>{@link com.specmate.model.requirements.impl.CEGConnectionImpl#getContents <em>Contents</em>}</li>
 *   <li>{@link com.specmate.model.requirements.impl.CEGConnectionImpl#getSource <em>Source</em>}</li>
 *   <li>{@link com.specmate.model.requirements.impl.CEGConnectionImpl#getTarget <em>Target</em>}</li>
 *   <li>{@link com.specmate.model.requirements.impl.CEGConnectionImpl#isNegate <em>Negate</em>}</li>
 * </ul>
 *
 * @generated
 */
public class CEGConnectionImpl extends CDOObjectImpl implements CEGConnection {
	/**
	 * The default value of the '{@link #getId() <em>Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getId()
	 * @generated
	 * @ordered
	 */
	protected static final String ID_EDEFAULT = null;
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
	 * The default value of the '{@link #getDescription() <em>Description</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDescription()
	 * @generated
	 * @ordered
	 */
	protected static final String DESCRIPTION_EDEFAULT = null;
	/**
	 * The default value of the '{@link #isNegate() <em>Negate</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isNegate()
	 * @generated
	 * @ordered
	 */
	protected static final boolean NEGATE_EDEFAULT = false;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected CEGConnectionImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return RequirementsPackage.Literals.CEG_CONNECTION;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected int eStaticFeatureCount() {
		return 0;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getId() {
		return (String)eDynamicGet(RequirementsPackage.CEG_CONNECTION__ID, BasePackage.Literals.IID__ID, true, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setId(String newId) {
		eDynamicSet(RequirementsPackage.CEG_CONNECTION__ID, BasePackage.Literals.IID__ID, newId);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getName() {
		return (String)eDynamicGet(RequirementsPackage.CEG_CONNECTION__NAME, BasePackage.Literals.INAMED__NAME, true, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setName(String newName) {
		eDynamicSet(RequirementsPackage.CEG_CONNECTION__NAME, BasePackage.Literals.INAMED__NAME, newName);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getDescription() {
		return (String)eDynamicGet(RequirementsPackage.CEG_CONNECTION__DESCRIPTION, BasePackage.Literals.IDESCRIBED__DESCRIPTION, true, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDescription(String newDescription) {
		eDynamicSet(RequirementsPackage.CEG_CONNECTION__DESCRIPTION, BasePackage.Literals.IDESCRIBED__DESCRIPTION, newDescription);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	public EList<IContentElement> getContents() {
		return (EList<IContentElement>)eDynamicGet(RequirementsPackage.CEG_CONNECTION__CONTENTS, BasePackage.Literals.ICONTAINER__CONTENTS, true, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CEGNode getSource() {
		return (CEGNode)eDynamicGet(RequirementsPackage.CEG_CONNECTION__SOURCE, RequirementsPackage.Literals.CEG_CONNECTION__SOURCE, true, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CEGNode basicGetSource() {
		return (CEGNode)eDynamicGet(RequirementsPackage.CEG_CONNECTION__SOURCE, RequirementsPackage.Literals.CEG_CONNECTION__SOURCE, false, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetSource(CEGNode newSource, NotificationChain msgs) {
		msgs = eDynamicInverseAdd((InternalEObject)newSource, RequirementsPackage.CEG_CONNECTION__SOURCE, msgs);
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSource(CEGNode newSource) {
		eDynamicSet(RequirementsPackage.CEG_CONNECTION__SOURCE, RequirementsPackage.Literals.CEG_CONNECTION__SOURCE, newSource);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CEGNode getTarget() {
		return (CEGNode)eDynamicGet(RequirementsPackage.CEG_CONNECTION__TARGET, RequirementsPackage.Literals.CEG_CONNECTION__TARGET, true, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CEGNode basicGetTarget() {
		return (CEGNode)eDynamicGet(RequirementsPackage.CEG_CONNECTION__TARGET, RequirementsPackage.Literals.CEG_CONNECTION__TARGET, false, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetTarget(CEGNode newTarget, NotificationChain msgs) {
		msgs = eDynamicInverseAdd((InternalEObject)newTarget, RequirementsPackage.CEG_CONNECTION__TARGET, msgs);
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setTarget(CEGNode newTarget) {
		eDynamicSet(RequirementsPackage.CEG_CONNECTION__TARGET, RequirementsPackage.Literals.CEG_CONNECTION__TARGET, newTarget);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isNegate() {
		return (Boolean)eDynamicGet(RequirementsPackage.CEG_CONNECTION__NEGATE, RequirementsPackage.Literals.CEG_CONNECTION__NEGATE, true, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setNegate(boolean newNegate) {
		eDynamicSet(RequirementsPackage.CEG_CONNECTION__NEGATE, RequirementsPackage.Literals.CEG_CONNECTION__NEGATE, newNegate);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case RequirementsPackage.CEG_CONNECTION__SOURCE:
				CEGNode source = basicGetSource();
				if (source != null)
					msgs = ((InternalEObject)source).eInverseRemove(this, RequirementsPackage.CEG_NODE__OUTGOING_CONNECTIONS, CEGNode.class, msgs);
				return basicSetSource((CEGNode)otherEnd, msgs);
			case RequirementsPackage.CEG_CONNECTION__TARGET:
				CEGNode target = basicGetTarget();
				if (target != null)
					msgs = ((InternalEObject)target).eInverseRemove(this, RequirementsPackage.CEG_NODE__INCOMING_CONNECTION, CEGNode.class, msgs);
				return basicSetTarget((CEGNode)otherEnd, msgs);
		}
		return super.eInverseAdd(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case RequirementsPackage.CEG_CONNECTION__CONTENTS:
				return ((InternalEList<?>)getContents()).basicRemove(otherEnd, msgs);
			case RequirementsPackage.CEG_CONNECTION__SOURCE:
				return basicSetSource(null, msgs);
			case RequirementsPackage.CEG_CONNECTION__TARGET:
				return basicSetTarget(null, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case RequirementsPackage.CEG_CONNECTION__ID:
				return getId();
			case RequirementsPackage.CEG_CONNECTION__NAME:
				return getName();
			case RequirementsPackage.CEG_CONNECTION__DESCRIPTION:
				return getDescription();
			case RequirementsPackage.CEG_CONNECTION__CONTENTS:
				return getContents();
			case RequirementsPackage.CEG_CONNECTION__SOURCE:
				if (resolve) return getSource();
				return basicGetSource();
			case RequirementsPackage.CEG_CONNECTION__TARGET:
				if (resolve) return getTarget();
				return basicGetTarget();
			case RequirementsPackage.CEG_CONNECTION__NEGATE:
				return isNegate();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case RequirementsPackage.CEG_CONNECTION__ID:
				setId((String)newValue);
				return;
			case RequirementsPackage.CEG_CONNECTION__NAME:
				setName((String)newValue);
				return;
			case RequirementsPackage.CEG_CONNECTION__DESCRIPTION:
				setDescription((String)newValue);
				return;
			case RequirementsPackage.CEG_CONNECTION__CONTENTS:
				getContents().clear();
				getContents().addAll((Collection<? extends IContentElement>)newValue);
				return;
			case RequirementsPackage.CEG_CONNECTION__SOURCE:
				setSource((CEGNode)newValue);
				return;
			case RequirementsPackage.CEG_CONNECTION__TARGET:
				setTarget((CEGNode)newValue);
				return;
			case RequirementsPackage.CEG_CONNECTION__NEGATE:
				setNegate((Boolean)newValue);
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
	public void eUnset(int featureID) {
		switch (featureID) {
			case RequirementsPackage.CEG_CONNECTION__ID:
				setId(ID_EDEFAULT);
				return;
			case RequirementsPackage.CEG_CONNECTION__NAME:
				setName(NAME_EDEFAULT);
				return;
			case RequirementsPackage.CEG_CONNECTION__DESCRIPTION:
				setDescription(DESCRIPTION_EDEFAULT);
				return;
			case RequirementsPackage.CEG_CONNECTION__CONTENTS:
				getContents().clear();
				return;
			case RequirementsPackage.CEG_CONNECTION__SOURCE:
				setSource((CEGNode)null);
				return;
			case RequirementsPackage.CEG_CONNECTION__TARGET:
				setTarget((CEGNode)null);
				return;
			case RequirementsPackage.CEG_CONNECTION__NEGATE:
				setNegate(NEGATE_EDEFAULT);
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
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case RequirementsPackage.CEG_CONNECTION__ID:
				return ID_EDEFAULT == null ? getId() != null : !ID_EDEFAULT.equals(getId());
			case RequirementsPackage.CEG_CONNECTION__NAME:
				return NAME_EDEFAULT == null ? getName() != null : !NAME_EDEFAULT.equals(getName());
			case RequirementsPackage.CEG_CONNECTION__DESCRIPTION:
				return DESCRIPTION_EDEFAULT == null ? getDescription() != null : !DESCRIPTION_EDEFAULT.equals(getDescription());
			case RequirementsPackage.CEG_CONNECTION__CONTENTS:
				return !getContents().isEmpty();
			case RequirementsPackage.CEG_CONNECTION__SOURCE:
				return basicGetSource() != null;
			case RequirementsPackage.CEG_CONNECTION__TARGET:
				return basicGetTarget() != null;
			case RequirementsPackage.CEG_CONNECTION__NEGATE:
				return isNegate() != NEGATE_EDEFAULT;
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int eBaseStructuralFeatureID(int derivedFeatureID, Class<?> baseClass) {
		if (baseClass == INamed.class) {
			switch (derivedFeatureID) {
				case RequirementsPackage.CEG_CONNECTION__NAME: return BasePackage.INAMED__NAME;
				default: return -1;
			}
		}
		if (baseClass == IDescribed.class) {
			switch (derivedFeatureID) {
				case RequirementsPackage.CEG_CONNECTION__DESCRIPTION: return BasePackage.IDESCRIBED__DESCRIPTION;
				default: return -1;
			}
		}
		return super.eBaseStructuralFeatureID(derivedFeatureID, baseClass);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int eDerivedStructuralFeatureID(int baseFeatureID, Class<?> baseClass) {
		if (baseClass == INamed.class) {
			switch (baseFeatureID) {
				case BasePackage.INAMED__NAME: return RequirementsPackage.CEG_CONNECTION__NAME;
				default: return -1;
			}
		}
		if (baseClass == IDescribed.class) {
			switch (baseFeatureID) {
				case BasePackage.IDESCRIBED__DESCRIPTION: return RequirementsPackage.CEG_CONNECTION__DESCRIPTION;
				default: return -1;
			}
		}
		return super.eDerivedStructuralFeatureID(baseFeatureID, baseClass);
	}

} //CEGConnectionImpl
