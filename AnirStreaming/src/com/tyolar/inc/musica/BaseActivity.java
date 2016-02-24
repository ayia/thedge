package com.tyolar.inc.musica;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.FragmentTransaction;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu.OnMenuItemClickListener;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;
import com.tyolar.inc.musica.DAO.PlayListDAO;
import com.tyolar.inc.musica.Services.AudioPlaybackService;
import com.tyolar.inc.musica.adapter.PlayListSongAdapter;
import com.tyolar.inc.musica.composants.LocalePopupMenu;
import com.tyolar.inc.musica.fragments.AlbumFragment;
import com.tyolar.inc.musica.fragments.ArtistFragment;
import com.tyolar.inc.musica.model.album;
import com.tyolar.inc.musica.model.apiurls;
import com.tyolar.inc.musica.model.artist;
import com.tyolar.inc.musica.model.song;

@SuppressLint("NewApi")
public class BaseActivity extends ActionBarActivity {
	private String titel;
	private Menu _menu = null;
	// private MusicService musicSrv;
	private Intent playIntent;
	private boolean musicBound = false;
	private Handler mHandler = new Handler();
	RemoteViews notificationView;

	// private Mini_Player__Fragment mControlsFragment;
	app2 mapp;

	@Override
	protected void onStart() {
		super.onStart();
		mapp = (app2) getApplication();

	}

	public void letsplayagain() {
		// TODO Auto-generated method stub

		if (mapp.getAudioWife().getInstance() != null) {

			mapp.getAudioWife().getInstance().updateUI();

		}

	}

	public void playSong(final song d) {

		String uri = apiurls.getAudiourl();

		uri = uri.replace("[id]", d.getId()).replace("[sid]",
				mapp.getAngami_id());
		final Uri myUri = Uri.parse(uri);
		if (mapp.getAudioWife().getInstance() != null) {
			mapp.getAudioWife().getInstance().release();
		}
		mapp.getAudioWife().getInstance().init(BaseActivity.this, myUri);
	}

	protected void onResume() {
		super.onResume();
		mapp.setCurrentActivity(this);

	}

	public void initMiniPlayer(final song d) {
		// findViewById(R.id.bottom_linear_layout_id).setVisibility(View.VISIBLE);
		if (mapp.getMusicaService().getSongtoplay().contains(d))
			mapp.getMusicaService().setSelectedtrackindex(
					mapp.getMusicaService().getSongtoplay().indexOf(d));
		else {
			mapp.getMusicaService().getSongtoplay().add(d);
			mapp.getMusicaService().setSelectedtrackindex(
					mapp.getMusicaService().getSongtoplay().size() - 1);
		}
		if (mapp.isFirstPlayerView()) {
			mapp.setFirstPlayerView(false);
			Intent myIntent = new Intent(BaseActivity.this,
					com.tyolar.inc.musica.PlayerActivity.class);
			BaseActivity.this.startActivity(myIntent);

		} else if (mapp.isFullPlayerVisible()) {
			mapp.getCurrentActivity().recreate();

		}

		playSong(d);

	}

	public void startfragment(final Intent data) {
		// Collect data from the intent and use it

		if (data.getExtras().containsKey("artist")) {

			artist artist = new Gson().fromJson(data.getStringExtra("artist"),
					artist.class);
			Intent myIntent = new Intent(this,
					com.tyolar.inc.musica.activities.Artist_Activity.class);
			myIntent.putExtra("artist", artist.toJson());
			startActivity(myIntent);
		}

		if (data.getExtras().containsKey("album")) {
			album album = new Gson().fromJson(data.getStringExtra("album"),
					album.class);
			Intent myIntent = new Intent(this,
					com.tyolar.inc.musica.activities.Album_Activity.class);
			myIntent.putExtra("album", album.toJson());
			startActivity(myIntent);

		}

	}

	public void PlayAllSongs(ArrayList<song> songtoplay) {
		mapp.getMusicaService().setSongtoplay(songtoplay);

		initMiniPlayer(songtoplay.get(0));

	}

	public void loadFragment(CFragment fragment) {
		String backStateName = fragment.getClass().getName();

		if (fragment != null) {

			android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
			android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager
					.beginTransaction();

			fragmentTransaction.add(R.id.frame_container, fragment,
					fragment.getTitel());
			fragmentTransaction
					.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
			fragmentTransaction.addToBackStack(fragment.getTitel())
					.commitAllowingStateLoss();
			fragmentManager.executePendingTransactions();

		} else {
			// error in creating fragment
			Log.e("MainActivity", "Error in creating fragment");
		}

	}

