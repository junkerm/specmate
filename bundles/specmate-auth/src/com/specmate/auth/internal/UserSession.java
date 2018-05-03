package com.specmate.auth.internal;

import java.util.Date;

public class UserSession {
	private String projectname;
	private AccessRights accessRights;
	private Date created;
	private final long maxIdleMilliSeconds;
	
	public UserSession(AccessRights accessRights, int maxIdleMinutes, String projectname) {
		this.accessRights = accessRights;
		this.projectname = projectname;
		created = new Date();
		this.maxIdleMilliSeconds = maxIdleMinutes * 60 * 1000L;
	}
	
	public boolean isExpired() {
		Date now = new Date();
		if (now.getTime() - created.getTime() > maxIdleMilliSeconds) {
			return true;
		}
		
		return false;
	}
	
	public AccessRights getAccessRights() {
		return this.accessRights;
	}
}
