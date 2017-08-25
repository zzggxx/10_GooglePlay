package com.itcast.googleplay.utils;

import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Build;

/**
 * Created by Lenovo on 2016/8/1.
 */
public class DrawableUtil {
    /**
     * 生成圆角的图片对应的sharp标签
     *
     * @param rgb
     * @param radius
     * @return
     */
    public static Drawable generateDrawable(int rgb, float radius) {
        GradientDrawable drawable = new GradientDrawable();
        drawable.setShape(GradientDrawable.RECTANGLE);
        drawable.setColor(rgb);
        drawable.setCornerRadius(radius);
        return drawable;
    }


    public static Drawable generateSelector(Drawable pressed, Drawable normal) {
        StateListDrawable drawable = new StateListDrawable();
        drawable.addState(new int[]{android.R.attr.state_pressed}, pressed);
        drawable.addState(new int[]{}, normal);

        //设置状态选择器的过度动画
        if (Build.VERSION.SDK_INT > 10) {
            drawable.setEnterFadeDuration(500);
            drawable.setExitFadeDuration(500);
        }

        return drawable;
    }

}
