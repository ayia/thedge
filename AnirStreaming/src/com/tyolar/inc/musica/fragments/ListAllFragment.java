package com.tyolar.inc.musica.fragments;

import java.util.ArrayList;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.tyolar.inc.musica.CFragment;
import com.tyolar.inc.musica.R;
import com.tyolar.inc.musica.adapter.SongGridViewAdapter;

public class ListAllFragment extends CFragment {
	
	public GridView grid;
	private ArrayList am;
	int numberofcolumns=1;
	public ListAllFragment() {
		super();
	}

	public ListAllFragment(ArrayList am,int ac,String titel) {
		super(titel);
		this.am=am;
		this.numberofcolumns=ac;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_listall2,
				container, false);
		grid = (GridView) rootView.findViewById(R.id.listall);
		setdata();
		grid.setNumColumns(this.numberofcolumns);
		return rootView;
	}

	private void setdata() {
		grid.setAdapter(new SongGridViewAdapter(this.getActivity(), am, 0,
				2, true));
		
	}

}
