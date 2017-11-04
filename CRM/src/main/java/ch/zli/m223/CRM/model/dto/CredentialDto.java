package ch.zli.m223.CRM.model.dto;

import java.util.HashSet;
import java.util.Set;

/** Maps a users credential information */
public class CredentialDto {

	public String userName;
	public Set<String> roles;

	public CredentialDto() {}
	
	public CredentialDto(String userName, Set<String> roles) {
		this.userName = userName;
		this.roles = roles != null ? roles : new HashSet<>();
	}
}
