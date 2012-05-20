package com.corsework.notepad.entities.program;

import java.util.Date;

public class Bell extends Record {
	
	/**
	 * Date of the bell.
	 */
	private long date;
	private boolean active;
	private long idrem;
	/**
	 * Creates new empty Bell.
	 */
	public Bell() {
		super();
		this.date = 0;
		active = true;
	}
//	
//	public Bell(long date) {
//		super();
//		this.date = date;
//	}
	public Bell(long date,long idp,boolean ac) {
		super();
		this.date = date;
		this.idrem = idp;
		this.active = ac;
	}
	
//	public Bell(Long id, Sys sys, long date) {
//		super(id, sys);
//		this.date = date;
//	}
	public Bell(Long id, Sys sys, long date,long idp,boolean ac) {
		super(id, sys);
		this.date = date;
		this.idrem = idp;
		this.active = ac;
	}
	
	public long getDate() {
		return date;
	}

	public void setDate(long date) {
		this.date = date;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public long getIdrem() {
		return idrem;
	}

	public void setIdrem(long idrem) {
		this.idrem = idrem;
	}

	@Override
	public String toString() {
		return "id=" + this.getId() +
			", cr=" + this.getSys().getCr() +
			", md=" + this.getSys().getMd() +
			", date=" + this.getDate()+
			", active=" + this.isActive();		
	}
	
}
