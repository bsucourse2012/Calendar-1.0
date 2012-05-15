package com.corsework.notepad.adapter;

import java.util.ArrayList;
import com.corsework.notepad.activity.R;
import com.corsework.notepad.entities.program.Note;
import com.corsework.notepad.entities.program.Record;
import com.corsework.notepad.entities.program.Reminder;
import com.corsework.notepad.view.NoteListItem;

import android.R.bool;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class ListAdapter extends BaseAdapter {
	
	private ArrayList<Reminder> recordR;
	private ArrayList<Note> records;
	private Context context;
	Boolean lNote;
	public ListAdapter(ArrayList<Note> rec,ArrayList<Reminder> rem, Context context) {
		super();
		lNote = true;
		this.records = rec;
		this.recordR = rem;
		this.context = context;
		
	}

	public int getCount() {
		if (lNote)
			return records.size();
		return recordR.size();
	}
	
	public Record getItem(int pos) {
		if (lNote)
			return (null== records)? null:records.get(pos);
		return (null== recordR)? null:recordR.get(pos);
	}

	public long getItemId(int pos) {
		return pos;
		
	}

	public View getView(int pos, View convertView, ViewGroup parent) {
		NoteListItem nli;
		if (null == convertView){
			nli = (NoteListItem)View.inflate(context,R.layout.notes_item, null);
		}else {
			nli =(NoteListItem)convertView;
		}
		if (lNote)
		nli.setRecord(records.get(pos));
		else nli.setRecord(recordR.get(pos));
		return nli;
	}

	public void forceReload(ArrayList<Note> arrayList,ArrayList<Reminder> ar,boolean b) {
		records=arrayList;
		recordR = ar;
		lNote = b;
		notifyDataSetChanged();
	}

}
