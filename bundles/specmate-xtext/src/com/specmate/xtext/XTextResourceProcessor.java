package com.specmate.xtext;

import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.ISetup;

public abstract class XTextResourceProcessor<T> {
	public abstract ISetup getGrammar();
	public abstract List<T> process(EList<EObject> data) throws XTextException;
	
	public List<T> loadXTextResources(URI mainFile, URI... paths) throws XTextException{
		return this.process(XTextUtil.loadXTextResources(this.getGrammar(), mainFile, paths));
	}
	
	public List<T> parseXTextResource(String data) throws XTextException{
		return this.process(XTextUtil.parseXTextResource(this.getGrammar(), data));
	}
}
