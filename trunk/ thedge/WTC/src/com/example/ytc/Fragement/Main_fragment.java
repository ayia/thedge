package com.example.ytc.Fragement;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;

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
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ProgressBar;

import com.costum.android.widget.LoadMoreListView;
import com.costum.android.widget.LoadMoreListView.OnLoadMoreListener;
import com.example.ytc.R;
import com.example.ytc.Yt_Video_player;
import com.example.ytc.clazz.Video;
import com.itconnect.inc.adapters.YouTubeAdapter;

public class Main_fragment extends Fragment {
	LoadMoreListView lv;
	int page = 1;
	private static String youtube_cahnnel;
	YouTubeAdapter you;
	ProgressBar pb;
	ArrayList<Video> vid = new ArrayList<Video>();

	public Main_fragment() {
	}

	public static Main_fragment newInstance(String ytuser) {
		Main_fragment fragment = new Main_fragment();
		youtube_cahnnel=ytuser;
		return fragment;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		final View view = inflater.inflate(R.layout.fragment_main, container,
				false);
		lv = (LoadMoreListView) view.findViewById(R.id.lv);
		pb=(ProgressBar) view.findViewById(R.id.progressbar);
		you = new YouTubeAdapter(view.getContext(), vid);
		lv.setAdapter(you);
		lv.setOnLoadMoreListener(new OnLoadMoreListener() {

			@Override
			public void onLoadMore() {
				// TODO Auto-generated method stub
				page = page + 10;
				new TheTask().execute();
			}
		});
		lv.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> myAdapter, View myView,
					int myItemInt, long mylng) {
				Intent myIntent = new Intent(getActivity(),
						Yt_Video_player.class);
				myIntent.putExtra("yt_id", vid.get(myItemInt)); // Optional
																			// parameters
				getActivity().startActivity(myIntent);
			}
		});
		
		new TheTask().execute();
		return view;
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);

	}

	public void getData() {
		HttpClient httpclient = new DefaultHttpClient();
		httpclient.getParams().setParameter(
				CoreProtocolPNames.PROTOCOL_VERSION, HttpVersion.HTTP_1_1);
		String urltoget= "https://gdata.youtube.com/feeds/api/users/"
				+ youtube_cahnnel
				+ "/uploads?&max-results=10&start-index=" + page 
				+ "&v=2&alt=jsonc";
		HttpGet request = new HttpGet(
				urltoget);
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
				String description=jsonObject.getString("description");
				String duration1 = jsonObject.getString("duration");
				String thumbUrl = jsonObject.getJSONObject("thumbnail")
						.getString("hqDefault");
				URL url1 = new URL(thumbUrl);
				Bitmap bmp = BitmapFactory.decodeStream(url1.openConnection()
						.getInputStream());
				String directurl = "";
				vid.add(new Video(id, title1, bmp, description, directurl, Integer
						.parseInt(duration1)));

			}
			//Collections.reverse(vid);
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
			lv.onLoadMoreComplete();
			lv.setVisibility(View.VISIBLE);
			pb.setVisibility(View.GONE);
			
			//you.removeDuplicated();
			you.notifyDataSetChanged();
		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			// pd.show();
		}

		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			getData();
			return null;
		}

	}

}
