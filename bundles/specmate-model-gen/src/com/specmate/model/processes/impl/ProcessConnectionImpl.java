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
 * An implementation of the model object '<em><b>Process Connection</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link com.specmate.model.processes.impl.ProcessConnectionImpl#getId <em>Id</em>}</li>
 *   <li>{@link com.specmate.model.processes.impl.ProcessConnectionImpl#getName <em>Name</em>}</li>
 *   <li>{@link com.specmate.model.processes.impl.ProcessConnectionImpl#getDescription <em>Description</em>}</li>
 *   <li>{@link com.specmate.model.processes.impl.ProcessConnectionImpl#getContents <em>Contents</em>}</li>
 *   <li>{@link com.specmate.model.processes.impl.ProcessConnectionImpl#getSource <em>Source</em>}</li>
 *   <li>{@link com.specmate.model.processes.impl.ProcessConnectionImpl#getTarget <em>Target</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ProcessConnectionImpl extends CDOObjectImpl implements ProcessConnection {
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
	protected ProcessConnectionImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ProcessesPackage.Literals.PROCESS_CONNECTION;
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
		return (String)eDynamicGet(ProcessesPackage.PROCESS_CONNECTION__ID, BasePackage.Literals.IID__ID, true, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setId(String newId) {
		eDynamicSet(ProcessesPackage.PROCESS_CONNECTION__ID, BasePackage.Literals.IID__ID, newId);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getName() {
		return (String)eDynamicGet(ProcessesPackage.PROCESS_CONNECTION__NAME, BasePackage.Literals.INAMED__NAME, true, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setName(String newName) {
		eDynamicSet(ProcessesPackage.PROCESS_CONNECTION__NAME, BasePackage.Literals.INAMED__NAME, newName);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getDescription() {
		return (String)eDynamicGet(ProcessesPackage.PROCESS_CONNECTION__DESCRIPTION, BasePackage.Literals.IDESCRIBED__DESCRIPTION, true, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDescription(String newDescription) {
		eDynamicSet(ProcessesPackage.PROCESS_CONNECTION__DESCRIPTION, BasePackage.Literals.IDESCRIBED__DESCRIPTION, newDescription);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	public EList<IContentElement> getContents() {
		return (EList<IContentElement>)eDynamicGet(ProcessesPackage.PROCESS_CONNECTION__CONTENTS, BasePackage.Literals.ICONTAINER__CONTENTS, true, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ProcessNode getSource() {
		return (ProcessNode)eDynamicGet(ProcessesPackage.PROCESS_CONNECTION__SOURCE, ProcessesPackage.Literals.PROCESS_CONNECTION__SOURCE, true, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ProcessNode basicGetSource() {
		return (ProcessNode)eDynamicGet(ProcessesPackage.PROCESS_CONNECTION__SOURCE, ProcessesPackage.Literals.PROCESS_CONNECTION__SOURCE, false, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetSource(ProcessNode newSource, NotificationChain msgs) {
		msgs = eDynamicInverseAdd((InternalEObject)newSource, ProcessesPackage.PROCESS_CONNECTION__SOURCE, msgs);
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSource(ProcessNode newSource) {
		eDynamicSet(ProcessesPackage.PROCESS_CONNECTION__SOURCE, ProcessesPackage.Literals.PROCESS_CONNECTION__SOURCE, newSource);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ProcessNode getTarget() {
		return (ProcessNode)eDynamicGet(ProcessesPackage.PROCESS_CONNECTION__TARGET, ProcessesPackage.Literals.PROCESS_CONNECTION__TARGET, true, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ProcessNode basicGetTarget() {
		return (ProcessNode)eDynamicGet(ProcessesPackage.PROCESS_CONNECTION__TARGET, ProcessesPackage.Literals.PROCESS_CONNECTION__TARGET, false, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetTarget(ProcessNode newTarget, NotificationChain msgs) {
		msgs = eDynamicInverseAdd((InternalEObject)newTarget, ProcessesPackage.PROCESS_CONNECTION__TARGET, msgs);
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setTarget(ProcessNode newTarget) {
		eDynamicSet(ProcessesPackage.PROCESS_CONNECTION__TARGET, ProcessesPackage.Literals.PROCESS_CONNECTION__TARGET, newTarget);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case ProcessesPackage.PROCESS_CONNECTION__SOURCE:
				ProcessNode source = basicGetSource();
				if (source != null)
					msgs = ((InternalEObject)source).eInverseRemove(this, ProcessesPackage.PROCESS_NODE__OUTGOING_CONNECTIONS, ProcessNode.class, msgs);
				return basicSetSource((ProcessNode)otherEnd, msgs);
			case ProcessesPackage.PROCESS_CONNECTION__TARGET:
				ProcessNode target = basicGetTarget();
				if (target != null)
					msgs = ((InternalEObject)target).eInverseRemove(this, ProcessesPackage.PROCESS_NODE__INCOMING_CONNECTIONS, ProcessNode.class, msgs);
				return basicSetTarget((ProcessNode)otherEnd, msgs);
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
			case ProcessesPackage.PROCESS_CONNECTION__CONTENTS:
				return ((InternalEList<?>)getContents()).basicRemove(otherEnd, msgs);
			case ProcessesPackage.PROCESS_CONNECTION__SOURCE:
				return basicSetSource(null, msgs);
			case ProcessesPackage.PROCESS_CONNECTION__TARGET:
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
			case ProcessesPackage.PROCESS_CONNECTION__ID:
				return getId();
			case ProcessesPackage.PROCESS_CONNECTION__NAME:
				return getName();
			case ProcessesPackage.PROCESS_CONNECTION__DESCRIPTION:
				return getDescription();
			case ProcessesPackage.PROCESS_CONNECTION__CONTENTS:
				return getContents();
			case ProcessesPackage.PROCESS_CONNECTION__SOURCE:
				if (resolve) return getSource();
				return basicGetSource();
			case ProcessesPackage.PROCESS_CONNECTION__TARGET:
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
			case ProcessesPackage.PROCESS_CONNECTION__ID:
				setId((String)newValue);
				return;
			case ProcessesPackage.PROCESS_CONNECTION__NAME:
				setName((String)newValue);
				return;
			case ProcessesPackage.PROCESS_CONNECTION__DESCRIPTION:
				setDescription((String)newValue);
				return;
			case ProcessesPackage.PROCESS_CONNECTION__CONTENTS:
				getContents().clear();
				getContents().addAll((Collection<? extends IContentElement>)newValue);
				return;
			case ProcessesPackage.PROCESS_CONNECTION__SOURCE:
				setSource((ProcessNode)newValue);
				return;
			case ProcessesPackage.PROCESS_CONNECTION__TARGET:
				setTarget((ProcessNode)newValue);
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
			case ProcessesPackage.PROCESS_CONNECTION__ID:
				setId(ID_EDEFAULT);
				return;
			case ProcessesPackage.PROCESS_CONNECTION__NAME:
				setName(NAME_EDEFAULT);
				return;
			case ProcessesPackage.PROCESS_CONNECTION__DESCRIPTION:
				setDescription(DESCRIPTION_EDEFAULT);
				return;
			case ProcessesPackage.PROCESS_CONNECTION__CONTENTS:
				getContents().clear();
				return;
			case ProcessesPackage.PROCESS_CONNECTION__SOURCE:
				setSource((ProcessNode)null);
				return;
			case ProcessesPackage.PROCESS_CONNECTION__TARGET:
				setTarget((ProcessNode)null);
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
			case ProcessesPackage.PROCESS_CONNECTION__ID:
				return ID_EDEFAULT == null ? getId() != null : !ID_EDEFAULT.equals(getId());
			case ProcessesPackage.PROCESS_CONNECTION__NAME:
				return NAME_EDEFAULT == null ? getName() != null : !NAME_EDEFAULT.equals(getName());
			case ProcessesPackage.PROCESS_CONNECTION__DESCRIPTION:
				return DESCRIPTION_EDEFAULT == null ? getDescription() != null : !DESCRIPTION_EDEFAULT.equals(getDescription());
			case ProcessesPackage.PROCESS_CONNECTION__CONTENTS:
				return !getContents().isEmpty();
			case ProcessesPackage.PROCESS_CONNECTION__SOURCE:
				return basicGetSource() != null;
			case ProcessesPackage.PROCESS_CONNECTION__TARGET:
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
				case ProcessesPackage.PROCESS_CONNECTION__NAME: return BasePackage.INAMED__NAME;
				default: return -1;
			}
		}
		if (baseClass == IDescribed.class) {
			switch (derivedFeatureID) {
				case ProcessesPackage.PROCESS_CONNECTION__DESCRIPTION: return BasePackage.IDESCRIBED__DESCRIPTION;
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
				case BasePackage.INAMED__NAME: return ProcessesPackage.PROCESS_CONNECTION__NAME;
				default: return -1;
			}
		}
		if (baseClass == IDescribed.class) {
			switch (baseFeatureID) {
				case BasePackage.IDESCRIBED__DESCRIPTION: return ProcessesPackage.PROCESS_CONNECTION__DESCRIPTION;
				default: return -1;
			}
		}
		return super.eDerivedStructuralFeatureID(baseFeatureID, baseClass);
	}

} //ProcessConnectionImpl
