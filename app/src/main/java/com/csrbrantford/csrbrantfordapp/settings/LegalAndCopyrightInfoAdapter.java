package com.csrbrantford.csrbrantfordapp.settings;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.csrbrantford.csrbrantfordapp.R;

import java.util.ArrayList;

/**
 * Created by 20Free on 6/6/2017.
 */

public class LegalAndCopyrightInfoAdapter extends BaseAdapter {

    private ArrayList mData;
    private LayoutInflater inflater;

    public LegalAndCopyrightInfoAdapter(Activity activity, ArrayList data) {
        mData = data;

        inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        if(mData.size() <= 0) {
            return 1;
        }
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LegalItem legalItem;
        View row = convertView;
        LegalItemsHolder holder;

        if(row == null){
            row = inflater.inflate(R.layout.legal_and_copyright_info_row_layout, parent, false);

            holder = new LegalItemsHolder();
            holder.legalItemTitle = (TextView)row.findViewById(R.id.legal_item_title);
            holder.legalItemDescription = (TextView)row.findViewById(R.id.legal_item_desc);

            row.setTag(holder);
        } else {
            holder = (LegalItemsHolder)row.getTag();
        }

        if(mData.size() <= 0){
            holder.legalItemTitle.setText("");
        } else {
            legalItem = (LegalItem)mData.get(position);

            holder.legalItemTitle.setText(legalItem.getTitle());
            holder.legalItemDescription.setText(legalItem.getDescription());
        }

        return row;
    }

    private static class LegalItemsHolder {
        TextView legalItemTitle;
        TextView legalItemDescription;
    }
}
