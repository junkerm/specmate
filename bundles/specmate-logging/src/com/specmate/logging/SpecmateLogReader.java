package com.specmate.logging;

import java.util.HashMap;
import java.util.Map;

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
		if (entry.getLevel() > LogService.LOG_DEBUG) {
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
