package com.corsework.notepad.activity;

import java.util.ArrayList;

import com.corsework.notepad.adapter.ListAdapterDel;
import com.corsework.notepad.application.NotePadApplication;
import com.corsework.notepad.entities.dao.NoteDao;
import com.corsework.notepad.entities.dao.ReminderDao;
import com.corsework.notepad.entities.program.Note;
import com.corsework.notepad.entities.program.Reminder;
import com.corsework.notepad.view.NoteListItemDel;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.ListView;

public class DeleteNoteActivity extends ListActivity {
	private NotePadApplication app;
	private NoteDao noteD;
	private ReminderDao remD;
	private ListAdapterDel adapter;
	private CheckedTextView chechedText;
	private Button delbutton;
	private Button canbutton;
	boolean lookNote;
	ArrayList<Long> arDel;
	
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listview_del);
        arDel = new ArrayList<Long>();
        app=(NotePadApplication)getApplication();
        lookNote = app.isLookNote();
        noteD= app.getNoteD();
        remD = app.getReminderD();
        adapter = new ListAdapterDel(noteD.getAll(),remD.getAll(),this);
        adapter.setlNote(lookNote);
        setListAdapter(adapter);
        chechedText = (CheckedTextView)findViewById(android.R.id.text1);
        delbutton = (Button)findViewById(R.id.delete_button);
        canbutton = (Button)findViewById(R.id.cancel_button);
        
        delbutton.setEnabled(false);
        
        chechedText.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				chechedText.setChecked(!chechedText.isChecked());
				ifCheck();
			}			
		});
        
        delbutton.setOnClickListener(new OnClickListener() {
			
			
			public void onClick(View v) {
				deleteArD();
				arDel.clear();
				adapter.setCheckAll(false);
				adapter.setBolic(true);
				delbutton.setEnabled(false);
				fillData();
			}
		});
        
        canbutton.setOnClickListener(new OnClickListener() {
			
			
			public void onClick(View v) {
				finish();
			}
		});
	}


	
	protected void onResume() {
		super.onResume();
		fillData();
	}
	


	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		adapter.setBolic(false);
		adapter.setCheckAll(false);
		chechedText.setChecked(false);
		((NoteListItemDel)v).click();
		long i = ((NoteListItemDel)v).getRecord().getId();
		if (arDel.contains(i))
			arDel.remove(i);
		else
			arDel.add(i);
		if (arDel.size()!=0)
			delbutton.setEnabled(true);
		else delbutton.setEnabled(false);
	}
		
	private void fillData() {
		if (lookNote)
			adapter.forceReload(noteD.getAll(),new ArrayList<Reminder>(),lookNote);
		else adapter.forceReload(new ArrayList<Note>(),remD.getAll(),lookNote);
	}
	
	private void ifCheck() {
		adapter.setBolic(true);
		adapter.setCheckAll(chechedText.isChecked());
		arDel.clear();
		if (chechedText.isChecked())
			getAllIds();
		delbutton.setEnabled(chechedText.isChecked());
		fillData();
	}

	private void getAllIds() {
		if (lookNote){
		ArrayList<Note> noteL = noteD.getAll();
		for (Note n: noteL)
			arDel.add(n.getId());
		}
		else{
			ArrayList<Reminder> noteL = remD.getAll();
			for (Reminder n: noteL)
				arDel.add(n.getId());
		}
	}
	private void deleteArD() {
		if (lookNote)
			for (Long l: arDel)
				noteD.deleteById(l);
		else
			for (Long l: arDel)
				remD.deleteById(l);
			
	}
}
