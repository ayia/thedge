package com.tyolar.inc.musica.Services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.app.Service;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.os.PowerManager;
import com.tyolar.inc.musica.app2;
import com.tyolar.inc.musica.library.media.MediaListener;
import com.tyolar.inc.musica.model.song;

public class AudioPlaybackService extends Service implements
		MediaPlayer.OnPreparedListener, MediaPlayer.OnCompletionListener {
	private static ArrayList<song> songtoplay = new ArrayList<song>();
	private static int selectedtrackindex = 0;
	private static boolean Shuffle = false;
	private static String TAG = "Musica.Service";
	private static MediaPlayer mMediaPlayer;
	List<MediaListener> mediaListenerList = new ArrayList<>();;

	// indicates the state our service:
	enum State {
		Retrieving, // the MediaRetriever is retrieving music
		Stopped, // media player is stopped and not prepared to play
		Preparing, // media player is preparing...
		Playing, Paused
	};

	State mState = State.Retrieving;

	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onStart(Intent intent, int startId) {
		// TODO Auto-generated method stub
		super.onStart(intent, startId);
		if (mMediaPlayer == null) {
			InitMediaPlayer();
		}
		app2 mapp = (app2) getApplication();
		mapp.setMusicaService(this);

	}
	
	public void InitMediaPlayer(){
		mMediaPlayer = new MediaPlayer();
		mMediaPlayer.setWakeMode(getApplicationContext(),
				PowerManager.PARTIAL_WAKE_LOCK);
		mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
		mMediaPlayer.setOnPreparedListener(this);
		mMediaPlayer.setOnCompletionListener(this);
		
	}

	/**
	 * Play music streaming
	 * 
	 * @param mStreamURL
	 */
	public void play(String mStreamURL) {
		notifyPlayerLoading();
		if (mState == State.Paused) {
			mMediaPlayer.start();
			mState = State.Playing;
		} else {
			try {
				resetMediaPlayer();

				mMediaPlayer.setDataSource(mStreamURL);
				mMediaPlayer.prepareAsync();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Stop music streaming
	 */
	public void stop() {
		mMediaPlayer.stop();
		mState = State.Stopped;
		notifyPlayerStopped();

	}

	public void start() {
		mMediaPlayer.start();
		mState = State.Playing;
		notifyPlayerStarted(mMediaPlayer.getDuration(),
				mMediaPlayer.getCurrentPosition());

	}

	/**
	 * Pause music streaming
	 */
	public void pause() {
		mMediaPlayer.pause();
		mState = State.Paused;
		notifyPlayerStopped();
	}

	private void resetMediaPlayer() {
		mMediaPlayer.reset();
		mState = State.Preparing;
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();

	}

	/**
	 * REGISTERING AND NOTIFYING LISTENERS
	 */
	public void registerMediaListener(MediaListener mediaListener) {
		mediaListenerList.add(mediaListener);
	}

	public void unregisterMediaListener(MediaListener mediaListener) {
		if (mediaListenerList.contains(mediaListener))
			mediaListenerList.remove(mediaListener);
	}

	private void notifyPlayerStarted(int totalDuration, int currentDuration) {
		for (MediaListener mediaListener : mediaListenerList)
			mediaListener.onMediaStarted(totalDuration, currentDuration);
	}

	private void notifyPlayerStopped() {
		for (MediaListener mediaListener : mediaListenerList)
			mediaListener.onMediaStopped();
	}

	private void notifyPlayerLoading() {
		for (MediaListener mediaListener : mediaListenerList)
			mediaListener.onMediaLoading();
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
	@Override
	public void onCompletion(MediaPlayer mp) {
		// TODO Auto-generated method stub
		mState = State.Stopped;
		mp.stop();
		mp.reset();
	}

	@Override
	public void onPrepared(MediaPlayer mp) {
		// TODO Auto-generated method stub
		mState = State.Playing;
		mp.start();
	}

}
