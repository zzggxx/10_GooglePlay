package com.itcast.googleplay.anim;

import android.view.View;

/**
 * 顶部值改变的动画
 * Created by Lenovo on 2016/8/2.
 */
public class PaddingTopAnim extends BaseAnim {
    public PaddingTopAnim(View target, int startValue, int endValue) {
        super(target, startValue, endValue);
    }

    @Override
    protected void doAnimation(float valueAnimator) {
        target.setPadding(target.getPaddingLeft(), (int) valueAnimator, target.getPaddingRight(), target.getPaddingBottom());
    }
}
