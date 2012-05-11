package com.corsework.notepad.activity;

import com.corsework.notepad.entities.dao.DB;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

public class AddNewTegsActivity extends Activity {
	
	int seekR, seekG, seekB;
	SeekBar redSeekBar, greenSeekBar, blueSeekBar;
	TextView mScreen;
	EditText mNameTeg;
    
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_tegs);
        

        redSeekBar = (SeekBar)findViewById(R.id.seekBarRed);
		greenSeekBar = (SeekBar)findViewById(R.id.seekBarGreen);
		blueSeekBar = (SeekBar)findViewById(R.id.seekBarBlue);
		mScreen = (TextView)findViewById(R.id.textViewc);
		mNameTeg = (EditText)findViewById(R.id.title);

		redSeekBar.setOnSeekBarChangeListener(seekBarChangeListener);
		greenSeekBar.setOnSeekBarChangeListener(seekBarChangeListener);
		blueSeekBar.setOnSeekBarChangeListener(seekBarChangeListener);
		
		Button okButton = (Button)findViewById(R.id.okBut);
		Button cnButton = (Button)findViewById(R.id.cansBut);
		okButton.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				 Bundle bundle = new Bundle();

	                bundle.putString(DB.TegSQLiteOpenHelper.TEG_TEXT, mNameTeg.getText().toString());
//	                bundle.putInt(DB.TegSQLiteOpenHelper.TEG_COL, 0xff000000 + seekR * 0x10000 + seekG * 0x100
//	    					+ seekB);
	                Intent mIntent = new Intent();
	                mIntent.putExtras(bundle);
	                setResult(RESULT_OK, mIntent);
	                finish();
			}
		});
		cnButton.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				finish();
			}
		});
    }
    
	 SeekBar.OnSeekBarChangeListener seekBarChangeListener = 
			    new SeekBar.OnSeekBarChangeListener() {
			
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				// TODO Auto-generated method stub
				updateBackground();
			}

			
			public void onStartTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
			}

			
			public void onStopTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
			}
		};
	void updateBackground() {
			seekR = redSeekBar.getProgress();
			seekG = greenSeekBar.getProgress();
			seekB = blueSeekBar.getProgress();
			// меняем фон через формат RGB
			mScreen.setBackgroundColor(0xff000000 + seekR * 0x10000 + seekG * 0x100
					+ seekB);
		}
}
