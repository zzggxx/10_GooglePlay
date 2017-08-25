package com.itcast.googleplay.global;

import android.app.Application;
import android.content.Context;
import android.os.Handler;

/**
 * $$$$使用继承appalication,并且修改配置程序的入口函数application的onCreate()肯定是先于activity中的oncreate()
 * 工具类,模板代码
 * Created by Lenovo on 2016/7/27.
 */
public class GooglePlayApplication extends Application {

    public static Context context;
    /**
     * 主线程的消息处理
     */
    public static Handler mHandler;

    @Override
    public void onCreate() {
        super.onCreate();
        //三大上下文
        context = this;
        mHandler = new Handler();
//线程创建的消息的方法,一般我们是使用不到子线程的handler的
/*        new Thread(new Runnable() {
            @Override
            public void run() {
                Handler handler = new Handler();
                Looper.prepare(); //创建轮询器
                Looper.loop();  //开启轮训器
                handler.sendEmptyMessage(0);
            }
        }).start();*/
    }
}
