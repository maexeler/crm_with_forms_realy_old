package ch.zli.m223.CRM.security.web;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class SecurityController {
	
	@GetMapping(value = {"/login/", "/login"})
    public String loginOrOut(@RequestParam Map<String,String> allRequestParams) {
		if (allRequestParams.containsKey("logout")) {
			return "redirect:/";
		}
        return "security/login";
    }
	
	@GetMapping("/403")
    public String error403() {
        return "security/403";
    }

	/**
	 * When we are using <b>csrf</b>, calling /logout must be done with a POST. </br>
	 * Calling /logout from a link results in a GET which doesn't work.</p>
	 * 
	 * Let's intersect GET and convert it to a POST
	 */
	@GetMapping(value="/logout")
	public String logoutGetHandler (HttpServletRequest request, HttpServletResponse response) {
	    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    if (auth != null){    
	        new SecurityContextLogoutHandler().logout(request, response, auth);
	    }
	    return "redirect:/login?logout";
	}
}
