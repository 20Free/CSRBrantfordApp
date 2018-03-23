package com.csrbrantford.csrbrantfordapp.campInfo.offerInfo;

/**
 * Created by Jonathan on 4/22/2017.
 */

class OfferChildItem {
    private String title;
    private String details;

    OfferChildItem(String[] offerChildItemStrings) {
        this.title = offerChildItemStrings[0];
        this.details = offerChildItemStrings[1];
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
