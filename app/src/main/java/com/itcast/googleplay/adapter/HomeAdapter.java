package com.itcast.googleplay.adapter;

import com.itcast.googleplay.bean.AppInfo;
import com.itcast.googleplay.holder.BaseHolder;
import com.itcast.googleplay.holder.HomeHolder;

import java.util.ArrayList;

/**
 * Created by Lenovo on 2016/7/28.
 */
public class HomeAdapter extends BaseicAdapter<AppInfo> {

    public HomeAdapter(ArrayList<AppInfo> list) {
        super(list);
    }

    @Override
    public BaseHolder<AppInfo> getHolder(int position) {
        return new HomeHolder();
    }
}
