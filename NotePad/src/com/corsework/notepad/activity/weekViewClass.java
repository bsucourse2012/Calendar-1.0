package com.corsework.notepad.activity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import com.corsework.notepad.adapter.WeekAdapter;
import com.corsework.notepad.application.NotePadApplication;
import com.corsework.notepad.entities.dao.ReminderDao;
import com.corsework.notepad.entities.program.Note;
import com.corsework.notepad.entities.program.Reminder;
import com.corsework.notepad.view.NoteListItem;
import com.corsework.notepad.view.WeekTextView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.util.MonthDisplayHelper;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class weekViewClass extends Activity {
	

	int week;
	Calendar cal;
	WeekAdapter adapter;
	TextView prevweek,nextweek;

	private ReminderDao remiD;
	public Handler handler;
	public ArrayList<String> items;
	
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.weekview_);
        cal = ((NotePadApplication)getApplication()).calcr;//Calendar.getInstance();
        week = cal.get(Calendar.WEEK_OF_YEAR);
        adapter = new WeekAdapter(this,cal,week);
        remiD = ((NotePadApplication)getApplication()).getReminderD();
        items = new ArrayList<String>();
//	    handler = new Handler();
//	    handler.post(calendarUpdater);
	    calUpd();
        
        GridView grid = (GridView)findViewById(R.id.gridview);
        grid.setAdapter(adapter);
        
        TextView title  = (TextView) findViewById(R.id.title);
	    title.setText(cal.get(Calendar.WEEK_OF_YEAR)+getResources().getString(R.string.stweek)+", "+ android.text.format.DateFormat.format("MMMM yyyy", cal));
	    
	    TextView previous  = (TextView) findViewById(R.id.previous);
	    previous.setOnClickListener(new OnClickListener() {
						
			public void onClick(View v) {
				if(cal.get(Calendar.WEEK_OF_YEAR)== cal.getActualMinimum(Calendar.WEEK_OF_YEAR)) {
					cal.set(Calendar.YEAR,cal.get(Calendar.YEAR)-1);
					cal.set(Calendar.WEEK_OF_YEAR, cal.getActualMaximum(Calendar.WEEK_OF_YEAR));
					
				} else {
					cal.set(Calendar.WEEK_OF_YEAR,cal.get(Calendar.WEEK_OF_YEAR)-1);
				}
				refreshCalendar();
			}
		});
	    
	    TextView next  = (TextView) findViewById(R.id.next);
	    next.setOnClickListener(new OnClickListener() {
						
			public void onClick(View v) {
				if(cal.get(Calendar.WEEK_OF_YEAR)== cal.getActualMaximum(Calendar.WEEK_OF_YEAR)) {
					cal.set((cal.get(Calendar.YEAR)+1),cal.getActualMinimum(Calendar.MONTH),1);
				} else {
					cal.set(Calendar.WEEK_OF_YEAR,cal.get(Calendar.WEEK_OF_YEAR)+1);
				}
				refreshCalendar();
				
			}
		});
	    
		grid.setOnItemClickListener(new OnItemClickListener() {
		    public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
		    	if (position>8 && position%8!=0){
		        	
		    		if (((WeekTextView)v).isF()){
		    			Intent i = new Intent(weekViewClass.this, DayListViewClass.class);
			    		Calendar c2 = (Calendar) cal.clone();
			    		c2.set(Calendar.DAY_OF_WEEK, position%8);
			    		Log.d("click", android.text.format.DateFormat.format("hh:mm dd-MM-yyyy",c2).toString());
		    			i.putExtra(NotePadApplication.KEY_SRTD,c2.getTimeInMillis());
		    			startActivity(i);
		    		}
		    		else{
			    		Calendar c2 = (Calendar) cal.clone();
			    		c2.set(Calendar.DAY_OF_WEEK, position%8);
			    		Intent i = new Intent(weekViewClass.this, AddReminderActivity.class);
			    		int pos= position-position%8-8,time=0;
			    		while (pos>0){
			    			time++;
			    			pos-=8;
			    		}
			    		c2.set(Calendar.HOUR, time%24);
			    		i.putExtra(NotePadApplication.KEY_CAL,c2.getTimeInMillis());
			            startActivity(i);
		    		}
		    	}
		    	else
		    		if (position>=1 && position<=7){
		    			cal.set(Calendar.DAY_OF_WEEK, position%8);
		    			Log.d("log click", android.text.format.DateFormat.format("hh:mm dd-MM-yyyy",cal).toString());   			
		    			((NotePadApplication)getApplication()).calcr = cal;	
		    	        ((NotePadApplication)getApplication()).tabH.setCurrentTabByTag("dayView");
		    		}
		    }
		});
	}
	@Override
	protected void onResume() {
		super.onResume();
		refreshCalendar();
	}
	public void refreshCalendar()
	{
		TextView title  = (TextView) findViewById(R.id.title);
		
	//	handler.post(calendarUpdater); 
		calUpd();
		adapter.refreshDays();
		adapter.notifyDataSetChanged();				
		
		title.setText(cal.get(Calendar.WEEK_OF_YEAR)+"week,"+android.text.format.DateFormat.format("MMMM yyyy", cal));
	}
	
