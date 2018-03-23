package com.csrbrantford.csrbrantfordapp.photoAlbums;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.util.FloatMath;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.RequestQueue;

import com.bosong.frescozoomablelib.zoomable.ZoomableDraweeView;
import com.csrbrantford.csrbrantfordapp.R;
import com.facebook.drawee.view.SimpleDraweeView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import java.util.ArrayList;

import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * Created by Jonathan on 10/29/2016.
 */

class PhotoPagerAdapter extends PagerAdapter{

    private ArrayList<Photo> images;
    private View v;
    private LayoutInflater inflater;
    private ZoomableDraweeView mImageView;
    private Activity activity;
    private ImageLoader imageLoader;
    private DisplayImageOptions options;
    private ProgressBar progressBar;

    PhotoPagerAdapter(ArrayList<Photo> images, Activity activity, DisplayImageOptions options) {
        super();
        this.images = images;
        this.activity = activity;
        ImageLoaderConfiguration configuration = new ImageLoaderConfiguration.Builder(activity).build();
        ImageLoader.getInstance().init(configuration);
        imageLoader = ImageLoader.getInstance();
        this.options = options;
    }

    @Override
    public Object instantiateItem(final ViewGroup container, int position) {
        inflater = LayoutInflater.from(activity);
        v = inflater.inflate(R.layout.photo_pager_layout, container, false);

        String url = images.get(position).getPhotoUrl().replaceAll(activity.getString(R.string.flickr_photo_link_fifth), activity.getString(R.string.flickr_photo_link_sixth));
        mImageView = (ZoomableDraweeView) v.findViewById(R.id.photo);
        progressBar = (ProgressBar) v.findViewById(R.id.pbHeaderProgress);
        PhotoViewAttacher pAttacher;
        pAttacher = new PhotoViewAttacher(mImageView);
        pAttacher.update();


        imageLoader.displayImage(url, mImageView, options, new SimpleImageLoadingListener() {
            @Override
            public void onLoadingStarted(String imageUri, View view) {
                progressBar.setVisibility(View.VISIBLE);
            }

            @Override
            public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                String message = null;
                switch (failReason.getType()) {
                    case IO_ERROR:
                        message = "Input/Output error";
                        break;
                    case DECODING_ERROR:
                        message = "Image can't be downloaded";
                        break;
                    case NETWORK_DENIED:
                        message = "Downloads are denied";
                        break;
                    case OUT_OF_MEMORY:
                        message = "Out Of Memory error";
                        break;
                    case UNKNOWN:
                        message = "Unknown error";
                        break;
                }

                Toast.makeText(v.getContext(), message, Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                progressBar.setVisibility(View.GONE);
            }
        });

        container.addView(v, 0);
        return v;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }

    @Override
    public int getCount() {
        return images.size();
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View)object);
    }

    @Override
    public Parcelable saveState() {
        return null;
    }
}