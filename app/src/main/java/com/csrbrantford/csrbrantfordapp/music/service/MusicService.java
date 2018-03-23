package com.csrbrantford.csrbrantfordapp.music.service;


import android.app.Notification;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v4.media.MediaBrowserCompat;
import android.support.v4.media.MediaBrowserServiceCompat;
import android.support.v4.media.MediaDescriptionCompat;
import android.support.v4.media.MediaMetadataCompat;
import android.support.v4.media.session.MediaButtonReceiver;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.util.Log;

import com.csrbrantford.csrbrantfordapp.music.service.contentcatalogs.MusicLibrary;
import com.csrbrantford.csrbrantfordapp.music.service.notifications.MediaNotificationManager;
import com.csrbrantford.csrbrantfordapp.music.service.players.MediaPlayerAdapter;
import com.csrbrantford.csrbrantfordapp.music.ui.Music;

import java.util.ArrayList;
import java.util.List;

public class MusicService extends MediaBrowserServiceCompat {

    private static final String TAG = MusicService.class.getSimpleName();

    private MediaSessionCompat mSession;
    private MediaPlayerAdapter mPlayback;
    private MediaNotificationManager mMediaNotificationManager;
    private MediaSessionCallback mCallback;
    private boolean mServiceInStartedState;

    @Override
    public void onCreate() {
        super.onCreate();

        // Create a new MediaSession.
        mSession = new MediaSessionCompat(this, "Music Service");
        mCallback = new MediaSessionCallback();

        mSession.setCallback(mCallback);
        mSession.setFlags(
                MediaSessionCompat.FLAG_HANDLES_MEDIA_BUTTONS |
                MediaSessionCompat.FLAG_HANDLES_QUEUE_COMMANDS |
                MediaSessionCompat.FLAG_HANDLES_TRANSPORT_CONTROLS);
        setSessionToken(mSession.getSessionToken());

        mMediaNotificationManager = new MediaNotificationManager(this);

        mPlayback = new MediaPlayerAdapter(this, new MediaPlayerListener());
        Log.d(TAG, "onCreate: MusicService creating MediaSession, and MediaNotificationManager");
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        super.onTaskRemoved(rootIntent);
        stopSelf();
    }

    @Override
    public void onDestroy() {
        mMediaNotificationManager.onDestroy();
        mPlayback.stop();
        mSession.release();
        Log.d(TAG, "onDestroy: MediaPlayerAdapter stopped, and MediaSession released");
    }

    @Override
    public BrowserRoot onGetRoot(@NonNull String clientPackageName,
                                 int clientUid,
                                 Bundle rootHints) {

        return new BrowserRoot(MusicLibrary.getRoot(), null);
    }

    @Override
    public void onLoadChildren(
            @NonNull String parentId,
            @NonNull Result<List<MediaBrowserCompat.MediaItem>> result) {
        result.sendResult(MusicLibrary.getMediaItems());
    }

    // MediaSessionCallback: Transport Controls -> MediaPlayerAdapter
    public class MediaSessionCallback extends MediaSessionCompat.Callback {
        private final List<MediaSessionCompat.QueueItem> mPlaylist = new ArrayList<>();
        private int mQueueIndex = -1;
        private MediaMetadataCompat mPreparedMedia;

        @Override
        public void onAddQueueItem(MediaDescriptionCompat description) {
            mPlaylist.add(new MediaSessionCompat.QueueItem(description, description.hashCode()));
            mQueueIndex = (mQueueIndex == -1) ? 0 : mQueueIndex;
        }

        @Override
        public void onRemoveQueueItem(MediaDescriptionCompat description) {
            mPlaylist.remove(new MediaSessionCompat.QueueItem(description, description.hashCode()));
            mQueueIndex = (mPlaylist.isEmpty()) ? -1 : mQueueIndex;
        }

        @Override
        public void onPrepare() {
            if (mQueueIndex < 0 && mPlaylist.isEmpty()) {
                // Nothing to play.
                return;
            }

            final String mediaId = mPlaylist.get(mQueueIndex).getDescription().getMediaId();
            mPreparedMedia = MusicLibrary.getMetadata(MusicService.this, mediaId);
            mSession.setMetadata(mPreparedMedia);

            Log.d(TAG, "index: " + mQueueIndex);
            if (!mSession.isActive()) {
                mSession.setActive(true);
            }
        }

