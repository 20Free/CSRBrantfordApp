package com.csrbrantford.csrbrantfordapp.campInfo.faqInfo;

/**
 * Created by Jonathan on 4/22/2017.
 */

class FAQChildItem {

    private String title;
    private String details;

    FAQChildItem(String[] faqChildStrings) {
        this.title = faqChildStrings[0];
        this.details = faqChildStrings[1];
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
