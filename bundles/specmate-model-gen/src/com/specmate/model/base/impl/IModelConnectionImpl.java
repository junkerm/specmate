/**
 */
package com.specmate.model.base.impl;

import com.specmate.model.base.BasePackage;
import com.specmate.model.base.IContentElement;
import com.specmate.model.base.IDescribed;
import com.specmate.model.base.IModelConnection;
import com.specmate.model.base.IModelNode;
import com.specmate.model.base.INamed;

import java.util.Collection;

import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.util.InternalEList;

import org.eclipse.emf.internal.cdo.CDOObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>IModel Connection</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link com.specmate.model.base.impl.IModelConnectionImpl#getId <em>Id</em>}</li>
 *   <li>{@link com.specmate.model.base.impl.IModelConnectionImpl#getName <em>Name</em>}</li>
 *   <li>{@link com.specmate.model.base.impl.IModelConnectionImpl#getDescription <em>Description</em>}</li>
 *   <li>{@link com.specmate.model.base.impl.IModelConnectionImpl#getContents <em>Contents</em>}</li>
 *   <li>{@link com.specmate.model.base.impl.IModelConnectionImpl#getSource <em>Source</em>}</li>
 *   <li>{@link com.specmate.model.base.impl.IModelConnectionImpl#getTarget <em>Target</em>}</li>
 * </ul>
 *
 * @generated
 */
public class IModelConnectionImpl extends CDOObjectImpl implements IModelConnection {
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
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected IModelConnectionImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return BasePackage.Literals.IMODEL_CONNECTION;
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
		return (String)eDynamicGet(BasePackage.IMODEL_CONNECTION__ID, BasePackage.Literals.IID__ID, true, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setId(String newId) {
		eDynamicSet(BasePackage.IMODEL_CONNECTION__ID, BasePackage.Literals.IID__ID, newId);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getName() {
		return (String)eDynamicGet(BasePackage.IMODEL_CONNECTION__NAME, BasePackage.Literals.INAMED__NAME, true, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setName(String newName) {
		eDynamicSet(BasePackage.IMODEL_CONNECTION__NAME, BasePackage.Literals.INAMED__NAME, newName);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getDescription() {
		return (String)eDynamicGet(BasePackage.IMODEL_CONNECTION__DESCRIPTION, BasePackage.Literals.IDESCRIBED__DESCRIPTION, true, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDescription(String newDescription) {
		eDynamicSet(BasePackage.IMODEL_CONNECTION__DESCRIPTION, BasePackage.Literals.IDESCRIBED__DESCRIPTION, newDescription);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	public EList<IContentElement> getContents() {
		return (EList<IContentElement>)eDynamicGet(BasePackage.IMODEL_CONNECTION__CONTENTS, BasePackage.Literals.ICONTAINER__CONTENTS, true, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IModelNode getSource() {
		return (IModelNode)eDynamicGet(BasePackage.IMODEL_CONNECTION__SOURCE, BasePackage.Literals.IMODEL_CONNECTION__SOURCE, true, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IModelNode basicGetSource() {
		return (IModelNode)eDynamicGet(BasePackage.IMODEL_CONNECTION__SOURCE, BasePackage.Literals.IMODEL_CONNECTION__SOURCE, false, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetSource(IModelNode newSource, NotificationChain msgs) {
		msgs = eDynamicInverseAdd((InternalEObject)newSource, BasePackage.IMODEL_CONNECTION__SOURCE, msgs);
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSource(IModelNode newSource) {
		eDynamicSet(BasePackage.IMODEL_CONNECTION__SOURCE, BasePackage.Literals.IMODEL_CONNECTION__SOURCE, newSource);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IModelNode getTarget() {
		return (IModelNode)eDynamicGet(BasePackage.IMODEL_CONNECTION__TARGET, BasePackage.Literals.IMODEL_CONNECTION__TARGET, true, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IModelNode basicGetTarget() {
		return (IModelNode)eDynamicGet(BasePackage.IMODEL_CONNECTION__TARGET, BasePackage.Literals.IMODEL_CONNECTION__TARGET, false, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetTarget(IModelNode newTarget, NotificationChain msgs) {
		msgs = eDynamicInverseAdd((InternalEObject)newTarget, BasePackage.IMODEL_CONNECTION__TARGET, msgs);
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setTarget(IModelNode newTarget) {
		eDynamicSet(BasePackage.IMODEL_CONNECTION__TARGET, BasePackage.Literals.IMODEL_CONNECTION__TARGET, newTarget);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case BasePackage.IMODEL_CONNECTION__SOURCE:
				IModelNode source = basicGetSource();
				if (source != null)
					msgs = ((InternalEObject)source).eInverseRemove(this, BasePackage.IMODEL_NODE__OUTGOING_CONNECTIONS, IModelNode.class, msgs);
				return basicSetSource((IModelNode)otherEnd, msgs);
			case BasePackage.IMODEL_CONNECTION__TARGET:
				IModelNode target = basicGetTarget();
				if (target != null)
					msgs = ((InternalEObject)target).eInverseRemove(this, BasePackage.IMODEL_NODE__INCOMING_CONNECTIONS, IModelNode.class, msgs);
				return basicSetTarget((IModelNode)otherEnd, msgs);
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
			case BasePackage.IMODEL_CONNECTION__CONTENTS:
				return ((InternalEList<?>)getContents()).basicRemove(otherEnd, msgs);
			case BasePackage.IMODEL_CONNECTION__SOURCE:
				return basicSetSource(null, msgs);
			case BasePackage.IMODEL_CONNECTION__TARGET:
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
			case BasePackage.IMODEL_CONNECTION__ID:
				return getId();
			case BasePackage.IMODEL_CONNECTION__NAME:
				return getName();
			case BasePackage.IMODEL_CONNECTION__DESCRIPTION:
				return getDescription();
			case BasePackage.IMODEL_CONNECTION__CONTENTS:
				return getContents();
			case BasePackage.IMODEL_CONNECTION__SOURCE:
				if (resolve) return getSource();
				return basicGetSource();
			case BasePackage.IMODEL_CONNECTION__TARGET:
				if (resolve) return getTarget();
				return basicGetTarget();
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
			case BasePackage.IMODEL_CONNECTION__ID:
				setId((String)newValue);
				return;
			case BasePackage.IMODEL_CONNECTION__NAME:
				setName((String)newValue);
				return;
			case BasePackage.IMODEL_CONNECTION__DESCRIPTION:
				setDescription((String)newValue);
				return;
			case BasePackage.IMODEL_CONNECTION__CONTENTS:
				getContents().clear();
				getContents().addAll((Collection<? extends IContentElement>)newValue);
				return;
			case BasePackage.IMODEL_CONNECTION__SOURCE:
				setSource((IModelNode)newValue);
				return;
			case BasePackage.IMODEL_CONNECTION__TARGET:
				setTarget((IModelNode)newValue);
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
			case BasePackage.IMODEL_CONNECTION__ID:
				setId(ID_EDEFAULT);
				return;
			case BasePackage.IMODEL_CONNECTION__NAME:
				setName(NAME_EDEFAULT);
				return;
			case BasePackage.IMODEL_CONNECTION__DESCRIPTION:
				setDescription(DESCRIPTION_EDEFAULT);
				return;
			case BasePackage.IMODEL_CONNECTION__CONTENTS:
				getContents().clear();
				return;
			case BasePackage.IMODEL_CONNECTION__SOURCE:
				setSource((IModelNode)null);
				return;
			case BasePackage.IMODEL_CONNECTION__TARGET:
				setTarget((IModelNode)null);
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
			case BasePackage.IMODEL_CONNECTION__ID:
				return ID_EDEFAULT == null ? getId() != null : !ID_EDEFAULT.equals(getId());
			case BasePackage.IMODEL_CONNECTION__NAME:
				return NAME_EDEFAULT == null ? getName() != null : !NAME_EDEFAULT.equals(getName());
			case BasePackage.IMODEL_CONNECTION__DESCRIPTION:
				return DESCRIPTION_EDEFAULT == null ? getDescription() != null : !DESCRIPTION_EDEFAULT.equals(getDescription());
			case BasePackage.IMODEL_CONNECTION__CONTENTS:
				return !getContents().isEmpty();
			case BasePackage.IMODEL_CONNECTION__SOURCE:
				return basicGetSource() != null;
			case BasePackage.IMODEL_CONNECTION__TARGET:
				return basicGetTarget() != null;
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
				case BasePackage.IMODEL_CONNECTION__NAME: return BasePackage.INAMED__NAME;
				default: return -1;
			}
		}
		if (baseClass == IDescribed.class) {
			switch (derivedFeatureID) {
				case BasePackage.IMODEL_CONNECTION__DESCRIPTION: return BasePackage.IDESCRIBED__DESCRIPTION;
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
				case BasePackage.INAMED__NAME: return BasePackage.IMODEL_CONNECTION__NAME;
				default: return -1;
			}
		}
		if (baseClass == IDescribed.class) {
			switch (baseFeatureID) {
				case BasePackage.IDESCRIBED__DESCRIPTION: return BasePackage.IMODEL_CONNECTION__DESCRIPTION;
				default: return -1;
			}
		}
		return super.eDerivedStructuralFeatureID(baseFeatureID, baseClass);
	}

} //IModelConnectionImpl
