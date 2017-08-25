package com.itcast.googleplay.holder;

import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.itcast.googleplay.R;
import com.itcast.googleplay.bean.AppInfo;
import com.itcast.googleplay.global.GooglePlayApplication;
import com.itcast.googleplay.manager.DownloadInfo;
import com.itcast.googleplay.manager.DownloadManager;

/**
 * Created by Lenovo on 2016/8/2.
 */
public class AppDownloadHolder extends BaseHolder<AppInfo> implements View.OnClickListener, DownloadManager.DownloadObserver {
    private ProgressBar pb_progress;
    private Button btn_download;

    @Override
    protected View initHolderView() {
        View view = View.inflate(GooglePlayApplication.context, R.layout.layout_detail_app_download, null);
        pb_progress = (ProgressBar) view.findViewById(R.id.pb_progress);
        btn_download = (Button) view.findViewById(R.id.btn_download);

        btn_download.setOnClickListener(this);
        //注册监听
        DownloadManager.getInstance().registerDownloadObserver(this);

        return view;
    }

    private AppInfo appInfo;

    @Override
    public void bindData(AppInfo appInfo) {
        this.appInfo = appInfo;

        //修复bug,暂停再进来会导致UI的错误,所以应该是手动获取状态
        DownloadInfo downloadInfo = DownloadManager.getInstance().getDownloadInfo(appInfo);
        if (downloadInfo != null) {
            updateDownloadState(downloadInfo);
        }
    }

    /**
     * 下载的点击事件
     *
     * @param view
     */
    @Override

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_download:
                //每一次进入一个界面就是一个新的appinfo,但是管理的类是单一的.
                DownloadInfo downloadInfo = DownloadManager.getInstance().getDownloadInfo(appInfo);
                if (downloadInfo == null) {
                    DownloadManager.getInstance().download(appInfo);
                } else {
                    if (downloadInfo.getState() == DownloadManager.STATE_DOWNLOADING
                            || downloadInfo.getState() == DownloadManager.STATE_WAITING) {
                        DownloadManager.getInstance().pause(appInfo);
                    } else if (downloadInfo.getState() == DownloadManager.STATE_PAUSE
                            || downloadInfo.getState() == DownloadManager.STATE_ERROR) {
                        DownloadManager.getInstance().download(appInfo);
                    } else if (downloadInfo.getState() == DownloadManager.STATE_FINISH) {
                        DownloadManager.getInstance().installApk(appInfo);
                    }
                }
                break;
        }
    }

    /**
     * 根据当前的状态更改UI
     *
     * @param downloadInfo
     */
    private void updateDownloadState(DownloadInfo downloadInfo) {
        if (appInfo == null || appInfo.getId() != downloadInfo.getId()) {
            return;
        }
        switch (downloadInfo.getState()) {
            case DownloadManager.STATE_PAUSE:
                btn_download.setText("继续下载");
                float percent = downloadInfo.getCurrentLength() * 100f / downloadInfo.getSize();
                pb_progress.setProgress((int) percent);
                btn_download.setBackgroundResource(0);
                break;
            case DownloadManager.STATE_FINISH:
                btn_download.setText("安装");
                break;
            case DownloadManager.STATE_WAITING:
                btn_download.setText("等待中");
                break;
            case DownloadManager.STATE_ERROR:
                btn_download.setText("出错,重下");
                break;
        }
    }


    /**
     * 下载状态的改变
     *
     * @param downloadInfo
     */
    @Override
    public void onDownloadStateChange(DownloadInfo downloadInfo) {
        updateDownloadState(downloadInfo);
    }

    /**
     * 进度的改变
     *
     * @param downloadInfo
     */
    @Override
    public void onDownloadProgressChange(DownloadInfo downloadInfo) {
        if (appInfo == null || appInfo.getId() != downloadInfo.getId()) {
            return;
        }
        float percent = downloadInfo.getCurrentLength() * 100f / downloadInfo.getSize();
        pb_progress.setProgress((int) percent);
        btn_download.setBackgroundResource(0);
        btn_download.setText((int) percent + "%");
    }
}
