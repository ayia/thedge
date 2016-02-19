package com.tyolar.inc.musica.composants;

import android.content.Context;
import android.view.View;
import android.widget.PopupMenu;

public class LocalePopupMenu extends PopupMenu {

	public LocalePopupMenu(Context activity, View anchor) {
		super(activity, anchor);
	
		
//		Locale locale = new Locale(((PopcornApplication) activity.getApplication()).getAppLocale().getLanguage());
//		Locale.setDefault(locale);
//		Configuration config = activity.getResources().getConfiguration();
//		config.locale = locale;
//		activity.getResources().updateConfiguration(config, activity.getResources().getDisplayMetrics());
	}

}
