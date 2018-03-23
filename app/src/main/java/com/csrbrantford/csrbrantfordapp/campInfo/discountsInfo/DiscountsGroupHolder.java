package com.csrbrantford.csrbrantfordapp.campInfo.discountsInfo;

import android.view.View;
import android.widget.TextView;

import com.bignerdranch.expandablerecyclerview.ParentViewHolder;
import com.csrbrantford.csrbrantfordapp.R;

/**
 * Created by Jonathan on 4/22/2017.
 */

class DiscountsGroupHolder extends ParentViewHolder{

    private TextView title;

    DiscountsGroupHolder(View itemView) {
        super(itemView);

        title = (TextView) itemView.findViewById(R.id.discount_list_title);
    }

    public TextView getTitle() {
        return title;
    }

    public void setTitle(TextView title) {
        this.title = title;
    }

    void bind(DiscountsGroupItem discountsGroupItem) {
        title.setText(discountsGroupItem.getTitle());
    }
}
