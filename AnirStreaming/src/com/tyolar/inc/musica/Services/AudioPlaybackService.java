package com.tyolar.inc.musica.Services;

import java.util.ArrayList;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.aocate.media.MediaPlayer;
import com.tyolar.inc.musica.app2;
import com.tyolar.inc.musica.model.song;

public class AudioPlaybackService extends Service{
	private static ArrayList<song> songtoplay = new ArrayList<song>();
	private static int selectedtrackindex = 0;
	private static boolean Shuffle = false;
	private static String TAG = "Musica.Service";
	private static MediaPlayer mMediaPlayer;

	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}
 
	@Override
	public void onStart(Intent intent, int startId) {
		// TODO Auto-generated method stub
		super.onStart(intent, startId);
		mMediaPlayer = new MediaPlayer(getBaseContext(),true);
		app2 mapp = (app2) getApplication();
		mapp.setMusicaService(this);
	
	}
	

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();

	}

	public static ArrayList<song> getSongtoplay() {
		return songtoplay;
	}

	public static void setSongtoplay(ArrayList<song> songtoplay) {
		AudioPlaybackService.songtoplay = songtoplay;
	}

	public static int getSelectedtrackindex() {
		return selectedtrackindex;
	}

	public static void setSelectedtrackindex(int selectedtrackindex) {
		AudioPlaybackService.selectedtrackindex = selectedtrackindex;
	}

	public static boolean isShuffle() {
		return Shuffle;
	}

	public static void setShuffle(boolean shuffle) {
		Shuffle = shuffle;
	}

	public static MediaPlayer getmMediaPlayer() {

		return mMediaPlayer;
	}

	public static void setmMediaPlayer(MediaPlayer mMediaPlayer) {
		AudioPlaybackService.mMediaPlayer = mMediaPlayer;
	}


	
	
}
