package ch.zli.m223.CRM.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ch.zli.m223.CRM.model.Customer;
import ch.zli.m223.CRM.service.CustomerService;

/**
 * Customer Web Controller </br>
 * maps any customer related URL and processes them 
 */
@Controller
public class CustomerController {
	
	@Autowired
	private CustomerService customerService;

	@RequestMapping("/")
	public String root() {
		return "redirect:home";
	}
	
	@RequestMapping("/home")
	public String showCustomerList(Model model) {
		List<Customer> customers = customerService.getCustomerList();
		model.addAttribute("customers", customers);
		return "showCustomerList";
	}
	
	@RequestMapping("/user/showCustomer/{id}")
	public String showCustomer(Model model, @PathVariable("id") long customerId) {
		model.addAttribute("customer", customerService.getCustomer(customerId));
		return "showCustomer";
	}
	
	@RequestMapping("/user/addMemo")
	public String addMemo(Model model, 
			@RequestParam("customerId") long customerId,
			@RequestParam("memoText") String memoText)
	{
		customerService.addMemoToCustomer(customerId, memoText);
		return "redirect:/user/showCustomer/" + customerId;
	}
}
