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
				bodyText.setText(((Reminder) note).getDescr());
				tegsText.setText(((Reminder) note).getType());
				titleText.setVisibility(INVISIBLE);

				imView.setImageResource(R.drawable.redhat);
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
				bodyText.setText(((Reminder) note).getDescr());
				tegsText.setText(((Reminder) note).getType());
				titleText.setVisibility(INVISIBLE);

				imView.setImageResource(R.drawable.redhat);
			}
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
