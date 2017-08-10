package ch.zli.m223.CRM.rest;

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
}
  	