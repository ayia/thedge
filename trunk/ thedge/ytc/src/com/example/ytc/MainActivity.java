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

import android.app.Activity;
import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SearchView;

import com.example.ytc.clazz.Video;
import com.itconnect.inc.adapters.YouTubeAdapter;

public class MainActivity extends Activity {
	ListView lv;
	ArrayList<Video> vid = new ArrayList<Video>();
	ProgressDialog pd;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		lv = (ListView) findViewById(R.id.lv);
		lv.setOnItemClickListener(new OnItemClickListener() {
		      public void onItemClick(AdapterView<?> myAdapter, View myView, int myItemInt, long mylng) {
		    	  Intent myIntent = new Intent(MainActivity.this, Yt_Video_player.class);
		    	  myIntent.putExtra("yt_id", vid.get(myItemInt).getYt_id()); //Optional parameters
		    	  MainActivity.this.startActivity(myIntent);
		      }                 
		  });
		pd = new ProgressDialog(MainActivity.this);
		pd.setTitle("Loading..");
		new TheTask().execute();

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		
		return true;
	}

	public void getData() {
		HttpClient httpclient = new DefaultHttpClient();
		httpclient.getParams().setParameter(
				CoreProtocolPNames.PROTOCOL_VERSION, HttpVersion.HTTP_1_1);
		HttpGet request = new HttpGet(
				"https://gdata.youtube.com/feeds/api/users/JustForLaughsTV/uploads?&max-results=20&v=2&alt=jsonc");
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
						.getString("sqDefault");
				URL url1 = new URL(thumbUrl);
				Bitmap bmp = BitmapFactory.decodeStream(url1.openConnection()
						.getInputStream());
				String directurl = jsonObject.getJSONObject("content").getString("1");
				vid.add(new Video(id, title1, bmp, "",directurl, Integer
						.parseInt(duration1)));

			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}

		httpclient.getConnectionManager().shutdown();
	}

	class TheTask extends AsyncTask<Void, Void, Void> {

		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			pd.dismiss();
			YouTubeAdapter you = new YouTubeAdapter(MainActivity.this, vid);
			lv.setAdapter(you);
		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			pd.show();
		}

		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			getData();
			return null;
		}

	}
}