package ch.zli.m223.CRM.model;

import java.util.Set;

/** Business object abstraction for a user */
public interface User {

	/** @return the id or null if not yet persisted on the data store */
	public Long getId();
	
	/** @return the user name */
	public String getUserName();
	
	/** @return the password */
	public String getPasswordHash();
	
	/** @return the users roles */
	public Set<String> getRoleNames();

	/**
	 * Verify a password against the users password
	 * @param password the password as text
	 * @return true if password valid, false otherwise
	 */
	public boolean verifyPassword(String password);
}
