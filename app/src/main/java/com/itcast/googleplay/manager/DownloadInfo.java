package com.itcast.googleplay.manager;

import com.itcast.googleplay.bean.AppInfo;

import java.io.File;

/**
 * 下载任务的封装,包括下载的数据,标识,进度,状态,文件保存的路径
 * Created by Lenovo on 2016/8/4.
 */
public class DownloadInfo {
    private long id;//下载的唯一标识
    private int state;//状态
    private long currentLength;//进度
    private long size;//大小
    private String downloadUrl;//请求的路径
    private String path;//保存的路径


    /**
     * 使用appinfo创建下载的对象DownloadInfo
     *
     * @param appInfo
     * @return
     */
    public static DownloadInfo create(AppInfo appInfo) {

        DownloadInfo downloadInfo = new DownloadInfo();

        downloadInfo.setId(appInfo.getId());
        downloadInfo.setState(DownloadManager.STATE_NONE);  //初始化状态
        downloadInfo.setCurrentLength(0);
        downloadInfo.setSize(appInfo.getSize());
        downloadInfo.setDownloadUrl(appInfo.getDownloadURl());
        downloadInfo.setPath(DownloadManager.DOWNLOAD_DIR + File.separator + appInfo.getName() + ".apk");

        return downloadInfo;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public long getCurrentLength() {
        return currentLength;
    }

    public void setCurrentLength(long currentLength) {
        this.currentLength = currentLength;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }


}
