package ch.zli.m223.CRM.repository;

import java.util.Date;

import org.springframework.data.repository.CrudRepository;

import ch.zli.m223.CRM.model.Customer;
import ch.zli.m223.CRM.model.Memo;
import ch.zli.m223.CRM.model.impl.CustomerImpl;
import ch.zli.m223.CRM.model.impl.MemoImpl;

/** The Memo repository */
public interface MemoRepository extends CrudRepository<MemoImpl, Long> {
	
	/**
	 * Create a new Memo object and add it to this customer 
	 * @param customer
	 * @param memoText
	 * @return a new Memo object
	 */
	public default Memo addMemo(Customer customer, String memoText) {
		CustomerImpl customerImpl = (CustomerImpl)customer;
		
		MemoImpl memo = new MemoImpl(customerImpl, memoText);
		customerImpl.addMemo(memo);
		save(memo);
		return memo;
	}

	default public Memo update(Memo memo, String memoText, Date date) {
		MemoImpl memoImpl = (MemoImpl)memo;
		memoImpl.setMemoText(memoText);
		memoImpl.setDate(date);
		return save(memoImpl);
	}

	default public void deleteMemoFromCustomer(Memo memo) {
		CustomerImpl customer = (CustomerImpl)memo.getCustomer();
		customer.removeMemo((MemoImpl)memo);
		delete(memo.getId());
	}
}
