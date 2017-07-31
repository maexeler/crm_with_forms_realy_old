package ch.zli.m223.CRM.security;

import ch.zli.m223.CRM.role.CrmRoles;

/**
 * Define CRM role names in a spring like fashion for commodity
 */
public interface CrmSpringRoles extends SpringRole, CrmRoles {
	
	public static final String   ROLE_USER      = ROLE_PREFIX + USER;
	public static final String   ROLE_ADMIN     = ROLE_PREFIX + ADMIN;
	public static final String[] ROLE_ALL_ROLES = { ROLE_USER, ROLE_ADMIN };
}
