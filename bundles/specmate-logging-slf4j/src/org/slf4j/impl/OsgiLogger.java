package org.slf4j.impl;

import org.osgi.framework.FrameworkUtil;
import org.osgi.service.log.LogService;
import org.osgi.util.tracker.ServiceTracker;
import org.slf4j.helpers.MarkerIgnoringBase;
import org.slf4j.helpers.MessageFormatter;

public class OsgiLogger extends MarkerIgnoringBase {

	ServiceTracker<LogService, LogService> tracker;
	
	
	public OsgiLogger() {
		tracker = new ServiceTracker<LogService, LogService>(
				FrameworkUtil.getBundle(OsgiLogger.class).getBundleContext(),
				LogService.class.getName(), null);
		tracker.open();
	}
	
	private void log(int logLevel, String msg, Throwable exception){
		if(!tracker.isEmpty()){
			tracker.getService().log(logLevel,msg, exception);
		}
	}
	
	@Override
	public boolean isTraceEnabled() {
		return true; 
	}

	@Override
	public void trace(String msg) {
		log(LogService.LOG_DEBUG,msg,null);
	}

	@Override
	public void trace(String format, Object arg) {
		trace(MessageFormatter.format(format,arg).getMessage());		
	}

	@Override
	public void trace(String format, Object arg1, Object arg2) {
		trace(MessageFormatter.format(format,arg1,arg2).getMessage());			
	}

	@Override
	public void trace(String format, Object... arguments) {
		trace(MessageFormatter.format(format,arguments).getMessage());	
	}

	@Override
	public void trace(String msg, Throwable t) {
		log(LogService.LOG_DEBUG,msg,t);
	}

	@Override
	public boolean isDebugEnabled() {
		return true;
	}

	@Override
	public void debug(String msg) {
		log(LogService.LOG_DEBUG,msg,null);
		
	}

	@Override
	public void debug(String format, Object arg) {
		debug(MessageFormatter.format(format,arg).getMessage());
	}

	@Override
	public void debug(String format, Object arg1, Object arg2) {
		debug(MessageFormatter.format(format,arg1,arg2).getMessage());			
		
	}

	@Override
	public void debug(String format, Object... arguments) {
		debug(MessageFormatter.format(format,arguments).getMessage());	
	}

	@Override
	public void debug(String msg, Throwable t) {
		log(LogService.LOG_DEBUG,msg,t);
	}

	@Override
	public boolean isInfoEnabled() {
		return true;
	}

	@Override
	public void info(String msg) {
		log(LogService.LOG_INFO,msg,null);
	}

	@Override
	public void info(String format, Object arg) {
		info(MessageFormatter.format(format,arg).getMessage());
	}

	@Override
	public void info(String format, Object arg1, Object arg2) {
		info(MessageFormatter.format(format,arg1,arg2).getMessage());
	}

	@Override
	public void info(String format, Object... arguments) {
		info(MessageFormatter.format(format,arguments).getMessage());	
	}

	@Override
	public void info(String msg, Throwable t) {
		log(LogService.LOG_DEBUG,msg,t);
	}

	@Override
	public boolean isWarnEnabled() {
		return true;
	}

	@Override
	public void warn(String msg) {
		log(LogService.LOG_WARNING,msg,null);
	}

	@Override
	public void warn(String format, Object arg) {
		warn(MessageFormatter.format(format,arg).getMessage());
	}

	@Override
	public void warn(String format, Object arg1, Object arg2) {
		warn(MessageFormatter.format(format,arg1,arg2).getMessage());
	}

	@Override
	public void warn(String format, Object... arguments) {
		warn(MessageFormatter.format(format,arguments).getMessage());	
	}

	@Override
	public void warn(String msg, Throwable t) {
		log(LogService.LOG_WARNING,msg,t);
	}	

	@Override
	public boolean isErrorEnabled() {
		return true;
	}

	@Override
	public void error(String msg) {
		log(LogService.LOG_ERROR,msg,null);
	}

	@Override
	public void error(String format, Object arg) {
		error(MessageFormatter.format(format,arg).getMessage());
	}

	@Override
	public void error(String format, Object arg1, Object arg2) {
		error(MessageFormatter.format(format,arg1,arg2).getMessage());
	}

	@Override
	public void error(String format, Object... arguments) {
		error(MessageFormatter.format(format,arguments).getMessage());	
	}

	@Override
	public void error(String msg, Throwable t) {
		log(LogService.LOG_ERROR,msg,t);
	}


}
