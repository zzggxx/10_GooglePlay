package com.itcast.googleplay.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.itcast.googleplay.global.GooglePlayApplication;
import com.itcast.googleplay.http.Api;

import java.util.ArrayList;

import it.sephiroth.android.library.picasso.Picasso;
import uk.co.senab.photoview.PhotoView;

/**
 * Created by Lenovo on 2016/8/2.
 */
public class ImageScaleAdapter extends PagerAdapter {
    public ImageScaleAdapter(ArrayList<String> list) {
        this.list = list;
    }

    private ArrayList<String> list;

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        PhotoView imageView = new PhotoView(GooglePlayApplication.context);
        Picasso.with(GooglePlayApplication.context).load(Api.IMAGE_PREFIX + list.get(position)).into(imageView);
        container.addView(imageView);
        return imageView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        //super.destroyItem(container, position, object);
        container.removeView((View) object);
    }
}
