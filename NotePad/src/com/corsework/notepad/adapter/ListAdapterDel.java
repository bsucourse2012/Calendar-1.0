package com.corsework.notepad.adapter;

import java.util.ArrayList;

import com.corsework.notepad.activity.R;
import com.corsework.notepad.entities.program.Note;
import com.corsework.notepad.entities.program.Record;
import com.corsework.notepad.view.NoteListItemDel;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class ListAdapterDel extends BaseAdapter {
	private ArrayList<Note> records;
	private Context context;
	boolean checkAll, bolic;
	
	public ListAdapterDel(ArrayList<Note> rec, Context context) {
		super();
		this.records = rec;
		this.context = context;
		checkAll = false;
		
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
		NoteListItemDel nli;
		if (null == convertView){
			nli = (NoteListItemDel)View.inflate(context,R.layout.notes_item_del, null);
		}else {
			nli =(NoteListItemDel)convertView;
		}
		if (bolic)
			nli.setRecord(records.get(pos),checkAll);
		else nli.setRecord(records.get(pos));
		return nli;
	}

	public void forceReload(ArrayList<Note> arrayList) {
		records=arrayList;
		notifyDataSetChanged();
	}

	public boolean isCheckAll() {
		return checkAll;
	}

	public void setCheckAll(boolean checkAll) {
		this.checkAll = checkAll;
	}

	public boolean isBolic() {
		return bolic;
	}

	public void setBolic(boolean bolic) {
		this.bolic = bolic;
	}
}
