package com.corsework.notepad.entities.program;

import java.util.Date;

public class Sys {
	
	/**
	 * The time of creation.
	 */
	private Date cr;

	/**
	 * The time of modification.
	 */
	private Date md;

	/**
	 * Creates new Sys data. cr = current time. md = null.
	 */
	public Sys() {
		this.cr = new Date();
	    this.md = this.cr;
	}
	  
	/**
	 * Creates Sys from database.
	 * @param cr
	 * @param md
	 */
	public Sys(Date cr, Date md) {
		this.cr = cr;
		this.md = md;
	}
	  
	public void setMd() {
	    this.md = new Date();
	}

	public Date getCr() {
	    return cr;
	}

	public void setCr(Date cr) {
	    this.cr = cr;
	}

	public Date getMd() {
	    return md;
	}

	public void setMd(Date md) {
		this.md = md;
	}

}
