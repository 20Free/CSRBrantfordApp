package com.csrbrantford.csrbrantfordapp.campInfo.faqInfo;

import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorListener;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.bignerdranch.expandablerecyclerview.ChildViewHolder;
import com.csrbrantford.csrbrantfordapp.R;

import jp.wasabeef.recyclerview.animators.holder.AnimateViewHolder;

/**
 * Created by Jonathan on 4/22/2017.
 */

class FAQChildHolder extends ChildViewHolder implements AnimateViewHolder{

    private TextView title;

    FAQChildHolder(View itemView) {
        super(itemView);
        title = (TextView)itemView.findViewById(R.id.faq_list_item);
    }

    void bind(FAQChildItem faqChildItem) {
        title.setText(faqChildItem.getTitle());
    }

    public TextView getTitle() {
        return title;
    }

    public void setTitle(TextView title) {
        this.title = title;
    }

    @Override
    public void preAnimateAddImpl(RecyclerView.ViewHolder holder) {
        ViewCompat.setTranslationY(itemView, -itemView.getHeight() * 0.3f);
        ViewCompat.setAlpha(itemView, 0);
    }

    @Override
    public void preAnimateRemoveImpl(RecyclerView.ViewHolder holder) {

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

    @Override
    public void animateRemoveImpl(RecyclerView.ViewHolder holder, ViewPropertyAnimatorListener listener) {
        ViewCompat.animate(itemView)
                .translationY(-itemView.getHeight() * 0.3f)
                .alpha(0)
                .setDuration(300)
                .setListener(listener)
                .start();
    }
}
