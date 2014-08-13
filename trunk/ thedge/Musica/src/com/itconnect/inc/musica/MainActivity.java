/*
 * Copyright 2011 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.itconnect.inc.musica;

import org.apache.cordova.DroidGap;
import android.view.LayoutInflater;
import android.view.View;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class MainActivity extends DroidGap {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		super.init();
		super.setIntegerProperty("splashscreen", R.drawable.splach_screen);
		super.appView.getSettings().setJavaScriptEnabled(true);
		super.setBooleanProperty("loadInWebView", true);
		super.loadUrl(getResources().getString(R.string.path_url),10000);
		GrooveSharkCatcher myWebViewClient = new GrooveSharkCatcher(this);
		myWebViewClient.setWebView(this.appView);
		this.appView.setWebViewClient(myWebViewClient);
		
		LayoutInflater inflater = (LayoutInflater)    
				getContext().getSystemService(super.getContext().LAYOUT_INFLATER_SERVICE);
		View adView = inflater.inflate(R.layout.main, null);
	 	android.widget.LinearLayout layout = super.root;
		layout.addView(adView);
		layout.setHorizontalGravity(android.view.Gravity.CENTER_HORIZONTAL); 
	}

	public void onReceivedError(int errorCode, String description,
			String failingUrl) {
		super.loadUrl("file:///android_asset/noconnction.html");

	}

	//
	// public void onCreate(Bundle savedInstanceState) {
	// super.onCreate(savedInstanceState);
	// setContentView(R.layout.main);
	//
	// mainView = (CordovaWebView) findViewById(R.id.mainView);
	// mainView.loadUrl(getResources().getString(R.string.path_url));
	//
	// mainView.getSettings().setJavaScriptEnabled(true);
	// mainView.setWebViewClient(new WebViewClient() {
	//
	// @Override
	// public void onPageStarted(WebView view, String url, Bitmap favicon) {
	// view.setVisibility(View.GONE);
	//
	// }
	//
	// @Override
	// public void onPageFinished(WebView view, String url) {
	// view.loadUrl("javascript:(function() { " +
	// "var head  = document.getElementsByTagName('head')[0];"+
	// "var link  = document.createElement('link');"+
	//
	// "link.rel  = 'stylesheet';"+
	// "link.type = 'text/css';"+
	// "link.href = 'http://zaaztv.com/9gag/styles.css';"+
	// "link.media = 'all';"+
	// "head.appendChild(link);"+
	// "})()");
	// super.onPageFinished(view, url);
	// view.setVisibility(View.VISIBLE);
	//
	// }
	//
	// @Override
	// public void onReceivedError(WebView view, int errorCode,
	// String description, String failingUrl) {
	// view.loadUrl("file:///android_asset/noconnction.html");
	// super.onReceivedError(view, errorCode, description, failingUrl);
	// }
	// });
	//
	// }
	//
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater menuInflater = getMenuInflater();
		menuInflater.inflate(R.menu.main, menu);

		// Calling super after populating the menu is necessary here to ensure
		// that the
		// action bar helpers have a chance to handle this event.
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menu_share:
			Intent sharingIntent = new Intent(
					android.content.Intent.ACTION_SEND);
			sharingIntent.setType("text/plain");
			String shareBody = getResources().getString(R.string.share_text);
			sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,
					"Musica");
			sharingIntent
					.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
			startActivity(Intent.createChooser(sharingIntent, "Share via"));
			break;
		}
		return super.onOptionsItemSelected(item);
	}

}
