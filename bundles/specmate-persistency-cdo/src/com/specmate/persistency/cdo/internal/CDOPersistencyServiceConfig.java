package com.specmate.persistency.cdo.internal;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.net4j.util.StringUtil;
import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.log.LogService;

import com.specmate.common.OSGiUtil;
import com.specmate.common.SpecmateException;
import com.specmate.config.api.IConfigService;

@Component(immediate = true)
public class CDOPersistencyServiceConfig {

	public static final String PID = "com.specmate.persistency.cdo.internal.CDOPersistencyService";
	public static final String KEY_REPOSITORY_NAME = "cdo.repositoryName";
	public static final String KEY_RESOURCE_NAME = "cdo.resourceName";
	public static final String KEY_CDO_USER = "cdo.user";
	public static final String KEY_CDO_PASSWORD = "cdo.password";
	public static final String KEY_SERVER_HOST_PORT = "cdo.serverHostAndPort";
	public static final String KEY_RECOVERY_FOLDER = "cdo.recoveryFolder";
	private ConfigurationAdmin configurationAdmin;
	private IConfigService configService;
	private LogService logService;
	private String specmateRepository;
	private String specmateResource;
	private String host;
	private ScheduledExecutorService checkConnectionEexcutor;
	private boolean connected;
	private String hostName;
	private int port;
	private Configuration configuration;
	private String cdoUser;
	private String cdoPassword;
	private String recoveryFolder;

	/**
	 * Configures the CDO persistency service.
	 *
	 * @throws SpecmateException
	 */
	@Activate
	private void activate() throws SpecmateException {
		this.specmateRepository = configService.getConfigurationProperty(KEY_REPOSITORY_NAME);
		this.specmateResource = configService.getConfigurationProperty(KEY_RESOURCE_NAME);
		this.cdoUser = configService.getConfigurationProperty(KEY_CDO_USER);
		this.cdoPassword = configService.getConfigurationProperty(KEY_CDO_PASSWORD);
		this.host = configService.getConfigurationProperty(KEY_SERVER_HOST_PORT);
		this.recoveryFolder = configService.getConfigurationProperty(KEY_RECOVERY_FOLDER);
		this.connected = false;
		String[] hostport = StringUtils.split(this.host, ":");
		if (!(hostport.length == 2)) {
			throw new SpecmateException(
					"Invalid format for CDO host: " + this.host + ". The expected format is [hostName]:[port]");
		}
		this.hostName = hostport[0];
		this.port = Integer.parseInt(hostport[1]);
		startMonitoringThread();
	}

	@Deactivate
	private void deactivate() {
		this.checkConnectionEexcutor.shutdown();
	}

	/**
	 * Starts a thread that periodically checks if the CDO server is still reachable
	 */
	private void startMonitoringThread() {

		this.checkConnectionEexcutor = Executors.newScheduledThreadPool(1);
		checkConnectionEexcutor.scheduleWithFixedDelay(() -> {

			if (this.connected) {
				if (!checkConnection()) {
					try {
						removeConfiguration();
						this.connected = false;
						logService.log(LogService.LOG_WARNING, "Lost connection to CDO server.");
					} catch (SpecmateException e) {
						logService.log(LogService.LOG_ERROR, "Could not stop persistency.");
					}
				}
			} else {
				if (checkConnection()) {
					try {
						logService.log(LogService.LOG_INFO, "Connection to CDO server established.");
						registerConfiguration();
						this.connected = true;
					} catch (SpecmateException e) {
						logService.log(LogService.LOG_ERROR, "Could not restart persistency.");
					}
				}
			}

		}, 5, 5, TimeUnit.SECONDS);

	}

	private void registerConfiguration() throws SpecmateException {
		Dictionary<String, Object> properties = new Hashtable<>();
		if (!StringUtil.isEmpty(specmateRepository) && !StringUtil.isEmpty(specmateResource)
				&& !StringUtil.isEmpty(host) && !StringUtil.isEmpty(cdoUser) && !StringUtil.isEmpty(cdoPassword)) {
			properties.put(KEY_REPOSITORY_NAME, specmateRepository);
			properties.put(KEY_RESOURCE_NAME, specmateResource);
			properties.put(KEY_SERVER_HOST_PORT, host);
			properties.put(KEY_CDO_USER, cdoUser);
			properties.put(KEY_CDO_PASSWORD, cdoPassword);
			OSGiUtil.saveAddToProperties(properties, KEY_RECOVERY_FOLDER, this.recoveryFolder);
			logService.log(LogService.LOG_DEBUG,
					"Configuring CDO with:\n" + OSGiUtil.configDictionaryToString(properties));
			this.configuration = OSGiUtil.configureService(configurationAdmin, PID, properties);
		}
	}

	private void removeConfiguration() throws SpecmateException {
		try {
			configuration.delete();
		} catch (IOException e) {
			throw new SpecmateException("Could not delete configuration.");
		}
	}

	/** Checks if the CDO sever is reachable */
	private boolean checkConnection() {
		try (Socket socket = new Socket()) {
			socket.connect(new InetSocketAddress(this.hostName, this.port), 5000);
			socket.close();
			return true;
		} catch (IOException e) {
			return false; // Either timeout or unreachable or failed DNS lookup.
		}
	}

	/** Service reference for config admin */
	@Reference
	public void setConfigurationAdmin(ConfigurationAdmin configurationAdmin) {
		this.configurationAdmin = configurationAdmin;
	}

	/** Service reference for config service */
	@Reference
	public void setConfigurationService(IConfigService configService) {
		this.configService = configService;
	}

	@Reference
	public void setLogService(LogService logService) {
		this.logService = logService;
	}
}
