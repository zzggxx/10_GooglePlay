package com.itcast.googleplay.anim;

import android.animation.ValueAnimator;
import android.view.View;

/**
 * Created by Lenovo on 2016/8/2.
 */
public abstract class BaseAnim {
    protected ValueAnimator valueAnimator = null;
    protected View target;

    public BaseAnim(View target, int startValue, int endValue) {
        this.target = target;
        valueAnimator = ValueAnimator.ofFloat(startValue, endValue);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float animatedValue = (float) valueAnimator.getAnimatedValue();
                doAnimation(animatedValue);
            }
        });
    }

    public void startAnimation(int duration) {
        valueAnimator.setDuration(duration).start();
    }

    protected abstract void doAnimation(float valueAnimator);
}
