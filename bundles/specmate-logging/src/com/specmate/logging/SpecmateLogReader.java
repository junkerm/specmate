package com.specmate.logging;

import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.LogManager;

import org.osgi.framework.FrameworkUtil;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.log.LogEntry;
import org.osgi.service.log.LogListener;
import org.osgi.service.log.LogReaderService;
import org.osgi.service.log.LogService;

@Component(service=LogListener.class,immediate=true)
public class SpecmateLogReader implements LogListener {
	private LogReaderService logReaderService;

	@Activate
	public void activate() {
		configureLogging();
	}

	private void configureLogging() {
		try {
			InputStream stream = FrameworkUtil
					.getBundle(SpecmateLogReader.class)
					.getEntry("logging.properties").openStream();
			LogManager logManager = LogManager.getLogManager();
			logManager.readConfiguration(stream);
		} catch (IOException e) {
			System.err
					.println("Logging configuration not found. Using default.");
	}

	}

	@Deactivate
	public void deactivate() {
		logReaderService.removeLogListener(this);
	}

	@Reference
	public void setLogReader(LogReaderService logReaderService) {
		this.logReaderService = logReaderService;
		logReaderService.addLogListener(this);
	}
	
	public void unsetLogReader(LogReaderService logReaderService){
		logReaderService.removeLogListener(this);
	}

	@Override
	public void logged(LogEntry entry) {
		if(entry.getLevel()>LogService.LOG_DEBUG){
			return;
		}
		System.out.println("LOG:" + entry.getBundle().getSymbolicName() + ":" +entry.getMessage());
		if(entry.getException()!=null){
			entry.getException().printStackTrace();
		}

		
	}

	private Level osgiLevel2JavaLevel(int level) {
		switch (level) {
		case LogService.LOG_DEBUG:
			return Level.FINEST;
		case LogService.LOG_INFO:
			return Level.FINE;
		case LogService.LOG_WARNING:
			return Level.WARNING;
		case LogService.LOG_ERROR:
			return Level.SEVERE;
		default:
			return Level.FINEST;
		}
	}

}
