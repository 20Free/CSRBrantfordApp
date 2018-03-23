package com.csrbrantford.csrbrantfordapp.videos;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.csrbrantford.csrbrantfordapp.R;

import java.util.ArrayList;

/**
 * Created by Jonathan on 7/7/2016.
 */
class VideoAdapter extends BaseAdapter {

    private Activity activity;
    private ArrayList data;
    private static LayoutInflater inflater = null;


    VideoAdapter(Activity activity, ArrayList data) {
        this.activity = activity;
        this.data = data;

        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
        Video video;
        View row = convertView;
        VideosHolder holder;

        if(row == null) {
            row = inflater.inflate(R.layout.video_row_layout, parent, false);

            holder = new VideosHolder();
            holder.videoTitle = (TextView)row.findViewById(R.id.videoTitle);
            holder.videoDescription = (TextView)row.findViewById(R.id.videoDesc);
            holder.playVideo = (ImageView)row.findViewById(R.id.playVideo);

            row.setTag(holder);
        } else {
            holder = (VideosHolder)row.getTag();
        }

        if(data.size() <= 0){
            holder.videoTitle.setText(activity.getString(R.string.no_videos));
        } else {
            video = (Video) data.get(position);

            holder.videoTitle.setText(video.getTitle());
            holder.videoDescription.setText(video.getDescription());
            holder.playVideo.setBackground(video.getPlayVideo());

            row.setOnClickListener(new OnItemClickListener(position));
        }

        return row;
    }

    private static class VideosHolder {
        TextView videoTitle;
        TextView videoDescription;
        ImageView playVideo;
    }

    private class OnItemClickListener implements  OnClickListener {
        private int mPosition;

        OnItemClickListener(int position){
            mPosition = position;
        }

        @Override
        public void onClick(View view) {
            Videos videos = (Videos)activity;
            videos.onItemClick(mPosition);
        }
    }


}