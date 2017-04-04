package com.specmate.model.support.commands;

import org.osgi.service.component.annotations.Component;

@Component(service = ICommandFactory.class, immediate = true)
public class CommonCommandFactory extends CommandFactoryBase {
	public CommonCommandFactory() {
		registerRetrieveCommand(GenericRetrieveCommand.class);
		registerCreateCommand(GenericCreateCommand.class);
		registerUpdateCommand(GenericUpdateCommand.class);
		registerDeleteCommand(GenericDeleteCommand.class);
	}
}
