package com.corsework.notepad.activity;

import com.corsework.notepad.activity.R;
import com.corsework.notepad.application.NotePadApplication;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class TimeNotification extends BroadcastReceiver {

	private NotificationManager nm;

	@Override
	public void onReceive(Context context, Intent intent) {
		nm = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
		String s="Notes",st = "See Notes";
		Long pos = 0L ;
		if (intent.hasExtra(NotePadApplication.KEY_ROWID)){
			s = intent.getExtras().getString(NotePadApplication.KEY_TITLE);
			st = intent.getExtras().getString(NotePadApplication.KEY_BODY);
			pos = intent.getExtras().getLong(NotePadApplication.KEY_ROWID);
		}
		Notification notification = new Notification(R.drawable.redhat,s,System.currentTimeMillis());
		
		Intent intentTl = new Intent(context,SeeReminderActivity.class);
		intentTl.putExtra(NotePadApplication.KEY_ROWID,pos);
		notification.setLatestEventInfo(context,s,st,
				PendingIntent.getActivity(context, 0, intentTl, PendingIntent.FLAG_CANCEL_CURRENT));
		notification.flags = Notification.DEFAULT_SOUND|Notification.DEFAULT_LIGHTS|Notification.FLAG_AUTO_CANCEL;
		nm.notify(1,notification);
		
		
	}
}
