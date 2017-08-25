package com.itcast.googleplay.holder;

import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.itcast.googleplay.R;
import com.itcast.googleplay.anim.PaddingTopAnim;
import com.itcast.googleplay.bean.AppInfo;
import com.itcast.googleplay.bean.SafeInfo;
import com.itcast.googleplay.global.GooglePlayApplication;
import com.itcast.googleplay.http.Api;
import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.view.ViewHelper;
import com.nineoldandroids.view.ViewPropertyAnimator;

import java.util.ArrayList;

import it.sephiroth.android.library.picasso.Picasso;

/**
 * Created by Lenovo on 2016/8/2.
 */
public class AppSafeholder extends BaseHolder<AppInfo> implements View.OnClickListener {

    private ImageView iv_safe_image1, iv_safe_image2, iv_safe_image3, iv_safe_arrow;
    private ImageView iv_safe_icon1, iv_safe_icon2, iv_safe_icon3;
    private TextView tv_safe_des1, tv_safe_des2, tv_safe_des3;
    private LinearLayout ll_safe2, ll_safe3, ll_safe_container;

    @Override
    protected View initHolderView() {
        View view = View.inflate(GooglePlayApplication.context, R.layout.layout_detail_app_safe, null);
        iv_safe_image1 = (ImageView) view.findViewById(R.id.iv_safe_image1);
        iv_safe_image2 = (ImageView) view.findViewById(R.id.iv_safe_image2);
        iv_safe_image3 = (ImageView) view.findViewById(R.id.iv_safe_image3);
        iv_safe_arrow = (ImageView) view.findViewById(R.id.iv_safe_arrow);

        iv_safe_icon1 = (ImageView) view.findViewById(R.id.iv_safe_icon1);
        iv_safe_icon2 = (ImageView) view.findViewById(R.id.iv_safe_icon2);
        iv_safe_icon3 = (ImageView) view.findViewById(R.id.iv_safe_icon3);

        tv_safe_des1 = (TextView) view.findViewById(R.id.tv_safe_des1);
        tv_safe_des2 = (TextView) view.findViewById(R.id.tv_safe_des2);
        tv_safe_des3 = (TextView) view.findViewById(R.id.tv_safe_des3);

        ll_safe2 = (LinearLayout) view.findViewById(R.id.ll_safe2);
        ll_safe3 = (LinearLayout) view.findViewById(R.id.ll_safe3);
        ll_safe_container = (LinearLayout) view.findViewById(R.id.ll_safe_container);

        view.setOnClickListener(this);

        return view;
    }

    @Override
    public void bindData(AppInfo appInfo) {
        ArrayList<SafeInfo> safeList = appInfo.getSafe();
        SafeInfo info1 = safeList.get(0);
        Picasso.with(GooglePlayApplication.context).load(Api.IMAGE_PREFIX + info1.getSafeUrl()).into(iv_safe_image1);
        Picasso.with(GooglePlayApplication.context).load(Api.IMAGE_PREFIX + info1.getSafeDesUrl()).into(iv_safe_icon1);
        tv_safe_des1.setText(info1.getSafeDes());

        if (safeList.size() > 1) {
            SafeInfo info2 = safeList.get(1);
            Picasso.with(GooglePlayApplication.context).load(Api.IMAGE_PREFIX + info2.getSafeUrl()).into(iv_safe_image2);
            Picasso.with(GooglePlayApplication.context).load(Api.IMAGE_PREFIX + info2.getSafeDesUrl()).into(iv_safe_icon2);
            tv_safe_des2.setText(info2.getSafeDes());
        } else {
            ll_safe2.setVisibility(View.GONE);
        }

        if (safeList.size() > 2) {
            SafeInfo info3 = safeList.get(2);
            Picasso.with(GooglePlayApplication.context).load(Api.IMAGE_PREFIX + info3.getSafeUrl()).into(iv_safe_image3);
            Picasso.with(GooglePlayApplication.context).load(Api.IMAGE_PREFIX + info3.getSafeDesUrl()).into(iv_safe_icon3);
            tv_safe_des3.setText(info3.getSafeDes());
        } else {
            ll_safe3.setVisibility(View.GONE);
        }

        //开始的时候进行隐藏
        maxPaddingTop = ll_safe_container.getPaddingTop();
        ll_safe_container.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                minPaddingTop = -ll_safe_container.getMeasuredHeight();
                ll_safe_container.setPadding(ll_safe_container.getPaddingLeft(), minPaddingTop,
                        ll_safe_container.getPaddingRight(), ll_safe_container.getPaddingBottom());
                ll_safe_container.getViewTreeObserver().removeGlobalOnLayoutListener(this);
            }
        });

        //开始的时候初始化整个View到最左边
        ViewHelper.setTranslationX(holderView, -holderView.getMeasuredWidth());
        ViewPropertyAnimator.animate(holderView)
                .translationXBy(holderView.getMeasuredWidth())
                .setInterpolator(new OvershootInterpolator())
                .setDuration(400)
                .setStartDelay(600)
                .start();

    }

    /**
     * 执行伸缩的动画
     */
    private boolean isExtend = false;
    private boolean isAniming = false;
    private PaddingTopAnim topAnim;
    private int minPaddingTop;
    private int maxPaddingTop;

    @Override
    public void onClick(View view) {
        if (view == holderView) {

            if (isAniming) {
                return;
            }

            if (isExtend) {
                //收缩
                topAnim = new PaddingTopAnim(ll_safe_container, maxPaddingTop, minPaddingTop);
            } else {
                topAnim = new PaddingTopAnim(ll_safe_container, minPaddingTop, maxPaddingTop);
            }

            topAnim.startAnimation(350);
            isExtend = !isExtend;

            //将箭头反转并且对动画的事件进行监听
            ViewPropertyAnimator.animate(iv_safe_arrow)
                    .setStartDelay(500)
                    .setDuration(350)
                    .setListener(new MyListener())
                    .rotationBy(180);
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
