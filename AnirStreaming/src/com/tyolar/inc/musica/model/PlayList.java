package com.tyolar.inc.musica.model;

import java.util.ArrayList;

public class PlayList {
	
	private int id;
	private String name;
	private String description;
	private ArrayList<song> songs=null;
	
	
	public PlayList(int id, String name, String description,
			ArrayList<song> songs) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.songs = songs;
	}
	public int getId() {
		return id;
	}
	public void setID(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
		
	}
	public ArrayList<song> getSongs() {
		return songs;
	}
	public void setSongs(ArrayList<song> songs) {
		this.songs = songs;
	}

	
}
