/**
 */
package com.specmate.model.requirements.impl;

import com.specmate.model.base.impl.ISpecmateModelObjectImpl;

import com.specmate.model.requirements.CEGConection;
import com.specmate.model.requirements.CEGNode;
import com.specmate.model.requirements.NodeType;
import com.specmate.model.requirements.RequirementsPackage;

import java.util.Collection;

import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>CEG Node</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link com.specmate.model.requirements.impl.CEGNodeImpl#getType <em>Type</em>}</li>
 *   <li>{@link com.specmate.model.requirements.impl.CEGNodeImpl#getOutgoingConnections <em>Outgoing Connections</em>}</li>
 *   <li>{@link com.specmate.model.requirements.impl.CEGNodeImpl#getIncomingConnection <em>Incoming Connection</em>}</li>
 * </ul>
 *
 * @generated
 */
public class CEGNodeImpl extends ISpecmateModelObjectImpl implements CEGNode {
	/**
	 * The default value of the '{@link #getType() <em>Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getType()
	 * @generated
	 * @ordered
	 */
	protected static final NodeType TYPE_EDEFAULT = NodeType.AND;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected CEGNodeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return RequirementsPackage.Literals.CEG_NODE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NodeType getType() {
		return (NodeType)eDynamicGet(RequirementsPackage.CEG_NODE__TYPE, RequirementsPackage.Literals.CEG_NODE__TYPE, true, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setType(NodeType newType) {
		eDynamicSet(RequirementsPackage.CEG_NODE__TYPE, RequirementsPackage.Literals.CEG_NODE__TYPE, newType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	public EList<CEGConection> getOutgoingConnections() {
		return (EList<CEGConection>)eDynamicGet(RequirementsPackage.CEG_NODE__OUTGOING_CONNECTIONS, RequirementsPackage.Literals.CEG_NODE__OUTGOING_CONNECTIONS, true, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	public EList<CEGConection> getIncomingConnection() {
		return (EList<CEGConection>)eDynamicGet(RequirementsPackage.CEG_NODE__INCOMING_CONNECTION, RequirementsPackage.Literals.CEG_NODE__INCOMING_CONNECTION, true, true);
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
			case RequirementsPackage.CEG_NODE__OUTGOING_CONNECTIONS:
				return ((InternalEList<InternalEObject>)(InternalEList<?>)getOutgoingConnections()).basicAdd(otherEnd, msgs);
			case RequirementsPackage.CEG_NODE__INCOMING_CONNECTION:
				return ((InternalEList<InternalEObject>)(InternalEList<?>)getIncomingConnection()).basicAdd(otherEnd, msgs);
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
			case RequirementsPackage.CEG_NODE__OUTGOING_CONNECTIONS:
				return ((InternalEList<?>)getOutgoingConnections()).basicRemove(otherEnd, msgs);
			case RequirementsPackage.CEG_NODE__INCOMING_CONNECTION:
				return ((InternalEList<?>)getIncomingConnection()).basicRemove(otherEnd, msgs);
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
			case RequirementsPackage.CEG_NODE__TYPE:
				return getType();
			case RequirementsPackage.CEG_NODE__OUTGOING_CONNECTIONS:
				return getOutgoingConnections();
			case RequirementsPackage.CEG_NODE__INCOMING_CONNECTION:
				return getIncomingConnection();
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
			case RequirementsPackage.CEG_NODE__TYPE:
				setType((NodeType)newValue);
				return;
			case RequirementsPackage.CEG_NODE__OUTGOING_CONNECTIONS:
				getOutgoingConnections().clear();
				getOutgoingConnections().addAll((Collection<? extends CEGConection>)newValue);
				return;
			case RequirementsPackage.CEG_NODE__INCOMING_CONNECTION:
				getIncomingConnection().clear();
				getIncomingConnection().addAll((Collection<? extends CEGConection>)newValue);
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
			case RequirementsPackage.CEG_NODE__TYPE:
				setType(TYPE_EDEFAULT);
				return;
			case RequirementsPackage.CEG_NODE__OUTGOING_CONNECTIONS:
				getOutgoingConnections().clear();
				return;
			case RequirementsPackage.CEG_NODE__INCOMING_CONNECTION:
				getIncomingConnection().clear();
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
			case RequirementsPackage.CEG_NODE__TYPE:
				return getType() != TYPE_EDEFAULT;
			case RequirementsPackage.CEG_NODE__OUTGOING_CONNECTIONS:
				return !getOutgoingConnections().isEmpty();
			case RequirementsPackage.CEG_NODE__INCOMING_CONNECTION:
				return !getIncomingConnection().isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //CEGNodeImpl
