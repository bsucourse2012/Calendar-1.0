package com.corsework.notepad.view;

import com.corsework.notepad.activity.R;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.CheckedTextView;
import android.widget.LinearLayout;

public class TegListItem extends LinearLayout{

	CheckedTextView tegText;
	boolean isCh;
	
	public TegListItem(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}
	public void setTeg(String name) {
		tegText.setText(name);
		tegText.setChecked(isCh);
	}

	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();
		tegText = (CheckedTextView)findViewById(R.id.teg_text);
	}

	public void click(Boolean b) {
		isCh=b;
		tegText.setChecked(isCh);
	}
}
