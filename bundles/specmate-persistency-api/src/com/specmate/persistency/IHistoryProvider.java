package com.specmate.persistency;

import org.eclipse.emf.ecore.EObject;

import com.specmate.common.exception.SpecmateException;
import com.specmate.model.history.History;

public interface IHistoryProvider {
	History getHistory(EObject object) throws SpecmateException;

	History getContainerHistory(EObject object) throws SpecmateException;

	History getRecursiveHistory(EObject object) throws SpecmateException;

}
