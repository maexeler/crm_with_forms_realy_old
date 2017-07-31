package ch.zli.m223.CRM.model;

import java.util.Set;

/** Business object abstraction for a user */
public interface User {

	/** @return the id or null if not yet persisted on the data store */
	public Long getId();
	
	/** @return the user name */
	public String getUserName();
	
	/** @return the password */
	public String getPassword();
	
	/** @return the users roles */
	public Set<String> getRoleNames();
}
