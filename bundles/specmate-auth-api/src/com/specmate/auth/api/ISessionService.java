package com.specmate.auth.api;

import com.specmate.common.SpecmateException;
import com.specmate.common.SpecmateValidationException;
import com.specmate.usermodel.AccessRights;
import com.specmate.usermodel.UserSession;

public interface ISessionService {
	public UserSession create(AccessRights alm, AccessRights ppm, String userName, String projectName)
			throws SpecmateException, SpecmateValidationException;

	public UserSession create();

	public boolean isExpired(String token) throws SpecmateException;

	public boolean isAuthorized(String token, String path) throws SpecmateException;

	public void refresh(String token) throws SpecmateException, SpecmateValidationException;

	public String getUserName(String token) throws SpecmateException;

	public AccessRights getSourceAccessRights(String token) throws SpecmateException;

	public AccessRights getTargetAccessRights(String token) throws SpecmateException;

	public void delete(String token) throws SpecmateException, SpecmateValidationException;
}