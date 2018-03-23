package com.csrbrantford.csrbrantfordapp.aboutCSR;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.ListViewCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.csrbrantford.csrbrantfordapp.R;

import java.util.ArrayList;
import java.util.List;

/**
 *  Created by Jonathan on 6/29/2016.
 */
public class MissionFragment extends Fragment{

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.mission_tab_fragment, container, false);
        List<AboutCSRObject> aboutMissionObjects = new ArrayList<>();

        String[] missionList = getActivity().getResources().getStringArray(R.array.ranch_bio);

        for(String mission : missionList) {
            String[] missionArray = mission.split("\\|");
            AboutCSRObject aboutMissionObject = new AboutCSRObject(missionArray[0], missionArray[3], missionArray[1], missionArray[2]);
            aboutMissionObjects.add(aboutMissionObject);
        }
        ListViewCompat missionListView = (ListViewCompat) view.findViewById(R.id.mission_list_view);
        AboutCSRListAdapter missionListAdapter = new AboutCSRListAdapter(getActivity(), aboutMissionObjects);
        missionListView.setAdapter(missionListAdapter);

        return view;
    }
}