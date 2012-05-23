package com.corsework.notepad.entities.dao;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class TagDao {
 
	/**
	 * Reference to the database.
	 */
	private SQLiteDatabase database;
	
	/**
	 * Information about the table.
	 */
	private TagInfo tableInfo;
	
	/**
	 * ArrayList of tags.
	 */
	public ArrayList<String> curTag;
  
	public TagDao(Context ctx) {
		tableInfo = new TagInfo();
		DBHelper dbHelper = new DBHelper(ctx);
		database = dbHelper.getWritableDatabase();
		if (null == curTag){
			loadTeg();
		}	
	}
  
	/**
	 * Loads all tags from the table to curTag.
	 */
	private void loadTeg() {
		curTag = new ArrayList<String>();
		Cursor tegCursor = database.query(tableInfo.TABLE_NAME,
				new String[]{tableInfo.COLUMN_ID, tableInfo.COLUMN_TEXT},
				null,null,null,null,null);
		tegCursor.moveToFirst();
		if (!tegCursor.isAfterLast()){
			do{
				String name = tegCursor.getString(1);
				curTag.add(name);
			}while (tegCursor.moveToNext());
		}
		tegCursor.close();
	}
  
	/**
	 * Gets all records from the table.
	 * @return Array of tags.
	 */
	public ArrayList<String> getAll() {
		ArrayList<String> tegs = new ArrayList<String>();
		
		Cursor cursor = database.query(tableInfo.TABLE_NAME, null, null,
				null, null, null, null, null);		
		if (cursor.moveToFirst()) {
			do {
				String n = cursor.getString(1);
				tegs.add(n);
			} while (cursor.moveToNext());
		}
              
		return tegs;
	}
	
	/**
	 * Gets marked tags from the table.
	 * @return Array of marked tags.
	 */
	public ArrayList<String> getAllCh() {
		ArrayList<String> tegs = new ArrayList<String>();
		
		Cursor cursor = database.query(tableInfo.TABLE_NAME, null, null,
            null, null, null, null, null);		
		if (cursor.moveToFirst()) {
			do {
				int n = cursor.getInt(2);
				if (n==1)
					tegs.add(cursor.getString(1));
        	} while (cursor.moveToNext());
		}
            
		return tegs;
	}
	
	/**
	 * Gets all data from the table.
	 * @return Cursor to the first record.
	 */
	public Cursor getAllData() {
		return database.query(tableInfo.TABLE_NAME,
				new String[]{tableInfo.COLUMN_ID, tableInfo.COLUMN_TEXT, tableInfo.COLUMN_CHK},
				null,null,null,null,null);
		
	}
  
	/**
	 * Changes record in the table.
	 * @param id ID of the record in the table.
	 * @param txt New value of the text.
	 * @param col TODO
	 * @param iid Index in the array curTag.
	 */
	public void changeRec(long id, String txt,int col,int iid) {
		ContentValues values = new ContentValues();
		values.put(tableInfo.COLUMN_TEXT, txt);
		values.put(tableInfo.COLUMN_CHK, 0);
		String where = String.format("%s = %d", tableInfo.COLUMN_ID, id);
		database.update(tableInfo.TABLE_NAME, values, where, null);
		curTag.set(iid, txt);
	}
	
	/**
	 * Sets the fild CHK of the record.
	 * @param pos
	 * @param isChecked
	 */
	public void changeRec(int pos, boolean isChecked) {
		ContentValues cv = new ContentValues();
		cv.put(tableInfo.COLUMN_CHK, (isChecked) ? 1 : 0);
		database.update(tableInfo.TABLE_NAME, cv, tableInfo.COLUMN_ID + " = " + (pos + 1), null);
	}
	
	/**
	 * Inserts new tag into database and into array curTag.
	 * @param n
	 */
	public void addTeg(String n){
		assert(null!=n);
		ContentValues values = new ContentValues();
		values.put(tableInfo.COLUMN_TEXT, n);
		values.put(tableInfo.COLUMN_CHK, 0);
		long id =database.insert(tableInfo.TABLE_NAME, null, values);
		if (id!=-1)
			curTag.add(n);
	}
  
	/**
	 * Deletes tag from the database.
	 * @param n Tag value to delete.
	 * @return Result of the operation.
	 */
	public int delete(String n) {
		int res = database.delete(tableInfo.TABLE_NAME, tableInfo.COLUMN_TEXT + " = ?",
              new String[] {n});
      
		if (res != 1) {
			Log.e("error!!! Note.deleteById:", "Must delete only one row at once");
		} else {
			Log.d("good.Teg deleted:", "teg = " + n);
		}
		curTag.clear();
		loadTeg();
		return res;
	}
	
	/**
	 * Sets all tags unchecked.
	 */
	public void unChAll() {
		ContentValues cv = new ContentValues();
		cv.put(tableInfo.COLUMN_CHK, 0);
		database.update(tableInfo.TABLE_NAME, cv,null, null);
	}
	
}


