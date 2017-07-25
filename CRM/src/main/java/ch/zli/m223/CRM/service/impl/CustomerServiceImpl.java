package ch.zli.m223.CRM.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import ch.zli.m223.CRM.model.Customer;
import ch.zli.m223.CRM.model.Memo;
import ch.zli.m223.CRM.model.impl.CustomerImpl;
import ch.zli.m223.CRM.repository.CustomerRepository;
import ch.zli.m223.CRM.repository.MemoRepository;
import ch.zli.m223.CRM.service.CustomerService;

/** @see CustomerService */
@Service
public class CustomerServiceImpl implements CustomerService {

	@Autowired private CustomerRepository customerRepository;
	@Autowired private MemoRepository memorepository;
	
	@Override
	public List<Customer> getCustomerList() {
		return new ArrayList<Customer>(customerRepository.findAll(new Sort("name")));
	}

	@Override
	public Customer getCustomer(long customerId) {
		return customerRepository.findOne(customerId);
	}

	@Override
	public Customer addCustomer(String name, String street, String city) {
		return customerRepository.create(name, street, city);
	}

	@Override
	public Memo addMemoToCustomer(long customerId, String memoText) {
		Customer customer = customerRepository.findOne(customerId);
		if (customer == null) { return null; }
		
		return memorepository.addMemo((CustomerImpl)customer, memoText);
	}

}
