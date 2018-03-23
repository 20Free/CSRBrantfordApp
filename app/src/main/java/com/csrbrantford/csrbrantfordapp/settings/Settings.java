package com.csrbrantford.csrbrantfordapp.settings;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.csrbrantford.csrbrantfordapp.R;
import com.csrbrantford.csrbrantfordapp.music.client.MediaBrowserAdapter;
import com.csrbrantford.csrbrantfordapp.music.ui.Music;

import java.io.IOException;

public class Settings extends AppCompatActivity {

    public static boolean BACKGROUND;

    private MediaBrowserAdapter mMediaBrowserAdapter;

    /**
     * Project strings stored in res/values/strings.xml
     *
     * Title bar details stored in res/layout/settings_activity.xml
     * Layout details stored in res/layout/settings_content.xml
     *
     * @param savedInstanceState holds the previous instance state
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mMediaBrowserAdapter  = new MediaBrowserAdapter(this);

        Switch musicBackgroundSwitch = (Switch) findViewById(R.id.musicBackgroundSwitch);
        //for first time instance, should work otherwise
        if(!musicBackgroundSwitch.isChecked() && Settings.BACKGROUND) {
            musicBackgroundSwitch.setChecked(true);
        }
        musicBackgroundSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Settings.BACKGROUND = isChecked;
                if(!isChecked) {
                    mMediaBrowserAdapter.onStop();
                }
            }
        });

        // Show the Up button in the action bar.
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    /**
     * Set custom animation when the back button is pressed.
     */
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            // This ID represents the Home or Up button. In the case of this
            // activity, the Up button is shown. For
            // more details, see the Navigation pattern on Android Design:
            //
            // http://developer.android.com/design/patterns/navigation.html#up-vs-back
            //
            finish();
            overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void openLegalInfo(View view) {
        Intent intent = new Intent(this, LegalAndCopyrightInfo.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }
}
