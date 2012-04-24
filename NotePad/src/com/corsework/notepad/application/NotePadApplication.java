package com.corsework.notepad.application;

import java.util.ArrayList;
import java.util.Calendar;

import com.corsework.notepad.entities.program.Record;

import android.app.Application;

public class NotePadApplication extends Application {


	private ArrayList<Record> currentNotes;
	public Calendar calcr;
	public Calendar calmd;
	
	public void onCreate() {
		super.onCreate();
		calcr = Calendar.getInstance();
		calmd = (Calendar)calcr.clone();
		//считать из базы заметки
	}

	public ArrayList<Record> getCurrentNotes() {
		return currentNotes;
	}


	public void setCurrentNotes(ArrayList<Record> currentNotes) {
		this.currentNotes = currentNotes;
	}
}
