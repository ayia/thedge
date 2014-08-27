package com.example.ytc.clazz;

import android.graphics.Bitmap;

public class Video {
	public String Yt_id;
	public String Titel;
	public Bitmap image;
	public String description;
	public String url_toplay;
	public int duration;
	
	public Video(String yt_id, String titel, Bitmap image, String description,
			String url_toplay, int duration) {
		super();
		Yt_id = yt_id;
		Titel = titel;
		this.image = image;
		this.description = description;
		this.url_toplay = url_toplay;
		this.duration = duration;
	}
	public String getYt_id() {
		return Yt_id;
	}
	public void setYt_id(String yt_id) {
		Yt_id = yt_id;
	}
	public String getTitel() {
		return Titel;
	}
	public void setTitel(String titel) {
		Titel = titel;
	}
	public Bitmap getImage() {
		return image;
	}
	public void setImage(Bitmap image) {
		this.image = image;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getUrl_toplay() {
		return url_toplay;
	}
	public void setUrl_toplay(String url_toplay) {
		this.url_toplay = url_toplay;
	}
	public int getDuration() {
		return duration;
	}
	public void setDuration(int duration) {
		this.duration = duration;
	}


}
