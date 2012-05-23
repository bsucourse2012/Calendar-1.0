package com.corsework.notepad.activity;

import com.corsework.notepad.application.NotePadApplication;

import android.app.AlertDialog;
import android.app.TabActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.TabHost.TabSpec;

public class ViewNotePadActivity extends TabActivity {
	
	public TabHost tabH;

	SharedPreferences mNoteSPerf;
	EditText passwText;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        mNoteSPerf = getSharedPreferences(NotePadApplication.NOTE_PREFERENCES, Context.MODE_PRIVATE);
        if (mNoteSPerf.contains(NotePadApplication.NOTE_PREFERENCES_PASSWORD)){
        	passwD();
		}
        
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
        ((NotePadApplication)getApplication()).setTabH(tabH);
        
    }

    private void passwD() {
		// TODO Auto-generated method stub
    	 LayoutInflater infl = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
         final View passwordDialogLayout = infl.inflate(R.layout.enter_password, (ViewGroup) findViewById(R.id.root1));

         AlertDialog.Builder passwordDialogBuilder = new AlertDialog.Builder(this);
         passwordDialogBuilder.setView(passwordDialogLayout);
         final TextView passwText = (TextView) passwordDialogLayout.findViewById(R.id.EditText_Password);
         passwordDialogBuilder.setCancelable(false);
    	 final AlertDialog dialog;
         // установка кнопок и слушателей для них
         passwordDialogBuilder.setPositiveButton(android.R.string.ok, onClickListener_DialogResetPin);
         passwordDialogBuilder.setNeutralButton(android.R.string.cancel, onClickListener_DialogResetPin);
         // создание и показ диалога
         dialog = passwordDialogBuilder.create();
         dialog.show();
         // КОГДА ДИАЛОГ ПОКАЗАН изменяем слушателя для кнопки ОК
         // теперь диалог не будет закрывать при нажатии на кнопку ОК
         Button btnOK = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
         btnOK.setOnClickListener( new View.OnClickListener() {
        	 
             public void onClick(View arg0) {
            	 String passw = mNoteSPerf.getString(NotePadApplication.NOTE_PREFERENCES_PASSWORD, "");
                 String userPassword = passwText.getText().toString();
                 if (userPassword != null && userPassword.equals(passw)) {
                 	dialog.dismiss();
                 }
                 else {
                 	Toast.makeText(ViewNotePadActivity.this,R.string.settings_pwd_not_equal, Toast.LENGTH_LONG).show();
                 	//ViewNotePadActivity.this.finish();
                 }
             }
         });
	}
    DialogInterface.OnClickListener onClickListener_DialogResetPin =
            new DialogInterface.OnClickListener() {
        public void onClick(DialogInterface dialog, int which) {
        //	dialog.dismiss();
        	ViewNotePadActivity.this.finish();
        }
    };
    @Override
    public void onConfigurationChanged(Configuration newConfig) {  
        super.onConfigurationChanged(newConfig);  
    }
    
}