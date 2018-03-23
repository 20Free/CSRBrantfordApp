package com.csrbrantford.csrbrantfordapp.campInfo.faqInfo;

import android.view.View;
import android.widget.TextView;

import com.bignerdranch.expandablerecyclerview.ParentViewHolder;
import com.csrbrantford.csrbrantfordapp.R;

/**
 * Created by Jonathan on 4/22/2017.
 */

class FAQGroupHolder extends ParentViewHolder{

    private TextView title;

    FAQGroupHolder(View itemView) {
        super(itemView);

        title = (TextView) itemView.findViewById(R.id.faq_list_title);
    }

    public TextView getTitle() {
        return title;
    }

    public void setTitle(TextView title) {
        this.title = title;
    }

    void bind(FAQGroupItem faqGroupItem) {
        title.setText(faqGroupItem.getTitle());
    }
}
