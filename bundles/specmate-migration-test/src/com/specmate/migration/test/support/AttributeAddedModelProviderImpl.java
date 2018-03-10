package com.specmate.migration.test.support;

import java.util.Arrays;
import java.util.Collection;

import org.eclipse.emf.ecore.EPackage;
import org.osgi.service.component.annotations.Component;

import com.specmate.migration.test.attributeadded.testmodel.artefact.ArtefactPackage;
import com.specmate.migration.test.attributeadded.testmodel.base.BasePackage;
import com.specmate.persistency.IPackageProvider;

@Component(service = IPackageProvider.class, property = {"service.ranking:Integer=0"})
public class AttributeAddedModelProviderImpl implements IPackageProvider {

	@Override
	public Collection<? extends EPackage> getPackages() {
		return Arrays.asList(BasePackage.eINSTANCE, ArtefactPackage.eINSTANCE);
	}

}
