package ch.zli.m223.CRM.role;

import ch.zli.m223.CRM.security.Role;

/**
 * Public constants to define CRM roles
 */
public enum CrmRole implements Role {
	ADMIN("crm admin"),
	USER("crm user");
	
	private String roleName;

	private CrmRole(String roleName) {
		this.roleName = roleName;
	}
	
	@Override
	public String getRoleName() { return roleName; }
	
	@Override
	public Role getRoleByName(String roleName) {
		for(CrmRole role: CrmRole.values()) {
		    if(role.roleName.equals(roleName)) {
		      return role;
		    }
		  }
		  return null; // not found
	}
}
