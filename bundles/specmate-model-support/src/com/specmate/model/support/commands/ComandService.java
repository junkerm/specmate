package com.specmate.model.support.commands;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import java.util.function.Function;

import org.eclipse.emf.ecore.EObject;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;

@Component(service = ICommandService.class)
public class ComandService implements ICommandService {

	Collection<ICommandFactory> factories = new ArrayList<>();

	public Optional<CommandBase> getCommandFromFactory(Function<ICommandFactory, Optional<CommandBase>> commandFunction) {
		return factories.stream().map(commandFunction).filter(Optional::isPresent).map(Optional::get)
				.sorted((c1, c2) -> Integer.compare(c1.getPriority(), c2.getPriority())).findFirst();
	}

	@Override
	public Optional<CommandBase> getRetrieveCommand(EObject object) {
		Optional<CommandBase> commandFromFactory = getCommandFromFactory(f -> f.getRetrieveCommand(object));
		return commandFromFactory;
	}

	@Override
	public Optional<CommandBase> getCreateCommand(Object parent, EObject object, String feature) {
		return getCommandFromFactory(f -> f.getCreateCommand(parent, object, feature));
	}

	@Override
	public Optional<CommandBase> getUpdateCommand(EObject oldObject, EObject update) {
		return getCommandFromFactory(f -> f.getUpdateCommand(oldObject, update));
	}

	@Override
	public Optional<CommandBase> getDeleteCommand(EObject oldObject) {
		return getCommandFromFactory(f -> f.getDeleteCommand(oldObject));
	}

	@Reference(cardinality = ReferenceCardinality.MULTIPLE, policy = ReferencePolicy.DYNAMIC)
	public void addCommandFactory(ICommandFactory factory) {
		this.factories.add(factory);
	}

	public void removeCommandFactory(ICommandFactory factory) {
		this.factories.remove(factory);
	}

}
