package com.corsework.notepad.entities.dao;

import android.graphics.Color;

public class Teg{
	String name;
	int color;
	long id;
	public Teg(){
		name = "def";
		color = Color.BLUE;
	}
	public Teg(String nam, int col){
		name = nam;
		color = col;
	}
	public Teg(String nam){
		name = nam;
		color = Color.BLUE;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getColor() {
		return color;
	}
	public void setColor(int color) {
		this.color = color;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
}