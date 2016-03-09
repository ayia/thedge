package com.tyolar.inc.musica.fragments;

import java.util.ArrayList;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.tyolar.inc.musica.CFragment;
import com.tyolar.inc.musica.FCFragment;
import com.tyolar.inc.musica.R;
import com.tyolar.inc.musica.adapter.ViewPagerAdapter;
import com.tyolar.inc.musica.widget.SlidingTabLayout;



public class DiscoverFragment extends CFragment {
	
	String titel;
	LinearLayout main_layout;
	View fragment_loading;
	View fragment_no_connection;
	View layout_main;

	ViewPager obedevpanelpager;
	SlidingTabLayout slidingTabLayout;
	View _rootView;

	public DiscoverFragment() {
		super();
	}

	
	public DiscoverFragment(String titel) {
		super(titel);
		this.titel = titel;
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		    	 runui();
			}

	public void runui() {
		layout_main.setVisibility(View.VISIBLE);
		fragment_loading.setVisibility(View.GONE);
		fragment_no_connection.setVisibility(View.GONE);
		
		ArrayList<FCFragment> list = new ArrayList<FCFragment>();
//		list.add(new FCFragment(this.getString(R.string.titel_genre)));
		list.add(new HomeFragment(this.getString(R.string.title_home)));
		list.add(new TopChartFragment(this.getString(R.string.titel_topchart)));
		list.add(new NewReleasesFragment(this.getString(R.string.titel_newreleases)));
		FragmentPagerAdapter adapter = new ViewPagerAdapter(
				this.getChildFragmentManager(), list);
		obedevpanelpager = (ViewPager) layout_main.findViewById(R.id.viewpager);
		obedevpanelpager.setAdapter(adapter);
//		obedevpanelpager.setCurrentItem(1);
		slidingTabLayout.setViewPager(obedevpanelpager);
		slidingTabLayout
				.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
					@Override
					public int getIndicatorColor(int position) {
						return Color.WHITE;
					}
				});
		
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		_rootView = inflater.inflate(R.layout.fragment_main_home, container,
				false);
		fragment_loading = _rootView.findViewById(R.id.fragment_loading);
		fragment_no_connection = _rootView
				.findViewById(R.id.fragment_no_connection);
		layout_main = _rootView.findViewById(R.id.layout_main);
		slidingTabLayout=(SlidingTabLayout) _rootView.findViewById(R.id.sliding_tabs);
		return _rootView;
	}

}
