package com.corsework.notepad.application;

import java.util.Calendar;

import com.corsework.notepad.entities.dao.DB;
import com.corsework.notepad.entities.dao.NoteDao;
import com.corsework.notepad.entities.dao.ReminderDao;

import android.app.Application;

public class NotePadApplication extends Application {

	public static final String KEY_TITLE = "title";
    public static final String KEY_BODY = "body";
    public static final String KEY_ROWID = "_id";

	private NoteDao noteD;
	private ReminderDao reminderD;
	public Calendar calcr;

	private DB dbc;
	
	public void onCreate() {
		super.onCreate();
		calcr = Calendar.getInstance();
		noteD = new NoteDao(this);
		reminderD = new ReminderDao(this);

        setDbc(new DB(this));
		
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

	public DB getDbc() {
		return dbc;
	}

	public void setDbc(DB dbc) {
		this.dbc = dbc;
	}
}
