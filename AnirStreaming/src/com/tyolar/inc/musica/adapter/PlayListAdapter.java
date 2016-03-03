package com.tyolar.inc.musica.adapter;

import java.util.Random;

import android.annotation.SuppressLint;
import android.content.Context;
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
import com.tyolar.inc.musica.DAO.PlayListDAO;
import com.tyolar.inc.musica.composants.LocalePopupMenu;
import com.tyolar.inc.musica.composants.Playlist_Editor;
import com.tyolar.inc.musica.fragments.PlayListFragment;
import com.tyolar.inc.musica.fragments.PlayList_SongsFragment;
import com.tyolar.inc.musica.model.PlayList;
import com.tyolar.inc.musica.model.apiurls;

@SuppressLint("NewApi")
public class PlayListAdapter extends BaseAdapter {
	private LayoutInflater inflater;
	private PlayList[] listactor;
	private PlayList selectedplaylist;

	private PlayListFragment PlayListFragment;
	BaseActivity context;

	public PlayListAdapter(BaseActivity context, PlayList[] listactor,
			PlayListFragment PlayListFragment) {
		super();
		this.PlayListFragment = PlayListFragment;
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
	public PlayList getItem(int position) {
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
		if (getItem(position).getSongs().size() > 1) {
			Random rand = new Random();

			String url = new apiurls().getArtimage();
			int randomNum = rand
					.nextInt(getItem(position).getSongs().size() - 1);
			url = url.replace("[sid]", mapp.getAngami_id()).replace("[id]",
					getItem(position).getSongs().get(randomNum).getCoverArt());

			Picasso.with(context).load(url).placeholder(R.drawable.ic_launcher)
					.error(R.drawable.ic_launcher).into(holder.container);
		}

		holder.subtext.setText(String.format(
				res.getString(R.string.dmusic_playlist_all_songs),
				getItem(position).getSongs().size()));
		holder.titel.setText(getItem(position).getName());
		holder.PlayList = getItem(position);
		holder.img_more.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				onOverflowPressed(v, getItem(position));
			}

		});
		convertView.setId(holder.PlayList.getId());
		if (holder.PlayList.getSongs().size() > 0)
			convertView.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					try {

						PlayList pls = PlayListDAO.get(context).get(position);
						PlayList_SongsFragment s = new PlayList_SongsFragment(
								pls);
						context.loadFragment(s);
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

	private void onOverflowPressed(final View v, final PlayList pls) {
		// TODO Auto-generated method stub
		LocalePopupMenu popup = new LocalePopupMenu(this.context, v);
		popup.inflate(R.menu.menu_playlist_music);
		if (pls.getSongs().size() == 0)
			popup.getMenu().getItem(0).setVisible(false);

		popup.setOnMenuItemClickListener(new OnMenuItemClickListener() {

			@Override
			public boolean onMenuItemClick(MenuItem item) {
				switch (item.getItemId()) {
				case R.id.play_all:
					context.PlayAllSongs(pls.getSongs());
					break;
				case R.id.remove_playlist:
					try {
						PlayListDAO.remove(v.getContext(), pls);
						PlayListFragment.notifyDataSetChanged();
						Toast.makeText(
								v.getContext(),
								context.getString(R.string.playlist_deleted_message),
								Toast.LENGTH_LONG).show();

					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						Toast.makeText(v.getContext(),
								context.getString(R.string.error),
								Toast.LENGTH_LONG).show();
						 app2 mapp = (app2) context.getApplicationContext();
						 mapp.getInstance().trackException(e);
					}
					break;
				case R.id.edit_playlist:
					try {
						final Playlist_Editor plsçeditor = new Playlist_Editor(
								context);

						plsçeditor.setHeader_titel(context.getResources()
								.getString(R.string.edit_playlist_menu));
						plsçeditor.setPlaylist_name(pls.getName());
						plsçeditor.setPlaylist_description(pls.getDescription());
						final MaterialDialog d = new MaterialDialog.Builder(
								context)
								.title("Edit")
								.customView(plsçeditor, false)
								.callback(new MaterialDialog.ButtonCallback() {
									@Override
									public void onPositive(MaterialDialog dialog) {
										try {
											pls.setDescription(plsçeditor
													.getPlaylist_description());
											pls.setName(plsçeditor
													.getPlaylist_name());
											PlayListDAO.remove(v.getContext(),
													pls);
											PlayListDAO.save(v.getContext(),
													pls);
											dialog.dismiss();
											Toast.makeText(
													v.getContext(),
													v.getContext()
															.getResources()
															.getString(
																	R.string.playlist_created),
													Toast.LENGTH_LONG).show();
											invalidate();
										} catch (Exception e) {
											// TODO Auto-generated catch block
											Toast.makeText(
													v.getContext(),
													v.getContext()
															.getResources()
															.getString(
																	R.string.error),
													Toast.LENGTH_LONG).show();
											 app2 mapp = (app2) context.getApplicationContext();
											 mapp.getInstance().trackException(e);

										}

									}

									@Override
									public void onNegative(MaterialDialog dialog) {
										dialog.dismiss();
									}
								}).positiveText(R.string.save_as_playlist)
								.negativeText(R.string.cancel).show();
						plsçeditor.getname_playlist().addTextChangedListener(
								new TextWatcher() {
									public void beforeTextChanged(
											CharSequence s, int start,
											int count, int after) {
										// don't care about this one
									}

									public void onTextChanged(CharSequence s,
											int start, int before, int count) {
										String newText = plsçeditor
												.getname_playlist().getText()
												.toString();
										if (newText.trim().length() == 0) {
											d.getpositiveButton().setEnabled(
													false);
										} else {
											d.getpositiveButton().setEnabled(
													true);

										}
									};

									public void afterTextChanged(Editable s) {
										// don't care about this one
									}
								});
						// d.getpositiveButton().setEnabled(false);

					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						Toast.makeText(v.getContext(), "a7777777",
								Toast.LENGTH_LONG).show();
						 app2 mapp = (app2) context.getApplicationContext();
						 mapp.getInstance().trackException(e);
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
		public PlayList PlayList;
		public ImageView container;
		public com.ctrlplusz.anytextview.AnyTextView titel;
		public TextView subtext;
		public ImageView img_more;
	}

}
