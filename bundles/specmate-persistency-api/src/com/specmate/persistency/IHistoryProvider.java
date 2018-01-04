package com.specmate.persistency;

import org.eclipse.emf.ecore.EObject;

import com.specmate.common.SpecmateException;
import com.specmate.model.history.History;

public interface IHistoryProvider {
	History getHistory(EObject object) throws SpecmateException;

}
