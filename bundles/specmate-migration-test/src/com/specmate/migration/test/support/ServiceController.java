package com.specmate.migration.test.support;

import java.util.Dictionary;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;

import com.specmate.common.SpecmateException;

public class ServiceController<T> {
	private ServiceRegistration sr;
	private BundleContext bc;
	private Class<T> serviceImplementation;
	
	public ServiceController(BundleContext context) {
		this.bc = context;
	}
	
	public void register(Class<?> serviceInterface, Class<T> serviceImplementation, 
			Dictionary<String, Object> serviceProperties) throws SpecmateException {
		this.serviceImplementation = serviceImplementation;
		try {
			Object obj = serviceImplementation.newInstance();
			sr = bc.registerService(serviceInterface.getName(), obj, serviceProperties);
		} catch (SecurityException | IllegalAccessException | InstantiationException e) {
			throw new SpecmateException("Could not register service " + serviceImplementation.getName());
		}
	}
	
	public void modify(Dictionary<String, Object> serviceProperties) {
		sr.setProperties(serviceProperties);
	}
	
	public void unregister() {
		sr.unregister(); 
	}
	
	public ServiceReference getServiceReference() {
		return sr.getReference();
	}
	
	public T getService() {
		Object obj =  bc.getService(sr.getReference());
		return serviceImplementation.cast(obj);
	}
	
	
}
