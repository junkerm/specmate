package com.specmate.connectors.hpconnector;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.log.LogService;

import com.specmate.model.support.commands.CommandFactoryBase;
import com.specmate.model.support.commands.ICommandFactory;

@Component(service = ICommandFactory.class, immediate = true)
public class HPCommandFactory extends CommandFactoryBase {

	public HPCommandFactory() {
		registerRetrieveCommand(HPConnectorRetrieveCommand.class);
	}

	@Reference
	public void setLogService(LogService logService) {
		super.setLogService(logService);
	}
}
