package com.specmate.emfrest.internal.rest;

import java.util.Optional;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.container.ResourceContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.osgi.service.log.LogService;

import com.specmate.common.AssertUtil;
import com.specmate.common.SpecmateException;
import com.specmate.emfrest.internal.util.EmfRestUtil;
import com.specmate.model.support.commands.ICommand;
import com.specmate.model.support.commands.ICommandService;
import com.specmate.model.support.util.SpecmateEcoreUtil;
import com.specmate.persistency.ITransaction;

/**
 * Resource representing a single EMF structural feature (reference of
 * containment)
 */
public class SingleFeatureResource {

	/** Context of this resource */
	@Context
	ResourceContext resourceContext;

	/** Transaction to retreive or save changes to the object graph */
	@Inject
	ITransaction transaction;

	/** Commmand service to obtain command for creating, updating and deleting objects from the repository */
	@Inject
	ICommandService commandService;
	
	/** The OSGi logging service */
	@Inject
	LogService logService;

	/** The feature that this resource relates to */
	private EReference reference;

	/** The intance object owning the feature */
	private EObject instance;

	/** Getter for the feature */
	public EReference getFeature() {
		return reference;
	}

	/** Setter for the feature */
	public void setFeature(EReference reference) {
		this.reference = reference;
	}

	/** Getter for the instance */
	public EObject getInstance() {
		return instance;
	}

	/** Setter for the instance */
	public void setInstance(EObject instance) {
		this.instance = instance;
	}

	/**
	 * Navigates along the feature if the given id matches the id of the object
	 * stored in the feature
	 */
	@Path("/{id}")
	public InstanceResource getObject(@PathParam("id") String id) {
		EObject retrieved = retrieveObject();
		if (retrieved != null && SpecmateEcoreUtil.getID(retrieved).equals(id)) {
			InstanceResource resource = resourceContext
					.getResource(InstanceResource.class);
			resource.setModelInstance(retrieved);
			return resource;
		} else {
			throw EmfRestUtil.throwNotFound("Resource does not exist");
		}
	}

	/** Returns the object behind the feature wrapped in a list */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public EList<EObject> getContent() {
		EObject retrieved = retrieveObject();
		BasicEList<EObject> list = new BasicEList<EObject>();
		if (retrieved != null) {
			list.add(retrieved);
		}
		return list;
	}

	/** Adds an object to a feature */
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public void setObject(EObject postedObject) {
		if (instance.eClass().getEAllStructuralFeatures().contains(reference)) {
			Optional<ICommand> command = null;
			if(postedObject.eContainer()==null){
				// not attached yes, hence it need to be created
				command=commandService.getCreateCommand(instance, postedObject, reference.getName());
			} else {
				EmfRestUtil.throwBadRequest("Attempt to create object that is already attached.");
			}
			if (command.isPresent()) {
				try {
					command.get().execute();
				} catch (SpecmateException e1) {
					EmfRestUtil.throwBadRequest(e1.getMessage());
				}
				try {
					transaction.commit();
				} catch (SpecmateException e) {
					logService.log(LogService.LOG_ERROR, "Commit failed");
				}
			} else {
				EmfRestUtil.throwMethodNotAllowed();
			}
			
		} else {
			EmfRestUtil.throwNotFound("Resource does not exist");
		}
	}
	
	/** Retrieves the object represented by this resource */
	private EObject retrieveObject() {
		AssertUtil.preTrue(!reference.isMany());
		if (instance.eClass().getEAllStructuralFeatures().contains(reference)) {
			if (instance.eIsSet(reference)) {
				return (EObject) instance.eGet(reference);
			} else {
				return null;
			}
		} else {
			throw EmfRestUtil.throwNotFound("Resource does not exist");
		}
	}
}
