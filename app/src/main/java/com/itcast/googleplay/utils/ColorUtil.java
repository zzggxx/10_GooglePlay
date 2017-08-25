package com.itcast.googleplay.utils;

import android.graphics.Color;

import java.util.Random;

/**
 * Created by Lenovo on 2016/8/1.
 */
public class ColorUtil {
    /**
     * 随机生成一些漂亮的颜色
     *
     * @return
     */
    public static int randomColor() {
        Random random = new Random();
        int red = random.nextInt(120);
        int green = random.nextInt(120);
        int blue = random.nextInt(120);
        return Color.rgb(red, green, blue);
    }
}
