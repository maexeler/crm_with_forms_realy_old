package ch.zli.m223.CRM.security.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * I am not able to construct an example to show CSRF if all its faces</p>
 * Code does not work !!!
 */
@Controller
public class ExampleCsrfController {

	@RequestMapping("/admin/csrf")
	public String showCsrfInputView() {
		return "csrf_test/csrfTestInput";
	}
	
	@RequestMapping("/admin/csrf/processing")
	public String showCsrfOutputView(Model model, @RequestParam("msg") String csrfMessage) {
		System.out.println(String.format("*** msg: %s ***", csrfMessage));
		model.addAttribute("message", csrfMessage);
		return "csrf_test/csrfTestOutput";
	}
	
	/**
	 * When we are using <b>csrf</b>, calling /logout must be done with a POST. </br>
	 * Calling /logout from a link results in a GET which doesn't work.</p>
	 * 
	 * Let's intersect GET and convert it to a POST
	 */
	@RequestMapping(value="/admin/csrf/processing", method = RequestMethod.GET)
	public String showCsrfOutputGetHandler (HttpServletRequest request, HttpServletResponse response) {
		System.out.println("*** showCsrfOutputGetHandler ***");
	    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    if (auth != null){    
	        new SecurityContextLogoutHandler().logout(request, response, auth);
	    }
	    return "redirect:/admin/csrf/processing";
	}
}
