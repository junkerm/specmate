package com.specmate.emfrest.internal.rest;

import java.util.List;
import java.util.Optional;

import javax.inject.Inject;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.osgi.service.log.LogService;

import com.specmate.common.SpecmateException;
import com.specmate.emfrest.internal.util.EmfRestUtil;
import com.specmate.model.support.commands.ICommand;
import com.specmate.model.support.commands.ICommandService;
import com.specmate.persistency.ITransaction;

/** Resource for a many-valued feature */
public class ManyFeatureResource extends ListResource {

	/** The transaction to retrieve objects manipulate the model repository */
	@Inject
	ITransaction transaction;

	/** The OSGi logging service */
	@Inject
	LogService logService;

	/**
	 * Commmand service to obtain command for creating, updating and deleting
	 * objects from the repository
	 */
	@Inject
	ICommandService commandService;

	/** The feature that this resource relates to */
	private EReference reference;

	/** The instance owning the feature */
	private EObject instance;

	/** Retrieves the feature that this resource relates to */
	public EReference getReference() {
		return reference;
	}

	/** Sets the feature that this resource relates to */
	public void setReference(EReference reference) {
		this.reference = reference;
	}

	/** Retrieves th parent object that owns the feature */
	public EObject getOwner() {
		return instance;
	}

	/** Sets the parent object that owns the feature */
	public void setOwner(EObject owner) {
		this.instance = owner;
	}

	/** {@inheritDoc} */
	@Override
	protected List<EObject> getList() {
		return (List<EObject>) instance.eGet(reference);
	}

	/** {@inheritDoc} */
	@Override
	protected void addToList(EObject addedObject) {
		if (instance.eClass().getEAllStructuralFeatures().contains(reference)) {
			Optional<ICommand> command;
			command = commandService.getCreateCommand(instance, addedObject, reference.getName());

			if (command.isPresent()) {
				try {
					command.get().execute();
				} catch (SpecmateException e1) {
					EmfRestUtil.throwBadRequest(e1.getMessage());
				}
				try {
					transaction.commit();
				} catch (SpecmateException e) {
					logService.log(LogService.LOG_ERROR, "Commit failed",e);
				}
			} else {
				EmfRestUtil.throwMethodNotAllowed();
			}

		} else {
			EmfRestUtil.throwNotFound("Resource does not exist");
		}
	}


}
