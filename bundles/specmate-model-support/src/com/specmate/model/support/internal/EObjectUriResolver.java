package com.specmate.model.support.internal;

import static com.specmate.model.support.util.SpecmateEcoreUtil.getEObjectWithId;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.Resource;
import org.osgi.service.component.annotations.Component;

import com.specmate.urihandler.IObjectResolver;

@Component(service = IObjectResolver.class)
public class EObjectUriResolver implements IObjectResolver {

	public EObjectUriResolver() {
	}

	@Override
	public EObject getObject(String uri, Resource resource) {
		List<String> segments = Arrays.asList(StringUtils.split(uri, "/"));
		EObject object = null;
		List<EObject> candidates = resource.getContents();
		for (int i = 0; i < segments.size(); i++) {
			String currentSegment = segments.get(i);
			if (currentSegment.isEmpty()) {
				continue;
			}
			object = getEObjectWithId(currentSegment, candidates);
			if (object == null) {
				return null;
			}
			EStructuralFeature feature = object.eClass().getEStructuralFeature("contents");
			if (feature != null) {
				candidates = (List<EObject>) object.eGet(feature);
			} else {
				candidates = Collections.emptyList();
			}
		}
		return object;
	}

}