package com.itcast.googleplay;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.itcast.googleplay.adapter.AppScreenHolder;
import com.itcast.googleplay.bean.AppInfo;
import com.itcast.googleplay.global.GooglePlayApplication;
import com.itcast.googleplay.holder.AppDesHolder;
import com.itcast.googleplay.holder.AppDownloadHolder;
import com.itcast.googleplay.holder.AppInfoHolder;
import com.itcast.googleplay.holder.AppSafeholder;
import com.itcast.googleplay.http.Api;
import com.itcast.googleplay.http.HttpUtils;
import com.itcast.googleplay.ui.fragment.LoadingPager;
import com.itcast.googleplay.utils.CommonUtil;
import com.itcast.googleplay.utils.JsonUtil;

public class AppDetailActivity extends AppCompatActivity {

    private String packageName;

    private AppInfo appInfo;

    private AppInfoHolder appInfoHolder;
    private FrameLayout fl_download;
    private ScrollView scrollView;
    private LinearLayout holder_container;
    private AppSafeholder appSafeholder;
    private AppScreenHolder appScreenHolder;

    public Toolbar toolbar;
    private AppDesHolder appDesHolder;
    private AppDownloadHolder appDownloadHolder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        packageName = getIntent().getStringExtra("packageName");

        LoadingPager loadingPager = new LoadingPager(this) {

            /**
             * **不初始化数据还不现实??????????????
             * @return
             */
            @Override
            public Object loadData() {
                return requestData();
            }

            @Override
            protected View createSuccessView() {
                View view = View.inflate(GooglePlayApplication.context, R.layout.activity_app_detail, null);
                holder_container = (LinearLayout) view.findViewById(R.id.holder_container);
                scrollView = (ScrollView) view.findViewById(R.id.scrollview);
                fl_download = (FrameLayout) view.findViewById(R.id.fl_download);
                toolbar = (Toolbar) view.findViewById(R.id.toolbar);
                //增加详情的布局AppInfo
                appInfoHolder = new AppInfoHolder();
                holder_container.addView(appInfoHolder.getHolderView());
                //增加安全的界面AppSafe
                appSafeholder = new AppSafeholder();
                holder_container.addView(appSafeholder.getHolderView());
                //增加水平的滚动图片页面
                appScreenHolder = new AppScreenHolder();
                holder_container.addView(appScreenHolder.getHolderView());
                //增加描述界面
                appDesHolder = new AppDesHolder();
                holder_container.addView(appDesHolder.getHolderView());
                appDesHolder.setScrollView(scrollView);
                //增加底部的下载界面
                appDownloadHolder = new AppDownloadHolder();
                fl_download.addView(appDownloadHolder.getHolderView());

                setToolBar();
                return view;
            }
        };
        setContentView(loadingPager);
    }

    /**
     * 请求数据
     *
     * @return
     */
    private Object requestData() {
        String url = String.format(Api.Detail, packageName);
        String json = HttpUtils.get(url);
        appInfo = JsonUtil.parseJsonToBean(json, AppInfo.class);
        if (appInfo != null) {
            CommonUtil.runOnUIThread(new Runnable() {
                @Override
                public void run() {
                    UpdateUI(appInfo);
                }
            });
        }
        return appInfo;
    }

    /**
     * 更新UI界面
     */
    private void UpdateUI(AppInfo appInfo) {
        appInfoHolder.bindData(appInfo);
        appSafeholder.bindData(appInfo);
        appScreenHolder.bindData(appInfo);
        appDesHolder.bindData(appInfo);
        appDownloadHolder.bindData(appInfo);
    }

    private void setToolBar() {

        toolbar.setLogo(R.drawable.ic_launcher);
        toolbar.setTitle(R.string.app_detail);
        toolbar.setTitleTextColor(Color.RED);


        setSupportActionBar(toolbar);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            default:

                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
