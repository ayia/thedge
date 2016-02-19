package com.tyolar.inc.musica.model;

import com.google.gson.Gson;

import android.text.Html;

public class artist {

	public String id, name, ArtistArt, numFollowers;

	public artist(String id, String name, String artistArt) {
		super();
		this.id = id;
		this.name = name;
		ArtistArt = artistArt;
	}

	public artist() {
		// TODO Auto-generated constructor stub
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return Html.fromHtml(name).toString();
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getArtistArt() {
		return ArtistArt;
	}

	public void setArtistArt(String artistArt) {
		ArtistArt = artistArt;
	}

	public String getNumFollowers() {
		return numFollowers;
	}

	public void setNumFollowers(String numFollowers) {
		this.numFollowers = numFollowers;
	}

	public String toJson() {
		return new Gson().toJson(this);
	}

}
