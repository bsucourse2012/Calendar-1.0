package com.corsework.notepad.application;

import java.util.ArrayList;
import java.util.Calendar;

import com.corsework.notepad.entities.dao.NoteDao;
import com.corsework.notepad.entities.dao.ReminderDao;
import com.corsework.notepad.entities.program.Note;
import com.corsework.notepad.entities.program.Record;
import com.corsework.notepad.entities.program.Reminder;

import android.app.Application;

public class NotePadApplication extends Application {

	public static final String KEY_TITLE = "title";
    public static final String KEY_BODY = "body";
    public static final String KEY_ROWID = "_id";

	private NoteDao noteD;
	private ReminderDao reminderD;
	public Calendar calcr;
	
	public void onCreate() {
		super.onCreate();
		calcr = Calendar.getInstance();
		noteD = new NoteDao(this);
		reminderD = new ReminderDao(this);
		
		//считать из базы заметки
	}

	public NoteDao getNoteD() {
		return noteD;
	}

	public void setNoteD(NoteDao noteD) {
		this.noteD = noteD;
	}

	public ReminderDao getReminderD() {
		return reminderD;
	}

	public void setReminderD(ReminderDao reminderD) {
		this.reminderD = reminderD;
	}
}
