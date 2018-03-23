package com.csrbrantford.csrbrantfordapp.campInfo.discountsInfo;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;

import com.csrbrantford.csrbrantfordapp.R;

import java.util.ArrayList;
import java.util.List;

import jp.wasabeef.recyclerview.animators.LandingAnimator;

/**
 *  Created by Jonathan on 4/25/2016.
 */
public class DiscountsFragment extends Fragment {


    private ArrayList<DiscountsGroupItem> discountListDataHeader;
    private List<DiscountsChildItem> summer2018;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.discounts_tab_fragment, container, false);

        summer2018 = new ArrayList<>();

        Fragment fragment = this;

        prepareListData();

        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        RecyclerView discountsExpandableListView = (RecyclerView) view.findViewById(R.id.discount_expandable_list_view);

        DiscountsAdapter discountsAdapter = new DiscountsAdapter(discountsExpandableListView, getContext(), fragment, discountListDataHeader);
        discountsExpandableListView.setLayoutManager(llm);
        LandingAnimator landingAnimator = new LandingAnimator(new OvershootInterpolator());
        landingAnimator.setAddDuration(300);
        landingAnimator.setRemoveDuration(300);
        discountsExpandableListView.setItemAnimator(landingAnimator);
        discountsExpandableListView.setAdapter(discountsAdapter);

        return view;
    }

    /**
     * Preparing the list data
     */
    private void prepareListData() {
        discountListDataHeader = new ArrayList<>();

        String[] data = getResources().getStringArray(R.array.discounts_variables);

        for(String data2: data) {
            String[] data1 = data2.split("\\|");
            if(data1[0].equals(getString(R.string.summer_2018))) {
                summer2018.add(new DiscountsChildItem(new String[] {data1[1], data1[2]}));
            }
        }

        discountListDataHeader.add(new DiscountsGroupItem(getString(R.string.summer_2018), summer2018));
    }
}