package com.itconnect.gmae.smartybubbles;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.CookieSyncManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class MainActivity extends Activity {
	private WebView webView;
	private View fragment_loading;
	private View fragment_no_connection;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.webviw);
		webView = (WebView) findViewById(R.id.webview);
		fragment_loading = (View) findViewById(R.id.fragment_loading);
		fragment_no_connection = (View) findViewById(R.id.fragment_no_connection);
		findViewById(R.id.connection_retry_btn).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						loadUrl();
					}
				});
		webView.getSettings().setJavaScriptEnabled(true);
		webView.getSettings().setDomStorageEnabled(true);
		webView.getSettings().setJavaScriptEnabled(true);
		webView.getSettings().setDomStorageEnabled(true);
		webView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
		webView.setWebViewClient(new WebViewClient() {

			@Override
			public void onPageFinished(WebView view, String url) {
				// TODO Auto-generated method stub
				webView.loadUrl("javascript:(function() { "
						+ "var head  = document.getElementsByTagName('head')[0];"
						+ "var link  = document.createElement('link');"
						+

						"link.rel  = 'stylesheet';"
						+ "link.type = 'text/css';"
						+ "link.href = 'http://zmovies.zz.vc/games/SmartyBubbles/css/style.css';"
						+ "link.media = 'all';" + "head.appendChild(link);"
						+ "})()");
				CookieSyncManager.getInstance().sync();
				super.onPageFinished(view, url);
				view.setVisibility(View.VISIBLE);
				fragment_loading.setVisibility(View.GONE);
				fragment_no_connection.setVisibility(View.GONE);
			}

			@Override
			public void onReceivedError(WebView view, int errorCode,
					String description, String failingUrl) {
				// TODO Auto-generated method stub
				super.onReceivedError(view, errorCode, description, failingUrl);
				view.setVisibility(View.GONE);
				fragment_loading.setVisibility(View.GONE);
				fragment_no_connection.setVisibility(View.VISIBLE);
			}

		});
		loadUrl();
	}
	
	private void loadUrl(){
		webView.setVisibility(View.GONE);
		fragment_loading.setVisibility(View.VISIBLE);
		fragment_no_connection.setVisibility(View.GONE);
		webView.loadUrl("http://games.cdn.famobi.com/html5games/s/smarty-bubbles/v8/?fg_domain=play.famobi.com&fg_aid=A1000-1&fg_uid=d8f24956-dc91-4902-9096-a46cb1353b6f&fg_pid=4638e320-4444-4514-81c4-d80a8c662371&fg_beat=667&_ga=1.152057274.2016474715.1424703668");
	}
}
