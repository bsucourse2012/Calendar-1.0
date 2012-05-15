package com.corsework.notepad.application;

import java.util.Calendar;

import com.corsework.notepad.entities.dao.DB;
import com.corsework.notepad.entities.dao.NoteDao;
import com.corsework.notepad.entities.dao.ReminderDao;

import android.app.Application;
import android.util.Log;
import android.widget.TabHost;

public class NotePadApplication extends Application {

	public static final String KEY_TITLE = "title";
    public static final String KEY_BODY = "body";
    public static final String KEY_ROWID = "_id";
	public static final String KEY_CAL = "calendar";
	public static final String KEY_SRTD = "startd";
	public static final String KEY_FIND = "finishd";

	private NoteDao noteD;
	private ReminderDao reminderD;
	public Calendar calcr;
	public TabHost tabH;
	boolean lookNote;
	
	private DB dbc;
	
	public void onCreate() {
		super.onCreate();
		calcr = Calendar.getInstance();

		Log.d("log cald", android.text.format.DateFormat.format("hh:mm dd-MM-yyyy",calcr).toString());
		noteD = new NoteDao(this);
		reminderD = new ReminderDao(this);
		lookNote = true;
		
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

	public TabHost getTabH() {
		return tabH;
	}

	public void setTabH(TabHost tabH) {
		this.tabH = tabH;
	}

	public boolean isLookNote() {
		return lookNote;
	}

	public void setLookNote(boolean lookNote) {
		this.lookNote = lookNote;
	}
}
