package com.tyolar.inc.musica.model;

import com.google.gson.Gson;

import android.text.Html;

public class album {
	public String id, title, artist, artistID, coverArt, ArtistArt, year,
			explicit, NbrSongs;

	public album() {

	}

	public album(String id, String title, String artist, String artistID,
			String coverArt, String artistArt) {
		super();
		this.id = id;
		this.title = title;
		this.artist = artist;
		this.artistID = artistID;
		this.coverArt = coverArt;
		ArtistArt = artistArt;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNbrSongs() {
		return NbrSongs;
	}

	public void setNbrSongs(String nbrSongs) {
		NbrSongs = nbrSongs;
	}

	public String getTitle() {
		return Html.fromHtml(title).toString();
	}

	public void setTitle(String title) {
		this.title = title;
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

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getExplicit() {
		return explicit;
	}

	public void setExplicit(String explicit) {
		this.explicit = explicit;
	}

	public String toJson() {
		return new Gson().toJson(this);
	}

}
