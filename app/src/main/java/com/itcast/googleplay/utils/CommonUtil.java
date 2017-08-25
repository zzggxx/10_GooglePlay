package com.itcast.googleplay.utils;

import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import com.itcast.googleplay.global.GooglePlayApplication;

/**
 * Created by Lenovo on 2016/7/27.
 */
public class CommonUtil {
    /**
     * $$在主线程执行一段子线程的任务,最常见的就是更新UI
     *
     * @param r Runnnable接口
     */
    public static void runOnUIThread(Runnable r) {
        GooglePlayApplication.mHandler.post(r);
    }

    /**
     * 通过父类移除自己
     *
     * @param view
     */
    public static void removeSelfFromParent(View view) {
        if (view != null) {
            ViewParent parent = view.getParent();
            if (parent != null && parent instanceof ViewGroup) {
                ViewGroup group = (ViewGroup) parent;
                group.removeView(view);
            }
        }
    }

    /**
     * $$$$$$$$获取资源文件(五大资源),方便用在无上下文处,适配器,供其他的工具类等.
     *
     * @param id
     * @return
     */
    public static Drawable getDrawable(int id) {
        return GooglePlayApplication.context.getResources().getDrawable(id);
    }

    public static String getString(int id) {
        return GooglePlayApplication.context.getResources().getString(id);
    }

    /**
     * 利用的全局变变量获取的是资源文件,这个获取的字符串数组,还可能是int类型的数组
     *
     * @param id 字符串数组的名称,比如R.array.tab_names
     * @return String[]
     */
    public static String[] getStringArray(int id) {
        return GooglePlayApplication.context.getResources().getStringArray(id);
    }

    public static int getColor(int id) {
        return GooglePlayApplication.context.getResources().getColor(id);
    }

    /**
     * 获取dp资源，并且会自动将dp值转为px值,有屏幕适配的特点
     *
     * @param id
     * @return
     */
    public static int getDimens(int id) {
        return GooglePlayApplication.context.getResources().getDimensionPixelSize(id);
    }
}
