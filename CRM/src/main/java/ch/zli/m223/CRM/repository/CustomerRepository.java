package ch.zli.m223.CRM.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ch.zli.m223.CRM.model.Customer;
import ch.zli.m223.CRM.model.impl.CustomerImpl;

/** The Customer repository */
public interface CustomerRepository extends JpaRepository<CustomerImpl, Long> {

	/**
	 * Create a new Customer object
	 * @param name the customers name
	 * @param street the customers street
	 * @param city the customers city
	 * @return a new Customer object
	 */
	default Customer create(String name, String street, String city) {
		CustomerImpl customer = new CustomerImpl(name, street, city);
		return save(customer); // Persist changes to data store and return the new customer
	}

	/**
	 * Update a given Customer object
	 * @param name the customers name
	 * @param street the customers street
	 * @param city the customers city
	 * @return the updated customer
	 */
	default Customer update(Customer customer, String name, String street, String city) {
		CustomerImpl customerImpl = (CustomerImpl) customer;
		customerImpl.setName(name);
		customerImpl.setStreet(street);
		customerImpl.setCity(city);
		return save(customerImpl);  // Persist changes to data store and return the updated customer
	}
}
