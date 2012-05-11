package com.corsework.notepad.activity;


import com.corsework.notepad.adapter.ListAdapter;
import com.corsework.notepad.application.NotePadApplication;
import com.corsework.notepad.entities.dao.NoteDao;
import com.corsework.notepad.entities.program.Note;
import com.corsework.notepad.view.NoteListItem;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

public class listViewClass extends ListActivity {
	
	private static final int MENU_ITEM_DELETE = Menu.FIRST;
	private static final int MENU_ITEM_INSERT_NOTE = Menu.FIRST + 1;
	private static final int MENU_ITEM_INSERT_REMI = Menu.FIRST + 2;
	private static final int MENU_ITEM_SHARE = Menu.FIRST + 3;
	private static final int MENU_SEARCH = Menu.FIRST + 4;
	private static final int MENU_ITEM_EDIT_TAGS = Menu.FIRST + 5;
	private static final int MENU_SETTINGS = Menu.FIRST + 6;
	private static final int MENU_DISTRIBUTION_START = Menu.FIRST + 100; // MUST BE LAST
	

    private static final int ACTIVITY_CREATE_NOTE=0;
    private static final int ACTIVITY_EDIT=1;

    private static final int ACTIVITY_SEE=3;

	private NotePadApplication app;
	private NoteDao noteD;
	private ListAdapter adapter;
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listview_);
        
        app=(NotePadApplication)getApplication();
        noteD= app.getNoteD();
        adapter = new ListAdapter(noteD.getAll(),this);
        setListAdapter(adapter);
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
		menu.add(0, MENU_ITEM_DELETE, 0, R.string.menu_delete).setShortcut('5', 'd')
		.setIcon(android.R.drawable.ic_menu_delete);
		menu.add(0, MENU_SEARCH, 0, R.string.menu_search).setShortcut('3','s')
		.setIcon(android.R.drawable.ic_menu_search);

		menu.add(0, MENU_SETTINGS, 0, R.string.settings).setIcon(
				android.R.drawable.ic_menu_preferences).setShortcut('9', 's');

		return true;
	}
	 @Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
	        switch(item.getItemId()) {
	            case MENU_ITEM_INSERT_NOTE:
	                createNote();
	                return true;
	            case MENU_ITEM_DELETE:
	            	Intent i = new Intent(this, DeleteNoteActivity.class);
	     	        startActivity(i);
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
	  /*
	  @Override
	    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
	        super.onActivityResult(requestCode, resultCode, intent);
	        if(resultCode==RESULT_OK) {
	        Bundle extras = intent.getExtras();
	        switch(requestCode) {
	            case ACTIVITY_CREATE_NOTE:
	            	if (extras!=null){
	                String title = extras.getString(NotePadApplication.KEY_TITLE);
	                String body = extras.getString(NotePadApplication.KEY_BODY);
	                Note n= new Note();
	                n.setTitle(title);
	                n.setCont(body);
	                n=noteD.create(n);
	                fillData();
	            	}
	                break;
	            case ACTIVITY_EDIT:
	               
	                break;

	            case ACTIVITY_SEE:
	               
	                break;
	        }
	        }
	    }
*/

	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		
		Intent i = new Intent(this,SeeNoteActivity.class);
		i.putExtra(NotePadApplication.KEY_ROWID,((NoteListItem)v).getRecord().getId());
		
		startActivity(i);
	}
		
	private void fillData() {
		adapter.forceReload(noteD.getAll());
	}
}
