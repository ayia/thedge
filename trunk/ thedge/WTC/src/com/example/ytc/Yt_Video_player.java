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

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.ytc.clazz.Video;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayer.ErrorReason;
import com.google.android.youtube.player.YouTubePlayer.PlaybackEventListener;
import com.google.android.youtube.player.YouTubePlayer.PlayerStateChangeListener;
import com.google.android.youtube.player.YouTubePlayer.Provider;
import com.google.android.youtube.player.YouTubePlayerView;
import com.itconnect.inc.adapters.YouTube_Related_Adapter;

public class Yt_Video_player extends YouTubeBaseActivity implements
		YouTubePlayer.OnInitializedListener {

	public String API_KEY = "";
	public Video selected_video;
	private String RelatedVideoUrl = "";
	YouTube_Related_Adapter you;
	ProgressBar pb;
	boolean fullScreen = false;
	YouTubePlayer mplayer;
	YouTubePlayerView youTubePlayerView;
	ArrayList<Video> vid = new ArrayList<Video>();
	private ListView relatedVideo;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Intent intent = getIntent();
		selected_video = (Video) intent.getExtras().getSerializable("yt_id");
		initUI(selected_video);

	}

	private void initUI(Video vIDEO_ID2) {
		// TODO Auto-generated method stub
		/** attaching layout xml **/
		selected_video = vIDEO_ID2;
		setContentView(R.layout.fragment_yt__video_player);
		API_KEY = getResources().getString(R.string.DEVELOPER_KEY);
		;
		/** Initializing YouTube player view **/
		youTubePlayerView = (YouTubePlayerView) findViewById(R.id.youtube_view);
		youTubePlayerView.initialize(API_KEY, this);
		findViewById(R.id.player_option_share).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						Intent intent = new Intent();
						intent.setAction(Intent.ACTION_SEND);
						intent.setType("image/*");
						Resources res = getResources();
						String text = String.format(
								res.getString(R.string.share_video_msg),
								selected_video.getTitel(),
								res.getString(R.string.app_name),
								"market://details?id="
										+ getApplicationContext()
												.getPackageName());
						intent.putExtra(Intent.EXTRA_TITLE,
								selected_video.getTitel());
						intent.putExtra(Intent.EXTRA_SUBJECT, text);
						intent.putExtra(Intent.EXTRA_STREAM,
								selected_video.getImage());

						startActivity(intent);
					}
				});
		initializeRelatedVideo();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		// TODO Auto-generated method stub
		if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
			mplayer.setFullscreen(true);
		} else
			mplayer.setFullscreen(false);
		// setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		super.onConfigurationChanged(newConfig);
	}

	@Override
	public void onBackPressed() {
		this.finish();
	}

	private void initializeRelatedVideo() {
		// TODO Auto-generated method stub
		pb = (ProgressBar) findViewById(R.id.progressbar);
		RelatedVideoUrl = "https://gdata.youtube.com/feeds/api/videos/"
				+ selected_video.getYt_id()
				+ "/related?v=2&max-results=5&alt=jsonc";
		relatedVideo = (ListView) findViewById(R.id.relatedVideo);
		relatedVideo.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> myAdapter, View myView,
					int myItemInt, long mylng) {
				initUI(vid.get(myItemInt)); // Optional

			}
		});

		new TheTask().execute();
	}

	public void getData() {
		HttpClient httpclient = new DefaultHttpClient();
		httpclient.getParams().setParameter(
				CoreProtocolPNames.PROTOCOL_VERSION, HttpVersion.HTTP_1_1);
		HttpGet request = new HttpGet(RelatedVideoUrl);
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
				String description = jsonObject.getString("description");
				String duration1 = jsonObject.getString("duration");
				String thumbUrl = jsonObject.getJSONObject("thumbnail")
						.getString("hqDefault");
				URL url1 = new URL(thumbUrl);
				Bitmap bmp = BitmapFactory.decodeStream(url1.openConnection()
						.getInputStream());
				String directurl = "";
				vid.add(new Video(id, title1, bmp, description, directurl,
						Integer.parseInt(duration1)));

			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}

		httpclient.getConnectionManager().shutdown();
	}

	@Override
	public void onInitializationFailure(Provider provider,
			YouTubeInitializationResult result) {
		Toast.makeText(this, "Failured to Initialize!", Toast.LENGTH_LONG)
				.show();
	}

	@Override
	public void onInitializationSuccess(Provider provider,
			YouTubePlayer player, boolean wasRestored) {

		/** add listeners to YouTubePlayer instance **/
		player.setPlayerStateChangeListener(playerStateChangeListener);
		player.setPlaybackEventListener(playbackEventListener);
		player.loadVideo(selected_video.getYt_id());
		if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
			player.setFullscreen(true);
		} else
			player.setFullscreen(false);

		mplayer = player;
	}

	class TheTask extends AsyncTask<Void, Void, Void> {

		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			you = new YouTube_Related_Adapter(getApplicationContext(), vid);
			relatedVideo.setAdapter(you);
			relatedVideo.setVisibility(View.VISIBLE);
			pb.setVisibility(View.GONE);

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

	private PlaybackEventListener playbackEventListener = new PlaybackEventListener() {

		@Override
		public void onBuffering(boolean arg0) {

		}

		@Override
		public void onPaused() {

		}

		@Override
		public void onPlaying() {

		}

		@Override
		public void onSeekTo(int arg0) {

		}

		@Override
		public void onStopped() {

		}

	};

	private PlayerStateChangeListener playerStateChangeListener = new PlayerStateChangeListener() {

		@Override
		public void onAdStarted() {

		}

		@Override
		public void onError(ErrorReason arg0) {

		}

		@Override
		public void onLoaded(String arg0) {

		}

		@Override
		public void onLoading() {
		}

		@Override
		public void onVideoEnded() {

		}

		@Override
		public void onVideoStarted() {

		}
	};
}