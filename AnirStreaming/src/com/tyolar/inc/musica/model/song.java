package com.tyolar.inc.musica.model;

import java.util.concurrent.TimeUnit;

import android.text.Html;

public class song {
	public String id, title, album, albumID, artist, artistID, year, duration,
			coverArt, ArtistArt;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return Html.fromHtml(title).toString();
	}

	public String getArtist() {
		return Html.fromHtml(artist).toString();
	}

	public void setArtist(String artist) {
		this.artist = artist;
	}

	public String getArtistID() {
		return artistID;
	}

	public void setArtistID(String artistID) {
		this.artistID = artistID;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAlbum() {
		return Html.fromHtml(album).toString();

	}

	public void setAlbum(String album) {
		this.album = album;
	}

	public String getAlbumID() {
		return albumID;
	}

	public void setAlbumID(String albumID) {
		this.albumID = albumID;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getDuration() {
//		  long l = Long.parseLong(duration);
//		 
//		return String.format("%d min, %d sec", 
//			    TimeUnit.MILLISECONDS.toMinutes(l),
//			    TimeUnit.MILLISECONDS.toSeconds(l) - 
//			    TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(l)
//			));
		
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	public String getCoverArt() {
		return coverArt;
	}

	public void setCoverArt(String coverArt) {
		this.coverArt = coverArt;
	}

	public String getArtistArt() {
		return ArtistArt;
	}

	public void setArtistArt(String artistArt) {
		ArtistArt = artistArt;
	}

}
