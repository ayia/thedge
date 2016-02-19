package com.tyolar.inc.musica.model;

import java.util.ArrayList;

public class searchResult {
	private boolean status=false;
	private ArrayList<artist> artistlist;
	private ArrayList<song> songlist;
	private ArrayList<album> albumlist;
	private String  title;
	
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public boolean isStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}
	public ArrayList<artist> getArtistlist() {
		return artistlist;
	}
	public void setArtistlist(ArrayList<artist> artistlist) {
		this.artistlist = artistlist;
	}
	public ArrayList<song> getSonglist() {
		return songlist;
	}
	public void setSonglist(ArrayList<song> songlist) {
		this.songlist = songlist;
	}
	public ArrayList<album> getAlbumlist() {
		return albumlist;
	}
	public void setAlbumlist(ArrayList<album> albumlist) {
		this.albumlist = albumlist;
	}
	
}
