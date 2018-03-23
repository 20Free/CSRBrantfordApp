package com.csrbrantford.csrbrantfordapp.music.service;

import android.support.v4.media.session.PlaybackStateCompat;

/**
 * Created by FreeF on 2/3/2018.
 */

public abstract class PlaybackInfoListener {

    public abstract void onPlaybackStateChange(PlaybackStateCompat state);

    public void onPlaybackCompleted() {
    }
}
