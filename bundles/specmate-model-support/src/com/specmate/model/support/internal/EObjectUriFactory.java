package com.specmate.model.support.internal;

import java.util.ArrayList;
import java.util.Collections;

import org.apache.commons.lang.StringUtils;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.Resource;
import org.osgi.service.component.annotations.Component;

import com.specmate.common.SpecmateException;
import com.specmate.model.support.util.SpecmateEcoreUtil;
import com.specmate.urihandler.IURIFactory;

@Component(service = IURIFactory.class)
public class EObjectUriFactory implements IURIFactory {

	@Override
	public String getURI(EObject object) throws SpecmateException {
		if (object instanceof Resource) {
			return "";
		}
		ArrayList<String> segments = new ArrayList<String>();
		while (object != null) {
			String id = SpecmateEcoreUtil.getID(object);
			if (id == null || id.isEmpty()) {
				return null;
			}

			EStructuralFeature containingFeature = object.eContainingFeature();
			if (containingFeature != null) {
				segments.add(id);

			} else {
				segments.add(id);
			}
			object = object.eContainer();
		}
		Collections.reverse(segments);
		String result = StringUtils.join(segments, "/");
		return result;
	}

}