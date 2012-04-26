package com.corsework.notepad.activity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import com.corsework.notepad.adapter.CalendarAdapter;
import com.corsework.notepad.application.NotePadApplication;
import com.corsework.notepad.entities.dao.NoteDao;
import com.corsework.notepad.entities.dao.ReminderDao;
import com.corsework.notepad.entities.program.Note;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class monthViewClass extends Activity {
	
	Calendar cal;
	CalendarAdapter adapter;
	TextView prevmonth,nextmonth;
	private NoteDao noteD;
	private ReminderDao remiD;
	public Handler handler;

	public ArrayList<String> items; 
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.monthview_);

        noteD = ((NotePadApplication)getApplication()).getNoteD();
        remiD = ((NotePadApplication)getApplication()).getReminderD();
        
        cal =((NotePadApplication)getApplication()).calcr;// Calendar.getInstance();
        adapter = new CalendarAdapter(this,cal);
        
        GridView grid = (GridView)findViewById(R.id.gridview);
        grid.setAdapter(adapter);

	//    handler = new Handler();
	//    handler.post(calendarUpdater);
	    
        TextView title  = (TextView) findViewById(R.id.title);
	    title.setText(android.text.format.DateFormat.format("MMMM yyyy", cal));
	    
	    TextView previous  = (TextView) findViewById(R.id.previous);
	    previous.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(cal.get(Calendar.MONTH)== cal.getActualMinimum(Calendar.MONTH)) {				
					cal.set((cal.get(Calendar.YEAR)-1),cal.getActualMaximum(Calendar.MONTH),1);
				} else {
					cal.set(Calendar.MONTH,cal.get(Calendar.MONTH)-1);
				}
				refreshCalendar();
			}
		});
	    
	    TextView next  = (TextView) findViewById(R.id.next);
	    next.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(cal.get(Calendar.MONTH)== cal.getActualMaximum(Calendar.MONTH)) {				
					cal.set((cal.get(Calendar.YEAR)+1),cal.getActualMinimum(Calendar.MONTH),1);
				} else {
					cal.set(Calendar.MONTH,cal.get(Calendar.MONTH)+1);
				}
				refreshCalendar();
				
			}
		});
	    
		grid.setOnItemClickListener(new OnItemClickListener() {
		    public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
		    	TextView date = (TextView)v.findViewById(R.id.date);
		        if(date instanceof TextView && !date.getText().equals("") && date.getText().length()<3) {
		        	String day = date.getText().toString();
		        	if(day.length()==1) {
		        		day = "0"+day;
		        	}
		        	Toast.makeText(monthViewClass.this,android.text.format.DateFormat.format("yyyy-MM", cal)+"-"+day, Toast.LENGTH_SHORT).show();
		       
		        }
		        
		    }
		});
	}
	public void refreshCalendar()
	{
		TextView title  = (TextView) findViewById(R.id.title);
		
		adapter.refreshDays();
		adapter.notifyDataSetChanged();		
	//	handler.post(calendarUpdater); 
		
		title.setText(android.text.format.DateFormat.format("MMMM yyyy", cal));
	}
	public Runnable calendarUpdater = new Runnable() {
		
		@Override
		public void run() {
			Date dat =  cal.getTime();
			Date dat2 = cal.getTime();
			dat2.setDate(dat2.getDate()+1);
		//	dat.setDate(1);
			ArrayList<Note> not = noteD.getByCrDate(dat, dat2);
			for(int i=0;i<31;i++) {
				if (!not.isEmpty())
				{
					items.add(""+dat.getDate());
				}
				dat.setDate(dat.getDate()+1);
			}

			adapter.setItems(items);
			adapter.notifyDataSetChanged();
		}
	};
}
