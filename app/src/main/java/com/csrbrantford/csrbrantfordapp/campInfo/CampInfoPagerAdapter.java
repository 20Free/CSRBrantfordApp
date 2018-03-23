package com.csrbrantford.csrbrantfordapp.campInfo;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.csrbrantford.csrbrantfordapp.campInfo.discountsInfo.DiscountsFragment;
import com.csrbrantford.csrbrantfordapp.campInfo.faqInfo.FAQFragment;
import com.csrbrantford.csrbrantfordapp.campInfo.offerInfo.OfferFragment;

/**
 * Created by Jonathan on 4/25/2016.
 */
public class CampInfoPagerAdapter extends FragmentStatePagerAdapter {

    private int mNumTabs;
    private OfferFragment offerFragment;
    private FAQFragment faqFragment;
    private DiscountsFragment discountsFragment;

    public CampInfoPagerAdapter(FragmentManager fragmentManager, int numTabs){
        super(fragmentManager);
        mNumTabs = numTabs;

        offerFragment = new OfferFragment();
        faqFragment = new FAQFragment();
        discountsFragment = new DiscountsFragment();
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return offerFragment;
            case 1:
                return faqFragment;
            case 2:
                return discountsFragment;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumTabs;
    }
}