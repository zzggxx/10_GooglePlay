package com.itcast.googleplay.adapter;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.itcast.googleplay.ImageScaleActivity;
import com.itcast.googleplay.R;
import com.itcast.googleplay.bean.AppInfo;
import com.itcast.googleplay.global.GooglePlayApplication;
import com.itcast.googleplay.holder.BaseHolder;
import com.itcast.googleplay.http.Api;
import com.itcast.googleplay.utils.DensityUtil;

import java.util.ArrayList;

import it.sephiroth.android.library.picasso.Picasso;

/**
 * Created by Lenovo on 2016/8/2.
 */
public class AppScreenHolder extends BaseHolder<AppInfo> {

    private LinearLayout ll_screen;

    @Override
    protected View initHolderView() {
        View view = View.inflate(GooglePlayApplication.context, R.layout.layout_detail_app_screen, null);
        ll_screen = (LinearLayout) view.findViewById(R.id.ll_screen);
        return view;
    }

    @Override
    public void bindData(AppInfo appInfo) {
        int width = DensityUtil.dip2px(GooglePlayApplication.context, 90);
        int height = DensityUtil.dip2px(GooglePlayApplication.context, 150);
        int margin = DensityUtil.dip2px(GooglePlayApplication.context, 8);

        final ArrayList<String> screen = appInfo.getScreen();
        for (int i = 0; i < screen.size(); i++) {
            ImageView imageView = new ImageView(GooglePlayApplication.context);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(width, height);
            layoutParams.leftMargin = (i == 0 ? 0 : margin);
            imageView.setLayoutParams(layoutParams);

            Picasso.with(GooglePlayApplication.context).load(Api.IMAGE_PREFIX + screen.get(i)).into(imageView);

            ll_screen.addView(imageView);

            final int finalI = i;
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    /**
                     * $$$$$使用任务栈的模式,记住!
                     */
                    Intent intent = new Intent(GooglePlayApplication.context, ImageScaleActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putStringArrayListExtra("urlList", screen);
                    intent.putExtra("currentItem", finalI);     //传入i的时候提示的自动记录i的生成.
                    GooglePlayApplication.context.startActivity(intent);
                }
            });
        }
    }
}
