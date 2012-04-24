package com.corsework.notepad.entities.program;

import com.corsework.notepad.errors.Err;
import com.corsework.notepad.errors.LastErrors;

public class Note extends Record {

	/**
	 * The title of the Note.
	 */
	private String title;
	private final static int TITLE_MAX_LENGTH = 30;

	/**
	 * The type of the Note. It is used to group notes by type.
	 * It's not a mandatory parameter.
	 * Default value is "".
	 */
	private String type;
	private final static int TYPE_MAX_LENGTH = 20;

	/**
	 * The contents of the note.
	 */
	private String cont;
	private final static int CONT_MAX_LENGTH = 30000;

	/**
	 * Creates empty Note and sets creation time.
	 */
	public Note() {
		super();
		this.title = "";
		this.cont = "";
	    this.type = "";
	}
	
	public Note(String title, String type, String cont) {
		super();
		this.title = title;
		this.type = type;
		this.cont = cont;
	}
	  
	/**
	 * Creates new Note from database;
	 * @param title
	 * @param type
	 * @param cont
	 */
	public Note(Long id, Sys sys, String title, String type, String cont) {
		super(id ,sys);
		this.title = title;
		this.type = type;
		this.cont = cont;
	}
	  
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getCont() {
		return cont;
	}

	public void setCont(String cont) {
		this.cont = cont;
	}

	@Override
	public String toString() {
		return "id=" + this.getId() +
			", cr=" + this.getSys().getCr() +
			", md=" + this.getSys().getMd() +
			", title=" + this.getTitle() +
			", type=" + this.getType() +
			", content=" + this.getCont();
	}
}
