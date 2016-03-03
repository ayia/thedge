package com.tyolar.inc.musica.utils;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;

import android.content.Context;
import android.graphics.Point;
import android.os.Build.VERSION;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;

import com.tyolar.inc.musica.R;
import com.tyolar.inc.musica.model.album;
import com.tyolar.inc.musica.model.artist;
import com.tyolar.inc.musica.model.searchResult;
import com.tyolar.inc.musica.model.song;

public class tools {
	public static String getcontent(String params, Context context)
			throws Exception {
		String tContents = "";
		URL yahoo = new URL(params);
		BufferedReader in = new BufferedReader(new InputStreamReader(
				yahoo.openStream()));
	
		String inputLine;

		while ((inputLine = in.readLine()) != null)
			tContents = tContents + inputLine;

		in.close();

		return tContents;

	}
	
	public static String getLocation(String urls)
			throws Exception  {
		URL url = new URL(urls);
		HttpURLConnection ucon = (HttpURLConnection) url.openConnection();
		ucon.setInstanceFollowRedirects(false);
		URL secondURL = new URL(ucon.getHeaderField("Location"));

		return secondURL.toString();

	}
	

	public static ArrayList<searchResult> getnewrelease(Context context,
			String params) throws Exception {
		ArrayList<searchResult> dc = new ArrayList<searchResult>();

		String result = getcontent(params, context);
		JSONObject jsonObj = null;
		jsonObj = XML.toJSONObject(result);
		jsonObj = jsonObj.getJSONObject("main-page");
		if (jsonObj.getJSONObject("Result").has("album-list")) {
			Object intervention = jsonObj.getJSONObject("Result").get(
					"album-list");

			if (intervention instanceof JSONObject) {
				searchResult searchResult = new searchResult();
				searchResult.setStatus(true);
				JSONObject jsonij = jsonObj.getJSONObject("Result")
						.getJSONObject("album-list");
				searchResult.setTitle(jsonij.getString("title"));
				searchResult.setAlbumlist(getalbums(jsonij));
				dc.add(searchResult);
			} else {
				JSONArray larray = jsonObj.getJSONObject("Result")
						.getJSONArray("album-list");
				for (int i = 0; i < larray.length(); i++) {
					JSONObject jsonij = larray.getJSONObject(i);
					searchResult searchResult = new searchResult();
					searchResult.setTitle(jsonij.getString("title"));
					searchResult.setAlbumlist(getalbums(jsonij));
					dc.add(searchResult);
				}

			}

		}
		if (jsonObj.getJSONObject("Result").has("song-list")) {
			Object intervention = jsonObj.getJSONObject("Result").get(
					"song-list");

			if (intervention instanceof JSONObject) {
				searchResult searchResult = new searchResult();
				searchResult.setStatus(true);
				JSONObject jsonij = jsonObj.getJSONObject("Result")
						.getJSONObject("song-list");
				searchResult.setTitle(jsonij.getString("title"));
				searchResult.setSonglist(getsongs(jsonij));
				dc.add(searchResult);
			} else {
				JSONArray larray = jsonObj.getJSONObject("Result")
						.getJSONArray("song-list");
				for (int i = 0; i < larray.length(); i++) {
					JSONObject jsonij = larray.getJSONObject(i);
					searchResult searchResult = new searchResult();
					searchResult.setTitle(jsonij.getString("title"));
					searchResult.setSonglist(getsongs(jsonij));
					dc.add(searchResult);
				}

			}

		}

		return dc;
	}

	public static ArrayList<searchResult> gettop_chart(Context context,
			String params) throws Exception {
		ArrayList<searchResult> dc = new ArrayList<searchResult>();

		String result = getcontent(params, context);
		JSONObject jsonObj = null;
		jsonObj = XML.toJSONObject(result);
		jsonObj = jsonObj.getJSONObject("main-page");
		if (jsonObj.getJSONObject("Result").has("album-list")) {
			Object intervention = jsonObj.getJSONObject("Result").get(
					"album-list");

			if (intervention instanceof JSONObject) {
				searchResult searchResult = new searchResult();
				searchResult.setStatus(true);
				JSONObject jsonij = jsonObj.getJSONObject("Result")
						.getJSONObject("album-list");
				searchResult.setTitle(jsonij.getString("title"));
				searchResult.setAlbumlist(getalbums(jsonij));
				dc.add(searchResult);
			} else {
				JSONArray larray = jsonObj.getJSONObject("Result")
						.getJSONArray("album-list");
				for (int i = 0; i < larray.length(); i++) {
					JSONObject jsonij = larray.getJSONObject(i);
					searchResult searchResult = new searchResult();
					searchResult.setTitle(jsonij.getString("title"));
					searchResult.setAlbumlist(getalbums(jsonij));
					dc.add(searchResult);
				}

			}

		}
		if (jsonObj.getJSONObject("Result").has("song-list")) {
			Object intervention = jsonObj.getJSONObject("Result").get(
					"song-list");

			if (intervention instanceof JSONObject) {
				searchResult searchResult = new searchResult();
				searchResult.setStatus(true);
				JSONObject jsonij = jsonObj.getJSONObject("Result")
						.getJSONObject("song-list");
				searchResult.setTitle(jsonij.getString("title"));
				searchResult.setSonglist(getsongs(jsonij));
				dc.add(searchResult);
			} else {
				JSONArray larray = jsonObj.getJSONObject("Result")
						.getJSONArray("song-list");
				for (int i = 0; i < larray.length(); i++) {
					JSONObject jsonij = larray.getJSONObject(i);
					searchResult searchResult = new searchResult();
					searchResult.setTitle(jsonij.getString("title"));
					searchResult.setSonglist(getsongs(jsonij));
					dc.add(searchResult);
				}

			}

		}

		return dc;
	}

