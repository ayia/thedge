package com.tyolar.inc.musica.adapter;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;
import com.tyolar.inc.musica.BaseActivity;
import com.tyolar.inc.musica.R;
import com.tyolar.inc.musica.app2;
import com.tyolar.inc.musica.model.album;
import com.tyolar.inc.musica.model.apiurls;
import com.tyolar.inc.musica.model.artist;
import com.tyolar.inc.musica.model.song;

@SuppressLint("NewApi")
public class SongGridViewAdapter extends BaseAdapter {
	private LayoutInflater inflater;
	private List listactor;
	Context context;

	public SongGridViewAdapter(Context context, ArrayList listactor,
			int unmberofrows, int nunmberofculumns, boolean listall) {
		super();
		this.context = context;
		inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		int fg = Math.abs(unmberofrows * nunmberofculumns);
		if (listall == false) {
			if (fg > listactor.size())
				this.listactor = listactor;
			else
				this.listactor = listactor.subList(0, fg);
		} else
			this.listactor = listactor;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return listactor.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return listactor.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	public List getListTrack() {
		return listactor;

	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		final ActorHolderSong holdersong;
		final ActorHolderAlbum holderalbum;
		final ActorHolderArtist holderartist;
		final app2 mapp = (app2) context.getApplicationContext();
		convertView = null;
		String url = new apiurls().getArtimage();
		url = url.replace("[sid]", mapp.getAngami_id());
		// /song Area
		if (getItem(position) instanceof song) {
			convertView = inflater.inflate(R.layout.music_list_item, parent,
					false);
			holdersong = new ActorHolderSong();
			holdersong.titel = (com.ctrlplusz.anytextview.AnyTextView) convertView
					.findViewById(R.id.grid_item_title);
			holdersong.artistName = (TextView) convertView
					.findViewById(R.id.grid_item_artist);

			holdersong.index_img = (ImageView) convertView
					.findViewById(R.id.index);
			holdersong.duration = (TextView) convertView
					.findViewById(R.id.grid_item_subtext);
			holdersong.layout_img = (ImageView) convertView
					.findViewById(R.id.grid_item_image);
			holdersong.img_more = (ImageView) convertView
					.findViewById(R.id.more_icon);
			convertView.setTag(holdersong);
			url = url.replace("[id]", ((song) getItem(position)).getCoverArt());
			Picasso.with(context).load(url)
					.placeholder(R.drawable.ic_music_note_black_48dp)
					.error(R.drawable.ic_music_note_black_48dp)
					.into(holdersong.layout_img);
			holdersong.titel.setText(((song) getItem(position)).getTitle());
			holdersong.duration.setText(((song) getItem(position))
					.getDuration());

			holdersong.track = (song) getItem(position);
			holdersong.artistName.setText(holdersong.track.getArtist());

			holdersong.img_more.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					((BaseActivity) context).showMenuItemMusic(v,
							holdersong.track);
					invalidate();
				}
			});

			convertView.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					((BaseActivity) context).initMiniPlayer(holdersong.track);
				}
			});
		}
		// /Artist Area
		if (getItem(position) instanceof artist) {
			convertView = inflater.inflate(R.layout.music_grid_item, parent,
					false);
			holderartist = new ActorHolderArtist();
			holderartist.titel = (com.ctrlplusz.anytextview.AnyTextView) convertView
					.findViewById(R.id.grid_item_title);
			holderartist.index_img = (ImageView) convertView
					.findViewById(R.id.index);
			holderartist.duration = (TextView) convertView
					.findViewById(R.id.grid_item_subtext);
			holderartist.layout_img = (ImageView) convertView
					.findViewById(R.id.grid_item_image);
			holderartist.img_more = (ImageView) convertView
					.findViewById(R.id.more_icon);
			convertView.setTag(holderartist);
			url = url.replace("[id]",
					((artist) getItem(position)).getArtistArt());
			Picasso.with(context).load(url).placeholder(R.drawable.ic_singer)
					.error(R.drawable.ic_singer).into(holderartist.layout_img);
			holderartist.titel.setText(((artist) getItem(position)).getName());
			// holdersong.duration.setText(((song) getItem(position))
			// .getDuration());
			holderartist.duration.setVisibility(View.GONE);
			holderartist.artist = (artist) getItem(position);
			convertView.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent myIntent = new Intent(
							context,
							com.tyolar.inc.musica.activities.Artist_Activity.class);
					myIntent.putExtra("artist", holderartist.artist.toJson());
					context.startActivity(myIntent);

				}
			});

		}

		// /album Area
		if (getItem(position) instanceof album) {
			convertView = inflater.inflate(R.layout.music_grid_item, parent,
					false);
			holderalbum = new ActorHolderAlbum();
			holderalbum.titel = (com.ctrlplusz.anytextview.AnyTextView) convertView
					.findViewById(R.id.grid_item_title);
			holderalbum.index_img = (ImageView) convertView
					.findViewById(R.id.index);
			holderalbum.year = (TextView) convertView
					.findViewById(R.id.grid_item_subtext);
			holderalbum.layout_img = (ImageView) convertView
					.findViewById(R.id.grid_item_image);
			holderalbum.img_more = (ImageView) convertView
					.findViewById(R.id.more_icon);
			convertView.setTag(holderalbum);
			url = url
					.replace("[id]", ((album) getItem(position)).getCoverArt());
			Picasso.with(context).load(url)
					.placeholder(R.drawable.ic_album_black_48dp)
					.error(R.drawable.ic_album_black_48dp)
					.into(holderalbum.layout_img);
			holderalbum.titel.setText(((album) getItem(position)).getTitle());
			holderalbum.year.setText(((album) getItem(position)).getYear());
			holderalbum.album = (album) getItem(position);
			convertView.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent myIntent = new Intent(
							context,
							com.tyolar.inc.musica.activities.Album_Activity.class);
					myIntent.putExtra("album", holderalbum.album.toJson());
					context.startActivity(myIntent);
				}
			});

		}

		return convertView;
	}

	public void invalidate() {
		// TODO Auto-generated method stub
		notifyDataSetChanged();

	}

	private class ActorHolderSong {

		public song track;
		public ImageView layout_img;
		public ImageView index_img;
		public com.ctrlplusz.anytextview.AnyTextView titel;
		public TextView artistName;
		public TextView duration;
		public TextView uploader;
		public ImageView img_more;
	}

	private class ActorHolderAlbum {
		public album album;
		public ImageView layout_img;
		public ImageView index_img;
		public com.ctrlplusz.anytextview.AnyTextView titel;
		public TextView year;
		public TextView uploader;
		public ImageView img_more;
	}

	private class ActorHolderArtist {

		public artist artist;
		public ImageView layout_img;
		public ImageView index_img;
		public com.ctrlplusz.anytextview.AnyTextView titel;
		public TextView duration;
		public TextView uploader;
		public ImageView img_more;
	}

}
