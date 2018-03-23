package com.csrbrantford.csrbrantfordapp.news;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.csrbrantford.csrbrantfordapp.R;
import com.csrbrantford.csrbrantfordapp.home.Home;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class CampNews extends AppCompatActivity {

    private RequestQueue queue;
    private ArrayList<BlogPost> blogPostArrayList;
    private String blogPostImageUrl;
    private String blogPostTitle;
    private String blogPostDate;
    private String blogPostDetails;
    private CampNews campNews;
    private ListView blogPostListView;
    private int count;
    private LinearLayout progressView;

    /**
     * Project strings stored in res/values/strings.xml
     *
     * Title bar details stored in res/layout/camp_news_activity.xml
     * Layout details stored in res/layout/camp_news_content.xml
     *
     * @param savedInstanceState holds the previous instance state
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.camp_news_activity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        queue = Volley.newRequestQueue(this);

        blogPostArrayList = new ArrayList<>();
        campNews = this;
        blogPostListView = (ListView) findViewById(R.id.news_list);
        progressView = (LinearLayout) findViewById(R.id.campNewsProgress);
        count = 0;

        blogPostListView.setVisibility(View.GONE);
        progressView.setVisibility(View.VISIBLE);

        final StringRequest blogPostArrayRequest = new StringRequest(Request.Method.GET, getString(R.string.news_base_url) + getString(R.string.blogger_api_key), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                count = 0;
                Log.i("RESPONSE", response);
                JSONObject blogPostJSONObject = null;
                try {
                    blogPostJSONObject = new JSONObject(response);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                final JSONArray blogPostsJSONArray = blogPostJSONObject.optJSONArray("items");

                for(int i = 0; i < blogPostsJSONArray.length(); i++) {
                    String url = null;
                    try {
                        url = blogPostsJSONArray.getJSONObject(i).getString("selfLink") + "?" + getString(R.string.blogger_api_key);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    StringRequest blogPostRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            Log.i("RESPONSE", response);
                            try {
                                final JSONObject blogPostJSONObject = new JSONObject(response);
                                if(blogPostJSONObject.getString("content").contains("src=")) {
                                    blogPostImageUrl = blogPostJSONObject.getString("content").substring(blogPostJSONObject.getString("content").indexOf("src=\"") + 5, blogPostJSONObject.getString("content").toLowerCase().lastIndexOf("g\"") + 1);
                                    Log.i("IMAGE URL", blogPostImageUrl);
                                } else {
                                    blogPostImageUrl = "";
                                }
                                if(blogPostImageUrl.equals("")) {
                                    blogPostTitle = blogPostJSONObject.getString("title");
                                    SimpleDateFormat postDateFormatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ", Locale.CANADA);
                                    Date postDate = null;
                                    try {
                                        postDate = postDateFormatter.parse(blogPostJSONObject.getString("updated").replaceAll("Z$","+0000"));
                                    } catch (ParseException e) {
                                        e.printStackTrace();
                                    }
                                    SimpleDateFormat postDateFormatOutput = new SimpleDateFormat("MMM, dd yyyy", Locale.CANADA);
                                    blogPostDate = postDateFormatOutput.format(postDate);
                                    if(blogPostJSONObject.getString("content").contains("=")) {
                                        String editContentString = blogPostJSONObject.getString("content").substring(0, blogPostJSONObject.getString("content").indexOf('='));

                                        int periodLastIndex = editContentString.lastIndexOf('.');
                                        int exclamationLastIndex = editContentString.lastIndexOf('!');
                                        if (periodLastIndex > exclamationLastIndex) {
                                            editContentString = editContentString.substring(0, editContentString.lastIndexOf('.') + 1);
                                        } else if (exclamationLastIndex > periodLastIndex) {
                                            editContentString = editContentString.substring(0, editContentString.lastIndexOf('!') + 1);
                                        }

                                        blogPostDetails = editContentString;
                                    } else {
                                        blogPostDetails = blogPostJSONObject.getString("content");
                                    }
                                    if(blogPostDetails.contains("www")) {
                                        blogPostDetails = blogPostDetails.substring(0, blogPostDetails.lastIndexOf("www"));
                                    }
                                    Bitmap defaultNewsImage = BitmapFactory.decodeResource(getResources(), R.drawable.camp_news);
                                    BlogPost blogPost = new BlogPost(new BitmapDrawable(getResources(), defaultNewsImage), blogPostTitle, blogPostDate, blogPostDetails);
                                    blogPostArrayList.add(blogPost);
                                    if (count == blogPostsJSONArray.length()-1) {
                                        CampNewsAdapter campNewsAdapter = new CampNewsAdapter(campNews, blogPostArrayList);
                                        blogPostListView.setAdapter(campNewsAdapter);
                                        progressView.setVisibility(View.GONE);
                                        blogPostListView.setVisibility(View.VISIBLE);
                                    }
                                    count++;
                                } else {
                                    ImageRequest newsImageRequest = new ImageRequest(blogPostImageUrl, new Response.Listener<Bitmap>() {
                                        @Override
                                        public void onResponse(Bitmap response) {
                                            try {
                                                blogPostTitle = blogPostJSONObject.getString("title");
                                                SimpleDateFormat postDateFormatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ", Locale.CANADA);
                                                Date postDate = null;
                                                try {
                                                    postDate = postDateFormatter.parse(blogPostJSONObject.getString("updated").replaceAll("Z$","+0000"));
                                                } catch (ParseException e) {
                                                    e.printStackTrace();
                                                }
                                                SimpleDateFormat postDateFormatOutput = new SimpleDateFormat("MMM, dd yyyy", Locale.CANADA);
                                                blogPostDate = postDateFormatOutput.format(postDate);
                                                if(blogPostJSONObject.getString("content").contains("=")) {
                                                    String editContentString = blogPostJSONObject.getString("content").substring(0, blogPostJSONObject.getString("content").indexOf('='));
                                                    int periodLastIndex = editContentString.lastIndexOf('.');
                                                    int exclamationLastIndex = editContentString.lastIndexOf('!');
                                                    if (periodLastIndex > exclamationLastIndex) {
                                                        editContentString = editContentString.substring(0, editContentString.lastIndexOf('.'));
                                                    } else if (exclamationLastIndex > periodLastIndex) {
                                                        editContentString = editContentString.substring(0, editContentString.lastIndexOf('!'));
                                                    }

                                                    blogPostDetails = editContentString;
                                                } else {
                                                    blogPostDetails = blogPostJSONObject.getString("content");
                                                }
                                                if(blogPostDetails.contains("www")) {
                                                    blogPostDetails = blogPostDetails.substring(0, blogPostDetails.lastIndexOf("www"));
                                                }
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                            BlogPost blogPost = new BlogPost(new BitmapDrawable(getResources(), response), blogPostTitle, blogPostDate, blogPostDetails);
                                            blogPostArrayList.add(blogPost);

                                            if (count == blogPostsJSONArray.length()-1) {
                                                CampNewsAdapter campNewsAdapter = new CampNewsAdapter(campNews, blogPostArrayList);
                                                blogPostListView.setAdapter(campNewsAdapter);
                                                progressView.setVisibility(View.GONE);
                                                blogPostListView.setVisibility(View.VISIBLE);
                                            }
                                            count++;
                                            Log.e("STRING CHECK", blogPostDetails);
                                        }
                                    }, 200, 200, ImageView.ScaleType.FIT_XY, Bitmap.Config.ARGB_8888, new Response.ErrorListener() {
                                        @Override
                                        public void onErrorResponse(VolleyError error) {

                                        }
                                    });
                                    queue.add(newsImageRequest);
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            progressView.setVisibility(View.GONE);
                            Toast.makeText(getApplicationContext(), "Error, something went wrong", Toast.LENGTH_SHORT).show();
                        }
                    });
                    queue.add(blogPostRequest);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressView.setVisibility(View.GONE);
                Toast.makeText(getApplicationContext(), "Error, something went wrong", Toast.LENGTH_SHORT).show();
            }
        });

        queue.add(blogPostArrayRequest);
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
}
