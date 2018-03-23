package com.csrbrantford.csrbrantfordapp.music.client;

import android.content.ComponentName;
import android.content.Context;
import android.media.MediaPlayer;
import android.media.browse.MediaBrowser;
import android.os.RemoteException;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.media.MediaBrowserCompat;
import android.support.v4.media.MediaMetadataCompat;
import android.support.v4.media.session.MediaControllerCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.util.Log;

import com.csrbrantford.csrbrantfordapp.music.service.MusicService;
import com.csrbrantford.csrbrantfordapp.music.service.players.MediaPlayerAdapter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Adapater for a MediaBrowser that handles connecting, disconnecting,
 * and basic browsing.
 */

public class MediaBrowserAdapter{

    private static final String TAG = MediaBrowserAdapter.class.getSimpleName();

    /**
     * Helper class for easily subscribing to changes in a MediaBrowserService connection.
     */
    public static abstract class MediaBrowserChangeListener {

        public void onConnected(@Nullable MediaControllerCompat mediaController) {

        }

        public void onMetadataChanged(@Nullable MediaMetadataCompat mediaMetadata) {

        }

        public void onPlaybackStateChanged(@Nullable PlaybackStateCompat playbackState) {
        }
    }

    private final InternalState mState;

    private final Context mContext;
    private final List<MediaBrowserChangeListener> mListeners = new ArrayList<>();

    private final MediaBrowserConnectionCallback mMediaBrowserConnectionCallback =
            new MediaBrowserConnectionCallback();
    private final MediaControllerCallback mMediaControllerCallback =
            new MediaControllerCallback();
    private final MediaBrowserSubscriptionCallback mMediaBrowserSubscriptionCallback =
            new MediaBrowserSubscriptionCallback();

    private MediaBrowserCompat mMediaBrowser;

    @Nullable
    private MediaControllerCompat mMediaController;

    public MediaBrowserAdapter(Context context) {
        mContext = context;
        mState = new InternalState();
    }

    public void onStart() {
        if(mMediaBrowser == null) {
            mMediaBrowser =
                    new MediaBrowserCompat(
                            mContext,
                            new ComponentName(mContext, MusicService.class),
                            mMediaBrowserConnectionCallback,
                            null);
            mMediaBrowser.connect();
        }
        Log.d(TAG, "onStart: Creating MediaBrowser, and connecting");
    }

    public void onStop() {
        if (mMediaController != null) {
            mMediaController.unregisterCallback(mMediaControllerCallback);
            mMediaController = null;
        }
        if(mMediaBrowser != null && mMediaBrowser.isConnected()) {
            mMediaBrowser.disconnect();
            mMediaBrowser = null;
        }
        resetState();
        Log.d(TAG, "onStop: Releasing MediaController, Disconnecting from MediaBrowser");
    }

    /**
     * THis internal state of the app needs to revert to what it looks like when it started before
     * any connections to the MusicService happens via the MediaSessionCompat
     */
    private void resetState() {
        mState.reset();
        performOnAllListeners(new ListenerCommand() {
            @Override
            public void perform(@NonNull MediaBrowserChangeListener listener) {
                listener.onPlaybackStateChanged(null);
            }
        });
        Log.d(TAG, "resetState: ");
    }

    public MediaControllerCompat.TransportControls getTransportControls() {
        if (mMediaController == null) {
            Log.d(TAG, "getTransportControls: MediaController is null!");
            throw new IllegalStateException();
        }
        return mMediaController.getTransportControls();
    }

    public void addListener(MediaBrowserChangeListener listener) {
        if(listener != null) {
            mListeners.add(listener);
        }
    }

    public MediaBrowserCompat getmMediaBrowser() {
        return mMediaBrowser;
    }

    private void removeListener(MediaBrowserChangeListener listener) {
        if(listener != null) {
            mListeners.remove(listener);
        }
    }

    private void performOnAllListeners(@NonNull ListenerCommand command) {
        for (MediaBrowserChangeListener listener : mListeners) {
            if (listener != null) {
                try {
                    command.perform(listener);
                } catch (Exception e) {
                    removeListener(listener);
                }
            }
        }
    }

    public interface ListenerCommand {
        void perform(@NonNull MediaBrowserChangeListener listener);
    }

    // Receives callbacks from the MediaBrowser when it has successfully connected to the
    // MediaBrowserService (MusicService).
    public class MediaBrowserConnectionCallback extends MediaBrowserCompat.ConnectionCallback {

        // Happens as a result of onStart().

