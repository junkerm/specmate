package com.specmate.logging.slf4j.julbridge;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.slf4j.bridge.SLF4JBridgeHandler;

public class Activator implements BundleActivator{

	@Override
	public void start(BundleContext context) throws Exception {
		 // Optionally remove existing handlers attached to j.u.l root logger
		 SLF4JBridgeHandler.removeHandlersForRootLogger();  

		 // add SLF4JBridgeHandler to j.u.l's root logger, should be done once during
		 // the initialization phase of your application
		 SLF4JBridgeHandler.install();		
	}

	@Override
	public void stop(BundleContext context) throws Exception {
		// TODO Auto-generated method stub
		
	}

}
