package com.corsework.notepad.activity;

import com.corsework.notepad.application.NotePadApplication;
import com.corsework.notepad.entities.program.Note;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddNoteActivity extends Activity {
	

	private static final int MENU_ITEM_DELETE = Menu.FIRST;
	private static final int MENU_ITEM_ADD_TAGS = Menu.FIRST + 2;
	
	private EditText mTitleText;
    private EditText mBodyText;
    private Long mRowId;
	private AlertDialog unsavedChangesDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_note);
        
        setTitle(R.string.edit_note);

        mTitleText = (EditText) findViewById(R.id.title);
        mBodyText = (EditText) findViewById(R.id.body);

        Button confirmButton = (Button) findViewById(R.id.confirm);

        mRowId = null;
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
        	 mRowId = extras.getLong(NotePadApplication.KEY_ROWID);
        	 Note n=((NotePadApplication)getApplication()).getNoteD().getById(mRowId);
        	 if (n!=null){
        		 mTitleText.setText(n.getTitle());
	         	mBodyText.setText(n.getCont());
        	 }

        }

        confirmButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                String ttitle =mTitleText.getText().toString();
                String bbody= mBodyText.getText().toString();
                Note n;
                if (mRowId != null) {
                	n=((NotePadApplication)getApplication()).getNoteD().getById(mRowId);
                } else 
                	n=new Note();
                n.setCont(bbody);
                n.setTitle(ttitle);
                n.getSys().setMd();
                n=((NotePadApplication)getApplication()).getNoteD().update(n);
                finish();
            }

        });
    }
    
    @Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		
		menu.add(0, MENU_ITEM_DELETE, 0, R.string.menu_delete).setShortcut('4','d')
		.setIcon(android.R.drawable.ic_menu_delete);

		menu.add(0, MENU_ITEM_ADD_TAGS, 0, R.string.add_tegs).setIcon(
				android.R.drawable.ic_menu_share).setShortcut('6', 'a');

		return true;
	}
	 @Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
	        switch(item.getItemId()) {
	            
	        }

	        return super.onMenuItemSelected(featureId, item);
	    }

	
}
