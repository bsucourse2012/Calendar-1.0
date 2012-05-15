package com.corsework.notepad.adapter;

import java.util.ArrayList;

import com.corsework.notepad.activity.R;
import com.corsework.notepad.entities.dao.NoteDao;
import com.corsework.notepad.entities.program.Note;
import com.corsework.notepad.entities.program.Record;
import com.corsework.notepad.entities.program.Reminder;
import com.corsework.notepad.view.NoteListItemDel;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class ListAdapterDel extends BaseAdapter {
	private ArrayList<Note> records;

	private ArrayList<Reminder> rem;
	private Context context;
	boolean checkAll, bolic;

	Boolean lNote;
	
	public ListAdapterDel(ArrayList<Note> rec,ArrayList<Reminder> rem, Context context) {
		super();
		this.records = rec;
		this.rem = rem;
		this.context = context;
		checkAll = false;
		
	}
	public int getCount() {
		if (lNote)
			return records.size();
		return rem.size();
	}
	
	public Record getItem(int pos) {
		if (lNote)
			return (null== records)? null:records.get(pos);
		return (null== rem)? null:rem.get(pos);
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
		if (lNote){
			if (bolic)
				nli.setRecord(records.get(pos),checkAll);
			else nli.setRecord(records.get(pos));
		}else{
			if (bolic)
				nli.setRecord(rem.get(pos),checkAll);
			else nli.setRecord(rem.get(pos));
		}
		return nli;
	}

	public void forceReload(ArrayList<Note> arrayList,ArrayList<Reminder> rem,boolean b) {
		records=arrayList;
		this.rem = rem;
		lNote = b;
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
	
	
	public Boolean getlNote() {
		return lNote;
	}
	public void setlNote(Boolean lNote) {
		this.lNote = lNote;
	}
}
