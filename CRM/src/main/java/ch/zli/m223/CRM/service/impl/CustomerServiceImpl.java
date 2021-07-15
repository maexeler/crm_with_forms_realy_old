package ch.zli.m223.CRM.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import ch.zli.m223.CRM.model.Customer;
import ch.zli.m223.CRM.model.Memo;
import ch.zli.m223.CRM.model.impl.CustomerImpl;
import ch.zli.m223.CRM.repository.CustomerRepository;
import ch.zli.m223.CRM.repository.MemoRepository;
import ch.zli.m223.CRM.role.CrmRoles;
import ch.zli.m223.CRM.service.CustomerService;

/** @see CustomerService */
@Service
public class CustomerServiceImpl implements CustomerService {

	@Autowired private CustomerRepository customerRepository;
	@Autowired private MemoRepository memorepository;
	
	@Override
	@PermitAll
	public Collection<Customer> getCustomerList() {
		return new ArrayList<Customer>(customerRepository.findAll(Sort.by("name")));
	}

	@Override
	@RolesAllowed(CrmRoles.USER)
	public Customer getCustomer(long customerId) {
		return customerRepository.findById(customerId).orElse(null);
	}

	@Override
	@RolesAllowed(CrmRoles.USER)
	public Customer addCustomer(String name, String street, String city) {
		return customerRepository.create(name, street, city);
	}

	@Override
	@RolesAllowed(CrmRoles.USER)
	public Memo addMemoToCustomer(long customerId, String memoText) {
		Customer customer = customerRepository.findById(customerId).orElse(null);
		if (customer == null) { return null; }
		
		return memorepository.addMemo((CustomerImpl)customer, memoText);
	}

	// Implementations needed by the REST-API
	// --------------------------------------
	
	@Override
	public Customer updateCustomer(Long customerId, String name, String street, String city) {
		Customer customer = customerRepository.findById(customerId).orElse(null);
		if (customer == null) { return null; }
		
		return customerRepository.update(customer, name, street, city);
	}

	@Override
	public void deleteCustomer(long customerId) {
		customerRepository.deleteById(customerId);
	}

	@Override
	public Collection<Memo> getMemos(long customerId) {
		Customer customer = customerRepository.findById(customerId).orElse(null);
		if (customer == null) { return null; }
		
		return customer.getMemos();
	}

	@Override
	public Memo getMemo(long memoId) {
		return memorepository.findById(memoId).orElse(null);
	}

	@Override
	public Memo updateMemo(Long memoId, String memoText, Date date) {
		Memo memo = memorepository.findById(memoId).orElse(null);
		if (memo == null) { return null; }
		
		return memorepository.update(memo, memoText, date);
	}

	@Override
	public void deleteMemo(long memoId) {
		Memo memo = memorepository.findById(memoId).orElse(null);
		if (memo == null) { return; }
		
		memorepository.deleteMemoFromCustomer(memo);
	}
	
}
