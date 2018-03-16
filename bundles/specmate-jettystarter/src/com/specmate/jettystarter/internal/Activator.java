package com.specmate.jettystarter.internal;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.startlevel.BundleStartLevel;

public class Activator implements BundleActivator {

	@Override
	public void start(BundleContext context) throws Exception {

				Bundle bundle = FrameworkUtil.getBundle(org.eclipse.equinox.http.jetty.JettyConstants.class);

				BundleStartLevel bundleStartLevel = bundle.adapt(BundleStartLevel.class);

				if (bundleStartLevel.isActivationPolicyUsed()) {
					bundle.stop();
					bundle.start();
				}
	}

	@Override
	public void stop(BundleContext context) throws Exception {
		// TODO Auto-generated method stub

	}

}
