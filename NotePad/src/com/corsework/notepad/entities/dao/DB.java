package com.corsework.notepad.entities.dao;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DB {
 
  private SQLiteDatabase database;
  public ArrayList<String> curTeg;
  
  public DB(Context ctx) {
    TegSQLiteOpenHelper helper = new TegSQLiteOpenHelper(ctx);
	database = helper.getWritableDatabase();
	if (null == curTeg){
		loadTeg();
	}
	
  }
  
  private void loadTeg() {
	  curTeg = new ArrayList<String>();
		Cursor tegCursor = database.query(TegSQLiteOpenHelper.TEGS_TABLE,
				new String[]{TegSQLiteOpenHelper.TEG_ID,TegSQLiteOpenHelper.TEG_TEXT},
				null,null,null,null,null);
		tegCursor.moveToFirst();
		if (!tegCursor.isAfterLast()){
			do{
				String name = tegCursor.getString(1);
				curTeg.add(name);
			}while (tegCursor.moveToNext());
		}
		tegCursor.close();
}
  public ArrayList<String> getAll() {
		ArrayList<String> tegs = new ArrayList<String>();
		
		Cursor cursor = database.query(TegSQLiteOpenHelper.TEGS_TABLE, null, null,
              null, null, null, null, null);		
		if (cursor.moveToFirst()) {
          do {
 	         String n = cursor.getString(1);
 	         tegs.add(n);
          } while (cursor.moveToNext());
      }
              
      return tegs;
	}
  public ArrayList<String> getAllCh() {
		ArrayList<String> tegs = new ArrayList<String>();
		
		Cursor cursor = database.query(TegSQLiteOpenHelper.TEGS_TABLE, null, null,
            null, null, null, null, null);		
		if (cursor.moveToFirst()) {
        do {
	         int n = cursor.getInt(2);
	         if (n==1)
	        	 tegs.add(cursor.getString(1));
        } while (cursor.moveToNext());
    }
            
    return tegs;
	}
  // получить все данные из таблицы DB_TABLE
  public Cursor getAllData() {
	  return database.query(TegSQLiteOpenHelper.TEGS_TABLE,
				new String[]{TegSQLiteOpenHelper.TEG_ID,TegSQLiteOpenHelper.TEG_TEXT,TegSQLiteOpenHelper.TEG_CHK},
				null,null,null,null,null);
		
  }
  
  // изменить запись в DB_TABLE
  public void changeRec(long id, String txt,int col,int iid) {
		ContentValues values = new ContentValues();
		values.put(TegSQLiteOpenHelper.TEG_TEXT, txt);
		values.put(TegSQLiteOpenHelper.TEG_CHK, 0);
		String where = String.format("%s = %d",TegSQLiteOpenHelper.TEG_ID,id);
		database.update(TegSQLiteOpenHelper.TEGS_TABLE, values, where, null);
		curTeg.set(iid, txt);
  }
  public void changeRec(int pos, boolean isChecked) {
      ContentValues cv = new ContentValues();
      cv.put(TegSQLiteOpenHelper.TEG_CHK, (isChecked) ? 1 : 0);
      database.update(TegSQLiteOpenHelper.TEGS_TABLE, cv,TegSQLiteOpenHelper.TEG_TEXT + " =?" , new String[]{curTeg.get(pos)});
  }
  public void addTeg(String n){
		assert(null!=n);
		ContentValues values = new ContentValues();
		values.put(TegSQLiteOpenHelper.TEG_TEXT, n);
		values.put(TegSQLiteOpenHelper.TEG_CHK, 0);
		long id =database.insert(TegSQLiteOpenHelper.TEGS_TABLE, null, values);
		if (id!=-1)
			curTeg.add(n);
	}
  
  public int delete(String n) {
      int res = database.delete(TegSQLiteOpenHelper.TEGS_TABLE, TegSQLiteOpenHelper.TEG_TEXT + " = ?",
              new String[] {n});
      
      if (res != 1) {
      	Log.e("error!!! Note.deleteById:", "Must delete only one row at once");
      } else {
      	Log.d("good.Teg deleted:", "teg = " + n);
      }
      curTeg.clear();
      loadTeg();
      return res;
	}
  public class TegSQLiteOpenHelper extends SQLiteOpenHelper {

		public static final int VERSION = 1;
		public static final String DB_NAME = "calteg.db";
		public static final String TEGS_TABLE = "tegs";
		public static final String TEG_ID = "_id";
		public static final String TEG_TEXT = "_text";
		public static final String TEG_CHK = "_check";
		
		public TegSQLiteOpenHelper(Context context) {
			super(context, DB_NAME, null, VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			createTable(db);
		}
		@Override
		public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
		}
		
		private void createTable(SQLiteDatabase db) {
			db.execSQL("create table " +TEGS_TABLE +" (" +
					TEG_ID + " integer primary key autoincrement not null," +
					TEG_TEXT + " text," +TEG_CHK + " integer" +
					");");

		}

	}
public void unChAll() {
	// TODO Auto-generated method stub
	 ContentValues cv = new ContentValues();
     cv.put(TegSQLiteOpenHelper.TEG_CHK, 0);
     database.update(TegSQLiteOpenHelper.TEGS_TABLE, cv,null, null);
     
}
}


