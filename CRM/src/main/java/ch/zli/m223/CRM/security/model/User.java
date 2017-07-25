package ch.zli.m223.CRM.security.model;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import ch.zli.m223.CRM.security.Role;

/**
 * User for the Security implementation
 */
@Entity
public class User {

	@Id
	@GeneratedValue
	private Long id;
	
	@Column(unique=true)
	private String userName;
	
	private String passwordHash;
	
	@ElementCollection(fetch=FetchType.EAGER) // For simple types only, no Role objects allowed
	private Set<String> roleNames;
	
	/** To be used by JPA only */
	public User() {
		userName = passwordHash = "";
		roleNames = new HashSet<>();
	}
	
	/**
	 * ctor
	 * @param userName the users name (must be unique)
	 * @param passwordHash the users password (should be the passwor hash)
	 */
	public User(String userName, String passwordHash) {
		this();
		this.userName = userName;
		this.passwordHash = passwordHash;
	}

	public String getUsername() { return userName;     }
	public String getPassword() { return passwordHash; }

	/** @return the id or null if not yet persisted on the data store */
	public Long getId() { return id; }
	
	/**
	 * Add another role to the user
	 * @param role the additional role
	 */
	public User addRole(Role role)    { roleNames.add(role.getRoleName()); return this; }
	
	/**
	 * Revoke a role from the user
	 * @param role the role to be revoked
	 */
	public User removeRole(Role role) { roleNames.remove(role.getRoleName()); return this; }
	
	/** @return the users roles */
	public Set<String> getRoleNames() {
		return Collections.unmodifiableSet(roleNames);
	}
}
