package com.specmate.model.support.urihandler;

import org.eclipse.emf.ecore.EObject;

import com.specmate.common.SpecmateException;

public interface IURIFactory {
	public String getURI(EObject object) throws SpecmateException;
}
