package com.corsework.notepad.activity;

import com.corsework.notepad.application.NotePadApplication;
import com.corsework.notepad.entities.program.Reminder;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class SeeReminderActivity extends Activity {
	private static final int MENU_ITEM_DELETE = Menu.FIRST;
	private static final int MENU_ITEM_EDIT_NOTE = Menu.FIRST + 1;
	
	
    private TextView mBodyText;
    private TextView mTegsText;
    private TextView mCrText;
    private TextView mMdText;

    private TextView mAlmText;
    private TextView mStrText;
    private Long mRowId;
	private AlertDialog unsavedChangesDialog;
    
	protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.see_reminder);
	        
	        mBodyText = (TextView) findViewById(R.id.body);
	        mTegsText = (TextView) findViewById(R.id.tegs);
	        mCrText = (TextView) findViewById(R.id.date_text);
	        mMdText = (TextView) findViewById(R.id.date_text2);
	        mStrText = (TextView) findViewById(R.id.date_text3);
	        mAlmText = (TextView) findViewById(R.id.date_text4);

	        mRowId = null;
	        Bundle extras = getIntent().getExtras();
	        if (extras != null) {
	        	 mRowId = extras.getLong(NotePadApplication.KEY_ROWID);
	        	 Reminder n=((NotePadApplication)getApplication()).getReminderD().getById(mRowId);
		         mBodyText.setText(n.getDescr());
		         mTegsText.setText(n.getType());
		         mCrText.setText(android.text.format.DateFormat.format("dd-MM-yyyy",n.getSys().getCr()));
		         mMdText.setText(android.text.format.DateFormat.format("dd-MM-yyyy",n.getSys().getMd()));
		         mStrText.setText(android.text.format.DateFormat.format("hh:mmaa dd-MM-yyyy",n.getStrDate())+" - "+
		        		 android.text.format.DateFormat.format("hh:mmaa dd-MM-yyyy",n.getEndDate()));
		         if (n.getPrior()!=-1)
		        	 mAlmText.setText(android.text.format.DateFormat.format("dd-MM-yyyy",n.getPrior()));
		         else mAlmText.setText(getResources().getString(R.string.none));
		         setTitle(n.getType());
	        }
	        else finish();
	 }
	
	@Override
	protected void onResume() {
		super.onResume();
		 Reminder n=((NotePadApplication)getApplication()).getReminderD().getById(mRowId);
		 if (n!=null){
			 mBodyText.setText(n.getDescr());
			 mTegsText.setText(n.getType());
			 mCrText.setText(android.text.format.DateFormat.format("dd-MM-yyyy",n.getSys().getCr()));
			 mMdText.setText(android.text.format.DateFormat.format("dd-MM-yyyy",n.getSys().getMd()));
			 mStrText.setText(android.text.format.DateFormat.format("hh:mmaa dd-MM-yyyy",n.getStrDate())+" - "+
	        		 android.text.format.DateFormat.format("hh:mmaa dd-MM-yyyy",n.getEndDate()));
	         if (n.getPrior()!=-1)
	        	 mAlmText.setText(android.text.format.DateFormat.format("hh:mmaa dd-MM-yyyy",n.getPrior()));
	         else mAlmText.setText(getResources().getString(R.string.none));
			 setTitle(n.getType());
		 }else finish();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		
		menu.add(0, MENU_ITEM_EDIT_NOTE, 0, R.string.edit_reminder).setShortcut('1', 'i')
			.setIcon(android.R.drawable.ic_menu_add);
		
		menu.add(0, MENU_ITEM_DELETE, 0, R.string.menu_delete).setShortcut('4','d')
		.setIcon(android.R.drawable.ic_menu_delete);

		return true;
	}
	 @Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
	        switch(item.getItemId()) {
	            case MENU_ITEM_EDIT_NOTE:
	            	Intent i = new Intent(this,AddReminderActivity.class);
	        		i.putExtra(NotePadApplication.KEY_ROWID, mRowId);
	        		startActivity(i);
	                return true;
	            case MENU_ITEM_DELETE:
	            	deleteNote();
	            	return true;
	        }

	        return super.onMenuItemSelected(featureId, item);
	    }

	private void deleteNote() {
		unsavedChangesDialog = new AlertDialog.Builder(this)
		.setTitle(R.string.delete_note_information)
		.setMessage(R.string.delete_note_message)
		.setPositiveButton(android.R.string.ok, new AlertDialog.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				Reminder note = ((NotePadApplication)getApplication()).getReminderD().getById(mRowId);
				((NotePadApplication)getApplication()).getReminderD().delete(note);
				((NotePadApplication)getApplication()).getBellD().deleteByRemId(note.getId());
				((NotePadApplication)getApplication()).startNotify(-1);
               finish();
			}
		})
		.setNegativeButton(android.R.string.no, new AlertDialog.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				unsavedChangesDialog.cancel();
			}
		}).create();
	unsavedChangesDialog.show();
	}
}
