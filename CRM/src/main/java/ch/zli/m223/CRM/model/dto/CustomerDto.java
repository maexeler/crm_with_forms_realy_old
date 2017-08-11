package ch.zli.m223.CRM.model.dto;

import ch.zli.m223.CRM.model.Customer;

/** @see Customer */
public class CustomerDto {

	public Long id = null;
	
	public String name = "";
	public String street= "";
	public String city= "";
	
	public CustomerDto() {}
	
	public CustomerDto(Customer customer) {
		if (customer == null) { return; }
		id = customer.getId();
		name = customer.getName();
		street = customer.getStreet();
		city = customer.getCity();
	}
}
