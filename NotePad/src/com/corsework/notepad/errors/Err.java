package com.corsework.notepad.errors;

public class Err {
	
	/**
	 * The text of the error.
	 */
	private String text;
	
	public Err(String text) {
		this.text = text;
	}
	
	public String getText() {
		return this.text;
	}

}
