package ch.zli.m223.CRM.service;

import java.util.Collection;
import java.util.Date;

import ch.zli.m223.CRM.model.Customer;
import ch.zli.m223.CRM.model.Memo;

/** The customer services */
public interface CustomerService {

	// CRUD functions for Customer
	// ---------------------------
	
	/** @return the list of all customers*/ 
	Collection<Customer> getCustomerList();
	
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
	 * Update a given Customer
	 * @param customerId the customers id
	 * @param name its name
	 * @param street its street
	 * @param city its city
	 * @return the updated customer object or null if not found
	 */
	Customer updateCustomer(Long customerId, String name, String street, String city);
	
	/**
	 * Delete a customer
	 * @param id the customers id
	 */
	void deleteCustomer(long customerId);
	
	// CRUD functions for Memo
	// -----------------------
	
	/**
	 * Get all memos for a given customer
	 * @param customerId the customers id
	 * @return a (possibly) empty list of memos
	 */
	Collection<Memo> getMemos(long customerId);
	
	/**
	 * Get a memo
	 * @param memoId the memos id
	 * @return the memo or null if not found
	 */
	Memo getMemo(long memoId);
	
	/**
	 * Add a new Memo to a customers memo list
	 * @param customerId the customers id
	 * @param memotext the text for the new memo
	 * @return the newly created memo object
	 */
	Memo addMemoToCustomer(long customerId, String memoText);
	
	/**
	 * Update a given memo
	 * @param memoId the memos id
	 * @param memoText the new text
	 * @param localDateTime the coverage date
	 * @return the updated memo or null if not found
	 */
	Memo updateMemo(Long memoId, String memoText, Date localDateTime);

	/**
	 * Delete a memo
	 * @param memoId the memos id
	 */
	void deleteMemo(long memoId);
}
