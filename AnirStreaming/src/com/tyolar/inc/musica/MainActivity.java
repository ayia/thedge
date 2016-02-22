package com.tyolar.inc.musica;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.miguelcatalan.materialsearchview.MaterialSearchView;
//import com.google.android.gms.analytics.GoogleAnalytics;
import com.splunk.mint.Mint;
import com.tyolar.inc.musica.Services.AudioPlaybackService;
import com.tyolar.inc.musica.adapter.NavDrawerListAdapter;
import com.tyolar.inc.musica.fragments.DiscoverFragment;
import com.tyolar.inc.musica.fragments.MyAlbumsFragment;
import com.tyolar.inc.musica.fragments.PlayListFragment;
import com.tyolar.inc.musica.model.NavDrawerItem;

public class MainActivity extends BaseActivity {
	private DrawerLayout mDrawerLayout;
	private ListView mDrawerList;
	private ActionBarDrawerToggle mDrawerToggle;
	private final static String TAG_FRAGMENT = "TAG_FRAGMENT";
	// nav drawer title
	private CharSequence mDrawerTitle;
	private Toolbar toolbar;
	// used to store app title
	private CharSequence mTitle;
	public MaterialSearchView searchView;
	MenuItem searchItem;
	// slide menu items
	private String[] navMenuTitles;
	private TypedArray navMenuIcons;

	private ArrayList<NavDrawerItem> navDrawerItems;
	private NavDrawerListAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Mint.initAndStartSession(MainActivity.this, "c3a52671");
		app2 mapp = (app2) getApplication();
		if (savedInstanceState == null) {
			setContentView(R.layout.activity_main);
			startService(new Intent(this, AudioPlaybackService.class));
			mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
			mDrawerList = (ListView) findViewById(R.id.list_slidermenu);
			initview();

		}

