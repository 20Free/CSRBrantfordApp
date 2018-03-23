package com.csrbrantford.csrbrantfordapp.photoAlbums;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
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


public class PhotoAlbums extends AppCompatActivity {

    private ListView photoAlbumList;
    private PhotoAlbumAdapter adapter;
    private PhotoAlbums photoAlbums;
    private ArrayList<PhotoAlbum> photoArrayList;
    private LinearLayout progressLayout;

    /**
     * Project strings stored in res/values/strings.xml
     *
     * Title bar details stored in res/layout/photo_albums_activity.xml
     * Layout details stored in res/layout/photo_albums_content.xml
     *
     * @param savedInstanceState holds the previous instance state
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.photo_albums_activity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Show the Up button in the action bar.
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        photoArrayList = new ArrayList<>();

        photoAlbumList = (ListView) findViewById(R.id.photoAlbumList);
        View footerView = ((LayoutInflater)getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.photo_albums_footer_view, photoAlbumList, false);
        photoAlbumList.addFooterView(footerView);
        photoAlbums = this;
        progressLayout = (LinearLayout) findViewById(R.id.photoAlbumsProgress);
        progressLayout.setVisibility(View.VISIBLE);
        photoAlbumList.setVisibility(View.GONE);

        String url = getString(R.string.flickr_photoset_list);
        RequestQueue queue = Volley.newRequestQueue(this);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject photosets = response.getJSONObject("photosets");
                    JSONArray albumArray = photosets.optJSONArray("photoset");
                    int totalWidth = (int)getResources().getDimension(R.dimen.csr_logo_bottompadding);
                    int totalHeight = (int)getResources().getDimension(R.dimen.csr_logo_bottompadding);

                    for(int i = 0; i < albumArray.length(); i++){
                        Bitmap playButtonBitmap = Bitmap.createBitmap(totalWidth, totalHeight, Bitmap.Config.ARGB_8888);
                        PlayButtonDrawer playButtonDrawer = new PlayButtonDrawer();
                        Canvas playButtonCanvas = playButtonDrawer.drawPlayButton(totalWidth, totalHeight, playButtonBitmap);
                        playButtonCanvas.drawBitmap(playButtonBitmap, 0, 0, null);
                        JSONObject photoAlbumObject = albumArray.getJSONObject(i);
                        PhotoAlbum photoAlbum = new PhotoAlbum(photoAlbumObject.getJSONObject("title").getString("_content"), photoAlbumObject.getJSONObject("description").getString("_content"), new BitmapDrawable(getResources(), playButtonBitmap), photoAlbumObject.getString("id"));
                        photoArrayList.add(photoAlbum);
                    }

                    adapter = new PhotoAlbumAdapter(photoAlbums, photoArrayList);
                    photoAlbumList.setAdapter(adapter);
                    progressLayout.setVisibility(View.GONE);
                    photoAlbumList.setVisibility(View.VISIBLE);
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(PhotoAlbums.this.getApplicationContext(), "Error, something went wrong", Toast.LENGTH_LONG).show();
                progressLayout.setVisibility(View.GONE);
                error.printStackTrace();
            }
        });

        queue.add(jsonObjectRequest);
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

    public void onItemClick(int pos){
        PhotoAlbum photoAlbum = photoArrayList.get(pos);
        Intent intent = new Intent(this, PhotoAlbumActivity.class);
        intent.putExtra("AlbumId", photoAlbum.getId());
        intent.putExtra("AlbumTitle", photoAlbum.getTitle());
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }
}
