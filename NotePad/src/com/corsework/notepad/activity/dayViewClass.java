package com.corsework.notepad.activity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import com.corsework.notepad.adapter.DayAdapter;
import com.corsework.notepad.application.NotePadApplication;
import com.corsework.notepad.entities.dao.ReminderDao;
import com.corsework.notepad.entities.program.Reminder;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class dayViewClass extends Activity {
	
	Calendar cal;
	DayAdapter adapter;
	TextView prevweek,nextweek;
	private ReminderDao remiD;
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dayview_);

        remiD = ((NotePadApplication)getApplication()).getReminderD();
        cal =((NotePadApplication)getApplication()).calcr;//  Calendar.getInstance();
        adapter = new DayAdapter(this,cal);
        calUpd();
        ListView list = (ListView)findViewById(R.id.list);
        list.setAdapter(adapter);
        
        TextView title  = (TextView) findViewById(R.id.title);
	    title.setText(android.text.format.DateFormat.format("dd MMMM yyyy", cal));
	    
	    TextView previous  = (TextView) findViewById(R.id.previous);
	    previous.setOnClickListener(new OnClickListener() {
			
			
			public void onClick(View v) {
				if(cal.get(Calendar.DAY_OF_YEAR)== cal.getActualMinimum(Calendar.DAY_OF_YEAR)) {
					cal.set((cal.get(Calendar.YEAR)-1),cal.getActualMaximum(Calendar.MONTH),cal.getActualMaximum(Calendar.DAY_OF_MONTH));
					
				} else {
					cal.set(Calendar.DAY_OF_YEAR,cal.get(Calendar.DAY_OF_YEAR)-1);
				}
				refreshCalendar();
			}
		});
	    
	    TextView next  = (TextView) findViewById(R.id.next);
	    next.setOnClickListener(new OnClickListener() {
			
			
			public void onClick(View v) {
				if(cal.get(Calendar.DAY_OF_YEAR)== cal.getActualMaximum(Calendar.DAY_OF_YEAR)) {
					cal.set((cal.get(Calendar.YEAR)+1),cal.getActualMinimum(Calendar.MONTH),1);
				} else {
					cal.set(Calendar.DAY_OF_YEAR,cal.get(Calendar.DAY_OF_YEAR)+1);
				}
				refreshCalendar();
				
			}
		});
	    
		list.setOnItemClickListener(new OnItemClickListener() {
		    public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
		    	
		    		Calendar c2 = (Calendar) cal.clone();
		    		c2.set(Calendar.HOUR_OF_DAY, position-1);
		    		if (!adapter.days[position].equalsIgnoreCase("")){
		    			Intent i = new Intent(dayViewClass.this, DayListViewClass.class);
		    			i.putExtra(NotePadApplication.KEY_SRTD,c2.getTimeInMillis());
		    			startActivity(i);
		    		}else{
			    		Intent i = new Intent(dayViewClass.this, AddReminderActivity.class);
			    		i.putExtra(NotePadApplication.KEY_CAL,c2.getTimeInMillis());
			            startActivity(i);
		    		}
		    	}
		    
		});
	}

	@Override
	protected void onResume() {
		super.onResume();
		 cal =((NotePadApplication)getApplication()).calcr;
		refreshCalendar();
	}
	public void refreshCalendar()
	{
		TextView title  = (TextView) findViewById(R.id.title);
		
		calUpd();
		adapter.refreshDays();
		adapter.notifyDataSetChanged();				
		
		title.setText(android.text.format.DateFormat.format("dd MMMM yyyy", cal));

	}
	
	
private void calUpd() {
	
	Date dat =  cal.getTime();
    
	Date dat2 = cal.getTime();
	dat.setHours(0);
	dat.setMinutes(0);
	dat.setSeconds(0);
	dat2.setHours(23);
	dat2.setMinutes(59);
	dat2.setSeconds(59);
	Log.d("log cald", android.text.format.DateFormat.format("hh:mmaa dd-MM-yyyy",dat).toString());
	Log.d("log cald", android.text.format.DateFormat.format("hh:mmaa dd-MM-yyyy",dat2).toString());
    
	ArrayList<Reminder> rec;
	rec = remiD.getByStEndDate(dat, dat2);
	Log.d("log cal", rec.toString());
    
	
	adapter.setItems(rec,cal);
//	adapter.notifyDataSetChanged();
	}
}