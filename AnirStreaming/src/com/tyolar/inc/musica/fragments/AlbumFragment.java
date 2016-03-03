package com.tyolar.inc.musica.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TableLayout;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.squareup.picasso.Picasso;
import com.tyolar.inc.musica.R;
import com.tyolar.inc.musica.app2;
import com.tyolar.inc.musica.datalaoder.Album_JsonAnghami;
import com.tyolar.inc.musica.model.album;
import com.tyolar.inc.musica.model.apiurls;

public class AlbumFragment extends Fragment {
	View album_view;
	TableLayout listmusic;
	private int lastTopValue = 0;
	album album;
	public AlbumFragment() {
		
	}
	public AlbumFragment(album album) {
		// TODO Auto-generated constructor stub
		this.album = album;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_album2, container,
				false);
		album_view=rootView.findViewById(R.id.albumview);
		final app2 mapp = (app2) getActivity().getApplicationContext();
		listmusic = (TableLayout) rootView.findViewById(R.id.listmusic);
		startsearch(this.album);
//		Tracker t = mapp.getTracker(app2.TrackerName.APP_TRACKER);
//		t.setScreenName("AlbumFragment");
//		t.send(new HitBuilders.AppViewBuilder().build());
		return rootView;
	}

	private void startsearch(album query) {
		new Album_JsonAnghami(this.getActivity(), album_view).execute(this.album);
	}

}
