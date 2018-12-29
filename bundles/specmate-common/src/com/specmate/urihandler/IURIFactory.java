package com.specmate.urihandler;

import org.eclipse.emf.ecore.EObject;

import com.specmate.common.exception.SpecmateException;

public interface IURIFactory {
	public String getURI(EObject object) throws SpecmateException;
}
