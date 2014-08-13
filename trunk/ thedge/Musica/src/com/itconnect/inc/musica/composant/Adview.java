package com.itconnect.inc.musica.composant;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import com.itconnect.inc.musica.R;

public class Adview extends View{

	public Adview(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		LayoutInflater inflater = (LayoutInflater)    
				getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

				View view = inflater.inflate(R.layout.main, null);
	
	}

}
