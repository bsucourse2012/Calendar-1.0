package com.corsework.notepad.activity;


import android.app.TabActivity;
import android.content.ComponentName;
import android.content.ContentUris;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

public class ViewNotePadActivity extends TabActivity {
	
	private static final int MENU_ITEM_DELETE = Menu.FIRST;
	private static final int MENU_ITEM_INSERT = Menu.FIRST + 1;
	private static final int MENU_ITEM_SHARE = Menu.FIRST + 2;
	private static final int MENU_SEARCH = Menu.FIRST + 3;
	private static final int MENU_ITEM_EDIT_TAGS = Menu.FIRST + 4;
	private static final int MENU_SETTINGS = Menu.FIRST + 5;
	private static final int MENU_DISTRIBUTION_START = Menu.FIRST + 100; // MUST BE LAST

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        TabHost tabH=(TabHost)findViewById(android.R.id.tabhost);
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
    

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		
		menu.add(0, MENU_ITEM_INSERT, 0, R.string.add_note_button).setShortcut('1', 'i')
			.setIcon(android.R.drawable.ic_menu_add);
		//Show the delete icon when there is an actionbar
		
		menu.add(0, MENU_SEARCH, 0, R.string.menu_search).setShortcut('2',
				's').setIcon(android.R.drawable.ic_menu_search);

		menu.add(0, MENU_SETTINGS, 0, R.string.settings).setIcon(
				android.R.drawable.ic_menu_preferences).setShortcut('9', 's');

		return true;
	}

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		super.onPrepareOptionsMenu(menu);
			

		return true;
	}
}