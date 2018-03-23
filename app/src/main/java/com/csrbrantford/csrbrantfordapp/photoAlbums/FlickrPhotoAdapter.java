package com.csrbrantford.csrbrantfordapp.photoAlbums;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.csrbrantford.csrbrantfordapp.R;

import java.util.ArrayList;

/**
 * Created by Jonathan on 10/28/2016.
 */

class FlickrPhotoAdapter extends BaseAdapter {

    private Activity activity;
    private ArrayList data;
    private static LayoutInflater inflater;


    FlickrPhotoAdapter(Activity activity, ArrayList data){
        this.activity = activity;
        this.data = data;

        inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public int getCount() {
        if(data.size() <= 0)
            return 1;
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
    public View getView(int i, View view, ViewGroup viewGroup) {
        Photo photo;
        View row = view;
        final PhotosHolder holder;

        if(row == null){
            row = inflater.inflate(R.layout.photo_row_layout, viewGroup, false);

            holder = new PhotosHolder();
            holder.flickrPhoto = (ImageView) row.findViewById(R.id.flickrPhoto);

            row.setTag(holder);
        } else {
            holder = (PhotosHolder)row.getTag();
        }

        if(data.size() <= 0){
            holder.flickrPhoto.setVisibility(View.GONE);
        } else {
            photo = (Photo) data.get(i);
            ImageRequest imageRequest = new ImageRequest(photo.getPhotoUrl(), new Response.Listener<Bitmap>() {
                @Override
                public void onResponse(Bitmap response) {
                    holder.flickrPhoto.setImageBitmap(response);
                }
            }, 150, 150, ImageView.ScaleType.CENTER, Bitmap.Config.ARGB_8888, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    error.printStackTrace();
                }
            });
            ((PhotoAlbumActivity)activity).getQueue().add(imageRequest);

            row.setOnClickListener(new OnItemClickListener(i));
        }
        return row;
    }

    private static class PhotosHolder{
        ImageView flickrPhoto;
    }

    private class OnItemClickListener implements View.OnClickListener {
        private int mPosition;

        OnItemClickListener(int mPosition){
            this.mPosition = mPosition;
        }

        @Override
        public void onClick(View view) {
            PhotoAlbumActivity photoAlbumActivity = (PhotoAlbumActivity)activity;
            photoAlbumActivity.onItemClick(mPosition);
        }
    }
}