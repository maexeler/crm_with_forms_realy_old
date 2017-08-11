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
 * Thats not the way to do it. <p />
 * 
 * It is the simplest way to deal with the security configuration
 * we already use for the Web-Controller. <br />
 * 
 * We would better install a Request filter and add some form of token store
 * for a truly stateless implementation. But that would be a lot of work.
 * May be next time.
 */
@RestController
public class AuthenticationRestController {
	
	@Autowired private AuthenticationManager authenticationManager;

	@RequestMapping(value="/rest/authentication/login") // TODO : PUT only, method=RequestMethod.PUT)
	public void login(@RequestParam("username") String username, @RequestParam("password") String password) {
		
		UsernamePasswordAuthenticationToken authRequest = 
				new UsernamePasswordAuthenticationToken(username, password);
		
		// This is a kind of a hack: let us inject a BCryptPasswordEncoder
		ProviderManager pm = (ProviderManager)authenticationManager;
		DaoAuthenticationProvider dap = (DaoAuthenticationProvider)pm.getProviders().get(0);
		dap.setPasswordEncoder(new BCryptPasswordEncoder());

		Authentication authentication = authenticationManager.authenticate(authRequest);
		if (authentication.isAuthenticated()) {
			SecurityContextHolder.getContext().setAuthentication(authentication);
		} else {
			logout();
		}
	}
	
	@RequestMapping(value="/rest/authentication/logout", method=RequestMethod.GET)
	public void logout() {
		SecurityContextHolder.clearContext();
	}
	
	@RequestMapping(value="/rest/authentication/roles", method=RequestMethod.GET)
	public RolesDto roles() {
		return new RolesDto(CrmRoles.ALL_ROLES);
	}
	
	@RequestMapping(value="/rest/authentication/credentials", method=RequestMethod.GET)
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
  	