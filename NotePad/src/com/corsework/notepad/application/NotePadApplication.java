package com.corsework.notepad.application;

import java.util.ArrayList;
import java.util.Calendar;

import com.corsework.notepad.activity.TimeNotification;
import com.corsework.notepad.entities.dao.BellDao;
import com.corsework.notepad.entities.dao.TagDao;
import com.corsework.notepad.entities.dao.NoteDao;
import com.corsework.notepad.entities.dao.ReminderDao;
import com.corsework.notepad.entities.program.Bell;
import com.corsework.notepad.entities.program.Reminder;

import android.app.AlarmManager;
import android.app.Application;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.TabHost;

public class NotePadApplication extends Application {
	public static final String NOTE_PREFERENCES="NotePrefs"; 
	public static final String NOTE_PREFERENCES_PASSWORD = "Password";
	   
	public static final String KEY_TITLE = "title";
    public static final String KEY_BODY = "body";
    public static final String KEY_ROWID = "_id";
	public static final String KEY_CAL = "calendar";
	public static final String KEY_SRTD = "startd";
	public static final String KEY_FIND = "finishd";

	private NoteDao noteD;
	private ReminderDao reminderD;
	private BellDao bellD;
	public Calendar calcr;
	public TabHost tabH;
	boolean lookNote;

	private AlarmManager am;
    
	private TagDao dbc;
	
	public void onCreate() {
		super.onCreate();
		
		calcr = Calendar.getInstance();
		calcr.setFirstDayOfWeek(Calendar.SUNDAY);
		Log.d("create", android.text.format.DateFormat.format("hh:mmaa dd-MM-yyyy",calcr).toString());
		noteD = new NoteDao(this);
		reminderD = new ReminderDao(this);
		bellD = new BellDao(this);
		lookNote = true;
		
        setDbc(new TagDao(this));
		
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

	public BellDao getBellD() {
		return bellD;
	}

	public void setBellD(BellDao bellD) {
		this.bellD = bellD;
	}
	
	public TagDao getDbc() {
		return dbc;
	}

	public void setDbc(TagDao dbc) {
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
	public void startNotify(long ids){//(Calendar cal,long pos){
		
		if (ids!=-1){
			Bell b=bellD.getById(ids);
       	 	b.setActive(false);
       	 	bellD.update(b);
		}
//		Bell b = bellD.getByRemId(pos);
//		if (b == null){
//			b= new Bell(cal.getTimeInMillis(),pos,true);
//			b = bellD.create(b);
//		}else{
//			b.setActive(true);
//			b.setDate(cal.getTimeInMillis());
//			b = bellD.update(b);
//		}
		Bell b = bellD.getNextBell(); 
		if (b==null) return;
		Log.d("bell getById", b.toString());        
		
		Reminder rem = reminderD.getById(b.getIdrem());
		
		am  = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
		Intent intent = new Intent(this, TimeNotification.class);
		PendingIntent pi = PendingIntent.getBroadcast(this, 0, intent, 
				PendingIntent.FLAG_CANCEL_CURRENT);
		am.cancel(pi);
		if (rem==null) return;
	
		intent.putExtra(KEY_ROWID, rem.getId());
		intent.putExtra(KEY_TITLE, rem.getType());
		intent.putExtra(KEY_BODY, rem.getDescr());
		intent.putExtra(KEY_SRTD, b.getId());
		pi = PendingIntent.getBroadcast(this, 0, intent, 
				PendingIntent.FLAG_CANCEL_CURRENT);
		am.cancel(pi);
		Log.d("add to time",android.text.format.DateFormat.format("hh:mm  dd-MM-yyyy",b.getDate()).toString());
		am.set(AlarmManager.RTC_WAKEUP, b.getDate(), pi);
	}

}
