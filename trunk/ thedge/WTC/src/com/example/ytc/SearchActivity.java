package com.example.ytc;

import java.net.URL;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.SearchManager;
import android.app.SearchableInfo;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener;


import com.example.ytc.clazz.Video;
import com.itconnect.inc.adapters.YouTubeAdapter;


@SuppressLint("NewApi")
public class SearchActivity extends ActionBarActivity implements OnQueryTextListener {
	ListView lv;
	YouTubeAdapter you;
	ArrayList<Video> vid = new ArrayList<Video>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_search_activity);
		lv = (ListView) findViewById(R.id.lvs);
		lv.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> myAdapter, View myView,
					int myItemInt, long mylng) {
				Intent myIntent = new Intent(SearchActivity.this,
						Yt_Video_player.class);
				myIntent.putExtra("yt_id", vid.get(myItemInt).getYt_id()); // Optional
																			// parameters
				SearchActivity.this.startActivity(myIntent);
			}
		});
		Intent intent = getIntent();
		String query = intent.getStringExtra("query");
		setTitle(query);
		String[] a = { query };
		new SearchTask().execute(a);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		getMenuInflater().inflate(R.menu.search_menu, menu);
		MenuItem searchItem = menu.findItem(R.id.searchview);
		SearchView searchView = (SearchView) MenuItemCompat
				.getActionView(searchItem);
  if (searchView != null) {
		     searchView.setOnQueryTextListener(this);
		  }
		return true;

	}

	public void search(CharSequence charSequence) {
		HttpClient httpclient = new DefaultHttpClient();
		httpclient.getParams().setParameter(
				CoreProtocolPNames.PROTOCOL_VERSION, HttpVersion.HTTP_1_1);
		HttpGet request = new HttpGet(
				"https://gdata.youtube.com/feeds/api/users/"
						+ getResources().getString(R.string.yt_use_id)
						+ "/uploads?&max-results=20&v=2&alt=jsonc&q="
						+ charSequence);
		try {
			HttpResponse response = httpclient.execute(request);
			HttpEntity resEntity = response.getEntity();
			String _response = EntityUtils.toString(resEntity); // content will
			JSONObject json = new JSONObject(_response);
			JSONArray jsonArray = json.getJSONObject("data").getJSONArray(
					"items");
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject jsonObject = jsonArray.getJSONObject(i);

				String title1 = jsonObject.getString("title");
				String id = jsonObject.getString("id");
				String duration1 = jsonObject.getString("duration");
				String thumbUrl = jsonObject.getJSONObject("thumbnail")
						.getString("hqDefault");
				URL url1 = new URL(thumbUrl);
				Bitmap bmp = BitmapFactory.decodeStream(url1.openConnection()
						.getInputStream());
				String directurl = jsonObject.getJSONObject("content")
						.getString("1");
				vid.add(new Video(id, title1, bmp, "", directurl, Integer
						.parseInt(duration1)));

			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}

		httpclient.getConnectionManager().shutdown();

	}

	class SearchTask extends AsyncTask<String, Void, Void> {

		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			you = new YouTubeAdapter(SearchActivity.this, vid);
			lv.setAdapter(you);

		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			// pd.show();
		}

		@Override
		protected Void doInBackground(String... params) {
			// TODO Auto-generated method stub
			search(params[0]);
			return null;
		}

	}

	@Override
	public boolean onQueryTextChange(String arg0) {
		// TODO Auto-generated method stub
		
		return false;
		
	}

	@Override
	public boolean onQueryTextSubmit(String arg0) {
		// TODO Auto-generated method stub
		String[] a = { arg0 };
		vid.clear();
		you.notifyDataSetChanged();
		new SearchTask().execute(a);
		return false;
	}

}
