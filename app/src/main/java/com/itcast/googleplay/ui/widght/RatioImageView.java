package com.itcast.googleplay.ui.widght;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * 根据指定的宽高比动态的获取自身的宽高,
 * $$$$防止出现listView的跳动两种解决方法:
 * 1,设置一张默认的背景;
 * 2,使用代码测量的方法(又有两种实用工具类或者是自定义imageview),使用网络的时候可以使用一定的效果
 * Created by Lenovo on 2016/7/29.
 */
public class RatioImageView extends ImageView {
    /**
     * 初始化的高宽比
     */
    private float ratio = 1.5f;

    public RatioImageView(Context context) {
        super(context);
    }

    public RatioImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        //****获取自定义的属性值,这里使用的两个参数,若是不知道三种构造方法都写上,复习自定义控件
        //命名空间和eclipse是不一样的,注意最后是res-auto,布局和java保持一致
        String namespace = "http://schemas.android.com/apk/res-auto";
        ratio = attrs.getAttributeFloatValue(namespace, "ratio", 0f);   //获取自定义属性的值,复习自定义控件
    }

    public RatioImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * 设置宽高比,就是让外界使用它,随时的更改
     *
     * @param ratio
     */
    public void setRatio(float ratio) {
        this.ratio = ratio;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        /**
         * widthMeasureSpec和heightMeasureSpec是父View帮我们计算好并且传给子View了
         * 测量规则： MeasureSpec ,封装了size和mode
         * size: 就是具体的大小值
         * mode: 测量模式, 对应的是xml布局中的参数
         * 		MeasureSpec.AT_MOST 对应的是wrap_content;
         * 		MeasureSpec.EXACTLY 对应的是match_parent,具体的dp值
         * 		MeasureSpec.UNSPECIFIED 表示未指定的，一般不用，只在adapter的测量用到
         */
        int width = MeasureSpec.getSize(widthMeasureSpec);  //也可以得到屏幕的宽度减去两边的padding值
        if (ratio != 0) {
            float height = width / ratio;
            heightMeasureSpec = MeasureSpec.makeMeasureSpec((int) height, MeasureSpec.EXACTLY);
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);//super是否删除要看父类的实现方法
    }
}
