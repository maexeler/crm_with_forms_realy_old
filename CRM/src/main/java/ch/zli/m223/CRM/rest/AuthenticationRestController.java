package ch.zli.m223.CRM.rest;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ch.zli.m223.CRM.model.dto.CredentialDto;
import ch.zli.m223.CRM.model.dto.RolesDto;
import ch.zli.m223.CRM.role.CrmRoles;
import ch.zli.m223.CRM.security.service.impl.UserDetailsServiceImpl;

/**
 * Rest authentication controller
 * <p>
 * Note: thats not the best way to do it.
 * <br>
 * It is only the simplest way to deal with the security configuration
 * we already use for the Web-Controller.
 * <br>
 * We should better install a Request filter and add some form of token store
 * for a truly state less implementation. <br>But that's lot of work.
 * May be next time :-)
 */
@RestController
public class AuthenticationRestController {
	
	@Autowired private AuthenticationManager authenticationManager;

	/** Log in as a user
	 * <br>
	 * We can't use the web interface, so let's do it by code.
	 * 
	 * @param username the users name
	 * @param password the users password
	 */
	@RequestMapping(value="/rest/v1/authentication/login", method=RequestMethod.POST)
	public void login(@RequestParam("username") String username, @RequestParam("password") String password) {
		
		// Create user information
		UsernamePasswordAuthenticationToken authRequest = 
				new UsernamePasswordAuthenticationToken(username, password);
		
		// Inject a BCryptPasswordEncoder for the authentication
		ProviderManager pm = (ProviderManager)authenticationManager;
		DaoAuthenticationProvider dap = (DaoAuthenticationProvider)pm.getProviders().get(0);
		dap.setPasswordEncoder(new BCryptPasswordEncoder());

		// Try to authenticate the user
		Authentication authentication = authenticationManager.authenticate(authRequest);
		if (authentication.isAuthenticated()) {
			// if OK, we're in
			SecurityContextHolder.getContext().setAuthentication(authentication);
		} else {
			// no one should be authenticated
			logout();
		}
	}
	
	/** Log out
	 * <br> by clearing the security context
	 */
	@RequestMapping(value="/rest/v1/authentication/logout", method=RequestMethod.GET)
	public void logout() {
		SecurityContextHolder.clearContext();
	}
	
	/** @return all available roles */
	@RequestMapping(value="/rest/v1/authentication/roles", method=RequestMethod.GET)
	public RolesDto roles() {
		return new RolesDto(CrmRoles.ALL_ROLES);
	}
	
	/** @return the credentials for the currently authentication */
	@RequestMapping(value="/rest/v1/authentication/credentials", method=RequestMethod.GET)
	public CredentialDto credentials() {
		String name = "";
		Set<String> roles = null;
		
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal(); 
		if (principal instanceof UserDetailsServiceImpl.UserDetailsImpl) {
			UserDetailsServiceImpl.UserDetailsImpl user = (UserDetailsServiceImpl.UserDetailsImpl)principal;
			name = user.getUsername();
			roles = user.getRoleNames();
		}
		
		return new CredentialDto(name, roles);
	}
}
  	