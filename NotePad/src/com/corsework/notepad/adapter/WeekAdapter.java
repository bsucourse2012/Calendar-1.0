package com.corsework.notepad.adapter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.util.MonthDisplayHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.corsework.notepad.activity.R;
import com.corsework.notepad.entities.program.Reminder;
import com.corsework.notepad.view.WeekTextView;

public class WeekAdapter extends BaseAdapter{
	private Context mContext;

    private Calendar month;

    

	public MonthDisplayHelper mHelper;
    
    public WeekAdapter(Context c, Calendar monthCalendar,int week) {
    	month = monthCalendar;
    	mContext = c;
    	itemRem = new ArrayList<Reminder>();
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
    	WeekTextView dayView;
        if (convertView == null) { 
        	// if it's not recycled, initialize some attributes
        	LayoutInflater vi = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(R.layout.week_item, null);
        	
        }
        
        dayView = (WeekTextView)v.findViewById(R.id.date);
        
        // a day title
        if ((position>=0 && position<=7) || position%8==0){
        	dayView.setClickable(false);
        	dayView.setFocusable(false);
        	dayView.setTextColor(Color.WHITE);
        	v.setBackgroundColor(android.R.color.background_light);
        	dayView.setF(false);
        	dayView.setText(days[position]);
        }
       else{//если есть заметка рисуем
	   
    	   if ("+".equalsIgnoreCase(days[position])){
           	dayView.setF(true);
           	dayView.invalidate();
           	dayView.setText("");
           }else{
           	dayView.setText(days[position]);
           	dayView.setF(false);
           }
       }
        
        
        
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

        int time = 0;
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
        Calendar cal2 = (Calendar) month.clone();
        Log.d("log cal", android.text.format.DateFormat.format("hh:mm dd-MM-yyyy",cal2).toString());
        cal2.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
         Date strt = cal2.getTime();
        cal2.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
        Date fin = cal2.getTime();
        strt.setHours(0);
        strt.setMinutes(0);
        strt.setSeconds(0);
    	fin.setHours(23);
    	fin.setMinutes(59);
    	fin.setSeconds(59);
    	Log.d("log cal", android.text.format.DateFormat.format("hh:mm dd-MM-yyyy",strt).toString());
    	 Log.d("log cal", android.text.format.DateFormat.format("hh:mm dd-MM-yyyy",fin).toString());
         
        int rowst,rowf,clmst=1,clmf=1;
        for (Reminder r: itemRem){
        	Date d1= r.getStrDate();
        	if (d1.before(strt))
        		clmst =1;
        	else
        		for (int i=1; i<n.length; i++)
        			if (n[i]==d1.getDate()){
        				clmst = i+1; break;
        			}
        	rowst = d1.getHours()+1;
        	d1= r.getEndDate();
        	if (d1.after(fin))
        		clmf =7;
        	else
        		for (int i=clmst-1; i<n.length; i++)
        			if (n[i]==d1.getDate()){
        				clmf = i+1; break;
        			}
        	rowf = d1.getHours()+1;//*8+8;
        	if (clmf == clmst){
        		for (int j=rowst; j<=rowf; j++)
        			days[clmst+j*8]="+";
        	}else
        	{
        		for (int j=rowst; j<=24; j++)
        			days[clmst+j*8]="+";
	        	for (int i= clmst+1; i<=clmf-1; i++)
	        		for (int j=1; j<=24; j++)
	        			days[i+j*8]="+";
	        	for (int j=0; j<=rowf; j++)
        			days[clmf+j*8]="+";
        	}
        }
        notifyDataSetChanged();
    }

    // references to our items
    public String[] days;
    ArrayList<Reminder> itemRem;
	public void setItems(ArrayList<Reminder> items) {
		// TODO Auto-generated method stub
		this.itemRem = items;
	}


}
