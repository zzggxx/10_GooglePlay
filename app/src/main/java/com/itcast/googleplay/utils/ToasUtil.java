package com.itcast.googleplay.utils;

import android.widget.Toast;

import com.itcast.googleplay.global.GooglePlayApplication;

/**
 * Created by Lenovo on 2016/8/7.
 */
public class ToasUtil {


    //    //最佳方法
//    private static Toast toast;
//
//    public static void toast(String str) {
//        if (toast == null) {
//            toast = Toast.makeText(GooglePlayApplication.context, str, Toast.LENGTH_SHORT);
//        } else {
//            toast.cancel();
//            toast = null;
//        }
//        toast.show();
//    }

    //此种方法最为常用,但是在有一些时候也会出现问题的,使用上边的为佳
    private static Toast toast;

    public static void toast(String str) {
        if (toast == null) {
            toast = Toast.makeText(GooglePlayApplication.context, str, Toast.LENGTH_SHORT);
        } else {
            toast.setText(str);
        }
        toast.show();
    }
}
