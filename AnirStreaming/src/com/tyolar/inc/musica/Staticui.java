package com.tyolar.inc.musica;

import java.util.ArrayList;
import java.util.Random;

import android.app.Activity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.tyolar.inc.musica.DAO.PlayListDAO;
import com.tyolar.inc.musica.composants.Playlist_Editor;
import com.tyolar.inc.musica.model.PlayList;
import com.tyolar.inc.musica.model.song;

public class Staticui {
	public static void show_add_playlist_view(final Activity ac,final song song) {
		try {
			PlayList[] listactor = new PlayList[0];
			try {
				listactor = PlayListDAO.get(ac).toArray(
						new PlayList[PlayListDAO.get(ac)
								.size()]);
			} catch (Exception d) {
				listactor = new PlayList[0];
			}
			final int size = listactor.length;
			String[] plsnames = new String[listactor.length + 1];

			for (int i = 0; i < listactor.length; i++)
				plsnames[i] = listactor[i].getName();

			plsnames[listactor.length] = ac.getResources().getString(
					R.string.dmusic_playlist_create_playlist);

			new MaterialDialog.Builder(ac)
					.title(R.string.dmusic_playlist_add_to_title).items(plsnames)
					.negativeText(R.string.cancel)
					.itemsCallback(new MaterialDialog.ListCallback() {
						@Override
						public void onSelection(MaterialDialog dialog,
								View view, int which, CharSequence text) {
							try {
								if (which == size) {

									create_playlist(ac,song);

								} else {
									PlayList d = PlayListDAO.get(
											ac).get(which);

									if (d.getSongs() == null)
										d.setSongs(new ArrayList<song>());
									d.getSongs().add(song);
									PlayListDAO.remove(ac, d);
									PlayListDAO.save(ac, d);
									Toast.makeText(
											ac,
											ac.getResources().getString(
													R.string.one_song_added),
											Toast.LENGTH_LONG).show();

								}
							} catch (Exception e) {
								// TODO Auto-generated catch
								// block
								e.printStackTrace();
								Toast.makeText(
										ac,
										ac.getResources()
												.getString(
														R.string.error),
										Toast.LENGTH_LONG).show();
							}

						}

					}).show();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Toast.makeText(ac,
					ac.getResources().getString(R.string.error),

					Toast.LENGTH_LONG).show();

		}

	}
	public static void create_playlist(final Activity ac,final song song) {
		// TODO Auto-generated method stub
		final Playlist_Editor plsçeditor = new Playlist_Editor(
				ac);
		Random generator = new Random();
		final int ida = generator.nextInt(1000000);
		final MaterialDialog d = new MaterialDialog.Builder(ac)
				.title(ac.getResources().getString(
						R.string.dmusic_download_cancel_dialog_prompt_collection_playlist))
				.customView(plsçeditor, false)
				.callback(new MaterialDialog.ButtonCallback() {
					@Override
					public void onPositive(MaterialDialog dialog) {
						try {

							PlayList pls = new PlayList(ida, plsçeditor
									.getPlaylist_name(), plsçeditor
									.getPlaylist_description(), null);
							ArrayList<song> list = new ArrayList<song>() {
								{
									if (song != null)
										add(song);
								}
							};
							pls.setSongs(list);
							PlayListDAO.save(ac, pls);

							dialog.dismiss();

							Toast.makeText(
									ac,
									ac.getResources().getString(
											R.string.playlist_created),
									Toast.LENGTH_LONG).show();

						} catch (Exception e) {
							// TODO Auto-generated catch block
							Toast.makeText(
									ac,
									ac.getResources().getString(
											R.string.error),
									Toast.LENGTH_LONG).show();

						}

					}

					@Override
					public void onNegative(MaterialDialog dialog) {
						dialog.dismiss();
					}
				}).positiveText(R.string.save_as_playlist)
				.negativeText(R.string.cancel).show();
		plsçeditor.getname_playlist().addTextChangedListener(new TextWatcher() {
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// don't care about this one
			}

			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				String newText = plsçeditor.getname_playlist().getText()
						.toString();
				if (newText.trim().length() == 0) {
					d.getpositiveButton().setEnabled(false);
				} else {
					d.getpositiveButton().setEnabled(true);

				}
			};

			public void afterTextChanged(Editable s) {
				// don't care about this one
			}
		});

	}
	public static void create_playlist(final Activity ac,final ArrayList<song> list) {
		// TODO Auto-generated method stub
		final Playlist_Editor plsçeditor = new Playlist_Editor(
				ac);
		Random generator = new Random();
		final int ida = generator.nextInt(1000000);
		final MaterialDialog d = new MaterialDialog.Builder(ac)
				.title(ac.getResources().getString(
						R.string.dmusic_download_cancel_dialog_prompt_collection_playlist))
				.customView(plsçeditor, false)
				.callback(new MaterialDialog.ButtonCallback() {
					@Override
					public void onPositive(MaterialDialog dialog) {
						try {

							PlayList pls = new PlayList(ida, plsçeditor
									.getPlaylist_name(), plsçeditor
									.getPlaylist_description(), null);
							pls.setSongs(list);
							PlayListDAO.save(ac, pls);

							dialog.dismiss();

							Toast.makeText(
									ac,
									ac.getResources().getString(
											R.string.playlist_created),
									Toast.LENGTH_LONG).show();

						} catch (Exception e) {
							// TODO Auto-generated catch block
							Toast.makeText(
									ac,
									ac.getResources().getString(
											R.string.error),
									Toast.LENGTH_LONG).show();

						}

					}

					@Override
					public void onNegative(MaterialDialog dialog) {
						dialog.dismiss();
					}
				}).positiveText(R.string.save_as_playlist)
				.negativeText(R.string.cancel).show();
		plsçeditor.getname_playlist().addTextChangedListener(new TextWatcher() {
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// don't care about this one
			}

			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				String newText = plsçeditor.getname_playlist().getText()
						.toString();
				if (newText.trim().length() == 0) {
					d.getpositiveButton().setEnabled(false);
				} else {
					d.getpositiveButton().setEnabled(true);

				}
			};

			public void afterTextChanged(Editable s) {
				// don't care about this one
			}
		});

	}



}
