package com.csrbrantford.csrbrantfordapp.campInfo.offerInfo;

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
public class OfferFragment extends Fragment {


    private ArrayList<OfferGroupItem> offerListDataHeader;
    private List<OfferChildItem> discovery8to16;
    private List<OfferChildItem> teensAndLeadership13to16;
    private List<OfferChildItem> juniorCamp6to9;
    private List<OfferChildItem> dayCamp5to13;
    private List<OfferChildItem> boysCamp8to15;
    private List<OfferChildItem> girlsCamp6to15;
    private List<OfferChildItem> horseSpecialtyCamp8to16;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.offer_tab_fragment, container, false);


        discovery8to16 = new ArrayList<>();
        teensAndLeadership13to16 = new ArrayList<>();
        juniorCamp6to9 = new ArrayList<>();
        dayCamp5to13 = new ArrayList<>();
        boysCamp8to15 = new ArrayList<>();
        girlsCamp6to15 = new ArrayList<>();
        horseSpecialtyCamp8to16 = new ArrayList<>();

        Fragment fragment = this;

        prepareListData();


        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        RecyclerView offerExpandableListView = (RecyclerView) view.findViewById(R.id.offer_expandable_list_view);

        OfferAdapter offerAdapter = new OfferAdapter(offerExpandableListView, getContext(),fragment, offerListDataHeader);
        offerExpandableListView.setLayoutManager(llm);
        LandingAnimator landingAnimator = new LandingAnimator(new OvershootInterpolator());
        landingAnimator.setAddDuration(300);
        landingAnimator.setRemoveDuration(300);
        offerExpandableListView.setItemAnimator(landingAnimator);
        offerExpandableListView.setAdapter(offerAdapter);

        return view;
    }

    /**
     * Preparing the list data
     */
    private void prepareListData() {
        offerListDataHeader = new ArrayList<>();

        String[] data = getResources().getStringArray(R.array.camp_info_strings);

        for(String data2: data) {
            String[] data1 = data2.split("\\|");
            if(data1[0].equals(getString(R.string.discovery_8_to_16))) {
                discovery8to16.add(new OfferChildItem(new String[] {data1[1], data1[2]}));
            } else if(data1[0].equals(getString(R.string.teens_and_leadership_13_to_16))) {
                teensAndLeadership13to16.add(new OfferChildItem(new String[] {data1[1], data1[2]}));
            } else if(data1[0].equals(getString(R.string.junior_camp_6_to_9))) {
                juniorCamp6to9.add(new OfferChildItem(new String[]{data1[1], data1[2]}));
            } else if(data1[0].equals(getString(R.string.day_camp_5_to_13))) {
                dayCamp5to13.add(new OfferChildItem(new String[] {data1[1], data1[2]}));
            } else if(data1[0].equals(getString(R.string.boys_camp_8_to_15))) {
                boysCamp8to15.add(new OfferChildItem(new String[] {data1[1], data1[2]}));
            } else if(data1[0].equals(getString(R.string.girls_camp_6_to_15))) {
                girlsCamp6to15.add(new OfferChildItem(new String[] {data1[1], data1[2]}));
            } else if(data1[0].equals(getString(R.string.horse_specialty_camp_8_to_16))) {
                horseSpecialtyCamp8to16.add(new OfferChildItem(new String[] {data1[1], data1[2]}));
            }
        }

        offerListDataHeader.add(new OfferGroupItem(getString(R.string.discovery_8_to_16), discovery8to16));
        offerListDataHeader.add(new OfferGroupItem(getString(R.string.teens_and_leadership_13_to_16), teensAndLeadership13to16));
        offerListDataHeader.add(new OfferGroupItem(getString(R.string.junior_camp_6_to_9), juniorCamp6to9));
        offerListDataHeader.add(new OfferGroupItem(getString(R.string.day_camp_5_to_13), dayCamp5to13));
        offerListDataHeader.add(new OfferGroupItem(getString(R.string.boys_camp_8_to_15), boysCamp8to15));
        offerListDataHeader.add(new OfferGroupItem(getString(R.string.girls_camp_6_to_15), girlsCamp6to15));
        offerListDataHeader.add(new OfferGroupItem(getString(R.string.horse_specialty_camp_8_to_16), horseSpecialtyCamp8to16));
    }
}