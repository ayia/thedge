package com.tyolar.inc.musica.adapter;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.tyolar.inc.musica.PlayerActivity;
import com.tyolar.inc.musica.R;
import com.tyolar.inc.musica.app2;
import com.tyolar.inc.musica.model.apiurls;
import com.tyolar.inc.musica.model.song;

@SuppressLint("NewApi")
public class List_Song_Player_Adapter extends BaseAdapter {
	private LayoutInflater inflater;
	private ArrayList<song> listTrack;
	PlayerActivity context;

	public List_Song_Player_Adapter(PlayerActivity context,
			ArrayList<song> arrayList) {
		super();
		this.context = context;
		inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		this.listTrack = arrayList;

	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return listTrack.size();
	}

	@Override
	public song getItem(int position) {
		// TODO Auto-generated method stub
		return listTrack.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	public ArrayList<song> getListTrack() {
		return listTrack;

	}

	private void invalidate() {
		// TODO Auto-generated method stub
		notifyDataSetChanged();

	}

	private class ActorHolder {
		public song track;
		public ImageView layout_img;
		public ImageView index_img;
		public com.ctrlplusz.anytextview.AnyTextView titel;
		public TextView duration;
		public TextView uploader;
		public ImageView delete;
	}

	public void fillValues(final int position, final View convertView) {
		// TODO Auto-generated method stub
		ActorHolder holder;
		final app2 mapp = (app2) context.getApplicationContext();
		String url = apiurls.getArtimage();
		url = url.replace("[sid]", mapp.getAngami_id());

		holder = new ActorHolder();
		holder.titel = (com.ctrlplusz.anytextview.AnyTextView) convertView
				.findViewById(R.id.grid_item_title);
		holder.duration = (TextView) convertView
				.findViewById(R.id.grid_item_subtext);
		holder.index_img = (ImageView) convertView.findViewById(R.id.index);
		holder.layout_img = (ImageView) convertView
				.findViewById(R.id.grid_item_image);
		holder.delete = (ImageView) convertView.findViewById(R.id.delete_icon);
		convertView.setTag(holder);
		holder = (ActorHolder) convertView.getTag();
		holder.titel.setText(getItem(position).getTitle());
		String currentTime = getItem(position).getDuration();
		holder.duration.setText(getItem(position).getArtist());
		url = url.replace("[id]", ((song) getItem(position)).getCoverArt());
		Picasso.with(context).load(url)
				.placeholder(R.drawable.ic_music_note_black_48dp)
				.error(R.drawable.ic_music_note_black_48dp)
				.into(holder.layout_img);

		holder.index_img.setVisibility(View.GONE);
		holder.track = getItem(position);
		if (mapp.getAudioWife().getInstance().getmUri().toString()
				.contains(holder.track.id)) {
			holder.index_img.setVisibility(View.VISIBLE);
			holder.delete.setVisibility(View.GONE);
			holder.index_img
					.setBackgroundResource(R.drawable.interlude_animation_list);
			((AnimationDrawable) holder.index_img.getBackground()).start();

			mapp.getMusicaService().setSelectedtrackindex(position);
		}

		holder.delete.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				mapp.getMusicaService().getSongtoplay().remove(position);
				invalidate();
				context.update_nextBackButton();
			}
		});

		convertView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				mapp.getMusicaService().setSelectedtrackindex(position);
				context.initializeViewsandPlay(mapp.getMusicaService()
						.getSongtoplay().get(mapp.getMusicaService().getSelectedtrackindex()));
				mapp.getBaseactivity().playSong(
						mapp.getMusicaService()
								.getSongtoplay()
								.get(mapp.getMusicaService()
										.getSelectedtrackindex()));
			}
		});

	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		convertView = inflater.inflate(R.layout.music_delete_item, parent,
				false);
		fillValues(position, convertView);
		return convertView;

	}

}
