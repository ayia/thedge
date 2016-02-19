package com.tyolar.inc.musica.composants;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.tyolar.inc.musica.R;

@SuppressLint("NewApi")
public class ads_banner extends RelativeLayout {

	public ads_banner(Context context) {
		super(context);
		initview(context);
	}

	public ads_banner(Context context, AttributeSet attrs) {
		super(context, attrs);
		initview(context);
	}

	public ads_banner(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initview(context);
	}

	private void initview(Context context) {
		// TODO Auto-generated method stub
		LayoutInflater mInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mInflater.inflate(R.layout.include_ads, this, true);
		try{
		AdView mAdView = (AdView) findViewById(R.id.adView);
	        AdRequest adRequest = new AdRequest.Builder().build();
	        mAdView.loadAd(adRequest);
		}catch(Exception s){
			s.printStackTrace();
		}
	}

	
}
