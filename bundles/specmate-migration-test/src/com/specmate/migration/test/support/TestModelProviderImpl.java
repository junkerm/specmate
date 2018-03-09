package com.specmate.migration.test.support;

import java.util.Arrays;
import java.util.Collection;

import org.eclipse.emf.ecore.EPackage;
import org.osgi.service.component.annotations.Component;

import com.specmate.migration.testmodel.baseline.artefact.ArtefactPackage;
import com.specmate.migration.testmodel.baseline.base.BasePackage;
import com.specmate.persistency.IPackageProvider;

@Component(service = IPackageProvider.class, property = {"service.ranking:Integer=1"})
public class TestModelProviderImpl implements IPackageProvider {

	@Override
	public Collection<? extends EPackage> getPackages() {
		return Arrays.asList(BasePackage.eINSTANCE, ArtefactPackage.eINSTANCE);
	}

}
