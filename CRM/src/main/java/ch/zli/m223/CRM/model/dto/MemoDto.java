package ch.zli.m223.CRM.model.dto;

import java.util.Date;

import ch.zli.m223.CRM.model.Memo;

/** @see Memo */
public class MemoDto {

	public Long id;
	
	public Date coverage;
	public String noteText;
			
	public MemoDto(Memo memo) {
		this.id = memo.getId();
		this.coverage = memo.getCoverageDate();
		this.noteText = memo.getNote();
	}
}
