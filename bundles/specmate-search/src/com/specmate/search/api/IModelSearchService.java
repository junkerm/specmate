package com.specmate.search.api;

import java.util.List;

import org.eclipse.emf.ecore.EObject;

import com.specmate.common.SpecmateException;

public interface IModelSearchService {

	/**
	 * Searches for model objects.
	 * 
	 * @param queryParams
	 *            A mapping from keys to values. For each key, a search is
	 *            constructed in the following way: [field with name key]
	 *            matches value1 or value2 or ...
	 * @return List of model objects that match to the query
	 * @throws SpecmateException
	 */
	List<EObject> search(String query) throws SpecmateException;

}