		Tracker t = mapp.getTracker(app2.TrackerName.APP_TRACKER);
		t.setScreenName("Home");
		t.send(new HitBuilders.AppViewBuilder().build());
		mapp.setBaseactivity(this);

//		final InterstitialAd mInterstitialAd = new InterstitialAd(this);
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
//
	}

	private void initview() {
		// TODO Auto-generated method stub
		toolbar = (Toolbar) findViewById(R.id.toolbar);
		if (toolbar != null) {
			setSupportActionBar(toolbar);
			toolbar.setNavigationIcon(R.drawable.ic_ab_drawer);
		}

		mTitle = mDrawerTitle = getTitle();

		searchView = (MaterialSearchView) findViewById(R.id.search_view);
		searchView
				.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
					@Override
					public boolean onQueryTextSubmit(String query) {
						// Do some magic
						com.tyolar.inc.musica.fragments.SearchFragment searchFragment = new com.tyolar.inc.musica.fragments.SearchFragment(
								query);
						loadFragment(searchFragment);
						InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
						inputManager.hideSoftInputFromWindow(getCurrentFocus()
								.getWindowToken(),
								InputMethodManager.HIDE_NOT_ALWAYS);
						// searchView.setIconified(true);

						mDrawerLayout.closeDrawer(mDrawerList);
						searchView.closeSearch();
						return false;
					}

					@Override
					public boolean onQueryTextChange(String newText) {
						// Do some magic
						return false;
					}
				});

		// load slide menu items
		navMenuTitles = getResources().getStringArray(R.array.nav_drawer_items);

		// nav drawer icons from resources
		navMenuIcons = getResources()
				.obtainTypedArray(R.array.nav_drawer_icons);

		navDrawerItems = new ArrayList<NavDrawerItem>();

		// adding nav drawer items to array
		// Home
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[0], navMenuIcons
				.getResourceId(0, -1)));
		// playlist
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[1], navMenuIcons
				.getResourceId(1, -1)));
		// albums
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[2], navMenuIcons
				.getResourceId(2, -1)));

		navDrawerItems.add(new NavDrawerItem(navMenuTitles[3], navMenuIcons
				.getResourceId(3, -1)));

		navDrawerItems.add(new NavDrawerItem(navMenuTitles[4], navMenuIcons
				.getResourceId(4, -1)));

		// Recycle the typed array
		navMenuIcons.recycle();

		mDrawerList.setOnItemClickListener(new SlideMenuClickListener());
		adapter = new NavDrawerListAdapter(getApplicationContext(),
				navDrawerItems);
		AddHeaderMenu();
		mDrawerList.setAdapter(adapter);
		mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
				R.drawable.ic_drawer, // nav menu toggle icon
				R.string.app_name, // nav drawer open - description for
									// accessibility
				R.string.app_name // nav drawer close - description for
									// accessibility
		) {
			public void onDrawerClosed(View view) {
				toolbar.setTitle(mTitle);
				// calling onPrepareOptionsMenu() to show action bar icons
				invalidateOptionsMenu();

			}

			public void onDrawerOpened(View drawerView) {
				toolbar.setTitle(mDrawerTitle);
				// calling onPrepareOptionsMenu() to hide action bar icons
				invalidateOptionsMenu();

			}
		};
		mDrawerLayout.setDrawerListener(mDrawerToggle);
		displayView(1);

	}

	private void AddHeaderMenu() {
		View header = (View) getLayoutInflater().inflate(
				R.layout.include_header_list_menu, null);
		mDrawerList.addHeaderView(header);

	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		GoogleAnalytics.getInstance(MainActivity.this)
				.reportActivityStart(this);
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		GoogleAnalytics.getInstance(MainActivity.this).reportActivityStop(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);

		MenuItem item = menu.findItem(R.id.action_search);
		searchView.setMenuItem(item);

		return super.onCreateOptionsMenu(menu);

	}

	private class SlideMenuClickListener implements
			ListView.OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			// display view for selected nav drawer item
			displayView(position - 1);
		}
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// toggle nav drawer on selecting action bar app icon/title

		if (mDrawerToggle.onOptionsItemSelected(item)) {
			return true;
		}

		int id = item.getItemId();
		if (id == R.id.action_shae) {
			Intent intent2 = new Intent();
			intent2.setAction("android.intent.action.SEND");
			intent2.setType("text/plain");
			intent2.putExtra(
					"android.intent.extra.TEXT",
					getResources().getString(R.string.label_share_text)
							+ (" https://play.google.com/store/apps/details?id="
									+ getPackageName()
									+ " http://www.amazon.com/gp/mas/dl/android?p=" + getPackageName()));
			startActivity(Intent.createChooser(intent2, getResources()
					.getString(R.string.label_menu_share)));
		}

		return super.onOptionsItemSelected(item);
		// }
	}

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		// if nav drawer is opened, hide the action items
		boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
		// menu.findItem(R.id.action_settings).setVisible(!drawerOpen);
		return super.onPrepareOptionsMenu(menu);
	}

	private void displayView(int position) {
		// update the main content by replacing fragments
		CFragment fragment = null;

		switch (position) {
		case 0:
			mDrawerList.setItemChecked(position, true);
			mDrawerList.setSelection(position);
			mDrawerLayout.closeDrawer(mDrawerList);
			mDrawerLayout.refreshDrawableState();
			searchView.showSearch(false);
			break;
		case 1:
			// fragment = new DiscoverFragment(navMenuTitles[position]);
			fragment = new DiscoverFragment(navMenuTitles[position]);
			break;

		case 2:
			fragment = new PlayListFragment(navMenuTitles[position]);
			break;
		case 3:
			fragment = new MyAlbumsFragment(navMenuTitles[position]);
			break;
		case 4:
			mDrawerList.setItemChecked(position, true);
			mDrawerList.setSelection(position);
			mDrawerLayout.closeDrawer(mDrawerList);
			mDrawerLayout.refreshDrawableState();
			Intent emailIntent = new Intent(Intent.ACTION_SENDTO,
					Uri.fromParts("mailto", "contact@itconncetinc.com", null));
			// emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Subject");
			// emailIntent.putExtra(Intent.EXTRA_TEXT, "Body");
			startActivity(Intent.createChooser(emailIntent,
					navMenuTitles[position]));

			break;

		default:
			// fragment = new DiscoverFragment(navMenuTitles[position]);
			break;
		}

		if (fragment != null) {
			mDrawerList.setItemChecked(position, true);
			mDrawerList.setSelection(position);
			setTitle(navMenuTitles[position]);
			mDrawerLayout.closeDrawer(mDrawerList);
			mDrawerLayout.refreshDrawableState();
			loadFragment(fragment);

		} else {
			// error in creating fragment
			Log.e("MainActivity", "Error in creating fragment");
		}
	}

	@Override
	public void setTitle(CharSequence title) {
		mTitle = title;
		toolbar.setTitle(mTitle);
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		// Sync the toggle state after onRestoreInstanceState has occurred.
		mDrawerToggle.syncState();
	}

	public Context getcontect() {
		return this;
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		// Pass any configuration change to the drawer toggls
		mDrawerToggle.onConfigurationChanged(newConfig);
	}

}
