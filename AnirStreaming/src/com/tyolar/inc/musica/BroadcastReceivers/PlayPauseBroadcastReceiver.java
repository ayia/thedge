package com.tyolar.inc.musica.BroadcastReceivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.tyolar.inc.musica.app2;


public class PlayPauseBroadcastReceiver extends BroadcastReceiver {
	@Override
	public void onReceive(Context context, Intent intent) {
		app2 mapp = (app2) context.getApplicationContext();
			mapp.getBaseactivity().togglePlaybackState();
			if (mapp.getCurrentActivity() instanceof com.tyolar.inc.musica.PlayerActivity) {
				mapp.getCurrentActivity()
						.recreate();
			}
	}

}
