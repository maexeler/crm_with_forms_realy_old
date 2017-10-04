package ch.zli.m223.CRM.model.dto;

import ch.zli.m223.CRM.model.Memo;

/** @see Memo */
public class MemoDto {

	public Long id = null;
	public Long coverage = null;
	public String noteText = "";
	public long customerId = 0;
			
	public MemoDto() {}
	
	public MemoDto(Memo memo) {
		if (memo == null) { return; }
		
		this.id = memo.getId();
		this.coverage = memo.getCoverageDate().getTime();
		this.noteText = memo.getNote();
		this.customerId = memo.getCustomer() != null ?memo.getCustomer().getId() : 0;
	}

	public Long getId()           { return id; }
	public Long getCoverage()     { return coverage; }
//	public Date getCoverageDate() { return new Date(coverage); }
	public String getNoteText()   { return noteText; }
	public long getCustomerId()   { return customerId; }

	public void setId(Long id)                 { this.id = id; }
	public void setCoverage(Long coverage)     { this.coverage = coverage; }
	public void setNoteText(String noteText)   { this.noteText = noteText; }
	public void setCustomerId(Long customerId) { this.customerId = customerId; }

	@Override
	public String toString() {
		return "MemoDto [id=" + id + ", coverage=" + coverage + ", noteText=" + noteText + ", customerId=" + customerId
				+ "]";
	}
	
}