	public static String getValue(String key, Context context) {
		String a = key;
		switch (key.toLowerCase()) {
		case "top_albums":
			a = context.getString(R.string.top_albums);
			break;
		case "top_songs":
			a = context.getString(R.string.top_songs);
			break;
		case "top_new_traks":
			a = context.getString(R.string.top_new_traks);
			break;
		case "titel_newreleases":
			a = context.getString(R.string.titel_newreleases);
			break;
		case "top_hot":
			a = context.getString(R.string.top_hot);
			break;
		case "top_hit":
			a = context.getString(R.string.top_hit);
			break;
		case "genre_alternative_ndie":
			a = context.getString(R.string.genre_alternative_ndie);
			break;
		case "genre_Blues":
			a = context.getString(R.string.genre_Blues);
			break;
		case "genre_child_music":
			a = context.getString(R.string.genre_child_music);
			break;
		case "genre_gospel":
			a = context.getString(R.string.genre_gospel);
			break;
		case "genre_classical":
			a = context.getString(R.string.genre_classical);
			break;
		case "genre_comedy":
			a = context.getString(R.string.genre_comedy);
			break;
		case "genre_country":
			a = context.getString(R.string.genre_country);
			break;
		case "genre_dance":
			a = context.getString(R.string.genre_dance);
			break;
		case "genre_folk":
			a = context.getString(R.string.genre_folk);
			break;
		case "genre_rap":
			a = context.getString(R.string.genre_rap);
			break;
		case "genre_holiday":
			a = context.getString(R.string.genre_holiday);
			break;
		case "genre_jazz":
			a = context.getString(R.string.genre_jazz);
			break;
		case "genre_pop":
			a = context.getString(R.string.genre_pop);
			break;
		case "genre_metal":
			a = context.getString(R.string.genre_metal);
			break;
		case "genre_soul":
			a = context.getString(R.string.genre_soul);
			break;
		case "genre_reggae":
			a = context.getString(R.string.genre_reggae);
			break;
		case "genre_rock":
			a = context.getString(R.string.genre_rock);
			break;
		case "genre_soundtracks":
			a = context.getString(R.string.genre_soundtracks);
			break;
		case "genre_vocal":
			a = context.getString(R.string.genre_vocal);
			break;
		case "genre_world":
			a = context.getString(R.string.genre_world);
			break;

		default:
			a = key;
		}
		return a;
	}

	private static ArrayList<artist> getartiste(JSONObject jsonObj) {
		ArrayList<artist> list = new ArrayList<artist>();
		try {
			Object intervention = jsonObj.get("artist");
			if (intervention instanceof JSONArray) {
				JSONArray artistjson = jsonObj.getJSONArray("artist");

				for (int i = 0; i < artistjson.length(); i++) {
					artist a = new artist();
					JSONObject jobject = artistjson.getJSONObject(i);

					a.setId(jobject.getString("id").toString());
					if (jobject.has("ArtistArt"))
						a.setArtistArt(jobject.getString("ArtistArt")
								.toString());
					a.setName(jobject.getString("name").toString());
					if (jobject.has("numFollowers"))
						a.setNumFollowers(jobject.getString("numFollowers")
								.toString());
					list.add(a);
				}
			} else if (intervention instanceof JSONObject) {
				artist a = new artist();
				JSONObject jobject = jsonObj.getJSONObject("artist");

				a.setId(jobject.getString("id").toString());
				if (jobject.has("ArtistArt"))
					a.setArtistArt(jobject.getString("ArtistArt").toString());
				a.setName(jobject.getString("name").toString());
				if (jobject.has("numFollowers"))
					a.setNumFollowers(jobject.getString("numFollowers")
							.toString());
				list.add(a);
			}

		} catch (Exception s) {
			
		}
		return list;

	}

