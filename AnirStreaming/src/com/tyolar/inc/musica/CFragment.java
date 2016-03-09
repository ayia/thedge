package com.tyolar.inc.musica;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;



public class CFragment extends Fragment {
	private String titel;

	public String getTitel() {
		return titel;
	}

	

	public void setTitel(String titel) {
		this.titel = titel;
	}
	public CFragment() {
		super();
	}
	public CFragment(String titel) {
		super();
		this.titel = titel;
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		getActivity().setTitle(getTitel());
		super.onResume();
	
	}

	
}
