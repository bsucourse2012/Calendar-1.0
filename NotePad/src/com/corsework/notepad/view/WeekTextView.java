package com.corsework.notepad.view;
import com.corsework.notepad.activity.R;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.TextView;

public class WeekTextView extends TextView {
	
	private Paint marginPaint;
	private Paint linePaint;
	private int paperColor;
	
	boolean f;
	
	public WeekTextView (Context context, AttributeSet attrs, int defStyle){
		super(context, attrs, defStyle);
		init();
	}

	public WeekTextView (Context context) {
		super(context);
		init();
	}
	
	public WeekTextView (Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}
	
	private void init() {
		// �������� ������ �� ������� ��������.
		Resources myResources = getResources();
//		// �������� ����� ��� ���������, ������� �� ����� ������������ � ������	onDraw.
//		marginPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
//		marginPaint.setColor(myResources.getColor(R.color.equp));
//		linePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
//		linePaint.setColor(myResources.getColor(R.color.equp));
		// �������� ���� ���� ��� ����� � ������ ������.
		paperColor = myResources.getColor(R.color.text_color);
		if (f)
			setBackgroundColor(paperColor);
	}
//	@Override
//	public void onDraw(Canvas canvas) {
//	// ... ��������� ���-���� �� ������ ��� ������� ... ]
//		// ������� ���� ��� �����
//		if (f)
//			canvas.drawColor(paperColor);
//		canvas.save();
//	super.onDraw(canvas);
//	// ... ��������� ���-���� �� ������ ��� ������� ... ]
//	}

	public boolean isF() {
		return f;
	}

	public void setF(boolean f) {
		this.f = f;
		if (f)
			setBackgroundColor(paperColor);
		else setBackgroundColor(Color.BLACK);
	}

}

