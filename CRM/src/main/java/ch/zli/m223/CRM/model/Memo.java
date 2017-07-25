package ch.zli.m223.CRM.model;

import java.util.Date;

/** Business object abstraction for a memo */
public interface Memo {
	
	/** @return the date the Memo was created. */
	Date getCoverageDate();
	
	/** @ return the memos content */
	String getNote();
}
