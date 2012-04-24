package com.corsework.notepad.entities.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class NoteHelper extends SQLiteOpenHelper {
	
	public static final String TABLE_NAME = "notes";
	public static final String COLUMN_ID = "_id";
	public static final String COLUMN_CREATED = "_created";
	public static final String COLUMN_MODIFIED = "_modified";
	public static final String COLUMN_TITLE = "title";
	public static final String COLUMN_TYPE = "type";
	public static final String COLUMN_CONTENT = "content";
	
	private static final String DATABASE_NAME = "calendar.db";
	private static final int DATABASE_VERSION = 1;
	
	// Database creation sql statement
	private static final String TABLE_CREATE = 
			"create table " + TABLE_NAME + "( " +
			COLUMN_ID + " integer primary key autoincrement, " +
			COLUMN_CREATED + " integer, " +
			COLUMN_MODIFIED + " integer, " +
			COLUMN_TITLE + " text, " +
			COLUMN_TYPE + " text, " +
			COLUMN_CONTENT + " text);";
	
	public NoteHelper(Context context) {
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
