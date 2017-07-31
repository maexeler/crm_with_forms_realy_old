package ch.zli.m223.CRM.security.model.impl;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import ch.zli.m223.CRM.security.model.User;

/**
 * User for the Security implementation
 */
@Entity
public class UserImpl implements User {

	@Id
	@GeneratedValue
	private Long id;
	
	@Column(unique=true)
	private String userName;
	
	private String passwordHash;
	
	@ElementCollection(fetch=FetchType.EAGER) // For simple types only, no Role objects allowed
	private Set<String> roleNames;

	/** To be used by JPA only */
	public UserImpl() {
		userName = passwordHash = "";
		roleNames = new HashSet<>();
	}
	
	/**
	 * ctor
	 * @param userName the users name (must be unique)
	 * @param passwordHash the users password (should be the passwor hash)
	 */
	public UserImpl(String userName, String passwordHash) {
		this();
		this.userName = userName;
		this.passwordHash = passwordHash;
	}

	@Override public Long getId() { return id; }
	@Override public String getUserName() { return userName;     }
	@Override public String getPassword() { return passwordHash; }

	@Override
	public Set<String> getRoleNames() {
		return Collections.unmodifiableSet(roleNames);
	}
	
	/**
	 * Add another role to the user
	 * @param role the additional role
	 */
	public UserImpl addRole(String role)         { roleNames.add(role); return this; }
	
	/**
	 * Revoke a role from the user
	 * @param role the role to be revoked
	 */
	public UserImpl removeRole(String role)      { roleNames.remove(role); return this; }
	
	/**
	 * Replace all roles
	 * @param roleNames the new role names to be granted
	 */
	public void setRoles(String... roleNames) {
		this.roleNames = new HashSet<>();
		for (String role : roleNames) {
			addRole(role);
		}
	}

	public void setUserName(String userName)     { this.userName = userName; }
	public void setPassword(String password) {
		if (passwordHash == password) { return; }
		
		passwordHash = new BCryptPasswordEncoder().encode(password);
	}

	/**
	 * Set a new password
	 * @param oldPassword the old (existing) password
	 * @param newPassword the new password to be used
	 * @return true if the password was changed, false otherwise
	 */
	public boolean changePassword(String oldPassword, String newPassword) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		
		if (encoder.matches(oldPassword, passwordHash)) {
			passwordHash = encoder.encode(newPassword);
			return true;
		}
		return false;
	}
}
