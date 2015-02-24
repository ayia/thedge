package com.itconnect.gmae.smartybubbles;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.CookieSyncManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.PopupMenu;
import android.widget.PopupMenu.OnMenuItemClickListener;

import com.google.analytics.tracking.android.EasyTracker;

public class MainActivity extends Activity {
	private WebView webView;
	private View fragment_loading;
	private View fragment_no_connection;
	private View fragment_webview;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.webviw);
		webView = (WebView) findViewById(R.id.webview);
		fragment_loading = (View) findViewById(R.id.fragment_loading);
		fragment_no_connection = (View) findViewById(R.id.fragment_no_connection);
		fragment_webview = (View) findViewById(R.id.fragment_webview);

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
				fragment_webview.setVisibility(View.VISIBLE);
				fragment_loading.setVisibility(View.GONE);
				fragment_no_connection.setVisibility(View.GONE);
			}

			@Override
			public void onReceivedError(WebView view, int errorCode,
					String description, String failingUrl) {
				// TODO Auto-generated method stub
				super.onReceivedError(view, errorCode, description, failingUrl);
				fragment_webview.setVisibility(View.GONE);
				fragment_loading.setVisibility(View.GONE);
				fragment_no_connection.setVisibility(View.VISIBLE);
			}

		});
		findViewById(R.id.popcorn_action_overflow).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						onOverflowPressed(v);
					}
				});

		loadUrl();
	}

	private void onOverflowPressed(final View v) {
		PopupMenu popup = new PopupMenu(MainActivity.this, v);

		try {
			Field[] fields = popup.getClass().getDeclaredFields();
			for (Field field : fields) {
				if ("mPopup".equals(field.getName())) {
					field.setAccessible(true);
					Object menuPopupHelper = field.get(popup);
					Class<?> classPopupHelper = Class.forName(menuPopupHelper
							.getClass().getName());
					Method setForceIcons = classPopupHelper.getMethod(
							"setForceShowIcon", boolean.class);
					setForceIcons.invoke(menuPopupHelper, true);
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		popup.setOnMenuItemClickListener(new OnMenuItemClickListener() {

			@Override
			public boolean onMenuItemClick(MenuItem item) {

				switch (item.getItemId()) {
				case R.id.menu_share:
					Intent sharingIntent = new Intent(
							android.content.Intent.ACTION_SEND);
					sharingIntent.setType("text/plain");
					String utl = "https://play.google.com/store/apps/details?id="
							+ getApplicationContext().getPackageName();
					String shareBody = v.getResources()
							.getString(R.string.share_msg)+" "+utl;
					sharingIntent.putExtra(
							android.content.Intent.EXTRA_SUBJECT, v.getResources()
							.getString(R.string.app_name));
					sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT,
							shareBody);
					startActivity(sharingIntent);

					break;
				case R.id.menu_like:
					try {
						startActivity(new Intent(Intent.ACTION_VIEW, Uri
								.parse("market://details?id=" + getApplicationContext().getPackageName())));
					} catch (android.content.ActivityNotFoundException anfe) {
						startActivity(new Intent(
								Intent.ACTION_VIEW,
								Uri.parse("https://play.google.com/store/apps/details?id="
										+ getApplicationContext().getPackageName())));
					}
					break;
				case R.id.menu_more:
					try {
						startActivity(new Intent(Intent.ACTION_VIEW, Uri
								.parse("market://search?q=pub:" + v.getResources()
										.getString(R.string.developer_id))));
					} catch (android.content.ActivityNotFoundException anfe) {
						startActivity(new Intent(
								Intent.ACTION_VIEW,
								Uri.parse("http://play.google.com/store/search?q=pub:"
										+ v.getResources()
										.getString(R.string.developer_id))));
					}
					break;
				default:
					return false;
				}

				return false;

			}
		});
		popup.inflate(R.menu.main);
		popup.show();
	}

	private void loadUrl() {
		fragment_webview.setVisibility(View.GONE);
		fragment_loading.setVisibility(View.VISIBLE);
		fragment_no_connection.setVisibility(View.GONE);
		webView.loadUrl("http://games.cdn.famobi.com/html5games/s/smarty-bubbles/v8/?fg_domain=play.famobi.com&fg_aid=A1000-1&fg_uid=d8f24956-dc91-4902-9096-a46cb1353b6f&fg_pid=4638e320-4444-4514-81c4-d80a8c662371&fg_beat=667&_ga=1.152057274.2016474715.1424703668");
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		EasyTracker.getInstance(this).activityStart(this);
		super.onStart();
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		EasyTracker.getInstance(this).activityStop(this);
		super.onStop();
	}

}
