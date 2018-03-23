package com.csrbrantford.csrbrantfordapp.photoAlbums;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.csrbrantford.csrbrantfordapp.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class PhotoAlbumActivity extends AppCompatActivity {

    private GridView photoGridView;
    private String photosetID;
    private String photosetTitle;
    private static ArrayList<Photo> photos;
    private FlickrPhotoAdapter adapter;
    private PhotoAlbumActivity photoAlbumActivity;
    private RequestQueue queue;
    private LinearLayout progressLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.photo_album_activity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Show the Up button in the action bar.
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        Bundle extras = getIntent().getExtras();


        if(extras != null){
            photosetID = extras.getString("AlbumId");
            photosetTitle = extras.getString("AlbumTitle");
        }
        setTitle(photosetTitle);
        photoGridView = (GridView) findViewById(R.id.photoGrid);

        photos = new ArrayList<>();

        queue = Volley.newRequestQueue(this);

        String url = getString(R.string.flickr_photoset_images_first) + photosetID + getString(R.string.flickr_photoset_images_second);

        photoAlbumActivity = this;

        progressLayout = (LinearLayout) findViewById(R.id.photoAlbumProgress);
        progressLayout.setVisibility(View.VISIBLE);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject photoSet = response.getJSONObject("photoset");
                    JSONArray photoArray = photoSet.optJSONArray("photo");

                    for(int i = 0; i < photoArray.length(); i++){
                        JSONObject photo = photoArray.getJSONObject(i);
                         String url = getString(R.string.flickr_photo_link_first)
                                + photo.getString("farm")
                                + getString(R.string.flickr_photo_link_second)
                                + photo.getString("server")
                                + getString(R.string.flickr_photo_link_third)
                                + photo.getString("id")
                                + getString(R.string.flickr_photo_link_fourth)
                                + photo.getString("secret")
                                + getString(R.string.flickr_photo_link_fifth);
                        photos.add(new Photo(url));

                    }
                    adapter = new FlickrPhotoAdapter(photoAlbumActivity, photos);
                    photoGridView.setAdapter(adapter);
                    progressLayout.setVisibility(View.GONE);
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        queue.add(jsonObjectRequest);


    }

    public RequestQueue getQueue() {
        return queue;
    }

    /**
     * Set custom animation when the back button is pressed.
     */
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
    }

    public static ArrayList<Photo> getPhotos() {
        return photos;
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
        Intent intent = new Intent(this, PhotoPagerActivity.class);
        intent.putExtra("AlbumTitle", photosetTitle);
        intent.putExtra("pos", mPosition);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }




}
