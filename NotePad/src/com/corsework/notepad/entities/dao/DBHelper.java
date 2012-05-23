package com.corsework.notepad.entities.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
	
	/**
	 * Database info.
	 */
	private static final String DATABASE_NAME = "npd8";
	private static final int DATABASE_VERSION = 1;
	
	// Array of table classes.
	private Object[] tables = {new NoteInfo(), new BellInfo(),
			new ReminderInfo(), new TagInfo()};
	
	public DBHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		for (int i = 0; i < this.tables.length; ++i) {
			db.execSQL(((TableInfo)this.tables[i]).createQuery());
		}		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		for (int i = 0; i < this.tables.length; ++i) {
			db.execSQL("DROP TABLE IF EXISTS " + ((TableInfo)this.tables[i]).tableName());
		}
		onCreate(db);
	}

}
