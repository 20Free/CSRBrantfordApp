package com.csrbrantford.csrbrantfordapp.music.service.contentcatalogs;

import android.content.ContentResolver;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.BuildConfig;
import android.support.v4.media.MediaBrowserCompat;
import android.support.v4.media.MediaDescriptionCompat;
import android.support.v4.media.MediaMetadataCompat;

import com.csrbrantford.csrbrantfordapp.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;
import java.util.concurrent.TimeUnit;


public class MusicLibrary {

    private static final TreeMap<String, MediaMetadataCompat> music = new TreeMap<>();
    private static final HashMap<String, Integer> albumRes = new HashMap<>();
    private static final HashMap<String, String> musicFileName = new HashMap<>();

    private static final String MUSIC_FILE_NAME = "camp_music.mp3";
    private static final String ARTIST = "Circle Square Ranch";
    private static final String ALBUM_ART_RES_NAME = "icon_music";

    static {
        createMediaMetadataCompat(
                "Song_0",
                "Circle Square Ranch Song",
                "CSR song",
                189,
                TimeUnit.SECONDS,
                0,
                R.drawable.icon_music);
        createMediaMetadataCompat(
                "Song_1",
                "Banana Song",
                "Banana",
                148,
                TimeUnit.SECONDS,
                1,
                R.drawable.icon_music);
        createMediaMetadataCompat(
                "Song_2",
                "My Redeemer Lives",
                "Redeemer",
                177,
                TimeUnit.SECONDS,
                2,
                R.drawable.icon_music);
        createMediaMetadataCompat(
                "Song_3",
                "Peace Like A River",
                "Peace",
                163,
                TimeUnit.SECONDS,
                3,
                R.drawable.icon_music);
        createMediaMetadataCompat(
                "Song_4",
                "Pharaoh Pharaoh",
                "Pharaoh",
                163,
                TimeUnit.SECONDS,
                4,
                R.drawable.icon_music);
        createMediaMetadataCompat(
                "Song_5",
                "Strong Tower",
                "Tower",
                145,
                TimeUnit.SECONDS,
                5,
                R.drawable.icon_music);
        createMediaMetadataCompat(
                "Song_6",
                "This Little Light Of Mine",
                "Light",
                138,
                TimeUnit.SECONDS,
                6,
                R.drawable.icon_music);
    }

    public static String getRoot() {
        return "root";
    }

    private static String getAlbumArtUri(String albumArtResName) {
        return ContentResolver.SCHEME_ANDROID_RESOURCE + "://" +
                BuildConfig.APPLICATION_ID + "/drawable/" + albumArtResName;
    }

    public static String getMusicFilename(String mediaId) {
        return musicFileName.containsKey(mediaId) ? musicFileName.get(mediaId) : null;
    }

    private static int getAlbumRes(String mediaId) {
        return albumRes.containsKey(mediaId) ? albumRes.get(mediaId) : 0;
    }

    private static Bitmap getAlbumBitmap(Context context, String mediaId) {
        return BitmapFactory.decodeResource(context.getResources(),
                MusicLibrary.getAlbumRes(mediaId));
    }

    public static List<MediaBrowserCompat.MediaItem> getMediaItems() {
        List<MediaBrowserCompat.MediaItem> result = new ArrayList<>();
        for(MediaMetadataCompat metadata : music.values()) {
            result.add(
                    new MediaBrowserCompat.MediaItem(
                            metadata.getDescription(), MediaBrowserCompat.MediaItem.FLAG_PLAYABLE));
        }
        return result;
    }

    public static MediaMetadataCompat getMetadata(Context context, String mediaId) {
        MediaMetadataCompat metadataWithoutBitmap = music.get(mediaId);
        Bitmap albumArt = getAlbumBitmap(context, mediaId);

        // Since MediaMetadataCompat is immutable, we need to create a copy to set the album art.
        // We don't set it initially on all items so that they don't take unnecessary memory.
        MediaMetadataCompat.Builder builder = new MediaMetadataCompat.Builder();
        for (String key :
                new String[] {
                        MediaMetadataCompat.METADATA_KEY_MEDIA_ID,
                        MediaMetadataCompat.METADATA_KEY_ALBUM,
                        MediaMetadataCompat.METADATA_KEY_DISPLAY_DESCRIPTION,
                        MediaMetadataCompat.METADATA_KEY_ARTIST,
                        MediaMetadataCompat.METADATA_KEY_GENRE,
                        MediaMetadataCompat.METADATA_KEY_TITLE
                }) {
            builder.putString(key, metadataWithoutBitmap.getString(key));
        }
        builder.putLong(
                MediaMetadataCompat.METADATA_KEY_TRACK_NUMBER,
                metadataWithoutBitmap.getLong(MediaMetadataCompat.METADATA_KEY_TRACK_NUMBER));
        builder.putLong(
                MediaMetadataCompat.METADATA_KEY_DURATION,
                metadataWithoutBitmap.getLong(MediaMetadataCompat.METADATA_KEY_DURATION));
        builder.putBitmap(MediaMetadataCompat.METADATA_KEY_ALBUM_ART, albumArt);
        return builder.build();
    }

    private static void createMediaMetadataCompat(
            String mediaId,
            String title,
            String description,
            long duration,
            TimeUnit durationUnit,
            long trackNumber,
            int albumArtResId) {
        music.put(mediaId,
                new MediaMetadataCompat.Builder()
                        .putString(MediaMetadataCompat.METADATA_KEY_MEDIA_ID, mediaId)
                        .putString(MediaMetadataCompat.METADATA_KEY_ARTIST, ARTIST)
                        .putLong(MediaMetadataCompat.METADATA_KEY_DURATION,
                                    TimeUnit.MILLISECONDS.convert(duration, durationUnit))
                        .putString(
                                MediaMetadataCompat.METADATA_KEY_ALBUM_ART_URI,
                                getAlbumArtUri(ALBUM_ART_RES_NAME))
                        .putString(
                                MediaMetadataCompat.METADATA_KEY_DISPLAY_ICON_URI,
                                getAlbumArtUri(ALBUM_ART_RES_NAME))
                        .putString(MediaMetadataCompat.METADATA_KEY_TITLE, title)
                        .putString(MediaMetadataCompat.METADATA_KEY_DISPLAY_DESCRIPTION, description)
                        .putLong(MediaMetadataCompat.METADATA_KEY_TRACK_NUMBER, trackNumber)
                        .build());
        albumRes.put(mediaId, albumArtResId);
        musicFileName.put(mediaId, MUSIC_FILE_NAME);
    }

}
