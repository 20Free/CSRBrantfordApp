package com.csrbrantford.csrbrantfordapp.campInfo.faqInfo;

import com.bignerdranch.expandablerecyclerview.model.Parent;

import java.util.List;

/**
 * Created by Jonathan on 4/22/2017.
 */

class FAQGroupItem implements Parent<FAQChildItem>{

    private String title;
    private List<FAQChildItem> items;

    FAQGroupItem(String title, List<FAQChildItem> items) {
        this.title = title;
        this.items = items;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public List<FAQChildItem> getChildList() {
        return items;
    }

    @Override
    public boolean isInitiallyExpanded() {
        return false;
    }
}
