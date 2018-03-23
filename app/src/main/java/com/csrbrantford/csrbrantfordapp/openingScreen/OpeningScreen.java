package com.csrbrantford.csrbrantfordapp.openingScreen;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

import com.csrbrantford.csrbrantfordapp.home.Home;
import com.csrbrantford.csrbrantfordapp.R;

public class OpeningScreen extends AppCompatActivity {

    AnimationDrawable openingAnimation;

    /**
     * Project strings stored in res/values/strings.xml
     *
     * Activity view stored in opening_screen_content.xml
     *
     * Create and initialize the activity.
     *
     * @param savedInstanceState holds the previous instance state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.opening_screen_activity);

        Animation brownBottomUp = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.brown_image_top_from_bottom);
        Animation blueBottomUp = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.blue_image_top_from_bottom);
        Animation buttonBottomUp = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.button_top_from_bottom);

        ImageView blueImage = (ImageView) findViewById(R.id.blueImg);
        ImageView brownImage = (ImageView) findViewById(R.id.brownImg);
        Button amaze_button = (Button) findViewById(R.id.startButton);

        blueImage.setAnimation(blueBottomUp);
        brownImage.setAnimation(brownBottomUp);
        amaze_button.setAnimation(buttonBottomUp);

        blueBottomUp.start();
        brownBottomUp.start();
        buttonBottomUp.start();
    }

    /**
     * Listener from startButton onClick method.
     * Starts the Home Activity.
     *
     * @param view contains the view from which the button is contained.
     */
    public void startHome(View view){
        Intent intent = new Intent(this, Home.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_from_bottom, android.R.anim.fade_out);

    }
}
