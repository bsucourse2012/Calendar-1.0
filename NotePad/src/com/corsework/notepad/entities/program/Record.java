package com.corsework.notepad.entities.program;

public class Record {
	
	/**
	 * Unique identifier of the record
	 * When calling the constructor
	 */
	protected Long id;

	/**
	 * System data of the record (creation and modification dates).
	 */
	protected Sys sys;
  
	/**
	 * Creates new Record.
	 */
	public Record() {
		this.id = null;
		this.sys = new Sys();    
	}
  
	/**
	 * Creates Record from db record.
	 */
	public Record(Long id, Sys sys) {
		this.id = id;
		this.sys = sys;
	}
  
	public Long getId() {
		return this.id;
	}
  
	public Sys getSys() {
		return this.sys;
	}

}
