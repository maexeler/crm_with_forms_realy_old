package ch.zli.m223.CRM.model.impl;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import ch.zli.m223.CRM.model.Memo;

/** @see Memo */
@Entity(name="Memo")
public class MemoImpl implements Memo {

	@Id
	@GeneratedValue
	private Long id;
	
	private Long coverage;
	private String noteText;
	
	// Two directional mapping,
	// each memo belongs to exact one customer
	@ManyToOne
	private CustomerImpl customer;
	
	
	/** To be used  by JPA only*/
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
	
	@Override
	public Date getCoverageDate() {
		return new Date(coverage);
	}
	
	@Override public String getNote() { return noteText; }
}
