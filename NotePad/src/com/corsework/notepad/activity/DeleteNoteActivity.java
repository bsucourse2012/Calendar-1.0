package com.corsework.notepad.activity;

import java.util.ArrayList;

import com.corsework.notepad.adapter.ListAdapterDel;
import com.corsework.notepad.application.NotePadApplication;
import com.corsework.notepad.entities.dao.NoteDao;
import com.corsework.notepad.entities.program.Note;
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
	private ListAdapterDel adapter;
	private CheckedTextView chechedText;
	private Button delbutton;
	private Button canbutton;
	ArrayList<Long> arDel;
	
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listview_del);
        
        arDel = new ArrayList<Long>();
        app=(NotePadApplication)getApplication();
        noteD= app.getNoteD();
        adapter = new ListAdapterDel(noteD.getAll(),this);
        setListAdapter(adapter);
        chechedText = (CheckedTextView)findViewById(android.R.id.text1);
        delbutton = (Button)findViewById(R.id.delete_button);
        canbutton = (Button)findViewById(R.id.cancel_button);
        
        delbutton.setEnabled(false);
        
        chechedText.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				chechedText.setChecked(!chechedText.isChecked());
				ifCheck();
			}			
		});
        
        delbutton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				deleteArD();
				arDel.clear();
				adapter.setCheckAll(false);
				adapter.setBolic(false);
				delbutton.setEnabled(false);
				fillData();
			}
		});
        
        canbutton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}


	@Override
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
		adapter.forceReload(noteD.getAll());
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
		ArrayList<Note> noteL = noteD.getAll();
		for (Note n: noteL)
			arDel.add(n.getId());
	}
	private void deleteArD() {
		for (Long l: arDel)
			noteD.deleteById(l);
	}
}
