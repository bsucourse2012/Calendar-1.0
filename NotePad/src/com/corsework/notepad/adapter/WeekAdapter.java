package com.corsework.notepad.adapter;

import java.util.Calendar;

import android.content.Context;
import android.graphics.Color;
import android.util.MonthDisplayHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.corsework.notepad.activity.R;

public class WeekAdapter extends BaseAdapter{
	private Context mContext;

    private Calendar month;
    private Calendar selectedDate;


	public MonthDisplayHelper mHelper;
    
    public WeekAdapter(Context c, Calendar monthCalendar,int week) {
    	month = monthCalendar;
    	selectedDate = (Calendar)monthCalendar.clone();
    	mContext = c;
     //   month.set(Calendar.DAY_OF_MONTH, 1);
        refreshDays();
    }
    

    public int getCount() {
        return days.length;
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    // create a new view for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
    	TextView dayView;
        if (convertView == null) { 
        	// if it's not recycled, initialize some attributes
        	LayoutInflater vi = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(R.layout.week_item, null);
        	
        }
        
        dayView = (TextView)v.findViewById(R.id.date);
        
        // a day title
        if ((position>=0 && position<=7) || position%8==0){
        	dayView.setClickable(false);
        	dayView.setFocusable(false);
        	dayView.setTextColor(Color.WHITE);
        	v.setBackgroundColor(android.R.color.background_light);
      
        }
        else{//если есть заметка рисуем
	        }
        
        dayView.setText(days[position]);
        
        
        return v;
    }
    
    public void refreshDays()
    {
        int firstDay = (int)month.get(Calendar.WEEK_OF_MONTH)-1;

		mHelper = new MonthDisplayHelper(month.get(Calendar.YEAR), month.get(Calendar.MONTH));
		int n[] = mHelper.getDigitsForRow(firstDay);
        days = new String[24*8+8];
        
        days[0]="";
        days[2]="Mon\n"; 
        days[3]="Tue\n"; 
        days[4]="Wen\n"; 
        days[5]="Thu\n";
        days[6]="Fri\n"; 
        days[7]="Sat\n"; 
        days[1]="Sun\n";
        for (int i=0; i<n.length; i++)
        	days[i+1]+=" "+n[i];

        int time = 1;
        // «аполн€ем  дни
        for(int i=8;i<days.length;i++) {
        	if (i%8!=0){
        	days[i] = "";
        	}
        	else {
        		days[i]=""+time%24;
        		time++;
        	}
        }
        
    }

    // references to our items
    public String[] days;
}
