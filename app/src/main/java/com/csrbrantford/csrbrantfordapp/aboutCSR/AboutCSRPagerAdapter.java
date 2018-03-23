package com.csrbrantford.csrbrantfordapp.aboutCSR;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 *  Created by Jonathan on 4/25/2016.
 */
class AboutCSRPagerAdapter extends FragmentStatePagerAdapter {

    private int mNumTabs;
    private MissionFragment missionFragment;
    private StaffFragment staffFragment;
    private HorsesFragment horsesFragment;

    AboutCSRPagerAdapter(FragmentManager fragmentManager, int numTabs){
        super(fragmentManager);
        mNumTabs = numTabs;
        missionFragment = new MissionFragment();
        staffFragment = new StaffFragment();
        horsesFragment = new HorsesFragment();
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return missionFragment;
            case 1:
                return staffFragment;
            case 2:
                return horsesFragment;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumTabs;
    }
}