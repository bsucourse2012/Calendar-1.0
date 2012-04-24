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

public class DayAdapter extends BaseAdapter{
	private Context mContext;

    private Calendar month;


	public MonthDisplayHelper mHelper;
    
    public DayAdapter(Context c, Calendar monthCalendar) {
    	month = monthCalendar;
    	mContext = c;
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
    	TextView dataView;
        if (convertView == null) { 
        	// if it's not recycled, initialize some attributes
        	LayoutInflater vi = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(R.layout.day_item, null);
        	
        }
        
        dayView = (TextView)v.findViewById(R.id.date);
        dataView = (TextView)v.findViewById(R.id.text);
        dayView.setText(""+(position%24));
        dataView.setText("");
       
        
        
        return v;
    }
    
    public void refreshDays()
    {
        
		days = new String[24];
        
        // Заполняем  дни
        for(int i=1;i<days.length;i++) {
        	days[i] = "";
        	
        }
        
        
    }
    // references to our items
    public String[] days;

}
