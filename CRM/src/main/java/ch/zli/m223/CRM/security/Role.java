package ch.zli.m223.CRM.security;

/**
 * Abstraction for a role implementation
 */
public interface Role {
	
	/** @return the role as string */
	String getRoleName();
	
	/** 
	 * Look up a role by name
	 * @param roleName the role name for the role to be searched
	 * @return the role corresponding to roleName or null if no such role exists */
	Role getRoleByName(String roleName);
	
	/** 
	 * Does a role for a given name exist?
	 * @param roleName the role name for the role to be searched
	 * @return true if a role for a given name exists, false otherwise */
	default boolean getRoleExists(String roleName) {
		return getRoleByName(roleName) != null;
	}
}
