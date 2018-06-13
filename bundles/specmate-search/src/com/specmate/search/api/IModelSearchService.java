package com.specmate.search.api;

import java.util.Set;

import org.eclipse.emf.ecore.EObject;

import com.specmate.common.SpecmateException;

public interface IModelSearchService {

	/**
	 * Searches for model objects.
	 * @param project TODO
	 * @param queryParams
	 *            A mapping from keys to values. For each key, a search is
	 *            constructed in the following way: [field with name key]
	 *            matches value1 or value2 or ...
	 * 
	 * @return List of model objects that match to the query
	 * @throws SpecmateException
	 */
	Set<EObject> search(String query, String project) throws SpecmateException;

	void clear() throws SpecmateException;

}
