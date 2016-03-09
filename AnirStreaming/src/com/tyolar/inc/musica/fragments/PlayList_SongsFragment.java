package com.tyolar.inc.musica.fragments;

import java.util.Collections;
import java.util.Random;

import android.app.ActionBar;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.ViewTreeObserver.OnScrollChangedListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TableLayout;

import com.tyolar.inc.musica.BaseActivity;
import com.tyolar.inc.musica.CFragment;
import com.tyolar.inc.musica.R;
import com.tyolar.inc.musica.app2;
import com.tyolar.inc.musica.composants.SearchListView;
import com.tyolar.inc.musica.model.PlayList;
import com.tyolar.inc.musica.model.apiurls;
import com.tyolar.inc.musica.model.searchResult;

public class PlayList_SongsFragment extends CFragment {
	View shuffle_button;
	View playall_button;
	View album_view;
	TableLayout listmusic;

	ScrollView sd;
	private int lastTopValue = 0;

	PlayList playlist;

	public PlayList_SongsFragment() {
		// TODO Auto-generated constructor stub
		super();
	}

	public PlayList_SongsFragment(PlayList album) {
		// TODO Auto-generated constructor stub
		super(album.getName());
		this.playlist = album;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_playlist_songs2,
				container, false);
		album_view = rootView.findViewById(R.id.albumview);
		listmusic = (TableLayout) rootView.findViewById(R.id.listmusic);
		shuffle_button = rootView.findViewById(R.id.shuffle_button);
		playall_button = rootView.findViewById(R.id.playall_button);

		playall_button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				BaseActivity s = (BaseActivity) getActivity();
				s.PlayAllSongs(playlist.getSongs());
			}
		});
		shuffle_button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Collections.shuffle(playlist.getSongs(),
						new Random(System.nanoTime()));
				BaseActivity s = (BaseActivity) getActivity();
				s.PlayAllSongs(playlist.getSongs());
			}
		});

		startsearch(this.playlist);
		return rootView;
	}

	private void startsearch(PlayList query) {
		SearchListView artistgrid = new SearchListView(this.getActivity(),
				query.getName(), query.getSongs().size(), 1);
		artistgrid.setId(1);
		app2 mapp = (app2) getActivity().getApplication();
		mapp.setPlaylist(query);
		artistgrid.more_button.setVisibility(View.INVISIBLE);
		artistgrid.Header.setText(query.getName());
		searchResult d = new searchResult();
		d.setSonglist(query.getSongs());
		d.setStatus(true);
		artistgrid.setSongPlaylistlist(d);
		if (query.getSongs().size() <= 1) {

			shuffle_button.setVisibility(View.GONE);

			playall_button.setVisibility(View.GONE);
		}
		listmusic.addView(artistgrid);
	}

}
