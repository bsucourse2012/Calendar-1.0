package com.corsework.notepad.adapter;

import java.util.ArrayList;
import java.util.Calendar;
import com.corsework.notepad.activity.R;

import android.content.Context;
import android.graphics.Color;
import android.util.MonthDisplayHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CalendarAdapter extends BaseAdapter {
static final int FIRST_DAY_OF_WEEK =0; // Sunday = 0, Monday = 1
	
	
	private Context mContext;

    private Calendar month;
    private Calendar selectedDate;

	private ArrayList<String> items,itemsR;
    
    public CalendarAdapter(Context c, Calendar monthCalendar) {

    	month = monthCalendar;
    	selectedDate = (Calendar)Calendar.getInstance().clone();
    	mContext = c;
      //  month.set(Calendar.DAY_OF_MONTH, 1);
        refreshDays();
    }

    public void setItems(ArrayList<String> items,ArrayList<String> itemsR) {
    	this.items = items;
    	this.itemsR = itemsR;
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
        if (convertView == null) {  // if it's not recycled, initialize some attributes
        	LayoutInflater vi = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(R.layout.calendar_item, null);
        	
        }
        dayView = (TextView)v.findViewById(R.id.date);
        
        //a day title
        if (position<=6){
        	dayView.setClickable(false);
        	dayView.setFocusable(false);
        	dayView.setTextColor(Color.WHITE);
        	v.setBackgroundColor(android.R.color.background_light);
      
        }
        else
	        // disable empty days from the beginning
	        if(days[position].equals("")) {
	        	dayView.setClickable(false);
	        	dayView.setFocusable(false);
	        }
	        else {
	        	// mark current day as focused
	        	
	        	if((month.get(Calendar.YEAR)== selectedDate.get(Calendar.YEAR)) && 
	        			(month.get(Calendar.MONTH)== selectedDate.get(Calendar.MONTH)) && 
	        			(days[position].equals(""+selectedDate.get(Calendar.DAY_OF_MONTH)))) {
	        		v.setBackgroundResource(R.drawable.item_background_focused);

	            	dayView.setTextColor(Color.WHITE);
	        	}
	        	else {

	        		v.setBackgroundResource(R.drawable.list_item_background);
	        		
	        	}
	        }
        
        dayView.setText(days[position]);
        
        // create date string for comparison
        String date = days[position];
    	
//        if(date.length()==1) {
//    		date = "0"+date;
//    	}
    	String monthStr = ""+(month.get(Calendar.MONTH)+1);
    	if(monthStr.length()==1) {
    		monthStr = "0"+monthStr;
    	}
       
        // show icon if date is not empty and it exists in the items array
       ImageView iw = (ImageView)v.findViewById(R.id.date_icon);
       if(date.length()>0 && items!=null && items.contains(date)) {        	
        	iw.setVisibility(View.VISIBLE);
       }
       else {
        	iw.setVisibility(View.INVISIBLE);
       }
       ImageView iw2 = (ImageView)v.findViewById(R.id.date_icon2);
       if(date.length()>0 && itemsR!=null && itemsR.contains(date)) {        	
        	iw2.setVisibility(View.VISIBLE);
       }
       else {
        	iw2.setVisibility(View.INVISIBLE);
       }
        return v;
    }
    
    public void refreshDays()
    {
//    	int lastDay = month.getActualMaximum(Calendar.DAY_OF_MONTH);
//        int firstDay = (int)month.get(Calendar.DAY_OF_WEEK);
//        
        // figure size of the array
//        if(firstDay==1){
//        	days = new String[lastDay+(FIRST_DAY_OF_WEEK*6)+7];
//        }
//        else {
//        	days = new String[lastDay+firstDay-(FIRST_DAY_OF_WEEK+1)+7];
//        }

        days= new String[7*6+7];
        
        days[1]="Mon"; days[2]="Tue"; days[3]="Wen"; days[4]="Thu";
        days[5]="Fri"; days[6]="Sat"; days[0]="Sun";


        MonthDisplayHelper mHelper = new MonthDisplayHelper(month.get(Calendar.YEAR), month.get(Calendar.MONTH));
        
	    for(int i=0; i<6; i++) {
	    	int n[] = mHelper.getDigitsForRow(i);
	    	for(int d=0; d<n.length; d++) {
	    		if(mHelper.isWithinCurrentMonth(i,d))
	    			days[7+i*7+d] = ""+n[d];
	    		else
	    			days[7+i*7+d]="";
	    		
	    	}
	    }

//        
//        int j=FIRST_DAY_OF_WEEK;
//        
//        // populate empty days before first real day
//        if(firstDay>1) {
//	        for(j=0;j<firstDay-FIRST_DAY_OF_WEEK;j++) {
//	        	days[j+7] = "";
//	        }
//        }
//	    else {
//	    	for(j=0;j<FIRST_DAY_OF_WEEK*6;j++) {
//	        	days[j+7] = "";
//	        }
//	    	j=FIRST_DAY_OF_WEEK*6+1; // sunday => 1, monday => 7
//	    }
//        
//        int m = month.get(Calendar.MONTH);
//        int y = month.get(Calendar.YEAR);
//        // populate days
//        int dayNumber = 1;
//        for(int i=j-1;i<days.length-7;i++) {
//        	days[i+7] = ""+dayNumber;
//        	dayNumber++;
//        }
    }

    // references to our items
    public String[] days;
}