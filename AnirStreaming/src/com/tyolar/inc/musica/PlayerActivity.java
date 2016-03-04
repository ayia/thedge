package com.tyolar.inc.musica;

import java.util.Collections;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu.OnMenuItemClickListener;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
//import com.google.android.gms.analytics.GoogleAnalytics;
import com.squareup.picasso.Picasso;
import com.tyolar.inc.musica.adapter.List_Song_Player_Adapter;
import com.tyolar.inc.musica.composants.LocalePopupMenu;
import com.tyolar.inc.musica.composants.TouchInterceptor;
import com.tyolar.inc.musica.model.album;
import com.tyolar.inc.musica.model.apiurls;
import com.tyolar.inc.musica.model.artist;
import com.tyolar.inc.musica.model.song;

public class PlayerActivity extends Activity {
	private SeekBar seekbar;
	Menu menu;
	private ImageView songimage;
	private TextView duration;
	private TextView current_time;
	private TextView song_artist;
	private TextView song_titel;
	private TextView song_album;
	ProgressBar loader_stat;
	ImageButton player_pause;
	ImageButton player_play;
	public ImageView back;
	public ImageView next;
	private com.tyolar.inc.musica.composants.TouchInterceptor SongsListView;
	private Handler mHandler = new Handler();
	List_Song_Player_Adapter adp;
	app2 mapp;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setView();
	}

	@Override
	public void onNewIntent(Intent intent) {
		Bundle extras = intent.getExtras();
		if (extras != null) {
			if (extras.containsKey("update")) {
			setView();
			}
		}
	}

	protected void setView() {
		setContentView(R.layout.activity_player);
		mapp = (app2) getApplication();
		mapp.PlayerActivity=this;
		song_titel = (TextView) findViewById(R.id.song_titel);
		song_artist = (TextView) findViewById(R.id.song_artist);
		song_album = (TextView) findViewById(R.id.song_album);
		loader_stat = (ProgressBar) findViewById(R.id.loader_stat);
		seekbar = (SeekBar) findViewById(R.id.seekprogress);
		SongsListView = (TouchInterceptor) findViewById(R.id.SongsListView);
		player_pause = (ImageButton) findViewById(R.id.player_pause);
		player_play = (ImageButton) findViewById(R.id.player_play);
		back = (ImageView) findViewById(R.id.preview_song2);
		next = (ImageView) findViewById(R.id.next_song2);
		songimage = (ImageView) findViewById(R.id.song_umage);
		current_time = (TextView) findViewById(R.id.current_time);
		duration = (TextView) findViewById(R.id.duration);
		adp = new List_Song_Player_Adapter(this, mapp.getMusicaService()
				.getSongtoplay());
		SongsListView.setAdapter(adp);
		findViewById(R.id.reduce).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				onBackPressed();
			}
		});

		findViewById(R.id.more_icon).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				onOverflowPressed(v, mapp.getMusicaService().getSongtoplay()
						.get(mapp.getMusicaService().getSelectedtrackindex()));
			}
		});
		findViewById(R.id.playlist).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				showhideplayedmusic();
			}
		});

		initializeViewsandPlay(mapp.getMusicaService().getSongtoplay()
				.get(mapp.getMusicaService().getSelectedtrackindex()));

	}

	public void initializeViewsandPlay(song song) {
		// TODO Auto-generated method stub
		setTitle(song.getTitle());
		song_titel.setText(song.getTitle());
		song_artist.setText(song.getArtist());
		song_album.setText(song.getAlbum());
		adp.notifyDataSetChanged();
		mapp.getAudioWife().getInstance().setPauseView(player_pause)
				.setPlayView(player_play).setTotalTimeView(duration)
				.setLoadingView(findViewById(R.id.loading_progress))
				.setRuntimeView(current_time).setSeekBar(seekbar);
		// mapp.getAudioWife().getInstance().updateUI();
		String url = new apiurls().getArtimage();
		url = url.replace("[sid]", mapp.getAngami_id()).replace("[id]",
				song.getCoverArt());
		Picasso.with(this).load(url)
				.placeholder(R.drawable.ic_music_note_black_48dp)
				.error(R.drawable.ic_launcher).into(songimage);

		update_nextBackButton();
		if (mapp.getMusicaService().getmMediaPlayer().getCurrentPosition() > 1) {
			mapp.getAudioWife().getInstance().updateUI();
			findViewById(R.id.loading_progress).setVisibility(View.GONE);

		}
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
	}

	public void update_nextBackButton() {
		back.setEnabled(true);
		next.setEnabled(true);
		if (mapp.getMusicaService().getSelectedtrackindex() == mapp
				.getMusicaService().getSongtoplay().size() - 1) {
			next.setEnabled(false);
		}

		if (mapp.getMusicaService().getSelectedtrackindex() == 0) {
			back.setEnabled(false);

		}
		back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				mapp.getMusicaService().setSelectedtrackindex(
						mapp.getMusicaService().getSelectedtrackindex() - 1);
				initializeViewsandPlay(mapp.getMusicaService().getSongtoplay()
						.get(mapp.getMusicaService().getSelectedtrackindex()));
				mapp.getAudioWife().getInstance().setPauseView(player_pause)
						.setPlayView(player_play)
						.setTotalTimeView(current_time)
						.setRuntimeView(duration).setSeekBar(seekbar);
				mapp.getBaseactivity().playSong(
						mapp.getMusicaService()
								.getSongtoplay()
								.get(mapp.getMusicaService()
										.getSelectedtrackindex()));
				mapp.getBaseactivity().ShowMiniPlayer(
						mapp.getMusicaService()
								.getSongtoplay()
								.get(mapp.getMusicaService()
										.getSelectedtrackindex()));

			}
		});
		next.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View qv) {
				// TODO Auto-generated method stub
				mapp.getMusicaService().setSelectedtrackindex(
						mapp.getMusicaService().getSelectedtrackindex() + 1);
				initializeViewsandPlay(mapp.getMusicaService().getSongtoplay()
						.get(mapp.getMusicaService().getSelectedtrackindex()));
				mapp.getAudioWife().getInstance().setPauseView(player_pause)
						.setPlayView(player_play)
						.setTotalTimeView(current_time)
						.setRuntimeView(duration).setSeekBar(seekbar);
				mapp.getBaseactivity().playSong(
						mapp.getMusicaService()
								.getSongtoplay()
								.get(mapp.getMusicaService()
										.getSelectedtrackindex()));
				mapp.getBaseactivity().ShowMiniPlayer(
						mapp.getMusicaService()
								.getSongtoplay()
								.get(mapp.getMusicaService()
										.getSelectedtrackindex()));

			}
		});
		mapp.getBaseactivity().ShowMiniPlayer(
				mapp.getMusicaService().getSongtoplay()
						.get(mapp.getMusicaService().getSelectedtrackindex()));
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		GoogleAnalytics.getInstance(PlayerActivity.this).reportActivityStart(
				this);
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		GoogleAnalytics.getInstance(PlayerActivity.this).reportActivityStop(
				this);
	}

	protected void onResume() {
		super.onResume();
		mapp.setCurrentActivity(this);
	}

	public void onOverflowPressed(final View v, final song track) {
		// TODO Auto-generated method stub
		LocalePopupMenu popup = new LocalePopupMenu(this, v);
		popup.inflate(R.menu.menu_player_more);
		popup.setOnMenuItemClickListener(new OnMenuItemClickListener() {

			@Override
			public boolean onMenuItemClick(MenuItem item) {
				Intent intent = new Intent();
				song playedsong = mapp.getMusicaService().getSongtoplay()
						.get(mapp.getMusicaService().getSelectedtrackindex());
				switch (item.getItemId()) {

				case R.id.save_quee:
					Staticui.create_playlist(getme(), mapp.getMusicaService()
							.getSongtoplay());
					break;

				case R.id.add_playlist:
					Staticui.show_add_playlist_view(
							getme(),
							mapp.getMusicaService()
									.getSongtoplay()
									.get(mapp.getMusicaService()
											.getSelectedtrackindex()));
					break;
				case R.id.go_artist:
					artist d = new artist(playedsong.getArtistID(), playedsong
							.getArtist(), playedsong.getArtistArt());
					intent.putExtra("artist", d.toJson());
					mapp.getBaseactivity().startfragment(intent);
					finish();
					break;
				case R.id.go_album:
					album album = new album(playedsong.getAlbumID(), playedsong
							.getAlbum(), playedsong.getArtist(), playedsong
							.getArtistID(), playedsong.getCoverArt(),
							playedsong.getArtistArt());

					Intent myIntent = new Intent(
							getme(),
							com.tyolar.inc.musica.activities.Album_Activity.class);
					myIntent.putExtra("album", album.toJson());
					getme().startActivity(myIntent);

					finish();
					break;
				default:
					return false;
				}

				return false;
			}
		});

		popup.show();
	}

	protected Activity getme() {
		// TODO Auto-generated method stub
		return this;
	}

	public PlayerActivity getcontext() {
		return this;
	}

	private void showhideplayedmusic() {
		app2 mapp = (app2) getApplication();

		if (SongsListView.getVisibility() == View.VISIBLE)
			SongsListView.setVisibility(View.GONE);
		else {
			SongsListView.setVisibility(View.VISIBLE);
			SongsListView.setSelection(mapp.getMusicaService()
					.getSelectedtrackindex());
			SongsListView.setDropListener(mDropListener);
		}

	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		// NotificationManager mNM = (NotificationManager)
		// getSystemService(NOTIFICATION_SERVICE);
		// mNM.cancelAll();
		super.onDestroy();
	}

	private TouchInterceptor.DropListener mDropListener = new TouchInterceptor.DropListener() {
		public void drop(int from, int to) {
			// update the currently playing list
			Collections.swap(mapp.getMusicaService().getSongtoplay(), from, to);
			SongsListView.setAdapter(new List_Song_Player_Adapter(getcontext(),
					mapp.getMusicaService().getSongtoplay()));
		}
	};

}
