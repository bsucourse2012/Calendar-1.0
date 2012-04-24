package com.corsework.notepad.entities.dao;

import java.util.ArrayList;

import com.corsework.notepad.entities.program.Bell;
import com.corsework.notepad.entities.program.Reminder;
import com.corsework.notepad.errors.Err;
import com.corsework.notepad.errors.LastErrors;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class ReminderToBellDao {
	
	/**
	 * Helper for current class.
	 */
	private ReminderToBellHelper dbHelper;
	
	public ReminderToBellDao(Context context) {
		this.dbHelper = new ReminderToBellHelper(context);
	}
	
	/**
	 * Creates connection between reminder and bell.
	 * @param remId The id of the Reminder.
	 * @param bellId The id of the Bell.
	 * @return Id of the record in table,
	 * 		or -1, if something was wrong.
	 */
	public long create(Long remId, Long bellId) {
		SQLiteDatabase db = this.dbHelper.getWritableDatabase();
		
        ContentValues values = new ContentValues();
        values.put(dbHelper.COLUMN_ID_FROM, remId);
        values.put(dbHelper.COLUMN_ID_TO, bellId);
        
        long id = db.insert(dbHelper.TABLE_NAME, null, values);
        db.close();
        
        if (id == -1) {
        	Log.e("error!!! ReminderToDelDao.create", "Record was not created.");
        } else {
        	Log.d("good. ReminderToBell created:",
        			"id=" + id + ", from=" + remId + ", to=" + bellId);
        }
        return id;
	}
	
	/**
	 * Creates connection between reminder and bell.
	 * @param reminder
	 * @param bell
	 * @return Id of the record in table,
	 * 		or -1, if something was wrong.
	 */
	public long create(Reminder reminder, Bell bell) {
		if (reminder.getId() == null) {
			Log.e("error!!! ReminderToBell.create:", "Reminder has no id");
			return -1;
		} else if (bell.getId() == null) {
			Log.e("error!!! ReminderToBell.create:", "Bell has no id");
			return -1;
		} else {
			return this.create(reminder.getId(), bell.getId());
		}
	}
		
	/**
	 * Deletes all connections from the reminder.
	 * @param reminderId Id of the reminder.
	 * @return Number of deleted rows.
	 */
	public long delete(Long reminderId) {
		SQLiteDatabase db = this.dbHelper.getWritableDatabase();
        long res = db.delete(dbHelper.TABLE_NAME, dbHelper.COLUMN_ID_FROM + " = ?",
                new String[] { String.valueOf(reminderId) });
        db.close();
        return res;
	}
	
	/**
	 * Deletes all connections from the reminder.
	 * @param reminder
	 * @return Number of deleted rows.
	 */
	public void delete(Reminder reminder) {
		this.delete(reminder.getId());
	}
	
	/**
	 * Gets ids of bells, connected with the reminder.	
	 * @param reminderId Id of the reminder.
	 * @return Array of bells ids.
	 */
	public ArrayList<Long> getBellsIds(Long reminderId) {
		SQLiteDatabase db = this.dbHelper.getReadableDatabase();
		ArrayList<Long> ids = new ArrayList<Long>();
		
		Cursor cursor = db.query(dbHelper.TABLE_NAME, null, dbHelper.COLUMN_ID_FROM + "=?",
				new String[] { String.valueOf(reminderId) }, null, null, null, null);		
		if (cursor.moveToFirst()) {
            do {
                Long id = cursor.getLong(3);
                ids.add(id);
            } while (cursor.moveToNext());
        }
		
		return ids;
	}
	
	/**
	 * Gets ids of bells, connected with the reminder.	
	 * @param reminder
	 * @return Array of bells ids.
	 * 		If the id of the reminder == null, return empty Array.
	 */
	public ArrayList<Long> getBellsIds(Reminder reminder) {
		if (reminder.getId() == null) {
			return new ArrayList<Long>();
		} else {
			return this.getBellsIds(reminder.getId());
		}
	}

}
