package com.specmate.emfrest.crud;

import java.util.Iterator;
import java.util.List;

import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.osgi.service.component.annotations.Component;

import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;
import com.specmate.common.RestResult;
import com.specmate.common.SpecmateException;
import com.specmate.emfrest.api.IRestService;
import com.specmate.emfrest.api.RestServiceBase;

@Component(immediate = true, service = IRestService.class)
public class ListRecursiveService extends RestServiceBase {
	@Override
	public boolean canGet(Object target) {
		return (target instanceof EObject);
	}

	@Override
	public RestResult<?> get(Object target, MultivaluedMap<String, String> queryParams, String token)
			throws SpecmateException {
		if (queryParams.containsKey("class")) {
			return new RestResult<>(Response.Status.OK, getChildren(target, queryParams.getFirst("class")));
		} else {
			return new RestResult<>(Response.Status.OK, getChildren(target, null));
		}
	}

	private List<EObject> getChildren(Object target, String className) throws SpecmateException {
		if (target instanceof EObject) {
			TreeIterator<EObject> contents = ((EObject) target).eAllContents();
			Iterator<EObject> filtered;
			if (className != null) {
				filtered = Iterators.filter(contents, o -> o.eClass().getName().equals(className));
			} else {
				filtered = contents;
			}
			return Lists.newArrayList(filtered);
		} else {
			throw new SpecmateException("Object is no resource and no EObject");
		}

	}

	@Override
	public String getServiceName() {
		return "listRecursive";
	}
}
