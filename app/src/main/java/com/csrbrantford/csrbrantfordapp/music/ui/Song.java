package com.csrbrantford.csrbrantfordapp.music.ui;

import android.graphics.drawable.BitmapDrawable;

/**
 * Created by Jonathan on 7/8/2016.
 */
public class Song {

    String title;
    String length;
    BitmapDrawable playSong;

    public Song(String title, String length, BitmapDrawable playSong) {
        this.title = title;
        this.length = length;
        this.playSong = playSong;
    }

    public String getTitle() {
        return title;
    }

    public String getLength() {
        return length;
    }

    public BitmapDrawable getPlaySong() {
        return playSong;
    }
}
