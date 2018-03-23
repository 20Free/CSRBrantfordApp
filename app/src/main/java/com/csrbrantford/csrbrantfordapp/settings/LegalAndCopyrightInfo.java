package com.csrbrantford.csrbrantfordapp.settings;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.ListViewCompat;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ListView;

import com.csrbrantford.csrbrantfordapp.R;

import java.util.ArrayList;

public class LegalAndCopyrightInfo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.legal_and_copyright_info_activity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Show the Up button in the action bar.
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        ArrayList<LegalItem> legalItems = new ArrayList<>();
        String[] data = getResources().getStringArray(R.array.legal_variables);

        for(String data2 : data) {
            String[] data1 = data2.split("\\|");
            legalItems.add(new LegalItem(data1[0], data1[1]));
        }

        ListViewCompat legalListView = (ListViewCompat) findViewById(R.id.legalInfoAndCopyrightListView);

        legalListView.setDivider(ContextCompat.getDrawable(legalListView.getContext(), R.drawable.list_divider_black));
        LegalAndCopyrightInfoAdapter adapter = new LegalAndCopyrightInfoAdapter(this, legalItems);
        legalListView.setAdapter(adapter);
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
