package com.itcast.googleplay.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.itcast.googleplay.global.GooglePlayApplication;
import com.itcast.googleplay.http.Api;

import java.util.ArrayList;

import it.sephiroth.android.library.picasso.Picasso;

/**
 * $$$$$$$$$ 也是可以将其抽取基类的,便于扩展的.
 * Created by Lenovo on 2016/7/29.
 */
public class HomeHeaderAdapter extends PagerAdapter {

    private ArrayList<String> list;

    public HomeHeaderAdapter(ArrayList<String> list) {
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size() * 10 * 1000;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        //动态的new和inflate都是可以的.注意映射的时候高度是忽略的,制定最小的高度.
        ImageView imageView = new ImageView(GooglePlayApplication.context);
        //$$$$强大的Picasso,继续探究使用方法.图片的内处理,除了基本的三级缓存和LruCatch之外的,比如图片的压缩,颜色的缩减等等.
        Picasso.with(GooglePlayApplication.context).
                load(Api.IMAGE_PREFIX + list.get(position % list.size())).
                into(imageView);

        container.addView(imageView);

        return imageView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        //super.destroyItem(container, position, object);
        container.removeView((View) object);
    }
}
