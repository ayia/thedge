package com.tyolar.inc.musica.datalaoder;

import java.util.ArrayList;
import java.util.Random;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TableLayout;

import com.tyolar.inc.musica.R;
import com.tyolar.inc.musica.app2;
import com.tyolar.inc.musica.composants.SearchListView;
import com.tyolar.inc.musica.model.searchResult;
import com.tyolar.inc.musica.utils.tools;

public class Discover_json extends
		AsyncTask<String, Void, ArrayList<searchResult>> {
	private Exception exExceptionc = null;
	private static String query = "";
	private Context context;
	private TableLayout main_music;
	private View main;
	private View loading;
	private View errorzone;
	private Exception s = null;
	private String url;

	public Discover_json(Context context, View m) {
		// TODO Auto-generated constructor stub
		super();
		this.context = context;
		this.main = m;
		this.main_music = (TableLayout) this.main.findViewById(R.id.listmusic);
		this.loading = this.main.findViewById(R.id.loading_view);
		this.errorzone = this.main.findViewById(R.id.error_view);

	}

	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		this.loading.setVisibility(View.VISIBLE);
		this.errorzone.setVisibility(View.GONE);
		this.main_music.setVisibility(View.GONE);
		super.onPreExecute();

	}

	@Override
	protected void onProgressUpdate(Void... values) {
		// TODO Auto-generated method stub
		this.loading.setVisibility(View.VISIBLE);
		this.main_music.setVisibility(View.GONE);
		this.errorzone.setVisibility(View.GONE);
		super.onProgressUpdate(values);
	}

	@Override
	protected void onPostExecute(ArrayList<searchResult> result) {

		if (s == null) {
			int i = 1;
			for (searchResult sx : result) {

				if (sx.getAlbumlist() != null && sx.getAlbumlist().size() > 0) {
					SearchListView artistgrid2;
					if (result.size() == 1) {
						Random r = new Random();
						int Low = 100;
						int High = 200;
						int Result = r.nextInt(High - Low) + Low;

						artistgrid2 = new SearchListView(context,
								tools.getValue(sx.getTitle(), context), Result,
								3);
					} else

						artistgrid2 = new SearchListView(context,
								tools.getValue(sx.getTitle(), context), 1, 3);
					artistgrid2.setId(i);
					artistgrid2.setAlbumlist(sx);
					main_music.addView(artistgrid2);
				}
				if (sx.getSonglist() != null && sx.getSonglist().size() > 0) {
					SearchListView artistgrid2 = new SearchListView(context,
							tools.getValue(sx.getTitle(), context), 5, 1);
					artistgrid2.setId(i);
					artistgrid2.setSonglist(sx);
					main_music.addView(artistgrid2);
				}

				i++;
			}
			this.loading.setVisibility(View.GONE);
			this.errorzone.setVisibility(View.GONE);
			this.main_music.setVisibility(View.VISIBLE);
		} else {
			this.loading.setVisibility(View.GONE);
			this.errorzone.setVisibility(View.VISIBLE);
			this.main_music.setVisibility(View.GONE);
			this.errorzone.findViewById(R.id.connection_retry_btn)
					.setOnClickListener(new OnClickListener() {
						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							new Discover_json(context, main).execute(url);

						}
					});
		}
	}

	@Override
	protected ArrayList<searchResult> doInBackground(String... params) {
		// TODO Auto-generated method stub
		try {
			this.url = params[0];
			return tools.gettop_chart(context, params[0]);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			s = e;
			 app2 mapp = (app2) context.getApplicationContext();
			 mapp.getInstance().trackException(e);

		}
		return null;
	}

}
