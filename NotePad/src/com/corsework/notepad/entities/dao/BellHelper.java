package com.corsework.notepad.entities.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class BellHelper extends SQLiteOpenHelper {
	
	/**
	 * Names of columns in table.
	 */
	public static final String TABLE_NAME = "bells";
	public static final String COLUMN_ID = "_id";
	public static final String COLUMN_CREATED = "_created";
	public static final String COLUMN_MODIFIED = "_modified";
	public static final String COLUMN_DATE = "date";
	
	/**
	 * Database info.
	 */
	private static final String DATABASE_NAME = "calbel.db";
	private static final int DATABASE_VERSION = 1;
	
	/**
	 * Database creation sql statement.
	 */
	private static final String TABLE_CREATE = 
			"create table " + TABLE_NAME + "( " +
			COLUMN_ID + " integer primary key autoincrement, " +
			COLUMN_CREATED + " integer, " +
			COLUMN_MODIFIED + " integer, " +
			COLUMN_DATE + " integer);";
	
	public BellHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}
	
	@Override
	public void onCreate(SQLiteDatabase database) {
		database.execSQL(TABLE_CREATE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
		onCreate(db);
	}

}
