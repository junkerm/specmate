package com.specmate.auth.api;

public interface IAuthentificationService {
	public boolean authenticate(String username, String password, String url);
}
