package com.specmate.auth.internal;

import java.util.Date;

public class UserSession {
	private String projectname;
	private AccessRights accessRights;
	private Date lastActive;
	private final long maxIdleMilliSeconds;
	
	public UserSession(AccessRights accessRights, int maxIdleMinutes, String projectname) {
		this.accessRights = accessRights;
		this.projectname = projectname;
		lastActive = new Date();
		this.maxIdleMilliSeconds = maxIdleMinutes * 60 * 1000L;
	}
	
	public boolean isExpired() {
		Date now = new Date();
		return (now.getTime() - lastActive.getTime() > maxIdleMilliSeconds);
	}
	
	public void refresh() {
		lastActive = new Date();
	}
	
	public AccessRights getAccessRights() {
		return this.accessRights;
	}
}
