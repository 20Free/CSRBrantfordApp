package com.csrbrantford.csrbrantfordapp.photoAlbums;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import com.csrbrantford.csrbrantfordapp.R;

/**
 * Created by Jonathan on 10/25/2016.
 */

class PhotoAlbumAdapter extends BaseAdapter {

    private Activity activity;
    private ArrayList data;
    private static LayoutInflater inflater;

    PhotoAlbumAdapter(Activity activity, ArrayList data){
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
    public View getView(int position, View convertView, ViewGroup parent) {
        PhotoAlbum photoAlbum;
        View row = convertView;
        PhotoAlbumsHolder holder;

        if(row == null){
            row = inflater.inflate(R.layout.photo_album_row_layout, parent, false);

            holder = new PhotoAlbumsHolder();
            holder.photoAlbumTitle = (TextView)row.findViewById(R.id.photoAlbumTitle);
            holder.photoAlbumDescription = (TextView)row.findViewById(R.id.photoAlbumDesc);
            holder.viewPhotoAlbum = (ImageView)row.findViewById(R.id.viewPhotoAlbum);

            row.setTag(holder);
        } else {
            holder = (PhotoAlbumsHolder)row.getTag();
        }

        if(data.size() <= 0){
            holder.photoAlbumTitle.setText(activity.getString(R.string.no_photos));
        } else {
            photoAlbum = (PhotoAlbum)data.get(position);

            holder.photoAlbumTitle.setText(photoAlbum.getTitle());
            holder.photoAlbumDescription.setText(photoAlbum.getDescription());
            holder.viewPhotoAlbum.setBackground(photoAlbum.getViewAlbum());

            row.setOnClickListener(new OnItemClickListener(position));
        }

        return row;
    }

    private static class PhotoAlbumsHolder{
        TextView photoAlbumTitle;
        TextView photoAlbumDescription;
        ImageView viewPhotoAlbum;
    }

    private class OnItemClickListener implements OnClickListener {
        private int mPosition;

        OnItemClickListener(int mPosition){
            this.mPosition = mPosition;
        }

        @Override
        public void onClick(View view) {
            PhotoAlbums photoAlbums = (PhotoAlbums)activity;
            photoAlbums.onItemClick(mPosition);
        }
    }
}
