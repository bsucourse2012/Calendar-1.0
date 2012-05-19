package com.corsework.notepad.view;

import com.corsework.notepad.activity.R;
import com.corsework.notepad.entities.program.Note;
import com.corsework.notepad.entities.program.Record;
import com.corsework.notepad.entities.program.Reminder;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.CheckedTextView;
import android.widget.ImageView;
import android.widget.TextView;

public class NoteListItemDel extends NoteListItem{
	CheckedTextView titleText;
	boolean isCh;
	
	public NoteListItemDel(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public void setRecord(Record note,boolean ch) {
		this.note = note;
		if (note instanceof Note){
			titleText.setText(((Note) note).getTitle());
			tegsText.setText(((Note) note).getType());
			imView.setImageResource(R.drawable.app_notes);
			isCh = ch;
			titleText.setChecked(isCh);
		} else
			if (note instanceof Reminder){
				String  st = ((Reminder) note).getDescr();
				titleText.setText(st.subSequence(0, min(20,st.length(),st.indexOf("\n")))+"...");
				tegsText.setText(((Reminder) note).getType());
				imView.setImageResource(R.drawable.redhat);
				isCh = ch;
				titleText.setChecked(isCh);
			}
	}
	public void setRecord(Record note) {
		this.note = note;
		if (note instanceof Note){
			titleText.setText(((Note) note).getTitle());
			tegsText.setText(((Note) note).getType());
			imView.setImageResource(R.drawable.app_notes);
			titleText.setChecked(isCh);
		} else
			if (note instanceof Reminder){
				titleText.setText(((Reminder) note).getDescr());
				tegsText.setText(((Reminder) note).getType());
				imView.setImageResource(R.drawable.redhat);
			}
	}

	private int min(int i, int j,int k) {
		int temp=(i<j)?i:j;
		if (k==-1)
			return temp;
		return (temp<k)?temp:k;
	}
	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();
		titleText = (CheckedTextView)findViewById(R.id.title_text);
		tegsText = (TextView)findViewById(R.id.tegs_text);
		imView = (ImageView)findViewById(R.id.imageView1);
	}

	public void click() {
		isCh=!isCh;
		titleText.setChecked(isCh);
	}
}
