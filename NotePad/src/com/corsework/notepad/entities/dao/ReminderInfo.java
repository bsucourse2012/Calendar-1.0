package com.corsework.notepad.entities.dao;

public class ReminderInfo implements TableInfo {
	
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

	public String createQuery() {
		return "create table " + TABLE_NAME + "( " +
				COLUMN_ID + " integer primary key autoincrement, " +
				COLUMN_CREATED + " integer, " +
				COLUMN_MODIFIED + " integer, " +
				COLUMN_TYPE + " text, " +
				COLUMN_DESCRIPTION + " text, " +
				COLUMN_START_DATE + " integer, " +
				COLUMN_END_DATE + " integer, " +
				COLUMN_PRIORITY + " long, " +
				COLUMN_REPETITION + " text);";
	}
	
	public ReminderInfo() {
	}
	
	public String tableName() {
		return TABLE_NAME;
	}

}
