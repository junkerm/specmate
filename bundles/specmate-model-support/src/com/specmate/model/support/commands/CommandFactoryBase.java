package com.specmate.model.support.commands;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.eclipse.emf.ecore.EObject;

import com.specmate.common.AssertUtil;

public class CommandFactoryBase implements ICommandFactory {

	List<Class<?>> createCommands = new ArrayList<>();
	List<Class<?>> updateCommands = new ArrayList<>();
	List<Class<?>> addCommands = new ArrayList<>();
	List<Class<?>> setCommands = new ArrayList<>();
	List<Class<?>> removeCommands = new ArrayList<>();
	List<Class<?>> unsetCommands = new ArrayList<>();
	List<Class<?>> deleteCommands = new ArrayList<>();

	protected void registerCreateCommand(Class<?> clazz) {
		createCommands.add(clazz);
	}

	protected void registerUpdateCommand(Class<?> clazz) {
		updateCommands.add(clazz);
	}

	protected void registerAddCommand(Class<?> clazz) {
		addCommands.add(clazz);
	}

	protected void registerSetCommand(Class<?> clazz) {
		setCommands.add(clazz);
	}

	protected void registerRemoveCommand(Class<?> clazz) {
		removeCommands.add(clazz);
	}

	protected void registerDeleteCommand(Class<?> clazz) {
		deleteCommands.add(clazz);
	}
	
	protected void registerUnsetCommand(Class<?> clazz) {
		unsetCommands.add(clazz);
	}

	@Override
	public Optional<ICommand> getCreateCommand(Object parent, EObject object, String feature) {
		return getCommand(createCommands, parent, object,  feature);
	}

	@Override
	public Optional<ICommand> getUpdateCommand(EObject oldObject, EObject update) {
		return getCommand(updateCommands, oldObject, update);
	}

	@Override
	public Optional<ICommand> getDeleteCommand(EObject oldObject) {
		return getCommand(deleteCommands, oldObject);
	}
	
	public Optional<ICommand> getCommand(List<Class<?>> commands, Object... objects) {
		for (Class<?> command : commands) {
			Optional<?> optionalCommand = match(command, objects);
			if (optionalCommand.isPresent()) {
				return Optional.of((ICommand) optionalCommand.get());
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
			if(objects[i]!=null){
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


}
