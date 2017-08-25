package com.itcast.googleplay.holder;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.itcast.googleplay.R;
import com.itcast.googleplay.anim.HeightAnim;
import com.itcast.googleplay.bean.AppInfo;
import com.itcast.googleplay.global.GooglePlayApplication;
import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.view.ViewPropertyAnimator;

/**
 * Created by Lenovo on 2016/8/2.
 */
public class AppDesHolder extends BaseHolder<AppInfo> implements View.OnClickListener {
    private TextView tv_des, tv_author;
    private ImageView iv_des_arrow;

    public void setScrollView(ScrollView scrollView) {
        this.scrollView = scrollView;
    }

    private ScrollView scrollView;


    @Override
    protected View initHolderView() {
        View view = View.inflate(GooglePlayApplication.context, R.layout.layout_detail_app_des, null);
        tv_des = (TextView) view.findViewById(R.id.tv_des);
        tv_author = (TextView) view.findViewById(R.id.tv_author);
        iv_des_arrow = (ImageView) view.findViewById(R.id.iv_des_arrow);

        view.setOnClickListener(this);
        return view;
    }

    @Override
    public void bindData(AppInfo appInfo) {
        tv_des.setText(appInfo.getDes());
        tv_author.setText(appInfo.getAuthor());

        tv_des.setMaxLines(5);
        tv_des.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                //$$this,代表的就是里边的注册者
                tv_des.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                minHeight = tv_des.getHeight();

                tv_des.setMaxLines(Integer.MAX_VALUE);
                tv_des.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        tv_des.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                        maxHeight = tv_des.getHeight();

                        ViewGroup.LayoutParams layoutParams = tv_des.getLayoutParams();
                        layoutParams.height = minHeight;
                        tv_des.setLayoutParams(layoutParams);

                    }
                });
            }
        });

    }

    private int minHeight;
    private int maxHeight;
    private boolean isExtend = false;
    private boolean isAniming = false;

    @Override
    public void onClick(View view) {
        if (view == holderView) {

            if (isAniming) {
                return;
            }
            HeightAnim heightAnim = null;
            if (isExtend) {
                //收缩
                heightAnim = new HeightAnim(tv_des, maxHeight, minHeight);
            } else {
                heightAnim = new HeightAnim(tv_des, minHeight, maxHeight);
            }
            heightAnim.startAnimation(350);

            heightAnim.setListener(new HeightAnim.OnHeightChangeListener() {
                @Override
                public void onHeightChange(int valueAnimator) {
                    //正值是向上滑动的
                    scrollView.scrollBy(0, maxHeight - minHeight);
                }
            });

            ViewPropertyAnimator.animate(iv_des_arrow)
                    .rotation(180)
                    .setDuration(350)
                    .setListener(new MyListener())
                    .start();
            isExtend = !isExtend;
        }
    }

    public class MyListener implements Animator.AnimatorListener {
        @Override
        public void onAnimationStart(Animator animator) {
            isAniming = true;
        }

        @Override
        public void onAnimationEnd(Animator animator) {
            isAniming = false;
        }

        @Override
        public void onAnimationCancel(Animator animator) {

        }

        @Override
        public void onAnimationRepeat(Animator animator) {

        }
    }

}

