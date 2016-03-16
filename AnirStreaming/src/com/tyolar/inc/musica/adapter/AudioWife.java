/***
 ²* The MIT License (MIT)

 * Copyright (c) 2014 Jaydeep

 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:

 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.

 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.tyolar.inc.musica.adapter;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import android.content.Context;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.net.Uri;
import android.os.Handler;
import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.tyolar.inc.musica.R;
import com.tyolar.inc.musica.app2;
import com.tyolar.inc.musica.model.song;
import com.tyolar.inc.musica.utils.tools;

/***
 * A simple audio player wrapper for Android
 ***/
public class AudioWife {

	private static final String TAG = AudioWife.class.getSimpleName();

	private static AudioWife mAudioWife;

	/****
	 * Playback progress update time in milliseconds
	 ****/
	private static final int AUDIO_PROGRESS_UPDATE_TIME = 100;

	// TODO: externalize the error messages.
	private static final String ERROR_PLAYVIEW_NULL = "Play view cannot be null";
	private static final String ERROR_PLAYTIME_CURRENT_NEGATIVE = "Current playback time cannot be negative";
	private static final String ERROR_PLAYTIME_TOTAL_NEGATIVE = "Total playback time cannot be negative";

	private Handler mProgressUpdateHandler;

	private MediaPlayer mMediaPlayer;

	private SeekBar mSeekBar;
	public static Context context;
	private TextView mPlaybackTime;

	private View mPlayButton;
	private View mPauseButton;
	private TextView mRunTime;
	private View loadingView = null;

	private TextView mTotalTime;

	private boolean mHasDefaultUi;
	private ArrayList<OnCompletionListener> mCompletionListeners = new ArrayList<OnCompletionListener>();

	private ArrayList<View.OnClickListener> mPlayListeners = new ArrayList<View.OnClickListener>();

	private ArrayList<View.OnClickListener> mPauseListeners = new ArrayList<View.OnClickListener>();
	private static Uri mUri;

	public AudioWife(Context ctx) {
		this.context = ctx;
	}

	public static Uri getmUri() {
		return mUri;
	}

	public static void setmUri(Uri mUri) {
		AudioWife.mUri = mUri;
	}

	public static String getErrorPlaytimeCurrentNegative() {
		return ERROR_PLAYTIME_CURRENT_NEGATIVE;
	}

	public AudioWife getInstance() {
		return this;
	}

	private Runnable mUpdateProgress = new Runnable() {

		public void run() {

			if (mSeekBar == null) {
				return;
			}

			if (mProgressUpdateHandler != null && getMediaPlayer().isPlaying()) {
				mSeekBar.setProgress((int) getMediaPlayer()
						.getCurrentPosition());
				int currentTime = getMediaPlayer().getCurrentPosition();
				updatePlaytime(currentTime);
				updateRuntime(currentTime);
				// repeat the process
				mProgressUpdateHandler.postDelayed(this,
						AUDIO_PROGRESS_UPDATE_TIME);
			} else {
				// DO NOT update UI if the player is paused
			}
		}
	};

	public void play() {

		// if play button itself is null, the whole purpose of AudioWife is
		// defeated.
		if (mPlayButton == null) {
			throw new IllegalStateException(ERROR_PLAYVIEW_NULL);
		}

		if (mUri == null) {
			throw new IllegalStateException(
					"Uri cannot be null. Call init() before calling this method");
		}

		if (mMediaPlayer == null) {
			throw new IllegalStateException(
					"Call init() before calling this method");
		}

		if (getMediaPlayer().isPlaying()) {
			return;
		}

		mProgressUpdateHandler.postDelayed(mUpdateProgress,
				AUDIO_PROGRESS_UPDATE_TIME);

		// enable visibility of all UI controls.
		setViewsVisibility();
		app2 mapp = (app2) context.getApplicationContext();
		mapp.getBaseactivity().ShowMiniPlayer(
				mapp.getMusicaService().getSongtoplay()
						.get(mapp.getMusicaService().getSelectedtrackindex()));
		mapp.getMusicaService().start();
		setPausable();
	}

	public void updateUI() {
		mProgressUpdateHandler.postDelayed(mUpdateProgress,
				AUDIO_PROGRESS_UPDATE_TIME);

		// enable visibility of all UI controls.
		setViewsVisibility();

		if (getMediaPlayer().isPlaying()) {
			setPausable();
		} else {
			setPlayable();
		}

	}

