package com.a2a.app.a2aapp;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Created by Mounika on 3/31/2016.
 */
public class RetailorSlidingmenu extends Activity {

    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;

    // nav drawer title
    private CharSequence mDrawerTitle;

    // used to store app title
    private CharSequence mTitle;

    // slide menu items
    private String[] retnavMenuTitles;
    private TypedArray retnavMenuIcons;

    private ArrayList<NavDrawerItem> navDrawerItems;
    private NavDrawerListAdapter adapter;

    String retid;
    String retusrname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sliding_retailor);
        //Fetching Retailor details from local file
        try {
            FileInputStream file = openFileInput("retailordet.txt");
            InputStreamReader reader = new InputStreamReader(file);
            BufferedReader buffer = new BufferedReader(reader);
            retid = buffer.readLine();
            retusrname = buffer.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }

        mTitle = mDrawerTitle = getTitle();
        // load slide menu items
        retnavMenuTitles = getResources().getStringArray(R.array.ret_nav_drawer_items);

        // nav drawer icons from resources
        retnavMenuIcons = getResources()
                .obtainTypedArray(R.array.ret_nav_drawer_icons);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.retailorlist_slidermenu);

        navDrawerItems = new ArrayList<NavDrawerItem>();

        // adding nav drawer items to array
        // Home
        navDrawerItems.add(new NavDrawerItem(retnavMenuTitles[0], retnavMenuIcons.getResourceId(0, -1)));
        // fruits
        navDrawerItems.add(new NavDrawerItem(retnavMenuTitles[1], retnavMenuIcons.getResourceId(1, -1)));
        // Vegetables
        navDrawerItems.add(new NavDrawerItem(retnavMenuTitles[2], retnavMenuIcons.getResourceId(2, -1)));
        // Cosmetics
        navDrawerItems.add(new NavDrawerItem(retnavMenuTitles[3], retnavMenuIcons.getResourceId(3, -1)));
        // Beverages
        navDrawerItems.add(new NavDrawerItem(retnavMenuTitles[4], retnavMenuIcons.getResourceId(4, -1)));
        // Groceries
        navDrawerItems.add(new NavDrawerItem(retnavMenuTitles[5], retnavMenuIcons.getResourceId(5, -1)));
        // Stationeries
        navDrawerItems.add(new NavDrawerItem(retnavMenuTitles[6], retnavMenuIcons.getResourceId(6, -1)));
        // Snacks
        navDrawerItems.add(new NavDrawerItem(retnavMenuTitles[7], retnavMenuIcons.getResourceId(7, -1)));
        // BrandedFoods
        navDrawerItems.add(new NavDrawerItem(retnavMenuTitles[8], retnavMenuIcons.getResourceId(8, -1)));

        // Recycle the typed array
        retnavMenuIcons.recycle();
        mDrawerList.setOnItemClickListener(new SlideMenuClickListener());
        // setting the nav drawer list adapter
        adapter = new NavDrawerListAdapter(getApplicationContext(),
                navDrawerItems);
        mDrawerList.setAdapter(adapter);
        // enabling action bar app icon and behaving it as toggle button
        getActionBar().setDisplayShowHomeEnabled(true);
        getActionBar().setHomeButtonEnabled(true);
        getActionBar().setDisplayHomeAsUpEnabled(true);

        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                R.drawable.ic_drawer, //nav menu toggle icon
                R.string.app_name, // nav drawer open - description for accessibility
                R.string.app_name // nav drawer close - description for accessibility
        ){
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
        if (savedInstanceState == null) {
            // on first time display view for first nav item
            displayView(0);
        }
    }

    /**
     * Slide menu item click listener
     * */

    private class SlideMenuClickListener implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,
                                long id) {
            // display view for selected nav drawer item
            displayView(position);
        }
    }

        @Override
        public boolean onCreateOptionsMenu(Menu menu) {
            getMenuInflater().inflate(R.menu.main_ret, menu);
            return true;
        }

        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            // toggle nav drawer on selecting action bar app icon/title
            if (mDrawerToggle.onOptionsItemSelected(item)) {
                return true;
            }
            // Handle action bar actions click
            switch (item.getItemId()) {
                case R.id.action_settings:
                    return true;
                case R.id.action_notifications:
                    Intent i=new Intent(this,MyNotifications.class);
                    startActivity(i);
                    return true;
                default:
                    return super.onOptionsItemSelected(item);
            }
        }

        /***
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
         * Diplaying fragment view for selected nav drawer list item
         * */
        private void displayView(int position) {
            // update the main content by replacing fragments
            Fragment fragment = null;
            switch (position) {
                case 0:
                    fragment = new HomeRetFragment();
                    break;
                case 1:
                    fragment = new FruitsFragment();
                    break;
                case 2:
                    fragment = new VegetablesFragment();
                    break;
                case 3:
                    fragment = new CosmeticsFragment();
                    break;
                case 4:
                    fragment = new BeveragesFragment();
                    break;
                case 5:
                    fragment = new GroceriesFragment();
                    break;
                case 6:
                    fragment = new StationeriesFragment();
                    break;
                case 7:
                    fragment = new SnacksFragment();
                    break;
                case 8:
                    fragment = new BrandedfoodFragment();
                    break;

                default:
                    break;
            }
            if (fragment != null) {
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.frame_container, fragment).commit();

            // update selected item and title, then close the drawer
            mDrawerList.setItemChecked(position, true);
            mDrawerList.setSelection(position);
            setTitle(retnavMenuTitles[position]);
            mDrawerLayout.closeDrawer(mDrawerList);
        } else {
            // error in creating fragment
            Log.e("MainActivity", "Error in creating fragment");
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
        // Pass any configuration change to the drawer toggls
        mDrawerToggle.onConfigurationChanged(newConfig);
    }
}
