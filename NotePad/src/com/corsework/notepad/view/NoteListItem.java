package com.corsework.notepad.view;

import com.corsework.notepad.activity.R;
import com.corsework.notepad.entities.program.Note;
import com.corsework.notepad.entities.program.Record;
import com.corsework.notepad.entities.program.Reminder;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class NoteListItem extends RelativeLayout {
	
	private Record note;
	private TextView dateText;
	private TextView dateText2;
	private TextView titleText;
	private TextView bodyText;
	private TextView tegsText;
	private ImageView imView;
	
	public NoteListItem(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public void setRecord(Record note) {
		this.note = note;
		if (note instanceof Note){
			titleText.setText(((Note) note).getTitle());
			bodyText.setText(((Note) note).getCont());
			tegsText.setText(((Note) note).getType());
			imView.setImageResource(R.drawable.app_notes);
		} else
			if (note instanceof Reminder){
				bodyText.setText(((Reminder) note).getDescr());
				tegsText.setText(((Reminder) note).getType());
				titleText.setVisibility(INVISIBLE);

				imView.setImageResource(R.drawable.redhat);
			}
		dateText.setText(android.text.format.DateFormat.format("dd-MM-yyyy", note.getSys().getCr()));
		dateText2.setText(android.text.format.DateFormat.format("dd-MM-yyyy", note.getSys().getMd()));
	}

	public Record getNote() {
		return note;
	}

	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();
		dateText = (TextView)findViewById(R.id.date_text);
		dateText2 = (TextView)findViewById(R.id.date_text2);
		titleText = (TextView)findViewById(R.id.title_text);
		bodyText = (TextView)findViewById(R.id.somebody_text);
		tegsText = (TextView)findViewById(R.id.tegs_text);
		imView = (ImageView)findViewById(R.id.imageView1);
	}
}

