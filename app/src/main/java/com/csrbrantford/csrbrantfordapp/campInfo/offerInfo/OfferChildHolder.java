package com.csrbrantford.csrbrantfordapp.campInfo.offerInfo;

import android.support.v4.app.Fragment;
import android.content.Context;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorListener;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.TextView;

import com.bignerdranch.expandablerecyclerview.ChildViewHolder;
import com.csrbrantford.csrbrantfordapp.R;
import com.csrbrantford.csrbrantfordapp.campInfo.PopupWindowCompat;

import jp.wasabeef.recyclerview.animators.holder.AnimateViewHolder;

/**
 * Created by Jonathan on 4/22/2017.
 */

class OfferChildHolder extends ChildViewHolder  implements AnimateViewHolder {
    private TextView title;
    private String mTitle;
    private String mDetails;
    public static int pos;

    /**
     * Default constructor.
     *
     * @param itemView The {@link View} being hosted in this ViewHolder
     */
    public OfferChildHolder(View itemView) {
        super(itemView);
        title = (TextView)itemView.findViewById(R.id.offer_list_item);
    }

    public void bind(OfferChildItem offerChildItem) {
        title.setText(offerChildItem.getTitle());
        mTitle = offerChildItem.getTitle();
        mDetails = offerChildItem.getDetails();
    }

    public TextView getTitle() {
        return title;
    }

    public void setTitle(TextView title) {
            this.title = title;
    }

    @Override
    public void preAnimateRemoveImpl(RecyclerView.ViewHolder holder) {

    }

    @Override
    public void animateRemoveImpl(RecyclerView.ViewHolder holder, ViewPropertyAnimatorListener listener) {
        ViewCompat.animate(itemView)
                .translationY(-itemView.getHeight() * 0.3f)
                .alpha(0)
                .setDuration(300)
                .setListener(listener)
                .start();
    }

    @Override
    public void preAnimateAddImpl(RecyclerView.ViewHolder holder) {
        ViewCompat.setTranslationY(itemView, -itemView.getHeight() * 0.3f);
        ViewCompat.setAlpha(itemView, 0);
    }

    @Override
    public void animateAddImpl(RecyclerView.ViewHolder holder, ViewPropertyAnimatorListener listener) {
        ViewCompat.animate(itemView)
                .translationY(0)
                .alpha(0.8f)
                .setDuration(300)
                .setListener(listener)
                .setStartDelay(0)
                .start();

    }
}
