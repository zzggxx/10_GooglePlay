package com.itcast.googleplay.holder;

import android.view.View;
import android.widget.TextView;

import com.itcast.googleplay.R;
import com.itcast.googleplay.global.GooglePlayApplication;

/**
 * Created by Lenovo on 2016/7/29.
 */
public class CategoryTitleHolder extends BaseHolder {

    private TextView textView;

    @Override
    protected View initHolderView() {
        View view = View.inflate(GooglePlayApplication.context, R.layout.adapter_category_title, null);
        textView = (TextView) view.findViewById(R.id.tv_title);
        return view;
    }

    @Override
    public void bindData(Object o) {
        textView.setText((String) o);
    }
}
