package com.corsework.notepad.activity;

import java.util.Calendar;

import com.corsework.notepad.adapter.DayAdapter;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class dayViewClass extends Activity {
	
	Calendar cal;
	DayAdapter adapter;
	TextView prevweek,nextweek;
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dayview_);cal = Calendar.getInstance();
        adapter = new DayAdapter(this,cal);
        
        ListView list = (ListView)findViewById(R.id.list);
        list.setAdapter(adapter);
        
        TextView title  = (TextView) findViewById(R.id.title);
	    title.setText(android.text.format.DateFormat.format("dd MMMM yyyy", cal));
	    
	    TextView previous  = (TextView) findViewById(R.id.previous);
	    previous.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(cal.get(Calendar.WEEK_OF_YEAR)== cal.getActualMinimum(Calendar.WEEK_OF_YEAR)) {
					cal.set((cal.get(Calendar.YEAR)-1),cal.getActualMaximum(Calendar.MONTH),1);
					
				} else {
					cal.set(Calendar.WEEK_OF_YEAR,cal.get(Calendar.WEEK_OF_YEAR)-1);
				}
				refreshCalendar();
			}
		});
	    
	    TextView next  = (TextView) findViewById(R.id.next);
	    next.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(cal.get(Calendar.WEEK_OF_YEAR)== cal.getActualMaximum(Calendar.WEEK_OF_YEAR)) {
					cal.set((cal.get(Calendar.YEAR)+1),cal.getActualMinimum(Calendar.MONTH),1);
				} else {
					cal.set(Calendar.WEEK_OF_YEAR,cal.get(Calendar.WEEK_OF_YEAR)+1);
				}
				refreshCalendar();
				
			}
		});
	    
		list.setOnItemClickListener(new OnItemClickListener() {
		    public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
		    	if (position>8 && position%8!=0)
		        	Toast.makeText(dayViewClass.this,android.text.format.DateFormat.format("yyyy-MM", cal)+"  + "+position, Toast.LENGTH_SHORT).show();
		        
		        
		    }
		});
	}
	public void refreshCalendar()
	{
		TextView title  = (TextView) findViewById(R.id.title);
		
		adapter.refreshDays();
		adapter.notifyDataSetChanged();				
		
		title.setText(cal.get(Calendar.WEEK_OF_YEAR)+"week,"+android.text.format.DateFormat.format("MMMM yyyy", cal));
	}
}