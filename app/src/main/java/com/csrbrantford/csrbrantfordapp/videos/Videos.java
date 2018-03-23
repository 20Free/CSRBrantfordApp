package com.csrbrantford.csrbrantfordapp.videos;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.csrbrantford.csrbrantfordapp.R;
import com.csrbrantford.csrbrantfordapp.buttonCanvases.PlayButtonDrawer;
import com.csrbrantford.csrbrantfordapp.home.Home;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.regex.Pattern;

public class Videos extends AppCompatActivity {

    private ListView videoList;
    private VideoAdapter adapter;
    public Videos videos;
    public ArrayList<Video> videoArrayList;
    private LinearLayout progressLayout;
    private RelativeLayout videoListLayout;
    private RequestQueue queue;
    private static String TAG = "video";

    /**
     * Project strings stored in res/values/strings.xml
     *
     * Title bar details stored in res/layout/videos_activity.xml
     * Layout details stored in res/layout/videos_content.xml
     *
     * @param savedInstanceState holds the previous instance state
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.videos_activity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Show the Up button in the action bar.
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        videoArrayList = new ArrayList<>();

        videoList = (ListView) findViewById(R.id.videoList);
        View footerView = ((LayoutInflater)getBaseContext().getSystemService(LAYOUT_INFLATER_SERVICE)).inflate(R.layout.videos_footer_view, videoList, false);
        videoList.addFooterView(footerView);
        videos = this;

        videoListLayout = (RelativeLayout) findViewById(R.id.videoListLayout);
        progressLayout = (LinearLayout) findViewById(R.id.videoProgress);

        videoList.setVisibility(View.GONE);
        progressLayout.setVisibility(View.VISIBLE);

        String url = getString(R.string.videos_remote_url);
        queue = Volley.newRequestQueue(this);


        final JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    String commaremoval = response.toString();
                    commaremoval = commaremoval.replaceAll(Pattern.quote(",\t\t}"), "}");
                    JSONObject inputJSON = new JSONObject(commaremoval);
                    JSONArray videoArray = inputJSON.optJSONArray("Videos");
                    int totalWidth = (int)getResources().getDimension(R.dimen.csr_logo_bottompadding);
                    int totalHeight = (int)getResources().getDimension(R.dimen.csr_logo_bottompadding);

                    for(int i = 0; i < videoArray.length(); i++){
                        Bitmap playButtonBitmap = Bitmap.createBitmap(totalWidth, totalHeight, Bitmap.Config.ARGB_8888);
                        PlayButtonDrawer playButtonDrawer = new PlayButtonDrawer();
                        Canvas playButtonCanvas = playButtonDrawer.drawPlayButton(totalWidth, totalHeight, playButtonBitmap);
                        playButtonCanvas.drawBitmap(playButtonBitmap,0,0,null);
                        JSONObject videoObject = videoArray.getJSONObject(i);
                        Video video = new Video(videoObject.getString("VideoTitle"),videoObject.getString("VideoDescription"), new BitmapDrawable(getResources(), playButtonBitmap), videoObject.getString("VideoLink"));
                        videoArrayList.add(video);
                    }

                    adapter = new VideoAdapter(videos, videoArrayList);
                    videoList.setAdapter(adapter);
                    videoList.setVisibility(View.VISIBLE);
                    progressLayout.setVisibility(View.GONE);


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Videos.this.getApplicationContext(), "Error, something went wrong", Toast.LENGTH_LONG).show();
                progressLayout.setVisibility(View.GONE);
                error.printStackTrace();
            }
        });
        jsonRequest.setTag(TAG);
        queue.add(jsonRequest);
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
    protected void onPause() {
        super.onPause();
        queue.cancelAll(TAG);
    }

    @Override
    protected void onStop() {
        super.onStop();
        queue.cancelAll(TAG);
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

    public void onItemClick(int mPosition){
        Video video = videoArrayList.get(mPosition);
        Intent intent = new Intent(this, VideoPlayerActivity.class);
        intent.putExtra("VideoURL", video.getUrl());
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }
}