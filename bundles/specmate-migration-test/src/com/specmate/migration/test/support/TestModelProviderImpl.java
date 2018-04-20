package com.specmate.migration.test.support;

import java.util.Arrays;
import java.util.Collection;

import org.eclipse.emf.ecore.EPackage;
import org.osgi.service.component.annotations.Component;

import com.specmate.persistency.IPackageProvider;

@Component(service = IPackageProvider.class, property= {"service.ranking:Integer=1"})
public class TestModelProviderImpl implements IPackageProvider {
	private String modelName;

	@Override
	public Collection<? extends EPackage> getPackages() {
		System.out.println(modelName);
		if(modelName == null) {
			return getBasemodelPackages();
		} else if (modelName.equals(com.specmate.migration.test.attributeadded.testmodel.base.BasePackage.class.getName())) {
			return getAttributeAddedPackages();
		} else if (modelName.equals(com.specmate.migration.test.severalattributesadded.testmodel.base.BasePackage.class.getName())) {
			return getSeveralAttributesAddedPackages();
		} else if (modelName.equals(com.specmate.migration.test.objectadded.testmodel.base.BasePackage.class.getName())) {
			return getObjectAddedPackages();
		} else if (modelName.equals(com.specmate.migration.test.attributerenamed.testmodel.base.BasePackage.class.getName())) {
			return getAttributeRenamedPackages();
		} else if (modelName.equals(com.specmate.migration.test.changedtypes.testmodel.base.BasePackage.class.getName())) {
			return getChangedTypesPackages();
		}
	
		return null;
	}
	
	public void setModelName(String modelName) {
		this.modelName = modelName;
	}
	
	private Collection<? extends EPackage> getBasemodelPackages() {
		return Arrays.asList(com.specmate.migration.test.baseline.testmodel.base.BasePackage.eINSTANCE, 
				com.specmate.migration.test.baseline.testmodel.artefact.ArtefactPackage.eINSTANCE);
	}
	
	private Collection<? extends EPackage> getAttributeAddedPackages() {
		return Arrays.asList(com.specmate.migration.test.attributeadded.testmodel.base.BasePackage.eINSTANCE,
				com.specmate.migration.test.attributeadded.testmodel.artefact.ArtefactPackage.eINSTANCE);
	}
	
	private Collection<? extends EPackage> getSeveralAttributesAddedPackages() {
		return Arrays.asList(com.specmate.migration.test.severalattributesadded.testmodel.base.BasePackage.eINSTANCE, 
				com.specmate.migration.test.severalattributesadded.testmodel.artefact.ArtefactPackage.eINSTANCE);
	}
	
	private Collection<? extends EPackage> getObjectAddedPackages() {
		return Arrays.asList(com.specmate.migration.test.objectadded.testmodel.base.BasePackage.eINSTANCE, 
				com.specmate.migration.test.objectadded.testmodel.artefact.ArtefactPackage.eINSTANCE);
	}
	
	private Collection<? extends EPackage> getAttributeRenamedPackages() {
		return Arrays.asList(com.specmate.migration.test.attributerenamed.testmodel.base.BasePackage.eINSTANCE,
				com.specmate.migration.test.attributerenamed.testmodel.artefact.ArtefactPackage.eINSTANCE);
	}
	
	private Collection<? extends EPackage> getChangedTypesPackages() {
		return Arrays.asList(com.specmate.migration.test.changedtypes.testmodel.base.BasePackage.eINSTANCE,
				com.specmate.migration.test.changedtypes.testmodel.artefact.ArtefactPackage.eINSTANCE);
	}
}