	@Override
	public void onBackPressed() {

		android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
		if (fragmentManager.getBackStackEntryCount() > 1) {
			fragmentManager.popBackStack();
			fragmentManager.executePendingTransactions();
			CFragment df = (CFragment) fragmentManager
					.findFragmentById(R.id.frame_container);
			setTitle(df.getTitel());
		} else {
			super.finish();
		}

	}

	private void setHeaderTitel(String titel) {
		getActionBar().setTitle(titel);
	}

	private BaseActivity getme() {
		return this;
	}

	private Menu getMenu() {
		return _menu;
	}

	public void showMenuItemMusic(final View v, final song track) {
		// TODO Auto-generated method stub
		LocalePopupMenu popup = new LocalePopupMenu(this, v);
		popup.inflate(R.menu.menu_list_music);
		popup.setOnMenuItemClickListener(new OnMenuItemClickListener() {
			@Override
			public boolean onMenuItemClick(MenuItem item) {
				switch (item.getItemId()) {

				case R.id.add_to_playlist:
					Staticui.show_add_playlist_view(getme(), track);
					break;

				case R.id.play_next:
					Playnext(track);
					break;
				case R.id.add_toquee:
					addtoquey(track);
					break;

				default:
					return false;
				}

				return false;
			}

		});

		popup.show();
	}

	public void showMenuItemPlayList(final View v, final song track,
			final PlayListSongAdapter adapter) {
		// TODO Auto-generated method stub
		LocalePopupMenu popup = new LocalePopupMenu(this, v);
		popup.inflate(R.menu.menu_playlist_songs);
		popup.setOnMenuItemClickListener(new OnMenuItemClickListener() {
			@Override
			public boolean onMenuItemClick(MenuItem item) {
				switch (item.getItemId()) {

				case R.id.remove__playlist:
					mapp.getPlaylist()
							.getSongs()
							.remove(mapp.getPlaylist().getSongs()
									.indexOf(track));
					PlayListDAO.updateRow(BaseActivity.this, mapp.getPlaylist());
					Toast.makeText(getApplicationContext(),
							getString(R.string.one_song_deleted),
							Toast.LENGTH_LONG).show();
					adapter.invalidate();
					break;
				case R.id.add_toquee:
					addtoquey(track);
					break;
				case R.id.go_artist:
					artist f = new artist(track.getArtistID(), track
							.getArtist(), track.getArtistArt());
					Intent myIntent = new Intent(
							getme(),
							com.tyolar.inc.musica.activities.Artist_Activity.class);
					myIntent.putExtra("artist", f.toJson());
					startActivity(myIntent);
					break;
				case R.id.go_album:
					album album = new album(track.getAlbumID(), track
							.getAlbum(), track.getArtist(),
							track.getArtistID(), track.getCoverArt(), track
									.getArtistArt());
					Intent myIntent1 = new Intent(
							v.getContext(),
							com.tyolar.inc.musica.activities.Album_Activity.class);
					myIntent1.putExtra("album", album.toJson());
					startActivity(myIntent1);
					break;

				default:
					return false;
				}

				return false;
			}

		});

		popup.show();
	}

	private void addtoquey(song track) {
		// TODO Auto-generated method stub
		try {

			if (!mapp.getMusicaService().getSongtoplay().contains(track))
				mapp.getMusicaService().getSongtoplay().add(track);
			// mControlsFragment.initilizeView();
		} catch (Exception s) {
			initMiniPlayer(track);

		}

	}

	private void Playnext(song track) {
		// TODO Auto-generated method stub
		try {

			if (!mapp.getMusicaService().getSongtoplay().contains(track))
				mapp.getMusicaService()
						.getSongtoplay()
						.add(mapp.getMusicaService().getSelectedtrackindex() + 1,
								track);
			else
				mapp.getMusicaService()
						.getSongtoplay()
						.set(mapp.getMusicaService().getSelectedtrackindex() + 1,
								track);
			// mControlsFragment.initilizeView();

		} catch (Exception s) {
			initMiniPlayer(track);

		}

	}

