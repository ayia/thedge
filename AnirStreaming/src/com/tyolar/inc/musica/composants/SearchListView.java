package com.tyolar.inc.musica.composants;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tyolar.inc.musica.BaseActivity;
import com.tyolar.inc.musica.R;
import com.tyolar.inc.musica.app2;
import com.tyolar.inc.musica.activities.More_Activity;
import com.tyolar.inc.musica.adapter.PlayListSongAdapter;
import com.tyolar.inc.musica.adapter.SongGridViewAdapter;
import com.tyolar.inc.musica.model.album;
import com.tyolar.inc.musica.model.artist;
import com.tyolar.inc.musica.model.searchResult;
import com.tyolar.inc.musica.model.song;

public class SearchListView extends RelativeLayout {
	Context context;
	public Button more_button;
	public TextView Header;
	private String hedertext;
	public GridView grid;
	private ArrayList am;
	int nunmberofrows = 1;
	int nunmberofculumns = 1;

	public SearchListView(Context context, String hedertext, int unmberofrows,
			int nunmberofculumns) {
		super(context);
		this.hedertext = hedertext;
		this.nunmberofrows = unmberofrows;
		this.nunmberofculumns = nunmberofculumns;
		init(context);

	}

	public SearchListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	public SearchListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
				MeasureSpec.AT_MOST);
		super.onMeasure(widthMeasureSpec, expandSpec);

	}

	private void init(Context context2) {
		this.context = context2;
		LayoutInflater mInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mInflater.inflate(R.layout.composant_music_list2, this, true);
		// this.setLayoutParams(new FrameLayout.LayoutParams(
		// LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
		this.setPadding(5, 0, 0, 0);
		
		grid = (GridView) findViewById(R.id.gridView1);
		grid.setNumColumns(nunmberofculumns);
		more_button = (Button) findViewById(R.id.more_button);
		more_button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				showall();
			}
		});
		Header = (TextView) findViewById(R.id.titel);
		Header.setText(this.hedertext);
	}

	public void showall() {
		app2 mapp=(app2)context.getApplicationContext();
		mapp.CFragment=new com.tyolar.inc.musica.fragments.ListAllFragment(
				am,nunmberofculumns,Header.getText().toString());
		
		Intent myIntent = new Intent(context,
				com.tyolar.inc.musica.activities.More_Activity.class);
		context.startActivity(myIntent);
		
//	
//		((BaseActivity) context)
//		.loadFragment(new com.tyolar.inc.musica.fragments.ListAllFragment(
//				am,nunmberofculumns,Header.getText().toString()));
	}

	public void setSonglist(searchResult result) {
		ArrayList a = new ArrayList();
		if (result.getSonglist() != null && result.getSonglist().size() > 0) {
			for (song ac : result.getSonglist()) {
				a.add(ac);
			}
			am = a;
		}
		grid.setAdapter(new SongGridViewAdapter(context, a, nunmberofrows,
				nunmberofculumns, false));
		if (a.size() < Math.abs(nunmberofrows * nunmberofculumns))
			more_button.setVisibility(View.INVISIBLE);

	}
	
	public void setSongPlaylistlist(searchResult result) {
		ArrayList a = new ArrayList();
		if (result.getSonglist() != null && result.getSonglist().size() > 0) {
			for (song ac : result.getSonglist()) {
				a.add(ac);
			}
			am = a;
		}
		grid.setAdapter(new PlayListSongAdapter(context, a, nunmberofrows,
				nunmberofculumns, false));
		if (a.size() < Math.abs(nunmberofrows * nunmberofculumns))
			more_button.setVisibility(View.INVISIBLE);

	}

	

	public void setAlbumlist(searchResult result) {
		ArrayList a = new ArrayList();
		if (result.getAlbumlist() != null && result.getAlbumlist().size() > 0) {
			for (album ac : result.getAlbumlist()) {
				a.add(ac);
			}
			am = a;
		}
		grid.setAdapter(new SongGridViewAdapter(context, a, nunmberofrows,
				nunmberofculumns, false));
		if (a.size() < Math.abs(nunmberofrows * nunmberofculumns))
			more_button.setVisibility(View.INVISIBLE);

	}

	public void setArtistlist(searchResult result) {
		ArrayList a = new ArrayList();
		if (result.getArtistlist() != null && result.getArtistlist().size() > 0) {
			for (artist ac : result.getArtistlist()) {
				a.add(ac);
			}
			am = a;
		}
		grid.setAdapter(new SongGridViewAdapter(context, a, nunmberofrows,
				nunmberofculumns, false));
		if (a.size() < Math.abs(nunmberofrows * nunmberofculumns))
			more_button.setVisibility(View.INVISIBLE);

	}

}
