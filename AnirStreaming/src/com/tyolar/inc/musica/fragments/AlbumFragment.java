package com.tyolar.inc.musica.fragments;

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
import com.tyolar.inc.musica.CFragment;
import com.tyolar.inc.musica.R;
import com.tyolar.inc.musica.app2;
import com.tyolar.inc.musica.datalaoder.Album_JsonAnghami;
import com.tyolar.inc.musica.model.album;
import com.tyolar.inc.musica.model.apiurls;

public class AlbumFragment extends CFragment {
	ImageView album_imageview;

	View album_view;
	TableLayout listmusic;
	private int lastTopValue = 0;
	album album;

	public AlbumFragment(album album) {
		// TODO Auto-generated constructor stub
		super(album.getTitle());
		this.album = album;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_album2, container,
				false);
		final app2 mapp = (app2) getActivity().getApplicationContext();
		album_view = rootView.findViewById(R.id.albumview);
		album_imageview = (ImageView) rootView.findViewById(R.id.albumImage);
		listmusic = (TableLayout) rootView.findViewById(R.id.listmusic);
		startsearch(this.album);
		String url = apiurls.getArtimage();
		url = url.replace("[sid]",
				mapp.getAngami_id()).replace("[id]",
				album.getCoverArt());
		Picasso.with(this.getActivity()).load(url)
				.placeholder(R.drawable.ic_album_cover)
				.error(R.drawable.ic_album_cover).into(album_imageview);
		Tracker t = mapp.getTracker(app2.TrackerName.APP_TRACKER);
		t.setScreenName("AlbumFragment");
		t.send(new HitBuilders.AppViewBuilder().build());
		return rootView;
	}
	

	private void startsearch(album query) {
		new Album_JsonAnghami(this.getActivity(), album_view).execute(query);
	}


}
