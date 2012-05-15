package com.corsework.notepad.activity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.corsework.notepad.application.NotePadApplication;
import com.corsework.notepad.entities.dao.DB;
import com.corsework.notepad.entities.program.Reminder;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TextView;
import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.Context;
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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TimePicker;

public class AddReminderActivity extends Activity {
	
	private static final int VOICE_RECOGNITION_REQUEST_CODE = 1234;
	private static final int ADD_TEG_ACT = 2345;
	
	final int DIALOG_TEGS = 1;
	final int DIALOG_SGNL = 2;
	final int DIALOG_TIMEB = 3;
	final int DIALOG_TIMEE = 4;
	final int DIALOG_DATEB = 5;
	final int DIALOG_DATEE = 6;
	final int DIALOG_ALARM = 7;
	Date srtD,endD;//,alarmD;
	Calendar alarmD;
	int alarmItem;
    private EditText mBodyText;
    private Long mRowId;

	protected TextView mTegText;
	protected TextView mAlmText;
	private AlertDialog unsavedChangesDialog;

	private Button voiceButton;
	private Button stDButton;
	private Button stTButton;
	private Button enDButton;
	private Button enTButton;
	
	private boolean changesPending;
//	Cursor cursor;

	private AlarmManager am;
    
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_reminder);
        srtD = new Date();
        endD = new Date((new Date()).getTime() + 10000);
        alarmD = Calendar.getInstance();
        alarmItem =0;
        setTitle(R.string.edit_note);
        changesPending = false;
        mBodyText = (EditText) findViewById(R.id.body);
        mTegText  = (TextView) findViewById(R.id.tegs);
        mAlmText  = (TextView) findViewById(R.id.signal);

        Button confirmButton = (Button) findViewById(R.id.confirm);
        Button cancelButton = (Button)findViewById(R.id.cancel_button);
        Button tegButton = (Button)findViewById(R.id.button_tegs);
        Button signalButton = (Button)findViewById(R.id.add_signal);
        voiceButton = (Button) findViewById(R.id.voice_notes);
        stDButton = (Button) findViewById(R.id.button_data_beg);
        stTButton = (Button) findViewById(R.id.button_time_beg);
        enDButton = (Button) findViewById(R.id.button_data_end);
        enTButton = (Button) findViewById(R.id.button_time_end);
        
        mRowId = null;
        Bundle extras = getIntent().getExtras();
        if (extras != null) 
        	 if (extras.containsKey(NotePadApplication.KEY_ROWID)){
	        	 mRowId = extras.getLong(NotePadApplication.KEY_ROWID);
	        	 Reminder n=((NotePadApplication)getApplication()).getReminderD().getById(mRowId);
	        	 if (n!=null){
		         	mBodyText.setText(n.getDescr());
		         	mTegText.setText(n.getType());
		         	srtD = n.getStrDate();
		         	endD = n.getEndDate();
		         	if (n.getPrior()!=-1)
		         	{
		         		alarmD.setTimeInMillis(n.getPrior()); //= new Date(n.getPrior());
		         		mAlmText.setText(android.text.format.DateFormat.format("hh:mm dd-MM-yyyy",alarmD));
		         		alarmItem = 100;
		         	}
	        	 }
	         }
        	 else {
        		 long time = extras.getLong(NotePadApplication.KEY_CAL);
        		 srtD.setTime(time);
        		 endD.setTime(time);
        	 }

        stTButton.setText(android.text.format.DateFormat.format("hh:mm",srtD));//.getHours()+":"+srtD.getMinutes());
        enTButton.setText(android.text.format.DateFormat.format("hh:mm",endD));//.getHours()+":"+endD.getMinutes());
        stDButton.setText(android.text.format.DateFormat.format("dd-MM-yyyy",srtD));//.getDate()+"/"+srtD.getMonth()+"/"+(srtD.getYear()+1900));
        enDButton.setText(android.text.format.DateFormat.format("dd-MM-yyyy",endD));//.getDate()+"/"+endD.getMonth()+"/"+(endD.getYear()+1900));
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
		signalButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                showDialog(DIALOG_SGNL);
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
        if (endD.compareTo(srtD)<0)
            n.setEndDate(srtD);
        else 
            n.setEndDate(endD);
        n.setStrDate(srtD);
        if (alarmItem!=0){
        	setAlarmD(alarmItem);
        	n.setPrior(alarmD.getTimeInMillis());

        }else n.setPrior(-1);
        n=((NotePadApplication)getApplication()).getReminderD().update(n);
        Log.d("log", n.toString());
        if (alarmItem!=0){
        	startNotify(alarmD,n.getId(),n.getType(),n.getDescr());
        }
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
					    	stTButton.setText(android.text.format.DateFormat.format("hh:mm",srtD));//.getHours()+":"+srtD.getMinutes());
						    }
						  } ,srtD.getHours(),srtD.getMinutes(), true);
			        return tpd;
			        
				case DIALOG_TIMEE: 
			        TimePickerDialog tpd2 = new TimePickerDialog(this,new OnTimeSetListener() {
					    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
					    	endD.setHours(hourOfDay);
					    	endD.setMinutes(minute);
					    	enTButton.setText(android.text.format.DateFormat.format("hh:mm",endD));//.getHours()+":"+endD.getMinutes());
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
			    		    	stDButton.setText(android.text.format.DateFormat.format("dd-MM-yyyy",srtD));//.getDate()+"/"+srtD.getMonth()+"/"+(srtD.getYear()+1900));
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
			    		    	enDButton.setText(android.text.format.DateFormat.format("dd-MM-yyyy",endD));//.getDate()+"/"+endD.getMonth()+"/"+(endD.getYear()+1900));
			    		    }
			    		    } , endD.getYear()+1900, endD.getMonth(),endD.getDate());
			    	 return dpd2;
			    	 
			    case DIALOG_SGNL:
			    	ArrayAdapter<?> adapter = ArrayAdapter.createFromResource(this,
							R.array.listBells, android.R.layout.select_dialog_singlechoice);
			    	AlertDialog.Builder adb = new AlertDialog.Builder(this);
			    	adb.setTitle(R.string.add_signal);
			    	adb.setSingleChoiceItems(adapter, -1, myClickListener);
			    	adb.setPositiveButton(android.R.string.ok, myClickListener);
			    	adb.setNegativeButton(android.R.string.cancel, myClickListener);
			    	return adb.create();

			    case DIALOG_ALARM:
			    	LayoutInflater infl = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		            final View addAlarmDialogLayout = infl.inflate(R.layout.add_custom_alarm, (ViewGroup) findViewById(R.id.root));

		            AlertDialog.Builder alarmDialogBuilder = new AlertDialog.Builder(this);
		            alarmDialogBuilder.setView(addAlarmDialogLayout);
		            final TimePicker timepic = (TimePicker) addAlarmDialogLayout.findViewById(R.id.timePicker1);
		            final DatePicker datepic = (DatePicker) addAlarmDialogLayout.findViewById(R.id.datePicker1);
		            
		            alarmDialogBuilder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
		                public void onClick(DialogInterface dialog, int which) {
		                	alarmD.set(datepic.getYear(), datepic.getMonth(), datepic.getDayOfMonth(), timepic.getCurrentHour(), timepic.getCurrentMinute());//= new Date(datepic.getYear(), datepic.getMonth(), datepic.getDayOfMonth(), timepic.getCurrentHour(), timepic.getCurrentMinute());

		                	mAlmText.setText(android.text.format.DateFormat.format("hh:mm  dd-MM-yyyy",alarmD));
			            	    
		                }
		            });
		           
		            alarmDialogBuilder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
		                public void onClick(DialogInterface dialog, int which) {
		                    finish();
		                }
		            });
		            return alarmDialogBuilder.create();
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

			    case DIALOG_ALARM:
			    	
			    default:
			      break;
			    }
			  };
