package com.csrbrantford.csrbrantfordapp.campInfo.discountsInfo;

/**
 * Created by Jonathan on 4/22/2017.
 */

class DiscountsChildItem {

    private String title;
    private String details;

    DiscountsChildItem(String[] discountsChildStrings) {
        this.title = discountsChildStrings[0];
        this.details = discountsChildStrings[1];
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }
}
