package com.corsework.notepad.activity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.corsework.notepad.application.NotePadApplication;
import com.corsework.notepad.entities.dao.DB;
import com.corsework.notepad.entities.program.Reminder;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.speech.RecognizerIntent;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TimePicker;

public class AddReminderActivity extends Activity {
	
	private static final int VOICE_RECOGNITION_REQUEST_CODE = 1234;
	private static final int ADD_TEG_ACT = 2345;
	
	final int DIALOG_TEGS = 1;
	final int DIALOG_ADD_TEGS = 2;
	final int DIALOG_TIMEB = 3;
	final int DIALOG_TIMEE = 4;
	final int DIALOG_DATEB = 5;
	final int DIALOG_DATEE = 6;
	Date srtD,endD;
	
    private EditText mBodyText;
    private Long mRowId;

	protected TextView mTegText;
	private AlertDialog unsavedChangesDialog;

	private Button voiceButton;
	private Button stDButton;
	private Button stTButton;
	private Button enDButton;
	private Button enTButton;
	
	private boolean changesPending;
	Cursor cursor;
	
    
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_reminder);
//
//        //для тегов
//        refreshCursor();
        srtD = new Date();
        endD = new Date((new Date()).getTime() + 10000);
        setTitle(R.string.edit_note);
        changesPending = false;
        mBodyText = (EditText) findViewById(R.id.body);
        mTegText  = (TextView) findViewById(R.id.tegs);

        Button confirmButton = (Button) findViewById(R.id.confirm);
        Button cancelButton = (Button)findViewById(R.id.cancel_button);
        Button tegButton = (Button)findViewById(R.id.button_tegs);
        voiceButton = (Button) findViewById(R.id.voice_notes);
        stDButton = (Button) findViewById(R.id.button_data_beg);
        stTButton = (Button) findViewById(R.id.button_time_beg);
        enDButton = (Button) findViewById(R.id.button_data_end);
        enTButton = (Button) findViewById(R.id.button_time_end);
        
        mRowId = null;
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
        	 mRowId = extras.getLong(NotePadApplication.KEY_ROWID);
        	 Reminder n=((NotePadApplication)getApplication()).getReminderD().getById(mRowId);
        	 if (n!=null){
	         	mBodyText.setText(n.getDescr());
	         	mTegText.setText(n.getType());
	         	srtD = n.getStrDate();
	         	endD = n.getEndDate();
        	 }

        }

        stTButton.setText(srtD.getHours()+":"+srtD.getMinutes());
        enTButton.setText(endD.getHours()+":"+endD.getMinutes());
        stDButton.setText(srtD.getDate()+"/"+srtD.getMonth()+"/"+srtD.getYear());
        enDButton.setText(endD.getDate()+"/"+endD.getMonth()+"/"+endD.getYear());
        confirmButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                addReminder();
            }

        });
        cancelButton.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				cancel();
			}
		});
        
		mBodyText.addTextChangedListener(new TextWatcher() {
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				changesPending=true;
			}
			public void beforeTextChanged(CharSequence s, int start, int count,	int after) {}
			public void afterTextChanged(Editable s) {}
		});
		
		tegButton.setOnClickListener(new OnClickListener() {
			
			
			public void onClick(View v) {
				Intent intent = new Intent(AddReminderActivity.this,AddTegsActivity.class);
				startActivityForResult(intent, ADD_TEG_ACT);
				//showDialog(DIALOG_TEGS);
			}
		});
		stDButton.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				  showDialog(DIALOG_DATEB);
			}
		});
		stTButton.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				  showDialog(DIALOG_TIMEB);
			}
		});
		enDButton.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				  showDialog(DIALOG_DATEE);
			}
		});
		enTButton.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				  showDialog(DIALOG_TIMEE);
			}
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
   
	private void addReminder(){;
        String bbody= mBodyText.getText().toString();
        String tteg = mTegText.getText().toString();
        Reminder n;
        if (mRowId != null) {
        	n=((NotePadApplication)getApplication()).getReminderD().getById(mRowId);
        } else 
        	n=new Reminder();
        n.setDescr(bbody);
        n.setType(tteg);
        n.getSys().setMd();
        n.setEndDate(endD);
        n.setStrDate(srtD);
        n=((NotePadApplication)getApplication()).getReminderD().update(n);
        Log.d("log", n.toString());
        finish();
	}
	 protected void cancel() {
			String noteBody = mBodyText.getText().toString();
			Boolean f=false;
			
			if ((changesPending &&  (!noteBody.equals(""))) || f){
				unsavedChangesDialog = new AlertDialog.Builder(this)
				.setTitle(R.string.unsaved_changes_title)
				.setMessage(R.string.unsaved_changes_message)
				.setPositiveButton(R.string.add_note_button, new AlertDialog.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						addReminder();
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
		 
		    }
		    else //добавляем teg
		    	if (requestCode == ADD_TEG_ACT && resultCode == RESULT_OK) {
		    		 Bundle extras = data.getExtras();
		    		 String title = extras.getString(DB.TegSQLiteOpenHelper.TEG_TEXT);
		    		 mTegText.setText(title);
		    	}
		 
		    super.onActivityResult(requestCode, resultCode, data);
		}
		
		 protected Dialog onCreateDialog(int id) {
			    switch (id) {
			    case DIALOG_TIMEB: 
			        TimePickerDialog tpd = new TimePickerDialog(this,new OnTimeSetListener() {
					    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
					    	srtD.setHours(hourOfDay);
					    	srtD.setMinutes(minute);
					    	stTButton.setText(srtD.getHours()+":"+srtD.getMinutes());
						    }
						  } ,srtD.getHours(),srtD.getMinutes(), true);
			        return tpd;
			        
				case DIALOG_TIMEE: 
			        TimePickerDialog tpd2 = new TimePickerDialog(this,new OnTimeSetListener() {
					    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
					    	endD.setHours(hourOfDay);
					    	endD.setMinutes(minute);
					    	enTButton.setText(endD.getHours()+":"+endD.getMinutes());
						    }
						  } ,endD.getHours(),endD.getMinutes(), true);
			        return tpd2;
			        
			    case DIALOG_DATEB: 
			    	 DatePickerDialog dpd = new DatePickerDialog(this,new OnDateSetListener() {

			    		    public void onDateSet(DatePicker view, int year, int monthOfYear,
			    		        int dayOfMonth) {
			    		    	srtD.setYear(year);
			    		    	srtD.setMonth(monthOfYear);
			    		    	srtD.setDate(dayOfMonth);
			    		    	stDButton.setText(srtD.getDate()+"/"+srtD.getMonth()+"/"+srtD.getYear());
			    		    }
			    		    } , srtD.getYear()+1900, srtD.getMonth(),srtD.getDate());
			    	 return dpd;
					
			    case DIALOG_DATEE: 
			    	 DatePickerDialog dpd2 = new DatePickerDialog(this,new OnDateSetListener() {

			    		    public void onDateSet(DatePicker view, int year, int monthOfYear,
			    		        int dayOfMonth) {
			    		    	endD.setYear(year);
			    		    	endD.setMonth(monthOfYear);
			    		    	endD.setDate(dayOfMonth);
			    		    	enDButton.setText(endD.getDate()+"/"+endD.getMonth()+"/"+endD.getYear());
			    		    }
			    		    } , endD.getYear()+1900, endD.getMonth(),endD.getDate());
			    	 return dpd2;
			    	 
//			    case DIALOG_TEGS:
//				    AlertDialog.Builder adb = new AlertDialog.Builder(this);
//				    adb.setTitle(R.string.add_new_tegs);
//				    adb.setCursor(cursor, myClickListener,DB.TegSQLiteOpenHelper.TEG_TEXT);
//				    
//				    adb.setPositiveButton(android.R.string.ok, myClickListener);
//				    adb.setNegativeButton(android.R.string.cancel,myClickListener);
//				    adb.setNeutralButton(R.string.add_new_tegs, myClickListener);
//				    return adb.create();
				    
//				  //диалог для добавления нового типа
//			    case DIALOG_ADD_TEGS:
//			    	LayoutInflater infl = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//		            final View addTegsDialogLayout = infl.inflate(R.layout.add_tegs, (ViewGroup) findViewById(R.id.root));
//
//		            AlertDialog.Builder tegDialogBuilder = new AlertDialog.Builder(this);
//		            tegDialogBuilder.setView(addTegsDialogLayout);
//		            final EditText tegsText = (EditText) addTegsDialogLayout.findViewById(R.id.title);
//		            
//		            tegDialogBuilder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
//		                public void onClick(DialogInterface dialog, int which) {
//		                    String tegText = tegsText.getText().toString();
//		                    if (tegText != null && tegText.length() > 0) {
//		                        //add tegs to array
//		                    	mTegText.setText(tegText);
//		                    	((NotePadApplication)getApplication()).getDbc().addTeg(new Teg(tegText));
//		                    	refreshCursor();
//		                    }
//		                }
//		            });
//		           
//		            tegDialogBuilder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
//		                public void onClick(DialogInterface dialog, int which) {
//		                    finish();
//		                }
//		            });
//		            return tegDialogBuilder.create();
//			    
			    }
			  return super.onCreateDialog(id);
			  }
		 protected void onPrepareDialog(int id, Dialog dialog) {
			   
			    switch (id) {
			    case DIALOG_DATEB:
					((DatePickerDialog)dialog).updateDate(srtD.getYear(),srtD.getMonth(),srtD.getDate());
					return;
			    case DIALOG_DATEE:
					((DatePickerDialog)dialog).updateDate(endD.getYear(),endD.getMonth(),endD.getDate());
					return;
			    case DIALOG_TIMEB:
					((TimePickerDialog)dialog).updateTime(srtD.getHours(),srtD.getMinutes());
					return;
			    case DIALOG_TIMEE:
					((TimePickerDialog)dialog).updateTime(endD.getHours(),endD.getMinutes());
					return;
//			    case DIALOG_TEGS:
//			    	ListAdapter lAdapter =((AlertDialog) dialog).getListView().getAdapter();
//			    	if (lAdapter instanceof CursorAdapter) {
//			            // преобразование и обновление курсора        
//			            CursorAdapter cAdapter = (CursorAdapter) lAdapter;
//			            cAdapter.changeCursor(cursor);
//			      }
//			      break;
//			    case DIALOG_ADD_TEGS:
//			    	return;
			    default:
			      break;
			    }
			  };
//			  // обработчик нажатия на пункт списка диалога или кнопку
//		 android.content.DialogInterface.OnClickListener myClickListener = new  android.content.DialogInterface.OnClickListener() {
//			    public void onClick(DialogInterface dialog,int which) {
//			      ListView lv = ((AlertDialog) dialog).getListView();
//			      if (which == Dialog.BUTTON_NEUTRAL){
//			    	  Intent intent = new Intent(AddReminderActivity.this,AddNewTegsActivity.class);
//					  startActivityForResult(intent, ADD_TEG_ACT);
//			    	  //showDialog(DIALOG_ADD_TEGS);
//			      }else
//			      if (which == Dialog.BUTTON_POSITIVE) {
//			    	  mTegText.setText(((NotePadApplication)getApplication()).getDbc().curTeg.get(lv.getCheckedItemPosition()));
//			      }
//			      
//			    }
//
//
//		};
//		void refreshCursor() {
//			    stopManagingCursor(cursor);
//			    cursor = ((NotePadApplication)getApplication()).getDbc().getAllData();
//			    startManagingCursor(cursor);
//		}
}
