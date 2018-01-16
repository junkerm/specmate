package com.specmate.search.api;

import java.util.List;

import org.eclipse.emf.ecore.EObject;

import com.specmate.common.SpecmateException;

public interface ISearchService {

	List<EObject> search(String queryString) throws SpecmateException;

}
