package com.specmate.model.support.internal.validation;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import com.specmate.common.SpecmateException;
import com.specmate.common.SpecmateValidationException;
import com.specmate.config.api.IConfigService;
import com.specmate.connectors.api.IProjectConfigService;
import com.specmate.model.base.Folder;
import com.specmate.model.support.api.IAttributeValidationService;
import com.specmate.model.support.util.SpecmateEcoreUtil;

@Component(immediate = true, service = IAttributeValidationService.class)
public class AttributeValidationService implements IAttributeValidationService {
	/** Pattern that describes valid object ids */
	private static Pattern idPattern = Pattern.compile("[a-zA-Z_0-9\\-]+");

	private IConfigService configService;

	@Override
	public void validateID(EObject object) throws SpecmateValidationException {
		String id = SpecmateEcoreUtil.getID(object);
		if (id == null) {
			throw new SpecmateValidationException("Object does not have a valid Id");
		}
		if (!idPattern.matcher(id).matches()) {
			throw new SpecmateValidationException("Object id may only contain letters, digits, '_' and '-'");
		}
	}

	@Override
	public void validateUniqueID(Object parent, EObject object) throws SpecmateValidationException {
		String id = SpecmateEcoreUtil.getID(object);
		EObject existing;
		try {
			existing = SpecmateEcoreUtil.getEObjectWithId(id, getChildren(parent));
		} catch (SpecmateException e) {
			throw new SpecmateValidationException(e.getMessage());
		}
		if (existing != null) {
			throw new SpecmateValidationException("Duplicate id:" + id);
		}
	}

	@Override
	public void validateFolderName(Folder folder) throws SpecmateValidationException {
		String name = folder.getName();
		if (name == null) {
			throw new SpecmateValidationException("Folder name is undefined");
		}

		if (name.trim().length() == 0) {
			throw new SpecmateValidationException("Folder name is empty");
		}
	}

	@Override
	public void validateNotTopLevel(Object parent, EObject object) throws SpecmateValidationException {
		if (parent instanceof EObject) {
			if (SpecmateEcoreUtil.isProject((EObject) parent)) {
				throw new SpecmateValidationException(SpecmateEcoreUtil.getName(object) + " is at top-level");
			}
		}
	}

	@Override
	public void validateNotTopLevelLibraryFolder(Object parent, EObject object) throws SpecmateValidationException {
		if (parent instanceof EObject) {
			EObject p = (EObject) parent;
			if (SpecmateEcoreUtil.isProject(p)) {
				String projectName = SpecmateEcoreUtil.getName(p);
				String[] libraryFolders = configService.getConfigurationPropertyArray(
						IProjectConfigService.PROJECT_PREFIX + projectName + IProjectConfigService.KEY_PROJECT_LIBRARY);
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

	private List<EObject> getChildren(Object target) throws SpecmateException {
		if (target instanceof Resource) {
			return ((Resource) target).getContents();
		} else if (target instanceof EObject) {
			return ((EObject) target).eContents();
		} else {
			throw new SpecmateException("Object is no resource and no EObject");
		}
	}

	@Reference
	public void setConfigService(IConfigService configService) {
		this.configService = configService;
	}
}
