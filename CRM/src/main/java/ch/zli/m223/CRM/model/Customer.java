package ch.zli.m223.CRM.model;

import java.util.List;

/** Business object abstraction for a customer */
public interface Customer {

	/** @return the customer id or null if not yet persisted on the datastore */
	Long getCustomerId();
	
	/** @return the customers name */
	String getName();
	
	/** @return the customers street */
	String getStreet();
	
	/** @return the customers location */
	String getCity();
	
	/** @return a list of memos */
	List<Memo> getMemos();
}
