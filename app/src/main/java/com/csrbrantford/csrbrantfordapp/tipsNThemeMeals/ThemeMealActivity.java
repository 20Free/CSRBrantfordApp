package com.csrbrantford.csrbrantfordapp.tipsNThemeMeals;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.csrbrantford.csrbrantfordapp.R;
import com.csrbrantford.csrbrantfordapp.home.Home;

public class ThemeMealActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.theme_meal_activity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Show the Up button in the action bar.
        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        Bundle extras = getIntent().getExtras();

        if(extras != null) {
            TextView themeMealTitle = (TextView)findViewById(R.id.themeMeal);
            TextView themeMealDescription = (TextView)findViewById(R.id.themeMealDescription);
            TextView actionBarTitle = (TextView) findViewById(R.id.themeMealBarTitle);
            String actionBarTitleString = extras.getString("title");
            String title = extras.getString("name");
            String description = extras.getString("desc");
            actionBarTitle.setText(actionBarTitleString);
            themeMealTitle.setText(title);
            themeMealDescription.setText(description);
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
            finish();
            overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}