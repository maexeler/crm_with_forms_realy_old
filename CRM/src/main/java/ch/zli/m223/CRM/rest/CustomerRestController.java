package ch.zli.m223.CRM.rest;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import ch.zli.m223.CRM.model.Customer;
import ch.zli.m223.CRM.model.Memo;
import ch.zli.m223.CRM.model.dto.CustomerDto;
import ch.zli.m223.CRM.model.dto.MemoDto;
import ch.zli.m223.CRM.service.CustomerService;

/**
 * Customer Rest Controller </br>
 * maps any customer related request and processes them 
 */
@RestController
public class CustomerRestController {
	
	@Autowired
	private CustomerService customerService;
	
	/**
	 * Get all customers
	 * @return a (possibly empty) list of customers
	 */
	@RequestMapping(value="/rest/customers", method=RequestMethod.GET)
	public Collection<CustomerDto> showCustomerList() {
		Collection<Customer> customers = customerService.getCustomerList();
		ArrayList<CustomerDto> res = new ArrayList<CustomerDto>();
		customers.forEach(customer -> res.add(new CustomerDto(customer)));
		return res;
	}
	
	// CRUD functions for Customers
	
	/**
	 * Create a new customer
	 * @param customerDto the new customer
	 * @return the new Customer
	 */
	@RequestMapping(value="/rest/customer/create", method=RequestMethod.POST)
	public CustomerDto createCustomer(@RequestBody CustomerDto customerDto) {
		Customer customer = 
			customerService.addCustomer(customerDto.name, customerDto.street, customerDto.city);
		return new CustomerDto(customer);
	}
	
	/**
	 * Get (read) an individual customer
	 * @param customerId the customers id
	 * @return a customer or null if not found
	 */
	@RequestMapping(value="/rest/customer/{id}", method=RequestMethod.GET)
	public CustomerDto showCustomer(@PathVariable("id") long customerId) {
		Customer customer = customerService.getCustomer(customerId);
		return new CustomerDto(customer);
	}
	
	/**
	 * Update a customer
	 * @param customerDto the changed customer
	 * @return the updated customer
	 */
	@RequestMapping(value="/rest/customer/update", method=RequestMethod.POST)
	public CustomerDto updateCustomer(@RequestBody CustomerDto customerDto) {
		Customer customer = 
			customerService.updateCustomer(customerDto.id, customerDto.name, customerDto.street, customerDto.city);
		return new CustomerDto(customer);
	}
	
	/**
	 * Delete an individual customer
	 * @param customerId the customers id
	 */
	@RequestMapping(value="/rest/customer/{id}/delete", method=RequestMethod.DELETE)
	public void deleteCustomer(@PathVariable("id") long customerId) {
		customerService.deleteCustomer(customerId);
	}
	
	// CRUD functions for Memos
	
	/**
	 * Get all memos for a given customer
	 */
	@RequestMapping(value="/rest/customer/{id}/memos", method=RequestMethod.GET)
	public List<MemoDto> getMemos(@PathVariable("id") long customerId) {
		Customer customer = customerService.getCustomer(customerId);
		List<MemoDto> res = new ArrayList<>();
		customer.getMemos().forEach(memo -> res.add(new MemoDto(memo)));
		return res;
	}
	
	/**
	 * Create a new memo
	 */
	@RequestMapping(value="/rest/customer/{id}/memo/create", method=RequestMethod.POST)
	public MemoDto createMemo(@PathVariable("id") long customerId, @RequestBody MemoDto memoDto) {
		Memo memo = customerService.addMemoToCustomer(customerId, memoDto.noteText);
		return new MemoDto(memo);
	}
	
	/**
	 * Get a memo
	 */
	@RequestMapping(value="/rest/customer/memo/{id}", method=RequestMethod.GET)
	public MemoDto getMemo(@PathVariable("id") long memoId) {
		Memo memo = customerService.getMemo(memoId);
		return new MemoDto(memo);
	}
	
	/**
	 * Update a memo
	 */
	@RequestMapping(value="/rest/customer/memo/update", method=RequestMethod.POST)
	public MemoDto updateMemo(@RequestBody MemoDto memoDto) {
		Memo memo = customerService.updateMemo(memoDto.id, memoDto.noteText, 
				memoDto.getCoverage()==null ? new Date() : new Date(memoDto.getCoverage()));
		return new MemoDto(memo);
	}
	
	/**
	 * Delete a memo
	 */
	@RequestMapping(value="/rest/customer/memo/{id}/delete", method=RequestMethod.DELETE)
	public void deleteMemo(@PathVariable("id") long memoId) {
		customerService.deleteMemo(memoId);
	}
}
