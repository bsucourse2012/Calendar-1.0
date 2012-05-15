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
import com.corsework.notepad.entities.program.Record;
import com.corsework.notepad.entities.program.Reminder;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class monthViewClass extends Activity {
	

	private static final int MENU_ITEM_INSERT_NOTE = Menu.FIRST + 1;
	private static final int MENU_ITEM_INSERT_REMI = Menu.FIRST + 2;
	
	Calendar cal;
	CalendarAdapter adapter;
	TextView prevmonth,nextmonth;
	private NoteDao noteD;
	private ReminderDao remiD;
	public Handler handler;

	public ArrayList<String> items,itemsR; 
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.monthview_);

        noteD = ((NotePadApplication)getApplication()).getNoteD();
        remiD = ((NotePadApplication)getApplication()).getReminderD();
        
        cal =((NotePadApplication)getApplication()).calcr;// Calendar.getInstance();
        adapter = new CalendarAdapter(this,cal);
        
        GridView grid = (GridView)findViewById(R.id.gridview);
        grid.setAdapter(adapter);

        items = new ArrayList<String>();
        itemsR = new ArrayList<String>();
//	    handler = new Handler();
//	    handler.post(calendarUpdater);
	    calUp();
	    
        TextView title  = (TextView) findViewById(R.id.title);
	    title.setText(android.text.format.DateFormat.format("MMMM yyyy", cal));
	    
	    TextView previous  = (TextView) findViewById(R.id.previous);
	    previous.setOnClickListener(new OnClickListener() {
			
			 
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
		        	int day = Integer.valueOf(date.getText().toString());
		        	((NotePadApplication)getApplication()).calcr.set(Calendar.DAY_OF_MONTH, day);
		        	((NotePadApplication)getApplication()).tabH.setCurrentTabByTag("dayView");
		        	Toast.makeText(monthViewClass.this,android.text.format.DateFormat.format("yyyy-MM", cal)+"-"+day, Toast.LENGTH_SHORT).show();
		       
		        }
		        
		    }
		});
	}
	
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		menu.add(0, MENU_ITEM_INSERT_NOTE, 0, R.string.add_note_button).setShortcut('1', 'i')
			.setIcon(android.R.drawable.ic_menu_add);
		menu.add(0, MENU_ITEM_INSERT_REMI, 0, R.string.add_reminder).setShortcut('2', 'u')
		.setIcon(android.R.drawable.ic_menu_call);
		
		return true;
	}
	 @Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
	        switch(item.getItemId()) {
	            case MENU_ITEM_INSERT_NOTE:
	                createNote();
	                return true;
	            case MENU_ITEM_INSERT_REMI:
	                createReminder();
	                return true;
	        }

	        return super.onMenuItemSelected(featureId, item);
	    }


	private void createReminder() {
		Intent i = new Intent(this, AddReminderActivity.class);
        startActivity(i);
	}

	private void createNote() {
	        Intent i = new Intent(this, AddNoteActivity.class);
	        startActivity(i);
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
		
	//	handler.post(calendarUpdater); 
		calUp();
		adapter.refreshDays();
		adapter.notifyDataSetChanged();	
		
		title.setText(android.text.format.DateFormat.format("MMMM yyyy", cal));
	}
	private void calUp() {
		Date dat =  cal.getTime();
		Date dat2 = cal.getTime();
		dat.setHours(0);
		dat.setMinutes(0);
		dat.setSeconds(0);
		dat2.setHours(23);
		dat2.setMinutes(59);
		dat2.setSeconds(59);
		
		items.clear();
		itemsR.clear();
		ArrayList<Note> not;
		ArrayList<Reminder> rec;
		for(int i=cal.getActualMinimum(Calendar.DAY_OF_MONTH);
				i<cal.getActualMaximum(Calendar.DAY_OF_MONTH)-1;i++) {
			dat.setDate(i);
			dat2.setDate(i);
			rec =  remiD.getByStDate(dat, dat2);//remiD.getByCrDate(dat, dat2);
			not = noteD.getByCrDate(dat, dat2);//(cal.getTime(),cal2.getTime());
			if (!not.isEmpty())
			{
				items.add(""+i);
			}
			if (!rec.isEmpty())
				itemsR.add(""+i);
		}

		adapter.setItems(items,itemsR);
	//	adapter.notifyDataSetChanged();
		
	}
//	public Runnable calendarUpdater = new Runnable() {
//		
//		
//		public void run() {
//			Date dat =  cal.getTime();
//			Date dat2 = cal.getTime();
//			dat.setHours(0);
//			dat.setMinutes(0);
//			dat.setSeconds(0);
//			dat2.setHours(23);
//			dat2.setMinutes(59);
//			dat2.setSeconds(59);
//			
//			items.clear();
//			itemsR.clear();
//			ArrayList<Note> not;
//			ArrayList<Reminder> rec;
//			for(int i=cal.getActualMinimum(Calendar.DAY_OF_MONTH);
//					i<cal.getActualMaximum(Calendar.DAY_OF_MONTH)-1;i++) {
//				dat.setDate(i);
//				dat2.setDate(i);
//				rec = remiD.getByStEndDate(dat, dat2);//getByCrDate(dat, dat2);
//				not = noteD.getByCrDate(dat, dat2);//(cal.getTime(),cal2.getTime());
//				if (!not.isEmpty())
//				{
//					items.add(""+i);
//				}
//				if (!rec.isEmpty())
//					itemsR.add(""+i);
//			}
//
//			adapter.setItems(items,itemsR);
//			adapter.notifyDataSetChanged();
//		}
//	};
}
