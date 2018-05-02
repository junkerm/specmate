package com.specmate.auth.internal;

import java.util.Date;

public class UserSession {
	private static final long MAXIMUM_AGE = 60 * 60 * 12 * 1000L; // 12 hours in milliseconds
	private String projectname;
	private AccessRights accessRights;
	private Date created;
	
	public UserSession(AccessRights accessRights, String projectname) {
		this.accessRights = accessRights;
		this.projectname = projectname;
		created = new Date();
	}
	
	public boolean isExpired() {
		Date now = new Date();
		if (now.getTime() - created.getTime() > MAXIMUM_AGE) {
			return true;
		}
		
		return false;
	}
	
	public AccessRights getAccessRights() {
		return this.accessRights;
	}
}