	private static ArrayList<song> getsongs(JSONObject jsonObj)
			throws JSONException {
		ArrayList<song> list = new ArrayList<song>();
		try {
			Object intervention = jsonObj.get("song");
			if (intervention instanceof JSONArray) {
				JSONArray artistjson = jsonObj.getJSONArray("song");
				for (int i = 0; i < artistjson.length(); i++) {
					song a = new song();
					JSONObject jobject = artistjson.getJSONObject(i);
					a.setId(jobject.getString("id").toString());
					a.setTitle(jobject.getString("title").toString());
					if (jobject.has("album"))
						a.setAlbum(jobject.getString("album").toString());
					if (jobject.has("albumID"))
						a.setAlbumID(jobject.getString("albumID").toString());
					if (jobject.has("artist"))
						a.setArtist(jobject.getString("artist").toString());
					if (jobject.has("artistID"))
						a.setArtistID(jobject.getString("artistID").toString());
					if (jobject.has("year"))
						a.setYear(jobject.getString("year").toString());
					if (jobject.has("duration"))
						a.setDuration(jobject.getString("duration").toString());
					if (jobject.has("coverArt"))
						a.setCoverArt(jobject.getString("coverArt").toString());
					if (jobject.has("ArtistArt"))
						a.setArtistArt(jobject.getString("ArtistArt")
								.toString());
					list.add(a);
				}
			} else if (intervention instanceof JSONObject) {
				song a = new song();
				JSONObject jobject = jsonObj.getJSONObject("song");
				a.setId(jobject.getString("id").toString());
				a.setTitle(jobject.getString("title").toString());
				if (jobject.has("album"))
					a.setAlbum(jobject.getString("album").toString());
				if (jobject.has("albumID"))
					a.setAlbumID(jobject.getString("albumID").toString());
				if (jobject.has("artist"))
					a.setArtist(jobject.getString("artist").toString());
				if (jobject.has("artistID"))
					a.setArtistID(jobject.getString("artistID").toString());
				if (jobject.has("year"))
					a.setYear(jobject.getString("year").toString());
				if (jobject.has("duration"))
					a.setDuration(jobject.getString("duration").toString());
				if (jobject.has("coverArt"))
					a.setCoverArt(jobject.getString("coverArt").toString());
				if (jobject.has("ArtistArt"))
					a.setArtistArt(jobject.getString("ArtistArt").toString());
			}
		} catch (Exception s) {
		}
		return list;

	}

	private static ArrayList<album> getalbums(JSONObject jsonObj)
			throws JSONException {
		ArrayList<album> list = new ArrayList<album>();

		try {
			Object intervention = jsonObj.get("album");
			if (intervention instanceof JSONArray) {

				JSONArray artistjson = jsonObj.getJSONArray("album");

				for (int i = 0; i < artistjson.length(); i++) {
					album a = new album();
					JSONObject jobject = artistjson.getJSONObject(i);
					a.setId(jobject.getString("id").toString());
					a.setTitle(jobject.getString("title").toString());
					if (jobject.has("artist"))
						a.setArtist(jobject.getString("artist").toString());
					if (jobject.has("artistID"))
						a.setArtistID(jobject.getString("artistID").toString());
					if (jobject.has("NbrSongs"))
						a.setNbrSongs(jobject.getString("NbrSongs").toString());
					if (jobject.has("year"))
						a.setYear(jobject.getString("year").toString());
					if (jobject.has("coverArt"))
						a.setCoverArt(jobject.getString("coverArt").toString());
					if (jobject.has("ArtistArt"))
						a.setArtistArt(jobject.getString("ArtistArt")
								.toString());
					list.add(a);

				}
			} else if (intervention instanceof JSONObject) {
				album a = new album();
				JSONObject jobject = jsonObj.getJSONObject("album");

				a.setId(jobject.getString("id").toString());
				a.setTitle(jobject.getString("title").toString());
				if (jobject.has("artist"))
					a.setArtist(jobject.getString("artist").toString());
				if (jobject.has("artistID"))
					a.setArtistID(jobject.getString("artistID").toString());
				if (jobject.has("NbrSongs"))
					a.setNbrSongs(jobject.getString("NbrSongs").toString());
				if (jobject.has("year"))
					a.setYear(jobject.getString("year").toString());
				if (jobject.has("coverArt"))
					a.setCoverArt(jobject.getString("coverArt").toString());
				if (jobject.has("ArtistArt"))
					a.setArtistArt(jobject.getString("ArtistArt").toString());
				list.add(a);
			}

		} catch (Exception s) {
		}
		return list;

	}

	public static String m2139b(String str) {
		InputStream bufferedInputStream;
		Exception e;
		String str2 = null;
		try {
			
			URL url = new URL(str);
			bufferedInputStream = new BufferedInputStream(url.openStream());
			str2 = readFully(bufferedInputStream);
	
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
	
		return str2;

	}
	


	
	private static String readFully(InputStream inputStream)
	        throws IOException {
	    return new String(readFullyString(inputStream));
	}    

	private static byte[] readFullyString(InputStream inputStream)
	        throws IOException {
	    ByteArrayOutputStream baos = new ByteArrayOutputStream();
	    byte[] buffer = new byte[1024];
	    int length = 0;
	    while ((length = inputStream.read(buffer)) != -1) {
	        baos.write(buffer, 0, length);
	    }
	    return baos.toByteArray();
	}

}