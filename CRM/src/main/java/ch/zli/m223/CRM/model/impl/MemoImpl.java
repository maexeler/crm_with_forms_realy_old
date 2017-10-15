package ch.zli.m223.CRM.model.impl;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import ch.zli.m223.CRM.model.Customer;
import ch.zli.m223.CRM.model.Memo;

/** @see Memo */
@Entity(name="Memo")
public class MemoImpl implements Memo {

	@Id
	@GeneratedValue
	private Long id;
	
	private long coverage;
	private String noteText;
	
	// Two directional mapping,
	// Each memo belongs to exact one customer
	@ManyToOne
	private CustomerImpl customer;
	
	/** To be used by us and JPA only*/
	public MemoImpl() {
		coverage = new Date().getTime();
		noteText = "";
	}
	
	/**
	 * ctor
	 * @param customer the customer object the memo belongs to
	 * @param noteText the memos text
	 */
	public MemoImpl(CustomerImpl customer, String noteText) {
		this();
		this.customer = customer;
		this.noteText = noteText;
	}
	
	@Override public Long getId()            { return id; }
	@Override public Date getCoverageDate()  { return new Date(coverage); }
	@Override public String getNote()        { return noteText; }
	@Override public Customer getCustomer()  { return customer; }

	public void setMemoText(String memoText) { noteText = memoText; }

	public void setDate(Date date)           { coverage = date.getTime(); }

	// Generated code
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MemoImpl other = (MemoImpl) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "MemoImpl [id=" + id + ", coverage=" + coverage + ", noteText=" + noteText + "]";
	}
	
}
