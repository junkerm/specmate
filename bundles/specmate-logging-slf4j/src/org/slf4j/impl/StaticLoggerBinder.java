package org.slf4j.impl;

import org.slf4j.ILoggerFactory;

public class StaticLoggerBinder {
    private static StaticLoggerBinder instance = new StaticLoggerBinder();
    
	public static final StaticLoggerBinder getSingleton(){
		return instance;
    }
	
    public ILoggerFactory getLoggerFactory(){
    	return new OsgiLoggerFactory();    	
    }
    
    public String getLoggerFactoryClassStr(){
    	return OsgiLoggerFactory.class.getName();
    }


}
