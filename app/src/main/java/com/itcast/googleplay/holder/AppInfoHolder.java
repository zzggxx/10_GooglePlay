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
 * Created by Lenovo on 2016/8/2.
 */
public class AppInfoHolder extends BaseHolder<AppInfo> {

    public ImageView iv_icon;
    public RatingBar rb_star;
    public TextView tv_name, tv_download_num, tv_version, tv_date, tv_size;

    @Override
    protected View initHolderView() {
        View view = View.inflate(GooglePlayApplication.context, R.layout.layout_detail_app_info, null);
        iv_icon = (ImageView) view.findViewById(R.id.iv_icon);
        rb_star = (RatingBar) view.findViewById(R.id.rb_star);
        tv_name = (TextView) view.findViewById(R.id.tv_name);
        tv_download_num = (TextView) view.findViewById(R.id.tv_download_num);
        tv_version = (TextView) view.findViewById(R.id.tv_version);
        tv_date = (TextView) view.findViewById(R.id.tv_date);
        tv_size = (TextView) view.findViewById(R.id.tv_size);
        return view;
    }

    @Override
    public void bindData(AppInfo appInfo) {
        Picasso.with(GooglePlayApplication.context).load(Api.IMAGE_PREFIX + appInfo.getIconUrl()).into(iv_icon);
        tv_name.setText(appInfo.getName());
        rb_star.setRating(appInfo.getStars());
        tv_download_num.setText("下载：" + appInfo.getDownloadNum());
        tv_version.setText("版本：" + appInfo.getVersion());
        tv_date.setText("日期：" + appInfo.getDate());
        tv_size.setText("大小：" + Formatter.formatFileSize(GooglePlayApplication.context, appInfo.getSize()));
    }
}
