package com.csrbrantford.csrbrantfordapp.tipsNThemeMeals;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.csrbrantford.csrbrantfordapp.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Jonathan on 7/4/2016.
 */
public class TipsFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tips_tab_fragment, container, false);

        String tipsString = "";
        TextView listOfTipsView = (TextView)view.findViewById(R.id.tipsTextView);
        List<String> tips = new ArrayList<>();
        tips.addAll(Arrays.asList(getResources().getStringArray(R.array.list_of_tips)));
        String[] listOfTips = new String[tips.size()];
        for(int i = 1; i < tips.size(); i++) {
            listOfTips[i-1] = "Tip " + i + ": " + tips.get(i-1) + "\n\n";
            Log.i("TIP " + i, listOfTips[i-1]);
            tipsString += listOfTips[i-1];
        }
        tipsString += "Tip " + tips.size() + ": " + tips.get(tips.size()-1);

        listOfTipsView.setText(tipsString);
        return view;
    }

}
