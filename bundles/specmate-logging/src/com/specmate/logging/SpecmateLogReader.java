package com.specmate.logging;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
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

@Component(service = LogListener.class, immediate = true)
public class SpecmateLogReader implements LogListener {

	private static Map<Integer, String> level2String = new HashMap<>();

	static {
		level2String.put(LogService.LOG_DEBUG, "DEBUG");
		level2String.put(LogService.LOG_INFO, "INFO");
		level2String.put(LogService.LOG_WARNING, "WARNING");
		level2String.put(LogService.LOG_ERROR, "ERROR");

	}

	private LogReaderService logReaderService;

	@Activate
	public void activate() {
		configureLogging();
	}

	private void configureLogging() {
		try {
			InputStream stream = FrameworkUtil.getBundle(SpecmateLogReader.class).getEntry("logging.properties")
					.openStream();
			LogManager logManager = LogManager.getLogManager();
			logManager.readConfiguration(stream);
		} catch (IOException e) {
			System.err.println("Logging configuration not found. Using default.");
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

	public void unsetLogReader(LogReaderService logReaderService) {
		logReaderService.removeLogListener(this);
	}

	@Override
	public void logged(LogEntry entry) {
		if (entry.getLevel() > LogService.LOG_WARNING) {
			return;
		}
		String message = level2String.get(entry.getLevel()) + ":" + entry.getBundle().getSymbolicName() + ":"
				+ entry.getMessage();
		if (entry.getLevel() <= LogService.LOG_WARNING) {
			System.err.println(message);
			if (entry.getException() != null) {
				entry.getException().printStackTrace();
			}
		} else {
			System.out.println(message);
		}

	}

}
