package com.itconnect.inc.musica;

import org.apache.cordova.CordovaWebViewClient;
import org.apache.cordova.DroidGap;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.webkit.WebView;

public class GrooveSharkCatcher extends CordovaWebViewClient {

	public GrooveSharkCatcher(DroidGap cordova) {
		super(cordova);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onPageFinished(WebView view, String url) {
		// TODO Auto-generated method stub
		view.loadUrl("javascript:(function() { "
				+ "var head  = document.getElementsByTagName('head')[0];"
				+ "var link  = document.createElement('link');" +

				"link.rel  = 'stylesheet';" + "link.type = 'text/css';"
				+ "link.href = 'http://zaaztv.com/9gag/styles.css';"
				+ "link.media = 'all';" + "head.appendChild(link);" + "})()");
		super.onPageFinished(view, url);

	}

	@Override
	public void onReceivedError(WebView view, int errorCode,
			String description, String failingUrl) {
		view.loadUrl("file:///android_asset/noconnction.html");
		super.onReceivedError(view, errorCode, description, failingUrl);
	}
	 @Override
     public boolean shouldOverrideUrlLoading(WebView view, String url) {
         if (isNetworkAvailable(view)) {
            
             return false;
         }
         view.loadUrl("file:///android_asset/noconnction.html");
         return true;
     }

     public boolean isNetworkAvailable(WebView view) {
         ConnectivityManager connec = (ConnectivityManager)view.getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
         NetworkInfo ni = connec.getActiveNetworkInfo();
         if (ni == null) {
             // There are no active networks.
             return false;
         }
         return true;
     }
}
