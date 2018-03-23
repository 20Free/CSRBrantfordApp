package com.csrbrantford.csrbrantfordapp.tipsNThemeMeals;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import com.csrbrantford.csrbrantfordapp.R;
import com.csrbrantford.csrbrantfordapp.home.Home;

public class TipsNThemeMeals extends AppCompatActivity {

    /**
     * Project strings stored in res/values/strings.xml
     *
     * Title bar details stored in res/layout/tips_n_theme_meals_activity.xml
     * Layout details stored in res/layout/tips_n_theme_meals_content.xml
     *
     * @param savedInstanceState holds the previous instance state
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tips_n_theme_meals_activity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Show the Up button in the action bar.
        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        if (tabLayout != null) {
            tabLayout.addTab(tabLayout.newTab().setText("Tips"));
            tabLayout.addTab(tabLayout.newTab().setText("Theme Meals"));
            tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        }

        final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        final TipsNThemeMealsPagerAdapter adapter = new TipsNThemeMealsPagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        if (viewPager != null) {
            viewPager.setAdapter(adapter);
            viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        }
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (viewPager != null) {
                    viewPager.setCurrentItem(tab.getPosition());
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
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
