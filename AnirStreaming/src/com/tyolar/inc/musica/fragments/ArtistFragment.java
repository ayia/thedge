package com.tyolar.inc.musica.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TableLayout;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.squareup.picasso.Picasso;
import com.tyolar.inc.musica.R;
import com.tyolar.inc.musica.app2;
import com.tyolar.inc.musica.datalaoder.Artist_JsonAnghami;
import com.tyolar.inc.musica.model.apiurls;
import com.tyolar.inc.musica.model.artist;

public class ArtistFragment extends Fragment {

	View artist_view;
	TableLayout table;
	private int lastTopValue = 0;
	artist artist;

	public ArtistFragment() {
		// TODO Auto-generated constructor stub
	}

	public ArtistFragment(artist artist) {
		// TODO Auto-generated constructor stub

		this.artist = artist;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_artist2, container,
				false);
		artist_view = rootView.findViewById(R.id.artistview);
		table = (TableLayout) rootView.findViewById(R.id.listmusic);
		startsearch(this.artist.getId());
//		final app2 mapp = (app2) getActivity().getApplicationContext();
//		Tracker t = mapp.getTracker(app2.TrackerName.APP_TRACKER);
//		t.setScreenName("ArtistFragment");
//		t.send(new HitBuilders.AppViewBuilder().build());

		return rootView;
	}

	private void startsearch(String query) {
		new Artist_JsonAnghami(this.getActivity(), artist_view).execute(query);
	}

}
