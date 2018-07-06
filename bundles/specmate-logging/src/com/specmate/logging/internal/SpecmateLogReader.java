package com.specmate.logging.internal;

import java.util.HashMap;
import java.util.Map;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.log.LogEntry;
import org.osgi.service.log.LogListener;
import org.osgi.service.log.LogReaderService;
import org.osgi.service.log.LogService;

import com.specmate.logging.internal.config.SpecmateLogReaderConfig;

@Component(service = LogListener.class, immediate = true, configurationPid = SpecmateLogReaderConfig.PID, configurationPolicy = ConfigurationPolicy.REQUIRE)
public class SpecmateLogReader implements LogListener {

	/** The log level threshold */
	private int logLevel;

	private static Map<Integer, String> level2String = new HashMap<>();

	static {
		level2String.put(LogService.LOG_DEBUG, "DEBUG");
		level2String.put(LogService.LOG_INFO, "INFO");
		level2String.put(LogService.LOG_WARNING, "WARNING");
		level2String.put(LogService.LOG_ERROR, "ERROR");
	}

	private static Map<String, Integer> string2level = new HashMap<>();

	static {
		string2level.put("debug", LogService.LOG_DEBUG);
		string2level.put("info", LogService.LOG_INFO);
		string2level.put("warning", LogService.LOG_WARNING);
		string2level.put("error", LogService.LOG_ERROR);
	}

	/** The log reader service */
	private LogReaderService logReaderService;

	private LogService logService;

	@Activate
	public void activate(Map<String, Object> properties) {
		// if no property is set, use info level
		String confLogLevel = (String) properties.getOrDefault(SpecmateLogReaderConfig.KEY_LOG_LEVEL, "info");

		Integer mappedLevel = getLevelFromString(confLogLevel);

		// the mapped level can be null in case the property is not a valid
		// value
		if (mappedLevel != null) {
			this.logLevel = mappedLevel;
		} else {
			logService.log(LogService.LOG_ERROR, "Unknown log level " + confLogLevel);
			this.logLevel = LogService.LOG_INFO;
		}
		logService.log(LogService.LOG_INFO, "Setting log level to " + level2String.get(this.logLevel));
	}

	private String getStringFromLevel(int level) {
		return level2String.get(level);
	}

	private Integer getLevelFromString(String level) {
		return string2level.get(level.toLowerCase());
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
		if (entry.getLevel() > this.logLevel) {
			return;
		}
		String message = getStringFromLevel(entry.getLevel()) + ":" + entry.getBundle().getSymbolicName() + ":"
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

	@Reference
	public void setLogService(LogService logService) {
		this.logService = logService;
	}

}