private void calUpd() {
		// TODO Auto-generated method stub
	Calendar cal2 = (Calendar) cal.clone();
 //   Log.d("log cal", android.text.format.DateFormat.format("hh:mm dd-MM-yyyy",cal2).toString());
    cal2.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
 //   Log.d("log cal", android.text.format.DateFormat.format("hh:mm dd-MM-yyyy",cal2).toString());
    
	Date dat =  cal2.getTime();
    cal2.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
 //   Log.d("log cal", android.text.format.DateFormat.format("hh:mm dd-MM-yyyy",cal2).toString());
    
	Date dat2 = cal2.getTime();
	dat.setHours(0);
	dat.setMinutes(0);
	dat.setSeconds(0);
	dat2.setHours(23);
	dat2.setMinutes(59);
	dat2.setSeconds(59);
//	Log.d("log cald", android.text.format.DateFormat.format("hh:mm dd-MM-yyyy",dat).toString());
//	Log.d("log cald", android.text.format.DateFormat.format("hh:mm dd-MM-yyyy",dat2).toString());
//    
	items.clear();
	ArrayList<Reminder> rec;
	rec = remiD.getByStEndDate(dat, dat2);//remiD.getByCrDate(dat, dat2);

    
	
	adapter.setItems(rec);
//	adapter.notifyDataSetChanged();
	}
//
//public Runnable calendarUpdater = new Runnable() {
//		
//		
//		public void run() {
//			 
//			Calendar cal2 = (Calendar) cal.clone();
//		    Log.d("log cal", android.text.format.DateFormat.format("hh:mm dd-MM-yyyy",cal2).toString());
//		    cal2.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
//		    Log.d("log cal", android.text.format.DateFormat.format("hh:mm dd-MM-yyyy",cal2).toString());
//		    
//			Date dat =  cal2.getTime();
//		    cal2.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
//		    Log.d("log cal", android.text.format.DateFormat.format("hh:mm dd-MM-yyyy",cal2).toString());
//		    
//			Date dat2 = cal2.getTime();
//			dat.setHours(0);
//			dat.setMinutes(0);
//			dat.setSeconds(0);
//			dat2.setHours(23);
//			dat2.setMinutes(59);
//			dat2.setSeconds(59);
//			Log.d("log cald", android.text.format.DateFormat.format("hh:mm dd-MM-yyyy",dat).toString());
//			Log.d("log cald", android.text.format.DateFormat.format("hh:mm dd-MM-yyyy",dat2).toString());
//		    
//			items.clear();
//			ArrayList<Reminder> rec;
//			rec = remiD.getByStEndDate(dat, dat2);
//			Log.d("log cal", rec.toString());
//		    
//			
//			adapter.setItems(rec);
//			adapter.notifyDataSetChanged();
//		}
//	};
//	
}
