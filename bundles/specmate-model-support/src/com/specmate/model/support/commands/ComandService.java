package com.specmate.model.support.commands;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

import org.eclipse.emf.ecore.EObject;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;

@Component(service = ICommandService.class)
public class ComandService implements ICommandService {

	Collection<ICommandFactory> factories = new ArrayList<>();

	@Override
	public Optional<ICommand> getCreateCommand(Object parent, EObject object, String feature) {
		for (ICommandFactory factory : factories) {
			Optional<ICommand> addCommand = factory.getCreateCommand(parent, object, feature);
			if (addCommand.isPresent()) {
				return addCommand;
			}
		}
		return Optional.empty();
	}

	@Override
	public Optional<ICommand> getUpdateCommand(EObject oldObject, EObject update) {
		for (ICommandFactory factory : factories) {
			Optional<ICommand> updateCommand = factory.getUpdateCommand(oldObject, update);
			if (updateCommand.isPresent()) {
				return updateCommand;
			}
		}
		return Optional.empty();
	}

	@Override
	public Optional<ICommand> getDeleteCommand(EObject oldObject) {
		for (ICommandFactory factory : factories) {
			Optional<ICommand> deleteCommand = factory.getDeleteCommand(oldObject);
			if (deleteCommand.isPresent()) {
				return deleteCommand;
			}
		}
		return Optional.empty();
	}

	@Reference(cardinality = ReferenceCardinality.MULTIPLE, policy = ReferencePolicy.DYNAMIC)
	public void addCommandFactory(ICommandFactory factory) {
		this.factories.add(factory);
	}

	public void removeCommandFactory(ICommandFactory factory) {
		this.factories.remove(factory);
	}

}
