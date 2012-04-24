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
	
	public void create(Long remId, Long bellId) {
		LastErrors.getInstance().clean();
		SQLiteDatabase db = this.dbHelper.getWritableDatabase();
		
        ContentValues values = new ContentValues();
        values.put(dbHelper.COLUMN_ID_FROM, remId);
        values.put(dbHelper.COLUMN_ID_TO, bellId);
        
        long id = db.insert(dbHelper.TABLE_NAME, null, values);
        db.close();
	}
	
	public void create(Reminder reminder, Bell bell) {
		this.create(reminder.getId(), bell.getId());
	}
	
	public void delete(Long reminderId) {
		SQLiteDatabase db = this.dbHelper.getWritableDatabase();
        db.delete(dbHelper.TABLE_NAME, dbHelper.COLUMN_ID_FROM + " = ?",
                new String[] { String.valueOf(reminderId) });
        db.close();
	}
	
	public void delete(Reminder reminder) {
		this.delete(reminder.getId());
	}
	
	public ArrayList<Long> getBellsIds(Long reminderId) {
		LastErrors.getInstance().clean();
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
	
	public ArrayList<Long> getBellsIds(Reminder reminder) {
		return this.getBellsIds(reminder.getId());
	}

}
