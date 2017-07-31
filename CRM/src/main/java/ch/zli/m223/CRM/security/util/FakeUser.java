package ch.zli.m223.CRM.security.util;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import ch.zli.m223.CRM.security.SpringRole;

/**
 * Create a fake user for Spring Security
 */
@SuppressWarnings("serial")
public class FakeUser implements Authentication {

	private String userName;
	private String password;
	private boolean isAuthenticated = true;
	private Collection<GrantedAuthorityImpl> grantedRoles = new ArrayList<>();
	
	/** userName and password is "fakeUser" */
	public FakeUser(String... roles) {
		this("fakeUser", "fakeUser", roles);
	}
	
	/** password equals userName */
	public FakeUser(String userName, String... roles) {
		this(userName, userName, roles);
	}

	public FakeUser(String userName, String password, String... roles) {
		this.userName = userName;
		this.password = password;
		for (String role : roles) {
			grantedRoles.add(
				new GrantedAuthorityImpl(role.startsWith(SpringRole.ROLE_PREFIX) ? role : SpringRole.ROLE_PREFIX + role));
		}
	}
	
	@Override public Collection<? extends GrantedAuthority> getAuthorities() {
		return grantedRoles;
	}

	@Override public String getName()          { return userName;        }
	@Override public boolean isAuthenticated() { return isAuthenticated; }
	@Override public Object getPrincipal()     { return userName;        }
	@Override public Object getDetails()       { return null;            }
	@Override public Object getCredentials()   { return password;        }
	@Override public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
		this.isAuthenticated = isAuthenticated;
	}
	
	private static class GrantedAuthorityImpl implements GrantedAuthority {
		private String authority;
		
		public GrantedAuthorityImpl(String authority) {
			this.authority = authority;
		}
		
		@Override public String getAuthority() { return authority; }
	}
	
}
