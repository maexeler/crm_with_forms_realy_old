package ch.zli.m223.CRM.model.dto;

import java.util.Date;

import ch.zli.m223.CRM.model.Memo;

/** @see Memo */
public class MemoDto {

	public Long id = null;
	
	public Date coverage = null;
	public String noteText = "";
			
	public MemoDto() {}
	
	public MemoDto(Memo memo) {
		if (memo == null) { return; }
		
		this.id = memo.getId();
		this.coverage = memo.getCoverageDate();
		this.noteText = memo.getNote();
	}
}
