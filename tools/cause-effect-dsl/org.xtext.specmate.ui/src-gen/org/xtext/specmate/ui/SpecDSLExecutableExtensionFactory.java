/*
 * generated by Xtext 2.17.1
 */
package org.xtext.specmate.ui;

import com.google.inject.Injector;
import org.eclipse.core.runtime.Platform;
import org.eclipse.xtext.ui.guice.AbstractGuiceAwareExecutableExtensionFactory;
import org.osgi.framework.Bundle;
import org.xtext.specmate.ui.internal.SpecmateActivator;

/**
 * This class was generated. Customizations should only happen in a newly
 * introduced subclass. 
 */
public class SpecDSLExecutableExtensionFactory extends AbstractGuiceAwareExecutableExtensionFactory {

	@Override
	protected Bundle getBundle() {
		return Platform.getBundle(SpecmateActivator.PLUGIN_ID);
	}
	
	@Override
	protected Injector getInjector() {
		SpecmateActivator activator = SpecmateActivator.getInstance();
		return activator != null ? activator.getInjector(SpecmateActivator.ORG_XTEXT_SPECMATE_SPECDSL) : null;
	}

}
