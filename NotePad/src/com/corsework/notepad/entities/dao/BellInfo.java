package com.corsework.notepad.entities.dao;

public class BellInfo implements TableInfo {
	
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

	public String createQuery() {
		return "create table " + TABLE_NAME + "( " +
				COLUMN_ID + " integer primary key autoincrement, " +
				COLUMN_CREATED + " long, " +
				COLUMN_MODIFIED + " long, " +
				COLUMN_DATE + " long," +
				COLUMN_ACTIVE + " string,"+
				COLUMN_IDREM + " long );";
	}
	
	public BellInfo() {
	}

	public String tableName() {
		return TABLE_NAME;
	}

}
