package com.tyolar.inc.musica.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tyolar.inc.musica.FCFragment;
import com.tyolar.inc.musica.R;
import com.tyolar.inc.musica.datalaoder.Discover_json;
import com.tyolar.inc.musica.model.apiurls;

public class NewReleasesFragment extends FCFragment{
	View search_view;
	public NewReleasesFragment() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public NewReleasesFragment(String titel) {
		super(titel);
		// TODO Auto-generated constructor stub
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.activity_search_fragment2,
				container, false);
		search_view =  rootView.findViewById(R.id.searchview);
		startsearch();
		return rootView;
	}

	private void startsearch() {
		new Discover_json(this.getActivity(),search_view).execute(new apiurls().getnew_releases());
	}

}
