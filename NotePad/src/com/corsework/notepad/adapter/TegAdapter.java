package com.corsework.notepad.adapter;

import java.util.ArrayList;
import com.corsework.notepad.activity.R;
import com.corsework.notepad.view.TegListItem;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class TegAdapter extends BaseAdapter {
	
	private ArrayList<String> types;
	private Context context;
	int toggle;
	
	public TegAdapter(ArrayList<String> rec, Context context) {
		super();
		this.types = rec;
		this.context = context;
		toggle = -1;
	}

	public int getCount() {
		return types.size();
	}
	
	public String getItem(int pos) {
		return (null==types)? null : types.get(pos);
	}

	public long getItemId(int pos) {
		return pos;
	}

	public View getView(int pos, View convertView, ViewGroup parent) {
		TegListItem tli;
		if (null == convertView){
			tli = (TegListItem)View.inflate(context,R.layout.tegs_item, null);
		}else {
			tli =(TegListItem)convertView;
		}
		if (pos == toggle)
			tli.click(true);
		else tli.click(false);
		tli.setTeg(types.get(pos));
		return tli;
	}
	
	public void togglePosition(int position) {
		toggle = position;
		notifyDataSetChanged();
	}
	
	public int getTogglePosition() {
		return toggle;
	}

	public void forceReload(ArrayList<String> arrayList) {
		types=arrayList;
		toggle = -1;
		notifyDataSetChanged();
	}
	
	public String getPos() {
		return types.get(toggle);
	}
	
}
