package com.example.navigationdrawermp3;

import java.util.ArrayList;

import android.app.ActionBar;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.SearchManager;
import android.app.ActionBar.Tab;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;

public class MainActivity extends FragmentActivity implements ActionBar.TabListener {
	
	ActionBar actionBar;
	MenuItem refreshMenuItem;
	ViewPager viewPager;
	private TabsFragmentPagerAdapter tabsAdapter;
	private String[] days = new String[] { "Facebook", "Whatsapp", "Twitter" };
	
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;
 
    private CharSequence mDrawerTitle;
 
    private CharSequence mTitle;
 
    private String[] navMenuTitles;
    private TypedArray navMenuIcons;
 
    private ArrayList<NavDrawerItem> navDrawerItems;
    private NavDrawerListAdapter adapter;
 
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
 
        mTitle = mDrawerTitle = getTitle();
 
        navMenuTitles = getResources().getStringArray(R.array.nav_drawer_items);
 
        navMenuIcons = getResources()
                .obtainTypedArray(R.array.nav_drawer_icons);
 
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.list_slidermenu);
 
        navDrawerItems = new ArrayList<NavDrawerItem>();
        
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[0], navMenuIcons.getResourceId(0, -1)));
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[1], navMenuIcons.getResourceId(1, -1)));
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[2], navMenuIcons.getResourceId(2, -1), true, "22"));
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[3], navMenuIcons.getResourceId(3, -1)));
        
        mDrawerList.setOnItemClickListener(new DrawerClickListener());
        
        adapter = new NavDrawerListAdapter(getApplicationContext(),
                navDrawerItems);
        mDrawerList.setAdapter(adapter);
 
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);
 
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                R.drawable.ic_drawer, 
                R.string.app_name, 
                R.string.app_name 
        ){
            public void onDrawerClosed(View view) {
                getActionBar().setTitle(mTitle);
                invalidateOptionsMenu();
            }
 
            public void onDrawerOpened(View drawerView) {
                getActionBar().setTitle(mDrawerTitle);
                invalidateOptionsMenu();
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);
 
        if (savedInstanceState == null) {
            displayView(0);
        }
        
        actionBar = getActionBar();
		viewPager = (ViewPager) findViewById(R.id.viewPager);
		tabsAdapter = new TabsFragmentPagerAdapter(getSupportFragmentManager());
		viewPager.setAdapter(tabsAdapter);
		actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		for (int i = 0; i < 3; i++) {
			actionBar.addTab(actionBar.newTab().setText(days[i])
					.setTabListener(this));
		}
		viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

			@Override
			public void onPageSelected(int arg) {
				// TODO Auto-generated method stub
				actionBar.setSelectedNavigationItem(arg);
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub

			}
		});
    }
    
    private class DrawerClickListener implements ListView.OnItemClickListener{
    	@Override
    	public void onItemClick(AdapterView<?> parent, View view, int position, long id)
    	{
    		displayView(position);
    	}
    }
    
    private void displayView(int position){
    	Fragment fragment = null;
    	switch(position)
    	{
    		case 0:
    			fragment = new HomeFrag();
    			break;
    		case 1:
    			fragment = new PeopleFrag();
    			break;
    		case 2:
    			fragment = new CommunityFrag();
    			break;
    		case 3:
    			fragment = new PagesFrag();
    			break;
    	}
    	
    	if (fragment != null)
    	{
    		FragmentManager fragManager = getFragmentManager();
    		fragManager.beginTransaction().replace(R.id.frame_container, fragment).commit();
    		
    		mDrawerList.setItemChecked(position, true);
    		mDrawerList.setSelection(position);
    		setTitle(navMenuTitles[position]);
    		mDrawerLayout.closeDrawer(mDrawerList);
    	}
    	
    	else
    	{
    		Log.e("MainActivity", "Error in creating Fragment");
    	}
    }
 
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
    	// Inflate the menu; this adds items to the action bar if it is present.
    	getMenuInflater().inflate(R.menu.main, menu);

    	MenuInflater inflater = getMenuInflater();
    	inflater.inflate(R.menu.activity_main, menu);
    			// Associate searchable configuration with the SearchView
    	SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
    	SearchView searchView = (SearchView) menu.findItem(R.id.action_search)
    					.getActionView();
    	searchView.setSearchableInfo(searchManager
    			.getSearchableInfo(getComponentName()));

    	return super.onCreateOptionsMenu(menu);
    }
 
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	
         if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
            
            switch (item.getItemId()) {
    		case R.id.action_search:
    			// search action
    			return true;
    		case R.id.location_found:
    			// location found
    			LocationFound();
    			return true;
    		case R.id.action_refresh:
    			refreshMenuItem = item;
    			// load the data from server
    			new SyncData().execute();
    			return true;
    		}
    		return super.onOptionsItemSelected(item);
        }
         
        switch (item.getItemId()) {
        case R.id.action_settings:
            return true;
        default:
            return super.onOptionsItemSelected(item);
        }
    }
    
    private void LocationFound() {
		Intent i = new Intent(MainActivity.this, LocationFound.class);
		startActivity(i);
	}

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
		viewPager.setCurrentItem(tab.getPosition());
	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub

	}
 
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
        menu.findItem(R.id.action_settings).setVisible(!drawerOpen);
        return super.onPrepareOptionsMenu(menu);
    }
 
    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getActionBar().setTitle(mTitle);
    }
 
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }
 
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }
    
    private class SyncData extends AsyncTask<String, Void, String> {
		@Override
		protected void onPreExecute() {
			// set the progress bar view
			refreshMenuItem.setActionView(R.layout.action_progressbar);

			refreshMenuItem.expandActionView();
		}

		@Override
		protected String doInBackground(String... params) {
			// not making real request in this demo
			// for now we use a timer to wait for sometime
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			refreshMenuItem.collapseActionView();
			// remove the progress bar view
			refreshMenuItem.setActionView(null);
		}
    };
}