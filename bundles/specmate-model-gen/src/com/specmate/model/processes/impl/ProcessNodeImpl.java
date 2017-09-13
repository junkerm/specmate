/**
 */
package com.specmate.model.processes.impl;

import com.specmate.model.base.BasePackage;
import com.specmate.model.base.IContentElement;
import com.specmate.model.base.IDescribed;
import com.specmate.model.base.INamed;

import com.specmate.model.processes.ProcessConnection;
import com.specmate.model.processes.ProcessNode;
import com.specmate.model.processes.ProcessesPackage;

import java.util.Collection;

import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.util.InternalEList;

import org.eclipse.emf.internal.cdo.CDOObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Process Node</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link com.specmate.model.processes.impl.ProcessNodeImpl#getId <em>Id</em>}</li>
 *   <li>{@link com.specmate.model.processes.impl.ProcessNodeImpl#getName <em>Name</em>}</li>
 *   <li>{@link com.specmate.model.processes.impl.ProcessNodeImpl#getDescription <em>Description</em>}</li>
 *   <li>{@link com.specmate.model.processes.impl.ProcessNodeImpl#getContents <em>Contents</em>}</li>
 *   <li>{@link com.specmate.model.processes.impl.ProcessNodeImpl#getX <em>X</em>}</li>
 *   <li>{@link com.specmate.model.processes.impl.ProcessNodeImpl#getY <em>Y</em>}</li>
 *   <li>{@link com.specmate.model.processes.impl.ProcessNodeImpl#getOutgoingConnections <em>Outgoing Connections</em>}</li>
 *   <li>{@link com.specmate.model.processes.impl.ProcessNodeImpl#getIncomingConnections <em>Incoming Connections</em>}</li>
 * </ul>
 *
 * @generated
 */
public abstract class ProcessNodeImpl extends CDOObjectImpl implements ProcessNode {
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
	protected ProcessNodeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ProcessesPackage.Literals.PROCESS_NODE;
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
		return (String)eDynamicGet(ProcessesPackage.PROCESS_NODE__ID, BasePackage.Literals.IID__ID, true, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setId(String newId) {
		eDynamicSet(ProcessesPackage.PROCESS_NODE__ID, BasePackage.Literals.IID__ID, newId);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getName() {
		return (String)eDynamicGet(ProcessesPackage.PROCESS_NODE__NAME, BasePackage.Literals.INAMED__NAME, true, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setName(String newName) {
		eDynamicSet(ProcessesPackage.PROCESS_NODE__NAME, BasePackage.Literals.INAMED__NAME, newName);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getDescription() {
		return (String)eDynamicGet(ProcessesPackage.PROCESS_NODE__DESCRIPTION, BasePackage.Literals.IDESCRIBED__DESCRIPTION, true, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDescription(String newDescription) {
		eDynamicSet(ProcessesPackage.PROCESS_NODE__DESCRIPTION, BasePackage.Literals.IDESCRIBED__DESCRIPTION, newDescription);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	public EList<IContentElement> getContents() {
		return (EList<IContentElement>)eDynamicGet(ProcessesPackage.PROCESS_NODE__CONTENTS, BasePackage.Literals.ICONTAINER__CONTENTS, true, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public double getX() {
		return (Double)eDynamicGet(ProcessesPackage.PROCESS_NODE__X, BasePackage.Literals.ISPECMATE_POSITIONABLE_MODEL_OBJECT__X, true, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setX(double newX) {
		eDynamicSet(ProcessesPackage.PROCESS_NODE__X, BasePackage.Literals.ISPECMATE_POSITIONABLE_MODEL_OBJECT__X, newX);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public double getY() {
		return (Double)eDynamicGet(ProcessesPackage.PROCESS_NODE__Y, BasePackage.Literals.ISPECMATE_POSITIONABLE_MODEL_OBJECT__Y, true, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setY(double newY) {
		eDynamicSet(ProcessesPackage.PROCESS_NODE__Y, BasePackage.Literals.ISPECMATE_POSITIONABLE_MODEL_OBJECT__Y, newY);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	public EList<ProcessConnection> getOutgoingConnections() {
		return (EList<ProcessConnection>)eDynamicGet(ProcessesPackage.PROCESS_NODE__OUTGOING_CONNECTIONS, ProcessesPackage.Literals.PROCESS_NODE__OUTGOING_CONNECTIONS, true, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	public EList<ProcessConnection> getIncomingConnections() {
		return (EList<ProcessConnection>)eDynamicGet(ProcessesPackage.PROCESS_NODE__INCOMING_CONNECTIONS, ProcessesPackage.Literals.PROCESS_NODE__INCOMING_CONNECTIONS, true, true);
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
			case ProcessesPackage.PROCESS_NODE__OUTGOING_CONNECTIONS:
				return ((InternalEList<InternalEObject>)(InternalEList<?>)getOutgoingConnections()).basicAdd(otherEnd, msgs);
			case ProcessesPackage.PROCESS_NODE__INCOMING_CONNECTIONS:
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
			case ProcessesPackage.PROCESS_NODE__CONTENTS:
				return ((InternalEList<?>)getContents()).basicRemove(otherEnd, msgs);
			case ProcessesPackage.PROCESS_NODE__OUTGOING_CONNECTIONS:
				return ((InternalEList<?>)getOutgoingConnections()).basicRemove(otherEnd, msgs);
			case ProcessesPackage.PROCESS_NODE__INCOMING_CONNECTIONS:
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
			case ProcessesPackage.PROCESS_NODE__ID:
				return getId();
			case ProcessesPackage.PROCESS_NODE__NAME:
				return getName();
			case ProcessesPackage.PROCESS_NODE__DESCRIPTION:
				return getDescription();
			case ProcessesPackage.PROCESS_NODE__CONTENTS:
				return getContents();
			case ProcessesPackage.PROCESS_NODE__X:
				return getX();
			case ProcessesPackage.PROCESS_NODE__Y:
				return getY();
			case ProcessesPackage.PROCESS_NODE__OUTGOING_CONNECTIONS:
				return getOutgoingConnections();
			case ProcessesPackage.PROCESS_NODE__INCOMING_CONNECTIONS:
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
			case ProcessesPackage.PROCESS_NODE__ID:
				setId((String)newValue);
				return;
			case ProcessesPackage.PROCESS_NODE__NAME:
				setName((String)newValue);
				return;
			case ProcessesPackage.PROCESS_NODE__DESCRIPTION:
				setDescription((String)newValue);
				return;
			case ProcessesPackage.PROCESS_NODE__CONTENTS:
				getContents().clear();
				getContents().addAll((Collection<? extends IContentElement>)newValue);
				return;
			case ProcessesPackage.PROCESS_NODE__X:
				setX((Double)newValue);
				return;
			case ProcessesPackage.PROCESS_NODE__Y:
				setY((Double)newValue);
				return;
			case ProcessesPackage.PROCESS_NODE__OUTGOING_CONNECTIONS:
				getOutgoingConnections().clear();
				getOutgoingConnections().addAll((Collection<? extends ProcessConnection>)newValue);
				return;
			case ProcessesPackage.PROCESS_NODE__INCOMING_CONNECTIONS:
				getIncomingConnections().clear();
				getIncomingConnections().addAll((Collection<? extends ProcessConnection>)newValue);
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
			case ProcessesPackage.PROCESS_NODE__ID:
				setId(ID_EDEFAULT);
				return;
			case ProcessesPackage.PROCESS_NODE__NAME:
				setName(NAME_EDEFAULT);
				return;
			case ProcessesPackage.PROCESS_NODE__DESCRIPTION:
				setDescription(DESCRIPTION_EDEFAULT);
				return;
			case ProcessesPackage.PROCESS_NODE__CONTENTS:
				getContents().clear();
				return;
			case ProcessesPackage.PROCESS_NODE__X:
				setX(X_EDEFAULT);
				return;
			case ProcessesPackage.PROCESS_NODE__Y:
				setY(Y_EDEFAULT);
				return;
			case ProcessesPackage.PROCESS_NODE__OUTGOING_CONNECTIONS:
				getOutgoingConnections().clear();
				return;
			case ProcessesPackage.PROCESS_NODE__INCOMING_CONNECTIONS:
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
			case ProcessesPackage.PROCESS_NODE__ID:
				return ID_EDEFAULT == null ? getId() != null : !ID_EDEFAULT.equals(getId());
			case ProcessesPackage.PROCESS_NODE__NAME:
				return NAME_EDEFAULT == null ? getName() != null : !NAME_EDEFAULT.equals(getName());
			case ProcessesPackage.PROCESS_NODE__DESCRIPTION:
				return DESCRIPTION_EDEFAULT == null ? getDescription() != null : !DESCRIPTION_EDEFAULT.equals(getDescription());
			case ProcessesPackage.PROCESS_NODE__CONTENTS:
				return !getContents().isEmpty();
			case ProcessesPackage.PROCESS_NODE__X:
				return getX() != X_EDEFAULT;
			case ProcessesPackage.PROCESS_NODE__Y:
				return getY() != Y_EDEFAULT;
			case ProcessesPackage.PROCESS_NODE__OUTGOING_CONNECTIONS:
				return !getOutgoingConnections().isEmpty();
			case ProcessesPackage.PROCESS_NODE__INCOMING_CONNECTIONS:
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
				case ProcessesPackage.PROCESS_NODE__NAME: return BasePackage.INAMED__NAME;
				default: return -1;
			}
		}
		if (baseClass == IDescribed.class) {
			switch (derivedFeatureID) {
				case ProcessesPackage.PROCESS_NODE__DESCRIPTION: return BasePackage.IDESCRIBED__DESCRIPTION;
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
				case BasePackage.INAMED__NAME: return ProcessesPackage.PROCESS_NODE__NAME;
				default: return -1;
			}
		}
		if (baseClass == IDescribed.class) {
			switch (baseFeatureID) {
				case BasePackage.IDESCRIBED__DESCRIPTION: return ProcessesPackage.PROCESS_NODE__DESCRIPTION;
				default: return -1;
			}
		}
		return super.eDerivedStructuralFeatureID(baseFeatureID, baseClass);
	}

} //ProcessNodeImpl
