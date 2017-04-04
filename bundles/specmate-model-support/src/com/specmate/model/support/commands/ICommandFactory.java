package com.specmate.model.support.commands;

import java.util.Optional;

import org.eclipse.emf.ecore.EObject;

public interface ICommandFactory {

	/**
	 * Retrieves a command that retrieves a result object from a given object
	 * 
	 * @return
	 */
	Optional<CommandBase> getRetrieveCommand(EObject addedObject);

	/**
	 * Retrieves a command that creates a new object from
	 * <code>addedObject</code> with
	 * <code>parent<code> as parent with regard to the containment feature <code>feature</code>
	 * 
	 * @return
	 */
	Optional<CommandBase> getCreateCommand(Object parent, EObject addedObject, String feature);

	/**
	 * Retrieves a command that updates the attributes of the instance
	 * <code>instance</code> with the values from <code>update</code>.
	 * 
	 * @return
	 */
	Optional<CommandBase> getUpdateCommand(EObject instance, EObject update);

	/**
	 * Retrieves a command that deletes <code>instance</code>.
	 * 
	 * @param parent
	 * @param addedObject
	 * @param feature
	 * @return
	 */
	Optional<CommandBase> getDeleteCommand(EObject instance);
}
