package com.csrbrantford.csrbrantfordapp.campInfo.offerInfo;

import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorListener;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bignerdranch.expandablerecyclerview.ParentViewHolder;
import com.csrbrantford.csrbrantfordapp.R;

import jp.wasabeef.recyclerview.animators.holder.AnimateViewHolder;

/**
 * Created by Jonathan on 4/22/2017.
 */

class OfferGroupHolder extends ParentViewHolder{

    private TextView title;

    public OfferGroupHolder(View itemView) {
        super(itemView);

        title = (TextView) itemView.findViewById(R.id.offer_list_title);
    }

    public TextView getTitle() {
        return title;
    }

    public void setTitle(TextView title) {
        this.title = title;
    }

    public void bind(OfferGroupItem offerGroupItem) {
        title.setText(offerGroupItem.getTitle());
    }


}
