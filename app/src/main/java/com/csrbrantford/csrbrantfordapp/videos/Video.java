package com.csrbrantford.csrbrantfordapp.videos;

import android.graphics.drawable.BitmapDrawable;

/**
 *  Created by Jonathan on 7/7/2016.
 */

class Video {

    private String title;
    private String description;
    private BitmapDrawable playVideo;
    private String url;

    Video(String title, String description, BitmapDrawable playVideo, String url){
        this.title = title;
        this.description = description;
        this.playVideo = playVideo;
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    String getDescription() {
        return description;
    }

    BitmapDrawable getPlayVideo() {
        return playVideo;
    }

    String getUrl(){
        return url;
    }
}