	public void ShowMiniPlayer(song track) {
		NotificationCompat.Builder mNotificationBuilder = new NotificationCompat.Builder(
				this);
		mNotificationBuilder.setOngoing(true);
		mNotificationBuilder.setSmallIcon(R.drawable.ic_stat_musica);
		mNotificationBuilder.setAutoCancel(false);
		Intent notificationIntent = new Intent(this, PlayerActivity.class);
		PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
				notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
		notificationIntent.setAction(Intent.ACTION_MAIN);
		notificationIntent.addCategory(Intent.CATEGORY_LAUNCHER);
		mNotificationBuilder.setContentIntent(contentIntent);

		notificationView = new RemoteViews(getPackageName(),
				R.layout.background_player);
		notificationView.setTextViewText(R.id.notification_base_line_one,
				track.getTitle());
		notificationView.setTextViewText(R.id.notification_base_line_two,
				track.getAlbum());
		notificationView.setViewVisibility(R.id.notification_base_next,
				View.VISIBLE);
		notificationView.setViewVisibility(R.id.notification_base_previous,
				View.VISIBLE);

		if (mapp.getMusicaService().getmMediaPlayer().isPlaying())
			notificationView.setImageViewResource(
					R.id.notification_base_play_pause,
					R.drawable.btn_playback_pause_light);
		else
			notificationView.setImageViewResource(
					R.id.notification_base_play_pause,
					R.drawable.btn_playback_play_light);

		// ¨Play Pause Buttons
		Intent playPauseTrackIntent = new Intent();
		playPauseTrackIntent.setAction("player.PLAY_PAUSE_ACTION");
		PendingIntent playPauseTrackPendingIntent = PendingIntent.getBroadcast(
				this.getApplicationContext(), 0, playPauseTrackIntent, 0);
		notificationView.setOnClickPendingIntent(
				R.id.notification_base_play_pause, playPauseTrackPendingIntent);
		if (mapp.getMusicaService().getSelectedtrackindex() == mapp
				.getMusicaService().getSongtoplay().size() - 1) {
			notificationView.setViewVisibility(R.id.notification_base_next,
					View.INVISIBLE);
		}
		Intent nextTrackIntent = new Intent();
		nextTrackIntent.setAction("player.NEXT_ACTION");
		PendingIntent nextTrackPendingIntent = PendingIntent.getBroadcast(
				this.getApplicationContext(), 0, nextTrackIntent, 0);
		notificationView.setOnClickPendingIntent(R.id.notification_base_next,
				nextTrackPendingIntent);

		if (mapp.getMusicaService().getSelectedtrackindex() == 0) {
			notificationView.setViewVisibility(R.id.notification_base_previous,
					View.INVISIBLE);
		}
		Intent previousTrackIntent = new Intent();
		previousTrackIntent.setAction("player.BACK_ACTION");
		PendingIntent previousTrackPendingIntent = PendingIntent.getBroadcast(
				this.getApplicationContext(), 0, previousTrackIntent, 0);
		notificationView.setOnClickPendingIntent(
				R.id.notification_base_previous, previousTrackPendingIntent);

		String url = apiurls.getArtimage();
		url = url.replace("[sid]", mapp.getAngami_id()).replace("[id]",
				track.getCoverArt());
		mNotificationBuilder.setContent(notificationView);
		Notification notification = mNotificationBuilder.build();
		notification.flags |= Notification.FLAG_ONGOING_EVENT
				| Notification.FLAG_NO_CLEAR;
		NotificationManager notifManager = (NotificationManager) getSystemService(this.NOTIFICATION_SERVICE);
		notifManager.notify(0, notification);
		Picasso.with(this)
				.load(url)
				.into(notificationView, R.id.notification_base_image, 0,
						notification);

	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		NotificationManager mNM = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		mNM.cancelAll();
		super.onDestroy();

	}

	public void togglePlaybackState() {
		// TODO Auto-generated method stub
		if (mapp.getMusicaService().getmMediaPlayer().isPlaying())
			mapp.getMusicaService().getmMediaPlayer().pause();
		else
			mapp.getMusicaService().getmMediaPlayer().start();

		ShowMiniPlayer(mapp.getMusicaService().getSongtoplay()
				.get(mapp.getMusicaService().getSelectedtrackindex()));
	}
}
