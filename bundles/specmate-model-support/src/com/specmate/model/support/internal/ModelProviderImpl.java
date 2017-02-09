package com.specmate.model.support.internal;

import java.util.Arrays;
import java.util.Collection;

import org.eclipse.emf.ecore.EPackage;
import org.osgi.service.component.annotations.Component;

import com.specmate.model.base.BasePackage;
import com.specmate.model.requirements.RequirementsPackage;
import com.specmate.persistency.IPackageProvider;

@Component(service = IPackageProvider.class)
public class ModelProviderImpl implements IPackageProvider {

	@Override
	public Collection<? extends EPackage> getPackages() {
		return Arrays.asList(BasePackage.eINSTANCE, RequirementsPackage.eINSTANCE);
	}

}
