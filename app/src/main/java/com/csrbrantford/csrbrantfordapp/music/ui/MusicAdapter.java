package com.csrbrantford.csrbrantfordapp.music.ui;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;


import com.csrbrantford.csrbrantfordapp.R;
import com.csrbrantford.csrbrantfordapp.music.ui.Music;

import java.util.ArrayList;

/**
 * Created by Jonathan on 7/8/2016.
 */
public class MusicAdapter extends BaseAdapter {

    private Activity activity;
    private ArrayList data;
    private static LayoutInflater inflater = null;
    public Resources res;
    Song song;

    public MusicAdapter(Activity activity, ArrayList data, Resources res) {
        this.activity = activity;
        this.data = data;
        this.res = res;

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
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        SongsHolder holder;

        if(row == null){
            row = inflater.inflate(R.layout.music_row_layout, parent, false);

            holder = new SongsHolder();
            holder.songTitle = row.findViewById(R.id.songName);
            holder.songLength = row.findViewById(R.id.songLength);
            holder.playSong = row.findViewById(R.id.playSongButton);

            row.setTag(holder);
        } else {
            holder = (SongsHolder)row.getTag();
        }

        if(data.size() <= 0){
            holder.songTitle.setText("No Songs Available");
        } else {
            song = null;
            song = (Song) data.get(position);

            holder.songTitle.setText(song.getTitle());
            holder.songLength.setText(song.getLength());
            holder.playSong.setBackground(song.getPlaySong());
            holder.playSong.setOnClickListener(new OnItemClickListener(position));
        }

        row.setOnClickListener(new OnItemClickListener(position));

        return row;
    }

    private class SongsHolder {
        TextView songTitle;
        TextView songLength;
        ImageButton playSong;
    }

    private class OnItemClickListener implements View.OnClickListener {
        private int mPosition;

        OnItemClickListener(int position){
            mPosition = position;
        }

        @Override
        public void onClick(View view) {
            Music music = (Music)activity;
            music.onItemClick(mPosition);
        }
    }
}
