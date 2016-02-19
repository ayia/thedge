package com.tyolar.inc.musica;

import java.util.HashMap;

import android.app.Activity;
import android.app.Application;
import android.app.NotificationManager;
import android.content.ComponentCallbacks;
import android.os.Bundle;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Tracker;
import com.tyolar.inc.musica.Services.AudioPlaybackService;
import com.tyolar.inc.musica.adapter.AudioWife;
import com.tyolar.inc.musica.model.PlayList;

public class app2 extends Application {
	private static AudioWife AudioWife = null;
	private static BaseActivity baseactivity;
	private static AudioPlaybackService MusicaService;
	private static PlayList playlist;
	private static String angami_id = "i2:dikcfiel:893o4q52493n6n41:ecdhdddidccgce:ZN:d:ra:n1.7.8:qqrq366snr";

	// change the following line
	private static final String PROPERTY_ID = "UA-38382183-6";

	public static int GENERAL_TRACKER = 0;

	public enum TrackerName {
		APP_TRACKER, GLOBAL_TRACKER, ECOMMERCE_TRACKER,
	}

	public HashMap<TrackerName, Tracker> mTrackers = new HashMap<TrackerName, Tracker>();

	@Override
	public void onCreate() {
		super.onCreate();
	
	}
	

	
	public synchronized Tracker getTracker(TrackerName appTracker) {
		if (!mTrackers.containsKey(appTracker)) {
			GoogleAnalytics analytics = GoogleAnalytics.getInstance(this);
			Tracker t = (appTracker == TrackerName.APP_TRACKER) ? analytics
					.newTracker(PROPERTY_ID)
					: (appTracker == TrackerName.GLOBAL_TRACKER) ? analytics
							.newTracker(R.xml.global_tracker) : analytics
							.newTracker(R.xml.ecommerce_tracker);
			mTrackers.put(appTracker, t);
		}
		return mTrackers.get(appTracker);
	}

	public static PlayList getPlaylist() {
		return playlist;
	}

	public static void setPlaylist(PlayList playlist) {
		app2.playlist = playlist;
	}

	public static String getAngami_id() {
		return angami_id;
	}

	public static void setAngami_id(String angami_id) {
		app2.angami_id = angami_id;
	}

	private Activity mCurrentActivity = null;

	public Activity getCurrentActivity() {
		return mCurrentActivity;
	}

	public void setCurrentActivity(Activity mCurrentActivity) {
		this.mCurrentActivity = mCurrentActivity;
	}

	public static BaseActivity getBaseactivity() {
		return baseactivity;
	}

	public static void setBaseactivity(BaseActivity baseactivity) {
		app2.baseactivity = baseactivity;
	}

	public static AudioWife getAudioWife() {
		return AudioWife;
	}

	public static void setAudioWife(AudioWife audioWife) {
		AudioWife = audioWife;
	}

	public static AudioPlaybackService getMusicaService() {
		return MusicaService;
	}

	public static void setMusicaService(AudioPlaybackService musicaService) {
		MusicaService = musicaService;
	}

}