	private void setViewsVisibility() {

		if (mSeekBar != null) {
			mSeekBar.setVisibility(View.VISIBLE);
		}

		if (mPlaybackTime != null) {
			mPlaybackTime.setVisibility(View.VISIBLE);
		}

		if (mRunTime != null) {
			mRunTime.setVisibility(View.VISIBLE);
		}

		if (mTotalTime != null) {
			mTotalTime.setVisibility(View.VISIBLE);
		}

		if (mPlayButton != null) {
			mPlayButton.setVisibility(View.VISIBLE);
		}

		if (mPauseButton != null) {
			mPauseButton.setVisibility(View.VISIBLE);
		}
	}

	public void pause() {

		if (getMediaPlayer() == null) {
			return;
		}

		if (getMediaPlayer().isPlaying()) {
			app2 mapp = (app2) context.getApplicationContext();
			mapp.getMusicaService().stop();
			setPlayable();
		
			mapp.getBaseactivity().ShowMiniPlayer(
					mapp.getMusicaService()
							.getSongtoplay()
							.get(mapp.getMusicaService()
									.getSelectedtrackindex()));
		}
	}

	@Deprecated
	private void updatePlaytime(int currentTime) {
		StringBuilder playbackStr = new StringBuilder();

		long totalDuration = 0;

		if (getMediaPlayer() != null) {
			try {
				totalDuration = getMediaPlayer().getDuration();
			} catch (Exception e) {
				app2 mapp = (app2) context.getApplicationContext();
				mapp.getInstance().trackException(e);
				e.printStackTrace();
			}
		}

		// set total time as the audio is being played
		if (totalDuration != 0) {
			playbackStr.append(String.format(
					"%02d:%02d",
					TimeUnit.MILLISECONDS.toMinutes((long) totalDuration),
					TimeUnit.MILLISECONDS.toSeconds((long) totalDuration)
							- TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS
									.toMinutes((long) totalDuration))));
		} else {
			Log.w(TAG, "Something strage this audio track duration in zero");
		}

		mTotalTime.setText(playbackStr);

