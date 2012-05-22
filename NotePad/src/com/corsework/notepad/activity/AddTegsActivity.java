package com.corsework.notepad.activity;
import com.corsework.notepad.adapter.TegAdapter;
import com.corsework.notepad.application.NotePadApplication;
import com.corsework.notepad.entities.dao.DB;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

public class AddTegsActivity extends ListActivity {
	private static final int MENU_ITEM_DELETE = Menu.FIRST;

	final int DIALOG_ADD_TEGS = 2;

	DB tegD;
	private TegAdapter adapter;
	
	
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_tegs2);
        
        tegD= ((NotePadApplication) getApplication()).getDbc();
        adapter = new TegAdapter(tegD.getAll(),this);
        setListAdapter(adapter);
        Button addButton = (Button)findViewById(R.id.addBut);
		Button okButton = (Button)findViewById(R.id.okBut);
		Button cnButton = (Button)findViewById(R.id.cansBut);
		String s;
		Bundle extras = getIntent().getExtras();
        if (extras != null) {
        	s= extras.getString(NotePadApplication.KEY_ROWID);
        	int k=-1;
        	if ((k=tegD.getAll().indexOf(s))!=-1)
        		adapter.togglePosition(k);
        }
		okButton.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				Bundle bundle = new Bundle();
				if (adapter.getTogglePosition()!=-1){
					bundle.putString(DB.TegSQLiteOpenHelper.TEG_TEXT, adapter.getPos());  
				}else
					bundle.putString(DB.TegSQLiteOpenHelper.TEG_TEXT, "");
				Intent mIntent = new Intent();
	            mIntent.putExtras(bundle);
	            setResult(RESULT_OK, mIntent);
				finish();
			}
		});
		cnButton.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				finish();
			}
		});
		addButton.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				showDialog(DIALOG_ADD_TEGS);
			}
		});
    }
    
	protected void onResume() {
		super.onResume();
		fillData();
	}
    protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		adapter.togglePosition(position);
		
	}
	
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		
		menu.add(0, MENU_ITEM_DELETE, 0, R.string.menu_delete).setShortcut('5', 'd')
		.setIcon(android.R.drawable.ic_menu_delete);
		
		return true;
	}
	 
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
	        switch(item.getItemId()) {
	             case MENU_ITEM_DELETE:
	            	if (adapter.getTogglePosition()!=-1){
	            		tegD.delete(adapter.getPos());
	            		adapter.forceReload(tegD.getAll());
	            	}
	            	return true;
	          }
	        return super.onMenuItemSelected(featureId, item);
	    }

	 private void fillData() {
			adapter.forceReload(tegD.getAll());
		}
	 protected Dialog onCreateDialog(int id) {
		    switch (id) {
			//диалог для добавления нового типа
		    case DIALOG_ADD_TEGS:
		    	LayoutInflater infl = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	            final View addTegsDialogLayout = infl.inflate(R.layout.add_tegs_new, (ViewGroup) findViewById(R.id.rootteg));

	            AlertDialog.Builder tegDialogBuilder = new AlertDialog.Builder(this);
	            tegDialogBuilder.setView(addTegsDialogLayout);
	            final EditText tegsText = (EditText) addTegsDialogLayout.findViewById(R.id.teg_new);
	    		
	            tegDialogBuilder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
	                public void onClick(DialogInterface dialog, int which) {
	                    String tegText = tegsText.getText().toString();
	                    if (tegText != null && tegText.length() > 0) {
	                    	tegD.addTeg(tegText);
	                    	adapter.forceReload(tegD.getAll());
	                    	
	                    }
	                }
	            });
	            tegDialogBuilder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
	                public void onClick(DialogInterface dialog, int which) {
	                    finish();
	                }
	            });
	            return tegDialogBuilder.create();
		    
		    }
		    return super.onCreateDialog(id);
		  }
}
