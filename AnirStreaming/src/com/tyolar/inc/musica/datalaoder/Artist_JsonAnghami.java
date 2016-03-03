package com.tyolar.inc.musica.datalaoder;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;

import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TableLayout;

import com.tyolar.inc.musica.R;
import com.tyolar.inc.musica.app2;
import com.tyolar.inc.musica.composants.SearchListView;
import com.tyolar.inc.musica.model.album;
import com.tyolar.inc.musica.model.apiurls;
import com.tyolar.inc.musica.model.artist;
import com.tyolar.inc.musica.model.searchResult;
import com.tyolar.inc.musica.model.song;
import com.tyolar.inc.musica.utils.RestTemplate;

public class Artist_JsonAnghami extends AsyncTask<String, Void, searchResult> {
	private Exception exExceptionc = null;
	private static String query = "";
	private Context context;
	private TableLayout main_music;
	private View main;
	private View loading;
	private View resultzone;
	private View errorzone;
	private Exception s = null;
	private String selectedzrtist;

	public Artist_JsonAnghami(Context context, View m) {
		// TODO Auto-generated constructor stub
		super();
		this.context = context;
		this.main = m;
		this.main_music = (TableLayout) this.main.findViewById(R.id.listmusic);
		this.loading = this.main.findViewById(R.id.loading_view);
		this.resultzone = this.main.findViewById(R.id.resultzone);
		this.errorzone = this.main.findViewById(R.id.error_view);

	}

	private String getArtistData(String params) throws Exception {

		String url = new apiurls().getArtisturlView();
		final app2 mapp = (app2) context.getApplicationContext();
		url = url.replace("[sid]",
				mapp.getAngami_id()).replace(
				"[id]", params);
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
		nameValuePairs.add(new BasicNameValuePair("sid", mapp.getAngami_id()));
		nameValuePairs.add(new BasicNameValuePair("id", params));
		return RestTemplate.getResponse(url, nameValuePairs);

		// StringBuilder buf = new StringBuilder();
		// InputStream json =
		// context.getAssets().open("web/GETmusicdirectory.xml");
		// BufferedReader in = new BufferedReader(new InputStreamReader(json,
		// "UTF-8"));
		// String str;
		//
		// while ((str = in.readLine()) != null) {
		// buf.append(str);
		// }
		//
		// in.close();
		// return buf.toString();

	}

	private searchResult getResults(String params) {
		searchResult searchResult = new searchResult();
		try {
			String result = getArtistData(params);
			JSONObject jsonObj = null;
			jsonObj = XML.toJSONObject(result);
			jsonObj = jsonObj.getJSONObject("anghami-response");

			if (jsonObj.getString("status").equalsIgnoreCase("ok"))
				searchResult.setStatus(true);

			JSONObject jsonij = jsonObj.getJSONObject("directory");
			searchResult.setAlbumlist(getalbums(jsonij));
			searchResult.setSonglist(getsongs(jsonij));

		} catch (Exception e) {
			// TODO Auto-generated catch block
			s = e;
			 app2 mapp = (app2) context.getApplicationContext();
			 mapp.getInstance().trackException(e);
		}

		return searchResult;
	}

	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
	}

	private ArrayList<artist> getartiste(JSONObject jsonObj)
			throws JSONException {
		ArrayList<artist> list = new ArrayList<artist>();
		JSONArray artistjson = jsonObj.getJSONArray("artist");

		for (int i = 0; i < artistjson.length(); i++) {
			artist a = new artist();
			JSONObject jobject = artistjson.getJSONObject(i);

			a.setId(jobject.getString("id").toString());
			a.setArtistArt(jobject.getString("ArtistArt").toString());
			a.setName(jobject.getString("name").toString());
			a.setNumFollowers(jobject.getString("numFollowers").toString());
			list.add(a);
		}

		return list;

	}

	private ArrayList<song> getsongs(JSONObject jsonObj) throws JSONException {
		ArrayList<song> list = new ArrayList<song>();

		try {
			Object intervention = jsonObj.get("child");
			if (intervention instanceof JSONArray) {

				JSONArray artistjson = jsonObj.getJSONArray("child");

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
				// It's an object
				song a = new song();
				JSONObject jobject = jsonObj.getJSONObject("child");
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
				list.add(a);

			}
		} catch (Exception s) {
			 app2 mapp = (app2) context.getApplicationContext();
			 mapp.getInstance().trackException(s);
		}
		return list;

	}

	private ArrayList<album> getalbums(JSONObject jsonObj) throws JSONException {
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
			 app2 mapp = (app2) context.getApplicationContext();
			 mapp.getInstance().trackException(s);
		}
		return list;

	}

	@Override
	protected void onProgressUpdate(Void... values) {
		// TODO Auto-generated method stub
		this.loading.setVisibility(View.VISIBLE);
		this.resultzone.setVisibility(View.GONE);
		this.errorzone.setVisibility(View.GONE);
		super.onProgressUpdate(values);
	}

	@Override
	protected void onPostExecute(searchResult result) {
		// TODO Auto-generated method stub
		this.loading.setVisibility(View.GONE);
		if (s == null) {
			if (result.getSonglist().size() != 0) {
				SearchListView artistgrid = new SearchListView(context,
						context.getString(R.string.title_songs), 5, 1);
				artistgrid.setId(1);
				artistgrid.setSonglist(result);
				main_music.addView(artistgrid);
			}

			if (result.getAlbumlist().size() != 0) {
				SearchListView albumgrid = new SearchListView(context,
						context.getString(R.string.title_albyms), 1, 3);
				albumgrid.setId(2);
				albumgrid.setAlbumlist(result);

				main_music.addView(albumgrid);
			}

			this.loading.setVisibility(View.GONE);
			this.errorzone.setVisibility(View.GONE);
			this.resultzone.setVisibility(View.VISIBLE);

		} else {
			this.loading.setVisibility(View.GONE);
			this.errorzone.setVisibility(View.VISIBLE);
			
			this.errorzone.findViewById(R.id.connection_retry_btn).setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					new Artist_JsonAnghami(context,main).execute(selectedzrtist);
					
				}
			});
			
			
			this.resultzone.setVisibility(View.GONE);
		}

	}

	@Override
	protected searchResult doInBackground(String... params) {
		// TODO Auto-generated method stub
		this.selectedzrtist=params[0];
		return getResults(params[0]);
	}

}
