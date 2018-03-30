package com.specmate.emfrest.internal;

import org.glassfish.hk2.api.PerThread;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.servlet.ServletContainer;
import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.http.HttpService;
import org.osgi.service.log.LogService;

import com.specmate.administration.api.IStatusService;
import com.specmate.auth.api.IAuthentificationService;
import com.specmate.common.ISerializationConfiguration;
import com.specmate.persistency.IPersistencyService;
import com.specmate.persistency.ITransaction;
import com.specmate.urihandler.IObjectResolver;
import com.specmate.urihandler.IURIFactory;

@Component(immediate = true)
public class EmfRestServletDeployer {

	private LogService logService;
	private HttpService httpService;
	private ServletContainer container;
	private IObjectResolver resolver;
	private IURIFactory uriFactory;
	private RestServiceProvider restServiceProvider;
	private IPersistencyService persistencyService;
	private IAuthentificationService authentificationService;
	private ISerializationConfiguration serializationConfiguration;
	private IStatusService statusService;

	@Activate
	public void activate(BundleContext context) {
		logService.log(LogService.LOG_INFO, "Deploying EMF-Rest Jersey Servlet");
		EmfRestJerseyApplication application = new EmfRestJerseyApplication();
		application.register(new AbstractBinder() {

			@Override
			protected void configure() {
				bind(logService).to(LogService.class);
				bind(resolver).to(IObjectResolver.class);
				bind(uriFactory).to(IURIFactory.class);
				bind(serializationConfiguration).to(ISerializationConfiguration.class);
				bind(context).to(BundleContext.class);
				bind(restServiceProvider).to(RestServiceProvider.class);
				bind(authentificationService).to(IAuthentificationService.class);
				bind(statusService).to(IStatusService.class);
				bindFactory(new TransactionFactory(persistencyService)).to(ITransaction.class).in(PerThread.class)
						.proxy(true);

			}
		});
		container = new ServletContainer(application);
		try {
			httpService.registerServlet("/services", container, null, null);
		} catch (Exception e) {
			logService.log(LogService.LOG_ERROR, "Deploying EMF-Rest Servlet failed", e);
		}
		logService.log(LogService.LOG_INFO, "EMF-Rest Jersey Servlet successfully deployed");
	}

	@Deactivate
	public void deactivate() {
		container.destroy();
	}

	@Reference
	public void setLogService(LogService logService) {
		this.logService = logService;
	}

	@Reference
	public void setHttpService(HttpService service) {
		this.httpService = service;
	}

	@Reference
	public void setPersistency(IPersistencyService persistency) {
		this.persistencyService = persistency;
	}

	@Reference
	public void setObjectResolver(IObjectResolver resolver) {
		this.resolver = resolver;
	}

	@Reference
	public void setUriFactory(IURIFactory uriFactory) {
		this.uriFactory = uriFactory;
	}

	@Reference
	public void setSerializationConfiguration(ISerializationConfiguration serializationConfiguration) {
		this.serializationConfiguration = serializationConfiguration;
	}

	@Reference
	public void setRestServiceProvicer(RestServiceProvider restServiceProvider) {
		this.restServiceProvider = restServiceProvider;
	}

	@Reference
	public void setAuthentificationService(IAuthentificationService authentificationService) {
		this.authentificationService = authentificationService;
	}

	@Reference
	public void setStatusService(IStatusService statusService) {
		this.statusService = statusService;
	}
}
