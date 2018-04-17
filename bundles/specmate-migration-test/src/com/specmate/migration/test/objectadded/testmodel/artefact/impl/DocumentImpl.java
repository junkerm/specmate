/**
 */
package com.specmate.migration.test.objectadded.testmodel.artefact.impl;

import com.specmate.migration.test.objectadded.testmodel.artefact.ArtefactPackage;
import com.specmate.migration.test.objectadded.testmodel.artefact.Document;

import com.specmate.migration.test.objectadded.testmodel.base.BasePackage;
import com.specmate.migration.test.objectadded.testmodel.base.IContainer;
import com.specmate.migration.test.objectadded.testmodel.base.IContentElement;
import com.specmate.migration.test.objectadded.testmodel.base.IID;

import java.util.Collection;

import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Document</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link com.specmate.migration.test.objectadded.testmodel.artefact.impl.DocumentImpl#isTested <em>Tested</em>}</li>
 *   <li>{@link com.specmate.migration.test.objectadded.testmodel.artefact.impl.DocumentImpl#getId <em>Id</em>}</li>
 *   <li>{@link com.specmate.migration.test.objectadded.testmodel.artefact.impl.DocumentImpl#getContents <em>Contents</em>}</li>
 *   <li>{@link com.specmate.migration.test.objectadded.testmodel.artefact.impl.DocumentImpl#getLength <em>Length</em>}</li>
 *   <li>{@link com.specmate.migration.test.objectadded.testmodel.artefact.impl.DocumentImpl#getOwner <em>Owner</em>}</li>
 * </ul>
 *
 * @generated
 */
public class DocumentImpl extends MinimalEObjectImpl.Container implements Document {
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
	 * The default value of the '{@link #getId() <em>Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getId()
	 * @generated
	 * @ordered
	 */
	protected static final String ID_EDEFAULT = null;

	/**
	 * The default value of the '{@link #getLength() <em>Length</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLength()
	 * @generated
	 * @ordered
	 */
	protected static final long LENGTH_EDEFAULT = 0L;

	/**
	 * The default value of the '{@link #getOwner() <em>Owner</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOwner()
	 * @generated
	 * @ordered
	 */
	protected static final String OWNER_EDEFAULT = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected DocumentImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ArtefactPackage.Literals.DOCUMENT;
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
		return (Boolean)eDynamicGet(ArtefactPackage.DOCUMENT__TESTED, BasePackage.Literals.ITESTABLE__TESTED, true, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setTested(boolean newTested) {
		eDynamicSet(ArtefactPackage.DOCUMENT__TESTED, BasePackage.Literals.ITESTABLE__TESTED, newTested);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getId() {
		return (String)eDynamicGet(ArtefactPackage.DOCUMENT__ID, BasePackage.Literals.IID__ID, true, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setId(String newId) {
		eDynamicSet(ArtefactPackage.DOCUMENT__ID, BasePackage.Literals.IID__ID, newId);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	public EList<IContentElement> getContents() {
		return (EList<IContentElement>)eDynamicGet(ArtefactPackage.DOCUMENT__CONTENTS, BasePackage.Literals.ICONTAINER__CONTENTS, true, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public long getLength() {
		return (Long)eDynamicGet(ArtefactPackage.DOCUMENT__LENGTH, ArtefactPackage.Literals.DOCUMENT__LENGTH, true, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setLength(long newLength) {
		eDynamicSet(ArtefactPackage.DOCUMENT__LENGTH, ArtefactPackage.Literals.DOCUMENT__LENGTH, newLength);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getOwner() {
		return (String)eDynamicGet(ArtefactPackage.DOCUMENT__OWNER, ArtefactPackage.Literals.DOCUMENT__OWNER, true, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setOwner(String newOwner) {
		eDynamicSet(ArtefactPackage.DOCUMENT__OWNER, ArtefactPackage.Literals.DOCUMENT__OWNER, newOwner);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case ArtefactPackage.DOCUMENT__CONTENTS:
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
			case ArtefactPackage.DOCUMENT__TESTED:
				return isTested();
			case ArtefactPackage.DOCUMENT__ID:
				return getId();
			case ArtefactPackage.DOCUMENT__CONTENTS:
				return getContents();
			case ArtefactPackage.DOCUMENT__LENGTH:
				return getLength();
			case ArtefactPackage.DOCUMENT__OWNER:
				return getOwner();
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
			case ArtefactPackage.DOCUMENT__TESTED:
				setTested((Boolean)newValue);
				return;
			case ArtefactPackage.DOCUMENT__ID:
				setId((String)newValue);
				return;
			case ArtefactPackage.DOCUMENT__CONTENTS:
				getContents().clear();
				getContents().addAll((Collection<? extends IContentElement>)newValue);
				return;
			case ArtefactPackage.DOCUMENT__LENGTH:
				setLength((Long)newValue);
				return;
			case ArtefactPackage.DOCUMENT__OWNER:
				setOwner((String)newValue);
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
			case ArtefactPackage.DOCUMENT__TESTED:
				setTested(TESTED_EDEFAULT);
				return;
			case ArtefactPackage.DOCUMENT__ID:
				setId(ID_EDEFAULT);
				return;
			case ArtefactPackage.DOCUMENT__CONTENTS:
				getContents().clear();
				return;
			case ArtefactPackage.DOCUMENT__LENGTH:
				setLength(LENGTH_EDEFAULT);
				return;
			case ArtefactPackage.DOCUMENT__OWNER:
				setOwner(OWNER_EDEFAULT);
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
			case ArtefactPackage.DOCUMENT__TESTED:
				return isTested() != TESTED_EDEFAULT;
			case ArtefactPackage.DOCUMENT__ID:
				return ID_EDEFAULT == null ? getId() != null : !ID_EDEFAULT.equals(getId());
			case ArtefactPackage.DOCUMENT__CONTENTS:
				return !getContents().isEmpty();
			case ArtefactPackage.DOCUMENT__LENGTH:
				return getLength() != LENGTH_EDEFAULT;
			case ArtefactPackage.DOCUMENT__OWNER:
				return OWNER_EDEFAULT == null ? getOwner() != null : !OWNER_EDEFAULT.equals(getOwner());
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
		if (baseClass == IID.class) {
			switch (derivedFeatureID) {
				case ArtefactPackage.DOCUMENT__ID: return BasePackage.IID__ID;
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
				case ArtefactPackage.DOCUMENT__CONTENTS: return BasePackage.ICONTAINER__CONTENTS;
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
		if (baseClass == IID.class) {
			switch (baseFeatureID) {
				case BasePackage.IID__ID: return ArtefactPackage.DOCUMENT__ID;
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
				case BasePackage.ICONTAINER__CONTENTS: return ArtefactPackage.DOCUMENT__CONTENTS;
				default: return -1;
			}
		}
		return super.eDerivedStructuralFeatureID(baseFeatureID, baseClass);
	}

} //DocumentImpl
