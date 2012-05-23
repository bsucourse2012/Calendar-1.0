package com.corsework.notepad.entities.dao;

public class NoteInfo implements TableInfo {
	
	/**
	 * Names of columns in table.
	 */
	public static final String TABLE_NAME = "notes";
	public static final String COLUMN_ID = "_id";
	public static final String COLUMN_CREATED = "_created";
	public static final String COLUMN_MODIFIED = "_modified";
	public static final String COLUMN_TITLE = "title";
	public static final String COLUMN_TYPE = "type";
	public static final String COLUMN_CONTENT = "content";

	public String createQuery() {
		return "create table " + TABLE_NAME + "( " +
				COLUMN_ID + " integer primary key autoincrement, " +
				COLUMN_CREATED + " integer, " +
				COLUMN_MODIFIED + " integer, " +
				COLUMN_TITLE + " text, " +
				COLUMN_TYPE + " text, " +
				COLUMN_CONTENT + " text);";
	}
	
	public NoteInfo() {
	}
	
	public String tableName() {
		return TABLE_NAME;
	}

}
