package com.specmate.persistency;

import java.util.Collection;

import org.eclipse.emf.ecore.EPackage;

/** Provides EMF model packages */
public interface IPackageProvider {
	
	/** Retrieves the packages provided by this model provider */
	Collection<? extends EPackage> getPackages();
}
