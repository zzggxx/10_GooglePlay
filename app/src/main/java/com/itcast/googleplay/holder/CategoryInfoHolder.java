package com.itcast.googleplay.holder;

import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.itcast.googleplay.R;
import com.itcast.googleplay.bean.CategoryInfo;
import com.itcast.googleplay.global.GooglePlayApplication;
import com.itcast.googleplay.http.Api;

import it.sephiroth.android.library.picasso.Picasso;

/**
 * Created by Lenovo on 2016/7/29.
 */
public class CategoryInfoHolder extends BaseHolder<Object> {

    private ImageView iv_image1;
    private ImageView iv_image2;
    private ImageView iv_image3;
    private TextView tv_name1;
    private TextView tv_name2;
    private TextView tv_name3;

    @Override
    protected View initHolderView() {
        View view = View.inflate(GooglePlayApplication.context, R.layout.adapter_category_info, null);
        iv_image1 = (ImageView) view.findViewById(R.id.iv_image1);
        iv_image2 = (ImageView) view.findViewById(R.id.iv_image2);
        iv_image3 = (ImageView) view.findViewById(R.id.iv_image3);
        tv_name1 = (TextView) view.findViewById(R.id.tv_name1);
        tv_name2 = (TextView) view.findViewById(R.id.tv_name2);
        tv_name3 = (TextView) view.findViewById(R.id.tv_name3);
        return view;
    }

    @Override
    public void bindData(Object o) {

        CategoryInfo info = (CategoryInfo) o;
        //设置文字,图片加载.第一个必然是有内容的,若是没有的话,就不会有集合的
        tv_name1.setText(info.getName1());

        Picasso.with(GooglePlayApplication.context).
                load(Api.IMAGE_PREFIX + info.getUrl1()).
                placeholder(R.drawable.action_download).
                error(R.drawable.ic_error_page).
                into(iv_image1);
        //判断第二个是不是为空,在复用的时候,若是不为空必须的将其设置为可见
        if (!TextUtils.isEmpty(info.getName2())) {

            ((ViewGroup) tv_name2.getParent()).setVisibility(View.VISIBLE);

            tv_name2.setText(info.getName2());

            Picasso.with(GooglePlayApplication.context).
                    load(Api.IMAGE_PREFIX + info.getUrl2()).
                    placeholder(R.drawable.action_download).
                    error(R.drawable.ic_error_page).
                    into(iv_image2);
        } else {
            ((ViewGroup) tv_name2.getParent()).setVisibility(View.INVISIBLE);
        }
        //判断第三个条目
        if (!TextUtils.isEmpty(info.getName3())) {

            ((ViewGroup) tv_name3.getParent()).setVisibility(View.VISIBLE);

            tv_name3.setText(info.getName3());

            Picasso.with(GooglePlayApplication.context).
                    load(Api.IMAGE_PREFIX + info.getUrl3()).
                    placeholder(R.drawable.action_download).
                    error(R.drawable.ic_error_page).
                    into(iv_image3);
        } else {
            ((ViewGroup) tv_name3.getParent()).setVisibility(View.INVISIBLE);
        }
    }
}
