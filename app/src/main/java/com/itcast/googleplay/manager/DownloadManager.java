package com.itcast.googleplay.manager;

import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.util.SparseArray;

import com.itcast.googleplay.bean.AppInfo;
import com.itcast.googleplay.global.GooglePlayApplication;
import com.itcast.googleplay.http.Api;
import com.itcast.googleplay.http.HttpUtils;
import com.itcast.googleplay.utils.CommonUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * 下载的一个管理类
 * Created by Lenovo on 2016/8/4.
 */
public class DownloadManager {

    /**
     * $$$$定义下载目录 : /mnt/sdcard/包名/download,每一个程序再开始的时候就分好了目录,也不排除在其他的时候分配,自己定义在自己的私有包下
     */
    public static String DOWNLOAD_DIR = Environment.getExternalStorageDirectory().getPath()
            + File.separator + GooglePlayApplication.context.getPackageName() + File.separator
            + "download";

    //定义常量
    public static final int STATE_NONE = 0;//未下载的状态
    public static final int STATE_DOWNLOADING = 1;//下载中
    public static final int STATE_PAUSE = 2;//暂停
    public static final int STATE_WAITING = 3;//等待中,任务对象已经创建
    public static final int STATE_FINISH = 4;//下载完成
    public static final int STATE_ERROR = 5;//下载出错

    private static DownloadManager mInstance = new DownloadManager();

    public static DownloadManager getInstance() {
        return mInstance;
    }


    /**
     * 此处是在进行内存性的存贮并非是持久化的存储,用来存放所有任务的DownloadInfo数据
     */
    private SparseArray<DownloadInfo> downloadInfoMap = new SparseArray<DownloadInfo>();

    /**
     * 利用构造方法初始化下载的目录,一般是在程序入口的时候创建
     */
    private DownloadManager() {
        File file = new File(DOWNLOAD_DIR);
        if (!file.exists()) {
            file.mkdirs();
        }
        //System.out.println(file.toString());
    }

    /**
     * 定义下载的方法,有对象downloadinfo才能下载,所以传入AppInfo.
     */
    public void download(AppInfo appInfo) {
        //1,获取下载任务的对象
        DownloadInfo downloadInfo = downloadInfoMap.get((int) appInfo.getId());
        if (downloadInfo == null) {
            downloadInfo = DownloadInfo.create(appInfo);
            downloadInfoMap.put((int) downloadInfo.getId(), downloadInfo);
        }
        //2,获取下载任务的state来判断是否能够进行下载,有三种情况可以进行下载,none,pause,error
        int state = downloadInfo.getState();
        if (state == STATE_NONE || state == STATE_PAUSE || state == STATE_ERROR) {
            //3.交给线程池进行管理
            DownloadTask downloadTask = new DownloadTask(downloadInfo);

            downloadInfo.setState(STATE_WAITING);
            notifyDownloadStateChange(downloadInfo);

            ThreadPoolManager.getInstance().execute(downloadTask);
        }
    }


    /**
     * 将下载文件和保存文件的逻辑都提取出来,直接是扔给线程池进行的管理的,我们不再管理
     */
    private class DownloadTask implements Runnable {

        private DownloadInfo downloadInfo;

        public DownloadTask(DownloadInfo downloadInfo) {      //需要拿不到就构造方法
            this.downloadInfo = downloadInfo;
        }

