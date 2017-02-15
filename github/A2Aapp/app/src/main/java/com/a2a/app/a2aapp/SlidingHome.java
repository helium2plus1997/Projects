package com.a2a.app.a2aapp;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by Mounika on 3/9/2016.
 */
public class SlidingHome extends Activity {

    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;
    private String URL="http://a2a.co.in/webservice2/fetch_retailers.php?retailerid=xyz123";//to add php file for fetching specific retailer.
    //nav Drawer title
    private CharSequence mDrawerTitle;

    //used to store app title
    private CharSequence mTitle;

    //slidingmenu items
    private String[] navMenuTitles;
    private TypedArray navMenuicons;

    private ArrayList<NavDrawerItem> navDrawerItems;
    private NavDrawerListAdapter adapter;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_ACTION_BAR);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slidinghome);

        mTitle = mDrawerTitle = getTitle();
        ServiceHandler sh= new ServiceHandler();
        //load slide  menu items
        navMenuTitles = getResources().getStringArray(R.array.nav_drawer_items);

        //nav drawer icons from resources
        navMenuicons = getResources().obtainTypedArray(R.array.nav_drawer_icons);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.list_slidermenu);
       // navigationView = (NavigationView) findViewById(R.id.navigation_view);
      //  navigationView.setNavigationItemSelectedListener();


        navDrawerItems = new ArrayList<NavDrawerItem>();

        //adding nav drawer items to array

        //Home
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[0], navMenuicons.getResourceId(0, -1)));
        //MyProfile
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[1], navMenuicons.getResourceId(1, -1)));
        //Call Operator
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[2], navMenuicons.getResourceId(2, -1)));
        //Help
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[3], navMenuicons.getResourceId(3, -1)));

       // navDrawerItems.add(new NavDrawerItem(navMenuTitles[4], navMenuicons.getResourceId(4, -1)));
        //settings
        // navDrawerItems.add(new NavDrawerItem(navMenuTitles[5], navMenuicons.getResourceId(5, -1)));

        //recycle the typed array
        navMenuicons.recycle();
        mDrawerList.setOnItemClickListener(new SlideMenuClickListener());

        //setting the nav Drawer list adapter
        adapter = new NavDrawerListAdapter(getApplicationContext(), navDrawerItems);
        mDrawerList.setAdapter(adapter);

        //enabling action bar app icon and behaving it as a toggle button
       // assert getSupportActionBar() != null;
        ActionBar bar = getActionBar();
      //  bar.setBackgroundDrawable(new ColorDrawable(Color.RED));

            getActionBar().setDisplayShowHomeEnabled(true);
            getActionBar().setHomeButtonEnabled(true);
            getActionBar().setDisplayHomeAsUpEnabled(true);

        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                R.drawable.ic_drawer,
                R.string.app_name,
                R.string.app_name
        ) {
            public void onDrawerClosed(View view) {
                getActionBar().setTitle(mTitle);
                // calling onPrepareOptionsMenu() to show action bar icons
                invalidateOptionsMenu();
            }

            public void onDrawerOpened(View drawerView) {
                getActionBar().setTitle(mDrawerTitle);
                // calling onPrepareOptionsMenu() to hide action bar icons
                invalidateOptionsMenu();
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        if(savedInstanceState == null){
            // on first time display view for first nav item
            displayView(0);
        }

    }

    /**
     * Slide menu item click listener
     * */
    private class SlideMenuClickListener implements AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            // display view for selected nav drawer item
            displayView(position);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // toggle nav drawer on selecting action bar app icon/title
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        // Handle action bar actions click
        switch (item.getItemId()){
            case R.id.action_logout:
                Intent i = new Intent(getBaseContext(),HomePage.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
                return true;
            case R.id.action_settings:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /* *
         * Called when invalidateOptionsMenu() is triggered
         */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // if nav drawer is opened, hide the action items
        boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
        menu.findItem(R.id.action_settings).setVisible(!drawerOpen);
        return super.onPrepareOptionsMenu(menu);
    }
    /**
     * Displaying fragment view for selected nav drawer list item
     * */
    private void displayView(int position) {
        // update the main content by replacing fragments
        Fragment fragment = null;
        switch(position) {
            case 0:
                fragment = new HomeFragment();
                break;
            case 1:
                fragment = new MyProfileFragment();
                break;
            case 2:
                fragment = new CallOperatorFragment();
                break;
            case 3:
                fragment = new HelpFragment();
                break;
            case 4:
                fragment = new SettingsFragment();
                break;
            default:
                break;
        }

        if(fragment !=null) {
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.frame_container,fragment).commit();

            // update selected item and title, then close the drawer

            mDrawerList.setItemChecked(position,true);
            mDrawerList.setSelection(position);
            setTitle(navMenuTitles[position]);
            mDrawerLayout.closeDrawer(mDrawerList);

        }else {
            //error in creating fragment
            Log.e("PropertyListings", "Error in Creating Fragment");
        }

    }

    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getActionBar().setTitle(mTitle);
    }

    /**
     * When using the ActionBarDrawerToggle, you must call it during
     * onPostCreate() and onConfigurationChanged()...
     */
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggle
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

}
