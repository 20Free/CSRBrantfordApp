package com.csrbrantford.csrbrantfordapp.campInfo.discountsInfo;

import com.bignerdranch.expandablerecyclerview.model.Parent;

import java.util.List;

/**
 * Created by Jonathan on 4/22/2017.
 */

class DiscountsGroupItem implements Parent<DiscountsChildItem>{

    private String title;
    private List<DiscountsChildItem> items;

    DiscountsGroupItem(String title, List<DiscountsChildItem> items) {
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
    public List<DiscountsChildItem> getChildList() {
        return items;
    }

    @Override
    public boolean isInitiallyExpanded() {
        return false;
    }
}
