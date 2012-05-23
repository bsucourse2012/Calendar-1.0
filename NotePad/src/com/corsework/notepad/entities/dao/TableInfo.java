package com.corsework.notepad.entities.dao;

public interface TableInfo {
	
	/**
	 * Generates a query for table creation.
	 * @return String query.
	 */
	public abstract String createQuery();
	
	public abstract String tableName();

}
