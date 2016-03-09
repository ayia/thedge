package com.tyolar.inc.musica.fragments;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.tyolar.inc.musica.BaseActivity;
import com.tyolar.inc.musica.CFragment;
import com.tyolar.inc.musica.R;
import com.tyolar.inc.musica.app2;
import com.tyolar.inc.musica.DAO.AlbumsDAO;
import com.tyolar.inc.musica.adapter.MyAlbumAdapter;
import com.tyolar.inc.musica.adapter.PlayListAdapter;
import com.tyolar.inc.musica.model.album;

public class MyAlbumsFragment extends CFragment {
	GridView myalbums_grid;
	PlayListAdapter adapter;
	Button addplaylist;
	View emptyview;
	public MyAlbumsFragment() {
		super();
	}

	public MyAlbumsFragment(String titel) {
		super(titel);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_myalbum, container,
				false);
		myalbums_grid = (GridView) rootView.findViewById(R.id.playlist_grid);
		addplaylist = (Button) rootView.findViewById(R.id.add_new_playlist);
		emptyview=rootView.findViewById(R.id.empty_list_view);
		loadMyalbums((BaseActivity) getActivity(), this);
		app2 mapp = (app2) getActivity().getApplicationContext();
//		Tracker t = mapp.getTracker(app2.TrackerName.APP_TRACKER);
//		t.setScreenName("MyAlbumsFragment");
		mapp.getInstance().trackScreenView("yAlbums Screen");
		
		return rootView;

	}

	public void notifyDataSetChanged() {

		loadMyalbums((BaseActivity) getActivity(), this);
	}

	public void loadMyalbums(final BaseActivity contexta, final Fragment context) {
		album[] array;
		try {
			array = AlbumsDAO.get(contexta).toArray(
					new album[AlbumsDAO.get(context.getActivity())
							.size()]);
			myalbums_grid.setEmptyView(emptyview);
			myalbums_grid.setAdapter(new MyAlbumAdapter(contexta,array,this));

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private Fragment getthis() {
		return this;
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		//
		// if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
		// playlist_grid.setNumColumns(3);;
		// } else {
		// playlist_grid.setNumColumns(4);
		// }
		super.onConfigurationChanged(newConfig);
	}

}