        @Override
        public void onPlay() {
            if (!isReadyToPlay()) {
                // Nothing to play.
                return;
            }

            if (mPreparedMedia == null) {
                onPrepare();
            }
            final String mediaId = mPlaylist.get(mQueueIndex).getDescription().getMediaId();
            mPreparedMedia = MusicLibrary.getMetadata(MusicService.this, mediaId);
            mSession.setMetadata(mPreparedMedia);
            mPlayback.playFromMedia(mPreparedMedia);
            Log.d(TAG, "onPlayFromMediaId: MediaSession active");
        }

        @Override
        public void onPause() {
            mPlayback.pause();
        }

        @Override
        public void onStop() {
            mPlayback.stop();
            mSession.setActive(false);
        }

        @Override
        public void onSkipToNext() {
            mQueueIndex = (++mQueueIndex % mPlaylist.size());
            int seek = 0;
            for(int i = 0; i < mQueueIndex; i++) {
                seek += Music.getTrackTimes()[i];
            }
            onSeekTo(seek);
        }

        @Override
        public void onSkipToPrevious() {
            mQueueIndex = (--mQueueIndex % mPlaylist.size());
            int seek = 0;
            for(int i = 0; i < mQueueIndex; i++) {
                seek += Music.getTrackTimes()[i];
            }
            onSeekTo(seek);
        }

        @Override
        public void onSeekTo(long pos) {
            mPlayback.seekTo(pos);
            int[] trackTimes = Music.getTrackTimes();
            int currentTrackTime = 0;
            for(int i = 0; i < trackTimes.length; i++) {
                if (pos > currentTrackTime && pos < currentTrackTime + trackTimes[i]) {
                    mQueueIndex = i;
                }
                currentTrackTime += trackTimes[i];
            }

            onPause();
            onPlay();

        }

        private boolean isReadyToPlay() {
            return (!mPlaylist.isEmpty());
        }
    }

    // Media Player Adapter Callback: MediaPlayerAdapter state -> MusicService.
    public class MediaPlayerListener extends PlaybackInfoListener {

        private final ServiceManager mServiceManager;

        MediaPlayerListener() {
            mServiceManager = new ServiceManager();
        }

        @Override
        public void onPlaybackStateChange(PlaybackStateCompat state) {
            // Report the state to the MediaSession.
            mSession.setPlaybackState(state);

            // Manage the stated state of this service.
            switch (state.getState()) {
                case PlaybackStateCompat.STATE_PLAYING:
                    mServiceManager.moveServiceToStartedState(state);
                    break;
                case PlaybackStateCompat.STATE_PAUSED:
                    mServiceManager.updateNotificationForPause(state);
                    break;
                case PlaybackStateCompat.STATE_STOPPED:
                    mServiceManager.moveServiceOutOfStartedState(state);
                    break;
                case PlaybackStateCompat.STATE_SKIPPING_TO_NEXT:
                    mServiceManager.updateNotificationForNextSong(state);
                    break;
                case PlaybackStateCompat.STATE_SKIPPING_TO_PREVIOUS:
                    mServiceManager.updateNotificationForNextSong(state);
                    break;
                case PlaybackStateCompat.STATE_SKIPPING_TO_QUEUE_ITEM:
                    mServiceManager.updateNotificationForNextSong(state);
                    break;
            }
        }

        class ServiceManager {

            private void moveServiceToStartedState(PlaybackStateCompat state) {
                Notification notification =
                        mMediaNotificationManager.getNotification(
                                mPlayback.getCurrentMedia(), state, getSessionToken());

                if (!mServiceInStartedState) {
                    ContextCompat.startForegroundService(
                            MusicService.this,
                            new Intent(MusicService.this, MusicService.class));
                    mServiceInStartedState = true;
                }

                startForeground(MediaNotificationManager.NOITIFICATION_ID, notification);
            }

            private void updateNotificationForPause(PlaybackStateCompat state) {
                stopForeground(false);
                Notification notification =
                        mMediaNotificationManager.getNotification(
                                mPlayback.getCurrentMedia(), state, getSessionToken());

                mMediaNotificationManager.getNotificationManager()
                        .notify(MediaNotificationManager.NOITIFICATION_ID, notification);
            }

            private void updateNotificationForNextSong(PlaybackStateCompat state) {
                stopForeground(false);
                Notification notification =
                        mMediaNotificationManager.getNotification(
                                mPlayback.getCurrentMedia(), state, getSessionToken());

                mMediaNotificationManager.getNotificationManager()
                        .notify(MediaNotificationManager.NOITIFICATION_ID, notification);
            }

            private void moveServiceOutOfStartedState(PlaybackStateCompat state) {
                stopForeground(true);
                stopSelf();
                mServiceInStartedState = false;
            }
        }
    }
}