		// DebugLog.i(currentTime + " / " + totalDuration);
	}

	private void updateRuntime(int currentTime) {

		if (mRunTime == null) {
			// this view can be null if the user
			// does not want to use it. Don't throw
			// an exception.
			return;
		}

		if (currentTime < 0) {
			throw new IllegalArgumentException(ERROR_PLAYTIME_CURRENT_NEGATIVE);
		}

		StringBuilder playbackStr = new StringBuilder();

		// set the current time
		// its ok to show 00:00 in the UI
		playbackStr.append(String.format(
				"%02d:%02d",
				TimeUnit.MILLISECONDS.toMinutes((long) currentTime),
				TimeUnit.MILLISECONDS.toSeconds((long) currentTime)
						- TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS
								.toMinutes((long) currentTime))));

		mRunTime.setText(playbackStr);

		// DebugLog.i(currentTime + " / " + totalDuration);
	}

	private void setTotalTime() {

		if (mTotalTime == null) {
			// this view can be null if the user
			// does not want to use it. Don't throw
			// an exception.
			return;
		}

		StringBuilder playbackStr = new StringBuilder();
		long totalDuration = 0;

		// by this point the media player is brought to ready state
		// by the call to init().
		if (getMediaPlayer() != null) {
			try {
				totalDuration = getMediaPlayer().getDuration();

			} catch (Exception e) {
				app2 mapp = (app2) context.getApplicationContext();
				mapp.getInstance().trackException(e);
				e.printStackTrace();
			}
		}

		if (totalDuration < 0) {
			throw new IllegalArgumentException(ERROR_PLAYTIME_TOTAL_NEGATIVE);
		}

		// set total time as the audio is being played
		if (totalDuration != 0) {
			playbackStr.append(String.format(
					"%02d:%02d",
					TimeUnit.MILLISECONDS.toMinutes((long) totalDuration),
					TimeUnit.MILLISECONDS.toSeconds((long) totalDuration)
							- TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS
									.toMinutes((long) totalDuration))));
		}

		mTotalTime.setText(playbackStr);
	}

	private void setPlayable() {
		if (mPlayButton != null) {
			mPlayButton.setVisibility(View.VISIBLE);
		}

		if (mPauseButton != null) {
			mPauseButton.setVisibility(View.GONE);
		}

	}

	/****
	 * Changes audio wife to enable pause functionality.
	 */
	private void setPausable() {
		if (mPlayButton != null) {
			mPlayButton.setVisibility(View.GONE);
		}

		if (mPauseButton != null) {
			mPauseButton.setVisibility(View.VISIBLE);
		}
	}

	public AudioWife init(Context ctx) {
		this.context = ctx;

		if (mAudioWife == null) {
			mAudioWife = new AudioWife(this.context);
		}
		return this;
	}

	public AudioWife init(Context ctx, Uri uri) {

		this.context = ctx;
		if (uri == null) {
			throw new IllegalArgumentException("Uri cannot be null");
		}

		if (mAudioWife == null) {
			mAudioWife = new AudioWife(ctx);
		}

		mUri = uri;

		mProgressUpdateHandler = new Handler();

		initPlayer(context);
		return this;
	}

	public AudioWife setPlayView(View play) {

		if (play == null) {
			throw new NullPointerException("PlayView cannot be null");
		}

		if (mHasDefaultUi) {
			Log.w(TAG,
					"Already using default UI. Setting play view will have no effect");
			return this;
		}

		mPlayButton = play;

		initOnPlayClick();
		return this;
	}

	private void initOnPlayClick() {
		if (mPlayButton == null) {
			throw new NullPointerException(ERROR_PLAYVIEW_NULL);
		}

		// add default click listener to the top
		// so that it is the one that gets fired first
		mPlayListeners.add(0, new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				play();
			}
		});

		// Fire all the attached listeners
		// when the play button is clicked
		mPlayButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				for (View.OnClickListener listener : mPlayListeners) {
					listener.onClick(v);

				}
			}
		});
	}

	public AudioWife setPauseView(View pause) {

		if (pause == null) {
			throw new NullPointerException("PauseView cannot be null");
		}

		if (mHasDefaultUi) {
			Log.w(TAG,
					"Already using default UI. Setting pause view will have no effect");
			return this;
		}

		mPauseButton = pause;

		initOnPauseClick();
		return this;
	}

	private void initOnPauseClick() {
		if (mPauseButton == null) {
			throw new NullPointerException("Pause view cannot be null");
		}

		// add default click listener to the top
		// so that it is the one that gets fired first
		mPauseListeners.add(0, new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				pause();
			}
		});

		// Fire all the attached listeners
		// when the pause button is clicked
		mPauseButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				for (View.OnClickListener listener : mPauseListeners) {
					listener.onClick(v);
				}
			}
		});
	}

	public AudioWife setPlaytime(TextView playTime) {

		if (mHasDefaultUi) {
			Log.w(TAG,
					"Already using default UI. Setting play time will have no effect");
			return this;
		}

		mPlaybackTime = playTime;

		// initialize the playtime to 0
		updatePlaytime(0);
		return this;
	}

	public AudioWife setRuntimeView(TextView currentTime) {

		if (mHasDefaultUi) {
			Log.w(TAG,
					"Already using default UI. Setting play time will have no effect");
			return this;
		}

		mRunTime = currentTime;

		// initialize the playtime to 0
		updateRuntime(0);
		return this;
	}

	public AudioWife setLoadingView(View ldl) {
		this.loadingView = ldl;
		return this;
	}

	public AudioWife setTotalTimeView(TextView totalTime) {

		if (mHasDefaultUi) {
			Log.w(TAG,
					"Already using default UI. Setting play time will have no effect");
			return this;
		}

		mTotalTime = totalTime;

		setTotalTime();
		return this;
	}

	private MediaPlayer getMediaPlayer() {
		if (mMediaPlayer == null) {

			final app2 mapp = (app2) context.getApplicationContext();
			mMediaPlayer = mapp.getMusicaService().getmMediaPlayer();
		}
		return mMediaPlayer;
	}

	public AudioWife setSeekBar(SeekBar seekbar) {

		if (mHasDefaultUi) {
			Log.w(TAG,
					"Already using default UI. Setting seek bar will have no effect");
			return this;
		}

		mSeekBar = seekbar;
		initMediaSeekBar();
		return this;
	}

	public AudioWife addOnCompletionListener(OnCompletionListener listener) {

		// add default click listener to the top
		// so that it is the one that gets fired first
		mCompletionListeners.add(0, listener);

		return this;
	}

	public AudioWife addOnPlayClickListener(View.OnClickListener listener) {

		mPlayListeners.add(listener);

		return this;
	}

	/***
	 * Add custom pause view click listener. Calling this method multiple times
	 * will queue up all the listeners and fire them all together when the event
	 * occurs.
	 ***/
	public AudioWife addOnPauseClickListener(View.OnClickListener listener) {

		mPauseListeners.add(listener);

		return this;
	}

	/****
	 * Initialize and prepare the audio player
	 ****/
	private void initPlayer(final Context ctx) {

		this.context = ctx;
		if (loadingView != null) {
			loadingView.setVisibility(View.VISIBLE);
		}
		final app2 mapp = (app2) ctx.getApplicationContext();

		final song d = mapp.getMusicaService().getSongtoplay()
				.get(mapp.getMusicaService().getSelectedtrackindex());
		getMediaPlayer().setOnCompletionListener(new OnCompletionListener() {

			@Override
			public void onCompletion(MediaPlayer arg0) {
				// TODO Auto-generated method stub
				// set UI when audio finished playing
				int currentPlayTime = 0;
				updateRuntime(currentPlayTime);
				// setPlayable();
				if (arg0.getCurrentPosition() > 13) {
					if (mapp.getMusicaService().getSelectedtrackindex() != mapp
							.getMusicaService().getSongtoplay().size() - 1) {
						mapp.getMusicaService()
								.setSelectedtrackindex(
										mapp.getMusicaService()
												.getSelectedtrackindex() + 1);

						mapp.getBaseactivity().updateList_Playsong(
								mapp.getMusicaService()
										.getSongtoplay()
										.get(mapp.getMusicaService()
												.getSelectedtrackindex()));

						if (mapp.isFullPlayerisshown == true)
							mapp.PlayerActivity.recreate();

					}
					mapp.getBaseactivity().ShowMiniPlayer(
							mapp.getMusicaService()
									.getSongtoplay()
									.get(mapp.getMusicaService()
											.getSelectedtrackindex()));

				}
				try {
					mapp.PlayerActivity.initializeViewsandPlay(mapp
							.getMusicaService()
							.getSongtoplay()
							.get(mapp.getMusicaService()
									.getSelectedtrackindex()));
					mSeekBar.setProgress((int) currentPlayTime);

				} catch (Exception d) {
					app2 mapp = (app2) context.getApplicationContext();
					mapp.getInstance().trackException(d);
				}

			}
		});
		getMediaPlayer().setOnPreparedListener(new OnPreparedListener() {

			@Override
			public void onPrepared(MediaPlayer mp) {
				// TODO Auto-generated method stub

				getMediaPlayer().start();
				mapp.getBaseactivity().ShowMiniPlayer(d);
				updateUI();
				initMediaSeekBar();
				updatePlaytime(0);
				if (loadingView != null) {
					loadingView.setVisibility(View.GONE);
				}
			}
		});

		new Handler().post(new Runnable() {
			@Override
			public void run() {
				// Code here will run in UI thread
				try {
					StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
							.permitAll().build();
					StrictMode.setThreadPolicy(policy);
					String urp = tools.getLocation(mUri.toString());
					getMediaPlayer().setDataSource(urp);
					getMediaPlayer().prepareAsync();
				} catch (Exception e) {

					mapp.getInstance().trackException(e);
					Toast.makeText(context,
							context.getResources().getString(R.string.error),
							Toast.LENGTH_LONG).show();
				}

			}
		});

	}

	private void initMediaSeekBar() {

		if (mSeekBar == null) {
			return;
		}

		// update seekbar
		long finalTime = getMediaPlayer().getDuration();
		mSeekBar.setMax((int) finalTime);

		mSeekBar.setProgress(0);

		mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				getMediaPlayer().seekTo(seekBar.getProgress());

				updateRuntime(seekBar.getProgress());
			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {

			}

			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {

			}
		});
	}

	private void fireCustomCompletionListeners(MediaPlayer mp) {
		for (OnCompletionListener listener : mCompletionListeners) {
			listener.onCompletion(mp);
		}
	}

	public AudioWife useDefaultUi(ViewGroup playerContainer,
			LayoutInflater inflater) {
		if (playerContainer == null) {
			throw new NullPointerException("Player container cannot be null");
		}

		if (inflater == null) {
			throw new IllegalArgumentException("Inflater cannot be null");
		}

		View playerUi = inflater.inflate(R.layout.aw_player, playerContainer);

		// init play view
		View playView = playerUi.findViewById(R.id.play);
		setPlayView(playView);

		// init pause view
		View pauseView = playerUi.findViewById(R.id.pause);
		setPauseView(pauseView);

		// init seekbar
		SeekBar seekBar = (SeekBar) playerUi.findViewById(R.id.media_seekbar);
		setSeekBar(seekBar);

		// init playback time view
		TextView playbackTime = (TextView) playerUi
				.findViewById(R.id.playback_time);
		setPlaytime(playbackTime);

		// this has to be set after all the views
		// have finished initializing.
		mHasDefaultUi = true;
		return this;
	}

	public void release() {

		if (mMediaPlayer != null) {
			mMediaPlayer.stop();
			mMediaPlayer.reset();
			mMediaPlayer.release();
			mMediaPlayer = null;
			app2 mapp = (app2) context.getApplicationContext();
			mapp.getMusicaService().InitMediaPlayer();
			mMediaPlayer = mapp.getMusicaService().getmMediaPlayer();

			mProgressUpdateHandler = null;
		}
	}
}
