package com.csrbrantford.csrbrantfordapp.settings;

/**
 * Created by 20Free on 6/6/2017.
 */

public class LegalItem {

    private String title;
    private String description;

    public LegalItem(String title, String desc) {
        this.title = title;
        description = desc;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }
}
