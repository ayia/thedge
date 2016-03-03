package com.tyolar.inc.musica.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.PopupMenu.OnMenuItemClickListener;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.squareup.picasso.Picasso;
import com.tyolar.inc.musica.BaseActivity;
import com.tyolar.inc.musica.R;
import com.tyolar.inc.musica.app2;
import com.tyolar.inc.musica.DAO.AlbumsDAO;
import com.tyolar.inc.musica.composants.LocalePopupMenu;
import com.tyolar.inc.musica.fragments.AlbumFragment;
import com.tyolar.inc.musica.fragments.MyAlbumsFragment;
import com.tyolar.inc.musica.model.album;
import com.tyolar.inc.musica.model.apiurls;

@SuppressLint("NewApi")
public class MyAlbumAdapter extends BaseAdapter {
	private LayoutInflater inflater;
	private album[] listactor;
	private album selectedalbum;

	private MyAlbumsFragment albumFragment;
	BaseActivity context;

	public MyAlbumAdapter(BaseActivity context, album[] listactor,
			MyAlbumsFragment albumFragment) {
		super();
		this.albumFragment = albumFragment;
		this.context = context;
		inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		this.listactor = listactor;

	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return listactor.length;
	}

	@Override
	public album getItem(int position) {
		// TODO Auto-generated method stub
		return listactor[position];
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		final ActorHolder holder;
		final app2 mapp = (app2) context.getApplicationContext();
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.playlist_grid_item, parent,
					false);
			holder = new ActorHolder();
			holder.container = (ImageView) convertView
					.findViewById(R.id.container);
			holder.titel = (com.ctrlplusz.anytextview.AnyTextView) convertView
					.findViewById(R.id.grid_item_title);
			holder.subtext = (TextView) convertView
					.findViewById(R.id.grid_item_subtext);
			holder.img_more = (ImageView) convertView
					.findViewById(R.id.more_icon);
			convertView.setTag(holder);
		} else {
			holder = (ActorHolder) convertView.getTag();
		}
		Resources res = convertView.getResources();

		String url = new apiurls().getArtimage();

		url = url.replace("[sid]",
				mapp.getAngami_id()).replace(
				"[id]", getItem(position).getCoverArt());

		Picasso.with(context).load(url).placeholder(R.drawable.ic_launcher)
				.error(R.drawable.ic_launcher).into(holder.container);

		holder.subtext.setText(getItem(position).getArtist());
		holder.titel.setText(getItem(position).getTitle());
		holder.album = getItem(position);
		holder.img_more.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				onOverflowPressed(v, getItem(position));
			}

		});
		convertView.setId(position);
		convertView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				try {
					Intent myIntent = new Intent(context,
							com.tyolar.inc.musica.activities.Album_Activity.class);
					myIntent.putExtra("album", holder.album.toJson());
					context.startActivity(myIntent);
					
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					 app2 mapp = (app2) context.getApplicationContext();
					 mapp.getInstance().trackException(e);
				}
			}
		});

		return convertView;
	}

	private void onOverflowPressed(final View v, final album pls) {
		// TODO Auto-generated method stub
		LocalePopupMenu popup = new LocalePopupMenu(this.context, v);
		popup.inflate(R.menu.menu_myalbum);
		popup.setOnMenuItemClickListener(new OnMenuItemClickListener() {

			@Override
			public boolean onMenuItemClick(MenuItem item) {
				switch (item.getItemId()) {
				
				case R.id.remove_album:
					try {
						AlbumsDAO.remove(context, pls);
						albumFragment.loadMyalbums(context, albumFragment);
						   Toast.makeText(context, 
								   context.getResources().getString(R.string.one_album_deleted)
								   , Toast.LENGTH_LONG).show();	
						   
						   albumFragment.notifyDataSetChanged();
					} catch (Exception e) {
						 Toast.makeText(context, 
								   context.getResources().getString(R.string.error)
								   , Toast.LENGTH_LONG).show();	
						
						 app2 mapp = (app2) context.getApplicationContext();
						 mapp.getInstance().trackException(e);
						e.printStackTrace();
					}
					break;
				default:
					return false;
				}

				return false;
			}
		});

		popup.show();
	}

	private void invalidate() {
		// TODO Auto-generated method stub
		notifyDataSetChanged();

	}

	private class ActorHolder {
		public album album;
		public ImageView container;
		public com.ctrlplusz.anytextview.AnyTextView titel;
		public TextView subtext;
		public ImageView img_more;
	}

}
