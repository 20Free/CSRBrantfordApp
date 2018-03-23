package com.csrbrantford.csrbrantfordapp.music.ui;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.media.MediaBrowserCompat;
import android.support.v4.media.MediaDescriptionCompat;
import android.support.v4.media.MediaMetadataCompat;
import android.support.v4.media.session.MediaButtonReceiver;
import android.support.v4.media.session.MediaControllerCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.csrbrantford.csrbrantfordapp.R;
import com.csrbrantford.csrbrantfordapp.buttonCanvases.PauseButtonDrawer;
import com.csrbrantford.csrbrantfordapp.buttonCanvases.PlayButtonDrawer;
import com.csrbrantford.csrbrantfordapp.buttonCanvases.StopButtonDrawer;
import com.csrbrantford.csrbrantfordapp.home.Home;
import com.csrbrantford.csrbrantfordapp.music.client.MediaBrowserAdapter;
import com.csrbrantford.csrbrantfordapp.music.service.MusicService;
import com.csrbrantford.csrbrantfordapp.music.service.notifications.MediaNotificationManager;
import com.csrbrantford.csrbrantfordapp.music.service.players.MediaPlayerAdapter;
import com.csrbrantford.csrbrantfordapp.settings.Settings;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Need most likely fix up this code
 */
public class Music extends AppCompatActivity {


    private TextView mTitleTextView;

    private ArrayList<Song> songArrayList = new ArrayList<>();

    private ImageButton playButton;
    private ImageButton pauseButton;
    private ImageButton stopButton;

    private int startTime;
    private int songEndTime;

    private MediaSeekbar seekBar;
    private static Handler handler;
    private TextView endTimeText;
    private TextView startTimeText;
    private static int[] trackTimes;
    private String[] songLengths;
    private int currentTrack;
    private int firstTimePlayed;

    public static boolean LIST_ITEM_SELECTED;

    private MediaBrowserAdapter mMediaBrowserAdapter;

    private boolean mIsPlaying;

