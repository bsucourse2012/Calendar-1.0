package com.corsework.notepad.activity;


import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

public class ViewNotePadActivity extends TabActivity {
	
	
	public TabHost tabH;

	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        tabH=(TabHost)findViewById(android.R.id.tabhost);
        tabH.setup();
        TabSpec listViewTab= tabH.newTabSpec("listView");
        listViewTab.setIndicator("",getResources().getDrawable(android.R.drawable.ic_menu_agenda));
 //       listViewTab.setContent(R.id.list_view);
        Intent listIntent = new Intent(this, listViewClass.class);
        listViewTab.setContent(listIntent);
        tabH.addTab(listViewTab);
        
        TabSpec monthViewTab=tabH.newTabSpec("monthView");
        monthViewTab.setIndicator("",getResources().getDrawable(android.R.drawable.ic_menu_month));
 //       monthViewTab.setContent(R.id.calendar_month);
        Intent monthIntent = new Intent(this, monthViewClass.class);
        monthViewTab.setContent(monthIntent);
        tabH.addTab(monthViewTab);
        
        TabSpec weekViewTab=tabH.newTabSpec("weekView");
        weekViewTab.setIndicator("",getResources().getDrawable(android.R.drawable.ic_menu_week));
 //       weekViewTab.setContent(R.id.calendar_week);
        Intent weekIntent = new Intent(this, weekViewClass.class);
        weekViewTab.setContent(weekIntent);
        tabH.addTab(weekViewTab);
        
        TabSpec dayViewTab=tabH.newTabSpec("dayView");
        dayViewTab.setIndicator("",getResources().getDrawable(android.R.drawable.ic_menu_day));
 //       dayViewTab.setContent(R.id.calendar_day);
        Intent dayIntent = new Intent(this, dayViewClass.class);
        dayViewTab.setContent(dayIntent);
        tabH.addTab(dayViewTab);
        
        tabH.setCurrentTabByTag("listView");
        
    }

    
}