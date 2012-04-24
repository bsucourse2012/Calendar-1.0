package com.corsework.notepad.entities.dao;

import java.util.ArrayList;
import java.util.Date;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.corsework.notepad.entities.program.Bell;
import com.corsework.notepad.entities.program.Sys;
import com.corsework.notepad.errors.Err;
import com.corsework.notepad.errors.LastErrors;

public class BellDao {
	
	/**
	 * Helper for current class.
	 */
	private BellHelper dbHelper;
	
	public BellDao(Context context) {
		this.dbHelper = new BellHelper(context);
	}
	
	/**
	 * Creates new record in db-table and gets its id.
	 * @param bell Bell to save into table.
	 * @return The bell with it's id from the table,
	 * 				or null if record was not created.
	 */
	public Bell create(Bell bell) {
		LastErrors.getInstance().clean();
		SQLiteDatabase db = this.dbHelper.getWritableDatabase();		 
        ContentValues values = this.bellToValues(bell); 
        long id = db.insert(dbHelper.TABLE_NAME, null, values);
        db.close();
        
        if (id == -1) {
        	LastErrors.getInstance().add(new Err("New record was not created."));
        	return null;
        } else {
        	Bell bl = this.getById(id);
        	Log.d("Bell created: ", bl.toString());
            return bl;
        }
	}
	
	/**
	 * Updates or creates new record in db.
	 * @param bell Bell to update.
	 * @return Updates bell.
	 */
	public Bell update(Bell bell) {
		LastErrors.getInstance().clean();
		Bell bl;
		if (bell.getId() == null) {
			bl = this.create(bell);
		} else {
			SQLiteDatabase db = this.dbHelper.getWritableDatabase();
			 
			ContentValues values = this.bellToValues(bell);
	 
	        int res = db.update(dbHelper.TABLE_NAME, values, dbHelper.COLUMN_ID + " = ?",
	                new String[] { String.valueOf(bell.getId()) });
	        db.close();
	        if (res != 1) {
	        	LastErrors.getInstance().add(new Err("Wrong number of rows were modified."));
	        }
	        bl = bell;
		}
		
		Log.d("Bell updated: ", bl.toString());
		return bl;
	}
	
	/**
	 * Gets the bell from the table by its id.
	 * @param id The id of the bell to get.
	 * @return Bell with the id. Or null, if no bell was found.
	 */
	public Bell getById(long id) {
		LastErrors.getInstance().clean();
		SQLiteDatabase db = this.dbHelper.getReadableDatabase();
		
		Cursor cursor = db.query(dbHelper.TABLE_NAME, null, dbHelper.COLUMN_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
		if (cursor == null) {
			LastErrors.getInstance().add(new Err("No Bell with such id was found"));
			return null;
		}
        cursor.moveToFirst();
        
        Bell bell = this.cursorToBell(cursor);
        
        Log.d("Bell getById", bell.toString());        
        return bell;
	}
	
	public ArrayList<Bell> getByIds(ArrayList<Long> ids) {
		ArrayList<Bell> bells = new ArrayList<Bell>();
		for (int i = 0; i < ids.size(); ++i) {
			bells.add(this.getById(ids.get(i)));
		}
		return bells;
	}
	
	/**
	 * Gets all bells from db.
	 * @return ArrayList of Bells.
	 */
	public ArrayList<Bell> getAll() {
		LastErrors.getInstance().clean();
		SQLiteDatabase db = this.dbHelper.getReadableDatabase();
		ArrayList<Bell> bells = new ArrayList<Bell>();
		
		Cursor cursor = db.query(dbHelper.TABLE_NAME, null, null,
                null, null, null, null, null);		
		if (cursor.moveToFirst()) {
            do {
                Bell bell = this.cursorToBell(cursor);
                bells.add(bell);
            } while (cursor.moveToNext());
        }
                
        return bells;
	}
	
	public void deleteById(Long id) {
		SQLiteDatabase db = this.dbHelper.getWritableDatabase();
        db.delete(dbHelper.TABLE_NAME, dbHelper.COLUMN_ID + " = ?",
                new String[] { String.valueOf(id) });
        db.close();
	}
	
	public void delete(Bell bell) {
		this.deleteById(bell.getId());
	}
	
	public void deleteByIds(ArrayList<Long> ids) {
		for (int i = 0; i < ids.size(); ++i) {
			this.deleteById(ids.get(i));
		}
	}
	
	private Bell cursorToBell(Cursor cursor) {
		Long id = cursor.getLong(0);
		
		Date cr = new Date(cursor.getLong(1));
		Date md = new Date(cursor.getLong(2));
		Sys sys = new Sys(cr, md);
		
		Date date = new Date(cursor.getLong(3));
		
		Bell bell = new Bell(id, sys, date);
        return bell;
	}
	
	private ContentValues bellToValues(Bell bell) {
		ContentValues values = new ContentValues();
    	values.put(dbHelper.COLUMN_CREATED, bell.getSys().getCr().getTime());
    	values.put(dbHelper.COLUMN_MODIFIED, bell.getSys().getMd().getTime());
        values.put(dbHelper.COLUMN_DATE, bell.getDate().getTime());
        return values;
	}

}
