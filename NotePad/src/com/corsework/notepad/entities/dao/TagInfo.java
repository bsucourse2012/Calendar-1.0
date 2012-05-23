package com.corsework.notepad.entities.dao;

public class TagInfo implements TableInfo {
	
	/**
	 * Names of columns in table.
	 */
	public static final String TABLE_NAME = "tegs";
	public static final String COLUMN_ID = "_id";
	public static final String COLUMN_TEXT = "_text";
	public static final String COLUMN_CHK = "_check";

	public String createQuery() {
		return "create table " +TABLE_NAME +" (" +
				COLUMN_ID + " integer primary key autoincrement not null," +
				COLUMN_TEXT + " text," +
				COLUMN_CHK + " integer);";
	}

	public String tableName() {
		return TABLE_NAME;
	}

}