        @Override
        public void onConnected() {
            try {
                // Get a MediaController for the MediaSession.
                mMediaController = new MediaControllerCompat(mContext,
                                                             mMediaBrowser.getSessionToken());
                mMediaController.registerCallback(mMediaControllerCallback);

                // Sync existing MediaSession state to the UI.
                mMediaControllerCallback.onMetadataChanged(
                        mMediaController.getMetadata());
                mMediaControllerCallback
                        .onPlaybackStateChanged(mMediaController.getPlaybackState());

                performOnAllListeners(new ListenerCommand() {
                    @Override
                    public void perform(@NonNull MediaBrowserChangeListener listener) {
                        listener.onConnected(mMediaController);
                    }
                });
            } catch (RemoteException e) {
                Log.d(TAG, String.format("onConnected: Problem: %s", e.toString()));
                throw new RuntimeException(e);
            }

            mMediaBrowser.subscribe(mMediaBrowser.getRoot(), mMediaBrowserSubscriptionCallback);
        }
    }

    // Receives callbacks from the MediaBrowser when the MediaBrowserService has loaded new media
    // that is ready for playback.
    public class MediaBrowserSubscriptionCallback extends MediaBrowserCompat.SubscriptionCallback {
        @Override
        public void onChildrenLoaded(@NonNull String parentId,
                                     @NonNull List<MediaBrowserCompat.MediaItem> children) {
            assert mMediaController != null;

            // Queue up all media items for this simple sample.
            for (final MediaBrowserCompat.MediaItem mediaItem : children) {
                mMediaController.addQueueItem(mediaItem.getDescription());
            }

            // Call "playFromMedia" so the UI is updated.
            mMediaController.getTransportControls().prepare();
        }
    }

    // Receives callbacks from the MediaController and updates the UI state,
    // i.e.: Which is the current item, whether it's playing or paused, etc.
    public class MediaControllerCallback extends MediaControllerCompat.Callback {
        private MediaMetadataCompat mMetadata;

        @Override
        public void onMetadataChanged(MediaMetadataCompat metadata) {
            // Filtering out needless updates, given that the metadata has not changed.
            if (isMediaIdSame(metadata, mState.getMediaMetadata())) {
                Log.d(TAG, "onMetadataChanged: Filtering out needless onMetadataChanged() update");
                return;
            } else {
                mState.setMediaMetadata(metadata);
            }

            mMetadata = metadata;

            performOnAllListeners(new ListenerCommand() {
                @Override
                public void perform(@NonNull MediaBrowserChangeListener listener) {
                    listener.onMetadataChanged(mMetadata);
                }
            });


        }

        @Override
        public void onPlaybackStateChanged(final PlaybackStateCompat state) {
            mState.setPlaybackState(state);
            performOnAllListeners(new ListenerCommand() {
                @Override
                public void perform(@NonNull MediaBrowserChangeListener listener) {
                    listener.onPlaybackStateChanged(state);
                }
            });
        }

        // This might happen if the MusicService is killed while the Activity is in the
        // foreground and onStart() has been called (but not onStop()).
        @Override
        public void onSessionDestroyed() {
            resetState();
            onPlaybackStateChanged(null);
            Log.d(TAG, "onSessionDestroyed: MusicService is dead!!!");
        }

        private boolean isMediaIdSame(MediaMetadataCompat newMedia,
                                      MediaMetadataCompat currentMedia) {
            if (currentMedia == null || newMedia == null) {
                return false;
            }
            Log.d(TAG, "isMediaIdSame: " +  newMedia.getString(MediaMetadataCompat.METADATA_KEY_MEDIA_ID)+ "/" + currentMedia.getString(MediaMetadataCompat.METADATA_KEY_MEDIA_ID));
            String newMediaId =
                    newMedia.getString(MediaMetadataCompat.METADATA_KEY_MEDIA_ID);
            String currentMediaId =
                    currentMedia.getString(MediaMetadataCompat.METADATA_KEY_MEDIA_ID);
            return newMediaId.equals(currentMediaId);
        }

    }

    // A holder class that contains the internal state.
    public class InternalState {

        private PlaybackStateCompat playbackState;
        private MediaMetadataCompat mediaMetadata;

        private void reset() {
            playbackState = null;
            mediaMetadata = null;
        }

        public PlaybackStateCompat getPlaybackState() {
            return playbackState;
        }

        private void setPlaybackState(PlaybackStateCompat playbackState) {
            this.playbackState = playbackState;
        }

        private MediaMetadataCompat getMediaMetadata() {
            return mediaMetadata;
        }

        private void setMediaMetadata(MediaMetadataCompat mediaMetadata) {
            this.mediaMetadata = mediaMetadata;
        }
    }
}
