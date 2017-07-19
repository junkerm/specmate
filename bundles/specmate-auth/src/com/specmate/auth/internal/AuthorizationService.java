package com.specmate.auth.internal;

import java.util.List;
import java.util.regex.Pattern;

import org.osgi.service.component.annotations.Component;

import com.specmate.auth.api.IAuthorizationService;
import com.specmate.usermodel.User;

@Component
public class AuthorizationService implements IAuthorizationService {

	@Override
	public boolean isAuthorized(User user, String url) {
		List<String> urlPatterns = user.getAllowedUrls();
		return urlPatterns.stream().anyMatch(p -> Pattern.matches(p, url));
	}

}
