package com.csrbrantford.csrbrantfordapp.aboutCSR;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.ListViewCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.csrbrantford.csrbrantfordapp.R;

import java.util.ArrayList;
import java.util.List;

/**
 *  Created by Jonathan on 4/26/2016.
 */
public class StaffFragment extends Fragment{

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.staff_tab_fragment, container, false);
        List<AboutCSRObject> aboutCSRStaffList = new ArrayList<>();

        String[] staffList = getActivity().getResources().getStringArray(R.array.staff_bios);

        for(String staff: staffList) {
            String[] staffArray = staff.split("\\|");
            for(String s : staffArray) {
                Log.d("STAFF LIST", s);
            }
            //AboutCSRObject staffCSRObject = new AboutCSRObject(staffArray[0], staffArray[3], staffArray[1], staffArray[2]);
            AboutCSRObject staffCSRObject = new AboutCSRObject(staffArray[0], staffArray[3], staffArray[1], staffArray[2]);
            aboutCSRStaffList.add(staffCSRObject);
        }
        ListViewCompat aboutCSRListView = (ListViewCompat) view.findViewById(R.id.staff_list_view);
        AboutCSRListAdapter aboutCSRListAdapter = new AboutCSRListAdapter(getActivity(), aboutCSRStaffList);
        aboutCSRListView.setAdapter(aboutCSRListAdapter);

        return view;
    }
}