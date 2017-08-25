package com.itcast.googleplay.holder;

import android.text.format.Formatter;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.itcast.googleplay.R;
import com.itcast.googleplay.bean.AppInfo;
import com.itcast.googleplay.global.GooglePlayApplication;
import com.itcast.googleplay.http.Api;

import it.sephiroth.android.library.picasso.Picasso;

/**
 * Created by Lenovo on 2016/7/28.
 */
public class HomeHolder extends BaseHolder<AppInfo> {

    ImageView iv_icon;
    RatingBar rb_star;
    TextView tv_name, tv_size, tv_des;

    /**
     * 填充布局,注入的话使用两个参数
     *
     * @return
     */
    @Override
    protected View initHolderView() {

        View view = View.inflate(GooglePlayApplication.context, R.layout.adapter_home, null);

        iv_icon = (ImageView) view.findViewById(R.id.iv_icon);
        rb_star = (RatingBar) view.findViewById(R.id.rb_star);
        tv_name = (TextView) view.findViewById(R.id.tv_name);
        tv_size = (TextView) view.findViewById(R.id.tv_size);
        tv_des = (TextView) view.findViewById(R.id.tv_des);

        return view;
    }

    /**
     * 数据绑定就是数据的填充,尽量使用的最新的Picasso.
     *
     * @param appInfo
     */
    @Override
    public void bindData(AppInfo appInfo) {

        tv_name.setText(appInfo.getName());
        rb_star.setRating(appInfo.getStars());
        tv_size.setText(Formatter.formatFileSize(GooglePlayApplication.context, appInfo.getSize()));
        tv_des.setText(appInfo.getDes());

        //**图片的简单设置
        Picasso.with(GooglePlayApplication.context).
                load(Api.IMAGE_PREFIX + appInfo.getIconUrl()).
                centerCrop().
                resize(200, 200).
                placeholder(R.drawable.action_download).
                error(R.drawable.ic_error_page).
                into(iv_icon);

    }
}
