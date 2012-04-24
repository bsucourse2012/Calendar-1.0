package com.corsework.notepad.errors;

import android.util.Log;

public class Err {
	
	/**
	 * The text of the error.
	 */
	private String text;
	
	public Err(String text) {
		this.text = text;
		Log.d("error!!!", text);
	}
	
	public String getText() {
		return this.text;
	}

}