    /**
     * Project strings stored in res/values/strings.xml
     *
     * Title bar details stored in res/layout/music_activity.xml
     * Layout details stored in res/layout/music_content.xml
     *
     * Initializing all of the variables and drawing all of the play, pause and stop buttons.
     *
     * To add a song to the list of songs to play, just add it's title and time to the respective
     * lists of strings.
     *
     * Currently these lists are songNames(list of song names) and songLengths(String representation
     * of the length of a song in mm:ss).
     *
     * @param savedInstanceState holds the previous instance state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.music_activity);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        LIST_ITEM_SELECTED = false;

        initializeUI();

        mMediaBrowserAdapter = new MediaBrowserAdapter(this);
        mMediaBrowserAdapter.addListener(new MediaBrowserListener());

/*
        if(savedInstanceState != null) {
            int playerPos = savedInstanceState.getInt(MEDIA_PLAYER_POS);
            mediaPlayer.seekTo(playerPos);
            mediaPlayer.start();
            handler.post(UpdateSongTime);
        }



        if (PREFERENCES_SAVED) {
            mediaPlayer.seekTo(mediaPlayer.getCurrentPosition());
            handler.postDelayed(UpdateSongTime, 100);
            if(!PAUSED) {
                playButton.setAlpha(0.5f);
                playButton.setEnabled(false);
                pauseButton.setAlpha(1.0f);
                pauseButton.setEnabled(true);
                stopButton.setAlpha(1.0f);
                stopButton.setEnabled(true);
            } else {
                playButton.setAlpha(1.0f);
                playButton.setEnabled(true);
                pauseButton.setAlpha(0.5f);
                pauseButton.setEnabled(false);
                stopButton.setAlpha(0.5f);
                stopButton.setEnabled(false);
            }
            PREFERENCES_SAVED = false;
        } else {
            mediaPlayer = MediaPlayer.create(this, R.raw.camp_music);
        }

        if(Settings.SHUT_MUSIC_OFF) {
            playButton.setAlpha(1.0f);
            playButton.setEnabled(true);
            pauseButton.setAlpha(0.5f);
            pauseButton.setEnabled(false);
            stopButton.setAlpha(0.5f);
            stopButton.setEnabled(false);
            Settings.SHUT_MUSIC_OFF = false;
        }



        String[] songNames;
        String[] songLengths;
        String endTime = "-" + convertSeekLength(songEndTime);

            //displaying song info on listView
            songArrayList = new ArrayList<>();
            songNames = new String[]{"Circle Square Ranch Song", "Banana Song", "My Redeemer Lives", "Peace Like A River", "Pharaoh Pharaoh", "Strong Tower", "This Little Light Of Mine"};

            //setting up music player and seek bar to play
            songLengths = new String[]{"3:09", "2:28", "2:57", "2:43", "2:43", "2:25", "2:18"};




            startTimeText = (TextView) findViewById(R.id.startTimeText);
            endTimeText = (TextView) findViewById(R.id.endTimeText);
            songDisplay = (TextView) findViewById(R.id.trackDisplay);
            songEndTime = 0;
            startTime = 0;


            endTimeText.setText(endTime);
            startTimeText.setText(convertSeekLength(startTime));
            handler = new Handler();
            currentTrack = 0;
            firstTimePlayed = 0;
            seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {
                    if (firstTimePlayed > 0) {
                        mediaPlayer.seekTo(seekBar.getProgress());
                    } else {
                        mediaPlayer.seekTo((int) ((seekBar.getProgress() / 100.0f) * songEndTime));
                    }
                    handler.post(UpdateSongTime);
                }
            });




        trackTimes = new int[songLengths.length];
        int totalTrackTime = 0;

        int playWidth = (int)getResources().getDimension(R.dimen.csr_logo_bottompadding);
        int playHeight = (int)getResources().getDimension(R.dimen.csr_logo_bottompadding);

        for(int i = 0; i < songNames.length; i++) {
            Bitmap playButtonBitmap2 = Bitmap.createBitmap(playWidth, playHeight ,Bitmap.Config.ARGB_8888);
            PlayButtonDrawer playButtonDrawer2 = new PlayButtonDrawer();
            Canvas playButtonCanvas2 = playButtonDrawer2.drawPlayButton(playWidth, playHeight, playButtonBitmap2);
            playButtonCanvas2.drawBitmap(playButtonBitmap2,0,0,null);

            Song song = new Song(songNames[i], songLengths[i], new BitmapDrawable(getResources(), playButtonBitmap2));
            trackTimes[i] = convertToMilliseconds(songLengths[i]);
            totalTrackTime += trackTimes[i];
            songArrayList.add(song);
        }


        songEndTime = totalTrackTime;
        endTime = "-" + convertSeekLength(songEndTime);
        endTimeText.setText(endTime);

        music = this;
        Resources res = music.getResources();
        ListView musicListView = findViewById(R.id.listView);
        MusicAdapter adapter = new MusicAdapter(music, songArrayList, res);
        musicListView.setAdapter(adapter);

*/
    }

    private void initializeUI() {
        mTitleTextView = findViewById(R.id.trackDisplay);
        playButton = findViewById(R.id.playButton);
        pauseButton = findViewById(R.id.pauseButton);
        stopButton = findViewById(R.id.stopButton);

        pauseButton.setAlpha(0.5f);
        pauseButton.setEnabled(false);
        stopButton.setAlpha(0.5f);
        stopButton.setEnabled(false);

        seekBar = findViewById(R.id.seekBar);
        seekBar.setTitleView(mTitleTextView);

        // Show the Up button in the action bar.
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        // drawing the buttons on the screen
        int totalWidth = playButton.getLayoutParams().width;
        int totalHeight = playButton.getLayoutParams().height;

        Bitmap playButtonBitmap1 = Bitmap.createBitmap(totalWidth, totalHeight ,Bitmap.Config.ARGB_8888);
        PlayButtonDrawer playButtonDrawer1 = new PlayButtonDrawer();
        Canvas playButtonCanvas1 = playButtonDrawer1.drawPlayButton(totalWidth, totalHeight, playButtonBitmap1);
        playButtonCanvas1.drawBitmap(playButtonBitmap1,0,0,null);
        playButton.setBackground(new BitmapDrawable(getResources(), playButtonBitmap1));

        Bitmap pauseButtonBitmap = Bitmap.createBitmap(totalWidth, totalHeight ,Bitmap.Config.ARGB_8888);
        PauseButtonDrawer pauseButtonDrawer = new PauseButtonDrawer(totalWidth, totalHeight, pauseButtonBitmap);
        Canvas pauseButtonCanvas = pauseButtonDrawer.drawPauseButton(totalWidth, totalHeight, pauseButtonBitmap);
        pauseButtonCanvas.drawBitmap(pauseButtonBitmap,0,0,null);
        pauseButton.setBackground(new BitmapDrawable(getResources(), pauseButtonBitmap));

        Bitmap stopButtonBitmap = Bitmap.createBitmap(totalWidth, totalHeight ,Bitmap.Config.ARGB_8888);
        StopButtonDrawer stopButtonDrawer = new StopButtonDrawer(totalWidth, totalHeight, stopButtonBitmap);
        Canvas stopButtonCanvas = stopButtonDrawer.drawStopButton(totalWidth, totalHeight, stopButtonBitmap);
        stopButtonCanvas.drawBitmap(stopButtonBitmap,0,0,null);
        stopButton.setBackground(new BitmapDrawable(getResources(),stopButtonBitmap));

        //displaying song info on listView
        String[] songNames;

        songArrayList = new ArrayList<>();
        songNames = new String[]{"Circle Square Ranch Song", "Banana Song", "My Redeemer Lives", "Peace Like A River", "Pharaoh Pharaoh", "Strong Tower", "This Little Light Of Mine"};

        //setting up music player and seek bar to play
        songLengths = new String[]{"3:09", "2:28", "2:57", "2:43", "2:43", "2:25", "2:18"};

        startTimeText = (TextView) findViewById(R.id.startTimeText);
        endTimeText = (TextView) findViewById(R.id.endTimeText);
        songEndTime = 0;
        startTime = 0;

        String endTime = "-" + convertSeekLength(songEndTime);
        endTimeText.setText(endTime);
        startTimeText.setText(convertSeekLength(startTime));
        currentTrack = 0;
        handler = new Handler();
        firstTimePlayed = 0;

        trackTimes = new int[songLengths.length];
        int totalTrackTime = 0;

        int playWidth = (int)getResources().getDimension(R.dimen.csr_logo_bottompadding);
        int playHeight = (int)getResources().getDimension(R.dimen.csr_logo_bottompadding);

        for(int i = 0; i < songNames.length; i++) {
            Bitmap playButtonBitmap2 = Bitmap.createBitmap(playWidth, playHeight ,Bitmap.Config.ARGB_8888);
            PlayButtonDrawer playButtonDrawer2 = new PlayButtonDrawer();
            Canvas playButtonCanvas2 = playButtonDrawer2.drawPlayButton(playWidth, playHeight, playButtonBitmap2);
            playButtonCanvas2.drawBitmap(playButtonBitmap2,0,0,null);

            Song song = new Song(songNames[i], songLengths[i], new BitmapDrawable(getResources(), playButtonBitmap2));
            trackTimes[i] = convertToMilliseconds(songLengths[i]);
            totalTrackTime += trackTimes[i];
            songArrayList.add(song);
        }



        songEndTime = totalTrackTime;
        endTime = "-" + convertSeekLength(songEndTime);
        endTimeText.setText(endTime);



        Music music;
        music = this;
        Resources res = music.getResources();
        ListView musicListView = findViewById(R.id.listView);
        MusicAdapter adapter = new MusicAdapter(music, songArrayList, res);
        musicListView.setAdapter(adapter);
    }

    public static int[] getTrackTimes() {
        return trackTimes;
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("Music", "onStart: successful");
        mMediaBrowserAdapter.onStart();
        mTitleTextView.setText(R.string.default_track_title);
    }

    @Override
    protected void onStop() {
        super.onStop();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        seekBar.disconnectController();
        mMediaBrowserAdapter.onStop();
    }

    /**
     * Set custom animation when the back button is pressed.
     *
     */
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        seekBar.disconnectController();
        mMediaBrowserAdapter.onStop();
        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
    }

    /**
     * Goes back to the home screen and stops the music.
     *
     * @param item for the back arrow.
     * @return true, if the press was successful.
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Converts the current seek length in from milliseconds to a String representation.
     *
     * @param seekLength where the position of the seek bar is.
     * @return a String representation of where the seek bar is.
     */
    public String convertSeekLength(int seekLength){
        int minutes = (seekLength/60000)%60000;
        int seconds = seekLength %60000;
        seconds = seconds/1000;
        return String.format(Locale.CANADA, "%02d:%02d", minutes, seconds);
    }

    /**
     * Converts a String representation of a current time into an integer representation in milliseconds.
     *
     * @param time a String representation of the time in minutes.
     * @return an integer representation of that String in milliseconds
     */
    public int convertToMilliseconds(String time) {
        String splitString[] = time.split(":");
        int minutes = Integer.parseInt(splitString[0]);
        int seconds = Integer.parseInt(splitString[1]);
        int minutesToMillis = 60000 * minutes;
        int secondsToMillis = 1000 * seconds;
        return minutesToMillis + secondsToMillis;
    }

    /**
     * Handler for play button. Starts the mediaPlayer.
     *
     * @param view - the play button.
     */
    public void startPlayingSong(View view) {
        mMediaBrowserAdapter.getTransportControls().play();
        handler.post(UpdateSongTime);
        playButton.setAlpha(0.5f);
        playButton.setEnabled(false);
        pauseButton.setAlpha(1.0f);
        pauseButton.setEnabled(true);
        stopButton.setAlpha(1.0f);
        stopButton.setEnabled(true);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(mIsPlaying) {
            mMediaBrowserAdapter.getTransportControls().pause();
            playButton.setAlpha(1.0f);
            playButton.setEnabled(true);
            pauseButton.setAlpha(0.5f);
            pauseButton.setEnabled(false);
            stopButton.setAlpha(0.5f);
            stopButton.setEnabled(false);
        }
    }

    /**
     * Starts the music from the position of the play button clicked on the list.
     *
     * @param mPosition - the position of the play button on the list.
     */
    public void onItemClick(final int mPosition){
        if (mIsPlaying) {
            mMediaBrowserAdapter.getTransportControls().pause();
        }
        int seek = 0;
        for(int i = 0; i < mPosition; i++) {
            seek += trackTimes[i];
        }
        MediaButtonReceiver.buildMediaButtonPendingIntent(this,PlaybackStateCompat.ACTION_SKIP_TO_QUEUE_ITEM);
        mMediaBrowserAdapter.getTransportControls().play();
        mMediaBrowserAdapter.getTransportControls().seekTo(seek);


        if (firstTimePlayed == 0)
            handler.post(UpdateSongTime);

        playButton.setAlpha(0.5f);
        playButton.setEnabled(false);
        pauseButton.setAlpha(1.0f);
        pauseButton.setEnabled(true);
        stopButton.setAlpha(1.0f);
        stopButton.setEnabled(true);
    }



    /**
     * A handler for the pause button, pauses the music.
     *
     * @param view - the pause button.
     */
    public void pauseSong(View view) {
        mMediaBrowserAdapter.getTransportControls().pause();
        playButton.setAlpha(1.0f);
        playButton.setEnabled(true);
        pauseButton.setAlpha(0.5f);
        pauseButton.setEnabled(false);
        stopButton.setAlpha(0.5f);
        stopButton.setEnabled(false);
    }

    /**
     * A handler for the stop button, stops and resets the mediaPlayer.
     *
     * @param view - the stop button.
     */
    public void stopSong(View view) {
        mTitleTextView.setText(getString(R.string.default_track_title));
        mMediaBrowserAdapter.getTransportControls().seekTo(0);
        mMediaBrowserAdapter.getTransportControls().pause();


        playButton.setAlpha(1.0f);
        playButton.setEnabled(true);
        pauseButton.setAlpha(0.5f);
        pauseButton.setEnabled(false);
        stopButton.setAlpha(0.5f);
        stopButton.setEnabled(false);
    }

    private class MediaBrowserListener extends MediaBrowserAdapter.MediaBrowserChangeListener {

        @Override
        public void onConnected(@Nullable MediaControllerCompat mediaController) {
            super.onConnected(mediaController);
            seekBar.setMediaController(mediaController);
        }

        @Override
        public void onPlaybackStateChanged(@Nullable PlaybackStateCompat playbackState) {
            super.onPlaybackStateChanged(playbackState);
            mIsPlaying = playbackState != null &&
                    playbackState.getState() == PlaybackStateCompat.STATE_PLAYING;
            if(mIsPlaying) {
                playButton.setAlpha(0.5f);
                playButton.setEnabled(false);
                pauseButton.setAlpha(1.0f);
                pauseButton.setEnabled(true);
                stopButton.setAlpha(1.0f);
                stopButton.setEnabled(true);
            } else {
                playButton.setAlpha(1.0f);
                playButton.setEnabled(true);
                pauseButton.setAlpha(0.5f);
                pauseButton.setEnabled(false);
                stopButton.setAlpha(0.5f);
                stopButton.setEnabled(false);
            }
        }

        @Override
        public void onMetadataChanged(@Nullable MediaMetadataCompat mediaMetadata) {
            super.onMetadataChanged(mediaMetadata);
            if(mediaMetadata == null) {
                return;
            }



            mTitleTextView.setText(
                    mediaMetadata.getString(MediaMetadataCompat.METADATA_KEY_TITLE));

        }
    }

    /**
     * Updates the view to the position of the mediaPlayer.
     */
    public Runnable UpdateSongTime = new Runnable() {
        @Override
        public void run() {
            android.os.Process.setThreadPriority(android.os.Process.THREAD_PRIORITY_BACKGROUND);
            MediaPlayer mediaPlayer = MediaPlayerAdapter.getMediaPlayer();
            if(mediaPlayer == null) {
                mediaPlayer = new MediaPlayer();
            }

            if (firstTimePlayed > 0) {
                startTime = mediaPlayer.getCurrentPosition();
                int currentSongEndTime = songEndTime - startTime;
                startTimeText.setText(convertSeekLength(startTime));
                String endTime = "-" + convertSeekLength(currentSongEndTime);
                int currentTrackTime = 0;
                for (int i = 0; i < trackTimes.length; i++) {
                    if (startTime > currentTrackTime && startTime < currentTrackTime + trackTimes[i]) {
                        currentTrack = i;
                    }
                    currentTrackTime += trackTimes[i];
                }
                mTitleTextView.setText(songArrayList.get(currentTrack).getTitle());

                if (mediaPlayer.getCurrentPosition() == 0) {
                    mTitleTextView.setText(getString(R.string.default_track_title));
                }
                endTimeText.setText(endTime);
                handler.post(this);
            } else {
                currentTrack = 0;
                startTime = mediaPlayer.getCurrentPosition();
                seekBar.setMax(songEndTime);
                int currentSongEndTime = songEndTime - startTime;
                startTimeText.setText(convertSeekLength(startTime));
                String endTime = "-" + convertSeekLength(currentSongEndTime);
                mTitleTextView.setText(songArrayList.get(currentTrack).getTitle());
                if (mediaPlayer.getCurrentPosition() == 0) {
                    mTitleTextView.setText(getString(R.string.default_track_title));
                }
                endTimeText.setText(endTime);
                firstTimePlayed++;
                handler.post(this);
            }
        }
    };

    /**
     * Handler for when user wants to change background music settings.
     * Takes user to the settings window
     *
     * @param view the clickable text view
     */
    public void goToSettings(View view) {
        Intent intent = new Intent(this, Settings.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }
}