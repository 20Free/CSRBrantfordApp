package com.csrbrantford.csrbrantfordapp.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.csrbrantford.csrbrantfordapp.aboutCSR.AboutCSR;
import com.csrbrantford.csrbrantfordapp.campInfo.CampInfo;
import com.csrbrantford.csrbrantfordapp.contactDirections.ContactDirections;
import com.csrbrantford.csrbrantfordapp.music.client.MediaBrowserAdapter;
import com.csrbrantford.csrbrantfordapp.music.ui.Music;
import com.csrbrantford.csrbrantfordapp.news.CampNews;
import com.csrbrantford.csrbrantfordapp.photoAlbums.PhotoAlbums;
import com.csrbrantford.csrbrantfordapp.settings.Settings;
import com.csrbrantford.csrbrantfordapp.tipsNThemeMeals.TipsNThemeMeals;
import com.csrbrantford.csrbrantfordapp.videos.Videos;
import com.csrbrantford.csrbrantfordapp.R;

public class Home extends AppCompatActivity {

    private static int FIRST_TIME = 0;

    /**
     * Project strings stored in res/values/strings.xml
     *
     * Title bar design details stored in res/layout/home_activity.xml
     * Layout design details stored in res/layout/home_content.xml
     *
     * @param savedInstanceState holds the previous instance state
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if(FIRST_TIME == 0) {
            Settings.BACKGROUND = true;
            FIRST_TIME++;
        }
        if(getIntent().getExtras() != null) {
            MediaBrowserAdapter mediaBrowserAdapter = (MediaBrowserAdapter)getIntent().getExtras().getSerializable("MEDIA PLAYER ADAPTER");
            mediaBrowserAdapter.getTransportControls().play();
        }


    }

    /**
     * Set custom animation when the back button is pressed.
     */
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(android.R.anim.fade_in, R.anim.slide_down_from_top);
    }

    /**
     * Listener from musicButton onClick method.
     * Starts the Music activity.
     *
     * @param view contains the view from which the button is contained.
     */
    public void playMusic(View view){
        Intent intent = new Intent(this, Music.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    /**
     * Listener from photosButton onClick method.
     * Starts the PhotoAlbums Activity.
     *
     * @param view contains the view from which the button is contained.
     */
    public void viewPhotoAlbums(View view){
        Intent intent = new Intent(this, PhotoAlbums.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    /**
     * Listener from videosButton onClick method.
     * Starts the Videos Activity.
     *
     * @param view contains the view from which the button is contained.
     */
    public void viewVideos(View view){
        Intent intent = new Intent(this, Videos.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    /**
     * Listener from newsButton onClick method.
     * Starts the CampNews Activity.
     *
     * @param view contains the view from which the button is contained.
     */
    public void viewCampNews(View view){
        Intent intent = new Intent(this, CampNews.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    /**
     * Listener from suggestionsButton onClick method.
     * Starts the TipsNThemeMeals Activity.
     *
     * @param view contains the view from which the button is contained.
     */
    public void sendSuggestions(View view){
        Intent intent = new Intent(this, TipsNThemeMeals.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    /**
     * Listener from campInfoButton onClick method.
     * Starts the CampInfo Activity.
     *
     * @param view contains the view from which the button is contained.
     */
    public void seeCampInfo(View view){
        Intent intent = new Intent(this, CampInfo.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    /**
     * Listener from whoButton onClick method.
     * Starts the AboutCSR Activity.
     *
     * @param view contains the view from which the button is contained.
     */
    public void learnAboutCSR(View view){
        Intent intent = new Intent(this, AboutCSR.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    /**
     * Listener from contactDirectionsButton onClick method.
     * Starts the ContactDirections Activity.
     *
     * @param view contains the view from which the button is contained.
     */
    public void getContactDirections(View view){
        Intent intent = new Intent(this, ContactDirections.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    /**
     * Listener from settingsButton onClick method.
     * Starts the Settings Activity.
     *
     * @param view contains the view from which the button is contained.
     */
    public void setSettings(View view){
        Intent intent = new Intent(this, Settings.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }
}