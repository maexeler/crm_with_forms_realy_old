package ch.zli.m223.CRM;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import ch.zli.m223.CRM.model.Customer;
import ch.zli.m223.CRM.model.Memo;
import ch.zli.m223.CRM.service.CustomerService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CustomerServiceTest {
	
	@Autowired
	private CustomerService customerService;

	@Test public void getCustomerListTest() {
		List<Customer> customers = customerService.getCustomerList();
		assertTrue(customers != null);
	}
	
	@Test public void getCustomerTest() {
		Customer customer = customerService.getCustomer(1);
		assertFalse(customer == null);
		assertEquals("Werner Max", customer.getName());
	}
	
	@Test public void addCustomerTest() {
		int sizeBefore = customerService.getCustomerList().size();
		customerService.addCustomer("Me", "here", "there");
		int sizeAfter = customerService.getCustomerList().size();
		assertTrue(sizeBefore == sizeAfter-1);
	}
	
	@Test public void updateCustomerTest() {
		Customer customer = customerService.getCustomer(1);
		customerService.updateCustomer(1L, "MaxTrax", customer.getStreet(), customer.getCity());
		customer = customerService.getCustomer(1);
		assertEquals("MaxTrax", customer.getName());
		
		customerService.updateCustomer(42L, "no customer 42", customer.getStreet(), customer.getCity());
	}
	
	@Test public void deleteCustomerTest() {
		Customer customer = customerService.addCustomer("MeToo", "here", "there");
		customerService.deleteCustomer(customer.getId());
		assertEquals(null, customerService.getCustomer(customer.getId()));
	}
	
	@Test public void getMemosTest() {
		List<Memo> memos = customerService.getMemos(1L);
		assertTrue(memos != null);
		assertTrue(memos.size() > 0);
		
		customerService.getMemos(42L);
	}
	
	@Test public void getMemoTest() {
		assertFalse(customerService.getMemo(1L) == null);
	}
	
	@Test public void addMemoToCustomerTest() {
		Customer customer = customerService.getCustomer(1L);
		int sizeBefore = customer.getMemos().size();
		customerService.addMemoToCustomer(customer.getId(), "blabla");
		int sizeAfter = customerService.getMemos(customer.getId()).size();
		assertTrue(sizeBefore == sizeAfter-1);
		
		customerService.addMemoToCustomer(42L, "no customer 42");
	}
	
	@Test public void updateMemoTest() {
		List<Memo> memos = customerService.getMemos(1L);
		long memoId = memos.get(1).getId();
		Date date = new Date();
		customerService.updateMemo(memoId, "gaga", date);
		Memo memo = customerService.getMemo(memoId);
		assertEquals("gaga", memo.getNote());
		assertEquals(memo.getCoverageDate().getTime(), date.getTime());
		
		customerService.updateMemo(42L, "No memo 42", new Date());
	}

	@Test public void deleteMemoTest() {
		List<Memo> memos = customerService.getMemos(1L);
		int sizeBefore = memos.size();
		customerService.deleteMemo(memos.get(0).getId());
		int sizeAfter = customerService.getMemos(1L).size();
		assertTrue(sizeBefore -1 == sizeAfter);
		
		customerService.deleteMemo(42);
	}
	
}
