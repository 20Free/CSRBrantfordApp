package com.csrbrantford.csrbrantfordapp.contactDirections;

import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.csrbrantford.csrbrantfordapp.R;
import com.csrbrantford.csrbrantfordapp.home.Home;

public class ContactDirections extends AppCompatActivity {
    RelativeLayout firstLayout;
    RelativeLayout thirdLayout;
    ImageView csrLogo;
    RelativeLayout instagramLayout;
    ImageButton instagramLogo;
    TextView instagramLink;
    RelativeLayout facebookLayout;
    ImageButton facebookLogo;
    TextView facebookLink;
    RelativeLayout twitterLayout;
    ImageButton twitterLogo;
    TextView twitterLink;
    RelativeLayout httpLayout;
    ImageButton httpLogo;
    TextView httpLink;
    TextView phoneTitle;
    TextView phoneLink;
    TextView emailTitle;
    TextView emailLink;
    TextView addressTitle;
    TextView addressLink;
    ImageView ivcfLogo;
    ImageView oefLogo;
    ImageView ocaLogo;
    ImageView cciLogo;

    int firstLayoutLeftRightPadding;
    int csrLogoWidth;
    int csrLogoHeight;
    int instaLogoWidth;
    int instaLogoHeight;
    int instaLogoStartEndMargin;
    int instaLogoTopMargin;
    int instaTextTopBottomPadding;
    int addressTextPaddingTop;
    int ivcfLogoPaddingTop;
    int ivcfLogoPaddingBottom;
    int oefLogoWidth;
    int oefLogoHeight;
    int oefLogoPaddingBottom;

    int screenWidth;
    int screenHeight;

    DisplayMetrics displayMetrics;

