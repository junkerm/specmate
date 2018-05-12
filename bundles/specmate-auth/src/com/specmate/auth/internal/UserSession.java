package com.specmate.auth.internal;

import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.specmate.usermodel.AccessRights;

public class UserSession {
	private static final Set<Character> ALLOWED_PROJECTNAME_CHARS = new HashSet<>(Arrays.asList(' ', '_', '-'));
	private AccessRights accessRights;
	private Date lastActive;
	private final long maxIdleMilliSeconds;
	private Pattern projectPattern;
	
	public UserSession(AccessRights accessRights, int maxIdleMinutes, String projectName) {
		this.accessRights = accessRights;
		lastActive = new Date();
		this.maxIdleMilliSeconds = maxIdleMinutes * 60 * 1000L;
		this.projectPattern = Pattern.compile(".+services/rest/" + sanitize(projectName) + "/.*");
	}
	
	public UserSession(AccessRights accessRights, int maxIdleMinutes) {
		this.accessRights = accessRights;
		lastActive = new Date();
		this.maxIdleMilliSeconds = maxIdleMinutes * 60 * 1000L;
		this.projectPattern = Pattern.compile(".*");
	}
	
	public boolean isExpired() {
		Date now = new Date();
		return (now.getTime() - lastActive.getTime() > maxIdleMilliSeconds);
	}
	
	public boolean isAuthorized(String path) {
		Matcher matcher = projectPattern.matcher(path);
		return matcher.matches();
	}
	
	public void refresh() {
		lastActive = new Date();
	}
	
	public AccessRights getAccessRights() {
		return this.accessRights;
	}
	
	private String sanitize(String projectName) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < projectName.length(); i++) {
			Character c = projectName.charAt(i);
			if (Character.isLetterOrDigit(c) || ALLOWED_PROJECTNAME_CHARS.contains(c)) {
				sb.append(c);
			}
		}
		
		return sb.toString();
	}
}
