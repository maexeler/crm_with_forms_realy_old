package ch.zli.m223.CRM.model.impl;

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

import ch.zli.m223.CRM.model.User;

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
	 * Set a new password
	 * @param oldPassword the old (existing) password
	 * @param newPassword the new password to be used
	 * @return true if the password was changed, false otherwise
	 */
	public boolean changePassword(String oldPassword, String newPassword) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		
		if (verifyPassword(oldPassword)) {
			passwordHash = encoder.encode(newPassword);
			return true;
		}
		return false;
	}

	/**
	 * Update roles
	 * @param roleNames the new roles
	 */
	public void updateRoles(String[] roleNames) {
		setRoles(roleNames);
	}
	
	public boolean verifyPassword(String password) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		return passwordHash.equals(password) || 
				encoder.matches(password, passwordHash);
	}
	
	private void setRoles(String... roleNames) {
		this.roleNames = new HashSet<>();
		for (String role : roleNames) {
			addRole(role);
		}
	}

	// Generated code
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserImpl other = (UserImpl) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "UserImpl [id=" + id + ", userName=" + userName + ", roleNames=" + roleNames + "]";
	}
	
	
}
