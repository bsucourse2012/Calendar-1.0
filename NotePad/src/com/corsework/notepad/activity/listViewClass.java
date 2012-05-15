package com.corsework.notepad.activity;


import java.util.ArrayList;

import com.corsework.notepad.adapter.ListAdapter;
import com.corsework.notepad.application.NotePadApplication;
import com.corsework.notepad.entities.dao.DB;
import com.corsework.notepad.entities.dao.NoteDao;
import com.corsework.notepad.entities.dao.ReminderDao;
import com.corsework.notepad.entities.program.Note;
import com.corsework.notepad.entities.program.Reminder;
import com.corsework.notepad.view.NoteListItem;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ListActivity;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CursorAdapter;
import android.widget.ListView;

public class listViewClass extends ListActivity {
	
	private static final int MENU_ITEM_DELETE = Menu.FIRST;
	private static final int MENU_ITEM_INSERT_NOTE = Menu.FIRST + 1;
	private static final int MENU_ITEM_INSERT_REMI = Menu.FIRST + 2;
	private static final int MENU_ITEM_SHARE = Menu.FIRST + 3;
	private static final int MENU_SEARCH = Menu.FIRST + 4;
	private static final int MENU_SETTINGS = Menu.FIRST + 6;
	private static final int MENU_DISTRIBUTION_START = Menu.FIRST + 100; // MUST BE LAST
	

	private static final int ADD_TEG_ACT = 2346;

	final int DIALOG_TEGS = 10;

