package com.tyolar.inc.musica;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.XML;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import com.tyolar.inc.musica.model.apiurls;
import com.tyolar.inc.musica.utils.tools;

public class SplashScreen extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		new idsjson(this).execute();
	}

	class idsjson extends AsyncTask<Void, Void, ArrayList<String>> {
		Context context;

		public idsjson(Context ct) {
			context = ct;
		}

		private ArrayList<String> getlistid() throws Exception {
			ArrayList<String> d = new ArrayList<String>();

			String result = tools.getcontent(new apiurls().getlistids(),
					SplashScreen.this);
			JSONObject jsonObj = null;
			jsonObj = XML.toJSONObject(result);
			JSONArray larray = jsonObj.getJSONObject("main-page")
					.getJSONObject("list").getJSONArray("id");
			for (int i = 0; i < larray.length(); i++) {
				JSONObject jsonij = larray.getJSONObject(i);
				d.add(jsonij.getString("value"));
			}
			Collections.shuffle(d, new Random(System.nanoTime()));
			return d;
		}

		@Override
		protected ArrayList<String> doInBackground(Void... params) {
			// TODO Auto-generated method stub
			ArrayList<String> d;
			try {
				d = getlistid();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				return null;
			}
			return d;
		}

		@Override
		protected void onPostExecute(ArrayList<String> result) {
			if (result == null) {
				AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
				builder1.setTitle(context.getResources().getString(
						R.string.error));
				builder1.setMessage(context.getResources().getString(
						R.string.dmusic_connection_dialog_title));
				builder1.setCancelable(true);
				builder1.setNeutralButton(
						context.getResources().getString(
								R.string.dmusic_button_retry),
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								new idsjson(context).execute();
							}
						});

				AlertDialog alert11 = builder1.create();
				alert11.show();

			} else {
				app2 mapp = (app2) context.getApplicationContext();
				mapp.setAngami_id(result.get(0));
				Intent i = new Intent(SplashScreen.this, MainActivity.class);
				startActivity(i);
				finish();
			}
		}

	};

}
