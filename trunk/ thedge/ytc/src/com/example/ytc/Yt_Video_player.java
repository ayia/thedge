package com.example.ytc;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayer.ErrorReason;
import com.google.android.youtube.player.YouTubePlayer.PlaybackEventListener;
import com.google.android.youtube.player.YouTubePlayer.PlayerStateChangeListener;
import com.google.android.youtube.player.YouTubePlayer.Provider;
import com.google.android.youtube.player.YouTubePlayerView;

public class Yt_Video_player  extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener {
	 
		public  String API_KEY = "";
	 
		//http://youtu.be/<VIDEO_ID>
		public  String VIDEO_ID = "dKLftgvYsVU";
	 
		@Override
		protected void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			Intent intent = getIntent();
			VIDEO_ID= intent.getStringExtra("yt_id"); 
			/** attaching layout xml **/
			setContentView(R.layout.fragment_yt__video_player);
			
			API_KEY = getResources().getString(R.string.DEVELOPER_KEY);;
			/** Initializing YouTube player view **/
			YouTubePlayerView youTubePlayerView = (YouTubePlayerView) findViewById(R.id.youtube_view);
			youTubePlayerView.initialize(API_KEY, this);
	 
		}
	 
		@Override
		public void onInitializationFailure(Provider provider, YouTubeInitializationResult result) {
			Toast.makeText(this, "Failured to Initialize!", Toast.LENGTH_LONG).show();
		}
	 
		@Override
		public void onInitializationSuccess(Provider provider, YouTubePlayer player, boolean wasRestored) {
	 
			/** add listeners to YouTubePlayer instance **/
			player.setPlayerStateChangeListener(playerStateChangeListener);
			player.setPlaybackEventListener(playbackEventListener);
	 		
			  player.loadVideo(VIDEO_ID); 
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