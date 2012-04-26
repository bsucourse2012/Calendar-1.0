package com.corsework.notepad.activity;

import com.corsework.notepad.application.NotePadApplication;
import com.corsework.notepad.entities.program.Note;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class SeeNoteActivity extends Activity {
	private TextView mTitleText;
    private TextView mBodyText;
    private TextView mTegsText;
    private TextView mCrText;
    private TextView mMdText;
    private Long mRowId;
    
	protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.see_note);
	        

	        mTitleText = (TextView) findViewById(R.id.title);
	        mBodyText = (TextView) findViewById(R.id.body);
	        mTegsText = (TextView) findViewById(R.id.tegs);
	        mCrText = (TextView) findViewById(R.id.date_text);
	        mMdText = (TextView) findViewById(R.id.date_text2);

	        mRowId = null;
	        Bundle extras = getIntent().getExtras();
	        if (extras != null) {
	        	 mRowId = extras.getLong(NotePadApplication.KEY_ROWID);
	        	 Note n=((NotePadApplication)getApplication()).getNoteD().getById(mRowId);
	        	 mTitleText.setText(n.getTitle());
		         mBodyText.setText(n.getCont());
		         mTegsText.setText(n.getType());
		         mCrText.setText(android.text.format.DateFormat.format("dd-MM-yyyy",n.getSys().getCr()));
		         mMdText.setText(android.text.format.DateFormat.format("dd-MM-yyyy",n.getSys().getMd()));
		         setTitle(n.getTitle());
	        }
	 }
}
