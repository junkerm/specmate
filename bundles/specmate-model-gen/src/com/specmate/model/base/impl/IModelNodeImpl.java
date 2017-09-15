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
 * An implementation of the model object '<em><b>IModel Node</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link com.specmate.model.base.impl.IModelNodeImpl#getId <em>Id</em>}</li>
 *   <li>{@link com.specmate.model.base.impl.IModelNodeImpl#getName <em>Name</em>}</li>
 *   <li>{@link com.specmate.model.base.impl.IModelNodeImpl#getDescription <em>Description</em>}</li>
 *   <li>{@link com.specmate.model.base.impl.IModelNodeImpl#getContents <em>Contents</em>}</li>
 *   <li>{@link com.specmate.model.base.impl.IModelNodeImpl#getX <em>X</em>}</li>
 *   <li>{@link com.specmate.model.base.impl.IModelNodeImpl#getY <em>Y</em>}</li>
 *   <li>{@link com.specmate.model.base.impl.IModelNodeImpl#getOutgoingConnections <em>Outgoing Connections</em>}</li>
 *   <li>{@link com.specmate.model.base.impl.IModelNodeImpl#getIncomingConnections <em>Incoming Connections</em>}</li>
 * </ul>
 *
 * @generated
 */
public class IModelNodeImpl extends CDOObjectImpl implements IModelNode {
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
	 * The default value of the '{@link #getX() <em>X</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getX()
	 * @generated
	 * @ordered
	 */
	protected static final double X_EDEFAULT = 0.0;

	/**
	 * The default value of the '{@link #getY() <em>Y</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getY()
	 * @generated
	 * @ordered
	 */
	protected static final double Y_EDEFAULT = 0.0;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected IModelNodeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return BasePackage.Literals.IMODEL_NODE;
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
		return (String)eDynamicGet(BasePackage.IMODEL_NODE__ID, BasePackage.Literals.IID__ID, true, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setId(String newId) {
		eDynamicSet(BasePackage.IMODEL_NODE__ID, BasePackage.Literals.IID__ID, newId);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getName() {
		return (String)eDynamicGet(BasePackage.IMODEL_NODE__NAME, BasePackage.Literals.INAMED__NAME, true, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setName(String newName) {
		eDynamicSet(BasePackage.IMODEL_NODE__NAME, BasePackage.Literals.INAMED__NAME, newName);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getDescription() {
		return (String)eDynamicGet(BasePackage.IMODEL_NODE__DESCRIPTION, BasePackage.Literals.IDESCRIBED__DESCRIPTION, true, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDescription(String newDescription) {
		eDynamicSet(BasePackage.IMODEL_NODE__DESCRIPTION, BasePackage.Literals.IDESCRIBED__DESCRIPTION, newDescription);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	public EList<IContentElement> getContents() {
		return (EList<IContentElement>)eDynamicGet(BasePackage.IMODEL_NODE__CONTENTS, BasePackage.Literals.ICONTAINER__CONTENTS, true, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public double getX() {
		return (Double)eDynamicGet(BasePackage.IMODEL_NODE__X, BasePackage.Literals.ISPECMATE_POSITIONABLE_MODEL_OBJECT__X, true, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setX(double newX) {
		eDynamicSet(BasePackage.IMODEL_NODE__X, BasePackage.Literals.ISPECMATE_POSITIONABLE_MODEL_OBJECT__X, newX);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public double getY() {
		return (Double)eDynamicGet(BasePackage.IMODEL_NODE__Y, BasePackage.Literals.ISPECMATE_POSITIONABLE_MODEL_OBJECT__Y, true, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setY(double newY) {
		eDynamicSet(BasePackage.IMODEL_NODE__Y, BasePackage.Literals.ISPECMATE_POSITIONABLE_MODEL_OBJECT__Y, newY);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	public EList<IModelConnection> getOutgoingConnections() {
		return (EList<IModelConnection>)eDynamicGet(BasePackage.IMODEL_NODE__OUTGOING_CONNECTIONS, BasePackage.Literals.IMODEL_NODE__OUTGOING_CONNECTIONS, true, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	public EList<IModelConnection> getIncomingConnections() {
		return (EList<IModelConnection>)eDynamicGet(BasePackage.IMODEL_NODE__INCOMING_CONNECTIONS, BasePackage.Literals.IMODEL_NODE__INCOMING_CONNECTIONS, true, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case BasePackage.IMODEL_NODE__OUTGOING_CONNECTIONS:
				return ((InternalEList<InternalEObject>)(InternalEList<?>)getOutgoingConnections()).basicAdd(otherEnd, msgs);
			case BasePackage.IMODEL_NODE__INCOMING_CONNECTIONS:
				return ((InternalEList<InternalEObject>)(InternalEList<?>)getIncomingConnections()).basicAdd(otherEnd, msgs);
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
			case BasePackage.IMODEL_NODE__CONTENTS:
				return ((InternalEList<?>)getContents()).basicRemove(otherEnd, msgs);
			case BasePackage.IMODEL_NODE__OUTGOING_CONNECTIONS:
				return ((InternalEList<?>)getOutgoingConnections()).basicRemove(otherEnd, msgs);
			case BasePackage.IMODEL_NODE__INCOMING_CONNECTIONS:
				return ((InternalEList<?>)getIncomingConnections()).basicRemove(otherEnd, msgs);
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
			case BasePackage.IMODEL_NODE__ID:
				return getId();
			case BasePackage.IMODEL_NODE__NAME:
				return getName();
			case BasePackage.IMODEL_NODE__DESCRIPTION:
				return getDescription();
			case BasePackage.IMODEL_NODE__CONTENTS:
				return getContents();
			case BasePackage.IMODEL_NODE__X:
				return getX();
			case BasePackage.IMODEL_NODE__Y:
				return getY();
			case BasePackage.IMODEL_NODE__OUTGOING_CONNECTIONS:
				return getOutgoingConnections();
			case BasePackage.IMODEL_NODE__INCOMING_CONNECTIONS:
				return getIncomingConnections();
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
			case BasePackage.IMODEL_NODE__ID:
				setId((String)newValue);
				return;
			case BasePackage.IMODEL_NODE__NAME:
				setName((String)newValue);
				return;
			case BasePackage.IMODEL_NODE__DESCRIPTION:
				setDescription((String)newValue);
				return;
			case BasePackage.IMODEL_NODE__CONTENTS:
				getContents().clear();
				getContents().addAll((Collection<? extends IContentElement>)newValue);
				return;
			case BasePackage.IMODEL_NODE__X:
				setX((Double)newValue);
				return;
			case BasePackage.IMODEL_NODE__Y:
				setY((Double)newValue);
				return;
			case BasePackage.IMODEL_NODE__OUTGOING_CONNECTIONS:
				getOutgoingConnections().clear();
				getOutgoingConnections().addAll((Collection<? extends IModelConnection>)newValue);
				return;
			case BasePackage.IMODEL_NODE__INCOMING_CONNECTIONS:
				getIncomingConnections().clear();
				getIncomingConnections().addAll((Collection<? extends IModelConnection>)newValue);
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
			case BasePackage.IMODEL_NODE__ID:
				setId(ID_EDEFAULT);
				return;
			case BasePackage.IMODEL_NODE__NAME:
				setName(NAME_EDEFAULT);
				return;
			case BasePackage.IMODEL_NODE__DESCRIPTION:
				setDescription(DESCRIPTION_EDEFAULT);
				return;
			case BasePackage.IMODEL_NODE__CONTENTS:
				getContents().clear();
				return;
			case BasePackage.IMODEL_NODE__X:
				setX(X_EDEFAULT);
				return;
			case BasePackage.IMODEL_NODE__Y:
				setY(Y_EDEFAULT);
				return;
			case BasePackage.IMODEL_NODE__OUTGOING_CONNECTIONS:
				getOutgoingConnections().clear();
				return;
			case BasePackage.IMODEL_NODE__INCOMING_CONNECTIONS:
				getIncomingConnections().clear();
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
			case BasePackage.IMODEL_NODE__ID:
				return ID_EDEFAULT == null ? getId() != null : !ID_EDEFAULT.equals(getId());
			case BasePackage.IMODEL_NODE__NAME:
				return NAME_EDEFAULT == null ? getName() != null : !NAME_EDEFAULT.equals(getName());
			case BasePackage.IMODEL_NODE__DESCRIPTION:
				return DESCRIPTION_EDEFAULT == null ? getDescription() != null : !DESCRIPTION_EDEFAULT.equals(getDescription());
			case BasePackage.IMODEL_NODE__CONTENTS:
				return !getContents().isEmpty();
			case BasePackage.IMODEL_NODE__X:
				return getX() != X_EDEFAULT;
			case BasePackage.IMODEL_NODE__Y:
				return getY() != Y_EDEFAULT;
			case BasePackage.IMODEL_NODE__OUTGOING_CONNECTIONS:
				return !getOutgoingConnections().isEmpty();
			case BasePackage.IMODEL_NODE__INCOMING_CONNECTIONS:
				return !getIncomingConnections().isEmpty();
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
				case BasePackage.IMODEL_NODE__NAME: return BasePackage.INAMED__NAME;
				default: return -1;
			}
		}
		if (baseClass == IDescribed.class) {
			switch (derivedFeatureID) {
				case BasePackage.IMODEL_NODE__DESCRIPTION: return BasePackage.IDESCRIBED__DESCRIPTION;
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
				case BasePackage.INAMED__NAME: return BasePackage.IMODEL_NODE__NAME;
				default: return -1;
			}
		}
		if (baseClass == IDescribed.class) {
			switch (baseFeatureID) {
				case BasePackage.IDESCRIBED__DESCRIPTION: return BasePackage.IMODEL_NODE__DESCRIPTION;
				default: return -1;
			}
		}
		return super.eDerivedStructuralFeatureID(baseFeatureID, baseClass);
	}

} //IModelNodeImpl
