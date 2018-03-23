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
 *  Created by Jonathan on 4/26/2016.
 */
public class HorsesFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.horses_tab_fragment, container, false);
        List<AboutCSRObject> aboutHorsesList = new ArrayList<>();

        String[] horseList = getActivity().getResources().getStringArray(R.array.horse_bios);

        for(String horse : horseList) {
            String[] horseArray = horse.split("\\|");
            AboutCSRObject horseCSRObject = new AboutCSRObject(horseArray[0], horseArray[3], horseArray[1], horseArray[2]);
            aboutHorsesList.add(horseCSRObject);
        }
        ListViewCompat horseListView = (ListViewCompat) view.findViewById(R.id.horse_list_view);
        AboutCSRListAdapter aboutCSRListAdapter = new AboutCSRListAdapter(getActivity(), aboutHorsesList);
        horseListView.setAdapter(aboutCSRListAdapter);

        return view;
    }
}