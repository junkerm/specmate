package com.specmate.config.internal;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.log.LogService;

import com.specmate.config.api.IConfigService;

/** Service for providing configurations for other services. */
@Component(immediate = true)
public class ConfigService implements IConfigService {

	/** Command line switch to specify the configuration file. */
	private static final String CONFIGURATION_FILE = "configurationFile";

	/** Key for the command line arguments (bnd-specific) */
	private static final String LAUNCHER_ARGUMENTS = "launcher.arguments";

	/** The command line arguments */
	private String[] commandLineArguments;

	private Properties configuration = new Properties();

	private LogService logService;

	@Activate
	public void activate() throws IOException {
		processCommandLineArguments();
		readConfigurationFile();
	}

	private void processCommandLineArguments() {
		String lastSwitch = null;
		for (int i = 0; i < commandLineArguments.length; i++) {
			String arg = commandLineArguments[i];
			if (arg.startsWith("--")) {
				lastSwitch = arg.substring(2);
				configuration.setProperty(lastSwitch, "");
			} else if (lastSwitch != null) {
				configuration.setProperty(lastSwitch, arg);
				lastSwitch = null;
			}
		}
	}

	private void readConfigurationFile() {
		String configurationFileLocation = configuration.getProperty(CONFIGURATION_FILE);
		if (!StringUtils.isEmpty(configurationFileLocation)) {
			File file = new File(configurationFileLocation);
			Properties properties = new Properties();
			try {
				properties.load(new FileInputStream(file));
			} catch (FileNotFoundException e) {
				this.logService.log(LogService.LOG_ERROR, "Configuration file " + configurationFileLocation
						+ " does not exist. Using default configuration.");
				return;
			} catch (IOException e) {
				this.logService.log(LogService.LOG_ERROR, "Configuration file " + configurationFileLocation
						+ " could not be read. Using default configuration.", e);
				return;
			}
			properties.putAll(configuration);
			this.configuration = properties;
		}
	}

	@Override
	public String getConfigurationProperty(String key) {
		return this.configuration.getProperty(key);
	}

	@Override
	public String getConfigurationProperty(String key, String defaultValue) {
		return this.configuration.getProperty(key, defaultValue);
	}

	/**
	 * The bnd launcher provides access to the command line arguments via the
	 * Launcher object. This object is registered under Object.
	 */
	@Reference
	public void setBndDoneObject(Object done, Map<String, Object> parameters) {
		this.commandLineArguments = (String[]) parameters.get(LAUNCHER_ARGUMENTS);
	}

	@Reference
	public void setLogService(LogService logService) {
		this.logService = logService;
	}
}
