package ch.zli.m223.CRM.model.dto;

import java.util.Set;

import ch.zli.m223.CRM.model.User;

/** @see User */
public class UserDto {

	public Long id = null;
	public String userName = "";
	public String password = "";
	public Set<String> roles = null;

	public UserDto() {}
	
	public UserDto(User user) {
		if (user == null ) { return; }
		
		this.id = user.getId();
		this.userName = user.getUserName();
		// Do not give away the password
		this.roles = user.getRoleNames();
	}

}
