package com.csrbrantford.csrbrantfordapp.photoAlbums;

import android.graphics.drawable.BitmapDrawable;

/**
 * Created by Jonathan on 10/25/2016.
 */

class PhotoAlbum {
    private String title;
    private String description;
    private BitmapDrawable viewAlbum;
    private String id;

    PhotoAlbum(String title, String description, BitmapDrawable viewAlbum, String id){
        this.title = title;
        this.description = description;
        this.viewAlbum = viewAlbum;
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    String getDescription() {
        return description;
    }

    BitmapDrawable getViewAlbum() {
        return viewAlbum;
    }

    public String getId() {
        return id;
    }
}
