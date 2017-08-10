package ch.zli.m223.CRM.model.dto;

import ch.zli.m223.CRM.model.Customer;

/** @see Customer */
public class CustomerDto {

	public Long id;
	
	public String name;
	public String street;
	public String city;
	
	public CustomerDto(Customer customer) {
		id = customer.getId();
		name = customer.getName();
		street = customer.getStreet();
		city = customer.getCity();
	}
}
