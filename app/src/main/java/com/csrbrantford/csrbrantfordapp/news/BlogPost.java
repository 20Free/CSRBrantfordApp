package com.csrbrantford.csrbrantfordapp.news;

import android.graphics.drawable.BitmapDrawable;

/**
 * Created by Jonathan on 12/21/2016.
 */

class BlogPost {
    private BitmapDrawable newsImage;
    private String newsTitle;
    private String newsDate;
    private String newsDetails;

    BlogPost(BitmapDrawable newsImage, String newsTitle, String newsDate, String newsDetails) {
        this.newsImage = newsImage;
        this.newsTitle = newsTitle;
        this.newsDate = newsDate;
        this.newsDetails = newsDetails;
    }

    BitmapDrawable getNewsImage() {
        return newsImage;
    }

    String getNewsTitle() {
        return newsTitle;
    }

    String getNewsDate() {
        return newsDate;
    }

    String getNewsDetails() {
        return newsDetails;
    }
}
