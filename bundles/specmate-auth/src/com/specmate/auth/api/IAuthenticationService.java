package com.specmate.auth.api;

import com.specmate.common.SpecmateException;

public interface IAuthenticationService {
	public String authenticate(String username, String password, String projectname) throws SpecmateException;
	public String authenticate(String username, String password) throws SpecmateException;
	public void deauthenticate(String token) throws SpecmateException;
	public void validateToken(String token, String path, boolean refresh) throws SpecmateException;
	public boolean isExportAllowedToALM(String token) throws SpecmateException;
}
