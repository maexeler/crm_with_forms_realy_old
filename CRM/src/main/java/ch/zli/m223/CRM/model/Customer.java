package ch.zli.m223.CRM.model;

import java.util.Collection;

/** Business object abstraction for a customer */
public interface Customer {

	/** @return the customer id or null if not yet persisted on the data store */
	Long getId();
	
	/** @return the customers name */
	String getName();
	
	/** @return the customers street */
	String getStreet();
	
	/** @return the customers location */
	String getCity();
	
	/** @return a list of memos sorted by time, descending */
	Collection<Memo> getMemos();
}
