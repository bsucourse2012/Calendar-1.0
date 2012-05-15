package com.corsework.notepad.activity;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import com.corsework.notepad.adapter.ListAdapter;
import com.corsework.notepad.application.NotePadApplication;
import com.corsework.notepad.entities.dao.DB;
import com.corsework.notepad.entities.dao.NoteDao;
import com.corsework.notepad.entities.dao.ReminderDao;
import com.corsework.notepad.entities.program.Note;
import com.corsework.notepad.entities.program.Reminder;
import com.corsework.notepad.view.NoteListItem;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

public class DayListViewClass extends ListActivity {
	
	private static final int MENU_ITEM_INSERT_NOTE = Menu.FIRST ;
	private static final int MENU_ITEM_INSERT_REMI = Menu.FIRST + 1;

	private NotePadApplication app;
	private ReminderDao remD;
	private NoteDao noteD;
	DB tegD;
	private ListAdapter adapter;
	int status;
	boolean lN;
	Date  dat,dat2;
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listview_);
        Calendar cal = Calendar.getInstance();
        status = 0;
        lN = ((NotePadApplication)getApplication()).isLookNote();
        Bundle exstra =  getIntent().getExtras();
        if (exstra!=null){
        	if (exstra.containsKey(NotePadApplication.KEY_SRTD)){
        		cal.setTimeInMillis(exstra.getLong(NotePadApplication.KEY_SRTD));
        		status = 1; lN= false;
        	}
        	else status = 2;
        }
        dat = cal.getTime();
        dat2 = cal.getTime();
        dat.setHours(0);
    	dat.setMinutes(0);
    	dat.setSeconds(0);
    	dat2.setHours(23);
    	dat2.setMinutes(59);
    	dat2.setSeconds(59);
        app=(NotePadApplication)getApplication();
        remD = app.getReminderD();
        noteD = app.getNoteD();
        tegD = app.getDbc();
        adapter = new ListAdapter(getByStatusT(),getByStatusF(),this);
        setListAdapter(adapter);
	}

	private ArrayList<Reminder> getByStatusF() {
		if (!lN){
			switch (status){
			case 1:
				return remD.getByStEndDate(dat,dat2);
			case 2: 
					return remD.getByType(tegD.getAllCh());
			default: return new ArrayList<Reminder>();
		}
		}
		else
			return new ArrayList<Reminder>();
	}

	private ArrayList<Note> getByStatusT() {
		if (lN){
			switch (status){
			case 2: 
					return noteD.getByType(tegD.getAllCh());
			default: return new ArrayList<Note>();
		}
		}
		else		return new ArrayList<Note>();
	}

	@Override
	protected void onResume() {
		super.onResume();

		fillData();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		menu.add(0, MENU_ITEM_INSERT_NOTE, 0, R.string.add_note_button).setShortcut('1', 'i')
			.setIcon(android.R.drawable.ic_menu_add);
		menu.add(0, MENU_ITEM_INSERT_REMI, 0, R.string.add_reminder).setShortcut('2', 'u')
		.setIcon(android.R.drawable.ic_menu_call);

		return true;
	}
	
	 @Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
	        switch(item.getItemId()) {
	        	case MENU_ITEM_INSERT_NOTE:
	                createNote();
	                return true;
	            case MENU_ITEM_INSERT_REMI:
	                createReminder();
	                return true;
	        }

	        return super.onMenuItemSelected(featureId, item);
	    }


	private void createReminder() {
		Intent i = new Intent(this, AddReminderActivity.class);
        startActivity(i);
	}

	private void createNote() {
	        Intent i = new Intent(this, AddNoteActivity.class);
	        startActivity(i);
	  }


	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		if (lN){
			Intent i = new Intent(this,SeeNoteActivity.class);
			i.putExtra(NotePadApplication.KEY_ROWID,((NoteListItem)v).getRecord().getId());
			startActivity(i);
		}else{
			Intent i = new Intent(this,SeeReminderActivity.class);
			i.putExtra(NotePadApplication.KEY_ROWID,((NoteListItem)v).getRecord().getId());
			startActivity(i);
		}
	}
		
	private void fillData() {
		adapter.forceReload(getByStatusT(),getByStatusF(),lN);
	}
}