//			  // обработчик нажатия на пункт списка диалога или кнопку
		 android.content.DialogInterface.OnClickListener myClickListener = new  android.content.DialogInterface.OnClickListener() {
			    public void onClick(DialogInterface dialog,int which) {
			      ListView lv = ((AlertDialog) dialog).getListView();
			      if (which == Dialog.BUTTON_NEGATIVE){
			      }else
			      if (which == Dialog.BUTTON_POSITIVE) {

				    alarmItem = lv.getCheckedItemPosition();
			    	setAlarmD(lv.getCheckedItemPosition());
			    	  Log.d("log", "pos = " + lv.getCheckedItemPosition());
			      }
			      
			    }


		};

		private void startNotify(Calendar cal,long pos,String teg,String text){
			am  = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
			Intent intent = new Intent(this, TimeNotification.class);
			intent.putExtra(NotePadApplication.KEY_ROWID, pos);
			intent.putExtra(NotePadApplication.KEY_TITLE, teg);
			intent.putExtra(NotePadApplication.KEY_BODY, text);
			
			PendingIntent pi = PendingIntent.getBroadcast(this, 0, intent, 
					PendingIntent.FLAG_CANCEL_CURRENT);
			
		//	am.cancel(pi);
			Log.d("log",android.text.format.DateFormat.format("hh:mm  dd-MM-yyyy",cal).toString());
			am.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), pi);
		}
		
		void setAlarmD(int pos){
			  switch (pos) {
				case 0:
					alarmD = null;
					mAlmText.setText(R.string.none);
					break;
				case 1: 
					alarmD.setTimeInMillis(srtD.getTime()); //= new Date(srtD.getTime());
					mAlmText.setText(R.string.on_time);
					break;
				case 2: 
					alarmD.setTimeInMillis(srtD.getTime()-5*60*1000); //= new Date(srtD.getTime()-5*60*1000);
					mAlmText.setText(R.string.f_min);
					break;
				case 3: 
					alarmD.setTimeInMillis(srtD.getTime()-15*60*1000); //= new Date(srtD.getTime()-15*60*1000);
					mAlmText.setText(R.string.fif_min);
					break;
				case 4: 
					alarmD.setTimeInMillis(srtD.getTime()-60*60*1000); //= new Date(srtD.getTime()-60*60*1000);
					mAlmText.setText(R.string.hour_b);
					break;
				case 5: 
					alarmD.setTimeInMillis(srtD.getTime()-24*60*60*1000); //= new Date(srtD.getTime()-24*60*60*1000);
					mAlmText.setText(R.string.hour_b);
					break;
				case 6: 
					showDialog(DIALOG_ALARM);
					alarmItem = 100;
					break;
				default:
					break;
				}
		}
}
