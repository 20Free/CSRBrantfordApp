package com.csrbrantford.csrbrantfordapp.aboutCSR;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.csrbrantford.csrbrantfordapp.R;

import java.util.ArrayList;
import java.util.List;

/**
 *  Created by Jonathan on 12/28/2016.
 */

class AboutCSRListAdapter extends BaseAdapter {
    private Activity activity;
    private List data;
    private static LayoutInflater inflater = null;


    AboutCSRListAdapter(Activity activity, List data) {
        this.activity = activity;
        this.data = data;

        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        if(data.size() <= 0)
            return 1;
        return data.size();
    }

    @Override
    public Object getItem(int pos) {
        return pos;
    }

    @Override
    public long getItemId(int pos) {
        return pos;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        AboutCSRObject aboutCSRObject;
        View row = convertView;
        AboutCSRHolder holder;

        if(row == null) {
            row = inflater.inflate(R.layout.about_csr_row_layout, parent, false);

            holder = new AboutCSRHolder();
            holder.aboutCSRName = (TextView)row.findViewById(R.id.what_we_offer_name);
            holder.aboutCSRImage = (ImageView) row.findViewById(R.id.what_we_offer_photo);
            holder.aboutCSRDesc = (TextView) row.findViewById(R.id.what_we_offer_description);
            holder.aboutCSRFunFact = (TextView) row.findViewById(R.id.what_we_offer_fun_fact);

            row.setTag(holder);
        } else {
            holder = (AboutCSRListAdapter.AboutCSRHolder)row.getTag();
        }

        if(data.size() <= 0){
            holder.aboutCSRName .setText(activity.getString(R.string.no_videos));
        } else {
            aboutCSRObject = (AboutCSRObject) data.get(position);

            holder.aboutCSRName.setText(aboutCSRObject.getAboutCSRName());
            holder.aboutCSRImage.setImageResource(activity.getResources().getIdentifier(aboutCSRObject.getAboutCSRImageRef(), "drawable", activity.getPackageName()));
            holder.aboutCSRDesc.setText(aboutCSRObject.getAboutCSRDesc());
            holder.aboutCSRFunFact.setText(aboutCSRObject.getAboutCSRFunFact());
        }

        return row;
    }

    private static class AboutCSRHolder {
        TextView aboutCSRName;
        ImageView aboutCSRImage;
        TextView aboutCSRDesc;
        TextView aboutCSRFunFact;
    }
}