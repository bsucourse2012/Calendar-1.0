package com.corsework.notepad.errors;

import java.util.ArrayList;

public class LastErrors {
	
	private static LastErrors _instance = null;    

    public static synchronized LastErrors getInstance() {
        if (_instance == null)
            _instance = new LastErrors();
        return _instance;
    }
    
    /**
     * Array of last, not read, errors.
     */
    private ArrayList<Err> arr;
    
    private LastErrors() {
    	arr = new ArrayList<Err>();
    }
    
    /**
     * Adds error to the list of unread errors.
     * @param error The error to add to list.
     */
    public void add(Err error) {
    	this.arr.add(error);
    }
    
    /**
     * Returns last unread errors, saves them to log 
     * and cleans array of last errors.
     * @return ArrayList<Error> of last errors.
     */
    public ArrayList<Err> getAll() {
    	ArrayList<Err> res = this.arr;
    	
    	// TODO Add to ErrorsLog.
    	this.arr = new ArrayList<Err>();
    	
    	return res;
    }
    
    /**
     * Cleans the list of last errors.
     */
    public void clean() {
    	this.arr = new ArrayList<Err>();
    }
    
    /**
     * Tells, whether any errors were
     * @return
     */
    public boolean hasErrors() {
    	return ( this.arr.size() == 0 );
    }

}
