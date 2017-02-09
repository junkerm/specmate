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

import com.specmate.model.support.commands.ICommandService;
import com.specmate.model.support.urihandler.IObjectResolver;
import com.specmate.model.support.urihandler.IURIFactory;
import com.specmate.persistency.IPersistencyService;
import com.specmate.persistency.ITransaction;

@Component(immediate = true)
public class EmfRestServletDeployer {

	private LogService logService;
	private HttpService httpService;
	private ServletContainer container;
	private IObjectResolver resolver;
	private IURIFactory uriFactory;
	private ICommandService commandService;
	private IPersistencyService persistencyService;

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
				bind(context).to(BundleContext.class);
				bind(commandService).to(ICommandService.class);
				bindFactory(new TransactionFactory(persistencyService)).to(ITransaction.class).in(PerThread.class).proxy(true);
				
			}
		});
		container = new ServletContainer(application);
		try {
			httpService.registerServlet("/services", container, null, null);
		} catch (Exception e) {
			logService.log(LogService.LOG_ERROR,
					"Deploying EMF-Rest Servlet failed", e);
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
		this.persistencyService=persistency;
	}
	
	
	@Reference
	public void setObjectResolver(IObjectResolver resolver){
		this.resolver=resolver;
	}
	
	@Reference
	public void setUriFactory(IURIFactory uriFactory){
		this.uriFactory=uriFactory;
	}
	
	@Reference
	public void setCommandService(ICommandService commandService){
		this.commandService = commandService;
	}

}
