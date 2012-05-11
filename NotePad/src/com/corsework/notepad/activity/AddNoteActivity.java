package com.corsework.notepad.activity;

import java.util.ArrayList;
import java.util.List;

import com.corsework.notepad.application.NotePadApplication;
import com.corsework.notepad.entities.dao.DB;
import com.corsework.notepad.entities.program.Note;

import android.view.View.OnClickListener;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class AddNoteActivity extends Activity {
	
	private static final int VOICE_RECOGNITION_REQUEST_CODE = 1234;
	private static final int ADD_TEG_ACT = 2345;

	final int DIALOG_TEGS = 1;
	final int DIALOG_ADD_TEGS = 2;
	
	private TextView mTegText;
	private EditText mTitleText;
    private EditText mBodyText;
    private Long mRowId;
	private AlertDialog unsavedChangesDialog;

	private Button voiceButton;

	private boolean changesPending;

	Cursor cursor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_note);
        
        setTitle(R.string.edit_note);
        changesPending = false;
        mTitleText = (EditText) findViewById(R.id.title);
        mBodyText = (EditText) findViewById(R.id.body);
        mTegText  = (TextView) findViewById(R.id.tegs);

        Button confirmButton = (Button) findViewById(R.id.confirm);
        Button cancelButton = (Button)findViewById(R.id.cancel_button);
        Button tegButton = (Button)findViewById(R.id.button_tegs);
        voiceButton = (Button) findViewById(R.id.voice_notes);
        
        
        mRowId = null;
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
        	 mRowId = extras.getLong(NotePadApplication.KEY_ROWID);
        	 Note n=((NotePadApplication)getApplication()).getNoteD().getById(mRowId);
        	 if (n!=null){
        		mTitleText.setText(n.getTitle());
	         	mBodyText.setText(n.getCont());
	         	mTegText.setText(n.getType());
        	 }

        }
        tegButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(AddNoteActivity.this,AddTegsActivity.class);
				startActivityForResult(intent, ADD_TEG_ACT);
				//showDialog(DIALOG_TEGS);
			}
		});
        confirmButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                addNote();
            }

        });
        cancelButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				cancel();
			}
		});
        mTitleText.addTextChangedListener(new TextWatcher() {
			
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				changesPending=true;
			}
			public void beforeTextChanged(CharSequence s, int start, int count,	int after) {}
			public void afterTextChanged(Editable s) {}
		});
		mBodyText.addTextChangedListener(new TextWatcher() {
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				changesPending=true;
			}
			public void beforeTextChanged(CharSequence s, int start, int count,	int after) {}
			public void afterTextChanged(Editable s) {}
		});
		
		 PackageManager pm = getPackageManager();
		 List<ResolveInfo> activities = pm.queryIntentActivities(
	                new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH), 0);
	        if (activities.size() != 0) {
	            voiceButton.setOnClickListener(new OnClickListener() {
					public void onClick(View v) {
						startVoiceRecognitionActivity();
					}
				});
	        } else {
	            voiceButton.setEnabled(false);
	           // voiceButton.setText(R.string.recognizer_not_present);
	        //	voiceButton.setVisibility(View.GONE);
	        }
    }
    
	private void addNote() {
		String ttitle =mTitleText.getText().toString();
        String bbody= mBodyText.getText().toString();
        String tteg = mTegText.getText().toString();
        Note n;
        if (mRowId != null) {
        	n=((NotePadApplication)getApplication()).getNoteD().getById(mRowId);
        } else 
        	n=new Note();
        n.setCont(bbody);
        n.setTitle(ttitle);
        n.setType(tteg);
        n.getSys().setMd();
        n=((NotePadApplication)getApplication()).getNoteD().update(n);
        finish();
	}
	 protected void cancel() {
			String noteName = mTitleText.getText().toString();
			String noteBody = mBodyText.getText().toString();
		
			if ((changesPending && (!noteName.equals("") ||  !noteBody.equals("")))){
				unsavedChangesDialog = new AlertDialog.Builder(this)
				.setTitle(R.string.unsaved_changes_title)
				.setMessage(R.string.unsaved_changes_message)
				.setPositiveButton(R.string.add_note_button, new AlertDialog.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						addNote();
					}

				})
				.setNeutralButton(R.string.discard, new AlertDialog.OnClickListener(){
					public void onClick(DialogInterface dialog, int which) {
						finish();
					}
				})
				.setNegativeButton(android.R.string.cancel, new AlertDialog.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						unsavedChangesDialog.cancel();
					}
				}).create();
				unsavedChangesDialog.show();
			}else finish();
		}
	 private void startVoiceRecognitionActivity() {
		    Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
		    intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
		            RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
		    intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speech recognition demo");
		    startActivityForResult(intent, VOICE_RECOGNITION_REQUEST_CODE);
		}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		    if (requestCode == VOICE_RECOGNITION_REQUEST_CODE && resultCode == RESULT_OK) {
		        // Fill the list view with the strings the recognizer thought it could have heard
		    	ArrayList<String> matches = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
		      	String resString = "";
		       	for (String s : matches)
		       	{
		       		resString += s + "\t";
		       	}
		 
		         mBodyText.setText(resString);
		         if (resString.length()<20)
		        	 mTitleText.setText(resString);
		         else mTitleText.setText(resString.subSequence(0, 20));
		    }
		    else
		    	if (requestCode == ADD_TEG_ACT && resultCode == RESULT_OK) {
		    		 Bundle extras = data.getExtras();
		    		 String title = extras.getString(DB.TegSQLiteOpenHelper.TEG_TEXT);
		             mTegText.setText(title);
		    	}

		    super.onActivityResult(requestCode, resultCode, data);
	}
}
