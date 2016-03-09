package com.tyolar.inc.musica;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;

public class FCFragment extends Fragment {
	private String titel;
	protected InterstitialAd mInterstitialAd;

	public String getTitel() {
		return titel;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
//		mInterstitialAd = new InterstitialAd(this.getActivity());
//		mInterstitialAd.setAdUnitId("ca-app-pub-3908763514019803/6819661174");
//		AdRequest adRequest = new AdRequest.Builder().addTestDevice(
//				"SEE_YOUR_LOGCAT_TO_GET_YOUR_DEVICE_ID").build();
//		mInterstitialAd.loadAd(adRequest);
//		// Begin listening to interstitial & show ads.
//		mInterstitialAd.setAdListener(new AdListener() {
//			public void onAdLoaded() {
//				mInterstitialAd.show();
//			}
//		});
	}

	public void setTitel(String titel) {
		this.titel = titel;
	}

	public FCFragment(String titel) {
		super();
		this.titel = titel;

	}
	public FCFragment() {
		super();
		}

}
