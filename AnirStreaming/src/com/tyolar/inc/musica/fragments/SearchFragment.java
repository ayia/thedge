package com.tyolar.inc.musica.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.tyolar.inc.musica.CFragment;
import com.tyolar.inc.musica.MainActivity;
import com.tyolar.inc.musica.R;
import com.tyolar.inc.musica.app2;
import com.tyolar.inc.musica.datalaoder.search_JsonAnghami;

public class SearchFragment extends CFragment {
	View search_view;
	String query;
	public SearchFragment() {
		super();
	}
	public SearchFragment(String query) {

		super(query);
		
	
		this.query = query;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.activity_search_fragment2,
				container, false);
		search_view = rootView.findViewById(R.id.searchview);

		final app2 mapp = (app2) getActivity().getApplicationContext();
		// Tracker t = mapp.getTracker(app2.TrackerName.APP_TRACKER);
		// t.setScreenName("MyAlbumsFragment");
		// t.send(new HitBuilders.AppViewBuilder().build());
		mapp.getInstance().trackScreenView("Search Screen");
		mapp.getInstance().trackEvent("Screen", this.query, this.query);
		this.getActivity().setTitle(
				getResources().getString(
						R.string.abc_searchview_description_search)
						+ " : " + this.query);
		
		setTitel(getResources().getString(
				R.string.abc_searchview_description_search)
				+ " : " + query);
		
		startsearch(this.query);
		return rootView;
	}

	private void startsearch(String query) {
		if (query != null)
			new search_JsonAnghami(this.getActivity(), search_view)
					.execute(query);

	}

}
