package com.tyolar.inc.musica.BroadcastReceivers;

import com.tyolar.inc.musica.app2;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class PreviousBroadcastReceiver extends BroadcastReceiver {
	
	@Override
	public void onReceive(Context context, Intent intent) {
		app2 mapp = (app2) context.getApplicationContext();
		mapp.getMusicaService().setSelectedtrackindex(
				mapp.getMusicaService().getSelectedtrackindex() - 1);
		mapp.getBaseactivity().playSong(
				mapp.getMusicaService()
						.getSongtoplay()
						.get(mapp.getMusicaService()
								.getSelectedtrackindex()));

		if (mapp.getCurrentActivity() instanceof com.tyolar.inc.musica.PlayerActivity) {
			mapp.getCurrentActivity()
					.recreate();
		}
	}
	  
}