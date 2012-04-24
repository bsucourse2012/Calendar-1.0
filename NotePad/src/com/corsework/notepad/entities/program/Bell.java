package com.corsework.notepad.entities.program;

import java.util.Date;

public class Bell extends Record {
	
	/**
	 * Date of the bell.
	 */
	private Date date;
	
	/**
	 * Creates new empty Bell.
	 */
	public Bell() {
		super();
		this.date = new Date();
	}
	
	public Bell(Date date) {
		super();
		this.date = date;
	}
	
	public Bell(Long id, Sys sys, Date date) {
		super(id, sys);
		this.date = date;
	}
	
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	@Override
	public String toString() {
		return "id=" + this.getId() +
			", cr=" + this.getSys().getCr() +
			", md=" + this.getSys().getMd() +
			", date=" + this.getDate();		
	}
	
}
