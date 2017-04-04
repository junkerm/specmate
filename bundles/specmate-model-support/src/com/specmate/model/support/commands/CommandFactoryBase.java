package com.specmate.model.support.commands;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.eclipse.emf.ecore.EObject;
import org.osgi.service.log.LogService;

import com.specmate.common.AssertUtil;

public class CommandFactoryBase implements ICommandFactory {

	List<Class<?>> retrieveCommands = new ArrayList<>();
	List<Class<?>> createCommands = new ArrayList<>();
	List<Class<?>> updateCommands = new ArrayList<>();
	List<Class<?>> deleteCommands = new ArrayList<>();
	protected LogService logService;

	protected void registerRetrieveCommand(Class<?> clazz) {
		retrieveCommands.add(clazz);
	}

	protected void registerCreateCommand(Class<?> clazz) {
		createCommands.add(clazz);
	}

	protected void registerUpdateCommand(Class<?> clazz) {
		updateCommands.add(clazz);
	}

	protected void registerDeleteCommand(Class<?> clazz) {
		deleteCommands.add(clazz);
	}

	@Override
	public Optional<CommandBase> getRetrieveCommand(EObject object) {
		return getCommand(retrieveCommands, object);
	}

	@Override
	public Optional<CommandBase> getCreateCommand(Object parent, EObject object, String feature) {
		return getCommand(createCommands, parent, object, feature);
	}

	@Override
	public Optional<CommandBase> getUpdateCommand(EObject oldObject, EObject update) {
		return getCommand(updateCommands, oldObject, update);
	}

	@Override
	public Optional<CommandBase> getDeleteCommand(EObject oldObject) {
		return getCommand(deleteCommands, oldObject);
	}

	public Optional<CommandBase> getCommand(List<Class<?>> commands, Object... objects) {
		for (Class<?> command : commands) {
			Optional<?> optionalCommand = match(command, objects);
			if (optionalCommand.isPresent()) {
				CommandBase commandInstance = (CommandBase) optionalCommand.get();
				if (this.logService != null) {
					commandInstance.setLogService(logService);
				}
				return Optional.of(commandInstance);
			}
		}
		return Optional.empty();
	}

	private Optional<?> match(Class<?> clazz, Object... objects) {
		Constructor<?>[] constructors = clazz.getConstructors();
		AssertUtil.assertTrue(constructors.length == 1);
		Constructor<?> constructor = constructors[0];
		Class<?>[] types = constructor.getParameterTypes();
		if (types.length > objects.length) {
			return Optional.empty();
		}

		// check if constructor arguments match
		for (int i = 0; i < types.length; i++) {
			if (objects[i] != null) {
				if (!types[i].isAssignableFrom(objects[i].getClass())) {
					return Optional.empty();
				}
			}
		}

		try {
			return Optional.of(constructor.newInstance(Arrays.copyOf(objects, types.length)));
		} catch (Exception e) {
			return Optional.empty();
		}
	}

	public void setLogService(LogService logService) {
		this.logService = logService;
	}

}
