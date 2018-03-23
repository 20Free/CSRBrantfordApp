package com.csrbrantford.csrbrantfordapp.news;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;
import com.csrbrantford.csrbrantfordapp.R;

import java.util.ArrayList;

/**
 * Created by Jonathan on 12/21/2016.
 */

public class CampNewsAdapter extends BaseAdapter {

    private Activity activity;
    private ArrayList data;
    private LayoutInflater inflater;
    private NewsHolder holder;
    private LinearLayout progressView;
    private ListView blogPostListView;

    public CampNewsAdapter(Activity activity, ArrayList data){
        this.activity = activity;
        this.data = data;

        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        if(data.size() <= 0) {
            return 1;
        }
        return data.size();
    }

    @Override
    public Object getItem(int pos) {
        return pos;
    }

    @Override
    public long getItemId(int pos) {
        return pos;
    }

    @Override
    public View getView(int pos, View convertView, ViewGroup parent) {
        BlogPost blogPost;
        View row = convertView;
        progressView = (LinearLayout)activity.findViewById(R.id.campNewsProgress);
        blogPostListView = (ListView)activity.findViewById(R.id.news_list);

        if(row == null) {
            row = inflater.inflate(R.layout.camp_news_row_layout, parent, false);

            holder = new NewsHolder();
            holder.newsImage = (ImageView)row.findViewById(R.id.camp_news_photo);
            holder.newsTitle = (TextView)row.findViewById(R.id.news_title);
            holder.newsDate = (TextView)row.findViewById(R.id.news_date);
            holder.newsDetails = (TextView)row.findViewById(R.id.news_details);

            row.setTag(holder);
        } else {
            holder = (NewsHolder)row.getTag();
        }

        if(data.size() <= 0) {
            holder.newsTitle.setText(activity.getString(R.string.default_post_title));
        } else {
            blogPost = (BlogPost) data.get(pos);
            holder.newsImage.setBackground(blogPost.getNewsImage());
            holder.newsTitle.setText(blogPost.getNewsTitle());
            holder.newsDate.setText(blogPost.getNewsDate());
            holder.newsDetails.setText(blogPost.getNewsDetails());
        }
        return row;
    }

    private static class NewsHolder {
        ImageView newsImage;
        TextView newsTitle;
        TextView newsDate;
        TextView newsDetails;
    }
}
