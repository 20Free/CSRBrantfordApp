package com.csrbrantford.csrbrantfordapp.tipsNThemeMeals;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;

/**
 * Created by Jonathan on 7/4/2016.
 */
public class TipsNThemeMealsPagerAdapter extends FragmentStatePagerAdapter {

    int mNumTabs;
    TipsFragment tipsFragment;
    ThemeMealsFragment themeMealsFragment;

    public TipsNThemeMealsPagerAdapter(FragmentManager fragmentManager, int numTabs){
        super(fragmentManager);
        mNumTabs = numTabs;
        tipsFragment = new TipsFragment();
        themeMealsFragment = new ThemeMealsFragment();
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return tipsFragment;
            case 1:
                return themeMealsFragment;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumTabs;
    }
}
