package com.corsework.notepad.adapter;

import java.util.ArrayList;

import com.corsework.notepad.activity.R;
import com.corsework.notepad.entities.program.Note;
import com.corsework.notepad.entities.program.Record;
import com.corsework.notepad.view.NoteListItem;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class ListAdapter extends BaseAdapter {
	
	private ArrayList<Note> records;
	private Context context;
	
	public ListAdapter(ArrayList<Note> rec, Context context) {
		super();
		this.records = rec;
		this.context = context;
	}

	public int getCount() {
		return records.size();
	}
	
	public Record getItem(int pos) {
		return (null== records)? null:records.get(pos);
	}

	public long getItemId(int pos) {
		return pos;
	}

	public View getView(int pos, View convertView, ViewGroup parent) {
		NoteListItem nli;
		if (null == convertView){
			nli = (NoteListItem)View.inflate(context, R.layout.notes_item, null);
		}else {
			nli =(NoteListItem)convertView;
		}
		nli.setRecord(records.get(pos));
		return nli;
	}

	public void forceReload(ArrayList<Note> arrayList) {
		records=arrayList;
		notifyDataSetChanged();
	}

}
