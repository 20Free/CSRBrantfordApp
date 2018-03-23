package com.csrbrantford.csrbrantfordapp.campInfo.faqInfo;

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
public class FAQFragment extends Fragment {


    private ArrayList<FAQGroupItem> faqListDataHeader;
    private List<FAQChildItem> registrationAndCamperFees;
    private List<FAQChildItem> lifeAtCamp;
    private List<FAQChildItem> ourCaringStaff;
    private List<FAQChildItem> whatShouldMyCamperBringToCamp;
    private List<FAQChildItem> theHealthAndSafetyOfMyCamper;
    private List<FAQChildItem> comingAndGoingToCamp;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.faq_tab_fragment, container, false);

        registrationAndCamperFees = new ArrayList<>();
        lifeAtCamp = new ArrayList<>();
        ourCaringStaff = new ArrayList<>();
        whatShouldMyCamperBringToCamp = new ArrayList<>();
        theHealthAndSafetyOfMyCamper = new ArrayList<>();
        comingAndGoingToCamp = new ArrayList<>();

        Fragment fragment = this;

        prepareListData();

        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        RecyclerView faqExpandableListView = (RecyclerView) view.findViewById(R.id.faq_expandable_list_view);

        FAQAdapter faqAdapter = new FAQAdapter(faqExpandableListView, getContext(), fragment, faqListDataHeader);
        faqExpandableListView.setLayoutManager(llm);
        LandingAnimator landingAnimator = new LandingAnimator(new OvershootInterpolator());
        landingAnimator.setAddDuration(300);
        landingAnimator.setRemoveDuration(300);
        faqExpandableListView.setItemAnimator(landingAnimator);
        faqExpandableListView.setAdapter(faqAdapter);

        return view;
    }

    /**
     * Preparing the list data
     */
    private void prepareListData() {
        faqListDataHeader = new ArrayList<>();

        String[] data = getResources().getStringArray(R.array.faqs_strings);

        for(String data2: data) {
            String[] data1 = data2.split("\\|");
            if(data1[0].equals(getString(R.string.registration_and_camper_fees))) {
                registrationAndCamperFees.add(new FAQChildItem(new String[] {data1[1], data1[2]}));
            } else if(data1[0].equals(getString(R.string.life_at_camp))) {
                lifeAtCamp.add(new FAQChildItem(new String[]{data1[1], data1[2]}));
            } else if(data1[0].equals(getString(R.string.our_caring_staff))) {
                ourCaringStaff.add(new FAQChildItem(new String[] {data1[1], data1[2]}));
            } else if(data1[0].equals(getString(R.string.what_should_my_camper_bring_to_camp))) {
                whatShouldMyCamperBringToCamp.add(new FAQChildItem(new String[] {data1[1], data1[2]}));
            } else if(data1[0].equals(getString(R.string.the_health_and_safety_of_my_camper))) {
                theHealthAndSafetyOfMyCamper.add(new FAQChildItem(new String[] {data1[1], data1[2]}));
            } else if(data1[0].equals(getString(R.string.coming_and_going_to_camp))) {
                comingAndGoingToCamp.add(new FAQChildItem(new String[] {data1[1], data1[2]}));
            }
        }

        faqListDataHeader.add(new FAQGroupItem(getString(R.string.registration_and_camper_fees), registrationAndCamperFees));
        faqListDataHeader.add(new FAQGroupItem(getString(R.string.life_at_camp), lifeAtCamp));
        faqListDataHeader.add(new FAQGroupItem(getString(R.string.our_caring_staff), ourCaringStaff));
        faqListDataHeader.add(new FAQGroupItem(getString(R.string.what_should_my_camper_bring_to_camp), whatShouldMyCamperBringToCamp));
        faqListDataHeader.add(new FAQGroupItem(getString(R.string.the_health_and_safety_of_my_camper), theHealthAndSafetyOfMyCamper));
        faqListDataHeader.add(new FAQGroupItem(getString(R.string.coming_and_going_to_camp), comingAndGoingToCamp));
    }
}