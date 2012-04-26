package com.corsework.notepad.activity;

import com.corsework.notepad.application.NotePadApplication;
import com.corsework.notepad.entities.program.Note;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddNoteActivity extends Activity {
	private EditText mTitleText;
    private EditText mBodyText;
    private Long mRowId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_note);
        
        setTitle(R.string.edit_note);

        mTitleText = (EditText) findViewById(R.id.title);
        mBodyText = (EditText) findViewById(R.id.body);

        Button confirmButton = (Button) findViewById(R.id.confirm);

        mRowId = null;
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
        	 mRowId = extras.getLong(NotePadApplication.KEY_ROWID);
        	 Note n=((NotePadApplication)getApplication()).getNoteD().getById(mRowId);
        	 mTitleText.setText(n.getTitle());
	         mBodyText.setText(n.getCont());
        	//obtain information, if we change a note

        }

        confirmButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                String ttitle =mTitleText.getText().toString();
                String bbody= mBodyText.getText().toString();
                Note n;
                if (mRowId != null) {
                	n=((NotePadApplication)getApplication()).getNoteD().getById(mRowId);
                } else 
                	n=new Note();
                n.setCont(bbody);
                n.setTitle(ttitle);
                n=((NotePadApplication)getApplication()).getNoteD().update(n);
                finish();
            }

        });
    }
}
