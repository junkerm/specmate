package com.specmate.persistency.validation;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;

import com.specmate.common.SpecmateValidationException;
import com.specmate.model.support.util.SpecmateEcoreUtil;
import com.specmate.persistency.IChangeListener;
import com.specmate.persistency.IValidator;
import com.specmate.persistency.event.EChangeKind;

public class LibraryFolderValidator implements IChangeListener, IValidator {

	// NOT NEEDED?

	@Override
	public void changedObject(EObject object, EStructuralFeature feature, EChangeKind changeKind, Object oldValue,
			Object newValue) throws SpecmateValidationException {

	}

	@Override
	public void removedObject(EObject object) throws SpecmateValidationException {
		// TODO Auto-generated method stub

	}

	@Override
	public void newObject(EObject object, String id, String className, Map<EStructuralFeature, Object> featureMap)
			throws SpecmateValidationException {
		// TODO Auto-generated method stub

	}

	public void validateNotTopLevelLibraryFolder(Object parent, EObject object) throws SpecmateValidationException {
		if (parent instanceof EObject) {
			EObject p = (EObject) parent;
			if (SpecmateEcoreUtil.isProject(p)) {
				String projectName = SpecmateEcoreUtil.getName(p);
				String[] libraryFolders = null; // configService.getConfigurationPropertyArray(projectName);
				if (libraryFolders != null) {
					List<String> lf = Arrays.asList(libraryFolders);
					String oName = SpecmateEcoreUtil.getName(object);
					if (lf.contains(oName)) {
						throw new SpecmateValidationException(oName + " is a top-level folder");
					}
				}
			}
		}
	}

}
