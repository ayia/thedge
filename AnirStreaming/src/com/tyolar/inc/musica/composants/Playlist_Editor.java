package com.tyolar.inc.musica.composants;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tyolar.inc.musica.R;



public class Playlist_Editor extends LinearLayout {
	TextView header_titel;
	EditText playlist_name;

	public void setPlaylist_name(EditText playlist_name) {
		this.playlist_name = playlist_name;
	}

	public void setPlaylist_description(EditText playlist_description) {
		this.playlist_description = playlist_description;
	}

	EditText playlist_description;
	Button create_playlist;
	Button cancel_playlist;

	public Playlist_Editor(Context context) {
		super(context);
		initview(context);
	}

	public Playlist_Editor(Context context, AttributeSet attrs) {
		super(context, attrs);
		initview(context);
	}

	public Playlist_Editor(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initview(context);
	}

	private void initview(Context context) {
		// TODO Auto-generated method stub
		LayoutInflater mInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mInflater.inflate(R.layout.activity_create_playlist, this, true);
		header_titel = (TextView) findViewById(R.id.header_titel);
		playlist_name = (EditText) findViewById(R.id.playlist_name);
		playlist_description = (EditText) findViewById(R.id.playlist_description);
		create_playlist = (Button) findViewById(R.id.create_playlist);
		cancel_playlist = (Button) findViewById(R.id.cancel_playlist);
		playlist_name.addTextChangedListener(mTextWatcher);
		playlist_name.setHintTextColor(getResources().getColor(R.color.c_dimgray));
		playlist_description.setHintTextColor(getResources().getColor(R.color.c_dimgray));
		create_playlist.setEnabled(false);
	}

	TextWatcher mTextWatcher = new TextWatcher() {
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {
			// don't care about this one
		}

		public void onTextChanged(CharSequence s, int start, int before,
				int count) {
			String newText = playlist_name.getText().toString();
			if (newText.trim().length() == 0) {
				create_playlist.setEnabled(false);
			} else {
				create_playlist.setEnabled(true);

			}
		};

		public void afterTextChanged(Editable s) {
			// don't care about this one
		}
	};

	public void setHeader_titel(String titel) {
		this.header_titel.setText(titel);
	}

	public void setPlaylist_name(String playlist_name) {
		this.playlist_name.setText(playlist_name);
	}

	public String getPlaylist_name() {
		return this.playlist_name.getText().toString();
	}

	public void setPlaylist_description(String playlist_description) {
		this.playlist_description.setText(playlist_description);
		;
	}

	public String getPlaylist_description() {
		return this.playlist_description.getText().toString();
	}
	public EditText  getname_playlist() {
		return playlist_name;
	}



}
