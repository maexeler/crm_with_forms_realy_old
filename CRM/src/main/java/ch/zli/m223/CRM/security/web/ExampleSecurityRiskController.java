package ch.zli.m223.CRM.security.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import ch.zli.m223.CRM.service.CustomerService;

@Controller
public class ExampleSecurityRiskController {
	
	@Autowired CustomerService customerService;
	
	/**
	 * This mapping is a big security hole since we have 
	 * mapped "/public/** as permitAll() in WebSecurityConfig
	 * and customerService.getCustomer(customerId)) should only
	 * be accessible to a CrmRole.USER
	 * </p>
	 * Securing Http is obviously not good enough!
	 * </br>
	 * Securing all service methods is a must.
	 * </p>
	 * To do this, add @EnableGlobalMethodSecurity(jsr250Enabled  = true)
	 * to the WebSecurityConfig class and the annotations @RolesAllowed(...)
	 * or @PermittAll to every service method in the implementation.
	 */
	@RequestMapping("/public/showCustomer/{id}")
	public String showCustomer(Model model, @PathVariable("id") long customerId) {
		model.addAttribute("customer", customerService.getCustomer(customerId));
		return "showCustomer";
	}

}
