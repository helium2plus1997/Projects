package com.a2a.app.a2aapp;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;


/**
 * Created by Mounika on 3/11/2016.
 */
public class TabsPagerAdapter extends FragmentPagerAdapter {

    public TabsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int index) {

        switch (index) {
            case 0:
                // Top Rated fragment activity
                return new ContactUsFragment();
            case 1:
                // Games fragment activity
                return new ConditionsFragment();
            case 2:
                // Movies fragment activity
                return new OthersFragment();
        }
        return null;
    }

    @Override
    public int getCount() {
        return 3;
    }
}
