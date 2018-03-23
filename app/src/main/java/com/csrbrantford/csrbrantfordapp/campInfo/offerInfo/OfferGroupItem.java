package com.csrbrantford.csrbrantfordapp.campInfo.offerInfo;

import android.widget.AdapterView;

import com.bignerdranch.expandablerecyclerview.model.Parent;

import java.util.List;

/**
 * Created by Jonathan on 4/22/2017.
 */

class OfferGroupItem implements Parent<OfferChildItem>{
    private String title;
    private List<OfferChildItem> items;

    OfferGroupItem(String title, List<OfferChildItem> items) {
        this.title = title;
        this.items = items;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public List<OfferChildItem> getChildList() {
        return items;
    }

    @Override
    public boolean isInitiallyExpanded() {
        return false;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
