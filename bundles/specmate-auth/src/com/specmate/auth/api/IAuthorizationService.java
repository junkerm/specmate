package com.specmate.auth.api;

import com.specmate.usermodel.User;

public interface IAuthorizationService {

	boolean isAuthorized(User user, String url);

}
