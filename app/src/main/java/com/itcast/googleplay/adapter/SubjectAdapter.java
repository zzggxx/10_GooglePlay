package com.itcast.googleplay.adapter;

import com.itcast.googleplay.holder.BaseHolder;
import com.itcast.googleplay.holder.SubjectHolder;

import java.util.ArrayList;

/**
 * Created by Lenovo on 2016/7/29.
 */
public class SubjectAdapter extends BaseicAdapter {

    public SubjectAdapter(ArrayList list) {
        super(list);
    }

    @Override
    public BaseHolder getHolder(int position) {
        return new SubjectHolder();
    }
}
