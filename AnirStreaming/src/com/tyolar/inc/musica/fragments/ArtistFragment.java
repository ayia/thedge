package com.tyolar.inc.musica.fragments;

import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver.OnScrollChangedListener;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TableLayout;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.squareup.picasso.Picasso;
import com.tyolar.inc.musica.CFragment;
import com.tyolar.inc.musica.MainActivity;
import com.tyolar.inc.musica.R;
import com.tyolar.inc.musica.app2;
import com.tyolar.inc.musica.datalaoder.Artist_JsonAnghami;
import com.tyolar.inc.musica.model.apiurls;
import com.tyolar.inc.musica.model.artist;
import com.tyolar.inc.musica.widget.ScrollViewX;
import com.tyolar.inc.musica.widget.ScrollViewX.OnScrollViewListener;

public class ArtistFragment extends CFragment {
	ImageView artist_imageview;
	View artist_view;
	TableLayout table;
	private int lastTopValue = 0;
	artist artist;

	public ArtistFragment(artist artist) {
		// TODO Auto-generated constructor stub
		super(artist.getName());
		this.artist = artist;
	}

	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_artist2, container,
				false);
		artist_view = rootView.findViewById(R.id.artistview);
		artist_imageview = (ImageView) rootView.findViewById(R.id.artistImage);
		table = (TableLayout) rootView.findViewById(R.id.listmusic);
		startsearch(this.artist.getId());
		final app2 mapp = (app2) getActivity().getApplicationContext();
		String url = apiurls.getArtimage();
		url = url.replace("[sid]",
				mapp.getAngami_id()).replace("[id]",
				artist.getArtistArt());
		Picasso.with(this.getActivity()).load(url)
				.placeholder(R.drawable.ic_singer).error(R.drawable.ic_singer)
				.into(artist_imageview);
		Tracker t = mapp.getTracker(app2.TrackerName.APP_TRACKER);
		t.setScreenName("ArtistFragment");
		t.send(new HitBuilders.AppViewBuilder().build());
	
		return rootView;
	}

	private void startsearch(String query) {
		new Artist_JsonAnghami(this.getActivity(), artist_view).execute(query);
	}

}
