package com.corsework.notepad.entities.dao3;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ReminderHelper extends SQLiteOpenHelper {

	/**
	 * Names of columns in table.
	 */
	public static final String TABLE_NAME = "reminders";
	public static final String COLUMN_ID = "_id";
	public static final String COLUMN_CREATED = "_created";
	public static final String COLUMN_MODIFIED = "_modified";
	public static final String COLUMN_TYPE = "type";
	public static final String COLUMN_DESCRIPTION = "description";
	public static final String COLUMN_START_DATE = "startDate";
	public static final String COLUMN_END_DATE = "endDate";
	public static final String COLUMN_PRIORITY = "priority";
	public static final String COLUMN_REPETITION = "repetition";
		
	/**
	 * Database info.
	 */
	private static final String DATABASE_NAME = DbOptions.DATABASE_NAME;
	private static final int DATABASE_VERSION = DbOptions.DATABASE_VERSION;
	
	/**
	 * Database creation sql statement.
	 */
	private static final String TABLE_CREATE = 
			"create table " + TABLE_NAME + "( " +
			COLUMN_ID + " integer primary key autoincrement, " +
			COLUMN_CREATED + " integer, " +
			COLUMN_MODIFIED + " integer, " +
			COLUMN_TYPE + " text, " +
			COLUMN_DESCRIPTION + " text, " +
			COLUMN_START_DATE + " integer, " +
			COLUMN_END_DATE + " integer, " +
			COLUMN_PRIORITY + " long, " +
			COLUMN_REPETITION + " text);";
	
	public ReminderHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}
	
	@Override
	public void onCreate(SQLiteDatabase database) {
		database.execSQL(TABLE_CREATE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//		db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
//		onCreate(db);
	}

}
