/**
 */
package com.specmate.model.requirements.impl;

import com.specmate.model.base.BasePackage;
import com.specmate.model.base.IContentElement;
import com.specmate.model.base.IDescribed;
import com.specmate.model.base.INamed;

import com.specmate.model.requirements.CEGConnection;
import com.specmate.model.requirements.CEGNode;
import com.specmate.model.requirements.NodeType;
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
 * An implementation of the model object '<em><b>CEG Node</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link com.specmate.model.requirements.impl.CEGNodeImpl#getId <em>Id</em>}</li>
 *   <li>{@link com.specmate.model.requirements.impl.CEGNodeImpl#getName <em>Name</em>}</li>
 *   <li>{@link com.specmate.model.requirements.impl.CEGNodeImpl#getDescription <em>Description</em>}</li>
 *   <li>{@link com.specmate.model.requirements.impl.CEGNodeImpl#getContents <em>Contents</em>}</li>
 *   <li>{@link com.specmate.model.requirements.impl.CEGNodeImpl#getX <em>X</em>}</li>
 *   <li>{@link com.specmate.model.requirements.impl.CEGNodeImpl#getY <em>Y</em>}</li>
 *   <li>{@link com.specmate.model.requirements.impl.CEGNodeImpl#getType <em>Type</em>}</li>
 *   <li>{@link com.specmate.model.requirements.impl.CEGNodeImpl#getOutgoingConnections <em>Outgoing Connections</em>}</li>
 *   <li>{@link com.specmate.model.requirements.impl.CEGNodeImpl#getIncomingConnections <em>Incoming Connections</em>}</li>
 *   <li>{@link com.specmate.model.requirements.impl.CEGNodeImpl#getVariable <em>Variable</em>}</li>
 *   <li>{@link com.specmate.model.requirements.impl.CEGNodeImpl#getCondition <em>Condition</em>}</li>
 * </ul>
 *
 * @generated
 */
public class CEGNodeImpl extends CDOObjectImpl implements CEGNode {
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
	 * The default value of the '{@link #getType() <em>Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getType()
	 * @generated
	 * @ordered
	 */
	protected static final NodeType TYPE_EDEFAULT = NodeType.AND;

	/**
	 * The default value of the '{@link #getVariable() <em>Variable</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getVariable()
	 * @generated
	 * @ordered
	 */
	protected static final String VARIABLE_EDEFAULT = null;

	/**
	 * The default value of the '{@link #getCondition() <em>Condition</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCondition()
	 * @generated
	 * @ordered
	 */
	protected static final String CONDITION_EDEFAULT = null;

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
		return (String)eDynamicGet(RequirementsPackage.CEG_NODE__ID, BasePackage.Literals.IID__ID, true, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setId(String newId) {
		eDynamicSet(RequirementsPackage.CEG_NODE__ID, BasePackage.Literals.IID__ID, newId);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getName() {
		return (String)eDynamicGet(RequirementsPackage.CEG_NODE__NAME, BasePackage.Literals.INAMED__NAME, true, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setName(String newName) {
		eDynamicSet(RequirementsPackage.CEG_NODE__NAME, BasePackage.Literals.INAMED__NAME, newName);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getDescription() {
		return (String)eDynamicGet(RequirementsPackage.CEG_NODE__DESCRIPTION, BasePackage.Literals.IDESCRIBED__DESCRIPTION, true, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDescription(String newDescription) {
		eDynamicSet(RequirementsPackage.CEG_NODE__DESCRIPTION, BasePackage.Literals.IDESCRIBED__DESCRIPTION, newDescription);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	public EList<IContentElement> getContents() {
		return (EList<IContentElement>)eDynamicGet(RequirementsPackage.CEG_NODE__CONTENTS, BasePackage.Literals.ICONTAINER__CONTENTS, true, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public double getX() {
		return (Double)eDynamicGet(RequirementsPackage.CEG_NODE__X, BasePackage.Literals.ISPECMATE_POSITIONABLE_MODEL_OBJECT__X, true, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setX(double newX) {
		eDynamicSet(RequirementsPackage.CEG_NODE__X, BasePackage.Literals.ISPECMATE_POSITIONABLE_MODEL_OBJECT__X, newX);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public double getY() {
		return (Double)eDynamicGet(RequirementsPackage.CEG_NODE__Y, BasePackage.Literals.ISPECMATE_POSITIONABLE_MODEL_OBJECT__Y, true, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setY(double newY) {
		eDynamicSet(RequirementsPackage.CEG_NODE__Y, BasePackage.Literals.ISPECMATE_POSITIONABLE_MODEL_OBJECT__Y, newY);
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
	public EList<CEGConnection> getOutgoingConnections() {
		return (EList<CEGConnection>)eDynamicGet(RequirementsPackage.CEG_NODE__OUTGOING_CONNECTIONS, RequirementsPackage.Literals.CEG_NODE__OUTGOING_CONNECTIONS, true, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	public EList<CEGConnection> getIncomingConnections() {
		return (EList<CEGConnection>)eDynamicGet(RequirementsPackage.CEG_NODE__INCOMING_CONNECTIONS, RequirementsPackage.Literals.CEG_NODE__INCOMING_CONNECTIONS, true, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getVariable() {
		return (String)eDynamicGet(RequirementsPackage.CEG_NODE__VARIABLE, RequirementsPackage.Literals.CEG_NODE__VARIABLE, true, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setVariable(String newVariable) {
		eDynamicSet(RequirementsPackage.CEG_NODE__VARIABLE, RequirementsPackage.Literals.CEG_NODE__VARIABLE, newVariable);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getCondition() {
		return (String)eDynamicGet(RequirementsPackage.CEG_NODE__CONDITION, RequirementsPackage.Literals.CEG_NODE__CONDITION, true, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setCondition(String newCondition) {
		eDynamicSet(RequirementsPackage.CEG_NODE__CONDITION, RequirementsPackage.Literals.CEG_NODE__CONDITION, newCondition);
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
			case RequirementsPackage.CEG_NODE__INCOMING_CONNECTIONS:
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
			case RequirementsPackage.CEG_NODE__CONTENTS:
				return ((InternalEList<?>)getContents()).basicRemove(otherEnd, msgs);
			case RequirementsPackage.CEG_NODE__OUTGOING_CONNECTIONS:
				return ((InternalEList<?>)getOutgoingConnections()).basicRemove(otherEnd, msgs);
			case RequirementsPackage.CEG_NODE__INCOMING_CONNECTIONS:
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
			case RequirementsPackage.CEG_NODE__ID:
				return getId();
			case RequirementsPackage.CEG_NODE__NAME:
				return getName();
			case RequirementsPackage.CEG_NODE__DESCRIPTION:
				return getDescription();
			case RequirementsPackage.CEG_NODE__CONTENTS:
				return getContents();
			case RequirementsPackage.CEG_NODE__X:
				return getX();
			case RequirementsPackage.CEG_NODE__Y:
				return getY();
			case RequirementsPackage.CEG_NODE__TYPE:
				return getType();
			case RequirementsPackage.CEG_NODE__OUTGOING_CONNECTIONS:
				return getOutgoingConnections();
			case RequirementsPackage.CEG_NODE__INCOMING_CONNECTIONS:
				return getIncomingConnections();
			case RequirementsPackage.CEG_NODE__VARIABLE:
				return getVariable();
			case RequirementsPackage.CEG_NODE__CONDITION:
				return getCondition();
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
			case RequirementsPackage.CEG_NODE__ID:
				setId((String)newValue);
				return;
			case RequirementsPackage.CEG_NODE__NAME:
				setName((String)newValue);
				return;
			case RequirementsPackage.CEG_NODE__DESCRIPTION:
				setDescription((String)newValue);
				return;
			case RequirementsPackage.CEG_NODE__CONTENTS:
				getContents().clear();
				getContents().addAll((Collection<? extends IContentElement>)newValue);
				return;
			case RequirementsPackage.CEG_NODE__X:
				setX((Double)newValue);
				return;
			case RequirementsPackage.CEG_NODE__Y:
				setY((Double)newValue);
				return;
			case RequirementsPackage.CEG_NODE__TYPE:
				setType((NodeType)newValue);
				return;
			case RequirementsPackage.CEG_NODE__OUTGOING_CONNECTIONS:
				getOutgoingConnections().clear();
				getOutgoingConnections().addAll((Collection<? extends CEGConnection>)newValue);
				return;
			case RequirementsPackage.CEG_NODE__INCOMING_CONNECTIONS:
				getIncomingConnections().clear();
				getIncomingConnections().addAll((Collection<? extends CEGConnection>)newValue);
				return;
			case RequirementsPackage.CEG_NODE__VARIABLE:
				setVariable((String)newValue);
				return;
			case RequirementsPackage.CEG_NODE__CONDITION:
				setCondition((String)newValue);
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
			case RequirementsPackage.CEG_NODE__ID:
				setId(ID_EDEFAULT);
				return;
			case RequirementsPackage.CEG_NODE__NAME:
				setName(NAME_EDEFAULT);
				return;
			case RequirementsPackage.CEG_NODE__DESCRIPTION:
				setDescription(DESCRIPTION_EDEFAULT);
				return;
			case RequirementsPackage.CEG_NODE__CONTENTS:
				getContents().clear();
				return;
			case RequirementsPackage.CEG_NODE__X:
				setX(X_EDEFAULT);
				return;
			case RequirementsPackage.CEG_NODE__Y:
				setY(Y_EDEFAULT);
				return;
			case RequirementsPackage.CEG_NODE__TYPE:
				setType(TYPE_EDEFAULT);
				return;
			case RequirementsPackage.CEG_NODE__OUTGOING_CONNECTIONS:
				getOutgoingConnections().clear();
				return;
			case RequirementsPackage.CEG_NODE__INCOMING_CONNECTIONS:
				getIncomingConnections().clear();
				return;
			case RequirementsPackage.CEG_NODE__VARIABLE:
				setVariable(VARIABLE_EDEFAULT);
				return;
			case RequirementsPackage.CEG_NODE__CONDITION:
				setCondition(CONDITION_EDEFAULT);
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
			case RequirementsPackage.CEG_NODE__ID:
				return ID_EDEFAULT == null ? getId() != null : !ID_EDEFAULT.equals(getId());
			case RequirementsPackage.CEG_NODE__NAME:
				return NAME_EDEFAULT == null ? getName() != null : !NAME_EDEFAULT.equals(getName());
			case RequirementsPackage.CEG_NODE__DESCRIPTION:
				return DESCRIPTION_EDEFAULT == null ? getDescription() != null : !DESCRIPTION_EDEFAULT.equals(getDescription());
			case RequirementsPackage.CEG_NODE__CONTENTS:
				return !getContents().isEmpty();
			case RequirementsPackage.CEG_NODE__X:
				return getX() != X_EDEFAULT;
			case RequirementsPackage.CEG_NODE__Y:
				return getY() != Y_EDEFAULT;
			case RequirementsPackage.CEG_NODE__TYPE:
				return getType() != TYPE_EDEFAULT;
			case RequirementsPackage.CEG_NODE__OUTGOING_CONNECTIONS:
				return !getOutgoingConnections().isEmpty();
			case RequirementsPackage.CEG_NODE__INCOMING_CONNECTIONS:
				return !getIncomingConnections().isEmpty();
			case RequirementsPackage.CEG_NODE__VARIABLE:
				return VARIABLE_EDEFAULT == null ? getVariable() != null : !VARIABLE_EDEFAULT.equals(getVariable());
			case RequirementsPackage.CEG_NODE__CONDITION:
				return CONDITION_EDEFAULT == null ? getCondition() != null : !CONDITION_EDEFAULT.equals(getCondition());
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
				case RequirementsPackage.CEG_NODE__NAME: return BasePackage.INAMED__NAME;
				default: return -1;
			}
		}
		if (baseClass == IDescribed.class) {
			switch (derivedFeatureID) {
				case RequirementsPackage.CEG_NODE__DESCRIPTION: return BasePackage.IDESCRIBED__DESCRIPTION;
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
				case BasePackage.INAMED__NAME: return RequirementsPackage.CEG_NODE__NAME;
				default: return -1;
			}
		}
		if (baseClass == IDescribed.class) {
			switch (baseFeatureID) {
				case BasePackage.IDESCRIBED__DESCRIPTION: return RequirementsPackage.CEG_NODE__DESCRIPTION;
				default: return -1;
			}
		}
		return super.eDerivedStructuralFeatureID(baseFeatureID, baseClass);
	}

} //CEGNodeImpl