    /**
     * Project strings stored in res/values/strings.xml
     *
     * Title bar details stored in res/layout/contact_directions_activity.xml
     * Layout details stored in res/layout/contact_directions_content.xml
     *
     * @param savedInstanceState holds the previous instance state
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contact_directions_activity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Show the Up button in the action bar.
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        firstLayout = (RelativeLayout) findViewById(R.id.firstLayout);
        thirdLayout = (RelativeLayout) findViewById(R.id.thirdLayout);
        csrLogo = (ImageView) findViewById(R.id.csrLogo);
        instagramLayout = (RelativeLayout) findViewById(R.id.instagramLayout);
        instagramLogo = (ImageButton) findViewById(R.id.instagramLogo);
        instagramLink = (TextView) findViewById(R.id.instagramLink);
        facebookLayout = (RelativeLayout) findViewById(R.id.facebookLayout);
        facebookLogo = (ImageButton) findViewById(R.id.facebookLogo);
        facebookLink = (TextView) findViewById(R.id.facebookLink);
        twitterLayout = (RelativeLayout) findViewById(R.id.twitterLayout);
        twitterLogo = (ImageButton) findViewById(R.id.twitterLogo);
        twitterLink = (TextView) findViewById(R.id.twitterLink);
        httpLayout = (RelativeLayout) findViewById(R.id.httpLayout);
        httpLogo = (ImageButton) findViewById(R.id.httpLogo);
        httpLink = (TextView) findViewById(R.id.httpLink);
        phoneTitle = (TextView) findViewById(R.id.phoneTitle);
        phoneLink = (TextView) findViewById(R.id.phoneLink);
        emailTitle = (TextView) findViewById(R.id.emailTitle);
        emailLink = (TextView) findViewById(R.id.emailLink);
        addressTitle = (TextView) findViewById(R.id.addressTitle);
        addressLink = (TextView) findViewById(R.id.addressLink);
        ivcfLogo = (ImageView) findViewById(R.id.ivcfLogo);
        oefLogo = (ImageView) findViewById(R.id.oefLogo);
        ocaLogo = (ImageView) findViewById(R.id.ocaLogo);
        cciLogo = (ImageView) findViewById(R.id.cciLogo);

        displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            screenWidth = displayMetrics.widthPixels;
            screenHeight = displayMetrics.heightPixels;
            firstLayoutLeftRightPadding = (int) (screenWidth * 0.049f);
            csrLogoWidth = (int) (screenWidth * 0.486f);
            csrLogoHeight = (int) (screenHeight * 0.137f);
            instaLogoWidth = (int) (screenWidth * 0.2f);
            instaLogoHeight = (int) (screenHeight * 0.116f);
            instaLogoStartEndMargin = (int) (screenWidth * 0.134f);
            instaLogoTopMargin = (int) (screenHeight * 0.055f);
            instaTextTopBottomPadding = (int) (screenHeight * 0.014f);
            addressTextPaddingTop = (int) (screenHeight * 0.007f);
            ivcfLogoPaddingTop = (int) (screenHeight * 0.034f);
            ivcfLogoPaddingBottom = (int) (screenHeight * 0.137f);
            oefLogoWidth = (int) (screenWidth * 0.17f);
            oefLogoHeight = (int) (screenHeight * 0.096f);
            oefLogoPaddingBottom = (int) (screenHeight * 0.068f);
            updateScreen();

        } else if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            screenWidth = displayMetrics.widthPixels;
            screenHeight = displayMetrics.heightPixels;
            firstLayoutLeftRightPadding = (int) (screenHeight * 0.049f);
            csrLogoWidth = (int) (screenWidth * 0.486f);
            csrLogoHeight = (int) (screenHeight * 0.137f);
            instaLogoWidth = (int) (screenWidth * 0.2f);
            instaLogoHeight = (int) (screenHeight * 0.116f);
            instaLogoStartEndMargin = (int) (screenHeight * 0.273f);
            instaLogoTopMargin = (int) (screenHeight * 0.055f);
            instaTextTopBottomPadding = (int) (screenHeight * 0.014f);
            addressTextPaddingTop = (int) (screenHeight * 0.007f);
            ivcfLogoPaddingTop = (int) (screenHeight * 0.034f);
            ivcfLogoPaddingBottom = (int) (screenHeight * 0.137f);
            oefLogoWidth = (int) (screenWidth * 0.17f);
            oefLogoHeight = (int) (screenHeight * 0.096f);
            oefLogoPaddingBottom = (int) (screenHeight * 0.068f);
            updateScreen();
        }

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            screenWidth = displayMetrics.widthPixels;
            screenHeight = displayMetrics.heightPixels;
            firstLayoutLeftRightPadding = (int) (screenWidth * 0.049f);
            csrLogoWidth = (int) (screenWidth * 0.486f);
            csrLogoHeight = (int) (screenHeight * 0.137f);
            instaLogoWidth = (int) (screenWidth * 0.2f);
            instaLogoHeight = (int) (screenHeight * 0.116f);
            instaLogoStartEndMargin = (int) (screenWidth * 0.134f);
            instaLogoTopMargin = (int) (screenHeight * 0.055f);
            instaTextTopBottomPadding = (int) (screenHeight * 0.014f);
            addressTextPaddingTop = (int) (screenHeight * 0.007f);
            ivcfLogoPaddingTop = (int) (screenHeight * 0.034f);
            ivcfLogoPaddingBottom = (int) (screenHeight * 0.137f);
            oefLogoWidth = (int) (screenWidth * 0.17f);
            oefLogoHeight = (int) (screenHeight * 0.096f);
            oefLogoPaddingBottom = (int) (screenHeight * 0.068f);
            updateScreen();
        } else if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            screenWidth = displayMetrics.widthPixels;
            screenHeight = displayMetrics.heightPixels;
            firstLayoutLeftRightPadding = (int) (screenHeight * 0.049f);
            csrLogoWidth = (int) (screenWidth * 0.486f);
            csrLogoHeight = (int) (screenHeight * 0.137f);
            instaLogoWidth = (int) (screenWidth * 0.2f);
            instaLogoHeight = (int) (screenHeight * 0.116f);
            instaLogoStartEndMargin = (int) (screenHeight * 0.273f);
            instaLogoTopMargin = (int) (screenHeight * 0.055f);
            instaTextTopBottomPadding = (int) (screenHeight * 0.014f);
            addressTextPaddingTop = (int) (screenHeight * 0.007f);
            ivcfLogoPaddingTop = (int) (screenHeight * 0.034f);
            ivcfLogoPaddingBottom = (int) (screenHeight * 0.137f);
            oefLogoWidth = (int) (screenWidth * 0.17f);
            oefLogoHeight = (int) (screenHeight * 0.096f);
            oefLogoPaddingBottom = (int) (screenHeight * 0.068f);
            updateScreen();
        }
    }

    public void updateScreen() {
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        layoutParams.setMargins(firstLayoutLeftRightPadding, 0, firstLayoutLeftRightPadding, 0);
        firstLayout.setLayoutParams(layoutParams);

        csrLogo.getLayoutParams().width = csrLogoWidth;
        csrLogo.getLayoutParams().height = csrLogoHeight;
        csrLogo.requestLayout();

        RelativeLayout.LayoutParams instaParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        instaParams.setMargins(instaLogoStartEndMargin, instaLogoTopMargin, 0, 0);
        instaParams.addRule(RelativeLayout.BELOW, R.id.csrLogo);
        instaParams.addRule(RelativeLayout.ALIGN_PARENT_START);
        instagramLayout.setLayoutParams(instaParams);
        instagramLayout.setGravity(Gravity.CENTER);
        instagramLogo.getLayoutParams().width = instaLogoWidth;
        instagramLogo.getLayoutParams().height = instaLogoHeight;
        instagramLogo.requestLayout();
        instagramLink.setPadding(0, instaTextTopBottomPadding, 0, instaTextTopBottomPadding);

        RelativeLayout.LayoutParams facebookParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        facebookParams.setMargins(0, instaLogoTopMargin, instaLogoStartEndMargin, 0);
        facebookParams.addRule(RelativeLayout.BELOW, R.id.csrLogo);
        facebookParams.addRule(RelativeLayout.ALIGN_PARENT_END);
        facebookLayout.setLayoutParams(facebookParams);
        facebookLayout.setGravity(Gravity.CENTER);
        facebookLogo.getLayoutParams().width = instaLogoWidth;
        facebookLogo.getLayoutParams().height = instaLogoHeight;
        facebookLogo.requestLayout();
        facebookLink.setPadding(0, instaTextTopBottomPadding, 0, instaTextTopBottomPadding);

        RelativeLayout.LayoutParams twitterParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        twitterParams.setMargins(instaLogoStartEndMargin, addressTextPaddingTop, 0, addressTextPaddingTop);
        twitterParams.addRule(RelativeLayout.BELOW, R.id.instagramLayout);
        twitterParams.addRule(RelativeLayout.ALIGN_PARENT_START);
        twitterLayout.setGravity(Gravity.CENTER);
        twitterLayout.setLayoutParams(twitterParams);
        twitterLogo.getLayoutParams().width = instaLogoWidth;
        twitterLogo.getLayoutParams().height = instaLogoHeight;
        twitterLogo.requestLayout();
        twitterLink.setPadding(0, instaTextTopBottomPadding, 0, instaTextTopBottomPadding);

        RelativeLayout.LayoutParams httpParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        httpParams.setMargins(0, addressTextPaddingTop, instaLogoStartEndMargin, addressTextPaddingTop);
        httpParams.addRule(RelativeLayout.ALIGN_PARENT_END);
        httpParams.addRule(RelativeLayout.BELOW, R.id.facebookLayout);
        httpLayout.setGravity(Gravity.CENTER);
        httpLayout.setLayoutParams(httpParams);
        httpLogo.getLayoutParams().width = instaLogoWidth;
        httpLogo.getLayoutParams().height = instaLogoHeight;
        httpLogo.requestLayout();
        httpLink.setPadding(0, instaTextTopBottomPadding, 0, instaTextTopBottomPadding);

        addressTitle.setPadding(0, addressTextPaddingTop, 0, 0);
        addressLink.setPadding(0, addressTextPaddingTop, 0, 0);
        phoneTitle.setPadding(0, addressTextPaddingTop, 0, 0);
        phoneLink.setPadding(0, addressTextPaddingTop, 0, 0);
        emailTitle.setPadding(0, addressTextPaddingTop, 0, 0);
        emailLink.setPadding(0, addressTextPaddingTop, 0, 0);

        ivcfLogo.setPadding(0, ivcfLogoPaddingTop, 0, ivcfLogoPaddingBottom);

        oefLogo.getLayoutParams().width = oefLogoWidth;
        oefLogo.getLayoutParams().height = oefLogoHeight;
        oefLogo.setPadding(0, 0, 0, oefLogoPaddingBottom);
        ocaLogo.getLayoutParams().height = oefLogoHeight;
        ocaLogo.setPadding(0, 0, 0, oefLogoPaddingBottom);
        cciLogo.getLayoutParams().width = oefLogoWidth;
        cciLogo.getLayoutParams().height = oefLogoHeight;
        cciLogo.setPadding(0, 0, 0, oefLogoPaddingBottom);
    }

    /**
     * Set custom animation when the back button is pressed.
     */
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
    }

    /**
     * Enable use of back button
     *
     * @param item is the back button. When the user clicks it, it goes back to the home screen.
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            navigateUpTo(new Intent(this, Home.class));
            overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Listener from Instagram button onClick method.
     * Opens a link to the Instagram page.
     *
     * @param view contains the view from which the button is contained.
     */
    public void openInstagram(View view){
        Intent intent;
        try {
            intent = new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.instagram_scheme)));
        } catch (Exception e) {
            intent = new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.instagram_url)));
        }
        startActivity(intent);
    }

    /**
     * Listener from Facebook button onClick method.
     * Opens a link to the Facebook page.
     *
     * @param view contains the view from which the button is contained.
     */
    public void openFacebook(View view){
        Intent intent;
        try {
            intent = new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.facebook_scheme)));
        } catch (Exception e) {
            intent =  new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.facebook_url)));
        }
        startActivity(intent);
    }

    /**
     * Listener from Twitter button onClick method.
     * Opens a link to the Twitter page.
     *
     * @param view contains the view from which the button is contained.
     */
    public void openTwitter(View view){
        Intent intent;
        try {
            intent = new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.twitter_scheme)));
        } catch (Exception e){
            intent = new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.twitter_url)));
        }
        startActivity(intent);
    }

    /**
     * Listener from Website button onClick method.
     * Opens a link to the webpage.
     *
     * @param view contains the view from which the button is contained
     */
    public void openWebsite(View view){
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.csr_brantford_url)));
        startActivity(intent);
    }

    /**
     * Listener from Phone number link onClick method.
     * Calls the Brantford Circle Square Ranch number.
     *
     * @param view contains the view from which the link is contained
     */
    public void callBrantfordCSR(View view){
        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse(getString(R.string.csr_brantford_phone_number)));
        try {
            startActivity(intent);
        } catch (Exception e){
            Toast.makeText(this, getString(R.string.error_could_not_call), Toast.LENGTH_LONG).show();
        }
    }

    /**
     * Listener from Email link onClick method.
     * Emails Circle Square Ranch from default email.
     *
     * @param view contains the view from which the link is contained.
     */
    public void emailBrantfordCSR(View view){
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse(getString(R.string.mailto_email_csr_brantford)));

        try{
            startActivity(Intent.createChooser(intent, getString(R.string.email_csr)));
        }catch (Exception e){
            Toast.makeText(this,getString(R.string.no_email_clients),Toast.LENGTH_LONG).show();
        }
    }

    /**
     * Listener from Address link onClick method.
     * Opens google maps to Brantford Circle Square Ranch location.
     *
     * @param view contains the view from which the link is contained.
     */
    public void findBrantfordCSR(View view){
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.csr_brantford_location)));
        startActivity(intent);
    }
}