/**
 */
package com.specmate.model.requirements.impl;

import com.specmate.model.base.impl.ISpecmateModelObjectImpl;

import com.specmate.model.requirements.CEGConection;
import com.specmate.model.requirements.CEGNode;
import com.specmate.model.requirements.RequirementsPackage;

import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>CEG Conection</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link com.specmate.model.requirements.impl.CEGConectionImpl#getSource <em>Source</em>}</li>
 *   <li>{@link com.specmate.model.requirements.impl.CEGConectionImpl#getTarget <em>Target</em>}</li>
 *   <li>{@link com.specmate.model.requirements.impl.CEGConectionImpl#isNegate <em>Negate</em>}</li>
 * </ul>
 *
 * @generated
 */
public class CEGConectionImpl extends ISpecmateModelObjectImpl implements CEGConection {
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
	protected CEGConectionImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return RequirementsPackage.Literals.CEG_CONECTION;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CEGNode getSource() {
		return (CEGNode)eDynamicGet(RequirementsPackage.CEG_CONECTION__SOURCE, RequirementsPackage.Literals.CEG_CONECTION__SOURCE, true, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CEGNode basicGetSource() {
		return (CEGNode)eDynamicGet(RequirementsPackage.CEG_CONECTION__SOURCE, RequirementsPackage.Literals.CEG_CONECTION__SOURCE, false, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetSource(CEGNode newSource, NotificationChain msgs) {
		msgs = eDynamicInverseAdd((InternalEObject)newSource, RequirementsPackage.CEG_CONECTION__SOURCE, msgs);
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSource(CEGNode newSource) {
		eDynamicSet(RequirementsPackage.CEG_CONECTION__SOURCE, RequirementsPackage.Literals.CEG_CONECTION__SOURCE, newSource);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CEGNode getTarget() {
		return (CEGNode)eDynamicGet(RequirementsPackage.CEG_CONECTION__TARGET, RequirementsPackage.Literals.CEG_CONECTION__TARGET, true, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CEGNode basicGetTarget() {
		return (CEGNode)eDynamicGet(RequirementsPackage.CEG_CONECTION__TARGET, RequirementsPackage.Literals.CEG_CONECTION__TARGET, false, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetTarget(CEGNode newTarget, NotificationChain msgs) {
		msgs = eDynamicInverseAdd((InternalEObject)newTarget, RequirementsPackage.CEG_CONECTION__TARGET, msgs);
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setTarget(CEGNode newTarget) {
		eDynamicSet(RequirementsPackage.CEG_CONECTION__TARGET, RequirementsPackage.Literals.CEG_CONECTION__TARGET, newTarget);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isNegate() {
		return (Boolean)eDynamicGet(RequirementsPackage.CEG_CONECTION__NEGATE, RequirementsPackage.Literals.CEG_CONECTION__NEGATE, true, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setNegate(boolean newNegate) {
		eDynamicSet(RequirementsPackage.CEG_CONECTION__NEGATE, RequirementsPackage.Literals.CEG_CONECTION__NEGATE, newNegate);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case RequirementsPackage.CEG_CONECTION__SOURCE:
				CEGNode source = basicGetSource();
				if (source != null)
					msgs = ((InternalEObject)source).eInverseRemove(this, RequirementsPackage.CEG_NODE__OUTGOING_CONNECTIONS, CEGNode.class, msgs);
				return basicSetSource((CEGNode)otherEnd, msgs);
			case RequirementsPackage.CEG_CONECTION__TARGET:
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
			case RequirementsPackage.CEG_CONECTION__SOURCE:
				return basicSetSource(null, msgs);
			case RequirementsPackage.CEG_CONECTION__TARGET:
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
			case RequirementsPackage.CEG_CONECTION__SOURCE:
				if (resolve) return getSource();
				return basicGetSource();
			case RequirementsPackage.CEG_CONECTION__TARGET:
				if (resolve) return getTarget();
				return basicGetTarget();
			case RequirementsPackage.CEG_CONECTION__NEGATE:
				return isNegate();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case RequirementsPackage.CEG_CONECTION__SOURCE:
				setSource((CEGNode)newValue);
				return;
			case RequirementsPackage.CEG_CONECTION__TARGET:
				setTarget((CEGNode)newValue);
				return;
			case RequirementsPackage.CEG_CONECTION__NEGATE:
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
			case RequirementsPackage.CEG_CONECTION__SOURCE:
				setSource((CEGNode)null);
				return;
			case RequirementsPackage.CEG_CONECTION__TARGET:
				setTarget((CEGNode)null);
				return;
			case RequirementsPackage.CEG_CONECTION__NEGATE:
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
			case RequirementsPackage.CEG_CONECTION__SOURCE:
				return basicGetSource() != null;
			case RequirementsPackage.CEG_CONECTION__TARGET:
				return basicGetTarget() != null;
			case RequirementsPackage.CEG_CONECTION__NEGATE:
				return isNegate() != NEGATE_EDEFAULT;
		}
		return super.eIsSet(featureID);
	}

} //CEGConectionImpl
