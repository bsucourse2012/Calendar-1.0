package com.corsework.notepad.entities.dao3;

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
	public static final String COLUMN_ACTIVE = "activ";
	public static final String COLUMN_IDREM = "idreminder";
	
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
			COLUMN_CREATED + " long, " +
			COLUMN_MODIFIED + " long, " +
			COLUMN_DATE + " long," +
			COLUMN_ACTIVE + " string,"+
			COLUMN_IDREM + " long );";
	
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
