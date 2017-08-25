package com.itcast.googleplay.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;
import android.widget.BaseAdapter;

import com.itcast.googleplay.holder.BaseHolder;
import com.nineoldandroids.view.ViewHelper;
import com.nineoldandroids.view.ViewPropertyAnimator;

import java.util.ArrayList;

/**
 * Created by Lenovo on 2016/7/28.
 */
public abstract class BaseicAdapter<T> extends BaseAdapter {
    /**
     * 需要的数据及其各个参数设置项,已经很熟悉,无需纠结;
     */
    public ArrayList<T> list;

    public BaseicAdapter(ArrayList<T> list) {
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public T getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * 模拟的是listView的界面进行的抽取
     *
     * @param position
     * @param convertView
     * @param parent
     * @return
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        BaseHolder hodler;
        if (convertView == null) {
            hodler = getHolder(position);   //需要的是一个不固定的Holder,有需要适配器的一方创建
        } else {
            hodler = (BaseHolder) convertView.getTag();
        }

        hodler.bindData(list.get(position));    //$$$$绑定数据,调用数据的源头

        //***给item增加炫酷的效果,开始的时候缩小的,后边再增大,使用nineoldandroids-2.4.0
        //是单个条目的效果,在这里可以不需要的
        ViewHelper.setScaleX(hodler.getHolderView(), 0.5f);
        ViewHelper.setScaleY(hodler.getHolderView(), 0.5f);
        ViewPropertyAnimator.animate(hodler.getHolderView()).scaleX(1f).setDuration(400)
                .setInterpolator(new OvershootInterpolator()).start();
        ViewPropertyAnimator.animate(hodler.getHolderView()).scaleY(1f).setDuration(400)
                .setInterpolator(new OvershootInterpolator()).start();

        return hodler.getHolderView();
    }

    /**
     * 传入position是为了程序的扩展性,为了不同的子类条目创建.
     *
     * @param position
     * @return
     */
    public abstract BaseHolder getHolder(int position);


}
