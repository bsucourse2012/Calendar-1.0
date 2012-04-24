package com.corsework.notepad.entities.program;

import java.util.ArrayList;
import java.util.Date;

import com.corsework.notepad.errors.Err;
import com.corsework.notepad.errors.LastErrors;

public class Reminder extends Record {

	/**
	 * The type of the Reminder. (birthday, meeting, ...)
	 * Mandatory field.
	 */
	private String type;
	private final static int TYPE_MAX_LENGTH = 20;

	/**
	 * The description user adds to Reminder.
	 * Can be null.
	 */
	private String descr;
	private final static int DESCR_MAX_LENGTH = 2000;

	/**
	 * The event beginning date.
	 * Mandatory field.
	 */
	private Date strDate;

	/**
	 * The event finish date.
	 * Can be null.
	 */
	private Date endDate;

	/**
	 * The array of bells to remind user about the event.
	 * Can be null.
	 */
	private ArrayList<Bell> bells;

	/**
	 * The priority of the the Reminder.
	 * Mandatory field.
	 */
	private long prior;
	private final long PRIOR_MIN_VALUE = 1;
	private final long PRIOR_MAX_VALUE = 10;
	private final long PRIOR_DEF_VALUE = 3;
	
	/**
	 * How often reminder should ...
	 */
	private String repetition;
	private final String REPETITION_DEF = "once";
	
	/**
	 * Creates new empty Reminder.
	 */
	public Reminder() {
		super();
		this.type = "";
		this.descr = "";
		this.strDate = new Date( (new Date()).getTime() + 10000 );
		this.endDate = this.strDate;
		this.bells = new ArrayList<Bell>();
		this.prior = PRIOR_DEF_VALUE;
		this.repetition = REPETITION_DEF;
	}
	
	public Reminder(String type, String descr, Date strDate, Date endDate,
			ArrayList<Bell> bells, long prior, String repetition) {
		super();
		this.type = type;
		this.descr = descr;
		this.strDate = strDate;
		this.endDate = endDate;
		this.bells = bells;
		this.prior = prior;
		this.repetition = repetition;
	}
	
	public Reminder(Long id, Sys sys, String type, String descr, Date strDate,
			Date endDate, ArrayList<Bell> bells, long prior, String repetition) {
		super(id, sys);
		this.type = type;
		this.descr = descr;
		this.strDate = strDate;
		this.endDate = endDate;
		this.bells = bells;
		this.prior = prior;
		this.repetition = repetition;
	}
	  
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getDescr() {
		return descr;
	}
	public void setDescr(String descr) {
		this.descr = descr;
	}
	public Date getStrDate() {
		return strDate;
	}
	public void setStrDate(Date strDate) {
		this.strDate = strDate;
		
		if (this.strDate.compareTo(this.endDate) > 0)
			this.endDate = this.strDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
		
		if (this.strDate.compareTo(this.endDate) > 0)
			this.strDate = this.endDate;
	}
	public ArrayList<Bell> getBells() {
		return bells;
	}
	public void setBells(ArrayList<Bell> bells) {
		this.bells = bells;
	}
	public long getPrior() {
		return prior;
	}
	public void setPrior(long prior) {
		this.prior = prior;
	}
	public String getRepetition() {
		return repetition;
	}
	public void setRepetition(String repetition) {
		this.repetition = repetition;
	}
	
}
