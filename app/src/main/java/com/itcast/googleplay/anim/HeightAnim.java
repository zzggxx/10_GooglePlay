package com.itcast.googleplay.anim;

import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Lenovo on 2016/8/2.
 */
public class HeightAnim extends BaseAnim {
    public HeightAnim(View target, int startValue, int endValue) {
        super(target, startValue, endValue);
    }

    @Override
    protected void doAnimation(float valueAnimator) {
        ViewGroup.LayoutParams layoutParams = target.getLayoutParams();
        layoutParams.height = (int) valueAnimator;
        target.setLayoutParams(layoutParams);

        if (listener != null) {
            listener.onHeightChange((int) valueAnimator);
        }
    }

    public void setListener(OnHeightChangeListener listener) {
        this.listener = listener;
    }

    private OnHeightChangeListener listener;

    public interface OnHeightChangeListener {
        void onHeightChange(int valueAnimator);
    }
}