	private NotePadApplication app;
	private NoteDao noteD;
	private ReminderDao remD;
	private ListAdapter adapter;
	boolean lookNote;
	Cursor cursor;
	DB tegD;
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listview_);
        
        app=(NotePadApplication)getApplication();
        noteD= app.getNoteD();
        remD = app.getReminderD();
        tegD = app.getDbc();
        
        lookNote=app.isLookNote();
        adapter = new ListAdapter(noteD.getAll(),remD.getAll(),this);
        setListAdapter(adapter);

        refreshCursor();
	}

	@Override
	protected void onResume() {
		super.onResume();

		lookNote = app.isLookNote();
		fillData();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		if (lookNote)
			menu.add(0, MENU_ITEM_SHARE, 0, R.string.show_reminder).setShortcut('5', 'r')
		.setIcon(android.R.drawable.ic_menu_my_calendar);
		else 
			menu.add(0, MENU_ITEM_SHARE, 0, R.string.show_note).setShortcut('5', 'r')
			.setIcon(android.R.drawable.ic_menu_my_calendar);
		menu.add(0, MENU_ITEM_INSERT_NOTE, 0, R.string.add_note_button).setShortcut('1', 'i')
			.setIcon(android.R.drawable.ic_menu_add);
		menu.add(0, MENU_ITEM_INSERT_REMI, 0, R.string.add_reminder).setShortcut('2', 'u')
		.setIcon(android.R.drawable.ic_menu_call);
		menu.add(0, MENU_ITEM_DELETE, 0, R.string.menu_delete).setShortcut('5', 'd')
		.setIcon(android.R.drawable.ic_menu_delete);
		menu.add(0, MENU_SEARCH, 0, R.string.menu_search_by_teg).setShortcut('3','s')
		.setIcon(android.R.drawable.ic_menu_search);

		menu.add(0, MENU_SETTINGS, 0, R.string.settings).setIcon(
				android.R.drawable.ic_menu_preferences).setShortcut('9', 's');

		return true;
	}
	 @Override
	    public boolean onPrepareOptionsMenu(Menu menu) {
	      // TODO Auto-generated method stub
		 MenuItem mi =  menu.findItem(MENU_ITEM_SHARE);
		 if (lookNote)
			 mi.setTitle(R.string.show_reminder);
		 else 
			 mi.setTitle(R.string.show_note);
				
	      return super.onPrepareOptionsMenu(menu);
	    }
	 @Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
	        switch(item.getItemId()) {
	        	case MENU_ITEM_SHARE:
	        		lookNote = !lookNote;
	        		fillData();
	        		return true;
	            case MENU_ITEM_INSERT_NOTE:
	            	app.setLookNote(lookNote);
	                createNote();
	                return true;
	            case MENU_ITEM_DELETE:
	            	app.setLookNote(lookNote);
	            	Intent i = new Intent(this, DeleteNoteActivity.class);
	     	        startActivity(i);
	            	return true;
	            case MENU_ITEM_INSERT_REMI:
	            	app.setLookNote(lookNote);
	                createReminder();
	                return true;
	            case MENU_SEARCH:
	            	app.setLookNote(lookNote);
	        		tegD.unChAll();
	        		refreshCursor();
	        		showDialog(DIALOG_TEGS);
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
	 protected Dialog onCreateDialog(int id) {
		    switch (id) {
		    case DIALOG_TEGS:
			    AlertDialog.Builder adb = new AlertDialog.Builder(this);
			    adb.setTitle(R.string.add_new_tegs);
			    adb.setMultiChoiceItems(cursor, DB.TegSQLiteOpenHelper.TEG_CHK, DB.TegSQLiteOpenHelper.TEG_TEXT, myCursorMultiClickListener);
			    adb.setPositiveButton(android.R.string.ok, myClickListener);
			    adb.setNegativeButton(android.R.string.cancel,myClickListener);
			    return adb.create();

		    }
			  return super.onCreateDialog(id);
			  }
	 @Override
	protected void onPrepareDialog(int id, Dialog dialog) {
		 switch (id) {
		      case DIALOG_TEGS:
		    	  CursorAdapter lAdapter = (CursorAdapter) ((AlertDialog) dialog).getListView().getAdapter();
		    	   CursorAdapter cAdapter = (CursorAdapter) lAdapter;
		           cAdapter.changeCursor(cursor);
		      
		      break;
		    	
		    }
		super.onPrepareDialog(id, dialog);
	}
	android.content.DialogInterface.OnClickListener myClickListener = new  android.content.DialogInterface.OnClickListener() {
		    public void onClick(DialogInterface dialog,int which) {
		     
		      if (which == Dialog.BUTTON_POSITIVE) {
		    	  Intent i = new Intent(listViewClass.this, DayListViewClass.class);
		    	  i.putExtra(NotePadApplication.KEY_FIND, "2");
		          startActivity(i);
		      }
		      
		    }


	};
	android.content.DialogInterface.OnMultiChoiceClickListener myCursorMultiClickListener = new android.content.DialogInterface.OnMultiChoiceClickListener() {
		    @Override
		    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
		      ListView lv = ((AlertDialog) dialog).getListView();
		      Log.d("log tag", "which = " + which + ", isChecked = " + isChecked);
		      tegD.changeRec(which, isChecked);
		        refreshCursor();
		        CursorAdapter cAdapter = (CursorAdapter) lv.getAdapter();
		        cAdapter.changeCursor(cursor);
		    }
		  };
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
    	app.setLookNote(lookNote);
		if (lookNote){
		Intent i = new Intent(this,SeeNoteActivity.class);
		i.putExtra(NotePadApplication.KEY_ROWID,((NoteListItem)v).getRecord().getId());
		startActivity(i);
		}
		else{
			Intent i = new Intent(this,SeeReminderActivity.class);
			i.putExtra(NotePadApplication.KEY_ROWID,((NoteListItem)v).getRecord().getId());
			startActivity(i);
		}
	}
		
	private void fillData() {
		if (lookNote)
			adapter.forceReload(noteD.getAll(),new ArrayList<Reminder>(),lookNote);
		else adapter.forceReload(new ArrayList<Note>(),remD.getAll(),lookNote);
	}
	void refreshCursor() {
	    stopManagingCursor(cursor);
	    cursor = tegD.getAllData();
	    startManagingCursor(cursor);
	  }
}
