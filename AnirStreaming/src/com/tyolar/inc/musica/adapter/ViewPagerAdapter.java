package com.tyolar.inc.musica.adapter;


import java.util.ArrayList;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.tyolar.inc.musica.FCFragment;



public class ViewPagerAdapter extends FragmentPagerAdapter  {
	ArrayList<FCFragment> registeredFragments = null;

	public ViewPagerAdapter(FragmentManager fm,
			ArrayList<FCFragment> registeredFragments2) {
		super(fm);
		this.registeredFragments = registeredFragments2;
		// TODO Auto-generated constructor stub
	}
	

	@Override
	public CharSequence getPageTitle(int position) {
		return registeredFragments.get(position).getTitel();
	}

	@Override
	public Fragment getItem(int arg0) {
		// TODO Auto-generated method stub
		return registeredFragments.get(arg0);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return registeredFragments.size();
	}

}
