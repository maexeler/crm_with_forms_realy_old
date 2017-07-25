package ch.zli.m223.CRM.service;

import java.util.List;

import ch.zli.m223.CRM.model.Customer;
import ch.zli.m223.CRM.model.Memo;

/** The customer services */
public interface CustomerService {

	/** @return the list of all customers*/ 
	List<Customer> getCustomerList();
	
	/** 
	 * @param customerId the customer id
	 * @return a customer object or null if not found
	 */
	Customer getCustomer(long customerId);
	
	/**
	 * Add a new customer
	 * @param name its name
	 * @param street its street
	 * @param city its city
	 * @return the newly created customer object
	 */
	Customer addCustomer(String name, String street, String city);
	
	/**
	 * Add a new Memo to a customers memo list
	 * @param customerId the customers id
	 * @param memotext the text for the new memo
	 * @return the newly created memo object
	 */
	Memo addMemoToCustomer(long customerId, String memoText);
}
