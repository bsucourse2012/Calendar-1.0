package com.corsework.notepad.entities.dao;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.util.Log;

public class Backuper {
	
	private static final String fromPath = "//data/data/com.corsework.notepad.activity/databases/";
	private static final String fromName = fromPath + DBHelper.DATABASE_NAME;
	
	private static final String backupPath = "//data/data/com.corsework.notepad.activity/databases/";
	private static final String backupName = backupPath + "[backup]" + DBHelper.DATABASE_NAME;
	
	private static Backuper _instance = null;

    private Backuper() {}

    public static synchronized Backuper getInstance() {
        if (_instance == null)
            _instance = new Backuper();
        return _instance;
    }
	
	/**
	 * Makes backup.
	 * @return Returns true if operation was successful.
	 */
	public boolean backup() {
		File oldDB = new File(backupName);
		if ( !oldDB.delete() ) {
			Log.d("bad. Backup failed...", "can not delete");
			//return false;
		}
		
		try {			
			InputStream input = new FileInputStream(fromName);
			File dir = new File(backupPath);
		 	dir.mkdir();
		 	OutputStream output = new FileOutputStream(backupName);
		 	
		 	byte[] buffer = new byte[1024];
		    int length;
		    while ((length = input.read(buffer))>0) {
		        output.write(buffer, 0, length);
		    }

		    output.flush();
		    output.close();
		    input.close();
		    
		    Log.d("good. Backup done!", "good");
			
			return true;
		} catch (FileNotFoundException e) {
			Log.d("error!!! Backup failed...", e.toString());
			return false;
		} catch (IOException e) {
			Log.d("error!!! Backup failed...", e.toString());
			return false;
		}
	}
	
	public boolean restore() {
		File oldDB = new File(fromName);
		if ( !oldDB.delete() ) {
			Log.d("bad. Restore failed...", "can not delete");
			//return false;
		}
		
		try {
			InputStream input = new FileInputStream(backupName);
		 	OutputStream output = new FileOutputStream(fromName);
		 	
		 	byte[] buffer = new byte[1024];
		    int length;
		    while ((length = input.read(buffer))>0) {
		        output.write(buffer, 0, length);
		    }

		    output.flush();
		    output.close();
		    input.close();
		    
		    Log.d("good. Restore done!", "good");
			
			return true;
		} catch (FileNotFoundException e) {
			Log.d("error!!! Restore failed...", e.toString());
			return false;
		} catch (IOException e) {
			Log.d("error!!! Restore failed...", e.toString());
			return false;
		}
	}

}
