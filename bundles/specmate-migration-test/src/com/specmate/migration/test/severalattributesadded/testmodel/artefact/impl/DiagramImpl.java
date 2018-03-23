/**
 */
package com.specmate.migration.test.severalattributesadded.testmodel.artefact.impl;

import com.specmate.migration.test.severalattributesadded.testmodel.artefact.ArtefactPackage;
import com.specmate.migration.test.severalattributesadded.testmodel.artefact.Diagram;

import com.specmate.migration.test.severalattributesadded.testmodel.base.BasePackage;
import com.specmate.migration.test.severalattributesadded.testmodel.base.IContainer;
import com.specmate.migration.test.severalattributesadded.testmodel.base.IContentElement;
import com.specmate.migration.test.severalattributesadded.testmodel.base.IID;
import com.specmate.migration.test.severalattributesadded.testmodel.base.INamed;

import java.util.Collection;

import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Diagram</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link com.specmate.migration.test.severalattributesadded.testmodel.artefact.impl.DiagramImpl#isTested <em>Tested</em>}</li>
 *   <li>{@link com.specmate.migration.test.severalattributesadded.testmodel.artefact.impl.DiagramImpl#getName <em>Name</em>}</li>
 *   <li>{@link com.specmate.migration.test.severalattributesadded.testmodel.artefact.impl.DiagramImpl#getId <em>Id</em>}</li>
 *   <li>{@link com.specmate.migration.test.severalattributesadded.testmodel.artefact.impl.DiagramImpl#getContents <em>Contents</em>}</li>
 *   <li>{@link com.specmate.migration.test.severalattributesadded.testmodel.artefact.impl.DiagramImpl#getAmount <em>Amount</em>}</li>
 *   <li>{@link com.specmate.migration.test.severalattributesadded.testmodel.artefact.impl.DiagramImpl#getLength <em>Length</em>}</li>
 *   <li>{@link com.specmate.migration.test.severalattributesadded.testmodel.artefact.impl.DiagramImpl#getLinked <em>Linked</em>}</li>
 * </ul>
 *
 * @generated
 */
public class DiagramImpl extends MinimalEObjectImpl.Container implements Diagram {
	/**
	 * The default value of the '{@link #isTested() <em>Tested</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isTested()
	 * @generated
	 * @ordered
	 */
	protected static final boolean TESTED_EDEFAULT = false;

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
	 * The default value of the '{@link #getId() <em>Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getId()
	 * @generated
	 * @ordered
	 */
	protected static final String ID_EDEFAULT = null;

	/**
	 * The default value of the '{@link #getAmount() <em>Amount</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAmount()
	 * @generated
	 * @ordered
	 */
	protected static final Integer AMOUNT_EDEFAULT = null;

	/**
	 * The default value of the '{@link #getLength() <em>Length</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLength()
	 * @generated
	 * @ordered
	 */
	protected static final Double LENGTH_EDEFAULT = null;

	/**
	 * The default value of the '{@link #getLinked() <em>Linked</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLinked()
	 * @generated
	 * @ordered
	 */
	protected static final Boolean LINKED_EDEFAULT = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected DiagramImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ArtefactPackage.Literals.DIAGRAM;
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
	public boolean isTested() {
		return (Boolean)eDynamicGet(ArtefactPackage.DIAGRAM__TESTED, BasePackage.Literals.ITESTABLE__TESTED, true, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setTested(boolean newTested) {
		eDynamicSet(ArtefactPackage.DIAGRAM__TESTED, BasePackage.Literals.ITESTABLE__TESTED, newTested);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getName() {
		return (String)eDynamicGet(ArtefactPackage.DIAGRAM__NAME, BasePackage.Literals.INAMED__NAME, true, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setName(String newName) {
		eDynamicSet(ArtefactPackage.DIAGRAM__NAME, BasePackage.Literals.INAMED__NAME, newName);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getId() {
		return (String)eDynamicGet(ArtefactPackage.DIAGRAM__ID, BasePackage.Literals.IID__ID, true, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setId(String newId) {
		eDynamicSet(ArtefactPackage.DIAGRAM__ID, BasePackage.Literals.IID__ID, newId);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	public EList<IContentElement> getContents() {
		return (EList<IContentElement>)eDynamicGet(ArtefactPackage.DIAGRAM__CONTENTS, BasePackage.Literals.ICONTAINER__CONTENTS, true, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Integer getAmount() {
		return (Integer)eDynamicGet(ArtefactPackage.DIAGRAM__AMOUNT, ArtefactPackage.Literals.DIAGRAM__AMOUNT, true, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setAmount(Integer newAmount) {
		eDynamicSet(ArtefactPackage.DIAGRAM__AMOUNT, ArtefactPackage.Literals.DIAGRAM__AMOUNT, newAmount);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Double getLength() {
		return (Double)eDynamicGet(ArtefactPackage.DIAGRAM__LENGTH, ArtefactPackage.Literals.DIAGRAM__LENGTH, true, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setLength(Double newLength) {
		eDynamicSet(ArtefactPackage.DIAGRAM__LENGTH, ArtefactPackage.Literals.DIAGRAM__LENGTH, newLength);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Boolean getLinked() {
		return (Boolean)eDynamicGet(ArtefactPackage.DIAGRAM__LINKED, ArtefactPackage.Literals.DIAGRAM__LINKED, true, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setLinked(Boolean newLinked) {
		eDynamicSet(ArtefactPackage.DIAGRAM__LINKED, ArtefactPackage.Literals.DIAGRAM__LINKED, newLinked);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case ArtefactPackage.DIAGRAM__CONTENTS:
				return ((InternalEList<?>)getContents()).basicRemove(otherEnd, msgs);
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
			case ArtefactPackage.DIAGRAM__TESTED:
				return isTested();
			case ArtefactPackage.DIAGRAM__NAME:
				return getName();
			case ArtefactPackage.DIAGRAM__ID:
				return getId();
			case ArtefactPackage.DIAGRAM__CONTENTS:
				return getContents();
			case ArtefactPackage.DIAGRAM__AMOUNT:
				return getAmount();
			case ArtefactPackage.DIAGRAM__LENGTH:
				return getLength();
			case ArtefactPackage.DIAGRAM__LINKED:
				return getLinked();
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
			case ArtefactPackage.DIAGRAM__TESTED:
				setTested((Boolean)newValue);
				return;
			case ArtefactPackage.DIAGRAM__NAME:
				setName((String)newValue);
				return;
			case ArtefactPackage.DIAGRAM__ID:
				setId((String)newValue);
				return;
			case ArtefactPackage.DIAGRAM__CONTENTS:
				getContents().clear();
				getContents().addAll((Collection<? extends IContentElement>)newValue);
				return;
			case ArtefactPackage.DIAGRAM__AMOUNT:
				setAmount((Integer)newValue);
				return;
			case ArtefactPackage.DIAGRAM__LENGTH:
				setLength((Double)newValue);
				return;
			case ArtefactPackage.DIAGRAM__LINKED:
				setLinked((Boolean)newValue);
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
			case ArtefactPackage.DIAGRAM__TESTED:
				setTested(TESTED_EDEFAULT);
				return;
			case ArtefactPackage.DIAGRAM__NAME:
				setName(NAME_EDEFAULT);
				return;
			case ArtefactPackage.DIAGRAM__ID:
				setId(ID_EDEFAULT);
				return;
			case ArtefactPackage.DIAGRAM__CONTENTS:
				getContents().clear();
				return;
			case ArtefactPackage.DIAGRAM__AMOUNT:
				setAmount(AMOUNT_EDEFAULT);
				return;
			case ArtefactPackage.DIAGRAM__LENGTH:
				setLength(LENGTH_EDEFAULT);
				return;
			case ArtefactPackage.DIAGRAM__LINKED:
				setLinked(LINKED_EDEFAULT);
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
			case ArtefactPackage.DIAGRAM__TESTED:
				return isTested() != TESTED_EDEFAULT;
			case ArtefactPackage.DIAGRAM__NAME:
				return NAME_EDEFAULT == null ? getName() != null : !NAME_EDEFAULT.equals(getName());
			case ArtefactPackage.DIAGRAM__ID:
				return ID_EDEFAULT == null ? getId() != null : !ID_EDEFAULT.equals(getId());
			case ArtefactPackage.DIAGRAM__CONTENTS:
				return !getContents().isEmpty();
			case ArtefactPackage.DIAGRAM__AMOUNT:
				return AMOUNT_EDEFAULT == null ? getAmount() != null : !AMOUNT_EDEFAULT.equals(getAmount());
			case ArtefactPackage.DIAGRAM__LENGTH:
				return LENGTH_EDEFAULT == null ? getLength() != null : !LENGTH_EDEFAULT.equals(getLength());
			case ArtefactPackage.DIAGRAM__LINKED:
				return LINKED_EDEFAULT == null ? getLinked() != null : !LINKED_EDEFAULT.equals(getLinked());
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
				case ArtefactPackage.DIAGRAM__NAME: return BasePackage.INAMED__NAME;
				default: return -1;
			}
		}
		if (baseClass == IID.class) {
			switch (derivedFeatureID) {
				case ArtefactPackage.DIAGRAM__ID: return BasePackage.IID__ID;
				default: return -1;
			}
		}
		if (baseClass == IContentElement.class) {
			switch (derivedFeatureID) {
				default: return -1;
			}
		}
		if (baseClass == IContainer.class) {
			switch (derivedFeatureID) {
				case ArtefactPackage.DIAGRAM__CONTENTS: return BasePackage.ICONTAINER__CONTENTS;
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
				case BasePackage.INAMED__NAME: return ArtefactPackage.DIAGRAM__NAME;
				default: return -1;
			}
		}
		if (baseClass == IID.class) {
			switch (baseFeatureID) {
				case BasePackage.IID__ID: return ArtefactPackage.DIAGRAM__ID;
				default: return -1;
			}
		}
		if (baseClass == IContentElement.class) {
			switch (baseFeatureID) {
				default: return -1;
			}
		}
		if (baseClass == IContainer.class) {
			switch (baseFeatureID) {
				case BasePackage.ICONTAINER__CONTENTS: return ArtefactPackage.DIAGRAM__CONTENTS;
				default: return -1;
			}
		}
		return super.eDerivedStructuralFeatureID(baseFeatureID, baseClass);
	}

} //DiagramImpl
