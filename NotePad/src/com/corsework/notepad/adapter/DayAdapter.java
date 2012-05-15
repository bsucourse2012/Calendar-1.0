package com.corsework.notepad.adapter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import android.content.Context;
import android.graphics.Color;
import android.util.MonthDisplayHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.corsework.notepad.activity.R;
import com.corsework.notepad.entities.program.Reminder;
import com.corsework.notepad.view.WeekTextView;

public class DayAdapter extends BaseAdapter{
	private Context mContext;

    private Calendar month;


	public MonthDisplayHelper mHelper;
    
    public DayAdapter(Context c, Calendar monthCalendar) {
    	month = monthCalendar;
    	mContext = c;
    	rem = new ArrayList<Reminder>();
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
    	WeekTextView dataView;
        if (convertView == null) { 
        	// if it's not recycled, initialize some attributes
        	LayoutInflater vi = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(R.layout.day_item, null);
        	
        }
        
        dayView = (TextView)v.findViewById(R.id.date);
        dataView = (WeekTextView)v.findViewById(R.id.text);
        if (position<10)
        	dayView.setText("0"+(position%24));
        else
        	dayView.setText(""+(position%24));
    	dataView.setText("");
        if ("+".equalsIgnoreCase(days[position])){
        	dataView.setF(true);
        }else
        if (days[position]!=null && days[position].length()>1){
        	dataView.setF(true);
        	dataView.setText(days[position]);
        }else{
        	dataView.setF(false);
        }
       
        return v;
    }
    
    public void refreshDays()
    {
        
		days = new String[24];
        
        // Заполняем  дни
        for(int i=1;i<days.length;i++) {
        	days[i] = "";
        	
        }
        
        Date dat = month.getTime();
        int st,fin;
        for (Reminder r: rem){
        	Date ds = r.getStrDate();
        	if (ds.getDate()!=dat.getDate())
        		st=0;
        	else st = ds.getHours();
        	ds = r.getEndDate();
        	if (ds.getDate()!=dat.getDate())
        		fin = 23;
        	else fin = ds.getHours();
        	String str = r.getDescr();
        	days[st] +=" "+ str.subSequence(0, min(20,str.length(),str.indexOf("\n")))+"...";
        	for (int i=st+1; i<=fin; i++)
        		days[i]="+";
        }
        
    }
    private int min(int i, int j,int k) {
		int temp=(i<j)?i:j;
		if (k==-1)
			return temp;
		return (temp<k)?temp:k;
	}
    // references to our items
    public String[] days;
    public ArrayList<Reminder> rem;

	public void setItems(ArrayList<Reminder> rec) {
		// TODO Auto-generated method stub
		rem = rec;
	}

}
