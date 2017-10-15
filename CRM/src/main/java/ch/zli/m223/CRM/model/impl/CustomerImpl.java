package ch.zli.m223.CRM.model.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import ch.zli.m223.CRM.model.Customer;
import ch.zli.m223.CRM.model.Memo;

/** @see Customer */
@Entity(name="Customer")	// (1)
public class CustomerImpl implements Customer {

	@Id	// (2)
	@GeneratedValue // (3)
	private Long id;
	
	private String name;
	private String street;
	private String city;
	
	// Two directional mapping
	// Each customer owns its memos
	// Deleting a customer deletes its memos too.
	@OneToMany(mappedBy="customer", fetch=FetchType.EAGER, cascade = CascadeType.REMOVE)
	private List<MemoImpl> memos;

	/** To be used  by JPA only*/
	public CustomerImpl() {
		name = street = city = "";
		memos = new ArrayList<>();
	}
	
	/**
	 * ctor
	 * @param name the customers name
	 * @param street the customers street
	 * @param city the customers address
	 */
	public CustomerImpl(String name, String street, String city) {
		this();
		this.name = name;
		this.street = street;
		this.city = city;
	}
	
	@Override public Long getId()       { return id; }
	@Override public String getName()   { return name; }
	@Override public String getStreet() { return street; }
	@Override public String getCity()   { return city; }
	
	@Override public List<Memo> getMemos() {
		ArrayList<Memo> res = new ArrayList<Memo>(memos);
		Collections.sort(res, new Comparator<Memo>() {
			public int compare(Memo o1, Memo o2) {
				return (int)(o2.getId() - o1.getId());
			}
		});
		return res;
	}

	public void addMemo(MemoImpl memo) {
		memos.add(memo);
	}

	public void removeMemo(MemoImpl memo) {
		memos.remove(memo);
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public void setCity(String city) {
		this.city = city;
	}

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
		CustomerImpl other = (CustomerImpl) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "CustomerImpl [id=" + id + ", name=" + name + ", street=" + street + ", city=" + city + "]";
	}
	
}