        @Override
        public void run() {
            //4,run方法一执行就将state改变
            downloadInfo.setState(STATE_DOWNLOADING);
            notifyDownloadStateChange(downloadInfo);

            //5,判断下载的类型,a从头开始下载,b断点下载
            HttpUtils.HttpResult httpResult = null;
            File file = new File(downloadInfo.getPath());

            //System.out.println(file.toString());

            if (!file.exists() || file.length() != downloadInfo.getCurrentLength()) {   //不存在或者是保存出错
                //从头下载
                file.delete();
                downloadInfo.setCurrentLength(0);

                String url = String.format(Api.Download, downloadInfo.getDownloadUrl());
                httpResult = HttpUtils.download(url);

                //System.out.println(url);
                //System.out.println(downloadInfo);
                //System.out.println(downloadInfo.getDownloadUrl());

            } else {
                //进行断点下载
                String url = String.format(Api.Break_Download,
                        downloadInfo.getDownloadUrl(), downloadInfo.getCurrentLength());
                httpResult = HttpUtils.download(url);
            }

            System.out.println(httpResult);
            System.out.println(httpResult.getInputStream());

            //6读入流,写入文件
            if (httpResult != null && httpResult.getInputStream() != null) {
                //请求成功
                InputStream is = httpResult.getInputStream();
                FileOutputStream fos = null;
                try {
                    fos = new FileOutputStream(file, true);
                    byte[] buffer = new byte[1024 * 8];
                    int len = -1;
                    while ((len = is.read(buffer)) != -1 && downloadInfo.getState() == STATE_DOWNLOADING) {
                        fos.write(buffer, 0, len);

                        downloadInfo.setCurrentLength(downloadInfo.getCurrentLength() + len);
                        notifyDownloadProgressChange(downloadInfo);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    processDownloadError(file); //出异常就是下载失败
                } finally {
                    httpResult.close();
                    try {
                        if (fos != null) {
                            fos.close();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                //7,走到这里不是下载完成就是暂停的时候
                if (file.length() == downloadInfo.getSize() && downloadInfo.getState() == STATE_DOWNLOADING) {
                    downloadInfo.setState(STATE_FINISH);
                    notifyDownloadStateChange(downloadInfo);
                } else if (downloadInfo.getState() == STATE_PAUSE) {
                    notifyDownloadStateChange(downloadInfo);
                }
            } else {
                //请求失败
                processDownloadError(file);
            }
        }

        /**
         * 下载失败了是要进行处理的
         *
         * @param file
         */
        private void processDownloadError(File file) {
            file.delete();
            downloadInfo.setCurrentLength(0);
            downloadInfo.setState(STATE_ERROR);
            notifyDownloadStateChange(downloadInfo);
        }

    }

    /**
     * 下载的进度和状态的改变的回调不能使用单个的监听器,因为界面比较多(每个界面的观察者不一样),应该使用集合
     */
    public interface DownloadObserver {

        void onDownloadStateChange(DownloadInfo downloadInfo);

        void onDownloadProgressChange(DownloadInfo downloadInfo);
    }

    /**
     * 存放所有界面的监听器集合
     */
    private ArrayList<DownloadManager.DownloadObserver> observerList = new ArrayList<DownloadManager.DownloadObserver>();


    /**
     * 注册下载观察者
     *
     * @param downloadObserver
     */
    public void registerDownloadObserver(DownloadObserver downloadObserver) {
        if (!observerList.contains(downloadObserver)) {
            observerList.add(downloadObserver);
        }
    }

    /**
     * 移除下载观察者
     *
     * @param downloadObserver
     */
    public void unregisterDownloadObserver(DownloadObserver downloadObserver) {
        if (observerList.contains(downloadObserver)) {
            observerList.remove(downloadObserver);
        }
    }

    /**
     * 通知所有的监听器状态更改了,不不知道还会使用什么数据,就传达的数据
     *
     * @param downloadInfo
     */
    private void notifyDownloadStateChange(final DownloadInfo downloadInfo) {
        CommonUtil.runOnUIThread(new Runnable() {
            @Override
            public void run() {
                for (DownloadObserver observer : observerList) {
                    observer.onDownloadStateChange(downloadInfo);
                }
            }
        });
    }

    /**
     * 通知所有的监听器下载的进度更改了
     *
     * @param downloadInfo
     */
    private void notifyDownloadProgressChange(final DownloadInfo downloadInfo) {
        CommonUtil.runOnUIThread(new Runnable() {
            @Override
            public void run() {
                for (DownloadObserver observer : observerList) {
                    observer.onDownloadProgressChange(downloadInfo);
                }
            }
        });
    }

    /**
     * 由AppInfo获取相应的DownloadInfo
     *
     * @param appInfo
     * @return
     */
    public DownloadInfo getDownloadInfo(AppInfo appInfo) {
        return downloadInfoMap.get((int) appInfo.getId());
    }

    /**
     * 暂停的方法
     */
    public void pause(AppInfo appInfo) {
        DownloadInfo downloadInfo = getDownloadInfo(appInfo);
        if (downloadInfo != null) {
            downloadInfo.setState(STATE_PAUSE);
            notifyDownloadStateChange(downloadInfo);
        }
    }

    /**
     * 安装app的方法
     */
    public void installApk(AppInfo appInfo) {
        DownloadInfo downloadInfo = getDownloadInfo(appInfo);
        if (downloadInfo != null) {
            /*<action android:name="android.intent.action.VIEW" />
            <category android:name="android.intent.category.DEFAULT" />
            <data android:scheme="content" />
            <data android:scheme="file" />
            <data android:mimeType="application/vnd.android.package-archive" />*/

            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);//开启新的任务栈来存放新的Activity
            intent.setDataAndType(Uri.parse("file://" + downloadInfo.getPath()), "application/vnd.android.package-archive");
            GooglePlayApplication.context.startActivity(intent);
        }
    }


}
