package com.corsework.notepad.entities.dao;

import java.util.ArrayList;
import java.util.Date;

import com.corsework.notepad.entities.program.Bell;
import com.corsework.notepad.entities.program.Note;
import com.corsework.notepad.entities.program.Reminder;
import com.corsework.notepad.entities.program.Sys;
import com.corsework.notepad.errors.Err;
import com.corsework.notepad.errors.LastErrors;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class ReminderDao {
	
	/**
	 * Helper for current class.
	 */
	private ReminderHelper dbHelper;
	
	private ReminderToBellDao reminderToBellDao;
	private BellDao bellDao;
	
	public ReminderDao(Context context) {
		this.dbHelper = new ReminderHelper(context);
		this.reminderToBellDao = new ReminderToBellDao(context);
		this.bellDao = new BellDao(context);
	}
	
	public Reminder create(Reminder reminder) {
		LastErrors.getInstance().clean();
		SQLiteDatabase db = this.dbHelper.getWritableDatabase();
		 
        ContentValues values = this.reminderToValues(reminder);
        long id = db.insert(dbHelper.TABLE_NAME, null, values);
        db.close();
        
        this.createBells(reminder);
        
        if (id == -1) {
        	LastErrors.getInstance().add(new Err("New record was not created."));
        	return null;
        } else {
        	Reminder rem = this.getById(id);
        	Log.d("Reminder created: ", rem.toString());
            return rem;
        }
	}
	
	private void createBells(Reminder reminder) {
		for (int i = 0; i < reminder.getBells().size(); ++i) {
			Bell bell = this.bellDao.create(reminder.getBells().get(i));
			this.reminderToBellDao.create(reminder, bell);
		}
	}
	
	public Reminder update(Reminder reminder) {
		LastErrors.getInstance().clean();
		Reminder rem;
		
		if (reminder.getId() == null) {
			rem = this.create(reminder);
		} else {
			this.deleteBells(reminder);
			
			SQLiteDatabase db = this.dbHelper.getWritableDatabase();
			 
			ContentValues values = this.reminderToValues(reminder);	 
	        int res = db.update(dbHelper.TABLE_NAME, values, dbHelper.COLUMN_ID + " = ?",
	                new String[] { String.valueOf(reminder.getId()) });
	        db.close();
	        
	        this.createBells(reminder);
	        
	        if (res != 1) {
	        	LastErrors.getInstance().add(new Err("Wrong number of rows were modified."));
	        	rem = null;
	        } else {
	        	rem = this.getById(reminder.getId());
	        	Log.d("Reminder updated: ", rem.toString());
	        }
		}		
		
		return rem;
	}
	
	public Reminder getById(Long id) {
		LastErrors.getInstance().clean();
		SQLiteDatabase db = this.dbHelper.getReadableDatabase();
		
		Cursor cursor = db.query(dbHelper.TABLE_NAME, null, dbHelper.COLUMN_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
		if (cursor == null) {
			LastErrors.getInstance().add(new Err("No Bell with such id was found"));
			return null;
		}
        cursor.moveToFirst();
        
        Reminder reminder = this.cursorToReminder(cursor);
        
        Log.d("Reminder getById", reminder.toString());        
        return reminder;
	}
	
	public void deleteById(Long id) {
		this.deleteBells(id);
		
		SQLiteDatabase db = this.dbHelper.getWritableDatabase();
        db.delete(dbHelper.TABLE_NAME, dbHelper.COLUMN_ID + " = ?",
                new String[] { String.valueOf(id) });
        db.close();
	}
	
	public void delete(Reminder reminder) {
		this.deleteById(reminder.getId());
	}
	
	private void deleteBells(Long reminderId) {
		ArrayList<Long> bellsIds = this.reminderToBellDao.getBellsIds(reminderId);
		this.bellDao.deleteByIds(bellsIds);
		this.reminderToBellDao.delete(reminderId);
	}
	
	private void deleteBells(Reminder reminder) {
		this.deleteBells(reminder.getId());
	}
	
	public ArrayList<Reminder> getAll() {
		LastErrors.getInstance().clean();
		SQLiteDatabase db = this.dbHelper.getReadableDatabase();
		ArrayList<Reminder> reminders = new ArrayList<Reminder>();
		
		Cursor cursor = db.query(dbHelper.TABLE_NAME, null, null,
                null, null, null, null, null);		
		if (cursor.moveToFirst()) {
            do {
            	Reminder reminder = this.cursorToReminder(cursor);
            	reminders.add(reminder);
            } while (cursor.moveToNext());
        }
                
        return reminders;
	}
	
	public ArrayList<Reminder> getByType(ArrayList<String> types) {
		LastErrors.getInstance().clean();
		SQLiteDatabase db = this.dbHelper.getReadableDatabase();
		ArrayList<Reminder> reminders = new ArrayList<Reminder>();
		
		for (int i = 0; i < types.size(); ++i) {
			Cursor cursor = db.query(dbHelper.TABLE_NAME, null, dbHelper.COLUMN_TYPE + "=?",
					new String[] { types.get(i) }, null, null, null, null);		
			if (cursor.moveToFirst()) {
	            do {
	            	Reminder reminder = this.cursorToReminder(cursor);
	            	reminders.add(reminder);
	            } while (cursor.moveToNext());
	        }
		}
		
		return reminders;
	}
	
	public ArrayList<Reminder> getByCrDate(Date from, Date to) {
		LastErrors.getInstance().clean();
		SQLiteDatabase db = this.dbHelper.getReadableDatabase();
		ArrayList<Reminder> reminders = new ArrayList<Reminder>();
		
		Cursor cursor = db.query(dbHelper.TABLE_NAME, null,
				dbHelper.COLUMN_CREATED + ">=?" + " and "
				+ dbHelper.COLUMN_CREATED + "<=?",
				new String[] { String.valueOf(from.getTime()),
				String.valueOf(to.getTime()) }, null, null, null, dbHelper.COLUMN_CREATED + " DESC");		
		if (cursor.moveToFirst()) {
            do {
                Reminder reminder = this.cursorToReminder(cursor);
            	reminders.add(reminder);
            } while (cursor.moveToNext());
        }
		
		return reminders;
	}

	public ArrayList<Reminder> getByMdDate(Date from, Date to) {
		LastErrors.getInstance().clean();
		SQLiteDatabase db = this.dbHelper.getReadableDatabase();
		ArrayList<Reminder> reminders = new ArrayList<Reminder>();
		
		Cursor cursor = db.query(dbHelper.TABLE_NAME, null,
				dbHelper.COLUMN_MODIFIED + ">=?" + " and "
				+ dbHelper.COLUMN_MODIFIED + "<=?",
				new String[] { String.valueOf(from.getTime()),
				String.valueOf(to.getTime()) }, null, null, null, dbHelper.COLUMN_MODIFIED + " DESC");		
		if (cursor.moveToFirst()) {
            do {
                Reminder reminder = this.cursorToReminder(cursor);
            	reminders.add(reminder);
            } while (cursor.moveToNext());
        }
		
		return reminders;
	}
	
	private ContentValues reminderToValues(Reminder reminder) {
		ContentValues values = new ContentValues();
    	values.put(dbHelper.COLUMN_CREATED, reminder.getSys().getCr().getTime());
    	values.put(dbHelper.COLUMN_MODIFIED, reminder.getSys().getMd().getTime());
    	values.put(dbHelper.COLUMN_TYPE, reminder.getType());
    	values.put(dbHelper.COLUMN_DESCRIPTION, reminder.getDescr());
    	values.put(dbHelper.COLUMN_START_DATE, reminder.getStrDate().getTime());
    	values.put(dbHelper.COLUMN_END_DATE, reminder.getEndDate().getTime());
    	values.put(dbHelper.COLUMN_PRIORITY, reminder.getPrior());
    	values.put(dbHelper.COLUMN_REPETITION, reminder.getRepetition());
        return values;
	}
	
	private Reminder cursorToReminder(Cursor cursor) {
		Long id = cursor.getLong(0);
		
		Date cr = new Date(cursor.getLong(1));
		Date md = new Date(cursor.getLong(2));
		Sys sys = new Sys(cr, md);
		
		String type = cursor.getString(3);
		String descr = cursor.getString(4);
		Date strDate = new Date(cursor.getLong(5));
		Date endDate = new Date(cursor.getLong(6));
		long prior = cursor.getLong(7);
		String repetition = cursor.getString(8);
		
		ArrayList<Long> bellsIds = this.reminderToBellDao.getBellsIds(id);
		ArrayList<Bell> bells = this.bellDao.getByIds(bellsIds);
		
		Reminder reminder = new Reminder(id, sys, type, descr, strDate,
				endDate, bells, prior, repetition);
		return reminder;
	}

}
